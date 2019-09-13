package com.example.puji.track_lapangan;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class FindAP extends AppCompatActivity implements View.OnClickListener {

    private Button scanBtn;
    private Button manualBtn;
    private EditText editText;
    final ApiRequest api = new ApiRequest();
    boolean exit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_ap);


        scanBtn = (Button)findViewById(R.id.buttonScan);
        scanBtn.setOnClickListener(this);

        manualBtn = (Button)findViewById(R.id.buttonManual);
        manualBtn.setOnClickListener(this);

        SharedPreferences session = getApplicationContext().getSharedPreferences("Session", Context.MODE_PRIVATE);
        System.out.print(session.getString("id_user", "DEFAULT"));

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.buttonScan){
            IntentIntegrator scanIntegrator = new IntentIntegrator(this);
            scanIntegrator.initiateScan();
        }
        if(v.getId()==R.id.buttonManual){
            editText = (EditText)findViewById(R.id.editText_Mac);
            String AP = editText.getText().toString();
            getAP get=new getAP();
            get.execute(AP);
        }

    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);

        if (scanningResult != null) {
            String scanContent = scanningResult.getContents();

            getAP get=new getAP();
            get.execute(scanContent);

        }
        else{
            Toast toast = Toast.makeText(getApplicationContext(),
                    "No scan data received!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    @Override
    public void onBackPressed() {
        if (exit) {
            finish(); // finish activity
        } else {
            Toast.makeText(this, "Tekan kembali untuk keluar.",
                    Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 2000);

        }
    }

    class getAP extends AsyncTask<String, Void, String> {
        String res;
        @Override
        protected String doInBackground(String... params) {

            try {
                System.out.println(params);
                res = api.sendPOST_AP(params[0]);
                System.out.println(res);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            return res;

        }//close doInBackground


        protected void onPostExecute(final String res) {
            if (res!=null){
                Handler m = new Handler();
                m.post(new Runnable() {
                    @Override
                    public void run() {
                        Intent myIntent = new Intent(FindAP.this, ChangeStatus.class);
                        myIntent.putExtra("data", res);
                        startActivity(myIntent);
                    }
                });
            } else {System.out.println(res+" : Error");
                Toast.makeText(FindAP.this, "Data tidak ditemukan atau tidak tersambung dengan API",
                        Toast.LENGTH_LONG).show();
            }
        }//close onPostExecute
    }// close validateUserTask

}
