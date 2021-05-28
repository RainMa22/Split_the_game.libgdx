package me.rainma22.gdx;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Main extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img,star,asteroid;
	BitmapFont font;
	GameObject2D plane1,plane2;
	long frame;
	float speed,difficulty,floatCounter,score;
	boolean up,paused,died;
	Texture pauseBackground;
	OrthographicCamera camera;
	ArrayList<Rectangle> stars; ArrayList<GameObject2D> obstacles;
	@Override
	public void create () {
		camera=new OrthographicCamera(1600f,900f);
		camera.setToOrtho(false,1600f,900f);
		camera.lookAt(800, 450,0);
		//camera=new OrthographicCamera(MathUtils.round(2*(1600f/Gdx.graphics.getWidth())), MathUtils.round(2*(900f/Gdx.graphics.getHeight())));
		//camera=new OrthographicCamera();
		camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
		//resize(MathUtils.round(1600*(1600f/Gdx.graphics.getWidth())), MathUtils.round(900*(900f/Gdx.graphics.getHeight())));
		speed=4;
		batch = new SpriteBatch();
		img = new Texture("Shooter_SpriteSheet.png");
		asteroid=new Texture("Asteroid.png");
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
		obstacles=new ArrayList<>();
		plane1= new GameObject2D((1600/12f-(17f/2)), (900/2f-(17/2f)), new TextureRegion(img, 0, 17*2-1, 17*3, 17), 17, 17,3,5f);
		plane2=new GameObject2D((1600/12f-(17f/2)), (900/2f-(17/2f)), new TextureRegion(img, 17*3-1, 17*2-1, 17*3, 17), 17, 17,3,5);
		frame=0;
		difficulty=.5f;
		floatCounter=1;
		score=0;
		paused=false;died=false;
		font=new BitmapFont(Gdx.files.internal("Fonts/Ubuntu.fnt"));
	}

	@Override
	public void render () {
		camera.update();
		//resize(MathUtils.round(1600*(1600f/Gdx.graphics.getWidth())), MathUtils.round(900*(900f/Gdx.graphics.getHeight())));
		//resize((int)frame, (int)frame/16*9);
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		Gdx.gl.glClearColor(0, 0, 0.11764705882f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		for (int i = 0; i < stars.size(); i++) {
			Rectangle r=stars.get(i);
			batch.draw(star, r.getX(), r.getY(), r.getWidth(), r.getHeight());
		}
		batch.draw(plane1.getPreviousTexture(frame), plane1.x, plane1.y,plane1.width/2,plane1.height/2,plane1.width,plane1.height,plane1.scale,plane1.scale,-90f);
		batch.draw(plane2.getPreviousTexture(frame), plane2.x, plane2.y,plane2.width/2,plane2.height/2,plane2.width,plane2.height,plane2.scale,plane2.scale,-90f);
		GlyphLayout g=new GlyphLayout(font, Integer.toString((int)score));
		font.setColor(Color.WHITE);
		font.draw(batch, Integer.toString((int)score), 1600/2-g.width/2, 900/14*13-g.height/2);
		if (!paused) {
		int t=(int)(5*speed+0.5f);
		if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
			paused=true;
		}else if (Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY)) {
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
			r.x-=r.getWidth()*speed/2;
			if (r.x<=0) {
				r.x=1600;
			}
			stars.set(i, r);
		}
		frame++;
		floatCounter+=0.00002777777f;
		difficulty=.5f*floatCounter;
		speed=difficulty*4;
		score+=difficulty;
		
		}else {
			batch.draw(pauseBackground, 0, 0, 1600, 900);
			g=new GlyphLayout(font, "Paused");
			font.setColor(Color.WHITE);
			font.draw(batch, "Paused", 1600/2-g.width/2, 900/5*4-g.height/2);
			if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
				paused=false;
			}
			}
		
		batch.end();
		
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
		star.dispose();
		asteroid.dispose();
		pauseBackground.dispose();
	}
}
