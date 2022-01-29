package me.rainma22.gdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;
import java.util.Map;

import static me.rainma22.gdx.Utils.drawButton;
import static me.rainma22.gdx.Utils.drawText;

public class StartScreen {
    Main main;
    String[] options = new String[]{"Exit","Options","Start"};
    ArrayList<Rectangle> fontColliders=new ArrayList<>(3);
    int selected = 0;
    public StartScreen(Main main){
        this.main=main;
        for (int i = 0; i < options.length; i++) {
            fontColliders.add(new Rectangle(main.prefWidth/13,main.prefHeight/(options.length+2)*(1+i), main.prefWidth/4,main.prefHeight/7));
        }
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
        drawText(width/13*2,height/14*13,Color.WHITE,"Split!", font, 2f,batch,false);
        for (int i = 0; i < options.length; i++) {
            if(selected==i){
                drawButton(width/13,height/(options.length+2)*(1+i),width/4,height/7, new Color(47f / 256, 214f / 256, 211f / 256, 1),Color.CLEAR,options[i],font,1.5f,batch,shapeRenderer,false);
            }else{
                drawButton(width/13,height/(options.length+2)*(1+i),width/4,height/7, Color.WHITE,Color.CLEAR,options[i],font,1.5f,batch,shapeRenderer,false);
            }
        }
    }
    public boolean mouseMoved(float screenX,float screenY){
        Vector3 mousePos = new Vector3(screenX, screenY, 0);
        mousePos = main.camera.unproject(mousePos);
        Rectangle mouse = new Rectangle(mousePos.x, mousePos.y, 100, 100);
        for (int i = 0;i< options.length;i++) {
            Rectangle rect = fontColliders.get(i);
            if (mouse.overlaps(rect)) selected = i;
            selected %= 3;
        }
        return true;
    }
    public boolean keyDown(int keycode) {
        if(keycode== Input.Keys.ENTER){
            switch (options[selected]){
                case "Exit":
                    Gdx.app.exit();
                    break;
                case "Options":
                    break;
                case "Start":
                    main.stage=1;
            }
        }
        return false;
    }
}
