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

import static com.google.common.base.Preconditions.checkState;

import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import com.atanasg.fileprocessingapp.command.status.CommandExecStatus;
import com.atanasg.fileprocessingapp.command.status.CommandFailed;
import com.atanasg.fileprocessingapp.command.status.CommandSuccessful;
import com.atanasg.fileprocessingapp.rules.RuleSeparators;

/**
 * An implementation of the {@link FileContentModel}
 * interface. The Model holds an internal representation
 * of a file content. The methods are called from
 * the Controller and operate on this internal
 * representation.
 *
 * @author Atanas Gegov
 */
public class FileContentModelImpl implements FileContentModel {

	private static final String ERROR_THE_MODEL_HAS_NOT_PARSED_A_FILE_CONTENT_YET =
			"The Model has not parsed a file content yet";

	private List<List<String>> parsedFileContent;

	public FileContentModelImpl() {
		parsedFileContent = null;
	}

	@Override
	public void parseFileContent(List<String> fileLines) {
		parsedFileContent = new LinkedList<List<String>>();
		for (String line : fileLines) {
			List<String> parsedLine = parseLine(line);
			parsedFileContent.add(parsedLine);
		}

	}

	private List<String> parseLine(String fileLine) {
		List<String> lineList = new LinkedList<String>();

		String delimiterString = RuleSeparators.getAllSeparatorsAsString();
		StringTokenizer strTokenizer = new StringTokenizer(fileLine, delimiterString, true);
		while (strTokenizer.hasMoreTokens()) {
			lineList.add(strTokenizer.nextToken());
		}
		return lineList;
	}

	@Override
	public List<String> getFileContentAsFileLines() {
		checkState(this.parsedFileContent != null, ERROR_THE_MODEL_HAS_NOT_PARSED_A_FILE_CONTENT_YET);

		List<String> fileContent = new LinkedList<String>();
		for (List<String> lineTokens : parsedFileContent) {
			StringBuffer line = new StringBuffer();
			for (String token : lineTokens) {
				line.append(token);
			}
			fileContent.add(line.toString());
		}
		return fileContent;
	}

	@Override
	public int getNumberOfLines() {
		checkState(this.parsedFileContent != null, ERROR_THE_MODEL_HAS_NOT_PARSED_A_FILE_CONTENT_YET);

		return parsedFileContent.size();
	}

	private List<Integer> getPositionsOfIntegerTokensInLine(int lineIndex) {
		List<Integer> intPositions = new LinkedList<Integer>();

		List<String> lineTokens = parsedFileContent.get(lineIndex);
		for (int i = 0; i < lineTokens.size(); i++) {
			String token = lineTokens.get(i);
			boolean tokenIsSeparator = RuleSeparators.isValidSeparator(token);
			if (!tokenIsSeparator) {
				intPositions.add(i);
			}
		}
		return intPositions;
	}

	private CommandExecStatus isLineIndexValid(int lineIndex) {
		if (lineIndex < 0 || lineIndex >= parsedFileContent.size()) {
			CommandExecStatus invalidCheckStatus = new CommandFailed();
			invalidCheckStatus.appendDetailedInfo(String.format("Index for line %d out of range [1,%d]",
					(lineIndex + 1), getNumberOfLines()));
			return invalidCheckStatus;
		}
		return new CommandSuccessful();
	}

	private CommandExecStatus isLineNumberIndexValid(int lineIndex, int lineNumberIndex) {
		List<Integer> intPositionsInLine = getPositionsOfIntegerTokensInLine(lineIndex);
		int countOfNumbersInLine = intPositionsInLine.size();
		if (lineNumberIndex < 0 || lineNumberIndex >= countOfNumbersInLine) {
			CommandExecStatus invalidCheckStatus = new CommandFailed();
			invalidCheckStatus.appendDetailedInfo(String.format("Number index in line %d out of available range [1,%d]",
					(lineIndex + 1), countOfNumbersInLine));
			return invalidCheckStatus;
		}
		return new CommandSuccessful();
	}

