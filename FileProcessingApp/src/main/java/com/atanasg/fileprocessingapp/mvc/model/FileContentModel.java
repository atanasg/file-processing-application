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

import java.math.BigInteger;
import java.util.List;

import com.atanasg.fileprocessingapp.command.status.CommandExecStatus;
import com.atanasg.fileprocessingapp.mvc.controller.FileProcessingAppController;

/**
 * This application uses the Model-View-Controller pattern.
 *
 * This interface defines the methods supported by the
 * Model component. A Controller ({@link FileProcessingAppController})
 * is supposed to call the methods of the Model.
 *
 * @author Atanas Gegov
 */
public interface FileContentModel {

	public void parseFileContent(List<String> fileLines);

	public List<String> getFileContentAsFileLines();

	public int getNumberOfLines();

	public CommandExecStatus insertNumber(int lineIndex, int lineNumberIndex, BigInteger numberToBeInserted);

	public CommandExecStatus modifyNumber(int lineIndex, int lineNumberIndex, BigInteger numberToBeSet);

	public CommandExecStatus readNumber(int lineIndex, int lineNumberIndex);

	public CommandExecStatus removeNumber(int lineIndex, int lineNumberIndex);

	public CommandExecStatus swapLines(int firstLineIndex, int secondLineIndex);

	public CommandExecStatus swapNumbers(int firstLineIndex, int firstLineNumberIndex,
			int secondLineIndex, int secondLineNumberIndex);

}
