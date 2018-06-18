package com.mobilesmashers.tasks;

public abstract class Task {

	public abstract String getValue();

	public abstract match isMatch(Task task);

	public String toString() {
		return String.format(
				"$1%s($2%s)",
				this.getClass().toString(),
				getValue()
		);
	}

	public enum match {
		BAD_MATCH,
		GOOD_MATCH,
		NO_MATCH;
	}
}
