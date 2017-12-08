package com.adventofcode.day1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Day1 {

	public static void main(String[] args) throws IOException {
		String content = new String(Files.readAllBytes(Paths.get("res/day1.txt")));
		List<Integer> values = content.chars().sequential().mapToObj(c -> Character.getNumericValue(c))
				.collect(Collectors.toList());
		System.err.println(values);
		values = getFilteredElementListHalfWay(values);
		System.err.println(values);
		int result = values.stream().mapToInt(Integer::intValue).sum();
		System.err.println(result);
	}

	private static List<Integer> getFilteredElementList(List<Integer> rawList) {
		List<Integer> result = new ArrayList<>();
		for (int count = 0; count < rawList.size(); count++) {
			int nextIndex = (count == rawList.size() - 1) ? 0 : count + 1;
			int element = rawList.get(count);

			if (element == rawList.get(nextIndex)) {
				result.add(element);
			}
		}

		return result;
	}

	private static List<Integer> getFilteredElementListHalfWay(List<Integer> rawList) {
		int halfWayOffset = rawList.size() / 2;
		List<Integer> result = new ArrayList<>();
		for (int count = 0; count < rawList.size(); count++) {
			int nextIndex = count + halfWayOffset;
			nextIndex = nextIndex >= rawList.size() ? (nextIndex - rawList.size()) : nextIndex;
			int element = rawList.get(count);

			if (element == rawList.get(nextIndex)) {
				result.add(element);
			}
		}

		return result;
	}
}
