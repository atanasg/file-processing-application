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

import com.atanasg.fileprocessingapp.mvc.controller.FileProcessingAppController;
import com.atanasg.fileprocessingapp.mvc.model.FileContentModel;
import com.atanasg.fileprocessingapp.mvc.view.FileProcessingAppView;

/**
 * A class implementing an abstract command
 * without execution parameters. As commands
 * are executed by the {@link FileProcessingAppController}
 * they need to access the {@link FileContentModel}
 * and the {@link FileProcessingAppView}.
 *
 * @author Atanas Gegov
 */
public abstract class AbstractCommand implements Command {

	protected final FileContentModel fileContentModel;
	protected final FileProcessingAppView userInterface;

	protected AbstractCommand(FileContentModel fileContentModel,
			FileProcessingAppView userInterface) {
		this.fileContentModel = fileContentModel;
		this.userInterface = userInterface;
	}

}
