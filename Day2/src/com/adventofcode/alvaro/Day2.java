package com.adventofcode.alvaro;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.OptionalInt;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day2 {

	public static void main(String[] args) {
		String fileName = "res/source1.txt";
		List<String> list = null;
		int checksum = 0;
		//read file into stream, try-with-resources
		try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
			list = stream
					.collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (String line : list) {
			List<String> isolatedLine = Arrays.asList(line.split("\\t"));
			checksum += (getGreatest(isolatedLine.stream()) - getLowest(isolatedLine.stream()));
		}		
		System.out.println("Checksum " + checksum);
	}
	
	private static int getGreatest(Stream<String> stream) {
		IntStream lineStream = stream.mapToInt(s -> Integer.valueOf(s));
		OptionalInt max = lineStream.max();
		
		return max.getAsInt();
	}
	
	private static int getLowest(Stream<String> stream) {
		IntStream lineStream = stream.mapToInt(s -> Integer.valueOf(s));
		return lineStream.min().getAsInt();
	}
	
	
}
