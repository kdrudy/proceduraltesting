/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.procedural;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author kylerudy
 */
public class Dungeon {

    int rooms;
    int height, width;

    DungeonTile[][] dungeon;

    public Dungeon(int rooms, int height, int width) {
        this.rooms = rooms;
        this.height = height;
        this.width = width;

        dungeon = new DungeonTile[width][height];
    }

    public void generate() {
        //Initialize with all walls
        for (DungeonTile[] row : dungeon) {
            Arrays.fill(row, DungeonTile.WALL);
        }
        int maxRoomWidth = width / 3;
        int maxRoomHeight = height / 3;

        List<Rectangle> existingRooms = generateRooms(maxRoomWidth, maxRoomHeight);

        printDungeon();
        System.out.println("");

        generateHallways(existingRooms);
        
        printDungeon();
        System.out.println("");
        
        addDoors();
    }

    private void generateHallways(List<Rectangle> existingRooms) {
        //Generate hallways
        for (Rectangle room : existingRooms) {
            //Pick a room to go to
            Rectangle destination = null;
            do {
                int destIndex = (int) (Math.random() * rooms);
                if (!room.equals(existingRooms.get(destIndex))) {
                    destination = existingRooms.get(destIndex);
                }
            } while (destination == null);

            int startX = (int) room.getCenterX(); //(int) (Math.random()*room.getWidth() + room.getX());
            int startY = (int) room.getCenterY(); //(int) (Math.random()*room.getHeight() + room.getY());

            int endX = (int) destination.getCenterX();
            int endY = (int) destination.getCenterY();

            int lastX = 0;
            if (startX > endX) {
                for (int x = endX; x <= startX; x++) {
                    dungeon[x][endY] = dungeon[x][endY] == DungeonTile.WALL ? DungeonTile.CORRIDOR : DungeonTile.OPEN;

                }
                lastX = startX;
            } else {
                for (int x = startX; x <= endX; x++) {
                    dungeon[x][startY] = DungeonTile.OPEN;
                }
                lastX = endX;
            }

            if (startY > endY) {
                for (int y = endY; y <= startY; y++) {
                    dungeon[lastX][y] = DungeonTile.OPEN;
                }
            } else {
                for (int y = startY; y <= endY; y++) {
                    dungeon[lastX][y] = DungeonTile.OPEN;
                }
            }
        }
    }

    private void addRoomWalls() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (dungeon[x][y] == DungeonTile.WALL) {
                    boolean hasRight = x + 1 < width;
                    boolean hasLeft = x - 1 > 0;
                    boolean hasTop = y - 1 > 0;
                    boolean hasBottom = y + 1 < height;
                    if ((hasRight && dungeon[x + 1][y] == DungeonTile.OPEN)
                            || (hasLeft && dungeon[x - 1][y] == DungeonTile.OPEN)
                            || (hasBottom && dungeon[x][y + 1] == DungeonTile.OPEN)
                            || (hasTop && dungeon[x][y - 1] == DungeonTile.OPEN)
                            || (hasRight && hasBottom && dungeon[x + 1][y + 1] == DungeonTile.OPEN)
                            || (hasRight && hasTop && dungeon[x + 1][y - 1] == DungeonTile.OPEN)
                            || (hasLeft && hasBottom && dungeon[x - 1][y + 1] == DungeonTile.OPEN)
                            || (hasLeft && hasTop && dungeon[x - 1][y - 1] == DungeonTile.OPEN)) {
                        dungeon[x][y] = DungeonTile.ROOM_WALL;
                    }
                }
            }
        }
    }

    private List<Rectangle> generateRooms(int maxRoomWidth, int maxRoomHeight) {
        //Generate rooms
        List<Rectangle> existingRooms = new ArrayList<>();
        for (int i = 0; i < rooms; i++) {
            boolean validRoom = false;
            //long start = System.currentTimeMillis();
            do {
                int roomX = (int) (Math.random() * width);
                int roomY = (int) (Math.random() * height);
                int roomWidth = (int) (Math.random() * maxRoomWidth);
                int roomHeight = (int) (Math.random() * maxRoomHeight);

                if (roomWidth < 2 || roomHeight < 2
                        || roomX > width - 2 || roomY > height - 2
                        || roomX + roomWidth > width || roomY + roomHeight > height) {
                    continue;
                }

                Rectangle currentRoom = new Rectangle(roomX, roomY, roomWidth, roomHeight);
                boolean intersects = false;
                for (Rectangle room : existingRooms) {
                    if (currentRoom.intersects(room)) {
                        intersects = true;
                    }
                }
                if (intersects) {
                    continue;
                }
                validRoom = true;
                existingRooms.add(currentRoom);

                for (int x = 0; x < roomWidth; x++) {
                    for (int y = 0; y < roomHeight; y++) {
                        dungeon[roomX + x][roomY + y] = DungeonTile.OPEN;
                    }
                }

            } while (!validRoom);
        }
        return existingRooms;
    }

    private void setHallwayTile(int x, int y) {
        switch (dungeon[x][y]) {
            case WALL:
                dungeon[x][y] = DungeonTile.OPEN;
                break;
            case ROOM_WALL:
                dungeon[x][y] = DungeonTile.DOOR;
                break;
        }
    }

    private void addDoors() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if(dungeon[x][y] == DungeonTile.OPEN && isDoor(x, y)) {
                    dungeon[x][y] = DungeonTile.DOOR;
                }
            }
            
        }
    }

    private boolean isDoor(int x, int y) {
        boolean doorFound = false;

        if (x > 0 && x < width - 1 && y > 0 && y < height - 1) {
            if (dungeon[x - 1][y] == DungeonTile.OPEN && dungeon[x + 1][y] == DungeonTile.OPEN
                    && dungeon[x][y - 1] != DungeonTile.OPEN && dungeon[x][y + 1] != DungeonTile.OPEN) { //Left to right
                if (dungeon[x - 1][y - 1] == DungeonTile.OPEN || dungeon[x - 1][y + 1] == DungeonTile.OPEN
                        || dungeon[x + 1][y - 1] == DungeonTile.OPEN || dungeon[x + 1][y + 1] == DungeonTile.OPEN) { //A diagonal is also open
                    doorFound = true;
                }
            }
            if (dungeon[x - 1][y] != DungeonTile.OPEN && dungeon[x + 1][y] != DungeonTile.OPEN
                    && dungeon[x][y - 1] == DungeonTile.OPEN && dungeon[x][y + 1] == DungeonTile.OPEN) { //Top to bottom
                if (dungeon[x - 1][y - 1] == DungeonTile.OPEN || dungeon[x - 1][y + 1] == DungeonTile.OPEN
                        || dungeon[x + 1][y - 1] == DungeonTile.OPEN || dungeon[x + 1][y + 1] == DungeonTile.OPEN) { //A diagonal is also open
                    doorFound = true;
                }
            }
        }

        return doorFound;
    }

    public void printDungeon() {
        for (int i = 0; i < width + 2; i++) {
            System.out.print(DungeonTile.WALL.getDisplay());
        }
        System.out.println("");
        for (int y = 0; y < height; y++) {
            System.out.print(DungeonTile.WALL.getDisplay());
            for (int x = 0; x < width; x++) {
                System.out.print(dungeon[x][y].getDisplay());
            }
            System.out.println(DungeonTile.WALL.getDisplay());
        }
        for (int i = 0; i < width + 2; i++) {
            System.out.print(DungeonTile.WALL.getDisplay());
        }
        System.out.println("");
    }

    public int getRooms() {
        return rooms;
    }

    public void setRooms(int rooms) {
        this.rooms = rooms;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

}
