package com.rockbass2560.megacode.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.rockbass2560.megacode.util.Assets;
import com.rockbass2560.megacode.util.Constants;
import com.rockbass2560.megacode.util.Utils;

public class ExitPortal {

    public final static String TAG = ExitPortal.class.getName();

    public final Vector2 position;

    private final long startTime;

    public ExitPortal(Vector2 position) {
        this.position = position;
        startTime = TimeUtils.nanoTime();
    }

    public void render(SpriteBatch batch) {
        final float elapsedTime = Utils.secondsSince(startTime);
        final TextureRegion region = (TextureRegion)Assets.instance.exitPortalAssets.exitPortal.getKeyFrame(elapsedTime, true);
        Utils.drawTextureRegion(batch, region, position, Constants.EXIT_PORTAL_CENTER);
    }



}

