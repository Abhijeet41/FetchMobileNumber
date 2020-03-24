package com.example.fetchmobilenumber;

import androidx.appcompat.app.AppCompatActivity;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.auth.api.credentials.HintRequest;
import com.google.android.gms.common.api.GoogleApiClient;

public class MainActivity extends AppCompatActivity {
    private static final int PHONE_NUMBER_HINT = 100;
    private static final String TAG = "MainActivity";
    private final int PERMISSION_REQ_CODE = 200;
    private TextView textview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textview = findViewById(R.id.textview);
        textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogMobile();
            }
        });
    }

    private void dialogMobile() {

        final HintRequest hintRequest =
                new HintRequest.Builder().setPhoneNumberIdentifierSupported(true).build();

        try {
            final GoogleApiClient googleApiClient =
                    new GoogleApiClient.Builder(MainActivity.this).addApi(Auth.CREDENTIALS_API).build();

            final PendingIntent pendingIntent =
                    Auth.CredentialsApi.getHintPickerIntent(googleApiClient, hintRequest);

            startIntentSenderForResult(
                    pendingIntent.getIntentSender(),
                    PHONE_NUMBER_HINT,
                    null,
                    0,
                    0,
                    0
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PHONE_NUMBER_HINT && resultCode == RESULT_OK) {
            Credential credential = data.getParcelableExtra(Credential.EXTRA_KEY);
            final String phoneNumber = credential.getId();
            Log.d(TAG, "onActivityResult: "+phoneNumber);
        }
    }
}
