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
package com.atanasg.fileprocessingapp;

import com.atanasg.fileprocessingapp.mvc.controller.FileProcessingAppController;
import com.atanasg.fileprocessingapp.mvc.controller.FileProcessingAppControllerImpl;
import com.atanasg.fileprocessingapp.mvc.model.FileContentModel;
import com.atanasg.fileprocessingapp.mvc.model.FileContentModelImpl;
import com.atanasg.fileprocessingapp.mvc.view.FileProcessingAppCommandLineUI;
import com.atanasg.fileprocessingapp.mvc.view.FileProcessingAppView;
import com.atanasg.fileprocessingapp.rules.RuleFileFormat;

/**
 * The launcher class of the File Processing Application. Initializes and
 * connects the relevant components (Model, View and Controller) and asks for
 * the first user input.
 *
 * @author Atanas Gegov
 */
public final class FileProcessingAppLauncher {
	private static final String USER_FILE_PROMPT_MESSAGE = "Please provide a path to some %s file";

	private FileProcessingAppLauncher() {
	}

	public static void main(String[] args) {
		// create MVC components
		FileContentModel fileContentModel = new FileContentModelImpl();
		FileProcessingAppView appUI = new FileProcessingAppCommandLineUI();
		FileProcessingAppController appController = new FileProcessingAppControllerImpl();

		// connect MVC components
		appUI.setController(appController);
		appController.setModel(fileContentModel);
		appController.setView(appUI);

		// Ask the user for a file
		appUI.askUserForCommand(String.format(USER_FILE_PROMPT_MESSAGE,
				RuleFileFormat.getValidFileFormatAsString()));
	}
}
