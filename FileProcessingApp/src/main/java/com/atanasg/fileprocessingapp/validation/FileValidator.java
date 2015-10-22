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
package com.atanasg.fileprocessingapp.validation;

import java.util.List;

import com.atanasg.fileprocessingapp.command.status.CommandExecStatus;
import com.atanasg.fileprocessingapp.mvc.controller.FileProcessingAppController;

/**
 * This interface defines the methods needed
 * for a component able to validate file content.
 *
 * A validating component uses rules from the
 * com.atanasg.fileprocessingapp.rules package.
 *
 * A Controller ({@link FileProcessingAppController}) calls
 * this component on receiving a validation command from
 * the View (resp. the user).
 *
 * @author Atanas Gegov
 */
public interface FileValidator {

	public static final String LINE_HAS_AN_INVALID_CHARACTER_AT_POSITION =
			"Line %d has an invalid character '%s' at position %d";
	public static final String NO_VALIDATION_ERRORS_DETECTED =
			"No validation errors detected";

	public CommandExecStatus validateFileContents(List<String> fileLines);

}
