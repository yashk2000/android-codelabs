package com.yashk2000.simpleasynctask;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView mTextView;
    private static final String TEXT_STATE = "currentText";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = findViewById(R.id.textView1);
        mTextView.setText("Napping...");
        if(savedInstanceState!=null) {
            mTextView.setText(savedInstanceState.getString(TEXT_STATE));
        }
        new SimpleAsyncTask(mTextView).execute();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(TEXT_STATE, mTextView.getText().toString());
    }

    public void startTask(View view) {
    }

    public class SimpleAsyncTask extends AsyncTask<Void, Void, String> {

        private WeakReference<TextView> mTextView;

        SimpleAsyncTask(TextView tv) {
            mTextView = new WeakReference<>(tv);
        }

        @Override
        protected String doInBackground(Void... voids) {
            Random r = new Random();
            int n = r.nextInt(11);

            int s = n * 200;

            try {
                Thread.sleep(s);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return "Awake at last after sleeping for " + s + " milliseconds!";
        }

        protected void onPostExecute(String result) {
            mTextView.get().setText(result);
        }
    }

}
