package com.yaoguang.shipper.my.contact.contactcompanydetail;

import android.os.Bundle;

import com.yaoguang.appcommon.databinding.FragmentCompanyDetail2Binding;
import com.yaoguang.appcommon.phone.contact2.contactcompanydetail.BaseCompanyDetailFragment;
import com.yaoguang.appcommon.phone.contact2.contactmessage.ContactMessageFragment;
import com.yaoguang.greendao.entity.common.UserOfficeWrapper;


/**
 * Created by zhongjh on 2018/5/29.
 */
public class CompanyDetailFragment extends BaseCompanyDetailFragment<FragmentCompanyDetail2Binding> {

    public static CompanyDetailFragment newInstance(UserOfficeWrapper userOfficeWrapper) {
        CompanyDetailFragment companyDetailFragment = new CompanyDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("mUserOffice", userOfficeWrapper);
        companyDetailFragment.setArguments(bundle);
        return companyDetailFragment;
    }


    @Override
    public void IWantToConnect(UserOfficeWrapper userOffice) {
        startForResult(ContactMessageFragment.newInstance(userOffice), -1);
    }
}
