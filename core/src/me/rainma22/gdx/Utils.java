package me.rainma22.gdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Utils {
    public static void drawText(float x, float y, Color color,String text, BitmapFont font,float scale, SpriteBatch batch, boolean centered){
        if (!batch.isDrawing()){batch.begin();}
        font.setColor(color);font.getData().setScale(scale,scale);
        GlyphLayout glyphLayout=new GlyphLayout(font,text);
        if(centered){
            font.draw(batch,text,x- glyphLayout.width/2f,y- glyphLayout.height/2f);
        }else{
            font.draw(batch,text,x,y);
        }
        batch.end();
    }
    public static void drawButton(float x, float y,float width,float height, Color foregroundColor, Color backgroundColor, String text, BitmapFont font, float scale, SpriteBatch batch, ShapeRenderer shapeRenderer, boolean centered){
        Gdx.gl.glEnable(Gdx.gl.GL_BLEND);
        Gdx.gl.glBlendFunc(Gdx.gl.GL_SRC_ALPHA, Gdx.gl.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.setAutoShapeType(true);
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(backgroundColor);
        if(centered){
            shapeRenderer.rect(x-width/2,y-height/2,width,height);
        }else {
            shapeRenderer.rect(x,y,width,height);
        }
        shapeRenderer.end();
        drawText(x+width/2,y+height/2,foregroundColor,text,font,scale,batch,true);
        Gdx.gl.glDisable(Gdx.gl.GL_BLEND);
    }
}
