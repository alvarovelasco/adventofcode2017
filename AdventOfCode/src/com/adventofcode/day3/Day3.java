package com.adventofcode.day3;


import java.util.*;

import util.Direction;
import util.Node;

public class Day3 {


    private static Map<Node, Integer> points = new HashMap<>();

    private static int sumOfNeighbors(int x, int y) {
        int sum = 0;
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (i != 0 || j != 0) {
                    Node n = new Node(x + j, y + i);
                    if (points.containsKey(n)) {
                        sum += points.get(n);
                    }
                }
            }

        }
        return sum;
    }


    public static void main(String[] args) {
        int goal = 277678;
        int x = 0;
        int y = 0;
        Direction current = Direction.EAST;
        points.put(new Node(x, y), 1);
        int num = 1;
        int mov = 1;
        int movCount = 0;
        boolean firstHighest = false;
        while (num <= goal) {
            while (movCount < 2) {
                for (int i = 0; i < mov; i++) {
                    if (num++ == goal) break;
                    x += current.getDx();
                    y += current.getDy();
                    //num++;
                    int sum = sumOfNeighbors(x, y);
                    if (sum > goal) {
                        if (!firstHighest) {
                            System.out.println("Part 2: " + sum);
                        }
                        firstHighest = true;
                    }
                    points.put(new Node(x, y), sum);
                    if (num >= goal) {
                        break;
                    }

                }
                current = current.getLeft();
                movCount++;
            }
            movCount = 0;
            mov++;
        }
        System.out.println("Part 1: " + Math.abs(x + y));
    }
}