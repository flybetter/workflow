package com.calix.bseries.server.ana.process.command.po;

import java.util.ArrayList;
import java.util.List;

import com.calix.bseries.server.ana.common.CommonStringUtils;

public class CommandDetailsPO {
	private String name;
	private List<String> commandList;

	public CommandDetailsPO() {

	}

	public CommandDetailsPO(String name) {
		this.name = name;
	}

	public CommandDetailsPO(String name, String[] str) {
		this.name = name;
		if (CommonStringUtils.isEmpty(commandList)) {
			commandList = new ArrayList<String>();
		}
		for (String s : str) {
			commandList.add(s);
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getCommandList() {
		return commandList;
	}

	public void setCommandList(List<String> commandList) {
		this.commandList = commandList;
	}

	@Override
	public String toString() {
		return name+":"+commandList;
	}
	
	
}
