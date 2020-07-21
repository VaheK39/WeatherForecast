package com.example.lesson48.permission;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.lesson48.MainActivity;
import com.example.lesson48.R;

public class PermissionsHandler {
    public static final int PERMISSION_REQUEST_CODE = 9009;

    private Activity activity;
    private String[] permissions;

    public PermissionsHandler(Activity activity, String [] permissions) {
        this.activity = activity;
        this.permissions = permissions;
    }

    public void create() {
        if (!allPermissionsGranted()){
            requestPermissions();
        }
    }

    private boolean allPermissionsGranted() {
        for (String permission:permissions) {
            if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(activity, permissions, PERMISSION_REQUEST_CODE);
    }

    public void showPermissionDeniedDialog(final boolean neverAskAgainWasSelected) {
        AlertDialog dialog = new AlertDialog.Builder(activity)
                .setMessage(R.string.permission_denied_dialog)
                .setCancelable(false)
                .setPositiveButton("Grant permissions", (dialog1, which) -> {
                    Intent intent = new Intent();
                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
                    intent.setData(uri);
                    activity.startActivity(intent);
                    dialog1.dismiss();
                    if (neverAskAgainWasSelected) {
                        activity.finish();
                        Toast.makeText(activity, "App has been closed", Toast.LENGTH_SHORT).show();
                    }else {
                        create();
                    }
                })
                .create();
        dialog.show();
    }

}
