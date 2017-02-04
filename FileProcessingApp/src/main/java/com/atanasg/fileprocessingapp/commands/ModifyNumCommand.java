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

import java.math.BigInteger;

import com.atanasg.fileprocessingapp.command.status.CommandExecStatus;
import com.atanasg.fileprocessingapp.mvc.model.FileContentModel;
import com.atanasg.fileprocessingapp.mvc.view.FileProcessingAppView;

/**
 * Modify a number (set a new value) at a specific
 * position acting on the {@link FileContentModel}.
 * Propagate the status to the {@link FileProcessingAppView}.
 *
 * commandArgs[0] = line index,
 * commandArgs[1] = line number index,
 * commandArgs[2] = the new value.
 *
 * @author Atanas Gegov
 */
public class ModifyNumCommand extends AbstractCommandWithArgs {
	private static final int ARGS_COUNT = 3;

	public ModifyNumCommand(FileContentModel fileContentModel,
			FileProcessingAppView userInterface, String[] commandArgs) {
		super(fileContentModel, userInterface, commandArgs);
	}

	@Override
	public void execute() {
		CommandExecStatus modifyNumCommandStatus;

		boolean[] modifynumParamIsInt = {true, true, true};
		modifyNumCommandStatus = checkNumberAndTypeOfArguments(ARGS_COUNT, modifynumParamIsInt);

		if (modifyNumCommandStatus.isSuccessful()) {
			int lineIndex = Integer.parseInt(commandArgs[0]);
			int lineNumberIndex = Integer.parseInt(commandArgs[1]);
			BigInteger numberToBeSet = new BigInteger(commandArgs[2]);

			userInterface.showInfoForCommandInProgress(
					String.format("Replacing number at position (%d,%d) with %s...",
							lineIndex, lineNumberIndex, numberToBeSet.toString()));
			modifyNumCommandStatus = fileContentModel.modifyNumber(lineIndex - 1,
					lineNumberIndex - 1, numberToBeSet);
		}
		userInterface.showCommandExecutionStatus(modifyNumCommandStatus);
	}

}
