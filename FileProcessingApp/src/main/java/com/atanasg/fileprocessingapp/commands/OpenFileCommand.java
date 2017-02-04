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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import com.atanasg.fileprocessingapp.command.status.CommandExecStatus;
import com.atanasg.fileprocessingapp.command.status.CommandFailed;
import com.atanasg.fileprocessingapp.command.status.CommandSuccessful;
import com.atanasg.fileprocessingapp.mvc.model.FileContentModel;
import com.atanasg.fileprocessingapp.mvc.view.FileProcessingAppView;
import com.atanasg.fileprocessingapp.rules.RuleFileFormat;

/**
 * Check and open a file and feed the {@link FileContentModel}
 * with it. Propagate the status to the
 * {@link FileProcessingAppView}.
 *
 * commandArgs[0] = the path to a file
 *
 * @author Atanas Gegov
 */
public class OpenFileCommand extends AbstractCommandWithArgs {

	public OpenFileCommand(FileContentModel fileContentModel,
			FileProcessingAppView userInterface, String[] commandArgs) {
		super(fileContentModel, userInterface, commandArgs);
	}

	@Override
	public void execute() {
		String pathToFile = commandArgs[0];
		userInterface.showInfoForCommandInProgress(String.format("Reading file '%s'...", pathToFile));
		File file = new File(pathToFile);
		CommandExecStatus fileProcessingStatus;

		if (!file.exists() || !file.isFile() || !RuleFileFormat.isValidFileFormat(pathToFile)) {
			fileProcessingStatus = new CommandFailed();
			fileProcessingStatus.appendDetailedInfo(
					String.format("The specified path '%s' is not a valid file", pathToFile));

			userInterface.showCommandExecutionStatus(fileProcessingStatus);
			userInterface.terminateApplication();
		} else {
			List<String> fileLines = readFileLines(file);
			fileContentModel.parseFileContent(fileLines);
			fileProcessingStatus = new CommandSuccessful();
			fileProcessingStatus.appendDetailedInfo(
					String.format("Successfully parsed %d lines from file %s",
							fileContentModel.getNumberOfLines(), pathToFile));

			userInterface.showCommandExecutionStatus(fileProcessingStatus);
			userInterface.showUsageHelp();
		}
	}

	private List<String> readFileLines(File file) {
		List<String> fileLines = new LinkedList<String>();
		try (BufferedReader buffReader = new BufferedReader(new FileReader(file))) {
			String fileLine = buffReader.readLine();
			while (fileLine != null) {
				fileLines.add(fileLine);
				fileLine = buffReader.readLine();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fileLines;
	}

}
