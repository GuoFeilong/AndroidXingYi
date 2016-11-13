package com.android.xingyi.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.android.xingyi.AppConstant;

/**
 * Android M版本的权限申请权限适配
 * Created by Feilong.Guo on 2016/11/13.
 */

public abstract class BaseActivity extends AppCompatActivity {
    protected ATRequestPermissionCallBack permissionCallBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (permissionCallBack != null) {
                permissionCallBack.success(requestCode);
            }
            // If request is cancelled, the result arrays are empty.
            switch (requestCode) {
                case AppConstant.MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                    Intent moblieIntent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                    startActivityForResult(moblieIntent, AppConstant.PHONE_REQUEST_CODE);
                    break;
                }
                case AppConstant.MY_PERMISSIONS_REQUEST_READ_WRITE_STORAGE: {

                }
            }
            // other 'case' lines to check for other
            // permissions this app might request
        } else {
            if (requestCode == AppConstant.MY_PERMISSIONS_REQUEST_READ_WRITE_STORAGE) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                        requestCode);
            }
            // permission was granted, yay! Do the
            // contacts-related task you need to do.
        }
    }

    public void addPermissionCallBack(ATRequestPermissionCallBack permissionCallBack) {
        this.permissionCallBack = permissionCallBack;
    }


    public interface ATRequestPermissionCallBack {
        void success(int myRequestCode);
    }

    protected boolean isHavePermission(String permission, final int requestCode) {
        int havaPermission = -10;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            havaPermission = ContextCompat.checkSelfPermission(this, permission);
        } else {
            PackageManager pm = getPackageManager();
            havaPermission = pm.checkPermission(permission, AppConstant.MY_PAGE_NAME);
        }
        if (havaPermission != PackageManager.PERMISSION_GRANTED) {
            // No explanation needed, we can request the permission.
            String[] requestPermissionArray = new String[]{permission};
            if (Manifest.permission.ACCESS_FINE_LOCATION.equals(permission)) {
                requestPermissionArray = new String[]{permission, Manifest.permission.ACCESS_COARSE_LOCATION};
            } else if (Manifest.permission.WRITE_EXTERNAL_STORAGE.equals(permission)) {
                requestPermissionArray = new String[]{permission, Manifest.permission.READ_EXTERNAL_STORAGE};
            }
            ActivityCompat.requestPermissions(this,
                    requestPermissionArray,
                    requestCode);
            // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
            // app-defined int constant. The callback method gets the
            // result of the request.
            return false;
        }
        return true;
    }


}
