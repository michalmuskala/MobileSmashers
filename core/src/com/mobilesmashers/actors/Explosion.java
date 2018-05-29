package com.mobilesmashers.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;

import static com.mobilesmashers.utils.World.met_to_pix;

public class Explosion extends GameActor {

	private boolean expanding;

	private float
			max_radiusPx,
			delta_diameterPx,
			implosion_factor;

	private Actor
			exploding,
			exploded;

	public Explosion(float max_radius, float delta_radius, float implosion_factor, Actor exploding, Actor exploded, Texture texture) {
		super(texture);

		expanding = true;

		if(delta_radius < 0)
			throw new RuntimeException("Delta radius must be bigger than 0. Moron."); // this shouldn't happen

		this.max_radiusPx = met_to_pix(max_radius);
		this.delta_diameterPx = met_to_pix(delta_radius) * 2;
		this.implosion_factor = implosion_factor;
		this.exploding = exploding;
		this.exploded = exploded;

		setSize(0f, 0f);
	}

	@Override
	public void act(float delta) {
		float
				diameter = getWidth(),
				radius = diameter / 2f;

		if (expanding && radius >= max_radiusPx) {
			expanding = false;

			exploding.setVisible(false);
			exploded.setVisible(false);

			delta_diameterPx *= -implosion_factor;
		}

		diameter += delta_diameterPx;

		setX(exploding.getX() + exploding.getWidth() / 2f - radius);
		setY(exploding.getY() + exploding.getHeight() / 2f - radius);

		setSize(diameter, diameter);
	}
}
// FIXME: explosion performs small position jump on implosion start
