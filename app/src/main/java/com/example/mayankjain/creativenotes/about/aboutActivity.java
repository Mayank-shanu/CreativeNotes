package com.example.mayankjain.creativenotes.about;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.mayankjain.creativenotes.R;

public class aboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        setTitle("About");

        ((TextView)findViewById(R.id.suggestion)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/html");
                intent.putExtra(Intent.EXTRA_EMAIL,new String[]{"jmayank615@gmail.com"});
                intent.putExtra(Intent.EXTRA_TEXT,"To \nMayank Jain\n suggestion/experience regarding/with Creative Notes V 1.0 ");
                intent.putExtra(Intent.EXTRA_SUBJECT,"suggestion/experience ");
                startActivity(intent);

            }
        });
    }
}
