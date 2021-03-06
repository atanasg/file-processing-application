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
package com.atanasg.fileprocessingapp.command.status;

/**
 * {@link CommandSuccessful} objects are the return
 * values for successful commands. The method
 * getDetailedInformation() from
 * the base class returns data (e.g. a value if
 * the command reads a value) or other additional
 * information.
 *
 * @author Atanas Gegov
 */
public final class CommandSuccessful extends CommandExecStatus {

	public CommandSuccessful() {
		super("Command SUCCESSFUL", true);
	}

}
