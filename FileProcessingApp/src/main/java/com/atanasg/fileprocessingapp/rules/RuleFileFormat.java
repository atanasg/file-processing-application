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
 * the valid file formats that this application
 * works with.
 *
 * @author Atanas Gegov
 */
public class RuleFileFormat {

	private static final String VALID_FILE_FORMAT = ".txt";

	public static boolean isValidFileFormat(String fileName) {
		if(fileName.endsWith(VALID_FILE_FORMAT)) {
			return true;
		} else {
			return false;
		}
	}

	public static String getValidFileFormatAsString() {
		return VALID_FILE_FORMAT;
	}
}
