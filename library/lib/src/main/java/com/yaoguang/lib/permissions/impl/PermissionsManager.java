package com.yaoguang.lib.permissions.impl;

import android.Manifest;
import android.app.Activity;
import android.widget.Toast;

import com.yaoguang.lib.base.BaseApplication;
import com.yaoguang.lib.permissions.Permissions;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * 作者：韦理英
 * 时间： 2017/5/11 0011.
 */

public class PermissionsManager implements Permissions {
    private static final int RC_PERMISSIONS = 0x001;
    private static final String ALERT = "我们需要%s权限申请，请同意";
    private static PermissionsManager instance;

    public static synchronized PermissionsManager getInstance() {
        if (instance == null) {
            instance = new PermissionsManager();
        }

        return instance;
    }

    @Override
    @AfterPermissionGranted(RC_PERMISSIONS)
    public boolean checkLocatePermissions(Activity activity, boolean isTip) {
        String[] perms = {Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.READ_PHONE_STATE};
        if (EasyPermissions.hasPermissions(activity, perms)) {
            // Already have permission, do the thing
            // ...
            return true;
        } else {
            String alert = String.format(ALERT, "位置");
            showTip(activity, alert, isTip);
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(activity, alert, RC_PERMISSIONS, perms);
        }
        return false;
    }

    @Override
    @AfterPermissionGranted(RC_PERMISSIONS)
    public boolean checkCameraPermissions(Activity activity, boolean isTip) {
        String[] perms = {Manifest.permission.CAMERA};
        if (EasyPermissions.hasPermissions(activity, perms)) {
            // Already have permission, do the thing
            // ...
            return true;
        } else {
            String alert = String.format(ALERT, "相机");
            showTip(activity, alert, isTip);
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(activity, alert, RC_PERMISSIONS, perms);
        }
        return false;
    }

    @Override
    @AfterPermissionGranted(RC_PERMISSIONS)
    public boolean checkWriteExternalPermission(Activity activity, boolean isTip) {
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(activity, perms)) {
            // Already have permission, do the thing
            // ...
            return true;
        } else {
            String alert = String.format(ALERT, "写入");
            showTip(activity, alert, isTip);
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(activity, alert, RC_PERMISSIONS, perms);
        }
        return false;
    }

    @Override
    @AfterPermissionGranted(RC_PERMISSIONS)
    public boolean checkReadExternalPermission(Activity activity, boolean isTip) {
        String[] perms = {Manifest.permission.READ_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(activity, perms)) {
            // Already have permission, do the thing
            // ...
            return true;
        } else {
            String alert = String.format(ALERT, "读取");
            showTip(activity, alert, isTip);
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(activity, alert, RC_PERMISSIONS, perms);
        }
        return false;
    }

    @Override
    @AfterPermissionGranted(RC_PERMISSIONS)
    public boolean checkRecordAudioPermission(Activity activity, boolean isTip) {
        String[] perms = {Manifest.permission.RECORD_AUDIO};
        if (EasyPermissions.hasPermissions(activity, perms)) {
            // Already have permission, do the thing
            // ...
            return true;
        } else {
            String alert = String.format(ALERT, "录音");
            showTip(activity, alert, isTip);
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(activity, alert, RC_PERMISSIONS, perms);
        }
        return false;
    }

    private void showTip(Activity activity, String alert, boolean isTip) {
        if (isTip)
            Toast.makeText(BaseApplication.getInstance(), alert, Toast.LENGTH_LONG).show();
    }

    @Override
    @AfterPermissionGranted(RC_PERMISSIONS)
    public boolean checkCallPhonePermissions(Activity activity, boolean isTip) {
        String[] perms = {Manifest.permission.CALL_PHONE, Manifest.permission.READ_PHONE_STATE};
        if (EasyPermissions.hasPermissions(activity, perms)) {
            // Already have permission, do the thing
            // ...
            return true;
        } else {
            String alert = String.format(ALERT, "拨打电话");
            showTip(activity, alert, isTip);
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(activity, alert, RC_PERMISSIONS, perms);
        }
        return false;
    }
}
