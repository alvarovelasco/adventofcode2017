package com.adventofcode.day5;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day5 {

	private static final boolean FLAG_SECOND_PART = true;

	public static void main(String[] args) throws IOException {
		String content = new String(Files.readAllBytes(Paths.get("res/day5.txt")));
		List<String> lines = Arrays.asList(content.split("\\r?\\n"));

		List<Offset> listJump = lines.stream().map(s -> new Offset(Integer.valueOf(s))).collect(Collectors.toList());
		int numberOfJumps = getJumpsMade(listJump);
		System.out.println("Jumps " + numberOfJumps);
	}

	private static int getJumpsMade(List<Offset> offsetList) {
		int position = 0;
		int jumps = 0;

		while (position >= 0 && position < offsetList.size()) {
			Offset currentOffset = offsetList.get(position);
			position = currentOffset.move(position);
			jumps++;
		}

		return jumps;
	}

	// after each jump, if the offset was three or more, instead decrease it by
	// 1. Otherwise, increase it by 1 as before.

	private static class Offset {
		private int offset;

		public Offset(int offset) {
			this.offset = offset;
		}

		public int move(int currentPosition) {
			int newPosition = currentPosition + offset;
			if (FLAG_SECOND_PART && offset >= 3) {
				offset--;
			} else {
				offset++;
			}

			return newPosition;
		}
	}

}
