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

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;

import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import org.junit.BeforeClass;
import org.junit.Test;

import com.atanasg.fileprocessingapp.command.status.CommandExecStatus;

/**
 * Unit test for the validation of file content
 * according to specified rules from the
 * com.atanasg.fileprocessingapp.rules package
 *
 * @author Atanas Gegov
 */
public class FileValidationTest {

	private static FileValidator fileValidator;

	@BeforeClass
	public static void setUpTest() {
		fileValidator = new FileValidatorImpl();
	}

	@Test
	public void testEmptyFileIsSuccessfullyValidated() {
		List<String> fileLines = new LinkedList<String>();
		assertSuccessfulValidation(fileLines);
	}

	@Test
	public void testValidOneLineFileIsSuccessfullyValidated() {
		List<String> fileLines = new LinkedList<String>();
		fileLines.add("123");
		assertSuccessfulValidation(fileLines);

		fileLines = new LinkedList<String>();
		fileLines.add("123 456");
		assertSuccessfulValidation(fileLines);

		fileLines = new LinkedList<String>();
		fileLines.add("123	456");
		assertSuccessfulValidation(fileLines);

		fileLines = new LinkedList<String>();
		fileLines.add("123	456 789	");
		assertSuccessfulValidation(fileLines);
	}

	@Test
	public void testValidFileIsSuccessfullyValidated() {
		List<String> fileLines = new LinkedList<String>();
		fileLines.add("1234 4560000 6677	");;
		fileLines.add("12	4056 44");
		fileLines.add("123");
		fileLines.add("1235		4956 789	");
		assertSuccessfulValidation(fileLines);

		fileLines = new LinkedList<String>();
		fileLines.add("112312321525 12312312 5234234234 231321321312 123123 2131353453435345345 ");;
		fileLines.add("213123523520234234 2342340005320 3240230042340230803240 3240909 ");
		fileLines.add("9897823423975239 ");
		assertSuccessfulValidation(fileLines);
	}

	@Test
	public void testInvalidOneLineFileFailsValidation() {
		List<String> fileLines = new LinkedList<String>();
		fileLines.add("0123");     // starts with 0
		assertValidationFails(fileLines, 1);

		fileLines = new LinkedList<String>();
		fileLines.add(" 123 456"); // starts with a separator
		assertValidationFails(fileLines, 1);

		fileLines = new LinkedList<String>();
		fileLines.add("	123	456"); // starts with a separator
		assertValidationFails(fileLines, 1);

		fileLines = new LinkedList<String>();
		fileLines.add("0123a");    // starts with a separator and has a letter
		assertValidationFails(fileLines, 2);

		fileLines = new LinkedList<String>();
		fileLines.add("123-6666"); // has an invalid character
		assertValidationFails(fileLines, 1);
	}

	@Test
	public void testInvalidFileFailsValidation() {
		List<String> fileLines = new LinkedList<String>();
		fileLines.add("1234 456-0000 6677	"); // has an invalid character
		fileLines.add("012	4056 44");          // starts with 0
		fileLines.add(" 123");                  // starts with a separator
		fileLines.add("1235	4956 7a89	");     // has a letter
		assertValidationFails(fileLines, 4);

		fileLines = new LinkedList<String>();
		fileLines.add("112312321525 12312312 5234234234 231321321312 123123 2131353453435345345 ");;
		fileLines.add("0213123523520234234 2342340005320 3240230042340230803240 "
				+ "3240909 ");                  // starts with 0
		fileLines.add("98978$23423975239 ");    // has an invalid character
		assertValidationFails(fileLines, 2);
	}

	private void assertSuccessfulValidation(List<String> fileLines) {
		CommandExecStatus validationStatus = fileValidator.validateFileContents(fileLines);
		assertTrue(validationStatus.isSuccessful());
		assertEquals(validationStatus.getDetailedInformation(), FileValidator.NO_VALIDATION_ERRORS_DETECTED);
	}

	private void assertValidationFails(List<String> fileLines, int expectedNumberOfValidationErrors) {
		CommandExecStatus validationStatus = fileValidator.validateFileContents(fileLines);
		assertFalse(validationStatus.isSuccessful());
		StringTokenizer strTokenizer = new StringTokenizer(validationStatus.getDetailedInformation(), "\n", false);
		assertEquals(expectedNumberOfValidationErrors, strTokenizer.countTokens());
		while(strTokenizer.hasMoreTokens()) {
			assertTrue(strTokenizer.nextToken().startsWith(
					FileValidator.LINE_HAS_AN_INVALID_CHARACTER_AT_POSITION.substring(0, 5)));
		}
	}
}
