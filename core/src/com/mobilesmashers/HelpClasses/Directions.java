package com.mobilesmashers.HelpClasses;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Directions {
    private HashMap<String, Point> directions;
    private final int SPEED;

    public Directions(int speed) {
        directions = new HashMap<String, Point>();
        this.SPEED = speed;
        addDirections();
    }

    public Point get(String key) {
        return directions.get(key);
    }

    private void addDirections() {
        directions.put(Strings.DOWN_LEFT_DIRECTION, new Point(SPEED, -SPEED));
        directions.put(Strings.DOWN_RIGHT_DIRECTION, new Point(SPEED, SPEED));
        directions.put(Strings.UP_LEFT_DIRECTION, new Point(-SPEED, -SPEED));
        directions.put(Strings.UP_RIGHT_DIRECTION, new Point(-SPEED, SPEED));
    }

    public String drawDirection() {
        List<String> valuesList = new ArrayList<String>(directions.keySet());
        int randomIndex = new Random().nextInt(directions.size());
        return valuesList.get(randomIndex);
    }
}
