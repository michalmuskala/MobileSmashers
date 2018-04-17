package com.mobilesmashers;

import com.badlogic.gdx.graphics.Texture;
import com.mobilesmashers.HelpClasses.*;

public class Ball {
    public static final int WIDTH = 80;
    public static final int HEIGHT = 80;
    //maybe get all images to separate file?
    private final Texture image;
    private Point position;
    private final int SPEED;
    private String currentDirection;
    private Directions directions;
    private String state;

    public Ball(Point position) {
        SPEED = 1;
        this.image = new Texture("badlogic.jpg");
        this.position = position;
        directions = new Directions(SPEED);
        currentDirection = directions.drawDirection();
        state = Strings.STATE_FREE;
    }

    public Point getPosition() {
        return position;
    }

    public Texture getImage() {
        return image;
    }

    public String getState() {
        return state;
    }

    public void setState(String value) {
        this.state = value;
    }

    public void move() {
        setDirectionIfHitsBounds();
        setDirectionIfInCorners();

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

    private void setDirectionIfHitsBounds() {
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
    }

    private void setDirectionIfInCorners() {
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
    }
}
