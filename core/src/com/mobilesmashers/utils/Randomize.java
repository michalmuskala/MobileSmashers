package com.mobilesmashers.utils;

import java.util.Random;

public class Randomize {

	private static Random random = new Random();

	/**
	 * @param n - parameter
	 * @return - random integer in [0, n-1]
	 */
	public static int nextInt(int n) {
		return random.nextInt(n);
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
	 * Resets randomize class.
	 */
	public static void reset() {
		random = new Random();
	}

	private Randomize() {}
}