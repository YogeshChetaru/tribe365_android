package com.chetaru.tribe365_new.utility;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;

import androidx.annotation.Nullable;

public class Res extends Resources {
    /**
     * Create a new Resources object on top of an existing set of assets in an
     * AssetManager.
     *
     * @param //assets  Previously created AssetManager.
     * @param //metrics Current display metrics to consider when
     *                  selecting/computing resource values.
     * @param //config  Desired device configuration to consider when
     * @deprecated Resources should not be constructed by apps.
     * See {@link Context#createConfigurationContext(Configuration)}.
     */
    /*public Res(AssetManager assets, DisplayMetrics metrics, Configuration config) {
        super(assets, metrics, config);

    }*/
    public Res(Resources original) {
        super(original.getAssets(), original.getDisplayMetrics(), original.getConfiguration());
    }

    @Override
    public int getColor(int id) throws NotFoundException {
        return getColor(id, null);
    }

    @Override
    public int getColor(int id, @Nullable Theme theme) throws NotFoundException {
        switch (getResourceName(id)) {
            case "red":
                return Color.RED;
            default:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    return super.getColor(id, theme);
                }
                return super.getColor(id);
        }

    }
}
