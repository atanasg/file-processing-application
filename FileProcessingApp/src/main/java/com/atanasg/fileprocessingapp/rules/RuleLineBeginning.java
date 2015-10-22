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
 * A class dealing with the rule concerning
 * the valid start of a file line.
 *
 * @author Atanas Gegov
 */
public class RuleLineBeginning {

	private static final String VALID_LINE_BEGINNING_REGEX = "[1-9]";

	public static boolean isValidLineBeginning (String fileLine) {
		String beginningOfLine = fileLine.substring(0, 1);
		if(beginningOfLine.matches(VALID_LINE_BEGINNING_REGEX)) {
			return true;
		} else {
			return false;
		}
	}

}
