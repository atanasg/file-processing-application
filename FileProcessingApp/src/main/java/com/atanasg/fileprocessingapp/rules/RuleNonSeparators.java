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
package com.atanasg.fileprocessingapp.rules;

/**
 * A class dealing with the valid
 * characters inside a file besides the separators.
 *
 * @author Atanas Gegov
 */
public final class RuleNonSeparators {

	private static final String VALID_NON_SEPARATOR_CHAR_REGEX = "[0-9]";

	public static boolean isValidNonSeparator(char token) {
		return isValidNonSeparator(token + "");
	}

	public static boolean isValidNonSeparator(String token) {
		if (token.matches(VALID_NON_SEPARATOR_CHAR_REGEX)) {
			return true;
		} else {
			return false;
		}
	}
}
