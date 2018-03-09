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

import java.util.concurrent.ExecutorService;

import com.atanasg.fileprocessingapp.mvc.model.FileContentModel;
import com.atanasg.fileprocessingapp.mvc.view.FileProcessingAppView;

/**
 * Calls the {@link FileProcessingAppView} to
 * terminate the appliation.
 *
 * @author Atanas Gegov
 */
public class QuitCommand extends AbstractCommand {

	private ExecutorService executor;

	public QuitCommand(FileContentModel fileContentModel,
			FileProcessingAppView userInterface, ExecutorService executor) {
		super(fileContentModel, userInterface);
		this.executor = executor;
	}

	@Override
	public void execute() {
		userInterface.showInfoForCommandInProgress("Exiting application...");
		executor.shutdown();
	}

}
