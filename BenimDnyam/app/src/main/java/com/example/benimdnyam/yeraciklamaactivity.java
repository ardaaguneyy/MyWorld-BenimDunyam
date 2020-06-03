package com.example.benimdnyam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.io.ByteArrayOutputStream;
import java.util.ArrayList;


import static com.example.benimdnyam.MainActivity.favorikontrol;
import static com.example.benimdnyam.MainActivity.randomsayiyer;
import static com.example.benimdnyam.MainActivity.yeradlari;
import static com.example.benimdnyam.MainActivity.yeraciklamalari;
import static com.example.benimdnyam.MainActivity.yerresimleri;
import static com.example.benimdnyam.MainActivity.yerkonumlari;
public class yeraciklamaactivity extends AppCompatActivity {
TextView yeradi,yerkonumu,yerbilgisi;
ImageView imageView;
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yeraciklamaactivity);
        yeradi= findViewById(R.id.textViewad);
        yerkonumu=findViewById(R.id.textViewkonum);
        yerbilgisi=findViewById(R.id.textViewbilgi);
        imageView=findViewById(R.id.imageView4);
        yeradi.setText(yeradlari.get(randomsayiyer));
        yerbilgisi.setText(yeraciklamalari.get(randomsayiyer));
        imageView.setImageBitmap(yerresimleri.get(randomsayiyer));
        yerkonumu.setText(yerkonumlari.get(randomsayiyer));


    }
    public void favoriekleyeraciklama(View view) {
        try {
            database = this.openOrCreateDatabase("Favorisaklama", MODE_PRIVATE, null);
            Cursor cursor = database.rawQuery("SELECT * FROM favorisaklama  ", null);
            int nameIx = cursor.getColumnIndex("name");
            while (cursor.moveToNext()){
                favorikontrol.add(cursor.getString(nameIx));
            }
            cursor.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        if (!LookFavourites(favorikontrol,yeradlari.get(randomsayiyer))) {
            Bitmap anlikresim = yerresimleri.get(randomsayiyer);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            anlikresim.compress(Bitmap.CompressFormat.PNG, 50, outputStream);
            byte[] byteArray = outputStream.toByteArray();
            database.execSQL("CREATE TABLE IF NOT EXISTS favorisaklama(id INTEGER PRIMARY KEY, name VARCHAR, aciklama VARCHAR,konum VARCHAR,image BLOB)");
            String sqlstring = "INSERT INTO favorisaklama(name,aciklama,konum,image) VALUES (?,?,?,?)";
            SQLiteStatement sqLiteStatement = database.compileStatement(sqlstring);
            sqLiteStatement.bindString(1, yeradlari.get(randomsayiyer));
            sqLiteStatement.bindString(2, yeraciklamalari.get(randomsayiyer));
            sqLiteStatement.bindString(3, yerkonumlari.get(randomsayiyer));
            sqLiteStatement.bindBlob(4, byteArray);
            sqLiteStatement.execute();
            Toast.makeText(yeraciklamaactivity.this, "FAVORILERE EKLENDI", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), Favorilerimactivity.class);
            startActivity(intent);
        }else{
            Toast.makeText(yeraciklamaactivity.this, "ZATEN FAVORİLERİNİZDE", Toast.LENGTH_SHORT).show();
        }



    }
    public static boolean LookFavourites(ArrayList<String> favoriler, String favori)
    {
        if(favoriler.contains(favori))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    }

