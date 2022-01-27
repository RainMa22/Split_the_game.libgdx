package me.rainma22.gdx;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

public class Utils {
    public static void drawText(float x, float y, Color color,String text, BitmapFont font,float scale, SpriteBatch batch, boolean centered){
        font.setColor(color);font.getData().setScale(scale,scale);
        GlyphLayout glyphLayout=new GlyphLayout(font,text);
        if(centered){
            font.draw(batch,text,x- glyphLayout.width/2f,y- glyphLayout.height/2f);
        }else{
            font.draw(batch,text,x,y);
        }
    }
}
