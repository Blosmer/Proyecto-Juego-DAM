package com.mac.proyecto.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mac.proyecto.ProyectoJuego;

public class ResultadoScreen implements Screen {
	private Viewport viewport;
	private Stage stage;
	private Game game;
	private Music music;
	private int score1, score2;

	public ResultadoScreen(ProyectoJuego game, int score1, int score2) {
		this.game = game;
		this.score1 = score1;
		this.score2 = score2;

		viewport = new FitViewport(ProyectoJuego.V_WIDTH, ProyectoJuego.V_HEIGHT, new OrthographicCamera());
		stage = new Stage(viewport, ((ProyectoJuego) game).batch);

		Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.WHITE);

		Table table = new Table();
		table.center();
		table.setFillParent(true);

		Label resultadoLabel;
		if (score1 > score2) {
			resultadoLabel = new Label("Player 1 WINS", font);
			cargarMusicaVictoria();
		} else if (score1 < score2) {
			resultadoLabel = new Label("Player 2 WINS", font);
			cargarMusicaVictoria();
		}else{
			resultadoLabel = new Label("Where have all the good men gone?", font);
			cargarMusicaEmpate();
		}
		Label playAgainLabel = new Label("Press SPACE to rematch", font);
		
		resultadoLabel.setFontScale(2);
		
		table.add(resultadoLabel).expandX();
		table.row();
		table.add(playAgainLabel).expandX().padTop(10f);

		stage.addActor(table);

		
	}

	public void cargarMusicaVictoria() {
		music = Gdx.audio.newMusic(Gdx.files.internal("audio/We Are The Champions 8Bit.mp3"));
		music.setVolume(0.5f);
		music.play();
		music.setLooping(true);
	}
	
	public void cargarMusicaEmpate() {
		music = Gdx.audio.newMusic(Gdx.files.internal("audio/Holding Out For A Hero - 8Bit.mp3"));
		music.setVolume(0.5f);
		music.play();
		music.setLooping(true);
	}
	

	@Override
	public void show() {

	}

	@Override
	public void render(float delta) {
		if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
			game.setScreen(new PistaHielo((ProyectoJuego) game));
			dispose();
		}

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		stage.draw();
	}

	// public void update(float dt) {
	// handleInput(dt);
	// }
	//
	// private void handleInput(float dt) {
	//
	// }

	@Override
	public void resize(int width, int height) {

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

	@Override
	public void dispose() {
		music.dispose();
		stage.dispose();
	}

}
