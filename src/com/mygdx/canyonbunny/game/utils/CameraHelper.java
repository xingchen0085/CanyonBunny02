package com.mygdx.canyonbunny.game.utils;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

/**
 * 相机工具辅助
 */
public class CameraHelper {
    private final static String TAG = CameraHelper.class.getName();//日志需要
    private static final float MAX_ZOOM_IN = 0.25f;//最小缩小
    private static final float MAX_ZOOM_OUT = 10.0f;//最大放大

    private Vector2 position;//位置

    private float zoom;//当前放大率

    private Sprite target;//相机追踪目标

    public CameraHelper() {
        position = new Vector2();
        zoom = 1.0f;
    }

    /**
     * 更新
     */
    public void update(float deltaTime) {
        if (!hasTarget()) {
            return;
        }
        position.x = target.getX() + target.getOriginX();
        position.y = target.getY() + target.getOriginY();
    }

    /**
     * 执行相机配置
     */
    public void applyTo(OrthographicCamera camera) {
        camera.position.x = position.x;
        camera.position.y = position.y;
        camera.zoom = zoom;
        camera.update();
    }

    public boolean hasTarget() {
        return target != null;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(float x, float y) {
        this.position.set(x, y);
    }

    public float getZoom() {
        return zoom;
    }

    public void setZoom(float zoom) {
        this.zoom = MathUtils.clamp(zoom, MAX_ZOOM_IN, MAX_ZOOM_OUT);
    }

    public Sprite getTarget() {
        return target;
    }

    public void setTarget(Sprite target) {
        this.target = target;
    }
}
