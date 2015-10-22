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
 * A base class for the execution status
 * of a performed command.
 *
 * @author Atanas Gegov
 */
public abstract class CommandExecStatus {

	private final boolean isSuccessful;
	private final String shortStatusMessage;
	private String detailedInformation;

	protected CommandExecStatus(String message, boolean isSuccessful) {
		this.isSuccessful = isSuccessful;
		this.shortStatusMessage = message;
		this.detailedInformation = "";
	}

	public void appendDetailedInfo(String detailedInfo) {
		if(detailedInformation.equals("")) {
			detailedInformation = detailedInfo;
		} else {
			detailedInformation += "\n" + detailedInfo;
		}
	}

	public boolean isSuccessful() {
		return isSuccessful;
	}

	public String getStatusMessage() {
		return shortStatusMessage;
	}

	public String getDetailedInformation() {
		return detailedInformation;
	}

}
