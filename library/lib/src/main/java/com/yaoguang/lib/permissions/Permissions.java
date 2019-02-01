package com.yaoguang.lib.permissions;

import android.app.Activity;

/**
 * 作者：韦理英
 * 时间： 2017/5/11 0011.
 */

public interface Permissions {
    boolean checkLocatePermissions(Activity activity, boolean isTip);

    boolean checkCameraPermissions(Activity activity, boolean isTip);

    boolean checkWriteExternalPermission(Activity activity, boolean isTip);

    boolean checkReadExternalPermission(Activity activity, boolean isTip);

    boolean checkRecordAudioPermission(Activity activity, boolean isTip);

    boolean checkCallPhonePermissions(Activity activity, boolean b);
}
