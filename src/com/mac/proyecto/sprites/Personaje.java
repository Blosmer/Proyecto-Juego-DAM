package com.mac.proyecto.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public class Personaje extends Sprite {

	public Body b2body;
	public BodyDef bdef;
	public World world;

	private Animation arrbAnim;
	private Animation abjoAnim;
	private Animation izqdAnim;
	private Animation drchAnim;
	private Animation actualAnim;
	private Texture pTexture;
	private TextureRegion frameActual;
	private String file;

	public TextureRegion getFrameActual() {
		return frameActual;
	}

	public void setFrameActual(TextureRegion frameActual) {
		this.frameActual = frameActual;
	}

	private int anchoPersonaje;
	private int altoPersonaje;
	private int medioAnchoPersonaje;

	private float posX, posY, posAnteriorX, posAnteriorY;
	private boolean andando;

	public Personaje(World world, String file, float posX, float posY) {
		super();
		this.world = world;
		this.file = file;
		this.posAnteriorX = posX;
		this.posAnteriorY = posY;
		this.posX = posX;
		this.posY = posY;
		andando = false;
		definePlayer(posX, posY);
		cargarAnimacion(4, 4, 0.1f);
	}

	public void definePlayer(float x, float y) {
		bdef = new BodyDef();
		bdef.position.set(x, y);
		bdef.type = BodyDef.BodyType.DynamicBody;
		b2body = world.createBody(bdef);

		FixtureDef fdef = new FixtureDef();
		CircleShape shape = new CircleShape();
		shape.setRadius(15);

		fdef.shape = shape;
		fdef.restitution = 0.8f;
		fdef.density = 0.08f;
		fdef.friction = 0.4f;
		b2body.createFixture(fdef);
	}

	public boolean cargarAnimacion(int tilesAnchoHoja, int tilesAltoHoja, float duracionFrame) {

		pTexture = new Texture(file);
		int altoHoja = pTexture.getHeight();
		int anchoHoja = pTexture.getWidth();
		anchoPersonaje = anchoHoja / tilesAnchoHoja;
		altoPersonaje = altoHoja / tilesAltoHoja;
		medioAnchoPersonaje = anchoPersonaje / 2;

		TextureRegion tRegion = new TextureRegion(pTexture, anchoHoja, altoHoja);
		if (tRegion != null) {

			TextureRegion[][] temp = tRegion.split(anchoHoja / tilesAnchoHoja, altoHoja / tilesAltoHoja);

			arrbAnim = new Animation(duracionFrame, temp[3]);
			drchAnim = new Animation(duracionFrame, temp[2]);
			abjoAnim = new Animation(duracionFrame, temp[0]);
			izqdAnim = new Animation(duracionFrame, temp[1]);

			actualAnim = abjoAnim; // comenzamos con uno cualquiera...
			return (true);

		}
		return (false);

	}

	public void moverDerecha(float delta) {
		this.andando = true;
		this.actualAnim = this.drchAnim;

	}

	public void moverIzquierda(float delta) {
		this.andando = true;
		this.actualAnim = this.izqdAnim;
	}

	public void moverAbajo(float delta) {
		this.andando = true;
		this.actualAnim = this.abjoAnim;

	}

	public void moverArriba(float delta) {
		this.andando = true;
		this.actualAnim = this.arrbAnim;

	}

	public boolean isAndando() {
		return andando;
	}

	public void setAndando(boolean andando) {
		this.andando = andando;
	}

	public float getPosX() {
		return posX;
	}

	public float getPosY() {
		return posY;
	}

	public Animation getActualAnim() {
		return actualAnim;
	}

	public void setActualAnim(Animation actAnim) {
		this.actualAnim = actAnim;
	}

	public Texture getHTexture() {
		return pTexture;
	}

	public Texture getpTexture() {
		return pTexture;
	}

	public void setpTexture(Texture pTexture) {
		this.pTexture = pTexture;
	}

	public int getMedioAnchoPersonaje() {
		return medioAnchoPersonaje;
	}

	public void setMedioAnchoPersonaje(int medioAnchoPersonaje) {
		this.medioAnchoPersonaje = medioAnchoPersonaje;
	}

	public int getAnchoPersonaje() {
		return anchoPersonaje;
	}

	public void setAnchoPersonaje(int anchoPersonaje) {
		this.anchoPersonaje = anchoPersonaje;
	}

	public int getAltoPersonaje() {
		return altoPersonaje;
	}

	public void setAltoPersonaje(int altoPersonaje) {
		this.altoPersonaje = altoPersonaje;
	}

	public void activarEA(float dt, float x, float y) {
		if (x > b2body.getPosition().x /** && x < 728 && x > 200 **/
		) {
			// right
			b2body.applyLinearImpulse(
					new Vector2((float) (60 + (Math.random() * (80 - 60))), (float) (0 + (Math.random() * (10 - 0)))),
					b2body.getWorldCenter(), true);
			moverDerecha(dt);
		}
		if (x < b2body.getPosition().x) {
			// left
			b2body.applyLinearImpulse(
					new Vector2((float) -(60 + (Math.random() * (80 - 60))), (float) (0 + (Math.random() * (10 - 0)))),
					b2body.getWorldCenter(), true);
			moverIzquierda(dt);
		}
		if (y < b2body.getPosition().y) {
			// down
			b2body.applyLinearImpulse(
					new Vector2((float) (0 + (Math.random() * (10 - 0))), (float) -(60 + (Math.random() * (80 - 60)))),
					b2body.getWorldCenter(), true);
			moverAbajo(dt);
		}
		if (y > b2body.getPosition().y) {
			// up
			b2body.applyLinearImpulse(
					new Vector2((float) (0 + (Math.random() * (10 - 0))), (float) (60 + (Math.random() * (80 - 60)))),
					b2body.getWorldCenter(), true);
			moverArriba(dt);
		}
	}
	
}
