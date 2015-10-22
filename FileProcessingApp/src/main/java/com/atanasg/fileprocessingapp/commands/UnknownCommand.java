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
import com.atanasg.fileprocessingapp.command.status.CommandFailed;
import com.atanasg.fileprocessingapp.mvc.model.FileContentModel;
import com.atanasg.fileprocessingapp.mvc.view.FileProcessingAppView;

/**
 * Process an unknown user input. Only
 * inform the {@link FileProcessingAppView}.
 *
 * @author Atanas Gegov
 */
public class UnknownCommand extends AbstractCommandWithArgs {

	public UnknownCommand(FileContentModel fileContentModel,
			FileProcessingAppView userInterface, String[] commandArgs) {
		super(fileContentModel, userInterface, commandArgs);
	}

	@Override
	public void execute() {
		userInterface.showInfoForCommandInProgress(String.format("Executing '%s'...", commandArgs[0]));
		CommandExecStatus unknownCommandStatus = new CommandFailed();
		unknownCommandStatus.appendDetailedInfo("Unknown command");
		userInterface.showCommandExecutionStatus(unknownCommandStatus);
	}

}
