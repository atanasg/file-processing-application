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

import java.math.BigInteger;

import com.atanasg.fileprocessingapp.command.status.CommandExecStatus;
import com.atanasg.fileprocessingapp.command.status.CommandFailed;
import com.atanasg.fileprocessingapp.command.status.CommandSuccessful;
import com.atanasg.fileprocessingapp.mvc.model.FileContentModel;
import com.atanasg.fileprocessingapp.mvc.view.FileProcessingAppView;

/**
 * An extension of the {@link AbstractCommand}
 * with the support of command parameters
 * that influence the execution.
 *
 * @author Atanas Gegov
 */
public abstract class AbstractCommandWithArgs extends AbstractCommand {

	protected final String[] commandArgs;

	protected AbstractCommandWithArgs(FileContentModel fileContentModel,
			FileProcessingAppView userInterface, String[] commandArgs) {
		super(fileContentModel, userInterface);
		this.commandArgs = commandArgs;
	}

	protected CommandExecStatus checkNumberAndTypeOfArguments(int numOfExpectedArgs,
			boolean[] expectedArgIsInt) {
		CommandExecStatus argsCheckStatus;

		if(commandArgs.length != numOfExpectedArgs) {
			argsCheckStatus = new CommandFailed();
			argsCheckStatus.appendDetailedInfo("Wrong number of arguments provided");
			return argsCheckStatus;
		}

		for(int i = 0; i < commandArgs.length; i++) {
			if(expectedArgIsInt[i]) {
				try {
					new BigInteger(commandArgs[i]);
				} catch(NumberFormatException e) {
					argsCheckStatus = new CommandFailed();
					argsCheckStatus.appendDetailedInfo(
							String.format("%s is not an integer value", commandArgs[i]));
					return argsCheckStatus;
				}
			}
		}
		argsCheckStatus = new CommandSuccessful();
		return argsCheckStatus;
	}

}
