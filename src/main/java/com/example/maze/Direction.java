package com.example.maze;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private Map<String,Boolean> directions = new HashMap<>();

    public Direction(String mazeData) {
        this.mazeData = mazeData;
        parsePossibleMoveActionsArray();
    }

    // Parse the Possible Moves array
    private void parsePossibleMoveActionsArray() {
        JSONArray possibleMoveActionsArray = new JSONObject(mazeData).getJSONArray("possibleMoveActions");
        List<JSONObject> possibleMovesList = new ArrayList<>();

        for (int i = 0; i < possibleMoveActionsArray.length(); i++) {
            possibleMovesList.add((JSONObject) possibleMoveActionsArray.get(i));
        }

        parsePossibleMovesList(possibleMovesList);
    }

    // Parse all possible moves with visited data from the Possible Moves Array
    private void parsePossibleMovesList(List<JSONObject> possibleMovesList) {
        parsePossibleMovesList(possibleMovesList);
        for (JSONObject jsonObject : possibleMovesList) {
            directions.put(
                    jsonObject.getString("direction"),
                    jsonObject.getBoolean("hasBeenVisited")
                    //ToDo : handle "allowsExit" for efficiency;
            );
        }
    }

    public boolean exit() {
        return false;
    }

    public boolean isExit() {
        return new JSONObject(mazeData).getBoolean("canExitMazeHere");
    }

    public void takeGold() {

    }

    public boolean hasGold() {
        return new JSONObject(mazeData).getBoolean("canCollectScoreHere");
    }

    // Gets a new Direction based on UVP/random
    public Direction travelToNextDirection() {
        return new Direction(takeDirection());
    }

    // Choose direction via Non-Visited Priority, or at random
    private String chooseDirection() {
        return null;
    }

    //Make the Api call and put the data into the new Direction object
    private String takeDirection() {
        chooseDirection();
        //make Api call with chooseDirection as affix
        return null;
    }
}
