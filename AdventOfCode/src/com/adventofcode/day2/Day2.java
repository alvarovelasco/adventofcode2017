package com.adventofcode.day2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
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
		checksum = getSumGreatesLowest(list);
		System.out.println("Checksum " + checksum);
		checksum = getSumEvenDivisible(list);		
		System.out.println("Checksum evenly division " + checksum);
	}

	private static int getSumGreatesLowest(List<String> list) {
		int checksum = 0;
		for (String line : list) {
			List<String> isolatedLine = Arrays.asList(line.split("\\t"));
			checksum += (getGreatest(isolatedLine.stream()) - getLowest(isolatedLine.stream()));
		}
		return checksum;
	}
	
	private static int getSumEvenDivisible(List<String> list) {
		int checksum = 0;
		for (String line : list) {
			List<Integer> isolatedLine = Arrays.asList(line.split("\\t")).stream().
					mapToInt(s -> Integer.valueOf(s)).boxed().collect(Collectors.toList());
			
			for (int count = 0; count < isolatedLine.size(); count++) {
				int a = isolatedLine.get(count);
				Optional<Integer> resolvedDivision = Optional.empty();
				for (int count2 = count + 1;count2<isolatedLine.size();count2++) {
					int b = isolatedLine.get(count2);
					if ( (Math.max(a, b) % Math.min(a, b)) == 0) {
						resolvedDivision =
								Optional.of((Math.max(a, b) / Math.min(a, b)));
						break;
					}
				}
				
				if (resolvedDivision.isPresent()) {
					checksum += resolvedDivision.get();
				}
			}
		}
		return checksum;
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
