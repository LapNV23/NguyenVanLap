package com.example.nguyenvanlap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    EditText etUsername, etDes, etGmail;
    Spinner planets_spinner;
    CheckBox ckBox;
    Button btnFeedback;
    String gender = "Male";
    AppDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etUsername = findViewById(R.id.etUsername);
        etGmail = findViewById(R.id.etGmail);
        etDes = findViewById(R.id.etDes);
        ckBox = findViewById(R.id.ckBox);
        btnFeedback = findViewById(R.id.btnFeedback);
        planets_spinner = findViewById(R.id.planets_spinner);
        db = AppDatabase.getAppDatabase(this);
        btnFeedback.setOnClickListener(this);

        initGender();
    }

    private void initGender() {
        String[] listGender = {"Male", "Female", "Unknown"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                listGender);
        planets_spinner.setAdapter(adapter);
        planets_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("TAG", "onItemClick: " + listGender[position]);
                gender = listGender[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnFeedback:
                onSeen();
                break;

            default:
                break;
        }
    }

    private void onSeen() {
        if (!validate()) {
            return;
        }
        User user = new User();
        user.username = etUsername.getText().toString();
        user.gmail = etGmail.getText().toString();
        user.des = etDes.getText().toString();
        user.gender = gender;
        long id = db.userDao().insertUser(user);
        if (id > 0) {
            makeTotal();
        }

    }

    private void makeTotal() {
        Toast.makeText(this, "Total : " + db.userDao().getAllUser().size(), Toast.LENGTH_LONG).show();
    }

    private boolean validate() {
        String mess = null;
        if (etUsername.getText().toString().trim().isEmpty()) {
            mess = "Username is empty!";
        } else if (etGmail.getText().toString().trim().isEmpty()) {
            mess = "Email is empty!";
        } else if (etDes.getText().toString().trim().isEmpty()) {
            mess = "Description is empty!";
        }
        if (mess != null) {
            Toast.makeText(this, mess, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

}