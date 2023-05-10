package com.glbrt.bukusqllite;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton tambah;
    private RecyclerView rvBuku;
    MyDatabaseHelper myDB;
    AdapterBuku adapterBuku;
    ArrayList<String> arrID, arrJudul, arrPenulis, arrTahun;
    public static int posisiData = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tambah = findViewById(R.id.fab_tambah);

        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TambahActivity.class));
            }
        });
    }

    private  void  simpanDatakeArrayList() {
        Cursor cursor= myDB.bacaSemuaData();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "Tidak ada data", Toast.LENGTH_SHORT).show();
        }
        else {
            while (cursor.moveToNext()) {
                arrID.add(cursor.getString(0));
                arrJudul.add(cursor.getString(1));
                arrPenulis.add(cursor.getString(2));
                arrTahun.add(cursor.getString(3));
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        rvBuku = findViewById(R.id.rv_buku);

        myDB = new MyDatabaseHelper(MainActivity.this);
        arrID = new ArrayList<>();
        arrJudul = new ArrayList<>();
        arrPenulis = new ArrayList<>();
        arrTahun = new ArrayList<>();

        simpanDatakeArrayList();

        adapterBuku = new AdapterBuku(MainActivity.this, arrID, arrJudul,arrPenulis, arrTahun);
        rvBuku.setAdapter(adapterBuku);
        rvBuku.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        rvBuku.smoothScrollToPosition(posisiData);
    }
}