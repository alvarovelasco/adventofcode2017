package com.adventofcode.day7;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day7 {

	public static void main(String[] args) throws Exception {
		String content = new String(Files.readAllBytes(Paths.get("res/day7.txt")));
		System.out.println(partTwo(content));
	}

	public static String partOne(String input) {
		List<String> lines = Arrays.asList(input.split("\\r?\\n"));

		final List<Disc> discs = lines.stream().map(line -> new Disc(line)).collect(Collectors.toList());
		discs.stream().forEach(d -> d.addChildDiscs(discs));

		return getBaseDisc(discs).name;
	}

	public static Disc getBaseDisc(List<Disc> discs) {
		Disc disc = discs.get(0);

		while (disc.parent != null) {
			disc = disc.parent;
		}

		return disc;
	}

	public static String partTwo(String input) {
		List<String> lines = Arrays.asList(input.split("\\r?\\n"));

		final List<Disc> discs = lines.stream().map(line -> new Disc(line)).collect(Collectors.toList());
		discs.stream().forEach(d -> d.addChildDiscs(discs));

		Disc disc = getBaseDisc(discs);
		int targetWeight = 0;

		for (; !disc.isBalanced();) {
			UnbalancedChild uc = disc.getUnbalancedChild();
			disc = uc.disc;
			targetWeight = uc.targetWeight;
		}

		int weightDiff = targetWeight - disc.getTotalWeight();
		return String.valueOf((disc.weight + weightDiff));
	}

	private static class Disc {
		int weight;
		String name;
		List<String> childNames;
		List<Disc> childDiscs;
		Disc parent;

		public Disc(String input) {
			name = input.split("\\(")[0].trim();
			Matcher m = Pattern.compile("\\((.*?)\\)").matcher(input);
			if (m.find())
				weight = Integer.parseInt(m.group(1));
			childNames = new ArrayList<String>();
			if (input.contains(">")) {
				String childrenString = input.split(">")[1];
				String[] words = childrenString.split(",");

				for (int i = 0; i < words.length; i++) {
					childNames.add(words[i].trim());
				}
			}
		}

		public void addChildDiscs(List<Disc> discs) {
			childDiscs = childNames.stream()
					.map(name -> discs.stream().filter(d -> name.equals(d.name)).findFirst().get())
					.collect(Collectors.toList());
			childDiscs.stream().forEach(d -> d.parent = this);
		}

		public int getTotalWeight() {
			int childSum = childDiscs.stream().mapToInt(disc -> disc.getTotalWeight()).sum();
			return childSum + weight;
		}

		public boolean isBalanced() {
			int groups = childDiscs.stream().collect(Collectors.groupingBy(x -> x.getTotalWeight())).size();
			return groups == 1;
		}

		public UnbalancedChild getUnbalancedChild() {
			Map<Integer, List<Disc>> groups = childDiscs.stream()
					.collect(Collectors.groupingBy(x -> x.getTotalWeight()));
			int targetWeight = groups.keySet().stream().filter(w -> groups.get(w).size() > 1).findFirst().get();
			int unbalancedChildKey = groups.keySet().stream().filter(w -> groups.get(w).size() == 1).findFirst().get();
			Disc unbalancedChild = groups.get(unbalancedChildKey).get(0);

			return new UnbalancedChild(unbalancedChild, targetWeight);
		}

		@Override
		public String toString() {
			return "Disc [weight=" + weight + ", name=" + name + ", childNames=" + childNames + "]";
		}
	}

	private static class UnbalancedChild {
		private Disc disc;
		private int targetWeight;

		public UnbalancedChild(Disc disc, int targetWeight) {
			this.disc = disc;
			this.targetWeight = targetWeight;
		}
	}
}
