package com.mobilesmashers.tasks;

public abstract class Task {

	public abstract String getValue();

	public abstract boolean match(Task task);

	public String toString() {
		return String.format(
				"$1%s($2%s)",
				this.getClass().toString(),
				getValue()
		);
	}
}
