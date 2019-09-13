package com.example.puji.track_lapangan;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.crypto.Mac;

public class ChangeStatus extends AppCompatActivity implements View.OnClickListener {

    EditText editTextAlamat;
    EditText editTextAlamat2;
    EditText editTextAlamat3;
    EditText editTextAlamat4;
    EditText editTextAlasan;
    EditText editTextKeterangan;
    Spinner spinTipe;
    Spinner spinStatus;

    String Tipe;
    String Status;
    String Alamat;
    String Alamat2;
    String Alamat3;
    String Alamat4;
    String Alasan;
    String Keterangan;
    String MacAddress;
    private static final String[] dropTipe = {"INTERNAL", "EXTERNAL"};
    private static final String[] dropStatus = {"READY", "REPAIR", "HILANG", "ON AIR", "SCRAPPED", "DISMANTLED"};

    private Button updateBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_status);
        getSupportActionBar().setTitle("Update");

        String Json= getIntent().getStringExtra("data");
        JSONArray jsonResponse;
        JSONObject jsonChildNode=null;
        try {
            jsonResponse = new JSONArray(Json);
            jsonChildNode = jsonResponse.getJSONObject(0);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        String SerialNumber = jsonChildNode.optString("AP_SN");
        MacAddress = jsonChildNode.optString("MAC_ADDRESS");
        String Merk = jsonChildNode.optString("MERK");
        Tipe = jsonChildNode.optString("TIPE");
        Status = jsonChildNode.optString("STATUS");
        Alamat = jsonChildNode.optString("ALAMAT");
        Alamat2 = jsonChildNode.optString("ALAMAT2");
        Alamat3 = jsonChildNode.optString("ALAMAT3");
        Alamat4 = jsonChildNode.optString("ALAMAT4");
        Alasan = jsonChildNode.optString("ALASAN");
        Keterangan = jsonChildNode.optString("KETERANGAN");


        spinTipe = (Spinner) findViewById(R.id.spinnerTextTipe);
        ArrayAdapter<String>adapter1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,dropTipe);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinTipe.setAdapter(adapter1);
        ArrayAdapter adapTipe = (ArrayAdapter) spinTipe.getAdapter();
        int posiTipe = adapTipe.getPosition(Tipe);
        spinTipe.setSelection(posiTipe);

        spinStatus = (Spinner) findViewById(R.id.spinnerTextStatus);
        ArrayAdapter<String>adapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,dropStatus);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinStatus.setAdapter(adapter2);
        ArrayAdapter adapStatus = (ArrayAdapter) spinStatus.getAdapter();
        int posiStatus = adapStatus.getPosition(Status);
        spinStatus.setSelection(posiStatus);

        editTextAlamat = (EditText)findViewById(R.id.editTextAlamat);
        editTextAlamat.setText(Alamat, TextView.BufferType.EDITABLE);

        editTextAlamat2 = (EditText)findViewById(R.id.editTextAlamat2);
        editTextAlamat2.setText(Alamat2, TextView.BufferType.EDITABLE);

        editTextAlamat3 = (EditText)findViewById(R.id.editTextAlamat3);
        editTextAlamat3.setText(Alamat3, TextView.BufferType.EDITABLE);

        editTextAlamat4 = (EditText)findViewById(R.id.editTextAlamat4);
        editTextAlamat4.setText(Alamat4, TextView.BufferType.EDITABLE);

        editTextAlasan = (EditText)findViewById(R.id.editTextAlasan);
        editTextAlasan.setText(Alasan, TextView.BufferType.EDITABLE);

        editTextKeterangan = (EditText)findViewById(R.id.editTextKeterangan);
        editTextKeterangan.setText(Keterangan, TextView.BufferType.EDITABLE);

        TextView textSN = (TextView)findViewById(R.id.textSerialNumber);
        textSN.setText(SerialNumber);

        TextView textMac = (TextView)findViewById(R.id.textMacAddress);
        textMac.setText(MacAddress);

        TextView textMerk = (TextView)findViewById(R.id.textMerk);
        textMerk.setText(Merk);

        updateBtn = (Button)findViewById(R.id.buttonUpdate);
        updateBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {


        String[] var = new String[10];

        if(v.getId()==R.id.buttonUpdate){
            SharedPreferences session = getSharedPreferences("Session", Context.MODE_PRIVATE);

            var[0] = spinTipe.getSelectedItem().toString();
            var[1] = spinStatus.getSelectedItem().toString();
            var[2] = editTextAlamat.getText().toString();
            var[3] = editTextAlamat2.getText().toString();
            var[4] = editTextAlamat3.getText().toString();
            var[5] = editTextAlamat4.getText().toString();
            var[6] = editTextAlasan.getText().toString();
            var[7] = editTextKeterangan.getText().toString();
            var[8] = session.getString("id_user","");
            var[9] = MacAddress;

            System.out.println(var);

            updateAP update = new updateAP();
            update.execute(var);


        }

    }

    class updateAP extends AsyncTask<String, Void, String> {
        String res;
        @Override
        protected String doInBackground(String... params) {
        ApiRequest api = new ApiRequest();
            try {
                System.out.println(params);
                res = api.sendUPDATE_AP(params);
                System.out.println(res);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            return res;

        }//close doInBackground


        protected void onPostExecute(final String res) {
            if (res!=null){

                AlertDialog.Builder builder1 = new AlertDialog.Builder(ChangeStatus.this);
                builder1.setMessage("AP telah berhasil diupdate.");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();

            } else {System.out.println(res+" : Error");
                Toast.makeText(ChangeStatus.this, "Update gagal, silahkan coba lagi",
                        Toast.LENGTH_LONG).show();
            }
        }//close onPostExecute
    }// close validateUserTask

}
