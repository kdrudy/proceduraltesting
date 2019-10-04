/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.procedural;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author kylerudy
 */
public class Main {

    public static void main(String[] args) {
//        Dungeon d = new Dungeon(15, 30, 100);
//        d.generate();
//        d.printDungeon();

        Main m = new Main();
//        for (int i = 0; i < 6; i++) {
//            Integer[] map = m.generateSineNoise(5);
//            m.printNoiseMap(m.adjacentMin(map), 5);
////            m.printMap(m.generate());
//        }

//        DungeonCA dca = new DungeonCA(20, 80);
//        dca.init(0.44);
//        dca.printDungeon();
//        System.out.println("One Refinement");
//        dca.refine();        
//        dca.printDungeon();
//        System.out.println("Two Refinement");
//        dca.refine();        
//        dca.printDungeon();
//        System.out.println("Three Refinement");
//        dca.refine();        
//        dca.printDungeon();
//        System.out.println("Four Refinement");
//        dca.refine();        
//        dca.printDungeon();
//        System.out.println("Five Refinement");
//        dca.refine();        
//        dca.printDungeon();


        DungeonTile[][] newMap = new DungeonTile[20][80];
        Random r = new Random();
        OpenSimplexNoise osn = new OpenSimplexNoise(r.nextLong());
        int octaves = 6;
        double persistance = 0.75;
        double lacurnity = 0.40;
        for(int x = 0;x<newMap.length;x++) {
            for(int y = 0;y<newMap[x].length;y++) {
                double total = 0;
                double frequency = 1;
                double amplitude = 1;
                double totalAmplitude = 0;
                for(int i = 0;i<octaves;i++) {
                    total += osn.eval(x * frequency, y * frequency) * amplitude;
                    totalAmplitude += amplitude;
                    amplitude *= persistance;
                    frequency *= lacurnity;
                }
                double value = total/totalAmplitude;
//                System.out.println(value);
                if(value > 0) {
                    newMap[x][y] = DungeonTile.OPEN;
                } else {
                    newMap[x][y] = DungeonTile.WALL;
                }
            }
        }
        DungeonCA dca = new DungeonCA(newMap);
        dca.printDungeon();
        System.out.println("One Refinement");
        dca.refine();        
        dca.printDungeon();
    }

    private void printMap(int[] map) {
        StringBuilder sb = new StringBuilder();
        for (int i : map) {
            if (i == 1) {
                sb.append("#");
            } else {
                sb.append(".");
            }
        }
        System.out.println(sb.toString());
    }

    private int[] generate() {
        int[] map = new int[40];
        int limit = (int) (Math.random() * 40);
        int pos = (int) (Math.random() * limit);
        map[pos] = 1;
        return map;
    }

    private Integer[] generateNoise(int max) {
        Integer[] map = new Integer[40];
        for (int i = 0; i < map.length; i++) {
            int limit = (int) (Math.random() * max);
            map[i] = (int) (Math.random() * limit);
        }

        return map;
    }

    private Integer[] adjacentMin(Integer[] map) {
        List<Integer> newMap = new ArrayList<>();
        for (int i = 0; i < map.length - 1; i++) {
            newMap.add(Math.min(map[i], map[i + 1]));
        }
        Integer[] finalMap = new Integer[newMap.size()];
        return newMap.toArray(finalMap);
    }

    private void printNoiseMap(int[] map, int max) {
        StringBuilder sb = new StringBuilder();
        for (int i = max; i >= 0; i--) {
            for (int j = 0; j < map.length; j++) {
                if (map[j] >= i) {
                    sb.append("X");
                } else {
                    sb.append(" ");
                }
            }
            sb.append("\n");
        }
        System.out.println(sb.toString());
    }

    private void printNoiseMap(Integer[] map, int max) {
        StringBuilder sb = new StringBuilder();
        for (int i = max; i >= 0; i--) {
            for (int j = 0; j < map.length; j++) {
                if (map[j] >= i) {
                    sb.append("X");
                } else {
                    sb.append(" ");
                }
            }
            sb.append("\n");
        }
        System.out.println(sb.toString());
    }

    private Integer[] generateSineNoise(int max) {
        Integer[] map = new Integer[40];
        for (int i = 0; i < map.length; i++) {
            map[i] = (int) (((Math.sin(i * Math.random() + Math.cos(i)) + 1) / 2) * max);
        }
        return map;
    }

}
