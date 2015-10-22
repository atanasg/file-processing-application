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
package com.atanasg.fileprocessingapp.mvc.controller;

import com.atanasg.fileprocessingapp.mvc.model.FileContentModel;
import com.atanasg.fileprocessingapp.mvc.view.FileProcessingAppView;

/**
 * This application uses the Model-View-Controller pattern.
 *
 * This interface defines the methods supported by the
 * Controller component. A View ({@link FileProcessingAppView})
 * forwards the user input and calls the processUserCommand(...)
 * method.
 *
 * @author Atanas Gegov
 */
public interface FileProcessingAppController {

	public void setView(FileProcessingAppView appView);

	public void setModel(FileContentModel fileContentModel);

	public void processUserCommand(String userInputLine);

}
