package com.freshcodes.omdbapp.imageloader;

import com.freshcodes.omdbapp.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

public class DisplayImageOption {
    public static DisplayImageOptions getDisplayImage() {
        DisplayImageOptions options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.icon_place_holder)
                .showImageForEmptyUri(R.drawable.icon_place_holder).showImageOnFail(R.drawable.icon_place_holder)
                .cacheInMemory(true).cacheOnDisk(true).considerExifParams(true).build();

        return options;
    }

    public static DisplayImageOptions getDisplayRoundedImage() {
        DisplayImageOptions options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.icon_place_holder)
                .showImageForEmptyUri(R.drawable.icon_place_holder).showImageOnFail(R.drawable.icon_place_holder)
                .cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
                .displayer(new RoundedBitmapDisplayer(100)).build();

        return options;
    }
}