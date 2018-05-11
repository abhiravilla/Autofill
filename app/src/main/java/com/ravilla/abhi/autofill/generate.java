package com.ravilla.abhi.autofill;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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

import static android.content.Context.MODE_PRIVATE;


public class generate extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    private int le=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView tv =findViewById(R.id.password);
        SharedPreferences sharedPref = getSharedPreferences(
                "User", Context.MODE_PRIVATE);
        final String passphrase = sharedPref.getString("userid", "none");
       //tv.setText(key);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView tv =findViewById(R.id.password);
                EditText uedt = findViewById(R.id.username);
                EditText sedt = findViewById(R.id.site);
                String uname =uedt.getText().toString();
                String sname =sedt.getText().toString();
                if(TextUtils.isEmpty(uname)){
                    Snackbar.make(view, "Enter Username", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    return ;
                }else if(TextUtils.isEmpty(sname)){
                    Snackbar.make(view, "Enter Site Name", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    return;
                }else {
                    String password = tv.getText().toString();
                    encrypt enc = new encrypt();
                    try {
                        String enpass = enc.encryp(password,passphrase);
                        String unpass = enc.encryp(uname,passphrase);
                        String snpass = enc.encryp(sname,passphrase);
                        tv.setText(snpass);

                    } catch (Exception e) {
                        System.out.println(e);
                    }
                }
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        findViewById(R.id.gen).setOnClickListener(this);
        findViewById(R.id.regen).setOnClickListener(this);
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
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public String generate(int len){
        passgen pg=new passgen(len);
        return(pg.generate());
        /*
        encrypt enc = new encrypt();
        try {
            String enpass=enc.encrypt(pass);
            System.out.println(enpass);
            decrypt dcp = new decrypt();
            String depass = dcp.decrypt(enpass);
            System.out.println(depass);

        } catch (Exception e) {
            System.out.println(e);
        }{

        }
        */
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
            TextView tv =findViewById(R.id.password);
            tv.setText(result);
        }else if (i == R.id.regen) {
            String result = generate(le);
            TextView tv =findViewById(R.id.password);
            Log.d("length in Regenerate",""+le);
            Log.d("Password in Regenerate",""+result);
            tv.setText(result);
        }
    }
}
