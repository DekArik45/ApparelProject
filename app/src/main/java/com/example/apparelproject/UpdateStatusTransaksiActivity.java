package com.example.apparelproject;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apparelproject.database.TransactionQuery;
import com.example.apparelproject.model.TransactionModel;
import com.example.apparelproject.utils.Config;

public class UpdateStatusTransaksiActivity extends AppCompatActivity {

    Spinner spinnerStatus;
    Button save;
    String status;
    String dataStatus,dataTanggal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_status_transaksi);

        ActionBar ab = getSupportActionBar();
        ab.setTitle("Detail Transaksi");
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowHomeEnabled(true);

        Intent data = getIntent();
        dataStatus = data.getStringExtra(Config.COLUMN_TRX_STATUS);
        dataTanggal = data.getStringExtra(Config.COLUMN_TRX_TANGGAL);

        spinnerStatus = findViewById(R.id.update_status_transaksi_status);
        save = findViewById(R.id.update_status_transaksi_save);

        spinnerStatus();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });
    }

    private void save(){

        TransactionQuery query = new TransactionQuery(getApplicationContext());

        long id =query.updateTransactionStatus(dataTanggal,status);

        if (id>0){
            Toast.makeText(getApplicationContext(), "Data Berhasil diUpdate", Toast.LENGTH_LONG).show();
            Intent intent = new Intent();
            intent.putExtra("status",status);
            setResult(Activity.RESULT_OK, intent);
            finish();
        }
        else{
            Toast.makeText(getApplicationContext(), "data Gagal Diupdate", Toast.LENGTH_LONG).show();
        }
    }

    private void spinnerStatus(){
        int position = 0;

        for (int i =0;i<getResources().getStringArray(R.array.status).length;i++){
            if (getResources().getStringArray(R.array.status)[i].equals(dataStatus)) {
                position = i;
            }
        }

        final ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.status));

        spinnerStatus.setAdapter(adapter1);
        status = adapter1.getItem(position);
        spinnerStatus.setSelection(position, true);
        View v = spinnerStatus.getSelectedView();
        ((TextView)v).setTextColor(getResources().getColor(R.color.md_black_1000));
        // mengeset listener untuk mengetahui saat item dipilih
        spinnerStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                status = adapter1.getItem(i);
                ((TextView) view).setTextColor(getResources().getColor(R.color.md_black_1000));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}
