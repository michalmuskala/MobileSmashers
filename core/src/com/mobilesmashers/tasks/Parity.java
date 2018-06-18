package com.mobilesmashers.tasks;

import com.mobilesmashers.utils.Constants;
import com.mobilesmashers.utils.RandomUtils;

public class Parity extends Task {

	public static Task[] createTask() {

		Parity[] result = new Parity[2];

		result[0] = new Parity();
		result[1] = new Parity();

		if (result[1].isMatch(result[0]) == match.BAD_MATCH)
			result[1].value += 1;

		return result;
	}

	private Integer value;

	private Parity() {
		value = RandomUtils.nextInt(Constants.TASK_PARITY_MAX);
	}

	public String getValue() {
		return value.toString();
	}

	public match isMatch(Task task) {
		if (task.getClass() == Parity.class)
			if (value % 2 == ((Parity) task).value % 2)
				return match.GOOD_MATCH;

		return match.BAD_MATCH;
	}
}
