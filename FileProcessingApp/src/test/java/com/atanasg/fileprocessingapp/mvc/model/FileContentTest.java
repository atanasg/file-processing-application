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
package com.atanasg.fileprocessingapp.mvc.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.atanasg.fileprocessingapp.command.status.CommandExecStatus;

/**
 * Unit test for the manipulation operations on file content.
 *
 * @author Atanas Gegov
 */
public class FileContentTest {

	private static FileContentModel fileContentModel;

	@BeforeClass
	public static void setUpTest() {
		fileContentModel = new FileContentModelImpl();
	}

	@Test
	public void testSequenceOfOperations() {
		List<String> inputLines = new LinkedList<String>();
		inputLines.add("100 101 102 103 104");
		inputLines.add("200 201 202 203");
		inputLines.add("300 301 302");
		inputLines.add("400");
		fileContentModel.parseFileContent(inputLines);

		CommandExecStatus status;

		status = fileContentModel.swapLines(0, 2);
		assertTrue(status.isSuccessful());

		status = fileContentModel.swapNumbers(0, 0, 1, 3);
		assertTrue(status.isSuccessful());

		status = fileContentModel.readNumber(2, 2);
		assertTrue(status.isSuccessful());
		assertTrue(status.getDetailedInformation().equals("102"));

		int numLines = fileContentModel.getNumberOfLines();
		assertTrue(numLines == 4);

		status = fileContentModel.swapNumbers(2, 0, 2, 1);
		assertTrue(status.isSuccessful());

		status = fileContentModel.modifyNumber(3, 0, new BigInteger("444"));
		assertTrue(status.isSuccessful());

		status = fileContentModel.removeNumber(0, 1);
		assertTrue(status.isSuccessful());

		status = fileContentModel.insertNumber(-1, 0, new BigInteger("999"));
		assertTrue(status.isSuccessful());

		status = fileContentModel.insertNumber(5, 0, new BigInteger("501"));
		assertTrue(status.isSuccessful());

		status = fileContentModel.insertNumber(5, 0, new BigInteger("500"));
		assertTrue(status.isSuccessful());

		numLines = fileContentModel.getNumberOfLines();
		assertTrue(numLines == 6);

		status = fileContentModel.swapLines(1, 4);
		assertTrue(status.isSuccessful());

		List<String> expectedOutputLines = new LinkedList<String>();
		expectedOutputLines.add("999");
		expectedOutputLines.add("444");
		expectedOutputLines.add("200 201 202 300");
		expectedOutputLines.add("101 100 102 103 104");
		expectedOutputLines.add("203 302");
		expectedOutputLines.add("500 501");

		List<String> outputLines = fileContentModel.getFileContentAsFileLines();
		assertFileModificationsCorrectlyPerformed(expectedOutputLines, outputLines);
	}

	@Test
	public void testRemoveNumCommandOnLineWithOneNumberDeletesTheLine() {
		List<String> inputLines = new LinkedList<String>();
		inputLines.add("300 301 302");
		inputLines.add("400");
		fileContentModel.parseFileContent(inputLines);

		CommandExecStatus status = fileContentModel.removeNumber(1, 0);
		assertTrue(status.isSuccessful());

		int numLines = fileContentModel.getNumberOfLines();
		assertTrue(numLines == 1);

		List<String> expectedOutputLines = new LinkedList<String>();
		expectedOutputLines.add("300 301 302");
		List<String> outputLines = fileContentModel.getFileContentAsFileLines();
		assertFileModificationsCorrectlyPerformed(expectedOutputLines, outputLines);
	}

	private void assertFileModificationsCorrectlyPerformed(
			List<String> expectedOutputLines, List<String> outputLines) {

		// both should have the same number of lines
		assertEquals(expectedOutputLines.size(), outputLines.size());

		// each line should have the same content
		for(int i = 0; i < expectedOutputLines.size(); i++) {
			String expectedLine = expectedOutputLines.get(i);
			String outputLine = outputLines.get(i);
			assertEquals(expectedLine, outputLine);
		}

	}
}
