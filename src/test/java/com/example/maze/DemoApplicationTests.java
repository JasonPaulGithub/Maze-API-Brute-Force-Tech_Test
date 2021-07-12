package com.example.maze;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class DemoApplicationTests {

    @Test
    public void testForgetApiObject() {
        Api api = new Api("/api/player/", "forget", "delete");
        api.call();
        assert api.getStatus() == 202;
    }

    @Test
    public void testRegisterApiObject() {
        Api api = new Api("/api/player/", "register?name=Jason", "post");
        api.call();
        assert (api.getStatus() == 202 || api.getStatus() == 409);
    }

    @Test
    public void testGetMazesApiObject() {
        Api api = new Api("/api/mazes/", "all", "get");
        api.call();
        assert (api.getStatus() == 200);
    }

    @Test
    public void checkForGoldApiObject() {
        // ToDo: We can test positive and negative results with "collectScore() vs Api" with custom strings
    }
}