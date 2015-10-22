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
package com.atanasg.fileprocessingapp.mvc.view;

import com.atanasg.fileprocessingapp.command.status.CommandExecStatus;
import com.atanasg.fileprocessingapp.mvc.controller.FileProcessingAppController;

/**
 * This application uses the Model-View-Controller pattern.
 * This interface defines the methods supported by the
 * View component.
 * A Controller ({@link FileProcessingAppController}) is supposed
 * to call the methods of the View.
 *
 * @author Atanas Gegov
 */
public interface FileProcessingAppView {

	public void setController(FileProcessingAppController appController);

	public void showUsageHelp();

	public void askUserForCommand(String promptText);

	public void showInfoForCommandInProgress(String infoForCommandInProgress);

	public void showCommandExecutionStatus(CommandExecStatus commandStatus);

	public void terminateApplication();
}
