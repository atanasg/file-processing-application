/*
 * Copyright (C) 2015 Atanas Gegov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.atanasg.fileprocessingapp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.atanasg.fileprocessingapp.command.status.CommandFailed;
import com.atanasg.fileprocessingapp.command.status.CommandSuccessful;
import com.atanasg.fileprocessingapp.mvc.controller.FileProcessingAppController;
import com.atanasg.fileprocessingapp.mvc.controller.FileProcessingAppControllerImpl;
import com.atanasg.fileprocessingapp.mvc.model.FileContentModel;
import com.atanasg.fileprocessingapp.mvc.model.FileContentModelImpl;
import com.atanasg.fileprocessingapp.mvc.view.FileProcessingAppView;

/**
 * Integration test for the complete application
 * involving all MVC components.
 *
 * @author Atanas Gegov
 */
public class ApplicationIntegrationTest {

	private static ExecutorService executorMock;
	private static FileContentModel fileContentModelSpy;
	private static FileProcessingAppView appUI;
	private static FileProcessingAppController appController;
	private static File testFile;
	private static List<String> inputLines;

	@BeforeClass
	public static void setUpTest() {
		// spy the model
		fileContentModelSpy = spy(new FileContentModelImpl());

		// mock the UI
		appUI = mock(FileProcessingAppView.class);

		// mock the executor service
		executorMock = mock(ExecutorService.class);

		appController = new FileProcessingAppControllerImpl(executorMock);

		// connect MVC components
		appUI.setController(appController);
		appController.setModel(fileContentModelSpy);
		appController.setView(appUI);

		testFile = new File("TestFile.txt");

		inputLines = new LinkedList<String>();
		inputLines.add("100 101 102 103 104");
		inputLines.add("200 201 202 203 204");
		inputLines.add("300 301 302 303 304");
		inputLines.add("400 401 402 403 405 406");

		try (BufferedWriter buffWriter = new BufferedWriter(new FileWriter(testFile))) {
			for (String line : inputLines) {
				buffWriter.write(line + "\n");
			}
			buffWriter.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@AfterClass
	public static void tearDownTest() {
		testFile.delete();
	}

	@Test
	public void testApplicationUsageSequence() {
		// first input is the path to a file
		appController.processUserCommand("TestFile.txt");
		verify(fileContentModelSpy).parseFileContent(anyListOf(String.class));

		// some allowed commands
		appController.processUserCommand("swaplines 1 4");
		verify(fileContentModelSpy).swapLines(0, 3);
		appController.processUserCommand("swapnums 1 1 3 3");
		verify(fileContentModelSpy).swapNumbers(0, 0, 2, 2);
		appController.processUserCommand("insertnum 1 2 666666666666667");
		verify(fileContentModelSpy).insertNumber(0, 1, new BigInteger("666666666666667"));
		appController.processUserCommand("readnum 3 2");
		verify(fileContentModelSpy).readNumber(2, 1);
		appController.processUserCommand("removenum 2 4");
		verify(fileContentModelSpy).removeNumber(1, 3);
		appController.processUserCommand("modifynum 3 1 1");
		verify(fileContentModelSpy).modifyNumber(2, 0, BigInteger.ONE);
		appController.processUserCommand("unknowncommand");

		// some commands with range violation
		appController.processUserCommand("swaplines 0 10");
		appController.processUserCommand("swapnum 1 100 2 200");

		// commands with invalid arguments
		appController.processUserCommand("insertnum 1 1 20abc20");

		// some other commands
		appController.processUserCommand("print");
		appController.processUserCommand("help");
		appController.processUserCommand("validate");

		// save
		appController.processUserCommand("save");

		//// check the correct interaction with the View

		// 13 commands show that they are in progress
		// (all besides 'help' + the invalid argument commands)
		verify(appUI, times(13)).showInfoForCommandInProgress(any(String.class));

		// 10 valid commands ('help' does not print exec status)
		verify(appUI, times(10)).showCommandExecutionStatus(isA(CommandSuccessful.class));

		// 4 invalid commands (invalid range commands + unknown command)
		verify(appUI, times(4)).showCommandExecutionStatus(isA(CommandFailed.class));

		// help is shown firstly at the beginning + one 'help' command;
		verify(appUI, times(2)).showUsageHelp();

		// no termination of the app
		verify(appUI, times(0)).terminateApplication();

		List<String> expectedFileFinalLines = new LinkedList<String>();
		expectedFileFinalLines.add("302 666666666666667 401 402 403 405 406");
		expectedFileFinalLines.add("200 201 202 204");
		expectedFileFinalLines.add("1 301 400 303 304");
		expectedFileFinalLines.add("100 101 102 103 104");
		checkFinalFileContent(expectedFileFinalLines);
	}

	private void checkFinalFileContent(final List<String> expectedFileFinalLines) {
		try (BufferedReader buffReader = new BufferedReader(new FileReader(testFile))) {
			for (int i = 0; i < expectedFileFinalLines.size(); i++) {
				String fileLine = buffReader.readLine();
				assertEquals(expectedFileFinalLines.get(i), fileLine);
			}
			// saved file should not have additional lines
			assertNull(buffReader.readLine());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
