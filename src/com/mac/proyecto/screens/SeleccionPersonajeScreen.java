package com.mac.proyecto.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mac.proyecto.ProyectoJuego;

public class SeleccionPersonajeScreen extends PlayScreen {
	private Viewport viewport;
	private Stage stage;
	private Music music;
	private int personajeActual1, personajeActual2;
	private Texture personaje;

	public SeleccionPersonajeScreen(ProyectoJuego game) {
		super(game);
		personajeActual1 = 1;
		viewport = new FitViewport(ProyectoJuego.V_WIDTH, ProyectoJuego.V_HEIGHT, new OrthographicCamera());
		stage = new Stage(viewport, ((ProyectoJuego) game).batch);

		Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.WHITE);

		Table table = new Table();
		table.center();
		table.setFillParent(true);

		Label selectorIZQ = new Label("<<<", font);
		Label selectorDER = new Label(">>>", font);
		selectorIZQ.setFontScale(4);
		selectorDER.setFontScale(4);

		prepareUi();

	}

	public void prepareUi() {
		TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
		buttonStyle.font = new BitmapFont();
		buttonStyle.fontColor = Color.WHITE;
		TextButton startBtn = new TextButton("SELECT", buttonStyle);
		startBtn.setPosition((stage.getWidth() - startBtn.getWidth()) / 2, stage.getHeight() / 6);
		startBtn.addListener(new ClickListener() {
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				dispose();
				game.setScreen(new PistaHielo((ProyectoJuego) game));
			}
		});
		startBtn.getLabel().setFontScale(4);
		stage.addActor(startBtn);

		TextButton nextBtn = new TextButton(">>", buttonStyle);
		nextBtn.setPosition(stage.getWidth() * 5 / 6 - nextBtn.getWidth() / 2, stage.getHeight() / 2);
		nextBtn.getLabel().setFontScale(4);
		nextBtn.addListener(new ClickListener() {
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				personajeActual1++;
				System.out.println(personajeActual1);
				stage.clear();
				prepareUi();
			}
		});

		stage.addActor(nextBtn);

		TextButton prevBtn = new TextButton("<<", buttonStyle);
		prevBtn.setPosition(stage.getWidth() / 6 - prevBtn.getWidth() / 2, stage.getHeight() / 2);
		prevBtn.getLabel().setFontScale(4);
		nextBtn.addListener(new ClickListener() {
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				personajeActual1--;
				System.out.println(personajeActual1);
				stage.clear();
				prepareUi();
			}
		});
		stage.addActor(prevBtn);
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}
	
	public void update(float dt){
		controlSelector(dt);
		
		switch (personajeActual1) {
		case 1:personaje = new Texture("imagenes/hanSoloFoto.jpg");
			break;
		case 2:personaje = new Texture("imagenes/LandoFoto.jpg");
			break;
		}

	}
	
	public void controlSelector(float dt){
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			if(personajeActual1 < 3)
			personajeActual1++;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			if(personajeActual1 > 0)
			personajeActual1--;
		}
	}

	@Override
	public void render(float delta) {
		update(delta);
		if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
			game.setScreen(new PistaHielo((ProyectoJuego) game));
			dispose();
		}
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		game.getBatch().begin();
		game.getBatch().draw(personaje, 250, 180, 400, 400);
		game.getBatch().end();

		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
