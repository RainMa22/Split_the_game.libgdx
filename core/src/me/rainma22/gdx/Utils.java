package me.rainma22.gdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

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

    public static Rectangle getMouseRect(float screenX, float screenY, Camera camera){
        Vector3 mousePos = new Vector3(screenX, screenY, 0);
        mousePos = camera.unproject(mousePos);
        return new Rectangle(mousePos.x, mousePos.y, 100, 100);
    }
}
interface interactable{
    public void setBounds(float x,float y, float width, float height);
    public void draw(SpriteBatch batch,ShapeRenderer shapeRenderer);
    public boolean mouseMoved(Rectangle mouseRect);
    public void setOnClickAction(Runnable r);
    public void onInteract();
}
class Button implements interactable{
    float x; float y;float width;float height; Color foregroundColor; Color backgroundColor; String text; BitmapFont font; float scale;boolean centered;Runnable onClickAction;
    boolean selected;
    Color selectedForegroundColor,selectedBackgroundColor;
    public Button(){ }
    public void setText(String text){this.text=text;}
    public void setColors(Color foregroundColor, Color backgroundColor,Color selectedForegroundColor, Color selectedBackgroundColor){
        this.foregroundColor=foregroundColor;
        this.backgroundColor=backgroundColor;
        this.selectedForegroundColor=selectedForegroundColor;
        this.selectedBackgroundColor=selectedBackgroundColor;
    }
    public Button(float x, float y,float width,float height, Color foregroundColor, Color backgroundColor,Color selectedForegroundColor,Color selectedBackgroundColor, String text, BitmapFont font, float scale, boolean centered){
        setBounds(x, y, width, height);
        this.foregroundColor=foregroundColor;
        this.backgroundColor=backgroundColor;
        this.text=text;
        this.font=font;
        this.scale=scale;
        this.centered=centered;
        this.selectedForegroundColor=selectedForegroundColor;
        this.selectedBackgroundColor=selectedBackgroundColor;
        selected=false;
    }
    public void setBounds(float x,float y, float width, float height){
        this.x=x;this.y=y;this.width=width;this.height=height;
    }
    public void draw(SpriteBatch batch,ShapeRenderer shapeRenderer){
        Gdx.gl.glEnable(Gdx.gl.GL_BLEND);
        Gdx.gl.glBlendFunc(Gdx.gl.GL_SRC_ALPHA, Gdx.gl.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.setAutoShapeType(true);
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        if(selected) shapeRenderer.setColor(selectedBackgroundColor);
        else shapeRenderer.setColor(backgroundColor);
        if(centered){
            shapeRenderer.rect(x-width/2,y-height/2,width,height);
        }else {
            shapeRenderer.rect(x,y,width,height);
        }
        shapeRenderer.end();
        float textX,textY;
        if (centered){textX=x;textY=y;}
        else{textX=x+width/2;textY=y+height/2;}
        if(selected) Utils.drawText(textX,textY,selectedForegroundColor,text,font,scale,batch,true);
        else Utils.drawText(textX,textY,foregroundColor,text,font,scale,batch,true);
        Gdx.gl.glDisable(Gdx.gl.GL_BLEND);
    }
    public boolean mouseMoved(Rectangle mouseRect){
        Rectangle rect=new Rectangle(x, y, width, height);
        selected= mouseRect.overlaps(rect);
        return selected;
    }
    public void setOnClickAction(Runnable r){
        onClickAction=r;
    }
    public void onInteract(){
        if (onClickAction!=null&&selected) onClickAction.run();
    }
}
class Window implements interactable{
    float x,y,width,height;
    Color backgroundColor;
    Color outline= Color.CLEAR;
    String text;
    public Window(){}
    public void setColors(Color backgroundColor, Color outline){
        this.backgroundColor=backgroundColor;this.outline=outline;
    }
    @Override
    public void setBounds(float x, float y, float width, float height) {
        this.x=x;this.y=y;this.width=width;this.height=height;
    }

    @Override
    public void draw(SpriteBatch batch, ShapeRenderer shapeRenderer) {

    }

    @Override
    public boolean mouseMoved(Rectangle mouseRect) {
        return false;
    }

    @Override
    public void setOnClickAction(Runnable r) {

    }

    @Override
    public void onInteract() {

    }
}