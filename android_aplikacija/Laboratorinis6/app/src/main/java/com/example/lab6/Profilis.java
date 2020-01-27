package com.example.lab6;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.lab6.ds.Person;
import com.example.lab6.ds.User;
import com.example.lab6.helpers.TinkloKontrolleris;
import com.google.gson.Gson;

public class Profilis extends AppCompatActivity {
    private User user = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profilis);

        Intent dabar = this.getIntent();
        User prisijunges = (User)dabar.getSerializableExtra("user");
        user = prisijunges;
        UserInformation prisijungti = new UserInformation(prisijunges);
        prisijungti.execute();
    }

    public void atsijungti(View v) {
        Intent intent = new Intent(Profilis.this, com.example.lab6.MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void projektai (View view){
        Intent intent = new Intent(Profilis.this, com.example.lab6.Profilis.class);
        intent.putExtra("user", user);
        startActivity(intent);
        finish();
    }

    @SuppressLint("StaticFieldLeak")
    private final class UserInformation extends AsyncTask<String, String, String> {
        private User user = null;

        UserInformation(User prisijunges) {
            this.user = prisijunges;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            int id = user.getId();
            String url ="http://158.129.232.149:8080/lab5/userInfo_" + id + ".htm";
            try {
                return TinkloKontrolleris.sendGet(url);
            } catch (Exception e) {
                e.printStackTrace();
                return "Nepavyko gauti duomenu is web";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            System.out.println("GAUTA: " + result);
            if (result != null) {
                try {
                    Person person = new Gson().fromJson(result, Person.class);
                    TextView name = findViewById(R.id.nameField);
                    TextView login = findViewById(R.id.loginField);
                    TextView pass = findViewById(R.id.passField);

                    name.setText(person.getName());
                    login.setText(user.getLogin());
                    pass.setText(person.getPass());

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
