package com.mygdx.canyonbunny;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.mygdx.canyonbunny.game.CanyonBunnyMain;
import com.mygdx.canyonbunny.game.utils.Constants;

/**
 * 游戏启动类
 */
public class CanyonBunnyLaunch {
    //是否打包纹理
    private static boolean isPack = false;

    public static void main(String[] args) {
        if (isPack) {
            //纹理打包
            TexturePacker.process("E:\\Libgdx\\4775OS_Code\\raw-assets", "E:\\Libgdx\\4775OS_Code\\raw-assets\\out", "raw.atlas");
        }

        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = Constants.WINDOW_WIDTH;
        config.height = Constants.WINDOW_HEIGHT;

        new LwjglApplication(new CanyonBunnyMain(), config);
    }
}
