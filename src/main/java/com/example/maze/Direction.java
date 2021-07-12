package com.example.maze;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;

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
        Api api = new Api("/api/maze/", "exit", "post");
        api.call();
    }

    public boolean isExit() {
        return new JSONObject(mazeData).getBoolean("canExitMazeHere");
    }

    public void takeGold() throws Exception {
        Api api = new Api("/api/maze/", "collectScore", "post");
        System.out.println(api.call());
    }

    public boolean hasGold() {
        return new JSONObject(mazeData).getBoolean("canCollectScoreHere");
    }

    // Gets a new Direction based on UVP/random
    public Direction travelToNextDestination() throws Exception {
        return new Direction(takeDirection());
    }

    // Choose direction via Non-Visited Priority, or at random // Suggested: choose for score
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
    private String takeDirection() throws Exception {
        Api api = new Api("/api/maze/", "move?direction=" + chooseDirection(), "post");
        return api.call();
    }
}
