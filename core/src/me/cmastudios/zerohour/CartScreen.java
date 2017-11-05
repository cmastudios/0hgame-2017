package me.cmastudios.zerohour;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;

import java.util.Random;

public class CartScreen implements Screen {

    private final SatanCartHorror game;
    private SpriteBatch batch;
    private Sprite img;
    private Texture background;
    private Sprite man;
    private Sprite dev;
    private Random random;
    float man_vel_y = 0;
    int score = 0;

    CartScreen(SatanCartHorror game) {
        this.game = game;
        random = new Random();
        batch = new SpriteBatch();
        img = new Sprite(new Texture("cart.png"));
        background = new Texture("track.png");
        man = new Sprite(new Texture("man.png"));
        dev = new Sprite(new Texture("devil.png"));

        dev.setCenterX(750);
        dev.setCenterY(200);
        man.setX(390);
        man.setY(310);
        img.setX(400);
        img.setY(290);
    }

    boolean triggered = false;

    @Override
    public void render (float delta) {
        dev.setX(dev.getX() - delta*45 * 15);
        dev.setY(dev.getY() + delta*20 * 15);
        if (dev.getX() < 0 && random.nextInt(1000) > 995) {
            dev.setX(750);
            dev.setY(200);
//            if (dev.getX() > -99999999)
//            score += 1;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && !triggered) {
            man_vel_y = 300;
        } else {
            man_vel_y = -500;
        }

        man.setY(man.getY() + man_vel_y * delta);

        if (man.getY() < 310) {
            man.setY(310);
            man_vel_y = 0;
            triggered = false;
        }

        if (man.getY() > 500) {
            triggered = true;
        }

        Circle c = new Circle(man.getX() + man.getWidth() / 2.f, man.getY() + man.getHeight() / 2.f, 60f);
        if (c.contains(dev.getX() + dev.getWidth() / 2.f, dev.getY() + dev.getHeight() / 2.f)) {
            dev.setX(-99999);
            man.setY(310);
            score -= 1;
        }
        System.out.println(score);

        if (score <= -3) {
            game.setScreen(new LossScreen(game));
        }



        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(background, 0, 0);
//        batch.draw(man, 390, 310);
//        batch.draw(img, 400, 290);
        man.draw(batch);
        img.draw(batch);
        dev.draw(batch);
        batch.end();
    }

    @Override
    public void dispose () {
        batch.dispose();
    }

    @Override
    public void show() {

    }
    
    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }
}
