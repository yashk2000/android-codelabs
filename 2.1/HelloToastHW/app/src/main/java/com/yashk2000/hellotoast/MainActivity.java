package com.yashk2000.hellotoast;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView count;
    private int c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        count = findViewById(R.id.show_count);
    }

    public void showToast(View view) {
        Intent intent = new Intent(this, Main2Activity.class);
        intent.putExtra("Score", c);
        Toast.makeText(getApplicationContext(), "Hello", Toast.LENGTH_LONG).show();
        startActivity(intent);
    }

    public void count(View view) {
        ++c;
        if(count !=null) {
            count.setText(Integer.toString(c));
        }
    }
}
