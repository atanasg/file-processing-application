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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.atanasg.fileprocessingapp.command.status.CommandExecStatus;
import com.atanasg.fileprocessingapp.command.status.CommandSuccessful;
import com.atanasg.fileprocessingapp.mvc.model.FileContentModel;
import com.atanasg.fileprocessingapp.mvc.view.FileProcessingAppView;
import com.atanasg.fileprocessingapp.validation.FileValidator;
import com.atanasg.fileprocessingapp.validation.FileValidatorImpl;

/**
 * Get the current file content from the {@link FileContentModel}
 * and save it to the provided file. Propagate the status to the
 * {@link FileProcessingAppView}.
 *
 * commandArgs[0] = the path to a file
 *
 * @author Atanas Gegov
 */
public class SaveCommand extends AbstractCommandWithArgs {

	private FileValidator fileValidator;

	public SaveCommand(FileContentModel fileContentModel,
			FileProcessingAppView userInterface, String[] commandArgs) {
		super(fileContentModel, userInterface, commandArgs);
		this.fileValidator = new FileValidatorImpl();
	}

	@Override
	public void execute() {
		String fileName = commandArgs[0];
		userInterface.showInfoForCommandInProgress("Saving opened file...");
		CommandExecStatus saveCommandStatus;

		List<String> fileLines = fileContentModel.getFileContentAsFileLines();
		saveCommandStatus = fileValidator.validateFileContents(fileLines);

		if(saveCommandStatus.isSuccessful()) {
			File destinationFile = new File(fileName);
			saveFileLinesToFile(destinationFile, fileLines);
			saveCommandStatus = new CommandSuccessful();
			saveCommandStatus.appendDetailedInfo("File successfully saved");
		}
		userInterface.showCommandExecutionStatus(saveCommandStatus);
	}

	private void saveFileLinesToFile(File file, List<String> fileLines) {
		try(BufferedWriter buffWriter = new BufferedWriter(new FileWriter(file))) {
			for(String line : fileLines) {
				buffWriter.write(line + "\n");
			}
			buffWriter.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
