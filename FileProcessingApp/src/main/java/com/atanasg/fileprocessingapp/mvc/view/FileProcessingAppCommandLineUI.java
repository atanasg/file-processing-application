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
package com.atanasg.fileprocessingapp.mvc.view;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.atanasg.fileprocessingapp.command.status.CommandExecStatus;
import com.atanasg.fileprocessingapp.commands.CommandNames;
import com.atanasg.fileprocessingapp.mvc.controller.FileProcessingAppController;

/**
 * An implementation of the {@link FileProcessingAppView}
 * interface. This View reads input/commands from the standard input,
 * forwards the input to the {@link FileProcessingAppController}
 * and shows results on the standard output.
 *
 * @author Atanas Gegov
 */
public class FileProcessingAppCommandLineUI implements FileProcessingAppView {

	private static final String SEPARATING_LINE_STRING = "========================";
	private static final String SMALL_SEPARATING_LINE_STRING = "----";
	
	private FileProcessingAppController appController;
	
	public FileProcessingAppCommandLineUI() {
		this.appController = null;
	}

	@Override
	public void setController(FileProcessingAppController appController) {
		checkState(this.appController == null, "View: App controller already set");

		this.appController = appController;
	}

	@Override
	public void showUsageHelp() {
		System.out.println(SEPARATING_LINE_STRING);
		System.out.println("FILE PROCESSING APPLICATION - SUPPORTED COMMANDS");
		System.out.println(SMALL_SEPARATING_LINE_STRING);
		System.out.format("%s - Prints this message%n", CommandNames.COMMAND_HELP);
		System.out.println(SMALL_SEPARATING_LINE_STRING);
		System.out.format("%s <line_index> <line_number_index> <new integer to set> - "
				+ "Insert a number at a specific position%n", CommandNames.COMMAND_INSERTNUM);
		System.out.format("%s <first_line_index> <second_line_index> - "
				+ "Swap two lines of the file.%n", CommandNames.COMMAND_SWAPLINES);
		System.out.format("%s <first_line_index> <first_line_number_index> <second_line_index> <second_line_number_index> - "
				+ "Swap two numbers at specified positions%n", CommandNames.COMMAND_SWAPNUMS);
		System.out.format("%s <line_index> <line_number_index> - "
				+ "Read a number at a specified position%n", CommandNames.COMMAND_READNUM);
		System.out.format("%s <line_index> <line_number_index> <new integer to be set> - "
				+ "Modify a number at a specified position and set a new value%n", CommandNames.COMMAND_MODIFYNUM);
		System.out.format("%s <line_index> <line_number_index> - "
				+ "Remove a number at a specified position%n", CommandNames.COMMAND_REMOVENUM);
		System.out.println("* PAY ATTENTION: The first number in the first row has <line_index> = 1 and <line_number_index> = 1");
		System.out.println(SMALL_SEPARATING_LINE_STRING);
		System.out.format("%s - Save the performed (if any) changes in the file%n",
				CommandNames.COMMAND_SAVE);
		System.out.format("%s - Exit the application%n", CommandNames.COMMAND_QUIT);
		System.out.println(SMALL_SEPARATING_LINE_STRING);
		System.out.format("%s - Validate the file content%n", CommandNames.COMMAND_VALIDATE);
		System.out.println(SEPARATING_LINE_STRING);
	}

	@Override
	public void askUserForCommand(String promptText) {
		checkNotNull(appController, "The view is not connected to a controller");

		System.out.println("\n" + promptText + ":");

		String userCommandInput;
		BufferedReader buffConsoleReader = new BufferedReader(
				new InputStreamReader(System.in));
		try {
			userCommandInput = buffConsoleReader.readLine();
			appController.processUserCommand(userCommandInput);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void showInfoForCommandInProgress(String infoForCommandInProgress) {
		System.out.println(infoForCommandInProgress);
	}

	@Override
	public void showCommandExecutionStatus(CommandExecStatus commandStatus) {
		System.out.println(commandStatus.getStatusMessage());
		System.out.println(commandStatus.getDetailedInformation());	
	}

	@Override
	public void terminateApplication() {
		System.out.println("Exiting application...");
		System.exit(0);
	}

}
