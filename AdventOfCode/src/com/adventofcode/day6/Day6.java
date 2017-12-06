package com.adventofcode.day6;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Day6 {

	public static void main(String[] args) {
		List<Integer> banks = Arrays.asList(14, 0, 15, 12, 11, 11, 3, 5, 1, 6, 8, 4, 9, 1, 8, 4);

		List<List<Integer>> history = new ArrayList<>();

		while (!history.contains(banks)) {
			history.add(banks);
			banks = rellocate(banks);
		}
		int previous = history.indexOf(banks);
		System.out.println(" Difference " + history.size() + " previous: " + previous + " " + history.get(previous));

	}

	private static List<Integer> rellocate(List<Integer> banks) {
		Optional<Integer> maxValue = banks.stream().max(Integer::compareTo);

		if (!maxValue.isPresent())
			return null;

		int index = banks.indexOf(maxValue.get());

		final int setValue = maxValue.get() / banks.size();
		int remaining = maxValue.get() % banks.size();

		List<Integer> newBanks = new ArrayList<>(banks);
		newBanks.set(index, 0);

		int startIndex = index;
		for (; remaining > 0; remaining--) {
			startIndex = getNextIndex(startIndex + 1, banks.size());
			newBanks.set(startIndex, newBanks.get(startIndex) + 1);
		}

		return newBanks.stream().map(v -> v + setValue).collect(Collectors.toList());
	}

	private static int getNextIndex(int currentIndex, int max) {
		return currentIndex >= max ? currentIndex % max : currentIndex;
	}

}
