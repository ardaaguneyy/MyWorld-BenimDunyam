package com.example.benimdnyam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Random;


public class MainActivity extends AppCompatActivity {
    static ArrayList<String> yeradlari = new ArrayList<>();
    static  ArrayList<Bitmap> yerresimleri= new ArrayList<>();
    static  ArrayList<String> yerkonumlari= new ArrayList<>();
    static  ArrayList<String> yeraciklamalari= new ArrayList<>();
    static ArrayList<String> favoritelocationname=new ArrayList<>();
    static  ArrayList<String> favoritelocaitongps=new ArrayList<>();
    static  ArrayList<String> favoritelocationinfo=new ArrayList<>();
    static  ArrayList<Bitmap> favoritelocationimage=new ArrayList<>();
    static  int randomsayiyer;
    ImageView imageView;
    TextView textView;
    SQLiteDatabase database;
    Random random = new Random();
    static ArrayList<String> favorikontrol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView=findViewById(R.id.imageView);
         textView=findViewById(R.id.yerismitext);
         favorikontrol= new ArrayList<>();
         database= this.openOrCreateDatabase("Favorisaklama",MODE_PRIVATE,null);
         arraylistler();
         bitmapler();
         randomsayiyer= random.nextInt(10);
         imageView.setImageBitmap(yerresimleri.get(randomsayiyer));
         textView.setText(yeradlari.get(randomsayiyer));

    }
    public void favoriekle(View view) {
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
                Toast.makeText(MainActivity.this, "FAVORILERE EKLENDI", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), Favorilerimactivity.class);
                startActivity(intent);
         }else{
                Toast.makeText(MainActivity.this, "ZATEN FAVORİLERİNİZDE", Toast.LENGTH_SHORT).show();
           }

    }
    public  void sonrakiyer(View view){
        randomsayiyer= random.nextInt(10);
        imageView.setImageBitmap(yerresimleri.get(randomsayiyer));
        textView.setText(yeradlari.get(randomsayiyer));
    }
    public void yeraciklama(View view){
        Intent intent = new Intent(getApplicationContext(),yeraciklamaactivity.class);
        startActivity(intent);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = this.getMenuInflater();
        menuInflater.inflate(R.menu.optionsmenu,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.favorilerim ){
            Intent intent = new Intent(getApplicationContext(),Favorilerimactivity.class);
            startActivity(intent);
        }
        if (item.getItemId()==R.id.editörsecimi){
            Intent intent = new Intent(getApplicationContext(),Editorunsecimiactivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
    public Bitmap Resimkucultmek(Bitmap resim, int maksimumboyut){
        int yukseklik = resim.getHeight();
        int genislik = resim.getWidth();
        Float resimoranı = (float)genislik/(float)yukseklik;
        if (resimoranı>1){
            genislik=maksimumboyut;
            yukseklik= (int)(genislik/resimoranı);
        }else {
            yukseklik=maksimumboyut;
            genislik=(int)(yukseklik*resimoranı);
        }
        return Bitmap.createScaledBitmap(resim,genislik,yukseklik,true);
    }
    public void arraylistler(){
        // eyfel 1.
        yeradlari.add("Eyfel Kulesi");
        yeraciklamalari.add("Eyfel Kulesi 26 Ocak 1887-31 Mart 1889 tarihleri arasında inşa edilmiş Paris'in ve hatta Fransa'nın en önemli sembolü. 1665 basamaklı, 324,8 metre yüksekliğinde, 10.150 ton ağırlığında demirden yapılmış devasa bir mimari.");
        yerkonumlari.add("Fransa,Paris");
    //ozgurluk aniti 2
        yeradlari.add("Özgürlük Anıtı");
        yeraciklamalari.add("Bakırdan yapılan Özgürlük Heykeli, Fransa tarafından kuruluşunun 100. yılı nedeniyle ABD'ye hediye edilmiştir. 1884-1886 yılları arasında inşa edilmiştir. ABD'nin New York şehrindeki Özgürlük Adası'nda yer alır.");
        yerkonumlari.add("Amerika Birleşik Devletleri,New York");
        // Pisa kulesi 3
        yeradlari.add("Pisa Kulesi");
        yeraciklamalari.add("Pisa Kulesi, eğikliği ile turistlerin ilgi odağı. Mucizeler Meydanı'nda bulunan Kule, birbirinin üzerine bindirilmiş 6 sütundan oluşur ve 56 metre yüksekliğe sahiptir. En üst katına 294 basamaklı merdivenle çıkılır. Pisa Kulesi esasında bir çan kulesidir.");
        yerkonumlari.add("İtalya,Pisa");
        //Lord Hove Adası
        yeradlari.add("Lord Howe Adası");
        yeraciklamalari.add("Bundan yaklaşık 7 milyon yıl kadar önce volkanik patlamalar sonucunda meydana gelen Lord Howe Adası, Avustralya gezilecek yerler arasında bulunan ve UNESCO Dünya Mirası Listesi'nde yer alan adeta cennete benzer görünümlü bir adadır.");
        yerkonumlari.add("Avustralya, Sidney");
        //Petra Antik Kenti
        yeradlari.add("Petra Antik Kenti");
        yeraciklamalari.add("Ürdün sınırlarında yer alan Petra antik kenti günümüze ulaşan en etkileyici ve bir o kadar görkemli bir mekandır. Dünya mirasları arasında yer edinen Petra, Dünya'nın 8. harikası şeklinde ifadelendirilmektedir. ");
        yerkonumlari.add("Ürdün");
        //Mavi gol
        yeradlari.add("Mavi Göl");
        yeraciklamalari.add("İzlanda'da yer alan Mavi Göl,İzlanda'nın turistik açıdan en çok ziyaret edilen mekanı olmuştur. Dünya'da bulunan en geniş şifa kaynaklarından biri haline gelen Mavi Göl sülfür ve silis açısından oldukça zengindir ve Dünya genelinde çok sayıda turisti kendine çekmektedir.");
        yerkonumlari.add("İzlanda");
        //Mısır Piramitleri
        yeradlari.add("Mısır Piramitleri");
        yeraciklamalari.add("Mısır Piramitler,firavunların mumyasının bulunduğu piramitlerde birbirinden değerli sanat eserleri, kraliçe, kral ve prensin heykellerini de barındırmaktadır. Bugün gizemleri, efsaneleri ve hazineleri ile Dünya'daki kilit noktalardan biri haline gelmiştir.");
        yerkonumlari.add("Mısır");
        //Peri bacalari
         yeradlari.add("Peri Bacaları");
         yeraciklamalari.add("Ülkemizde bulunan Peri Bacaları,her yıl birçok yerli ve yabancı turistin ziyaret ettiği bir turistik mekan. 60 milyon sene öncesinde Hasandağ, Melendiz ve Göllüdağ'ın püskürttüğü lavlar Peri Pacalarını oluşturmuştur.");
        yerkonumlari.add("Türkiye,Nevşehir");
        //Machu Picchu
        yeradlari.add("Machu Picchu");
        yeraciklamalari.add("Peru'nun ev sahipliği yaptığı, günümüze kadar özenle korunan bir İnka antik kenti olan Machu Pichu, And Dağlarının zirvesinde yer alır. Zamanında İspanyollar bu kenti çok arasalar da bulamamışlar ve bu sayede Machu Pichu hiçbir zarara maruz kalmadan günümüze ulaşmıştır.");
        yerkonumlari.add("Peru");
        //Venedik
        yeradlari.add("Venedik");
        yeraciklamalari.add("Renkli renkli evleri, görkemli kiliseleri, muazzam görüntüdeki kanalları ve de manzara eşliğinde sürülen sandal keyfi ile Venedik, Dünya'da kendine has mekanlardan bir tanesidir. Burada müthiş fotoğraflar çektirebilir ve sandal sefası sürebilirsiniz.");
        yerkonumlari.add("İtalya,Venedik");
        //Roma
        yeradlari.add("Roma Collesiumu");
        yeraciklamalari.add("Yüzyıllarca Dünya'nın kalbi olarak nitelendirilen Roma'nın gezilecek yerleri, tarihi yapıları, Roma Collesiumu, Pisa Kulesi gibi pek çok turistik ögesiyle adeta tarihi bir müze. Sizler de bu güzide şehri ziyaret ederek bir müzede geziyormuş hissine kapılabilir, değerli anılar biriktirebilirsiniz.");
        yerkonumlari.add("İtalya,Roma");
    }
    public void bitmapler(){
    Bitmap eyfel = BitmapFactory.decodeResource(getApplicationContext().getResources(),R.drawable.eyfelkulesi);
    Bitmap eyfel1= Resimkucultmek(eyfel,300);
    yerresimleri.add(eyfel1);


    Bitmap ozgurlukaniti = BitmapFactory.decodeResource(getApplicationContext().getResources(),R.drawable.ozgurlukheykeli);
    Bitmap ozgurlukaniti1= Resimkucultmek(ozgurlukaniti,300);
    yerresimleri.add(ozgurlukaniti1);

    Bitmap pisakulesi = BitmapFactory.decodeResource(getApplicationContext().getResources(),R.drawable.pisa);
    Bitmap pisakulesi1= Resimkucultmek(pisakulesi,300);
    yerresimleri.add(pisakulesi1);

    Bitmap lordhoveadasi = BitmapFactory.decodeResource(getApplicationContext().getResources(),R.drawable.lordhoveadasi);
    Bitmap lordhoveadasi2= Resimkucultmek(lordhoveadasi,300);
    yerresimleri.add(lordhoveadasi2);

    Bitmap petraantikkenti = BitmapFactory.decodeResource(getApplicationContext().getResources(),R.drawable.petra);
    Bitmap petraantikkenti1= Resimkucultmek(petraantikkenti,300);
    yerresimleri.add(petraantikkenti1);

    Bitmap mavigol = BitmapFactory.decodeResource(getApplicationContext().getResources(),R.drawable.mavigol);
    Bitmap mavigol1= Resimkucultmek(mavigol,300);
    yerresimleri.add(mavigol1);

    Bitmap misirpiramit = BitmapFactory.decodeResource(getApplicationContext().getResources(),R.drawable.misirpiramitleri);
    Bitmap misirpiramit1= Resimkucultmek(misirpiramit,300);
    yerresimleri.add(misirpiramit1);

    Bitmap peribacasi = BitmapFactory.decodeResource(getApplicationContext().getResources(),R.drawable.peribacalari);
    Bitmap peribacasi1= Resimkucultmek(peribacasi,300);
    yerresimleri.add(peribacasi1);

    Bitmap peru = BitmapFactory.decodeResource(getApplicationContext().getResources(),R.drawable.peru);
    Bitmap peru1 = Resimkucultmek(peru,300);
    yerresimleri.add(peru1);

    Bitmap venedik = BitmapFactory.decodeResource(getApplicationContext().getResources(),R.drawable.venedi);
    Bitmap venedik1= Resimkucultmek(venedik,300);
    yerresimleri.add(venedik1);

    Bitmap roma = BitmapFactory.decodeResource(getApplicationContext().getResources(),R.drawable.roma);
    Bitmap roma1= Resimkucultmek(roma,300);
    yerresimleri.add(roma1);

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
