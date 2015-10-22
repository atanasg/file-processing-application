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
import com.atanasg.fileprocessingapp.mvc.model.FileContentModel;
import com.atanasg.fileprocessingapp.mvc.view.FileProcessingAppView;
import com.atanasg.fileprocessingapp.validation.FileValidator;
import com.atanasg.fileprocessingapp.validation.FileValidatorImpl;

/**
 * Validate the file content currently stored in the
 * {@link FileContentModel}. On failure propagate the
 * validation errors to the {@link FileProcessingAppView}
 * using a {@link CommandFailed} object.
 *
 * @author Atanas Gegov
 */
public class ValidateCommand extends AbstractCommand {

	private FileValidator fileValidator;

	public ValidateCommand(FileContentModel fileContentModel,
			FileProcessingAppView userInterface) {
		super(fileContentModel, userInterface);
		this.fileValidator = new FileValidatorImpl();
	}

	@Override
	public void execute() {
		userInterface.showInfoForCommandInProgress("Validating file content...");
		List<String> fileLines = fileContentModel.getFileContentAsFileLines();
		CommandExecStatus validateCommandStatus = fileValidator.validateFileContents(fileLines);
		userInterface.showCommandExecutionStatus(validateCommandStatus);
	}

}
