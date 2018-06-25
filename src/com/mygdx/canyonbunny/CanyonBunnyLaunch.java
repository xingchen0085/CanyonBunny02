package com.mygdx.canyonbunny;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.canyonbunny.game.CanyonBunnyMain;
import com.mygdx.canyonbunny.game.utils.Constants;

/**
 * 游戏启动类
 */
public class CanyonBunnyLaunch {
    public static void main(String[] args){
        //纹理打包


        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = Constants.WINDOW_WIDTH;
        config.height = Constants.WINDOW_HEIGHT;

        new LwjglApplication(new CanyonBunnyMain(),config);
    }
}
