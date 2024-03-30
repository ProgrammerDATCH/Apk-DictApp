package com.bonheur.dict1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class AmagamboDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "amagambo_database.db";
    private static final int DATABASE_VERSION = 1;

    public AmagamboDatabase(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createOurTable = "CREATE TABLE amagambo_yose(ijambo_id INTEGER PRIMARY KEY AUTOINCREMENT, ijambo_ijambo VARCHAR, ijambo_ubusobanuro TEXT);";
        sqLiteDatabase.execSQL(createOurTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
