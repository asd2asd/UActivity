package com.ua;

/**
 * Created by justs_000 on 2014/12/23.
 */
import android.app.Application;

public class MyApp extends Application{
    public String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
