package com.example.maze;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

// {
// "possibleMoveActions":[{
// "direction":"Right",
// "isStart":false,
// "allowsExit":false,
// "allowsScoreCollection":false,
// "hasBeenVisited":false,
// "rewardOnDestination":10,
// "tagOnTile":null
// }],
// "canCollectScoreHere":false,
// "canExitMazeHere":false,
// "currentScoreInHand":0,
// "currentScoreInBag":0,
// "tagOnCurrentTile":null
// }

public class Direction {

    private final String mazeData;
    private Map<String, Boolean> directions = new HashMap<>();

    public Direction(String mazeData) {
        this.mazeData = mazeData;
        parseMazeDataForNextDestination();
    }

    // Parse the Possible Moves array
    private void parseMazeDataForNextDestination() {
        JSONArray possibleMoveActionsArray = new JSONObject(mazeData).getJSONArray("possibleMoveActions");
        List<JSONObject> possibleMovesList = new ArrayList<>();

        for (int i = 0; i < possibleMoveActionsArray.length(); i++) {
            possibleMovesList.add((JSONObject) possibleMoveActionsArray.get(i));
        }

        parsePossibleMovesList(possibleMovesList);
    }

    // Parse all possible moves with visited data from the Possible Moves Array
    private void parsePossibleMovesList(List<JSONObject> possibleMovesList) {
        for (JSONObject jsonObject : possibleMovesList) {
            directions.put(
                    jsonObject.getString("direction"),
                    jsonObject.getBoolean("hasBeenVisited")
                    //ToDo : handle "allowsExit" for efficiency;
            );
        }
    }

    public void exit() throws Exception {
        String baseUrl = "https://maze.hightechict.nl/api/maze/exit";
        URL url = new URL(baseUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("accept", "application/json");
        con.setChunkedStreamingMode(100);
        con.setDoOutput(true);
        con.setRequestProperty(MazeApplication.auth, MazeApplication.code);
        int status = con.getResponseCode();
        System.out.println("exit status " + status);

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
    }

    public boolean isExit() {
        return new JSONObject(mazeData).getBoolean("canExitMazeHere");
    }

    public void takeGold() {
        //ToDo
        System.out.println("Tile has gold!");
    }

    public boolean hasGold() {
        return new JSONObject(mazeData).getBoolean("canCollectScoreHere");
    }

    // Gets a new Direction based on UVP/random
    public Direction travelToNextDestination() throws Exception {
        return new Direction(takeDirection());
    }

    // Choose direction via Non-Visited Priority, or at random
    private String chooseDirection() {
        String chosenDirection = null;
        // Go to non-visited
        for (Map.Entry<String, Boolean> entry : directions.entrySet()) {
            if (!entry.getValue()) {
                chosenDirection = entry.getKey();
            }
        }
        // Or choose at random
        if (chosenDirection == null) {
            String[] keysAsArray = directions.keySet().toArray(new String[0]);
            Random r = new Random();
            return keysAsArray[r.nextInt(keysAsArray.length)];
        }
        return chosenDirection;
    }

    //Make the Api call and put the data into the new Direction object
    private String takeDirection() throws Exception{
        String baseUrl = "https://maze.hightechict.nl/api/maze/move?direction="+chooseDirection();
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
        return mazeData.toString();
    }
}
