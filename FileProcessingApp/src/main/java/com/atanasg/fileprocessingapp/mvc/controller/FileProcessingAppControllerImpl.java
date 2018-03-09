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
package com.atanasg.fileprocessingapp.mvc.controller;

import static com.google.common.base.Preconditions.checkState;

import java.io.File;
import java.util.StringTokenizer;

import com.atanasg.fileprocessingapp.commands.Command;
import com.atanasg.fileprocessingapp.commands.CommandNames;
import com.atanasg.fileprocessingapp.commands.HelpCommand;
import com.atanasg.fileprocessingapp.commands.InsertNumCommand;
import com.atanasg.fileprocessingapp.commands.ModifyNumCommand;
import com.atanasg.fileprocessingapp.commands.OpenFileCommand;
import com.atanasg.fileprocessingapp.commands.PrintCommand;
import com.atanasg.fileprocessingapp.commands.QuitCommand;
import com.atanasg.fileprocessingapp.commands.ReadNumCommand;
import com.atanasg.fileprocessingapp.commands.RemoveNumCommand;
import com.atanasg.fileprocessingapp.commands.SaveCommand;
import com.atanasg.fileprocessingapp.commands.SwapLinesCommand;
import com.atanasg.fileprocessingapp.commands.SwapNumsCommand;
import com.atanasg.fileprocessingapp.commands.UnknownCommand;
import com.atanasg.fileprocessingapp.commands.ValidateCommand;
import com.atanasg.fileprocessingapp.mvc.model.FileContentModel;
import com.atanasg.fileprocessingapp.mvc.view.FileProcessingAppView;

/**
 * An implementation of the {@link FileProcessingAppController}
 * interface. The Controller gets user input from the View
 * ({@link FileProcessingAppView}), executes commands that act
 * on the Model ({@link FileContentModel}) and propagates results back
 * to the View.
 *
 * @author Atanas Gegov
 */
public class FileProcessingAppControllerImpl implements FileProcessingAppController {

	private FileContentModel fileContentModel;
	private FileProcessingAppView userInterface;
	private File activeFile;

	private boolean firstCommand;

	public FileProcessingAppControllerImpl() {
		this.fileContentModel = null;
		this.userInterface = null;
		this.activeFile = null;
		this.firstCommand = true;
	}

	@Override
	public void setView(FileProcessingAppView appView) {
		checkState(this.userInterface == null, "Controller: App view already set");

		this.userInterface = appView;
	}

	@Override
	public void setModel(FileContentModel fileContentModel) {
		checkState(this.fileContentModel == null, "Controller: File content model already set");

		this.fileContentModel = fileContentModel;
	}

	@Override
	public void processUserCommand(String userInputLine) {
		if (firstCommand) {
			Command openFileCommand = new OpenFileCommand(fileContentModel, userInterface,
					new String[]{userInputLine});
			openFileCommand.execute();

			activeFile = new File(userInputLine);
			firstCommand = false;
		} else {
			StringTokenizer tokenizedCommand = new StringTokenizer(userInputLine, " ", false);
			if (tokenizedCommand.countTokens() > 0) {
				String[] inputParams = new String [tokenizedCommand.countTokens() - 1];

				String commandStr = tokenizedCommand.nextToken();
				for (int i = 0; i < inputParams.length; i++) {
					inputParams[i] = tokenizedCommand.nextToken();
				}
				processRegularCommand(commandStr, inputParams);
			}
		}

		// Handle a next command cycle in a new thread.
		// Otherwise the calling stack increases due to the
		// circular calls between the current method and
		// userInterface.askUserForCommand(...)
		Thread newCommandIteration =
				new Thread(() -> userInterface.askUserForCommand("Enter a command"));
		newCommandIteration.start();
	}

	private void processRegularCommand(String commandStr, String[] commandArgs) {
		Command command;

		switch (commandStr) {
		case CommandNames.COMMAND_HELP:
			command = new HelpCommand(fileContentModel, userInterface);
			break;
		case CommandNames.COMMAND_QUIT:
			command = new QuitCommand(fileContentModel, userInterface);
			break;
		case CommandNames.COMMAND_INSERTNUM:
			command = new InsertNumCommand(fileContentModel, userInterface, commandArgs);
			break;
		case CommandNames.COMMAND_MODIFYNUM:
			command = new ModifyNumCommand(fileContentModel, userInterface, commandArgs);
			break;
		case CommandNames.COMMAND_READNUM:
			command = new ReadNumCommand(fileContentModel, userInterface, commandArgs);
			break;
		case CommandNames.COMMAND_REMOVENUM:
			command = new RemoveNumCommand(fileContentModel, userInterface, commandArgs);
			break;
		case CommandNames.COMMAND_SWAPLINES:
			command = new SwapLinesCommand(fileContentModel, userInterface, commandArgs);
			break;
		case CommandNames.COMMAND_SWAPNUMS:
			command = new SwapNumsCommand(fileContentModel, userInterface, commandArgs);
			break;
		case CommandNames.COMMAND_PRINT:
			command = new PrintCommand(fileContentModel, userInterface);
			break;
		case CommandNames.COMMAND_VALIDATE:
			command = new ValidateCommand(fileContentModel, userInterface);
			break;
		case CommandNames.COMMAND_SAVE:
			command = new SaveCommand(fileContentModel, userInterface,
					new String[]{activeFile.getAbsolutePath()});
			break;
		default:
			command = new UnknownCommand(fileContentModel, userInterface, new String[]{commandStr});
			break;
		}
		command.execute();
	}

}
