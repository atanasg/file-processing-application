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
import static org.junit.Assert.assertFalse;

import java.util.LinkedList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.atanasg.fileprocessingapp.command.status.CommandExecStatus;

/**
 * Test the range checks performed on the
 * parameters of the different operations
 * manipulating the file content.
 *
 * @author Atanas Gegov
 */
public class FileContentRangeTest {

	private static FileContentModel fileContentModel;
	private static List<String> inputLines;
	private static int[] numOfElementsInLine;

	@BeforeClass
	public static void setUpTest() {
		fileContentModel = new FileContentModelImpl();
		inputLines = new LinkedList<String>();
		inputLines.add("100 101 102 103 104");
		inputLines.add("200 201 202 203");
		inputLines.add("300 301 302");
		inputLines.add("400");
		numOfElementsInLine = new int[]{5, 4, 3, 1};
		fileContentModel.parseFileContent(inputLines);
	}

	@Test
	public void testReadNumCommandRange() {
		CommandExecStatus status;

		status = fileContentModel.readNumber(-1, 0);
		assertFalse(status.isSuccessful());
		assertEquals(String.format("Index for line 0 out of range [1,%d]", inputLines.size()),
				status.getDetailedInformation());

		status = fileContentModel.readNumber(4, 0);
		assertFalse(status.isSuccessful());
		assertEquals(String.format("Index for line 5 out of range [1,%d]", inputLines.size()),
				status.getDetailedInformation());

		status = fileContentModel.readNumber(0, 5);
		assertFalse(status.isSuccessful());
		assertEquals(String.format("Number index in line 1 out of available range [1,%d]",
				numOfElementsInLine[0]), status.getDetailedInformation());

		status = fileContentModel.readNumber(2, -1);
		assertFalse(status.isSuccessful());
		assertEquals(String.format("Number index in line 3 out of available range [1,%d]",
				numOfElementsInLine[2]), status.getDetailedInformation());

		assertFileContentWasNotModified();
	}

	@Test
	public void testSwapLinesCommandRange() {
		CommandExecStatus status;

		status = fileContentModel.swapLines(-1, 0);
		assertFalse(status.isSuccessful());
		assertEquals(String.format("Index for line 0 out of range [1,%d]", inputLines.size()),
				status.getDetailedInformation());

		status = fileContentModel.swapLines(0, 4);
		assertFalse(status.isSuccessful());
		assertEquals(String.format("Index for line 5 out of range [1,%d]", inputLines.size()),
				status.getDetailedInformation());

		status = fileContentModel.swapLines(7, 10);
		assertFalse(status.isSuccessful());
		assertEquals(String.format("Index for line 8 out of range [1,%d]", inputLines.size()),
				status.getDetailedInformation());

		assertFileContentWasNotModified();
	}

	//TODO test the other functions like swapnum, insert, modify etc.

	private void assertFileContentWasNotModified() {
		List<String> outputLines = fileContentModel.getFileContentAsFileLines();

		assertEquals(inputLines.size(), outputLines.size());

		for (int i = 0; i < inputLines.size(); i++) {
			String expectedLine = inputLines.get(i);
			String outputLine = outputLines.get(i);
			assertEquals(expectedLine, outputLine);
		}
	}
}
