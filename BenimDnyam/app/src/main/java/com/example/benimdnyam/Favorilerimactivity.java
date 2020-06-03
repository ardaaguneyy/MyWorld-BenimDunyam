package com.example.benimdnyam;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import java.util.ArrayList;
import static com.example.benimdnyam.MainActivity.favoritelocaitongps;
import static com.example.benimdnyam.MainActivity.favoritelocationimage;
import static com.example.benimdnyam.MainActivity.favoritelocationinfo;
import static com.example.benimdnyam.MainActivity.favoritelocationname;



public class Favorilerimactivity extends AppCompatActivity {

private recyclerviewadapterfavori recyclerviewadapterfavori1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorilerimactivity);
        favoritelocationname.clear();
        favoritelocationinfo.clear();
        favoritelocaitongps.clear();
        favoritelocationimage.clear();
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        favoridata();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerviewadapterfavori1 =new recyclerviewadapterfavori(favoritelocationname,favoritelocationimage,favoritelocationinfo, favoritelocaitongps);
        recyclerView.setAdapter(recyclerviewadapterfavori1);

    }
    public  void  favoridata() {

        try {
            SQLiteDatabase database = this.openOrCreateDatabase("Favorisaklama", MODE_PRIVATE, null);
            Cursor cursor = database.rawQuery("SELECT * FROM favorisaklama  ", null);
            int nameIx = cursor.getColumnIndex("name");
            int locationinfo = cursor.getColumnIndex("aciklama");
            int locationgps = cursor.getColumnIndex("konum");
            int locationimage = cursor.getColumnIndex("image");
            while (cursor.moveToNext()){
                favoritelocationname.add(cursor.getString(nameIx));
                favoritelocationinfo.add(cursor.getString(locationinfo));
                favoritelocaitongps.add(cursor.getString(locationgps));
                byte[] bytes = cursor.getBlob(locationimage);
                Bitmap favoriresim = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                favoritelocationimage.add(favoriresim);
            }
            cursor.close();
        } catch (Exception e) {
            System.out.println(e);
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

