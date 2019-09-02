package com.example.espressosecond;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.espressosecond.Utils.EspressoIdlingResource;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button startButton = findViewById(R.id.button);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EspressoIdlingResource.increment(); // stops Espresso tests from going forward
                FirebaseAuth.getInstance().createUserWithEmailAndPassword("EMAIL", "PASSWORD")
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception command) {
                                startButton.setText("Failed");
                            }
                        })
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                EspressoIdlingResource.decrement(); // Tells Espresso test to resume
                                if (task.isSuccessful()) {
                                    startButton.setText("Success");
                                } else {
                                    startButton.setText("Failed");
                                }
                            }
                        });
            }
        });
    }
}
