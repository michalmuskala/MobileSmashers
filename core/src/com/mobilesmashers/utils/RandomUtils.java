package com.mobilesmashers.utils;

import java.util.Random;

public final class RandomUtils {

	private static Random random = new Random();

	/**
	 * @return - random boolean
	 */
	public static boolean nextBoolean() {
		return random.nextBoolean();
	}

	/**
	 * @param n - parameter
	 * @return - random integer in [0, n-1]
	 */
	public static int nextInt(int n) {
		return random.nextInt(n);
	}

	/**
	 *
	 * @param min - minimum number
	 * @param max - maximum number minus pne
	 * @return - random integer in [min, max-1]
	 */
	public static int nextInt(int min, int max) {
		return random.nextInt((max - min) + 1) + min;
	}

	/**
	 * @param n - parameter
	 * @return - random float in [0, n)
	 */
	public static float nextFloat(float n) {
		return n * random.nextFloat();
	}

	/**
	 * @param m - parameter
	 * @param n - parameter
	 * @return - random float in [m, n)
	 */
	public static float nextFloat(float m, float n) {
		return (n - m) * random.nextFloat() + m;
	}

	/**
	 * Set the seed for random
	 * @param seed - seed
	 */
	public static void reset(long seed) {
		random.setSeed(seed);
	}

	private RandomUtils() {
	}
}
