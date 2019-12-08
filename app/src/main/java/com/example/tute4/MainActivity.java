package com.example.tute4;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import Database.DBHelper;

public class MainActivity extends AppCompatActivity {

    private EditText et_username, et_password;
    private Button btn_selectAll, btn_update, btn_delete, btn_signIn, btn_add;

    private DBHelper dbHelper;

    private String un, pw;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DBHelper(this);

        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);

        btn_add = findViewById(R.id.btn_add);
        btn_signIn = findViewById(R.id.btn_signIn);
        btn_selectAll = findViewById(R.id.btn_selectAll);
        btn_update = findViewById(R.id.btn_update);
        btn_delete = findViewById(R.id.btn_delete);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                un = et_username.getText().toString().trim();
                pw = et_password.getText().toString().trim();

                dbHelper.addInfo(un, pw);

                clearData();
                Toast.makeText(MainActivity.this, "Data added successfully", Toast.LENGTH_SHORT).show();
            }
        });

        btn_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                un = et_username.getText().toString().trim();
                pw = et_password.getText().toString().trim();

                boolean valid = dbHelper.validate(un, pw);
                //System.out.println(valid);

                if (valid) {
                    clearData();

                    Toast.makeText(MainActivity.this, "Signed in successfully", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_selectAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //dbHelper.readAllInfo();

                Intent intent = new Intent(MainActivity.this, ListActivity.class);
                startActivity(intent);
            }
        });

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                un = et_username.getText().toString().trim();
                pw = et_password.getText().toString().trim();

                boolean upd = dbHelper.updateInfo(un, pw);

                if (upd) {
                    clearData();
                    Toast.makeText(MainActivity.this, "Updated successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Invalid username", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                un = et_username.getText().toString().trim();

                boolean del = dbHelper.deleteInfo(un);

                if (del) {
                    clearData();
                    Toast.makeText(MainActivity.this, "Deleted successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Invalid username", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void clearData() {
        et_username.setText("");
        et_password.setText("");
    }
}
