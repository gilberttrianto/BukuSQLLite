package com.glbrt.bukusqllite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class TambahActivity extends AppCompatActivity {

    private EditText etJudul, etPenulis, etTahun;
    private Button btnSimpan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah);

        etJudul = findViewById(R.id.et_judul);
        etPenulis = findViewById(R.id.et_penulis_buku);
        etTahun = findViewById(R.id.et_tahun);

        btnSimpan = findViewById(R.id.btn_simpan);

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getJudul = etJudul.getText().toString();
                String getPenulis = etPenulis.getText().toString();
                String getTahun = etTahun.getText().toString();

                if (getJudul.trim().equals("")) {
                    etJudul.setError("Judul tidak boleh kosong");
                }
                else if (getPenulis.trim().equals("")) {
                    etPenulis.setError("Penulis tidak boleh kosong");
                }
                else if (getTahun.trim().equals("")) {
                    etTahun.setError("Tahun terbit tidak boleh kosong");
                }
                else{
                    MyDatabaseHelper myDB = new MyDatabaseHelper(TambahActivity.this);
                    long eksekusi = myDB.tambahBuku(getJudul, getPenulis, Integer.valueOf(getTahun));

                    if (eksekusi == -1) {
                        Toast.makeText(TambahActivity.this, "Gagal Menambah Data !", Toast.LENGTH_SHORT).show();
                        etJudul.requestFocus();
                    } else {
                        Toast.makeText(TambahActivity.this, "Tambah Data Berhasil", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            }
        });

    }
}