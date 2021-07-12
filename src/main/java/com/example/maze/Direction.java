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

    Map<String,Boolean> directions = new HashMap<>();

    public Direction(String mazeData) {
        JSONObject mazeObj = new JSONObject(mazeData);
        parsePossibleMoveActionsArray();
        JSONArray possibleMoveActionsArray = mazeObj.getJSONArray("possibleMoveActions");
        parsePossibleMovesList();
        List<JSONObject> possibleMovesList = new ArrayList<>();

        for (int i = 0; i < possibleMoveActionsArray.length(); i++) {
            possibleMovesList.add((JSONObject) possibleMoveActionsArray.get(i));
        }

        for (JSONObject jsonObject : possibleMovesList) {
            directions.put(
                    jsonObject.getString("direction"),
                    jsonObject.getBoolean("hasBeenVisited")
            );
        }

    }

    private void parsePossibleMoveActionsArray() {
    }

    private void parsePossibleMovesList() {
    }

    public boolean exit() {
        //TODO
        return false;
    }

    public boolean isExit() {
        //TODO
        return false;
    }

    public void takeGold() {

    }

    public boolean hasGold() {
        //TODO
        return false;
    }

    // Gets a new Direction based on UVP/random
    public Direction getNextDirection() {
        //TODO
        return new Direction(null);
    }
}
