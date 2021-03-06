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

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * A class dealing with the valid separators
 * allowed inside a file.
 *
 * @author Atanas Gegov
 */
public final class RuleSeparators {

	private static final String[] VALID_SEPARATORS = { " ", "\t" };

	public static boolean isValidSeparator(char token) {
		return isValidSeparator(token + "");
	}

	public static boolean isValidSeparator(String token) {
		return Arrays.stream(VALID_SEPARATORS).anyMatch(sep -> sep.equals(token));
	}

	public static String getDefaultValidSeparator() {
		return VALID_SEPARATORS[0];
	}

	public static String getAllSeparatorsAsString() {
		return Arrays.stream(VALID_SEPARATORS).collect(Collectors.joining());
	}
}
