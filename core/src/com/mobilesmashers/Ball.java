package com.mobilesmashers;

import com.badlogic.gdx.graphics.Texture;
import com.mobilesmashers.HelpClasses.Point;
import com.mobilesmashers.HelpClasses.Strings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Ball {
    public static final int WIDTH = 80;
    public static final int HEIGHT = 80;
    //maybe get all images to separate file?
    private final Texture image;
    private Point position;
    private final int SPEED;
    private String currentDirection;
    private HashMap<String, Point> directions;
    public boolean stopped = false;

    public Ball(Point position) {
        SPEED = 1;
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
            if (currentDirection.equals(Strings.DOWN_RIGHT_DIRECTION)) {
                currentDirection = Strings.DOWN_LEFT_DIRECTION;
            } else {
                currentDirection = Strings.UP_LEFT_DIRECTION;
            }
        }
        //from right to left
        if (position.y == Board.LEFT_BOUND) {
            if (currentDirection.equals(Strings.DOWN_LEFT_DIRECTION)) {
                currentDirection = Strings.DOWN_RIGHT_DIRECTION;
            } else {
                currentDirection = Strings.UP_RIGHT_DIRECTION;
            }
        }
        //from up to down
        if (position.x + WIDTH == Board.BOTTOM_BOUND) {
            if (currentDirection.equals(Strings.DOWN_RIGHT_DIRECTION)) {
                currentDirection = Strings.UP_RIGHT_DIRECTION;
            } else {
                currentDirection = Strings.UP_LEFT_DIRECTION;
            }
        }
        //from down to up
        if (position.x == Board.UP_BOUND) {
            if (currentDirection.equals(Strings.UP_RIGHT_DIRECTION)) {
                currentDirection = Strings.DOWN_RIGHT_DIRECTION;
            } else {
                currentDirection = Strings.DOWN_LEFT_DIRECTION;
            }
        }

        //0,0
        if (position.x == Board.UP_BOUND && position.y == Board.LEFT_BOUND) {
            currentDirection = Strings.DOWN_RIGHT_DIRECTION;
        }
        //0,width
        if (position.x == Board.UP_BOUND && position.y == Board.RIGHT_BOUND) {
            currentDirection = Strings.DOWN_LEFT_DIRECTION;
        }
        //height, 0
        if (position.x == Board.BOTTOM_BOUND && position.y == Board.LEFT_BOUND) {
            currentDirection = Strings.UP_RIGHT_DIRECTION;
        }
        //height, width
        if (position.x == Board.BOTTOM_BOUND && position.y == Board.RIGHT_BOUND) {
            currentDirection = Strings.UP_LEFT_DIRECTION;
        }

        applyDirection(currentDirection);
    }

    public void moveToPoint(Point point) {
        if (position.x > point.x) {
            position.x--;
        } else if (position.x < point.x) {
            position.x++;
        }

        if (position.y > point.y) {
            position.y--;
        } else if (position.y < point.y) {
            position.y++;
        }
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
        directions.put(Strings.DOWN_LEFT_DIRECTION, new Point(SPEED, -SPEED));
        directions.put(Strings.DOWN_RIGHT_DIRECTION, new Point(SPEED, SPEED));
        directions.put(Strings.UP_LEFT_DIRECTION, new Point(-SPEED, -SPEED));
        directions.put(Strings.UP_RIGHT_DIRECTION, new Point(-SPEED, SPEED));
    }
}
