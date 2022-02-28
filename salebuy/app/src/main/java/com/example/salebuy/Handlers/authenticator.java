package com.example.salebuy.Handlers;

import android.util.Log;

import com.example.salebuy.Models.User;

import java.util.List;

public class authenticator {
    List<User> users;
    private static final String TAG = HttpHandler.class.getSimpleName();

    public authenticator(List<User> users) {
        this.users = users;
    }

    public boolean Authenticate(String email, String password){
        if(checkEmailExists(email) == -1){
            return false;
        }
        Log.e(TAG, Integer.toString(checkEmailExists(email)));;
        String passwordDB = users.get(checkEmailExists(email)).getPassword();
        if(passwordDB.equals(password)) {
            return true;
        }
        return false;
    }

    public int checkEmailExists(String email){
        int n = users.size();
        int position = -1;
        for(int i = 0; i < n;i++){
            String emailDB = users.get(i).getEmail();
            if(emailDB.equals(email)){
                position = i;
                return position;
            }
        }
        return position;
    }
}

