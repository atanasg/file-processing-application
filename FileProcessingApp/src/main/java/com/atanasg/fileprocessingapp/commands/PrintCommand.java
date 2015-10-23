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
package com.atanasg.fileprocessingapp.commands;

import java.util.List;

import com.atanasg.fileprocessingapp.command.status.CommandExecStatus;
import com.atanasg.fileprocessingapp.command.status.CommandFailed;
import com.atanasg.fileprocessingapp.command.status.CommandSuccessful;
import com.atanasg.fileprocessingapp.mvc.model.FileContentModel;
import com.atanasg.fileprocessingapp.mvc.view.FileProcessingAppView;

/**
 * Get the file content currently stored in the
 * {@link FileContentModel} and forward to the
 * {@link FileProcessingAppView} that shows it
 * to the user.
 *
 * @author Atanas Gegov
 */
public class PrintCommand extends AbstractCommand {

	public PrintCommand(FileContentModel fileContentModel,
			FileProcessingAppView userInterface) {
		super(fileContentModel, userInterface);
	}

	@Override
	public void execute() {
		CommandExecStatus printCommandStatus;

		userInterface.showInfoForCommandInProgress("Printing current content...");
		List<String> fileLines = fileContentModel.getFileContentAsFileLines();

		if(fileLines.isEmpty()) {
			printCommandStatus = new CommandFailed();
			printCommandStatus.appendDetailedInfo("No content found");
		} else {
			printCommandStatus = new CommandSuccessful();
			for(String line : fileLines) {
				printCommandStatus.appendDetailedInfo(line);
			}
		}
		userInterface.showCommandExecutionStatus(printCommandStatus);
	}

}
