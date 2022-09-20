package com.nmssdmf.gitlib.view;

import android.app.Application;

import me.jessyan.autosize.AutoSize;
import me.jessyan.autosize.AutoSizeConfig;
import me.jessyan.autosize.unit.Subunits;

/**
 * @author mahuafeng
 * @date 2022/9/14
 */
public class BaseApp extends Application {
    private static BaseApp app;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        //autosize使用mm作为单位
        AutoSizeConfig.getInstance()
                .getUnitsManager()
                .setSupportDP(false)
                .setSupportSP(false)
                .setSupportSubunits(Subunits.PT);
    }

    public static BaseApp getInstance() {
        return app;
    }
}
