package com.example.maze;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

public class Maze {

    Direction direction;

    // Constructor
    public Maze(String mazeName) throws IOException {
        this.direction = enterMaze(mazeName);
    }

    public void traverse() {
        checkForGold();
        if (direction.isExit()) {
            try {
                direction.exit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                this.direction = direction.travelToNextDestination();
                this.traverse();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void checkForGold() {
        if (direction.hasGold()) {
            System.out.println("Gold spotted! Trying to collect...");
            try {
                direction.takeGold();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private Direction enterMaze(String mazeName) throws IOException {
        Api api = new Api("/api/mazes/", "enter?mazeName=" + mazeName, "post");
        return new Direction(api.call());
    }

}
