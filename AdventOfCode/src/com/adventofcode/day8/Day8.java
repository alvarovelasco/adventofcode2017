package com.adventofcode.day8;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.OptionalInt;
import java.util.stream.Collectors;

public class Day8 {
	private static int maxValue = 0;
	
	public static void main(String[] args) throws Exception {
		String content = new String(Files.readAllBytes(Paths.get("res/day8.txt")));
		partOne(content);
	}
	
	private static void partOne(String content) {
		List<String> lines = Arrays.asList(content.split("\\r?\\n"));
		List<Instruction> instructions = lines.stream().map(line -> {
			// Parse all instructions
			String[] parts = line.split("if");
			String[] conditionParts = parts[1].trim().split(" ");
			Condition condition = new Condition(conditionParts[0], 
					Integer.parseInt(conditionParts[2].trim()), conditionParts[1]);
			String pattern = "inc";
			if (parts[0].contains("dec")) {
				pattern = "dec";
			} 
			String[] instruction = parts[0].split(pattern);
			int value = Integer.valueOf(instruction[1].trim());
			if (pattern.equals("dec"))
				value = -1 * value;
			return new Instruction(instruction[0].trim(), value, condition);
		}).collect(Collectors.toList());
		final Map<String, Integer> mapVariableValue = new HashMap<>();
		instructions.stream().forEach(i -> {
			// Evaluate each instruction and increase the value
			int value = 0;
			if (mapVariableValue.containsKey(i.name))
				value = mapVariableValue.get(i.name);
			if (i.condition.test(mapVariableValue)) {
				value = value + i.valueToAdd;
				registerAsMaxValueIfSo(value);
				mapVariableValue.put(i.name, value);
			} 
		});
		OptionalInt max = mapVariableValue.values().stream().mapToInt(i -> i).max();
		System.out.println("MAX VALUE>> " + max.getAsInt() + " MAX EVER: " + maxValue);
	}
	
	private static void registerAsMaxValueIfSo(int value) {
		if (value > maxValue) 
			maxValue = value;
	}
	
	private static class Instruction {
		final String name;
		
		final int valueToAdd;
		
		final Condition condition;
		
		public Instruction(String name, int valueToAdd, Condition condition) {
			this.name = name;
			this.valueToAdd = valueToAdd;
			this.condition = condition;
		}
	}
	
	private static class Condition {
		final String name;
		final int value;
		final String cond;
		
		public Condition(String name, int value, String cond) {
			this.name = name;
			this.value = value;
			this.cond = cond;
		}
		
		public boolean test(Map<String, Integer> mapValues) {
			int currentValue = 0;
			if (mapValues.containsKey(name))
				currentValue = mapValues.get(name);
			
			if (">".equals(cond)) {
				return currentValue > value ;
			} else if ("<".equals(cond)) {
				return currentValue < value ;
			} else if (">=".equals(cond)) {
				return currentValue >= value ;
			} else if ("<=".equals(cond)) {
				return currentValue <= value ;
			} else if ("==".equals(cond)) {
				return currentValue == value;
			} else if ("!=".equals(cond)) {
				return currentValue != value;
			}
			
			throw new IllegalArgumentException("Condition not matched: " + cond);
		}
		
	}
}