	@Override
	public CommandExecStatus insertNumber(int lineIndex, int lineNumberIndex, BigInteger numberToBeInserted) {
		checkState(this.parsedFileContent != null, ERROR_THE_MODEL_HAS_NOT_PARSED_A_FILE_CONTENT_YET);

		CommandExecStatus insertStatus;

		if (lineIndex < -1 || lineIndex > parsedFileContent.size()) {
			insertStatus = new CommandFailed();
			insertStatus.appendDetailedInfo(String.format("Index for line %d out of range [1,%d]v{0,%d}",
					(lineIndex + 1), getNumberOfLines(), (getNumberOfLines() + 1)));
			return insertStatus;
		}

		// insert into a new line at the beginning or end of file
		if (lineIndex == -1 || lineIndex == parsedFileContent.size()) {
			List<String> newLine = new LinkedList<String>();
			newLine.add(numberToBeInserted.toString());
			if (lineIndex == -1) {
				parsedFileContent.add(0, newLine);
			} else {
				parsedFileContent.add(newLine);
			}

			insertStatus = new CommandSuccessful();
			if (lineIndex == -1) {
				insertStatus.appendDetailedInfo("Created new line at the beginning of the file");
			} else {
				insertStatus.appendDetailedInfo("Created new line at the end of the file");
			}
			insertStatus.appendDetailedInfo("The argument about number index was ignored in this new line");
			return insertStatus;
		}

		// insert into some of the already existing lines
		List<Integer> intPositionsInLine = getPositionsOfIntegerTokensInLine(lineIndex);
		if (lineNumberIndex < 0 || lineNumberIndex > intPositionsInLine.size()) {
			insertStatus = new CommandFailed();
			insertStatus.appendDetailedInfo(String.format("Index of number in line out of range [1,%d]v{%d}",
					intPositionsInLine.size(), (intPositionsInLine.size() + 1)));
			return insertStatus;
		}

		List<String> lineContent = parsedFileContent.get(lineIndex);
		if (lineNumberIndex == intPositionsInLine.size()) {
			lineContent.add(RuleSeparators.getDefaultValidSeparator());
			lineContent.add(numberToBeInserted.toString());
		} else {
			int tokenPositionInLine = intPositionsInLine.get(lineNumberIndex);
			lineContent.add(tokenPositionInLine, numberToBeInserted.toString());
			lineContent.add((tokenPositionInLine + 1), RuleSeparators.getDefaultValidSeparator());
		}
		insertStatus = new CommandSuccessful();
		insertStatus.appendDetailedInfo(String.format("Inserted %s to file", numberToBeInserted));
		return insertStatus;
	}

	@Override
	public CommandExecStatus modifyNumber(int lineIndex, int lineNumberIndex, BigInteger numberToBeSet) {
		checkState(this.parsedFileContent != null, ERROR_THE_MODEL_HAS_NOT_PARSED_A_FILE_CONTENT_YET);

		CommandExecStatus checkStatus = isLineIndexValid(lineIndex);
		if (!checkStatus.isSuccessful()) {
			return checkStatus;
		}

		checkStatus = isLineNumberIndexValid(lineIndex, lineNumberIndex);
		if (!checkStatus.isSuccessful()) {
			return checkStatus;
		}

		CommandExecStatus modifyStatus;
		List<String> lineContent = parsedFileContent.get(lineIndex);
		List<Integer> intPositionsInLine = getPositionsOfIntegerTokensInLine(lineIndex);
		int tokenPositionInLine = intPositionsInLine.get(lineNumberIndex);
		lineContent.set(tokenPositionInLine, numberToBeSet.toString());
		modifyStatus = new CommandSuccessful();
		modifyStatus.appendDetailedInfo(String.format("The number at position (%d,%d) is now %s",
				(lineIndex + 1), (lineNumberIndex + 1), numberToBeSet));
		return modifyStatus;
	}

	@Override
	public CommandExecStatus readNumber(int lineIndex, int lineNumberIndex) {
		checkState(this.parsedFileContent != null, ERROR_THE_MODEL_HAS_NOT_PARSED_A_FILE_CONTENT_YET);

		CommandExecStatus checkStatus = isLineIndexValid(lineIndex);
		if (!checkStatus.isSuccessful()) {
			return checkStatus;
		}

		checkStatus = isLineNumberIndexValid(lineIndex, lineNumberIndex);
		if (!checkStatus.isSuccessful()) {
			return checkStatus;
		}

		CommandExecStatus readStatus;
		List<String> lineContent = parsedFileContent.get(lineIndex);
		List<Integer> intPositionsInLine = getPositionsOfIntegerTokensInLine(lineIndex);
		int tokenPositionInLine = intPositionsInLine.get(lineNumberIndex);
		String intAsString = lineContent.get(tokenPositionInLine);
		readStatus = new CommandSuccessful();
		readStatus.appendDetailedInfo(intAsString);
		return readStatus;
	}

