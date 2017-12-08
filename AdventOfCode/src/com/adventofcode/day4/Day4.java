package com.adventofcode.day4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day4 {
	public static void main(String[] args) {
		String fileName = "res/source4.txt";
		List<String> list = null;
		//read file into stream, try-with-resources
		try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
			list = stream
					.collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		List<String> passwords = 
				list.stream().filter(phrase -> isPassphraseValid(phrase)).collect(Collectors.toList());
		System.out.println("Number of valid phrases " + passwords.size());
		passwords=passwords.stream().filter(p -> isNonAnagram(p)).collect(Collectors.toList());
		System.out.println("Number of valid phrases " + passwords.size());
		
	}
	
	public static boolean isPassphraseValid(String passPhrase) {
		List<String> words = Arrays.asList(passPhrase.split(" "));
		
		Set<String> resultSet = 
				words.stream().collect(Collectors.toSet());
		
		return words.size() == resultSet.size();
	}
	
	public static boolean isNonAnagram(String passPhrase) {
		List<String> words = Arrays.asList(passPhrase.split(" "));
		words = words.stream().map(w -> {
			char[] caracters = w.toCharArray();
			Arrays.sort(caracters);
			return new String(caracters);
		}).collect(Collectors.toList());
		return isPassphraseValid(String.join(" ", words));
	}
}
