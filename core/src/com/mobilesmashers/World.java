package com.mobilesmashers;

import com.mobilesmashers.HelpClasses.Point;

public class World {
    public final Player player;

    public World()
    {
        this.player = new Player(new Point(Board.WIDTH/2 - Player.WIDTH +1, Board.HEIGHT/2 - Player.HEIGHT +1));
    }

    public void update()
    {
        updatePlayer();
    }

    private void updatePlayer()
    {
        player.move();
    }
}
