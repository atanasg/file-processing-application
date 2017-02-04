/*
 * Copyright (C) 2017 Atanas Gegov
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
package com.atanasg.fileprocessingapp.mvc.view;

import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Unit test for the output of the command-line user interface.
 *
 * @author Atanas Gegov
 */
public class CommandLineUIOutputTest {

	private static FileProcessingAppView commandLineUI;
	private static PrintStream defaultOutputStream;
	private static PrintStream defaultErrorStream;

	private ByteArrayOutputStream testOutStream;
	private ByteArrayOutputStream testErrStream;

	@BeforeClass
	public static void storeDeafultOutputStreams() {
		commandLineUI = new FileProcessingAppCommandLineUI();
		defaultOutputStream = System.out;
		defaultErrorStream = System.err;
	}

	@AfterClass
	public static void restoreDeafultOutputStreams() {
		commandLineUI = null;
		System.setOut(defaultOutputStream);
		System.setErr(defaultErrorStream);
	}

	@Before
	public void setUpStreams() {
		testOutStream = new ByteArrayOutputStream();
		testErrStream = new ByteArrayOutputStream();
		System.setOut(new PrintStream(testOutStream));
		System.setErr(new PrintStream(testErrStream));
	}

	@After
	public void cleanUpStreams() {
		System.setOut(null);
		System.setErr(null);

		try {
			testOutStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			testErrStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testCLIOutputsHelpMessage() {
		commandLineUI.showUsageHelp();

		// check what was written to System.out
		String usageHelpMessage = testOutStream.toString();
		assertTrue(usageHelpMessage.contains("help"));
		assertTrue(usageHelpMessage.contains("insert"));
		assertTrue(usageHelpMessage.contains("index"));
		assertTrue(usageHelpMessage.contains("remove"));
		assertTrue(usageHelpMessage.contains("swap"));
		assertTrue(usageHelpMessage.contains("validate"));
		assertTrue(usageHelpMessage.contains("save"));
		assertTrue(usageHelpMessage.contains("quit"));

		// check that command did not write into System.err
		assertTrue(testErrStream.toString().isEmpty());
	}

	@Test
	public void testCLIOutputsCommandProgressInfo() {
		final String infoForCommandInProgress = "exampleCommandProgressMessage";
		commandLineUI.showInfoForCommandInProgress(infoForCommandInProgress);

		// check what was written to System.out
		String commandProgressMessage = testOutStream.toString();
		assertTrue(commandProgressMessage.contains(infoForCommandInProgress));

		// check that command did not write into System.err
		assertTrue(testErrStream.toString().isEmpty());
	}
}
