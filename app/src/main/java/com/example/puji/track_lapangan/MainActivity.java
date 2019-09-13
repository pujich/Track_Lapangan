package com.example.puji.track_lapangan;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);

        final EditText editText_login_username = (EditText) findViewById(R.id.editText_login_username);
        final EditText editText_login_password = (EditText) findViewById(R.id.editText_login_password);

        Button button_login_login = (Button) findViewById(R.id.btnlogin);

        button_login_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String[] var = new String[2];
                    var[0]  = editText_login_username.getText().toString();
                    var[1]  = editText_login_password.getText().toString();


                    validateUserTask task = new validateUserTask();
                    task.execute(var);


                } catch (Exception ex) {
                        ex.printStackTrace();
                        System.out.print("onclicklistener");
                }

            }





        });
}
    class validateUserTask extends AsyncTask<String, Void, String> {
        String res;
        @Override
        protected String doInBackground(String... params) {
            ApiRequest api = new ApiRequest();


            try {
                System.out.println(params[0]);
                System.out.println(params[1]);
                String result = api.sendPOST_login(params[0],params[1]);

                if(result.equalsIgnoreCase("Wrong")){
                    res=result;
                }
                else if(result.equalsIgnoreCase("Failed")){
                    res=result;
                }
                else{JSONObject jsonChildNode= new JSONObject(result);

                    res = jsonChildNode.optString("ID_USER");
                    System.out.println("id: "+res);}

            }
            catch (Exception e) {
                e.printStackTrace();
            }
            return res;

        }//close doInBackground


        protected void onPostExecute(String res) {
            if (res.equalsIgnoreCase("Failed")){
                Toast.makeText(MainActivity.this, "Login gagal",
                        Toast.LENGTH_LONG).show();
            } else if (res.equalsIgnoreCase("Wrong")){
                Toast.makeText(MainActivity.this, "Username/password salah",
                        Toast.LENGTH_LONG).show();
            }
            else{
                SharedPreferences session = getSharedPreferences("Session", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = session.edit();
                editor.putString("id_user", res);
                editor.commit();

                System.out.print("id" + res);

                Handler m = new Handler();
                m.post(new Runnable() {
                    @Override
                    public void run() {
                        Intent myIntent = new Intent(MainActivity.this, FindAP.class);
                        startActivity(myIntent);
                    }
                });
            }
        }//close onPostExecute
    }// close validateUserTask



    }

