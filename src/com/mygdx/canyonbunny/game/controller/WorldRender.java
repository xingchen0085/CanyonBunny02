package com.mygdx.canyonbunny.game.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.canyonbunny.game.utils.Constants;
import com.sun.media.jfxmediaimpl.MediaDisposer;

/**
 * 游戏基本渲染
 */
public class WorldRender implements MediaDisposer.Disposable {

    private final static String TAG = WorldRender.class.getName();//日志需要
    private WorldController worldController;

    private SpriteBatch spriteBatch;//渲染

    private OrthographicCamera camera;//相机


    public WorldRender(WorldController worldController) {
        this.worldController = worldController;
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        Gdx.app.log(TAG, "初始化");
        spriteBatch = new SpriteBatch();
        camera = new OrthographicCamera(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
        //初始相机位置
        camera.position.set(0, 0, 0);
        camera.update();
    }

    /**
     * 渲染
     */
    public void render() {
        //相机配置更新
        worldController.getCameraHelper().applyTo(camera);
        //锁定相机视角
        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();

        Sprite rock = worldController.getRock();
        if (null != rock) {
            rock.draw(spriteBatch);
        }

        Sprite sprite = worldController.getSprite();
        if (null != sprite) {
            sprite.draw(spriteBatch);
        }

        Sprite banana = worldController.getBanana();
        if(null != banana){
            banana.draw(spriteBatch);
        }
        spriteBatch.end();
    }

    /**
     * 窗口尺寸更新
     *
     * @param width  宽
     * @param height 高
     */
    public void reSize(int width, int height) {
        camera.viewportWidth = (Constants.VIEWPORT_WIDTH / height) * width;
        camera.update();
    }

    /**
     * 销毁
     */
    @Override
    public void dispose() {
        Gdx.app.log(TAG, "资源回收");
        spriteBatch.dispose();
    }
}
