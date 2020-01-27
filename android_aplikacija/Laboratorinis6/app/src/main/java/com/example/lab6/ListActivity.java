package com.example.lab6;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lab6.ds.Project;
import com.example.lab6.ds.User;
import com.example.lab6.helpers.TinkloKontrolleris;
import com.example.lab6.ds.Project;
import com.example.lab6.ds.User;
import com.example.lab6.helpers.TinkloKontrolleris;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    private User user = null;

    public void profilis (View view){
        Intent intent = new Intent(ListActivity.this, com.example.lab6.Profilis.class);
        intent.putExtra("user", user);
        startActivity(intent);
        finish();
    }

    public void atsijungti(View v) {
        Intent intent = new Intent(ListActivity.this, com.example.lab6.MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Intent dabar = this.getIntent();
        User prisijunges = (User)dabar.getSerializableExtra("user");
        user = prisijunges;
        Toast.makeText(ListActivity.this, "Dabar prisijunges "+prisijunges.getLogin(), Toast.LENGTH_LONG).show();
        GetUserList prisijungti = new GetUserList(prisijunges);
        prisijungti.execute();
    }

    private final class GetUserList extends AsyncTask<String, String, String> {
        private User user = null;

        GetUserList(User prisijunges) {
            this.user = prisijunges;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(String... params) {
            int id = user.getId();
            String url ="http://158.129.232.149:8080/lab5/projects_" + id + ".htm";
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
                    Type listType = new TypeToken<List<Project>>(){}.getType();
                    List<Project> projects = new Gson().fromJson(result, listType);
                    ListView sar = findViewById(R.id.sarasas);
                    ArrayAdapter<Project> arrayAdapter = new ArrayAdapter<>
                            (ListActivity.this, android.R.layout.simple_list_item_1, projects);
                    sar.setAdapter(arrayAdapter);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(ListActivity.this, "Neteisingi duomenys", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(ListActivity.this, "Neteisingi duomenys", Toast.LENGTH_LONG).show();
            }
        }
    }
}