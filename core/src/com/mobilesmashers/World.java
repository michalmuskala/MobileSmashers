package com.mobilesmashers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Rectangle;
import com.mobilesmashers.HelpClasses.Point;
import com.mobilesmashers.HelpClasses.Pointf;
import com.mobilesmashers.ShapeDrawing.Disk;
import com.mobilesmashers.ShapeDrawing.Rect;
import com.mobilesmashers.ShapeDrawing.RotRect;
import com.mobilesmashers.ShapeDrawing.Shape;
import com.mobilesmashers.ShapeDrawing.Text;
import com.mobilesmashers.HelpClasses.Strings;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.badlogic.gdx.graphics.Color.BLACK;
import static com.badlogic.gdx.graphics.Color.BLUE;
import static com.badlogic.gdx.graphics.Color.GREEN;
import static com.badlogic.gdx.graphics.Color.YELLOW;

public class World implements InputProcessor {
    public final Player player;
    public List<Ball> balls;
    public Ball firstTouchedBall;
    public Ball secondTouchedBall;
    private MobileSmashers game;
    public Line line;

    // shape testing
    List<Shape> shapes;
    public World(MobileSmashers game) {
        this.player = new Player(new Point(Board.WIDTH / 2 - Player.WIDTH + 1, Board.HEIGHT / 2 - Player.HEIGHT + 1));
        this.balls = new ArrayList<Ball>();
        line = null;
        firstTouchedBall = null;
        secondTouchedBall = null;
        this.game = game;
        createBalls();
        Gdx.input.setInputProcessor(this);

        // shape testing
        shapes = new ArrayList<Shape>();
        shapes.add(new Disk(new Point(400, 400), 300, Disk.newTexture(300, BLACK)));
        shapes.add(new Rect(new Point(200, 200), new Point(600, 300), Rect.newTexture(new Point(600, 300), YELLOW)));
        shapes.add(new Text(new Point(600, 600), "Hello world!", BLUE, 3));
        shapes.add(new RotRect(new Point(100, 100), new Point(100, 1000), Rect.newTexture(new Point(100, 1000), GREEN)));
        shapes.add(new RotRect(new Point(500, 100), new Point(100, 1000), new Point(150, 600), new Pointf(0.5f, 1.f), 0.5f, Shape.textures.get(2)));
    }

    public void update() {
        Gdx.input.setInputProcessor(this);
        updatePlayer();
        updateBalls();
        updateLine();

        ((RotRect)shapes.get(4)).incAngle(10.f);
    }

    private void updatePlayer() {
        player.move();
    }

    private void updateBalls() {
        for (int i = 0; i < balls.size(); i++) {
            //check for collision
            //com.mobilesmashers.Sound.playPlayerDestroyed();
            if (balls.get(i).getState().equals(Strings.STATE_FREE)) {
                balls.get(i).move();
            } else if (balls.get(i).getState().equals(Strings.STATE_CATCHED)) {
                Point point = new Point((firstTouchedBall.getPosition().x + secondTouchedBall.getPosition().x) / 2, (firstTouchedBall.getPosition().y + secondTouchedBall.getPosition().y) / 2);
                if (balls.get(i).getPosition().x == point.x && balls.get(i).getPosition().y == point.y) {
                    //check if balls tasks are correct linked
                    //if(TaskValidator.areTasksCorrect(firstTouchedBall.task, secondTouchedBall.task)
                    balls.remove(balls.indexOf(firstTouchedBall));
                    balls.remove(balls.indexOf(secondTouchedBall));
                    com.mobilesmashers.Sound.playBallsCatched();

                    firstTouchedBall = null;
                    secondTouchedBall = null;
                    line = null;
                    break;
                } else {
                    balls.get(i).moveToPoint(point);
                }
            }
        }
        if (balls.size() == 0) {
            Board.level++;
            createBalls();
        }
    }

