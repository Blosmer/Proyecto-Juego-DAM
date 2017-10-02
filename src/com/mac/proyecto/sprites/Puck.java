package com.mac.proyecto.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public class Puck extends Sprite {

	public World world;
	public Body b2body;
	public BodyDef bdef;

	public Puck(World world, float x, float y) {
		this.world = world;
		definePuck(x, y);

		Texture playerTexture = new Texture("imagenes/puck.png");
		TextureRegion playerRegion = new TextureRegion(playerTexture, playerTexture.getWidth(),
				playerTexture.getHeight());

		setBounds(0, 0, playerTexture.getWidth(), playerTexture.getHeight());
		setSize(playerRegion.getRegionWidth() * 0.5f, playerRegion.getRegionHeight() * 0.5f);
		setRegion(playerRegion);
	}

	public void update(float dt) {
		setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
	}

	public void definePuck(float x, float y) {
		bdef = new BodyDef();
		bdef.position.set(x, y);
		bdef.type = BodyDef.BodyType.DynamicBody;
		b2body = world.createBody(bdef);

		FixtureDef fdef = new FixtureDef();
		CircleShape shape = new CircleShape();
		shape.setRadius(30);

		fdef.restitution = 0.8f;
		fdef.friction = 2f;
		fdef.shape = shape;
		b2body.createFixture(fdef);
	}

	public void dispose() {
		this.dispose();
	}

}
