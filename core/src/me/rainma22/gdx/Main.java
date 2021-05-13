package me.rainma22.gdx;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Main extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img,star;
	GameObject2D plane1,plane2;
	long frame;
	OrthographicCamera camera;
	public ArrayList<Rectangle> stars;
	@Override
	public void create () {
		camera=new OrthographicCamera(1600,900);
		batch = new SpriteBatch();
		img = new Texture("Shooter_SpriteSheet.png");
		Pixmap pixmap=new Pixmap(1,1,Format.RGBA4444);
		pixmap.setColor(1, 1, 1, 1);
		pixmap.fillRectangle(0, 0, 1, 1);
		star=new Texture(pixmap);
		stars=new ArrayList<Rectangle>(500);
		for(int i=0;i<500;i++) {
			float size=(float)(Math.random()*5+1);
			stars.add(new Rectangle((float)(Math.random()*1600+0.5f), (float)(Math.random()*900+0.5f), size,size));
		}
		plane1= new GameObject2D((int)(1600/10d-(17d/2)), (int)(900/2d-(17/2d)), new TextureRegion(img, 0, 17*2-1, 17*3, 17), 17, 17,3,5);
		plane2=new GameObject2D((int)(1600/10d-(17d/2)), (int)(900/2d-(17/2d)), new TextureRegion(img, 17*3-1, 17*2-1, 17*3, 17), 17, 17,3,5);
		frame=0;
		
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		batch.setTransformMatrix(camera.view);
		batch.begin();
		for (int i = 0; i < stars.size(); i++) {
			Rectangle r=stars.get(i);
			batch.draw(star, r.getX(), r.getY(), r.getWidth(), r.getHeight());
			r.x-=r.getWidth();
			if (r.x<=0) {
				r.x=1600;
			}
			stars.set(i, r);
		}
		batch.draw(plane1.getPreviousTexture(frame), plane1.x, plane1.y,plane1.width/2,plane1.height/2,plane1.width,plane1.height,plane1.scale,plane1.scale,-90f);
		batch.draw(plane2.getPreviousTexture(frame), plane2.x, plane2.y,plane2.width/2,plane2.height/2,plane2.width,plane2.height,plane2.scale,plane2.scale,-90f);
		plane2.y-=2;
		batch.end();
		frame++;
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
