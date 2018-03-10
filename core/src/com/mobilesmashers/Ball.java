package com.mobilesmashers;

import com.badlogic.gdx.graphics.Texture;
import com.mobilesmashers.HelpClasses.Point;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Ball {
    public static final int WIDTH = 60;
    public static final int HEIGHT = 60;
    //maybe get all images to separate file?
    private final Texture image;
    //create position from 1,1 to Board.WIDTH-1, Board.HEIGHT-1
    private Point position;
    private final int speed;
    private String currentDirection;
    private HashMap<String, Point> directions;

    public Ball(Point position) {
        speed = 1;
        this.image = new Texture("badlogic.jpg");
        this.position = position;
        directions = new HashMap<String, Point>();
        addDirections();
        currentDirection = drawDirection();
    }

    public Point getPosition() {
        return position;
    }

    public Texture getImage() {
        return image;
    }

    public void move() {
        //check for collision
        //from left to right
        if (position.y + HEIGHT == Board.RIGHT_BOUND) {
            if (currentDirection.equals("DOWN_RIGHT")) {
                currentDirection = "DOWN_LEFT";
            } else {
                currentDirection = "UP_LEFT";
            }
        }
        //from right to left
        if (position.y ==  Board.LEFT_BOUND){
            if (currentDirection.equals("DOWN_LEFT")) {
                currentDirection = "DOWN_RIGHT";
            } else {
                currentDirection = "UP_RIGHT";
            }
        }
        //from up to down
        if (position.x + WIDTH == Board.BOTTOM_BOUND) {
            if (currentDirection.equals("DOWN_RIGHT")) {
                currentDirection = "UP_RIGHT";
            } else {
                currentDirection = "UP_LEFT";
            }
        }
        //from down to up
        if (position.x == Board.UP_BOUND) {
            if (currentDirection.equals("UP_RIGHT")) {
                currentDirection = "DOWN_RIGHT";
            } else {
                currentDirection = "DOWN_LEFT";
            }
        }
        applyDirection(currentDirection);
    }

    private void applyDirection(String direction) {
        position.x += directions.get(direction).x;
        position.y += directions.get(direction).y;
    }

    private String drawDirection() {
        List<String> valuesList = new ArrayList<String>(directions.keySet());
        int randomIndex = new Random().nextInt(directions.size());
        return valuesList.get(randomIndex);
    }

    private void addDirections() {
        directions.put("DOWN_LEFT", new Point(speed, -speed));
        directions.put("DOWN_RIGHT", new Point(speed, speed));
        directions.put("UP_LEFT", new Point(-speed, -speed));
        directions.put("UP_RIGHT", new Point(-speed, speed));
    }
}
