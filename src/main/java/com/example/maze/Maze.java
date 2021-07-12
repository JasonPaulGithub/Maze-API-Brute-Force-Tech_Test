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
            try {
                direction.takeGold();
            } catch (Exception e) {
                //e.printStackTrace();
            }
        }
    }

    private Direction enterMaze(String mazeName) throws IOException {
        // TODO: URL url = new URL(baseUrl + affix + mazeName);
        String baseUrl = "https://maze.hightechict.nl/api/mazes/enter?mazeName=" + mazeName;
        URL url = new URL(baseUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("accept", "application/json");
        con.setChunkedStreamingMode(100);
        con.setDoOutput(true);
        con.setRequestProperty(MazeApplication.auth, MazeApplication.code);
        //int status = con.getResponseCode();
        //System.out.println(status);

        // We now read the data as string
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder mazeData = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            mazeData.append(inputLine);
        }
        // ToDo: use finally/withResources here
        in.close();
        con.disconnect();
        return new Direction(mazeData.toString());
    }

}
