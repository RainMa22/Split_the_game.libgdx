package me.rainma22.gdx;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Main extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img,star;
	BitmapFont font;
	GameObject2D plane1,plane2;
	long frame;
	float speed;
	boolean up,paused;
	Texture pauseBackground;
	OrthographicCamera camera;
	public ArrayList<Rectangle> stars;
	@Override
	public void create () {
		camera=new OrthographicCamera(1600,900);
		speed=1;
		batch = new SpriteBatch();
		img = new Texture("Shooter_SpriteSheet.png");
		Pixmap pixmap=new Pixmap(1,1,Format.RGBA4444);
		pixmap.setColor(1, 1, 1, 1);
		pixmap.fill();
		star=new Texture(pixmap);
		stars=new ArrayList<Rectangle>(500);
		for(int i=0;i<500;i++) {
			float size=(float)(Math.random()*5+1);
			stars.add(new Rectangle((float)(Math.random()*1600+0.5f), (float)(Math.random()*900+0.5f), size,size));
		}
		pixmap.setColor(0,0,0,0.58823529411f);
		pixmap.fill();
		pauseBackground=new Texture(pixmap);
		plane1= new GameObject2D((int)(1600/10d-(17d/2)), (int)(900/2d-(17/2d)), new TextureRegion(img, 0, 17*2-1, 17*3, 17), 17, 17,3,5);
		plane2=new GameObject2D((int)(1600/10d-(17d/2)), (int)(900/2d-(17/2d)), new TextureRegion(img, 17*3-1, 17*2-1, 17*3, 17), 17, 17,3,5);
		frame=0;
		paused=false;
		font=new BitmapFont(Gdx.files.internal("fonts/ubuntu.fnt"));
	}

	@Override
	public void render () {
		camera.update();
		batch.setTransformMatrix(camera.view);
		batch.begin();
		Gdx.gl.glClearColor(0, 0, 0.11764705882f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		for (int i = 0; i < stars.size(); i++) {
			Rectangle r=stars.get(i);
			batch.draw(star, r.getX(), r.getY(), r.getWidth(), r.getHeight());
		}
		batch.draw(plane1.getPreviousTexture(frame), plane1.x, plane1.y,plane1.width/2,plane1.height/2,plane1.width,plane1.height,plane1.scale,plane1.scale,-90f);
		batch.draw(plane2.getPreviousTexture(frame), plane2.x, plane2.y,plane2.width/2,plane2.height/2,plane2.width,plane2.height,plane2.scale,plane2.scale,-90f);
		if (!paused) {
		int t=(int)(5*speed+0.5f);
		if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
			up=!up;
		}
		if (up) {
			plane1.y+=t;
			plane2.y-=t;
			up=!((plane1.y+(plane1.height*plane1.scale)/2f)+1>=900);
			}else {
				plane1.y-=t;
				plane2.y+=t;
				up=!!((plane2.y+(plane2.height*plane2.scale)/2f)+1>=900);
			}
		for (int i = 0; i < stars.size(); i++) {
			Rectangle r=stars.get(i);
			//batch.draw(star, r.getX(), r.getY(), r.getWidth(), r.getHeight());
			r.x-=r.getWidth();
			if (r.x<=0) {
				r.x=1600;
			}
			stars.set(i, r);
		}
		
		frame++;
		}else {
			batch.draw(pauseBackground, 0, 0, 1600, 900);
			GlyphLayout g=new GlyphLayout(font, "Paused");
			font.setColor(Color.WHITE);
			font.draw(batch, "Paused", 1600/2-g.width/2, 900/5*4-g.height/2);
			}
		if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
			paused=!paused;
		}
		batch.end();
		
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
