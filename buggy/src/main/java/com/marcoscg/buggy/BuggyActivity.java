package com.marcoscg.buggy;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by @MarcosCGdev on 12/12/2017.
 */

public class BuggyActivity extends AppCompatActivity {

    private String INTENT_EXTRA_SUBJECT = "CRASH_ERROR_SUBJECT";
    private String INTENT_EXTRA_EMAIL = "CRASH_ERROR_EMAIL";
    private String INTENT_EXTRA_INFO = "CRASH_ERROR_INFO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getIntent()!=null && getIntent().getStringExtra(INTENT_EXTRA_INFO)!=null) {
            new AlertDialog.Builder(BuggyActivity.this)
                    .setTitle(getResources().getString(R.string.app_name))
                    .setCancelable(false)
                    .setMessage(getResources().getString(R.string.buggy_dialog_message))
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            Uri data = Uri.parse("mailto:" + getIntent().getStringExtra(INTENT_EXTRA_EMAIL)
                                    + "?subject=" + getIntent().getStringExtra(INTENT_EXTRA_SUBJECT)
                                    + "&body=" + getIntent().getStringExtra(INTENT_EXTRA_INFO));
                            intent.setData(data);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .show();
        } else finish();
    }
}
