package com.bonheur.dict1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    String amagambo[]={
            "intashyo", "umukambwe", "intango", "ikizeneko", "imana"
    };
    String ibisobanuro[] =
            {
                    "Ni ijambo bakoresha basuhuzanya","ni Umusaza", "ni igisabo", "Ni inzoga bahaga ba Nyirasenge w'umukobwa wakowe", "Ni Rurema"
            };

    TextView results;
    EditText givenWord, ibyatanzwe_ijambo, ibyatanzwe_ubusobanuro;
    Button searchBtn;
    Button addInDb, showBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        results = findViewById(R.id.results);
        givenWord = findViewById(R.id.givenWord);
        searchBtn = findViewById(R.id.searchBtn);
        addInDb = findViewById(R.id.addInDb);
        showBtn = findViewById(R.id.showBtn);
        ibyatanzwe_ijambo = findViewById(R.id.ibyatanzwe_ijambo);
        ibyatanzwe_ubusobanuro = findViewById(R.id.ibyatanzwe_ubusobanuro);

        addInDb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AmagamboDatabase amagamboDatabase = new AmagamboDatabase(MainActivity.this);
                SQLiteDatabase db = amagamboDatabase.getWritableDatabase();
                ContentValues ijambo_rishya = new ContentValues();
                ijambo_rishya.put("ijambo_ijambo", ibyatanzwe_ijambo.getText().toString());
                ijambo_rishya.put("ijambo_ubusobanuro", ibyatanzwe_ubusobanuro.getText().toString());
                long result = db.insert("amagambo_yose", null, ijambo_rishya);
                if(result != -1)
                {
                    Toast.makeText(MainActivity.this, "Ijambo ryagiyemo neza.", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Byanze!", Toast.LENGTH_LONG).show();
                }

            }
        });

        showBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                results.setText("");
                AmagamboDatabase amagamboDatabase = new AmagamboDatabase(MainActivity.this);
                SQLiteDatabase db = amagamboDatabase.getReadableDatabase();
                String[] projections = {"ijambo_id","ijambo_ijambo", "ijambo_ubusobanuro"};
                Cursor cursor = db.query("amagambo_yose",projections, null,null,null,null,"ijambo_id DESC");
                while(cursor.moveToNext())
                {
                    int ibibonetse_id = cursor.getInt(cursor.getColumnIndexOrThrow("ijambo_id"));
                    String ibibonetse_ijambo = cursor.getString(cursor.getColumnIndexOrThrow("ijambo_ijambo"));
                    String ibibonetse_ijambo_ubusobanuro = cursor.getString(cursor.getColumnIndexOrThrow("ijambo_ubusobanuro"));

                    results.append("ID: "+ ibibonetse_id + "\nIjambo: "+ ibibonetse_ijambo + "\nUbusobanuro: " + ibibonetse_ijambo_ubusobanuro+"\n\n");
                }
            }
        });


        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ijambo = givenWord.getText().toString().trim().toLowerCase(Locale.ROOT);
                if(ijambo.trim().equals(""))
                {
                    givenWord.setError("Andika hano...");
//                    givenWord.setFocusable(true);
                    return;
                }
                String result = getWordMeaning(ijambo);
                if(result.equals("-1"))
                {
                    results.setText("Mwihangane! ntago byabonetse");
                }
                else
                {
                    results.setText(result);
                }
            }
        });


    }
    int findWordIndex(String word)
    {
        for(int i=0; i< amagambo.length; i++)
        {
            if(amagambo[i].equals(word))
            {
                return i;
            }
        }
        return -1;
    }
    String getWordMeaning(String word)
    {
        AmagamboDatabase amagamboDatabase = new AmagamboDatabase(MainActivity.this);
        SQLiteDatabase db = amagamboDatabase.getReadableDatabase();
        String[] projections = {"ijambo_ubusobanuro"};
        String selection = "ijambo_ijambo = ?";
        String[] selectionArgs = {word};
        Cursor cursor = db.query("amagambo_yose",projections, selection,selectionArgs,null,null,"ijambo_id DESC");
        while(cursor.moveToNext())
        {
            String ibibonetse_ijambo_ubusobanuro = cursor.getString(cursor.getColumnIndexOrThrow("ijambo_ubusobanuro"));

            return ibibonetse_ijambo_ubusobanuro;
        }
        return "-1";
    }
}