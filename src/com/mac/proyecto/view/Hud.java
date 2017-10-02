package com.mac.proyecto.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mac.proyecto.ProyectoJuego;

public class Hud implements Disposable {
	public Stage stage;
	private Viewport viewport;

	private Integer matchTimer;
	private float timeCount;
	private static int score1;
	private static int score2;

	Label countdownLabel;
	Label score1Label;
	Label score2Label;
	Label timedLabel;
	Label player1Label;
	Label player2Label;

	public Hud(SpriteBatch sb) {
		matchTimer = 120;
		timeCount = 0;
		score1 = 0;
		score2 = 0;
		viewport = new FitViewport(ProyectoJuego.V_WIDTH, ProyectoJuego.V_HEIGHT, new OrthographicCamera());
		stage = new Stage(viewport, sb);

		Table table = new Table();
		table.top();
		table.setFillParent(true);

		countdownLabel = new Label(String.format("%03d", matchTimer),
				new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		score1Label = new Label(String.format("%02d", score1), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		player2Label = new Label("Player 2", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		score2Label = new Label(String.format("%02d", score2), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		timedLabel = new Label("TIME", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		player1Label = new Label("Player 1", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

		player1Label.setFontScale(2);
		player2Label.setFontScale(2);
		score1Label.setFontScale(2);
		score2Label.setFontScale(2);
		timedLabel.setFontScale(2);
		countdownLabel.setFontScale(2);

		table.add(player1Label).expandX().padTop(10);
		table.add(timedLabel).expandX().padTop(10);
		table.add(player2Label).expandX().padTop(10);
		table.row();
		table.add(score1Label).expandX();
		table.add(countdownLabel).expandX();
		table.add(score2Label).expandX();

		stage.addActor(table);
	}

	public void update(float dt) {
		timeCount += dt;
		if (timeCount >= 1) {
			if (matchTimer > 0) {
				matchTimer--;
			}
			countdownLabel.setText(String.format("%03d", matchTimer));
			timeCount = 0;
		}
	}
	
	public int getScore1(){
		return score1;
	}
	
	public int getScore2(){
		return score2;
	}
	
	public Integer getMatchTimer(){
		return matchTimer;
	}

	public void cambiarTexto1(boolean p1IA) {
		if (p1IA) {
			player1Label.setText("CPU");
		} else {
			player1Label.setText("Player 1");
		}
	}

	public void cambiarTexto2(boolean p2IA) {
		if (p2IA) {
			player2Label.setText("CPU");
		} else {
			player2Label.setText("Player 2");
		}
	}

	public void golJugador1() {
		score1++;
		score1Label.setText(String.format("%02d", score1));
	}

	public void golJugador2() {
		score2++;
		score2Label.setText(String.format("%02d", score2));
	}

	@Override
	public void dispose() {
		stage.dispose();
	}

}
