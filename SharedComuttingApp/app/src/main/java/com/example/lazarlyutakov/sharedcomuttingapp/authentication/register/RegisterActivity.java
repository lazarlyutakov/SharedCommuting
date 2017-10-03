package com.example.lazarlyutakov.sharedcomuttingapp.authentication.register;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lazarlyutakov.sharedcomuttingapp.MainActivity;
import com.example.lazarlyutakov.sharedcomuttingapp.R;
import com.example.lazarlyutakov.sharedcomuttingapp.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.Inflater;

import mehdi.sakout.fancybuttons.FancyButton;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "EmailPassword";

    private Button regBtn;
    private EditText passwordBox;
    private AutoCompleteTextView emailBox;
    private FirebaseAuth auth;
    private DatabaseReference database;
    private AutoCompleteTextView usernameBox;
    private AutoCompleteTextView firstNameBox;
    private AutoCompleteTextView lastNameBox;
    private AutoCompleteTextView phoneNumberBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        regBtn = (Button)findViewById(R.id.btn_submit_registration);
        regBtn.setOnClickListener(this);

        passwordBox = (EditText)findViewById(R.id.et_password_box);
        emailBox = (AutoCompleteTextView)findViewById(R.id.et_email_box);
        usernameBox = (AutoCompleteTextView)findViewById(R.id.et_username_box);
        firstNameBox = (AutoCompleteTextView)findViewById(R.id.et_first_name_box);
        lastNameBox = (AutoCompleteTextView)findViewById(R.id.et_last_name_box);
        phoneNumberBox = (AutoCompleteTextView)findViewById(R.id.et_phone_number_box);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    protected void onResume() {
        super.onResume();
        regBtn.setOnClickListener(this);


    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = auth.getCurrentUser();
    }

    @Override
    public void onClick(View view) {
        createAccount(emailBox.getText().toString(), passwordBox.getText().toString());
        Toast.makeText(this, emailBox.getText().toString(), Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "gotovo", Toast.LENGTH_SHORT).show();
    }

    private void createAccount(String email, String password) {

        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = auth.getCurrentUser();
                            User newUser = new User(usernameBox.getText().toString(),
                                    passwordBox.getText().toString(),
                                    firstNameBox.getText().toString(),
                                    lastNameBox.getText().toString(),
                                    phoneNumberBox.getText().toString(),
                                    emailBox.getText().toString()
                            );
                            String uId = user.getUid();
                           database.child("Users").child(uId).setValue(newUser);
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // [START_EXCLUDE]
                       // hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
        // [END create_user_with_email]
    }

    private void updateUI(FirebaseUser user) {
       // hideProgressDialog();
        if (user != null) {
            /*mStatusTextView.setText(getString(R.string.emailpassword_status_fmt,
                    user.getEmail(), user.isEmailVerified()));
            mDetailTextView.setText(getString(R.string.firebase_status_fmt, user.getUid()));*/

            FancyButton btnReg = (FancyButton)findViewById(R.id.btn_register);
            btnReg.setVisibility(View.GONE);

            FancyButton bntSignIn = (FancyButton)findViewById(R.id.btn_sign_in);
            bntSignIn.setVisibility(View.GONE);

            FancyButton btnLogOut = (FancyButton)findViewById(R.id.btn_logout);
            btnLogOut.setVisibility(View.VISIBLE);

            TextView signedUser = (TextView) findViewById(R.id.tv_signed_user);
            signedUser.setText(usernameBox.getText().toString() + " is signed in");

           // findViewById(R.id.verify_email_button).setEnabled(!user.isEmailVerified());
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

        } else {
            /*mStatusTextView.setText(R.string.signed_out);
            mDetailTextView.setText(null);*/

            /*findViewById(R.id.email_password_buttons).setVisibility(View.VISIBLE);
            findViewById(R.id.email_password_fields).setVisibility(View.VISIBLE);
            findViewById(R.id.signed_in_buttons).setVisibility(View.GONE);*/
        }
    }

}
