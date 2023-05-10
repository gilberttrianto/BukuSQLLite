package com.glbrt.bukusqllite;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterBuku extends RecyclerView.Adapter<AdapterBuku.ViewHolderBuku> {

    private Context mContext;
    private ArrayList arrID, arrJudul, arrPenulis, arrTahun;

    public AdapterBuku(Context mContext, ArrayList arrID, ArrayList arrJudul, ArrayList arrPenulis, ArrayList arrTahun) {
        this.mContext = mContext;
        this.arrID = arrID;
        this.arrJudul = arrJudul;
        this.arrPenulis = arrPenulis;
        this.arrTahun = arrTahun;
    }

    @NonNull
    @Override
    public ViewHolderBuku onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.card_item, parent, false);
        return new ViewHolderBuku(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderBuku holder,  int position) {
        holder.tvId.setText(arrID.get(position).toString());
        holder.tvjudul.setText(arrJudul.get(position).toString());
        holder.tvTahun.setText(arrTahun.get(position).toString());
        holder.tvPenulis.setText(arrPenulis.get(position).toString());

        holder.CVBuku.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder jendelaPesan = new AlertDialog.Builder(mContext);
                jendelaPesan.setMessage("Pilih perintah yang diinginkan!");
                jendelaPesan.setTitle("Perhatian");
                jendelaPesan.setCancelable(true);

                jendelaPesan.setNegativeButton("Hapus", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MyDatabaseHelper myDb = new MyDatabaseHelper(mContext);
                        long eksekusi = myDb.hapusBuku(arrID.get(position).toString());

                        if (eksekusi == -1) {
                            Toast.makeText(mContext, "Gagal menghapus Data", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(mContext,"Data berhasil di hapus", Toast.LENGTH_SHORT).show();
                            if (position == 0 ) {
                                MainActivity.posisiData = 0;
                            } else {
                                MainActivity.posisiData = position - 1;
                            }
                            dialog.dismiss();
                            ((MainActivity) mContext).onResume();
                        }
                    }
                });

                jendelaPesan.setPositiveButton("Ubah", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent pindah = new Intent(mContext, UbahActivity.class);
                        pindah.putExtra("varID",arrID.get(position).toString());
                        pindah.putExtra("varJudul",arrJudul.get(position).toString());
                        pindah.putExtra("varPenulis",arrPenulis.get(position).toString());
                        pindah.putExtra("varTahun",arrTahun.get(position).toString());
                        pindah.putExtra("varPosisi",position);
                        mContext.startActivity(pindah);
                    }
                });

                jendelaPesan.show();
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrJudul.size();
    }

    public class ViewHolderBuku extends RecyclerView.ViewHolder {

        TextView tvId, tvjudul, tvPenulis, tvTahun;

        CardView CVBuku;

        public ViewHolderBuku(@NonNull View itemView) {
            super(itemView);

            tvId = itemView.findViewById(R.id.tv_id);
            tvjudul = itemView.findViewById(R.id.tv_judul);
            tvPenulis = itemView.findViewById(R.id.tv_penulis);
            tvTahun = itemView.findViewById(R.id.tv_tahun);
            CVBuku = itemView.findViewById(R.id.cv_buku);
        }
    }
}
