package com.example.salebuy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.salebuy.Handlers.DatabaseHelper;
import com.example.salebuy.Handlers.HttpHandler;
import com.example.salebuy.Handlers.authenticator;
import com.example.salebuy.Models.User;

import java.util.List;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = HttpHandler.class.getSimpleName();

    private EditText textInputEditTextEmail;
    private EditText textInputEditTextPassword;
    private Button Login;
    private Button SignUp;
    private DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        initViews();
        initListeners();
        initObjects();
    }
    /**
     * This method is to initialize views
     */
    private void initViews() {

        textInputEditTextEmail = (EditText) findViewById(R.id.editTextTextEmailLogin);
        textInputEditTextPassword = (EditText) findViewById(R.id.editTextPasswordLogin);
        Login = (Button) findViewById(R.id.Login);
        SignUp  = (Button) findViewById(R.id.signupButton);
    }
    /**
     * This method is to initialize listeners
     */
    private void initListeners() {
        Login.setOnClickListener(this);
        SignUp.setOnClickListener(this);
    }
    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
        databaseHelper = new DatabaseHelper(this);
    }
    /**
     * This implemented method is to listen the click on view
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Login:
                try {
                    if(verifyFromSQLite()){
                        Intent intentLogin = new Intent(LoginActivity.this, allItemsDisplay.class);
                        String email = textInputEditTextEmail.getText().toString();
                        intentLogin.putExtra("email" ,email);
                        startActivity(intentLogin);
                    }else{
                        Toast.makeText(this, "Enter valid username or password", Toast.LENGTH_SHORT).show();
                    }

                    break;
                } catch (Exception e) {
                    Log.e(TAG,"Login verification error");
                }

            case R.id.signupButton:
                // Navigate to RegisterActivity
                Intent intentRegister = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intentRegister);
                break;
        }
    }

    /**
     * This method is to validate the input text fields and verify login credentials from SQLite
     */
    private boolean verifyFromSQLite() {
        List<User> usersList = databaseHelper.getAllUser();
        authenticator authenticatorObj = new authenticator(usersList);
        String email = textInputEditTextEmail.getText().toString();

        if(authenticatorObj.checkEmailExists(email) == -1){
            Log.e(TAG,"Email dont exists");
            return false;
        }
        String password = textInputEditTextPassword.getText().toString();
        if(authenticatorObj.Authenticate(email,password) == true){
            Log.e(TAG,"password check failed");
            return true;
        }
        return false;
    }

    /**
     * This method is to empty all input edit text
     */
    private void emptyInputEditText() {
        textInputEditTextEmail.setText(null);
        textInputEditTextPassword.setText(null);
    }
}