    private void updateLine() {
        if (line != null) {
            if (firstTouchedBall != null && secondTouchedBall != null) {
                Point tmp = new Point(secondTouchedBall.getPosition().x + Ball.WIDTH / 2, secondTouchedBall.getPosition().y + Ball.HEIGHT / 2);
                Point tmp2 = new Point(firstTouchedBall.getPosition().x + Ball.WIDTH / 2, firstTouchedBall.getPosition().y + Ball.HEIGHT / 2);
                line.setInitialPosition(tmp);
                line.setTarget(tmp2);
            } else if (firstTouchedBall != null) {
                {
                    Point tmp = new Point(player.getPosition().x + Player.WIDTH / 2, player.getPosition().y + Player.HEIGHT);
                    Point tmp2 = new Point(firstTouchedBall.getPosition().x + Ball.WIDTH / 2, firstTouchedBall.getPosition().y + Ball.HEIGHT / 2);
                    line.setInitialPosition(tmp);
                    line.setTarget(tmp2);
                }
            }
        }
    }

    private void createBalls() {
        for (int i = 0; i < Board.level * 2; i++) {
            balls.add(new Ball(drawBallsPosition()));
        }
    }

    private Point drawBallsPosition() {
        Point position = new Point(0, 0);
        Random random = new Random();
        int x = random.nextInt(Board.WIDTH - Ball.WIDTH);
        int y = random.nextInt(Board.HEIGHT - Ball.HEIGHT);
        Rectangle ballRectangle = new Rectangle(x, y, Ball.WIDTH, Ball.HEIGHT);
        int distance = 200;
        Rectangle playerRectangle = new Rectangle(player.getPosition().x - distance, player.getPosition().y - distance, Player.WIDTH + 2 * distance, Player.HEIGHT + 2 * distance);
        if (ballRectangle.overlaps(playerRectangle)) {
            return drawBallsPosition();
        } else {
            position.x = x;
            position.y = y;
            return position;
        }
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.BACK) {
            game.setMainMenuScreen();
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        for (int i = 0; i < balls.size(); i++) {
            Rectangle ballRectangle = new Rectangle(balls.get(i).getPosition().x, balls.get(i).getPosition().y, Ball.WIDTH, Ball.HEIGHT);

            int touchX = (Board.HEIGHT - Gdx.input.getY());
            int touchY = Gdx.input.getX();

            if (firstTouchedBall != null) {
                if (ballRectangle.contains(touchY, touchX) && balls.get(i) != firstTouchedBall && secondTouchedBall == null) {
                    secondTouchedBall = balls.get(i);
                    setTouchedBallsState(Strings.STATE_CATCHED);
                    com.mobilesmashers.Sound.playShootLine();
                }
                //if we touched again first touched ball
                else if (ballRectangle.contains(touchY, touchX) && balls.get(i) == firstTouchedBall && secondTouchedBall == null) {
                    firstTouchedBall.setState(Strings.STATE_FREE);
                    firstTouchedBall = null;
                    line = null;
                    com.mobilesmashers.Sound.playLineBack();
                }
                //if there are 2 touched balls and we touched first
                else if (ballRectangle.contains(touchY, touchX) && balls.get(i) == firstTouchedBall && secondTouchedBall != null) {
                    setTouchedBallsState(Strings.STATE_FREE);
                    firstTouchedBall = secondTouchedBall;
                    secondTouchedBall = null;
                    com.mobilesmashers.Sound.playLineBack();
                }
                //if there are 2 touched balls and we touched second
                else if (ballRectangle.contains(touchY, touchX) && balls.get(i) == secondTouchedBall && secondTouchedBall != null) {
                    setTouchedBallsState(Strings.STATE_FREE);
                    secondTouchedBall = null;
                    com.mobilesmashers.Sound.playLineBack();
                }

            } else if (ballRectangle.contains(touchY, touchX) && secondTouchedBall == null) {
                firstTouchedBall = balls.get(i);
                line = new Line();
                com.mobilesmashers.Sound.playShootLine();
            }
        }
        return false;
    }

    private void setTouchedBallsState(java.lang.String state) {
        firstTouchedBall.setState(state);
        secondTouchedBall.setState(state);
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}