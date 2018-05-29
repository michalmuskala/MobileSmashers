package com.mobilesmashers.utils.wywalic.oldActors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

import static com.mobilesmashers.utils.Geo.vectorAngle;

public class Rope extends Actor {

	private float thickness;

	private com.mobilesmashers.utils.wywalic.oldActors.GameActor
			beg,
			end;

	private Texture texture;

	public Rope(com.mobilesmashers.utils.wywalic.oldActors.GameActor beg, com.mobilesmashers.utils.wywalic.oldActors.GameActor end, float thickness, Texture texture) {
		this.beg = beg;
		this.end = end;
		this.thickness = thickness;
		this.texture = texture;
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		if (beg == null || end == null)
			return;

		Vector2
				begPos = beg.getMiddlePosition(),
				endPos = end.getMiddlePosition();
		float len = endPos.dst(begPos);

		batch.draw(
				texture,
				begPos.x, begPos.y,
				0, 0,// transform origin x, y (rel to origin)
				len, thickness,      // size x, y
				1, 1,  // scale x, y
				vectorAngle(begPos, endPos),
				0, 0,
				(int) len, (int) thickness,
				false, false
		);
	}
}
