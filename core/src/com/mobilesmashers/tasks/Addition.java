package com.mobilesmashers.tasks;

import com.mobilesmashers.utils.Constants;
import com.mobilesmashers.utils.RandomUtils;

import org.jetbrains.annotations.Contract;

public class Addition extends Task {

	private static Integer targetValue;
	private static int counter;

	static {
		resetTargetValue();
	}

	@Contract(pure = true)
	public static int getTargetValue() {
		return targetValue;
	}

	public static int resetTargetValue() {
		targetValue = RandomUtils.nextInt(Constants.TASK_ADDITI_MIN, Constants.TASK_ADDITI_MAX);
		counter = 0;
		return targetValue;
	}

	public static Task[] createTask(int n) {
		if (n < 1)
			throw new RuntimeException("Parameter n must be > 1.");

		Task[] result = new Task[n];
		int lastButONe = n - 1;
		result[0] = new Addition();

		for (int i = 1; i < lastButONe; ++i)
			result[i] = new Addition();

		result[lastButONe] = new Addition(targetValue - counter);

		counter = 0;

		return result;
	}

	private Integer value;

	private Addition() {
		this(RandomUtils.nextInt(targetValue - counter));
	}

	private Addition(int value) {
		this.value = value;
		counter += value;
	}

	public String getValue() {
		return value.toString();
	}

	public match isMatch(Task task) {
		if (task.getClass() == Addition.class) {
			if (value > targetValue)
				return match.BAD_MATCH;
			else if (value.equals(targetValue))
				return match.GOOD_MATCH;
		}
		return match.NO_MATCH;
	}
}
