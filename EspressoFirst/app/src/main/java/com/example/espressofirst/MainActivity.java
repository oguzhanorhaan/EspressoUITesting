package com.example.espressofirst;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.inputField);
    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.changeText:
                editText.setText("Lalala");
                Toast.makeText(this,"Example",Toast.LENGTH_LONG).show();
                break;
            case R.id.switchActivity:
                Intent intent = new Intent(this, SecondActivity.class);
                intent.putExtra("input", editText.getText().toString());
                intent.putExtra("Developer","Oguzhan Orhan");
                startActivity(intent);
                break;
            case R.id.asyncButton:
                editText.setText("Running");
                myTask.execute("test");
        }

    }

    @SuppressLint("StaticFieldLeak")
    final AsyncTask<String, Void, String> myTask = new AsyncTask<String, Void, String>() {

        @Override
        protected String doInBackground(String... arg0) {
            return "Long running stuff";
        }

        @Override
        protected void onPostExecute(String result) {
            TextView textView = (TextView) findViewById(R.id.inputField);
            textView.setText("Done");
        }
    };
}