	@Override
	public CommandExecStatus removeNumber(int lineIndex, int lineNumberIndex) {
		checkState(this.parsedFileContent != null, ERROR_THE_MODEL_HAS_NOT_PARSED_A_FILE_CONTENT_YET);

		CommandExecStatus checkStatus = isLineIndexValid(lineIndex);
		if (!checkStatus.isSuccessful()) {
			return checkStatus;
		}

		checkStatus = isLineNumberIndexValid(lineIndex, lineNumberIndex);
		if (!checkStatus.isSuccessful()) {
			return checkStatus;
		}

		CommandExecStatus removeStatus;
		List<String> lineContent = parsedFileContent.get(lineIndex);
		List<Integer> intPositionsInLine = getPositionsOfIntegerTokensInLine(lineIndex);
		int tokenPositionInLine = intPositionsInLine.get(lineNumberIndex);
		String intAsString = lineContent.remove(tokenPositionInLine);
		if (tokenPositionInLine < lineContent.size() - 1) {
			lineContent.remove(tokenPositionInLine);
		}

		// remove line if empty now
		intPositionsInLine = getPositionsOfIntegerTokensInLine(lineIndex);
		if (intPositionsInLine.isEmpty()) {
			parsedFileContent.remove(lineIndex);
		}

		removeStatus = new CommandSuccessful();
		removeStatus.appendDetailedInfo(String.format("Removed number %s from line %d",
				intAsString, (lineIndex + 1)));
		return removeStatus;
	}

	@Override
	public CommandExecStatus swapLines(int firstLineIndex, int secondLineIndex) {
		checkState(this.parsedFileContent != null, ERROR_THE_MODEL_HAS_NOT_PARSED_A_FILE_CONTENT_YET);

		CommandExecStatus checkStatus = isLineIndexValid(firstLineIndex);
		if (!checkStatus.isSuccessful()) {
			return checkStatus;
		}

		checkStatus = isLineIndexValid(secondLineIndex);
		if (!checkStatus.isSuccessful()) {
			return checkStatus;
		}

		CommandExecStatus swappingLineStatus;
		List<String> firstLine = parsedFileContent.get(firstLineIndex);
		parsedFileContent.set(firstLineIndex, parsedFileContent.get(secondLineIndex));
		parsedFileContent.set(secondLineIndex, firstLine);
		swappingLineStatus = new CommandSuccessful();
		swappingLineStatus.appendDetailedInfo(String.format("Swapped lines %d and %d",
				(firstLineIndex + 1), (secondLineIndex + 1)));
		return swappingLineStatus;
	}

	@Override
	public CommandExecStatus swapNumbers(int firstLineIndex, int firstLineNumberIndex,
			int secondLineIndex, int secondLineNumberIndex) {
		checkState(this.parsedFileContent != null, ERROR_THE_MODEL_HAS_NOT_PARSED_A_FILE_CONTENT_YET);

		CommandExecStatus checkStatus = isLineIndexValid(firstLineIndex);
		if (!checkStatus.isSuccessful()) {
			return checkStatus;
		}

		checkStatus = isLineNumberIndexValid(firstLineIndex, firstLineNumberIndex);
		if (!checkStatus.isSuccessful()) {
			return checkStatus;
		}

		checkStatus = isLineIndexValid(secondLineIndex);
		if (!checkStatus.isSuccessful()) {
			return checkStatus;
		}

		checkStatus = isLineNumberIndexValid(secondLineIndex, secondLineNumberIndex);
		if (!checkStatus.isSuccessful()) {
			return checkStatus;
		}

		CommandExecStatus swappingNumbersStatus;
		List<String> firstLine = parsedFileContent.get(firstLineIndex);
		List<String> secondLine = parsedFileContent.get(secondLineIndex);
		List<Integer> intPositionsFirstLine = getPositionsOfIntegerTokensInLine(firstLineIndex);
		List<Integer> intPositionsSecondLine = getPositionsOfIntegerTokensInLine(secondLineIndex);
		int firstNumberPositionInLine = intPositionsFirstLine.get(firstLineNumberIndex);
		int secondNumberPositionInLine = intPositionsSecondLine.get(secondLineNumberIndex);
		String intAsString1 = firstLine.get(firstNumberPositionInLine);
		String intAsString2 = secondLine.get(secondNumberPositionInLine);
		firstLine.set(firstNumberPositionInLine, intAsString2);
		secondLine.set(secondNumberPositionInLine, intAsString1);
		swappingNumbersStatus = new CommandSuccessful();
		swappingNumbersStatus.appendDetailedInfo(String.format("Swapped %s and %s",
				intAsString1, intAsString2));
		return swappingNumbersStatus;
	}

}
