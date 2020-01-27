package com.example.lab6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lab6.ds.User;
import com.example.lab6.helpers.TinkloKontrolleris;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void login(View v) {
        EditText log = findViewById(R.id.loginField);
        EditText pas = findViewById(R.id.passField);
        String login = log.getText().toString();
        String pass = pas.getText().toString();
        String send = "{\"login\":\"" + login + "\", \"pass\":\"" + pass + "\"}";
        UserLogin con = new UserLogin();
        con.execute(send);
    }

    private final class UserLogin extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(MainActivity.this, "Tikrinami prisijungimo duomenys", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected String doInBackground(String... params) {
            String url = "http://158.129.232.149:8080/lab5/login.htm";
            String postDataParams = params[0];
            System.out.println("ISSIUSTA: " + postDataParams);
            try {
                return TinkloKontrolleris.sendPost(url, postDataParams);
            } catch (Exception e) {
                e.printStackTrace();
                return "nepavyko gauti duomenu is web";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            System.out.println("GAUTA: " + result);
            Toast.makeText(MainActivity.this, result, Toast.LENGTH_LONG).show();
            if (result != null && result.contains("{")) {
                Gson parseris = new Gson();
                try {
                    User gautas = parseris.fromJson(result, User.class);
                    Intent intent = new Intent(MainActivity.this, com.example.lab6.ListActivity.class);
                    intent.putExtra("user", gautas);
                    startActivity(intent);
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "Neteisingi duomenys", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(MainActivity.this, "Neteisingi duomenys", Toast.LENGTH_LONG).show();
            }
        }
    }
}
