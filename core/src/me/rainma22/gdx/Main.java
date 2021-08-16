package me.rainma22.gdx;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Timer;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class Main extends ApplicationAdapter implements InputProcessor {
    SpriteBatch batch;
    Texture img, star, asteroid;
    BitmapFont font;
    GameObject2D plane1, plane2;
    long frame;
    float speed, difficulty, floatCounter, score, counter1;
    boolean up, paused, died;
    Texture pauseBackground;
    GameObject2D explosion;
    OrthographicCamera camera;
    ArrayList<Rectangle> stars;
    ArrayList<GameObject2D> obstacles;
    int selected;
    Map<String, Rectangle> fontColliders;
    Music[] musics;
    Music sfx;
    int currentMusic;

    @Override
    public void create() {
        camera = new OrthographicCamera(1920f, 1080f);
        camera.setToOrtho(false, 1920f, 1080f);
        //camera=new OrthographicCamera(MathUtils.round(2*(1920f/Gdx.graphics.getWidth())), MathUtils.round(2*(1080f/Gdx.graphics.getHeight())));
        //camera=new OrthographicCamera();
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        //resize(MathUtils.round(1920*(1920f/Gdx.graphics.getWidth())), MathUtils.round(1080*(1080f/Gdx.graphics.getHeight())));
        batch = new SpriteBatch();
        img = new Texture("Shooter_SpriteSheet.png");
        asteroid = new Texture("Asteroid.png");
        explosion = new GameObject2D(0,0, new TextureRegion(new Texture("explosion.png")), 32, 32, 1, 5f);
        Pixmap pixmap = new Pixmap(1, 1, Format.RGBA4444);
        pixmap.setColor(1, 1, 1, 1);
        pixmap.fill();
        star = new Texture(pixmap);
        stars = new ArrayList<Rectangle>(500);
        for (int i = 0; i < 500; i++) {
            float size = MathUtils.random(1, 4);
            stars.add(new Rectangle((float) (Math.random() * 1920 + 0.5f), (float) (Math.random() * 1080 + 0.5f), size, size));
        }
        pixmap.setColor(0, 0, 0, 0.58823529411f);
        pixmap.fill();
        pauseBackground = new Texture(pixmap);
        obstacles = new ArrayList<>();
        plane1 = new GameObject2D((1920 / 15f - (17f / 2)), (1080 / 2f - (17 / 2f)), new TextureRegion(img, 0, 17 * 2 - 1, 17 * 3, 17), 17, 17, 3, 5f);
        plane2 = new GameObject2D((1920 / 15f - (17f / 2)), (1080 / 2f - (17 / 2f)), new TextureRegion(img, 17 * 3 - 1, 17 * 2 - 1, 17 * 3, 17), 17, 17, 3, 5f);
        frame = 0;
        difficulty = 5f;
        speed = 5f * difficulty;
        floatCounter = 1;
        counter1 = 0;
        score = 0;
        paused = false;
        died = false;
        up = true;
        font = new BitmapFont(Gdx.files.internal("Fonts/Ubuntu.fnt"));
        selected = 0;
        fontColliders = new LinkedHashMap<>(0);
        GlyphLayout g = new GlyphLayout(font, "Resume");
        fontColliders.put("Resume", new Rectangle(1920 / 2 - g.width / 2, 1080 / 6 * 5 - g.height / 2, g.width, g.height));
        g = new GlyphLayout(font, "Restart");
        fontColliders.put("Restart", new Rectangle(1920 / 2 - g.width / 2, 1080 / 6 * 3 - g.height / 2, g.width, g.height));
        g = new GlyphLayout(font, "Exit");
        fontColliders.put("Exit", new Rectangle(1920 / 2 - g.width / 2, 1080 / 6 * 1 - g.height / 2, g.width, g.height));
        sfx= Gdx.audio.newMusic(Gdx.files.internal("SFX/0.mp3"));
        FileHandle[] handles = Gdx.files.internal("MP3").list();
        musics= new Music[handles.length];
        for (int i = 0; i < handles.length; i++) {
            musics[i]=Gdx.audio.newMusic(handles[i]);
        }
        currentMusic = 0;
        musics[currentMusic].play();
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render() {
        if (!musics[currentMusic].isPlaying()) Timer.post(new Timer.Task() {
            @Override
            public void run() {
                musics[currentMusic].stop();
                musics[currentMusic].setPosition(0);
                currentMusic++;
                musics[currentMusic].play();
            }
        });
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        Gdx.gl.glClearColor(0, 0, 0.11764705882f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        for (int i = 0; i < stars.size(); i++) {
            Rectangle r = stars.get(i);
            batch.draw(star, r.getX(), r.getY(), r.getWidth(), r.getHeight());
        }
        for (int i = 0; i < obstacles.size(); i++) {
            GameObject2D go2d = obstacles.get(i);
            batch.draw(go2d.getNextTexture(frame), go2d.x, go2d.y, go2d.width / 2, go2d.height / 2, go2d.width, go2d.height, go2d.scale, go2d.scale, go2d.rotation);

        }
        batch.draw(plane1.getPreviousTexture(frame), plane1.x, plane1.y, plane1.width / 2, plane1.height / 2, plane1.width, plane1.height, plane1.scale, plane1.scale, -90f);
        batch.draw(plane2.getPreviousTexture(frame), plane2.x, plane2.y, plane2.width / 2, plane2.height / 2, plane2.width, plane2.height, plane2.scale, plane2.scale, -90f);
        GlyphLayout g = new GlyphLayout(font, Integer.toString((int) score));
        font.setColor(Color.WHITE);
        font.draw(batch, Integer.toString((int) score), 1920 / 2 - g.width / 2, 1080 / 14 * 13 - g.height / 2);
        if (!paused) {


            if (up) {
                plane1.y += speed;
                plane2.y -= speed;
                up = !((plane1.y + (plane1.height * plane1.scale) / 2f) + 1 >= 1080);
            } else {
                plane1.y -= speed;
                plane2.y += speed;
                up = ((plane2.y + (plane2.height * plane2.scale) / 2f) + 1 >= 1080);
            }
            plane1.update();
            plane2.update();
            for (int i = 0; i < stars.size(); i++) {
                Rectangle r = stars.get(i);
                //batch.draw(star, r.getX(), r.getY(), r.getWidth(), r.getHeight());
                r.x -= r.getWidth() * speed / 8;
                if (r.x <= 0) {
                    r.x = 1920;
                }
                stars.set(i, r);
            }
            counter1 += difficulty += MathUtils.random(difficulty * 10);
            if (counter1 >= 500) {
                obstacles.add(new GameObject2D(1920, 8 + MathUtils.random(1080), new TextureRegion(asteroid), 8, 8, 60, 4f, MathUtils.random(60), MathUtils.random(-0.1f, 0.1f)));
                counter1 %= 500;
            }
            ArrayList<GameObject2D> remove = new ArrayList<>();
            for (int i = 0; i < obstacles.size(); i++) {
                GameObject2D go2d = obstacles.get(i);
                go2d.x -= speed;
                go2d.rotation += go2d.runit;
                go2d.update();
                if (go2d.getRect().overlaps(plane1.getRect())) {
                    explosion.x = plane1.x;
                    explosion.y = plane1.y;
                    plane1 =explosion;
                    died = true;
                    paused = true;
                    sfx.stop();
                    sfx.setPosition(0);
                    sfx.play();
                }else if( go2d.getRect().overlaps(plane2.getRect())){
                    explosion.x = plane2.x;
                    explosion.y = plane2.y;
                    plane2 =explosion;
                    died = true;
                    paused =true;
                    sfx.stop();
                    sfx.setPosition(0);
                    sfx.play();
                }
                if (go2d.x <= -16) {
                    remove.add(go2d);
                    break;
                }
                obstacles.set(i, go2d);
            }
            obstacles.removeAll(remove);
            frame++;
            floatCounter += 0.00002777777f;
            difficulty = 2.5f * floatCounter;
            speed = difficulty * 6;
            score += difficulty;
        } else {
            batch.draw(pauseBackground, 0, 0, 1920, 1080);
            int i = 0;
            for (Map.Entry<String, Rectangle> entry : fontColliders.entrySet()) {
                String key = entry.getKey();
                Rectangle rect = entry.getValue();
                if (selected == i) font.setColor(new Color(47f / 256, 214f / 256, 211f / 256, 1));
                font.draw(batch, key, rect.x, rect.y);
                font.setColor(Color.WHITE);
                i++;
            }

        }

        batch.end();

    }

    @Override
    public void dispose() {
        batch.dispose();
        img.dispose();
        star.dispose();
        asteroid.dispose();
        pauseBackground.dispose();
        sfx.dispose();
        for (Music music:musics) {
            music.dispose();
        }
    }

    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.N){
            Timer.post(new Timer.Task() {
                @Override
                public void run() {
                    musics[currentMusic].stop();
                    musics[currentMusic].setPosition(0);
                    currentMusic++;
                    musics[currentMusic].play();
                }
            });
            return true;
        }
        if (!paused) {
            if (keycode == (Input.Keys.ESCAPE)) {
                paused = true;
                return true;
            } else {
                up = !up;
                return true;
            }
        } else {
            switch (keycode) {
                case Input.Keys.ESCAPE:
                    if (died){
                        this.dispose();
                        this.create();
                        return true;
                    }
                    paused = false;
                    return true;
                case Input.Keys.UP:
                    selected--;
                    selected %= 3;
                    return true;
                case Input.Keys.DOWN:
                    selected++;
                    selected %= 3;
                    return true;
                case Input.Keys.ENTER:
                    if (selected == 0) {
                        if (died){
                            this.dispose();
                            this.create();
                            return true;
                        }
                        paused = false;
                        return true;
                    } else if (selected == 1) {
                        this.dispose();
                        this.create();
                        return true;
                    } else {
                        Gdx.app.exit();
                    }
            }
        }
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
        if (button == 0) {
            if (selected == 0) {
                if (died){
                    this.dispose();
                    this.create();
                    return true;
                }
                paused = false;
                return true;
            } else if (selected == 1) {
                this.dispose();
                this.create();
                return true;
            } else {
                Gdx.app.exit();
            }
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        if (paused) {
            Vector3 mousePos = new Vector3(screenX, screenY, 0);
            mousePos = camera.unproject(mousePos);
            Rectangle mouse = new Rectangle(mousePos.x, mousePos.y, 10, 10);
            int i = 0;
            for (Map.Entry<String, Rectangle> entry : fontColliders.entrySet()) {
                Rectangle rect = entry.getValue();
                if (mouse.overlaps(rect)) selected = i;
                selected %= 3;
                i++;
            }
            return true;

        }
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
