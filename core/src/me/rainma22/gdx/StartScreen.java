package me.rainma22.gdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;

import static me.rainma22.gdx.Utils.drawText;

public class StartScreen {
    Main main;
    public StartScreen(Main main){
        this.main=main;
    }
    public void draw(){
        ShapeRenderer shapeRenderer= main.shapeRenderer;
        float width= main.prefWidth;float height= main.prefHeight;
        SpriteBatch batch=main.batch;
        BitmapFont font = main.font;
        ArrayList<Rectangle> stars=main.stars;
        Gdx.gl.glEnable(Gdx.gl.GL_BLEND);
        Gdx.gl.glBlendFunc(Gdx.gl.GL_SRC_ALPHA, Gdx.gl.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.WHITE);
        for (Rectangle r : stars) {
            shapeRenderer.rect(r.getX(), r.getY(), r.getWidth(), r.getHeight());
        }
        shapeRenderer.setColor(new Color(0,0,18/255f,200/255f));
        shapeRenderer.rect(width/13,0,width/4,height);
        shapeRenderer.end();
        Gdx.gl.glDisable(Gdx.gl.GL_BLEND);
        batch.begin();
        drawText(width/13*2,height/14*13,Color.WHITE,"Split!", font, 2f,batch,false);
    }
}
