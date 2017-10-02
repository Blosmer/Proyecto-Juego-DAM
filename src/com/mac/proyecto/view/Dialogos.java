package com.mac.proyecto.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mac.proyecto.ProyectoJuego;

public class Dialogos extends Actor implements Disposable{
	public Stage stage;
	private Viewport viewport;
	private Label cuadroDialogo;
	
	public Dialogos(SpriteBatch sb){
		cuadroDialogo = new Label("Hola", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		viewport = new FitViewport(ProyectoJuego.V_WIDTH, ProyectoJuego.V_HEIGHT, new OrthographicCamera());
		stage = new Stage(viewport, sb);
		
		Table table = new Table();
		table.bottom();
		table.setFillParent(true);
		
		table.add(cuadroDialogo);
		
		stage.addActor(table);
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		
	}

	@Override
	public void dispose() {
		stage.dispose();
		
	}
}
