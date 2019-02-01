package com.yaoguang.appcommon.common.utils;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.widget.Toast;

import com.yaoguang.lib.base.BaseApplication;
import com.yaoguang.lib.common.IntentUtils;
import com.yaoguang.lib.permissions.impl.PermissionsManager;

import java.util.ArrayList;
import java.util.List;

import static com.yaoguang.appcommon.common.utils.CommonBottomPhoneDialog.FLAG_ARRAY;

/**
 * 文件名：
 * 描    述：
 * 作    者：韦理英
 * 时    间：2017/8/17 0017.
 * 版    权：
 */
public class PhoneUtils {
    /**
     * 描    述：打电话
     * 作    者：韦理英
     * 时    间：
     *
     * @param activity
     * @param mobile   电话
     */

    public static void nodeCallPhone(final Activity activity, FragmentManager fragmentManager, String[] mobile) {
        List<String> list = new ArrayList<>();
        //  判断联系人是否是空的
        if (mobile == null || mobile.length == 0) {
            return;
        } else {
            // 处理数据
            for (String s : mobile) {
                if (!TextUtils.isEmpty(s)) {
                    list.add(s);
                }
            }
            // 判断非空
            if (list.isEmpty()) {
                Toast.makeText(BaseApplication.getInstance(), "抱歉，没有此联系人电话", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        //  显示对话框
        if (PermissionsManager.getInstance().checkCallPhonePermissions(activity, true)) {
            CommonBottomPhoneDialog dialogHelper = new CommonBottomPhoneDialog();
            Bundle bundle = new Bundle();
            bundle.putStringArray(FLAG_ARRAY, list.toArray(new String[list.size()]));
            dialogHelper.setArguments(bundle);
            dialogHelper.show(fragmentManager, "CommonBottom");
        } else {
            Toast.makeText(BaseApplication.getInstance(), "请开拨打电话权限", Toast.LENGTH_SHORT).show();
        }
    }
}
