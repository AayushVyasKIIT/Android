package com.example.salebuy;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.salebuy.Handlers.DatabaseHelper;
import com.example.salebuy.Handlers.HttpHandler;
import com.example.salebuy.Models.User;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = HttpHandler.class.getSimpleName();
    private final AppCompatActivity activity = RegisterActivity.this;
    private EditText textInputEditTextName;
    private EditText textInputEditTextEmail;
    private EditText textInputEditTextPassword;
    private EditText textInputEditTextConfirmPassword;
    private Button signup;

    private DatabaseHelper databaseHelper;
    private User user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();
        initViews();
        initListeners();
        initObjects();
    }
    /**
     * This method is to initialize views
     */
    private void initViews() {

        textInputEditTextName = (EditText) findViewById(R.id.editTextTextEmailLogin);
        textInputEditTextEmail = (EditText) findViewById(R.id.editTextPasswordLogin);
        textInputEditTextPassword = (EditText) findViewById(R.id.editTextTextPassword);

        signup = (Button) findViewById(R.id.signupButton);
    }
    /**
     * This method is to initialize listeners
     */
    private void initListeners() {
        signup.setOnClickListener(this);
    }
    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
        databaseHelper = new DatabaseHelper(activity);
        user = new User();
    }
    /**
     * This implemented method is to listen the click on view
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signupButton:
                postDataToSQLite();
                break;
        }
    }
    /**
     * This method is to validate the input text fields and post data to SQLite
     */
    private void postDataToSQLite() {

            if (!databaseHelper.checkUser(textInputEditTextEmail.getText().toString().trim())) {
                user.setName(textInputEditTextName.getText().toString().trim());
                user.setEmail(textInputEditTextEmail.getText().toString().trim());
                user.setPassword(textInputEditTextPassword.getText().toString().trim());
                databaseHelper.addUser(user);
                Log.e(TAG, "Successfully registered");
                emptyInputEditText();
                Intent intent = new Intent(this,LoginActivity.class);
                startActivity(intent);
            } else {
                // Snack Bar to show error message that record already exists
                Log.e(TAG, "failed to register");
            }
    }
    /**
     * This method is to empty all input edit text
     */
    private void emptyInputEditText() {
        textInputEditTextName.setText(null);
        textInputEditTextEmail.setText(null);
        textInputEditTextPassword.setText(null);
        textInputEditTextConfirmPassword.setText(null);
    }
}