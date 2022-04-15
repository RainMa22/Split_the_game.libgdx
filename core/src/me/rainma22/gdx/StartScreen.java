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

import static me.rainma22.gdx.Utils.*;

public class StartScreen {
    Main main;
    String[] options = new String[]{"Exit","Start"};
    ArrayList<Button> buttons=new ArrayList<>();

    public StartScreen(Main main){
        this.main=main;
        for (int i = 0; i < options.length; i++) {
            Button btn=new Button(main.prefWidth/13,main.prefHeight/(options.length+2)*(1+i), main.prefWidth/4,main.prefHeight/(options.length+5),Color.WHITE,Color.CLEAR,
                    new Color(47f / 256, 214f / 256, 211f / 256, 1),Color.CLEAR,options[i], main.font, 1.5f,false);
            final Main finalMain=main;
            final int finalI=i;
            btn.setOnClickAction(new Runnable() {
                @Override
                public void run() {
                    switch (options[finalI]){
                        case "Exit":
                            Gdx.app.exit();
                            break;
                        case "Options":
                            break;
                        case "Start":
                            finalMain.restart();
                }
            }
            });
            buttons.add(btn);
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
            buttons.get(i).draw(batch,shapeRenderer);
        }
    }
    public boolean mouseMoved(float screenX,float screenY){
        Rectangle mouse=getMouseRect(screenX,screenY,main.camera);
        boolean prev=false;
        for (int i = 0; i < options.length; i++) {
            if (!prev) prev=buttons.get(i).mouseMoved(mouse);
            else buttons.get(i).selected=false;
        }
        return true;
    }
    public boolean keyDown(int keycode) {
        if (keycode== Input.Keys.ENTER){
            for (int i = 0; i < options.length; i++) {
                buttons.get(i).onInteract();
            }
            return true;
        }
        return false;
    }
}
