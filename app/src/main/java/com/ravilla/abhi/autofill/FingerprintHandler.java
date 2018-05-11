package com.ravilla.abhi.autofill;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.Manifest;
import android.os.CancellationSignal;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;


public class FingerprintHandler extends FingerprintManager.AuthenticationCallback {

    private CancellationSignal cancellationSignal;
    private Context context;


    public FingerprintHandler(Context mContext) {
        context = mContext;
    }

    public void startAuth(FingerprintManager manager, FingerprintManager.CryptoObject cryptoObject) {

        cancellationSignal = new CancellationSignal();
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
            SharedPreferences userpref = context.getSharedPreferences("User", context.MODE_PRIVATE);
            SharedPreferences.Editor file = userpref.edit();
            file.putInt("Authentication", 0);
            file.apply();        }
       manager.authenticate(cryptoObject, cancellationSignal, 0, this, null);

    }

    @Override
    public void onAuthenticationError(int errMsgId,
                                      CharSequence errString) {
        Toast.makeText(context,
                "Authentication error\n" + errString,
                Toast.LENGTH_LONG).show();
        SharedPreferences userpref = context.getSharedPreferences("User", context.MODE_PRIVATE);
        SharedPreferences.Editor file = userpref.edit();
        file.putInt("Authentication", -1);
        file.apply();
    }

    @Override
    public void onAuthenticationFailed() {
        Toast.makeText(context,
                "Authentication failed",
                Toast.LENGTH_LONG).show();
        SharedPreferences userpref = context.getSharedPreferences("User", context.MODE_PRIVATE);
        SharedPreferences.Editor file = userpref.edit();
        file.putInt("Authentication", -1);
        file.apply();
    }

    @Override
    public void onAuthenticationHelp(int helpMsgId,
                                     CharSequence helpString) {
        Toast.makeText(context,
                "Authentication help\n" + helpString,
                Toast.LENGTH_LONG).show();
        SharedPreferences userpref = context.getSharedPreferences("User", context.MODE_PRIVATE);
        SharedPreferences.Editor file = userpref.edit();
        file.putInt("Authentication", -1);
        file.apply();

    }


    @Override
    public void onAuthenticationSucceeded(
            FingerprintManager.AuthenticationResult result) {

        Toast.makeText(context,
                "Success!",
                Toast.LENGTH_LONG).show();
        SharedPreferences userpref = context.getSharedPreferences("User", context.MODE_PRIVATE);
        SharedPreferences.Editor file = userpref.edit();
        file.putInt("Authentication", 1);
        file.apply();

    }
}