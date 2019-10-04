/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.procedural;

/**
 *
 * @author kylerudy
 *
 * Cellular Automata generated dungeon
 */
public class DungeonCA {

    private DungeonTile[][] dungeon;

    public DungeonCA(int rows, int columns) {
        dungeon = new DungeonTile[rows][columns];
    }

    public DungeonCA(DungeonTile[][] dungeon) {
        this.dungeon = dungeon;
    }
    

    public void init(double percentWall) {
        for (int x = 0; x < dungeon.length; x++) {
            for (int y = 0; y < dungeon[x].length; y++) {
                if (Math.random() <= percentWall) {
                    dungeon[x][y] = DungeonTile.WALL;
                } else {
                    dungeon[x][y] = DungeonTile.OPEN;
                }
            }
        }
    }
    
    public void refine() {
        for (int x = 0; x < dungeon.length; x++) {
            for (int y = 0; y < dungeon[x].length; y++) {
                int wallCount = 0;
                wallCount = getWalls(x, y);
                if(dungeon[x][y] == DungeonTile.WALL) {
                    wallCount++;
                }                
                if(wallCount >= 5 ) {
                    dungeon[x][y] = DungeonTile.WALL;
                } else {
                    dungeon[x][y] = DungeonTile.OPEN;
                }
            }
        }
    }
    
    public void refine(int lowWall) {
        for (int x = 0; x < dungeon.length; x++) {
            for (int y = 0; y < dungeon[x].length; y++) {
                int wallCount = 0;
                wallCount = getWalls(x, y);
                if(dungeon[x][y] == DungeonTile.WALL) {
                    wallCount++;
                }                
                if(wallCount >= 5 || wallCount <= lowWall) {
                    dungeon[x][y] = DungeonTile.WALL;
                } else {
                    dungeon[x][y] = DungeonTile.OPEN;
                }
            }
        }
    }
    public void refine(int lowWall, int highWall) {
        for (int x = 0; x < dungeon.length; x++) {
            for (int y = 0; y < dungeon[x].length; y++) {
                int wallCount = 0;
                wallCount = getWalls(x, y);
                if(dungeon[x][y] == DungeonTile.WALL) {
                    wallCount++;
                }                
                if(wallCount >= highWall || wallCount <= lowWall) {
                    dungeon[x][y] = DungeonTile.WALL;
                } else {
                    dungeon[x][y] = DungeonTile.OPEN;
                }
            }
        }
    }

    public DungeonTile[][] getDungeon() {
        return dungeon;
    }

    public void printDungeon() {
        for (int i = 0; i < dungeon[0].length + 2; i++) {
            System.out.print(DungeonTile.WALL.getDisplay());
        }
        System.out.println("");
        for (int x = 0; x < dungeon.length; x++) {
            System.out.print(DungeonTile.WALL.getDisplay());
            for (int y = 0; y < dungeon[x].length; y++) {
                System.out.print(dungeon[x][y].getDisplay());
            }
            System.out.println(DungeonTile.WALL.getDisplay());
        }
        for (int i = 0; i < dungeon[0].length + 2; i++) {
            System.out.print(DungeonTile.WALL.getDisplay());
        }
        System.out.println("");
    }

    private int getWalls(int x, int y) {
        int count = 0;
        //Upper Left
        count += ((x == 0) || (y == 0) || dungeon[x-1][y-1] == DungeonTile.WALL) ? 1 : 0;
        //Upper
        count += ((x == 0) || dungeon[x-1][y] == DungeonTile.WALL) ? 1 : 0;
        //Upper Right
        count += ((x == 0) || (y == dungeon[x].length-1) || dungeon[x-1][y+1] == DungeonTile.WALL) ? 1 : 0;
        //Left
        count += ((y == 0) || dungeon[x][y-1] == DungeonTile.WALL) ? 1 : 0;
        //Right
        count += ((y == dungeon[x].length-1) || dungeon[x][y+1] == DungeonTile.WALL) ? 1 : 0;
        //Bottom Left
        count += ((x == dungeon.length-1) || (y == 0) || dungeon[x+1][y-1] == DungeonTile.WALL) ? 1 : 0;
        //Bottom
        count += ((x == dungeon.length-1) || dungeon[x+1][y] == DungeonTile.WALL) ? 1 : 0;
        //Bottom Right
        count += ((x == dungeon.length-1) || (y == dungeon[x].length-1) || dungeon[x+1][y+1] == DungeonTile.WALL) ? 1 : 0;
        
        return count;
        
    }

}
