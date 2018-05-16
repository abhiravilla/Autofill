package com.ravilla.abhi.autofill.menu;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.text.TextUtils;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.ravilla.abhi.autofill.Authentication.AuthenticatorActivity;
import com.ravilla.abhi.autofill.R;
import com.ravilla.abhi.autofill.data.datastore;
import com.ravilla.abhi.autofill.encryption.encrypt;
import com.ravilla.abhi.autofill.Ldispaly.fulllist;
import com.ravilla.abhi.autofill.background.passgen;

import java.util.List;


public class generate extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    private int le=0;
    Context context;
    String enpass;
    String unpass;
    String snpass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate);
        context = getApplicationContext();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        findViewById(R.id.gen).setOnClickListener(this);
        findViewById(R.id.regen).setOnClickListener(this);
        findViewById(R.id.save).setOnClickListener(this);

        setvalues(navigationView);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.generate, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected (MenuItem item){
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {

        } else if (id == R.id.nav_logins) {
            Intent in=new Intent(generate.this,logins.class);
            startActivity(in);

        } else if (id == R.id.nav_delete) {

        } else if (id == R.id.nav_settings) {

        } else if (id == R.id.nav_signout) {
            signout();
        }
        else if (id == R.id.nav_generate){
            Intent in=new Intent(generate.this,generate.class);
            startActivity(in);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public String generate(int len){
        passgen pg=new passgen(len);
        return(pg.generate());
    }
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.gen) {
            EditText edt = findViewById(R.id.length);
            String len =edt.getText().toString();
            if(TextUtils.isEmpty(len)){
                Toast.makeText(this,"Enter the Value first",Toast.LENGTH_LONG).show();
                return;
            }
            le=Integer.parseInt(len);
            String result = generate(le);
            findViewById(R.id.gen).setVisibility(View.GONE);
            findViewById(R.id.regen).setVisibility(View.VISIBLE);
            findViewById(R.id.length).setVisibility(View.GONE);
            findViewById(R.id.password).setVisibility(View.VISIBLE);
            findViewById(R.id.save).setVisibility(View.VISIBLE);
            TextView tv =findViewById(R.id.password);
            tv.setText(result);
        }else if (i == R.id.regen) {
            String result = generate(le);
            TextView tv =findViewById(R.id.password);
            Log.d("length in Regenerate",""+le);
            Log.d("Password in Regenerate",""+result);
            tv.setText(result);
        }
        else if (i == R.id.save){
            SharedPreferences sharedPref = getSharedPreferences(
                    "User", Context.MODE_PRIVATE);
            final String passphrase = sharedPref.getString("userid", "none");
            findViewById(R.id.password).setVisibility(View.VISIBLE);
            TextView tv =findViewById(R.id.password);
            EditText uedt = findViewById(R.id.username);
            EditText sedt = findViewById(R.id.site);
            unpass =uedt.getText().toString();
            snpass =sedt.getText().toString();
            if(TextUtils.isEmpty(unpass)){
                Toast to = Toast.makeText(context,"Enter Username",Toast.LENGTH_LONG);
                to.show();
                return ;
            }else if(TextUtils.isEmpty(snpass)){
                Toast to=Toast.makeText(context,"Enter Site name",Toast.LENGTH_LONG);
                to.show();
                return;
            }else {
                  enpass = tv.getText().toString();
                  enpass= new encrypt().encryp(enpass,key());
                  storeSQLite();
            }
        }
    }
    private void signout() {
        GoogleSignInClient mGoogleSignInClient;
        todefault();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        // [END config_signin]
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        mGoogleSignInClient.signOut();
        Intent in=new Intent(generate.this,AuthenticatorActivity.class);
        startActivity(in);
    }
    private  void todefault(){

        SharedPreferences userpref = getSharedPreferences("User", this.MODE_PRIVATE);
        SharedPreferences.Editor file = userpref.edit();
        file.putString("Email", ""+getResources().getString(R.string.default_email));
        file.putString("userid", ""+getResources().getString(R.string.default_id));
        file.putString("name",""+getResources().getString(R.string.default_name));
        file.apply();
    }

    public void setvalues(NavigationView navigationView){

        SharedPreferences sharedPref = getSharedPreferences("User", MODE_PRIVATE);
        String defaultname = getResources().getString(R.string.default_name);
        String name = sharedPref.getString(getString(R.string.user_name), defaultname);
        String defaultemail = getResources().getString(R.string.default_email);
        String email = sharedPref.getString(getString(R.string.user_email), defaultemail);
        View headerView = navigationView.getHeaderView(0);
        TextView uiname = (TextView) headerView.findViewById(R.id.name);
        uiname.setText(name);
        TextView uiemail = (TextView) headerView.findViewById(R.id.email);
        uiemail.setText(email);
    }
    private void storeSQLite() {
        // AsyncTask is used that SQLite operation not blocks the UI Thread.
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                datastore ds = new datastore(context);
                ds.addflist(new fulllist(snpass,unpass,enpass));
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
            }
        }.execute();
    }
    private String key(){
        SharedPreferences sharedPref = getSharedPreferences(
                "User", this.MODE_PRIVATE);
        final String passphrase = sharedPref.getString("userid", "none");
        return passphrase;
    }
}
