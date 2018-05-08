package com.mobilesmashers.ShapeDrawing;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.mobilesmashers.HelpClasses.Point;

import java.util.ArrayList;

public abstract class Shape {

	public static ArrayList<Texture> textures = new ArrayList<Texture>();

	protected Point position;

	protected Shape(Point position) {
		this.position = position;
	}

	public abstract void draw(Batch batch);
}
