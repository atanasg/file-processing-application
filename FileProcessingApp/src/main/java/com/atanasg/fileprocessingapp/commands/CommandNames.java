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
package com.atanasg.fileprocessingapp.commands;

/**
 * The names of the supported commands in
 * this application. These are the recognized
 * strings when the user enters some command.
 *
 * @author Atanas Gegov
 */
public class CommandNames {

	// swap commands
	public static final String COMMAND_SWAPLINES = "swaplines";
	public static final String COMMAND_SWAPNUMS = "swapnums";

	// CRUD commands
	public static final String COMMAND_INSERTNUM = "insertnum";
	public static final String COMMAND_MODIFYNUM = "modifynum";
	public static final String COMMAND_READNUM = "readnum";
	public static final String COMMAND_REMOVENUM = "removenum";

	// validate command
	public static final String COMMAND_VALIDATE = "validate";

	// print command
	public static final String COMMAND_PRINT = "print";

	// save command
	public static final String COMMAND_SAVE = "save";

	// other commands
	public static final String COMMAND_HELP = "help";
	public static final String COMMAND_QUIT = "quit";

}
