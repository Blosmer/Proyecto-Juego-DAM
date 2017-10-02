package com.mac.proyecto.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mac.proyecto.ProyectoJuego;
import com.mac.proyecto.sprites.NPC;
import com.mac.proyecto.sprites.Personaje;
import com.mac.proyecto.sprites.Puck;
import com.mac.proyecto.tools.MapBodyBuilder;
import com.mac.proyecto.view.Hud;

public class PistaHielo extends PlayScreen {

	private OrthographicCamera gameCam;
	private Viewport gamePort;
	private Hud hud;
	private float duracionTiempo = 0;

	private TmxMapLoader mapLoader;
	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
	TiledMapTileLayer layer0;
	Vector3 center;

	private World world;
	private Box2DDebugRenderer b2dr;

	private int[] capasInferiores;
	private int[] capasSuperiores;

	private Personaje jugador1, jugador2;
	private NPC coder;
	private Puck puck;
	private float impulso = 80f;

	private Music music;
	private Sound sonidoGol1;

	private boolean gol;
	private boolean puedeMarcar;
	private boolean p1IA, p2IA;
	private float timeCount;
	private int tiempoTrasMarcar;

	private BitmapFont font;

	public PistaHielo(ProyectoJuego game) {
		super(game);
		gol = false;
		puedeMarcar = true;
		p1IA = false;
		p2IA = false;
		timeCount = 0;
		tiempoTrasMarcar = 0;
		gameCam = new OrthographicCamera();
		world = new World(new Vector2(0, -100), true);

		gamePort = new FitViewport(ProyectoJuego.V_WIDTH, ProyectoJuego.V_HEIGHT, gameCam);

		mapLoader = new TmxMapLoader();
		map = mapLoader.load("mapas/testMap.tmx");
		renderer = new OrthogonalTiledMapRenderer(map);

		capasInferiores = new int[1];
		capasInferiores[0] = 0;
		//capasInferiores[1] = 1;

		//capasSuperiores = new int[1];
		//capasSuperiores[0] = 2;
		// capasSuperiores[1] = 3;

		// Centrar camara
		layer0 = (TiledMapTileLayer) map.getLayers().get(0);
		center = new Vector3(layer0.getWidth() * layer0.getTileWidth() / 2, 250, 0);
		gameCam.position.set(center);

		coder = new NPC("imagenes/coder.png", 2, 1, 438, 420);
		coder.setSize(coder.getWidth() * 0.7f, coder.getHeight() * 0.7f);
		b2dr = new Box2DDebugRenderer();

		// Crear Obstaculos de la capa Obstaculos de tipo Objeto
		MapBodyBuilder.crearBodies(map, world);

		jugador1 = new Personaje(world, "imagenes/hansolo.png", 200, 193);
		jugador2 = new Personaje(world, "imagenes/lando.png", 728, 193);

		puck = new Puck(world, 464, 193);
		hud = new Hud(game.batch);
		cargarMusica();
		cargarSonidos();

		font = new BitmapFont();
		font.setColor(Color.WHITE);

	}

	private void cargarSonidos() {
		sonidoGol1 = Gdx.audio.newSound(Gdx.files.internal("audio/goal1.mp3"));
	}

	public void cargarPartido() {
		game.setScreen(new PistaHielo(game));
	}

	public void cargarPosicionInicio() {
		jugador1.b2body.setType(BodyDef.BodyType.StaticBody);
		jugador2.b2body.setType(BodyDef.BodyType.StaticBody);
		puck.b2body.setType(BodyDef.BodyType.StaticBody);

		jugador1.b2body.setTransform(200, 193, 0);
		jugador2.b2body.setTransform(728, 193, 0);
		puck.b2body.setTransform(464, 193, 0);

		jugador1.b2body.setType(BodyDef.BodyType.DynamicBody);
		jugador2.b2body.setType(BodyDef.BodyType.DynamicBody);
		puck.b2body.setType(BodyDef.BodyType.DynamicBody);
	}

	@Override
	public void show() {

	}

	private void cargarMusica() {
		music = Gdx.audio.newMusic(Gdx.files.internal("audio/Mr. Blue Sky - 8Bit.mp3"));
		music.setVolume(0.5f);
		music.play();
		music.setLooping(true);
	}

	public void update(float dt) {
		if (gol == true) {
			marcarGolPausa(dt);
		}
		handleInput(dt);
		world.step(1 / 60f, 6, 2);
		puck.update(dt);
		coder.update(dt, puck.b2body.getPosition().x);
		hud.update(dt);
		controlGoles(dt);
		activarIA(dt);

		if (hud.getMatchTimer() == 0) {
			game.setScreen(new ResultadoScreen(game, hud.getScore1(), hud.getScore2()));
			dispose();
		}

		renderer.setView(gameCam);
	}

