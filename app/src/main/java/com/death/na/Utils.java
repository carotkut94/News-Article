package com.death.na;

import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.util.Log;
import android.util.TypedValue;

/**
 * Created by rajora_sd on 4/19/2017.
 */

public class Utils {


    public static String PROTOCOL = "https";
    public static String BASE_URL = "newsapi.org";
    public static String VERSION_CODE = "v1";
    public static String SOURCES = "sources";

    public static String getSourceURL()
    {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme(PROTOCOL)
                .authority(BASE_URL)
                .appendPath(VERSION_CODE)
                .appendPath(SOURCES);
        String myUrl = builder.build().toString();
        Log.e("LINK", myUrl);
        return  myUrl;
    }

    public static int dpToPx(Context context, int dp) {
        Resources r = context.getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}
