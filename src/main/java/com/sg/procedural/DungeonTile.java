/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.procedural;

/**
 *
 * @author kylerudy
 */
public enum DungeonTile {

    WALL('#'), OPEN('.'), DOOR('*'),
    ROOM_WALL('%'), CORRIDOR(',');

    private char display;

    DungeonTile(char display) {
        this.display = display;
    }

    char getDisplay() {
        return display;
    }
}