	public void marcarGolPausa(float dt) {
		timeCount += dt;
		if (timeCount >= 1) {
			if (tiempoTrasMarcar == 3) {
				puedeMarcar = true;
				gol = false;
				cargarPosicionInicio();
			} else {
				tiempoTrasMarcar++;
			}
			timeCount = 0;
		}
	}

	private void handleInput(float dt) {
		// Control Jugardor 1
		if (p1IA == false) {
			jugador1.setAndando(false);
			if (Gdx.input.isKeyPressed(Input.Keys.W)) {
				jugador1.b2body.applyLinearImpulse(new Vector2(0, impulso), jugador1.b2body.getWorldCenter(), true);
				jugador1.moverArriba(dt);
			}
			if (Gdx.input.isKeyPressed(Input.Keys.D)) {
				jugador1.b2body.applyLinearImpulse(new Vector2(impulso, 0), jugador1.b2body.getWorldCenter(), true);
				jugador1.moverDerecha(dt);
			}
			if (Gdx.input.isKeyPressed(Input.Keys.S)) {
				jugador1.b2body.applyLinearImpulse(new Vector2(0, -impulso), jugador1.b2body.getWorldCenter(), true);
				jugador1.moverAbajo(dt);
			}
			if (Gdx.input.isKeyPressed(Input.Keys.A)) {
				jugador1.b2body.applyLinearImpulse(new Vector2(-impulso, 0), jugador1.b2body.getWorldCenter(), true);
				jugador1.moverIzquierda(dt);
			}
		}

		// Control Jugador 2
		if (p2IA == false) {
			jugador2.setAndando(false);
			if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
				jugador2.b2body.applyLinearImpulse(new Vector2(0, impulso), jugador2.b2body.getWorldCenter(), true);
				jugador2.moverArriba(dt);
			}
			if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
				jugador2.b2body.applyLinearImpulse(new Vector2(impulso, 0), jugador2.b2body.getWorldCenter(), true);
				jugador2.moverDerecha(dt);
			}
			if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
				jugador2.b2body.applyLinearImpulse(new Vector2(0, -impulso), jugador2.b2body.getWorldCenter(), true);
				jugador2.moverAbajo(dt);
			}
			if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
				jugador2.b2body.applyLinearImpulse(new Vector2(-impulso, 0), jugador2.b2body.getWorldCenter(), true);
				jugador2.moverIzquierda(dt);
			}
		}

		// Reiniciar Partido
		if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
			// cargarPartido();
		}

		// Activar IA Jugador 1
		if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
			p1IA = !p1IA;
			hud.cambiarTexto1(p1IA);
		}

		// Activar IA Jugador 2
		if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) {
			p2IA = !p2IA;
			hud.cambiarTexto2(p2IA);
		}
	}

	public void controlGoles(float dt) {
		if (puedeMarcar == true) {
			if (puck.b2body.getPosition().x < 128) {
				puedeMarcar = false;
				hud.golJugador2();
				gol = true;
				sonidoGol1.play(0.6f);
			}
			if (puck.b2body.getPosition().x > 800) {
				puedeMarcar = false;
				hud.golJugador1();
				gol = true;
				sonidoGol1.play(0.6f);
			}
		}
	}

	public void activarIA(float dt) {
		if (p1IA) {
			jugador1.activarEA(dt, puck.b2body.getPosition().x, puck.b2body.getPosition().y);
		}
		if (p2IA) {
			jugador2.activarEA(dt, puck.b2body.getPosition().x, puck.b2body.getPosition().y);
		}
	}

	@Override
	public void render(float delta) {
		update(delta);
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		renderer.render(capasInferiores);
		b2dr.render(world, gameCam.combined);

		duracionTiempo += delta;

		jugador1.setFrameActual(
				(TextureRegion) jugador1.getActualAnim().getKeyFrame(duracionTiempo, jugador1.isAndando()));
		jugador2.setFrameActual(
				(TextureRegion) jugador2.getActualAnim().getKeyFrame(duracionTiempo, jugador2.isAndando()));

		game.batch.setProjectionMatrix(gameCam.combined);
		game.batch.begin();

		game.batch.draw(jugador1.getFrameActual(), jugador1.b2body.getPosition().x - 16,
				jugador1.b2body.getPosition().y - 10);
		game.batch.draw(jugador2.getFrameActual(), jugador2.b2body.getPosition().x - 16,
				jugador2.b2body.getPosition().y - 10);
		puck.draw(game.batch);

		coder.draw(game.batch);
		if (gol) {
			font.getData().setScale(2);
			font.draw(game.batch, "¡No hagais ruido!\n Que os estoy programando", 500, 480);
		}

		game.batch.end();

		//renderer.render(capasSuperiores);

		game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
		hud.stage.draw();

	}

	@Override
	public void resize(int width, int height) {
		gamePort.update(width, height);

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {

	}

	public World getWorld() {
		return world;
	}

	public TiledMap getMap() {
		return map;
	}

	@Override
	public void dispose() {
		map.dispose();
		music.dispose();
		hud.dispose();
	}
}
