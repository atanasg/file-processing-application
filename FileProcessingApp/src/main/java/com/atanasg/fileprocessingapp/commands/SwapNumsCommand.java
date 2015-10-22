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
import com.atanasg.fileprocessingapp.mvc.model.FileContentModel;
import com.atanasg.fileprocessingapp.mvc.view.FileProcessingAppView;

/**
 * Trigger the {@link FileContentModel} to swap
 * two numbers of the file content.
 *
 * commandArgs[0] = first line index,
 * commandArgs[1] = first line number index,
 * commandArgs[2] = second line index,
 * commandArgs[3] = second line number index.
 *
 * @author Atanas Gegov
 */
public class SwapNumsCommand extends AbstractCommandWithArgs {

	public SwapNumsCommand(FileContentModel fileContentModel,
			FileProcessingAppView userInterface, String[] commandArgs) {
		super(fileContentModel, userInterface, commandArgs);
	}

	@Override
	public void execute() {
		CommandExecStatus swapNumsCommandStatus;

		boolean[] swapnumsParamIsInt = {true, true, true, true};
		swapNumsCommandStatus = checkNumberAndTypeOfArguments(4, swapnumsParamIsInt);

		if(swapNumsCommandStatus.isSuccessful()) {
			int firstLineIndex = Integer.parseInt(commandArgs[0]);
			int firstLineNumberIndex = Integer.parseInt(commandArgs[1]);
			int secondLineIndex = Integer.parseInt(commandArgs[2]);
			int secondLineNumberIndex = Integer.parseInt(commandArgs[3]);

			userInterface.showInfoForCommandInProgress(
					String.format("Swapping numbers at position (%d,%d) and (%d,%d)...",
							firstLineIndex, firstLineNumberIndex,
							secondLineIndex, secondLineNumberIndex));
			swapNumsCommandStatus = fileContentModel.swapNumbers(firstLineIndex - 1, firstLineNumberIndex - 1,
					secondLineIndex - 1, secondLineNumberIndex - 1);
		}
		userInterface.showCommandExecutionStatus(swapNumsCommandStatus);
	}

}
