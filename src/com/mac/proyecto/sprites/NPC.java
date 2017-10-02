package com.mac.proyecto.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class NPC extends Sprite {

	private Texture npcTexture;
	private TextureRegion npcRegion;
	private TextureRegion[] npcFrames;
	private Animation npcAnimation;
	private Animation leftAni;
	private Animation rightAni;
	private TextureRegion[][] temp;

	public NPC(String textureRuta, int spritesPorAncho, int spritesPorAlto, float x, float y) {
		npcTexture = new Texture(textureRuta);
		npcRegion = new TextureRegion(npcTexture, npcTexture.getWidth() / spritesPorAncho,
				npcTexture.getHeight() / spritesPorAlto);
//		TextureRegion[][] temp = npcRegion.split(npcTexture.getWidth() / spritesPorAncho,
//				npcTexture.getHeight() / spritesPorAlto);
//		npcFrames = new TextureRegion[temp.length * temp[0].length];
//
//		int indice = 0;
//		for (int i = 0; i < temp.length; i++) {
//			//for (int j = 0; j < temp[2].length; j++) {
//				npcFrames[indice++] = temp[i][0];
//			//}
//		}

		// TextureRegion[] derecha = npcRegion.split(npcTexture.getWidth() /
		// spritesPorAncho);

		temp = npcRegion.split(npcTexture.getWidth() / spritesPorAncho,
				npcTexture.getHeight() / spritesPorAlto);


		setBounds(0, 0, 72, 72);
		//setRegion(npcRegion);
		setPosition(x, y);
	}

	public void update(float dt, float xPuck) {
		if(xPuck>464){
			setRegion(temp[0][0]);
		}else{
			setRegion(temp[0][0]);
		}
	}
}
