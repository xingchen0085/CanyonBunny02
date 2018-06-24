package com.mygdx.canyonbunny.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.mygdx.canyonbunny.game.controller.WorldController;
import com.mygdx.canyonbunny.game.controller.WorldRender;

/**
 * 游戏主类
 */
public class CanyonBunnyMain implements ApplicationListener {
    private final static String TAG = CanyonBunnyMain.class.getName();//日志需要

    private WorldController worldController;
    private WorldRender worldRender;

    private float deltaTime;

    private boolean isPause;//是否暂停

    @Override
    public void create() {
        worldController = new WorldController();
        worldRender = new WorldRender(worldController);

        //日志级别
        Gdx.app.setLogLevel(Application.LOG_DEBUG);

        //开始
        isPause = false;
    }

    @Override
    public void resize(int i, int i1) {
        worldRender.reSize(i, i1);
    }

    @Override
    public void render() {
        if (isPause) {
            //暂停状态不进行任何操作
            return;
        }
        //更新
        deltaTime = Gdx.graphics.getDeltaTime();
        worldController.update(deltaTime);

        //清屏
        Gdx.gl.glClearColor(0, 0, 0.7f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //渲染
        worldRender.render();
    }

    @Override
    public void pause() {
        isPause = true;
    }

    @Override
    public void resume() {
        isPause = false;
    }

    @Override
    public void dispose() {
        worldRender.dispose();
    }
}
