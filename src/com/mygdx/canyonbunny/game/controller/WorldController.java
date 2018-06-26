package com.mygdx.canyonbunny.game.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.canyonbunny.game.utils.CameraHelper;
import com.mygdx.canyonbunny.game.utils.Constants;


/**
 * 游戏基本控制
 */
public class WorldController extends InputAdapter {
    private final static String TAG = WorldController.class.getName();//日志需要
    private CameraHelper cameraHelper;
    private Sprite sprite;
    private Sprite rock;//地
    private boolean isMove;
    Sprite banana;

    public WorldController() {
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        Gdx.app.log(TAG, "初始化");
        Gdx.input.setInputProcessor(this);//事件处理
        cameraHelper = new CameraHelper();
        rock = iniRockPixMap();
        sprite = initPixMap();
        isMove = false;

        //测试相机的纹理包
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("images/test/atlas/raw.atlas"));
        //找出香蕉
        banana = atlas.createSprite("banana");
        banana.setSize(2,2);
        banana.setOriginCenter();//原点居中
        banana.setPosition(2,2);
    }

    /**
     * 游戏更新
     *
     * @param deltaTime 时间桢间隔
     */
    public void update(float deltaTime) {
        //事件处理
        resovleEventListener(deltaTime);
        //相机视角更新
        cameraHelper.update(deltaTime);

        //游戏事物更新
        //Gdx.app.log(TAG, "当前坐标: (" + sprite.getX() + " , " + sprite.getY() + ")");
        if (isMove) {
            sprite.translate(5 * deltaTime, 0);
        }
    }


    /**
     * 事件处理
     *
     * @param deltaTime
     */
    private void resovleEventListener(float deltaTime) {

        //对象控制
        float x = sprite.getX();
        float y = sprite.getY();
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            if (x <= 0) {
                return;
            }
            moveSprite(sprite, new Vector2(-5.0f * deltaTime, 0));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            if (x >= Constants.WINDOW_WIDTH - 1) {
                return;
            }
            moveSprite(sprite, new Vector2(5.0f * deltaTime, 0));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            if (y >= Constants.WINDOW_HEIGHT - 1) {
                return;
            }
            moveSprite(sprite, new Vector2(0, 5.0f * deltaTime));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            if (y <= 0) {
                return;
            }
            moveSprite(sprite, new Vector2(0, -5.0f * deltaTime));
        }

        //相机控制
        float camMoveSpeed = 5 * deltaTime;
        if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
            camMoveSpeed *= 5;//加快 5 倍速度
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            moveCamera(new Vector2(0, camMoveSpeed));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            moveCamera(new Vector2(0, -camMoveSpeed));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            moveCamera(new Vector2(-camMoveSpeed, 0));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            moveCamera(new Vector2(camMoveSpeed, 0));
        }
        float camZoomSpeed = 1 * deltaTime;
        if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
            camZoomSpeed *= 5;//加快 5 倍速度
        }
        if (Gdx.input.isKeyPressed(Input.Keys.COMMA)) {
            cameraHelper.addZoom(camMoveSpeed);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.PERIOD)) {
            cameraHelper.addZoom(-camMoveSpeed);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.SLASH)) {
            cameraHelper.setZoom(1);
        }
    }

    @Override
    public boolean keyUp(int keycode) {
        if (Input.Keys.ENTER == keycode) {
            if (!cameraHelper.hasTarget()) {
                cameraHelper.setTarget(sprite);
            } else {
                cameraHelper.setTarget(null);
            }
        }

        if (Input.Keys.SPACE == keycode) {
            if (!cameraHelper.hasTarget()) {
                cameraHelper.setTarget(sprite);
            }
            if (!isMove) {
                isMove = !isMove;
            }
        }
        return false;
    }

    /**
     * 根据增量向量移动物体
     *
     * @param sprite
     * @param translate
     */
    private void moveSprite(Sprite sprite, Vector2 translate) {
        if (null != sprite) {
            sprite.translate(translate.x, translate.y);
        }
    }

    private void moveCamera(Vector2 translate) {
        cameraHelper.setPosition(cameraHelper.getPosition().x + translate.x, cameraHelper.getPosition().y + translate.y);
    }

    private Sprite initPixMap() {

        int width = 32;
        int height = 32;

        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGB888);
        pixmap.setColor(1, 0, 0, 0.5f);
        pixmap.fill();

        pixmap.setColor(1, 1, 0, 1);
        pixmap.drawLine(0, height, width / 2, 0);
        pixmap.drawLine(width / 2, height, width, 0);
        pixmap.drawLine(width / 2, height, width / 2, 0);

        //画边框
        pixmap.setColor(0, 1, 1, 1);
        pixmap.drawRectangle(0, 0, width, height);

        Texture texture = new Texture(pixmap);
        //平滑纹里
        texture.setFilter(Texture.TextureFilter.Linear,Texture.TextureFilter.Linear);
        sprite = new Sprite(texture);
        sprite.setSize(1, 1);
        sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
        sprite.setPosition(0, 0);

        pixmap.dispose();

        return sprite;
    }

    public Sprite iniRockPixMap() {
        Pixmap pixmap = new Pixmap(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT / 10, Pixmap.Format.RGB888);
        Texture texture = new Texture(pixmap);

        Sprite sprite = new Sprite(texture);
        sprite.setSize(Constants.WINDOW_WIDTH, 1.0f);
        sprite.setPosition(0, 0);
        return sprite;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public Sprite getRock() {
        return rock;
    }

    public Sprite getBanana() {
        return banana;
    }

    public CameraHelper getCameraHelper() {
        return cameraHelper;
    }
}
