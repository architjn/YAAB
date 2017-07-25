package com.architjn.audiobook.utils;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;

import com.architjn.audiobook.R;

/**
 * Created by Archit on 25-07-2017.
 */

public class PermissionChecker {

    public static boolean hasPermission(Context context, String permission) {
        int res = context.checkCallingOrSelfPermission(permission);
        return res == PackageManager.PERMISSION_GRANTED;
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        boolean hasAllPermissions = true;
        for (String permission : permissions) {
            if (!hasPermission(context, permission)) {
                hasAllPermissions = false;
            }
        }
        return hasAllPermissions;
    }

    public static boolean requestForPermission(final Context context, String permission,
                                               int reqString, final int permissionCallBackID) {
        return requestForPermission(context, permission, context.getString(reqString), permissionCallBackID);
    }

    public static boolean requestForPermission(final Context context, final String permission,
                                               String reqString, final int permissionCallBackID) {
        if (hasPermission(context, permission)) return true;
        if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, permission)) {
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
            alertBuilder.setCancelable(true);
            alertBuilder.setMessage(reqString);
            alertBuilder.setTitle(R.string.permission_required);
            alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                public void onClick(DialogInterface dialog, int which) {
                    ActivityCompat.requestPermissions((Activity) context,
                            new String[]{permission}, permissionCallBackID);
                }
            });
            alertBuilder.create().show();
        } else {
            ActivityCompat.requestPermissions((Activity) context,
                    new String[]{permission}, permissionCallBackID);
        }
        return false;
    }

}
