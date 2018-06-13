package com.mobilesmashers.tasks;

import com.mobilesmashers.utils.Constants;
import com.mobilesmashers.utils.RandomUtils;

public class Parity extends Task {

	private Integer value;

	private Parity() {
		value = RandomUtils.nextInt(Constants.TASK_PARITY_MAX);
	}

	public static Task[] createTask(int n) {
		if (n < 2)
			throw new RuntimeException("Task number to small (must be > 2).");

		Parity[] result = new Parity[n];

		result[0] = new Parity();

		for (int i = 1; i < result.length; ++i) {
			result[i] = new Parity();
			if (!result[i].match(result[0]))
				result[i].value += 1;
		}

		return result;
	}

	public String getValue() {
		return value.toString();
	}

	public boolean match(Task task) {
		return task.getClass() == Parity.class &&
				value % 2 == ((Parity) task).value % 2;
	}
}
