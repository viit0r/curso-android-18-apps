package com.vitor.organizze.config;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseConfig {

    private static FirebaseAuth auth;
    private static DatabaseReference databaseReference;

    // Retorna a instancia do FirebaseDatabase
    public static DatabaseReference getFirebaseDatabase(){
        if (databaseReference == null) {
            databaseReference = FirebaseDatabase.getInstance().getReference();
        }
        return databaseReference;
    }

    // Retorna a instancia do FirebaseAuth
    public static FirebaseAuth getAuth(){
        if (auth == null) {
             auth = FirebaseAuth.getInstance();
        }
        return auth;
    }

}
