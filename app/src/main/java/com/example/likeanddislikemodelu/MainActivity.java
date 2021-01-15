package com.example.likeanddislikemodelu;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    Button btn;

    Animation scaleUp, scaleDown;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        btn = findViewById(R.id.button);
        scaleUp = AnimationUtils.loadAnimation(this,R.anim.scale_up);
        scaleDown = AnimationUtils.loadAnimation(this,R.anim.scale_down);


        btn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Intent intent = new Intent(MainActivity.this, SelectionScreen.class);
                startActivity(intent);

                if(event.getAction()==event.ACTION_DOWN){
                    btn.startAnimation(scaleUp);
                }else if(event.getAction()==event.ACTION_UP){
                    btn.startAnimation(scaleDown);

                }

                return true;
            }
        });
    }

    public void logout(View view) {
        mAuth.signOut();
        Intent intent = new Intent(MainActivity.this, ChooseLogginRegistration.class);
        startActivity(intent);
        finish();
        return;
    }

}