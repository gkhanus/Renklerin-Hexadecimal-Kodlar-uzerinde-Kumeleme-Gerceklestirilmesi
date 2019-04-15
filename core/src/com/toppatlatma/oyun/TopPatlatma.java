package com.toppatlatma.oyun;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import java.awt.TextComponent;
import java.util.Random;
import java.awt.Rectangle;


public class TopPatlatma extends ApplicationAdapter implements InputProcessor {

	SpriteBatch batch;
	Texture img;
	TextureRegion textureRegion;

	static Vector3[][] rgb  = new Vector3[6][6];
	static Vector2[][] koordinat = new Vector2[6][6];
	private float[][][] renkler = new float[5][145][3];
	private Texture[][] kareTexture = new Texture[6][6];
	private Pixmap[][] kare = new Pixmap[6][6];


	Kmeans filenesne = new Kmeans();

	public void create () {
		Gdx.input.setInputProcessor(this);
		batch = new SpriteBatch();
		img = new Texture("ekran6x6.png");
		//textureRegion.flip(false,true);
		filenesne.DosyaOku();
		filenesne.merkez(3);
		System.out.println("***************************");
		filenesne.merkez(4);
		System.out.println("***************************");
		filenesne.merkez(5);

		renkler = Kmeans.merkez(3);

		float a=0,b=0,c=0;
		int kume, eleman;
		Random rand = new Random();

		for(int i=0; i<6; i++){
			for(int j=0; j<6; j++){

				rgb[i][j] = new Vector3();
				koordinat[i][j] = new Vector2();

				kume = rand.nextInt(3);
				eleman = rand.nextInt(Kmeans.elemansayi[kume]);

				rgb[i][j].set(renkler[kume][eleman][0],renkler[kume][eleman][1],renkler[kume][eleman][2]);

				koordinat[i][j].set(66-32 + j * 70, 483-23 - i * 53);

				kare[i][j] = new Pixmap(63,49, Pixmap.Format.RGBA8888);
				if(kume == 0) {
					kare[i][j].setColor(rgb[i][j].x / 255f, rgb[i][j].y / 255f, rgb[i][j].z / 255f, 1);
					kare[i][j].fillRectangle(5, 10, 55, 30);

					kareTexture[i][j] = new Texture(kare[i][j]);
				}
				 if(kume == 1){
					kare[i][j].setColor(rgb[i][j].x / 255f, rgb[i][j].y / 255f, rgb[i][j].z / 255f, 1);
					kare[i][j].fillCircle(30,25, 22);
					 kareTexture[i][j] = new Texture(kare[i][j]);
				}
				 if(kume == 2) {
					kare[i][j].setColor(rgb[i][j].x / 255f, rgb[i][j].y / 255f, rgb[i][j].z / 255f, 1);
					 kare[i][j].fillTriangle(0, 5, 31, 43, 63, 5);
					 kareTexture[i][j] = new Texture(kare[i][j]);
				}
			}
		}

	}

	public void render () {

		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img, 0, 0);
		for(int i=0; i<6; i++) {
			for (int j = 0; j < 6; j++) {

				batch.draw(kareTexture[i][j], koordinat[i][j].x, koordinat[i][j].y);

			}
		}
				batch.end();
	}

	@Override
	public void dispose() {

		img.dispose();

		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 6; j++) {

				kareTexture[i][j].dispose();
			}
		}

	}

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {

		int xkord = Gdx.input.getX();
		int ykord = Gdx.input.getY();
		int basX=38, basY=231;
		boolean dokunma = false;

		int sutun = (xkord - basX) / 66;
		int satir = (ykord - basY) / 50;
		//System.out.println(xkord+" "+ykord);
		int top1satir = -1,  top1sutun =-1,top2satir=-1,top2sutun=-1;

		if(xkord>=38 & xkord<=442 & ykord>=231 & ykord<=532 ) {

			if (!dokunma & (top1satir != satir || top1sutun != sutun)) {
				top1satir = satir;
				top1sutun = sutun;
				dokunma = true;
				System.out.println(satir + " " + sutun);
			}

			if (dokunma & (top2satir != satir || top2sutun != sutun)) {

				top2satir = satir;
				top2sutun = sutun;
				dokunma=false;
				System.out.println(satir + " " + sutun);

			}
		}




		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
}

