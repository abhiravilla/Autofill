package com.ravilla.abhi.autofill.menu;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.database.Cursor;
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
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.ravilla.abhi.autofill.Authentication.AuthenticatorActivity;
import com.ravilla.abhi.autofill.Ldispaly.list;
import com.ravilla.abhi.autofill.R;

public class logins extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener,LoaderManager.LoaderCallbacks<Cursor>{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logins);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        findViewById(R.id.show).setOnClickListener(this);
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
        getMenuInflater().inflate(R.menu.logins, menu);
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

        } else if (id == R.id.nav_delete) {

        } else if (id == R.id.nav_settings) {

        } else if (id == R.id.nav_signout) {
           signout();
        }
        else if (id == R.id.nav_generate){
            Intent in=new Intent(logins.this,generate.class);
            startActivity(in);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
        Intent in=new Intent(logins.this,AuthenticatorActivity.class);
        startActivity(in);
    }
    private  void todefault() {

        SharedPreferences userpref = getSharedPreferences("User", this.MODE_PRIVATE);
        SharedPreferences.Editor file = userpref.edit();
        file.putString("Email", "" + getResources().getString(R.string.default_email));
        file.putString("userid", "" + getResources().getString(R.string.default_id));
        file.putString("name", "" + getResources().getString(R.string.default_name));
        file.apply();
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.show) {
            Log.i("Flow","Starting List class");
            Intent intent = new Intent(logins.this, list.class);
            startActivity(intent);
            Log.i("Flow","Done List class");
           // TextView sname = (TextView)findViewById(R.id.sitename);
           // TextView uname = (TextView)findViewById(R.id.username);
           // TextView pass = (TextView)findViewById(R.id.password);
           // datastore ds = new datastore(getApplicationContext());
            //List<fulllist> flists=ds.getAll();
//            Cursor cu = ds.getAll();
//            Intent intent = new Intent(logins.this, list.class);
//            startActivity(intent);
//            decrypt dc = new decrypt();
//            //for (fulllist fl:flists){
              //  sname.setText(dc.decrypt(fl.getsite(),key()));
              //  uname.setText(dc.decrypt(fl.getuname(),key()));
              //  pass.setText(dc.decrypt(fl.getpassword(),key()));
            //}
        }
    }
    private String key(){
        SharedPreferences sharedPref = getSharedPreferences(
                "User", this.MODE_PRIVATE);
        final String passphrase = sharedPref.getString("userid", "none");
        return passphrase;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
