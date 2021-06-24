package com.sseung.slidingpuzzle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class EntrolmentActivity extends Activity {

    EditText edit_id;
    TextView text_alarm, login_ok;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrolment);

        edit_id = findViewById(R.id.edit_id);
        text_alarm = findViewById(R.id.text_alarm);
        login_ok = findViewById(R.id.login_ok);

        edit_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text_alarm.setText("");
            }
        });

        login_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edit_id.getText().toString().equals("")){
                    text_alarm.setText("*");
                } else {
                    PublicFunction.addData(edit_id.getText().toString());

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);

                    overridePendingTransition(R.anim.image_in, R.anim.image_out);

                    finish();
                }
            }
        });
    }
}
