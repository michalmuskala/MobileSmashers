package com.mobilesmashers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.mobilesmashers.HelpClasses.Point;

public class Player {
    public static final int WIDTH = 60;
    public static final int HEIGHT = 60;
    //maybe get all images to separate file?
    private final Texture image;
    private Point position;
    private final int speed;

    public Player(Point position)
    {
        speed = 3;
        this.image = new Texture("badlogic.jpg");
        this.position = position;
    }

    public Point getPosition()
    {
        return position;
    }
    public Texture getImage()
    {
        return image;
    }

    public void move()
    {
        float accelX = Gdx.input.getAccelerometerX();
        float accelY = Gdx.input.getAccelerometerY();

        //go right
        if (accelX < -2 && position.y + Player.HEIGHT < Board.RIGHT_BOUND){
            position.y+=speed;
        }
        //go left
        if (accelX > +2 && position.y> Board.LEFT_BOUND){
            position.y-=speed;
        }
        //go up
        if (accelY < -2 && position.x > Board.UP_BOUND){
            position.x-=speed;
        }
        //go down
        if (accelY > +2 && position.x + Player.WIDTH < Board.BOTTOM_BOUND){
            position.x+=speed;
        }
    }

}
