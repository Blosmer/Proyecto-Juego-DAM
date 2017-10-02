package com.mac.proyecto;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mac.proyecto.screens.SeleccionPersonajeScreen;

public class ProyectoJuego extends Game {

	public static final int V_WIDTH = 900;
	public static final int V_HEIGHT = 700;
	public static final float PPM = 100;
	public static SpriteBatch batch;

	public static SpriteBatch getBatch() {
		return batch;
	}

	public static void setBatch(SpriteBatch batch) {
		ProyectoJuego.batch = batch;
	}


	@Override
	public void create() {
		batch = new SpriteBatch();
		//screen = new PistaHielo(this);
		screen = new SeleccionPersonajeScreen(this);
		setScreen(screen);
	}
}
