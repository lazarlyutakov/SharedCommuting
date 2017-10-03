package com.example.lazarlyutakov.sharedcomuttingapp.authentication.register;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.example.lazarlyutakov.sharedcomuttingapp.R;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private Button regBtn;
    private String tbValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        regBtn = (Button)findViewById(R.id.btn_submit_registration);
        regBtn.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        regBtn.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        AutoCompleteTextView username = (AutoCompleteTextView)findViewById(R.id.et_username_box);
        tbValue = username.getText().toString();
        Toast.makeText(this, tbValue + "", Toast.LENGTH_SHORT).show();
        System.out.println("HERE " + tbValue);

    }
}
