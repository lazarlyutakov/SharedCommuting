package com.example.lazarlyutakov.sharedcomuttingapp.authentication.login;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lazarlyutakov.sharedcomuttingapp.R;
import com.example.lazarlyutakov.sharedcomuttingapp.authentication.LoggedInActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth auth;
    private EditText etPassword;
    private AutoCompleteTextView tvEmail;
    private Button btnSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();

        etPassword = (EditText)findViewById(R.id.et_password_for_sign_in);
        tvEmail = (AutoCompleteTextView)findViewById(R.id.tv_email_for_sign_in);
        btnSignIn = (Button)findViewById(R.id.email_sign_in_button);

        btnSignIn.setOnClickListener(this);

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = auth.getCurrentUser();
        updateUI(currentUser);
    }

    @Override
    protected void onResume() {
        super.onResume();
        btnSignIn.setOnClickListener(this);

    }

    private void signIn(String email, String password) {
        if (!validateForm()) {
            return;
        }


        // showProgressDialog();

        // [START sign_in_with_email]
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            FirebaseUser user = auth.getCurrentUser();
                            updateUI(user);
                        } else {
                            Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // [START_EXCLUDE]
                        /*if (!task.isSuccessful()) {
                            mStatusTextView.setText(R.string.auth_failed);
                        }*/
                       // hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
        // [END sign_in_with_email]
    }

    private boolean validateForm() {
        boolean valid = true;

        String email = tvEmail.getText().toString();
        if (TextUtils.isEmpty(email)) {
            tvEmail.setError("Required.");
            valid = false;
        } else {
            tvEmail.setError(null);
        }

        String password = etPassword.getText().toString();
        if (TextUtils.isEmpty(password)) {
            etPassword.setError("Required.");
            valid = false;
        } else {
            etPassword.setError(null);
        }

        return valid;
    }

    private void updateUI(FirebaseUser user) {
       // hideProgressDialog();
        if (user != null) {
            Intent intent = new Intent(this, LoggedInActivity.class);
            intent.putExtra("username", user.getEmail());
            startActivity(intent);
        } else {
            Toast.makeText(this, "Please, sign in or register", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View view) {
        signIn(tvEmail.getText().toString(), etPassword.getText().toString());
    }
}
