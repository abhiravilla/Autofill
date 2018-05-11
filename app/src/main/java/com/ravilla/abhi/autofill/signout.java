package com.ravilla.abhi.autofill;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;

import static android.support.v4.content.ContextCompat.startActivity;

class signout extends AppCompatActivity{
    public void out(Context context) {
        GoogleSignInClient mGoogleSignInClient;
       // todefault(context);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        // [END config_signin]
        mGoogleSignInClient = GoogleSignIn.getClient(context, gso);
        mGoogleSignInClient.signOut();
        Intent in=new Intent(signout.this,AuthenticatorActivity.class);
        startActivity(in);
    }
    private  void todefault(Context context){

        SharedPreferences userpref = getSharedPreferences("User", context.MODE_PRIVATE);
        SharedPreferences.Editor file = userpref.edit();
        file.putString("Email", ""+getResources().getString(R.string.default_email));
        file.putString("userid", ""+getResources().getString(R.string.default_id));
        file.putString("name",""+getResources().getString(R.string.default_name));
        file.apply();
    }
}
