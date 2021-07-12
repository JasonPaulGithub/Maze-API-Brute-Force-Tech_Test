package com.example.maze;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MazeApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(MazeApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        forget();
        register();
        JSONArray mazes = new JSONArray(getMazes());
        for(int i = 0; i<mazes.length();i++){
            JSONObject jo = (JSONObject) mazes.get(i);
            String mapName = jo.getString("name");
            System.out.println("Visiting map :" + mapName);
            Maze maze = new Maze(mapName.replace(" ", "%20"));
            maze.traverse();
        }
    }

    private String getMazes() throws Exception{
        Api api = new Api("/api/mazes/", "all", "get");
        return api.call();
    }

    // API call to reset the player account and allow them to start over.
    public void forget() throws Exception {
        Api api = new Api("/api/player/", "forget", "delete");
        System.out.println(api.call());
    }

    // API call to start the player off in the game.
    public void register() throws Exception {
        Api api = new Api("/api/player/", "register?name=Jason", "post");
        System.out.println(api.call());
    }

}
