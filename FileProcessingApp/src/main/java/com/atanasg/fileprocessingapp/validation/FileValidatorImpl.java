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

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;

import com.atanasg.fileprocessingapp.command.status.CommandExecStatus;
import com.atanasg.fileprocessingapp.command.status.CommandFailed;
import com.atanasg.fileprocessingapp.command.status.CommandSuccessful;
import com.atanasg.fileprocessingapp.rules.RuleLineBeginning;
import com.atanasg.fileprocessingapp.rules.RuleNonSeparators;
import com.atanasg.fileprocessingapp.rules.RuleSeparators;

/**
 * An implementation of the {@link FileValidator}
 * interface. The file content is being checked
 * according to the rules and detailed description
 * of validation errors is being produced.
 *
 * @author Atanas Gegov
 */
public class FileValidatorImpl implements FileValidator {

	@Override
	public CommandExecStatus validateFileContents(final List<String> fileLines) {
		checkNotNull(fileLines, "The content validation function expects a non-null argument");

		CommandExecStatus validationStatus = null;

		for (int i = 0; i < fileLines.size(); i++) {
			String fileLine = fileLines.get(i);

			// check beginning of line
			boolean lineStartsCorrect = RuleLineBeginning.isValidLineBeginning(fileLine);
			if (!lineStartsCorrect) {
				if (validationStatus == null) {
					validationStatus = new CommandFailed();
				}
				validationStatus.appendDetailedInfo(
						String.format(LINE_HAS_AN_INVALID_CHARACTER_AT_POSITION,
								(i + 1), fileLine.substring(0, 1), 1));
			}

			// check rest of line
			validationStatus = checkLineAfterFirstCharacter(validationStatus, fileLine, i);
		}

		if (validationStatus == null) {
			validationStatus = new CommandSuccessful();
			validationStatus.appendDetailedInfo(NO_VALIDATION_ERRORS_DETECTED);
		}
		return validationStatus;
	}

	private CommandExecStatus checkLineAfterFirstCharacter(CommandExecStatus validationStatus,
			String fileLine, int lineIndex) {

		for (int j = 1; j < fileLine.length(); j++) {
			String currentPosition = fileLine.charAt(j) + "";
			boolean isValidSeparator = RuleSeparators.isValidSeparator(currentPosition);
			boolean isValidNonSeparator = RuleNonSeparators.isValidNonSeparator(currentPosition);
			if (!isValidSeparator && !isValidNonSeparator) {
				if (validationStatus == null) {
					validationStatus = new CommandFailed();
				}
				validationStatus.appendDetailedInfo(
						String.format(LINE_HAS_AN_INVALID_CHARACTER_AT_POSITION,
								(lineIndex + 1), currentPosition, (j + 1)));
			}
		}
		return validationStatus;
	}

}
