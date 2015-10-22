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

import com.atanasg.fileprocessingapp.command.status.CommandExecStatus;
import com.atanasg.fileprocessingapp.command.status.CommandSuccessful;
import com.atanasg.fileprocessingapp.mvc.model.FileContentModel;
import com.atanasg.fileprocessingapp.mvc.view.FileProcessingAppView;

/**
 * Trigger the {@link FileContentModel} to read a value
 * at a specific position. On success propagate the
 * read value to the {@link FileProcessingAppView}
 * using a {@link CommandSuccessful} object.
 *
 * commandArgs[0] = line index,
 * commandArgs[1] = line number index.
 *
 * @author Atanas Gegov
 */
public class ReadNumCommand extends AbstractCommandWithArgs {

	public ReadNumCommand(FileContentModel fileContentModel,
			FileProcessingAppView userInterface, String[] commandArgs) {
		super(fileContentModel, userInterface, commandArgs);
	}

	@Override
	public void execute() {
		CommandExecStatus readNumCommandStatus;

		boolean[] readnumParamIsInt = {true, true};
		readNumCommandStatus = checkNumberAndTypeOfArguments(2, readnumParamIsInt);

		if(readNumCommandStatus.isSuccessful()) {
			int lineIndex = Integer.parseInt(commandArgs[0]);
			int numberIndexInLine = Integer.parseInt(commandArgs[1]);

			userInterface.showInfoForCommandInProgress(
					String.format("Reading number at position (%d,%d)...", lineIndex, numberIndexInLine));
			readNumCommandStatus = fileContentModel.readNumber(lineIndex - 1, numberIndexInLine - 1);
		}
		userInterface.showCommandExecutionStatus(readNumCommandStatus);
	}

}
