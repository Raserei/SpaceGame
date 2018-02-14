package ru.raserei.spacegame.engine.utils;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Raserei on 12.02.2018.
 */

public class Regions {

    public static TextureRegion[] split(TextureRegion region, int rows, int cols, int frames){
        if (region == null) throw  new RuntimeException("region is null!");

        TextureRegion[] regions = new TextureRegion[frames];
        int frameWidth = region.getRegionWidth()/cols;
        int frameHeight = region.getRegionHeight()/rows;

        int frame = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++,frame++) {
                regions[frame] = new TextureRegion(region,frameWidth*j, frameHeight*i,frameWidth,frameHeight);
                if (frame == frames-1) return regions;
            }
        }
        return regions;
    }
}
