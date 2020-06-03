package com.example.benimdnyam;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class recyclerviewadapterfavori extends  RecyclerView.Adapter<recyclerviewadapterfavori.Favoritelocation>  {
 private ArrayList<String> userlocationnames;
 private ArrayList<Bitmap> userimagebitmaps;
 private ArrayList<String> userlocationinformations;
 private  ArrayList<String> userlocationgpsinfos;

    public recyclerviewadapterfavori(ArrayList<String> userlocationnames, ArrayList<Bitmap> userimagebitmaps, ArrayList<String> userlocationinformations, ArrayList<String> userlocationgpsinfos) {
        this.userlocationnames = userlocationnames;
        this.userimagebitmaps = userimagebitmaps;
        this.userlocationinformations = userlocationinformations;
        this.userlocationgpsinfos = userlocationgpsinfos;
    }

    @NonNull
    @Override
    public recyclerviewadapterfavori.Favoritelocation onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recyclerviewfavori,parent,false);
        return new Favoritelocation(view);

    }

    @Override
    public void onBindViewHolder(@NonNull recyclerviewadapterfavori.Favoritelocation holder, int position) {
      holder.textView.setText(userlocationnames.get(position));
      holder.imageView.setImageBitmap(userimagebitmaps.get(position));
      holder.textView2.setText(userlocationinformations.get(position));
      holder.textView3.setText(userlocationgpsinfos.get(position));
    }

    @Override
    public int getItemCount() {
        return userlocationnames.size();
    }

    public class Favoritelocation extends RecyclerView.ViewHolder {
        TextView textView,textView2,textView3;
        ImageView imageView;
        public Favoritelocation(@NonNull View itemView) {
            super(itemView);
            textView= itemView.findViewById(R.id.textView2);
            textView2=itemView.findViewById(R.id.textView3);
            textView3=itemView.findViewById(R.id.textView4);
            imageView=itemView.findViewById(R.id.imageView6);
        }
    }
}
