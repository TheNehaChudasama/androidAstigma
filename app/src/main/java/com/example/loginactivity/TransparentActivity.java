package com.example.loginactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import static android.content.ContentValues.TAG;

public class TransparentActivity extends Activity {
Button btnTransTap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transparent);

        btnTransTap = (Button) findViewById(R.id.btnTransTap);
        btnTransTap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: Trans");
                Toast.makeText(TransparentActivity.this, "lllllll", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
