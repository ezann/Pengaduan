package com.example.pengaduan.pengaduan;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Toast;

public class MyWebChromeClient extends WebChromeClient {
    // reference to activity instance. May be unnecessary if your web chrome client is member class.
    private MainActivity activity;

    public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
        // make sure there is no existing message
        if (activity.uploadMessage != null) {
            activity.uploadMessage.onReceiveValue(null);
            activity.uploadMessage = null;
        }

        activity.uploadMessage = filePathCallback;

        Intent intent = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            intent = fileChooserParams.createIntent();
        }
        try {
            activity.startActivityForResult(intent, activity.REQUEST_SELECT_FILE);
        } catch (ActivityNotFoundException e) {
            activity.uploadMessage = null;
            Toast.makeText(activity, "Cannot open file chooser", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }
}