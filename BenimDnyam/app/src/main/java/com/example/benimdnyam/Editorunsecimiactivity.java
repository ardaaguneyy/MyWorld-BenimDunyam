package com.example.benimdnyam;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import static com.example.benimdnyam.MainActivity.favoritelocaitongps;
import static com.example.benimdnyam.MainActivity.favoritelocationimage;
import static com.example.benimdnyam.MainActivity.favoritelocationinfo;
import static com.example.benimdnyam.MainActivity.favoritelocationname;
import static com.example.benimdnyam.MainActivity.yeraciklamalari;
import static com.example.benimdnyam.MainActivity.yeradlari;
import static com.example.benimdnyam.MainActivity.yerkonumlari;
import static com.example.benimdnyam.MainActivity.yerresimleri;

public class Editorunsecimiactivity extends AppCompatActivity {
public recyclerviewadapterfavori recyclerviewadapterfavori1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editorunsecimiactivity);
        RecyclerView recyclerView = findViewById(R.id.recyclerView2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
         recyclerviewadapterfavori1 =new recyclerviewadapterfavori(yeradlari,yerresimleri,yeraciklamalari, yerkonumlari);
        recyclerView.setAdapter(recyclerviewadapterfavori1);
    }
}
