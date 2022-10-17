package com.example.car;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class MyDatabase extends SQLiteAssetHelper {

    public static final String DB_NAME = "car.db";
    public static final int DB_VERION = 1;

    public static final String CAR_TB_NAME = "car";
    public static final String CAR_TB_ID = "id";
    public static final String CAR_TB_MODEL = "model";
    public static final String CAR_TB_COLOR = "color";
    public static final String CAR_TB_DESCRIPTION= "description";
    public static final String CAR_TB_IMAGE = "image";
    public static final String CAR_TB_DPL = "distancePreletter";


    public MyDatabase(Context context) {
        super(context, DB_NAME, null, DB_VERION);
    }
}
