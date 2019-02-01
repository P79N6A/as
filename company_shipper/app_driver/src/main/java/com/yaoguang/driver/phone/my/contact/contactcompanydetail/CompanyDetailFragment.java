package com.yaoguang.driver.phone.my.contact.contactcompanydetail;

import android.os.Bundle;
import android.text.TextUtils;

import com.yaoguang.appcommon.phone.contact2.contactcompanydetail.BaseCompanyDetailFragment;
import com.yaoguang.appcommon.phone.contact2.contactmessage.ContactMessageFragment;
import com.yaoguang.appcommon.databinding.FragmentCompanyDetail2Binding;
import com.yaoguang.datasource.common.DataStatic;
import com.yaoguang.driver.phone.my.authentication.personal.RealNameAuthenticationFragment;
import com.yaoguang.greendao.entity.common.UserOfficeWrapper;
import com.yaoguang.lib.appcommon.dialog.helper.CommonDialog;
import com.yaoguang.lib.appcommon.dialog.DialogHelper;

import static com.yaoguang.appcommon.phone.my.my.event.MyEvent.UPDATE;


/**
 * Created by zhongjh on 2018/5/29.
 */
public class CompanyDetailFragment extends BaseCompanyDetailFragment<FragmentCompanyDetail2Binding> {

    DialogHelper dialogHelper;

    public static CompanyDetailFragment newInstance(UserOfficeWrapper userOfficeWrapper) {
        CompanyDetailFragment companyDetailFragment = new CompanyDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("mUserOffice", userOfficeWrapper);
        companyDetailFragment.setArguments(bundle);
        return companyDetailFragment;
    }


    @Override
    public void IWantToConnect(UserOfficeWrapper userOffice) {
        // 判断是否姓名和身份证是否有填值
        if (TextUtils.isEmpty(DataStatic.getInstance().getDriver().getDriverName()) || TextUtils.isEmpty(DataStatic.getInstance().getDriver().getIdNumber())) {
            // 如果有空的，需要先提交该两值
            if (dialogHelper == null)
                 dialogHelper = new DialogHelper(getContext(), "提示", "请先填写姓名和身份证号", "", "去填写", "取消", false, false, -1, new CommonDialog.Listener() {
                    @Override
                    public void ok(String content) {
                        // 跳转到个人身份认证
                        startForResult(RealNameAuthenticationFragment.newInstance(true), UPDATE);
                    }

                    @Override
                    public void cancel() {
                    }
                });
            dialogHelper.show();
        } else {
            startForResult(ContactMessageFragment.newInstance(userOffice), -1);
        }
    }
}
