package com.yaoguang.driver.phone.my.contact.contactaddmode;

import com.yaoguang.appcommon.phone.contact2.contactaddmode.BaseContactAddModeFragment;
import com.yaoguang.appcommon.databinding.FragmentContactAddModeBinding;
import com.yaoguang.driver.phone.my.contact.contactSearch.ContactSearchFragment;

/**
 * Created by zhongjh on 2018/5/29.
 */
public class ContactAddModeFragment extends BaseContactAddModeFragment<FragmentContactAddModeBinding> {

    public static ContactAddModeFragment newInstance() {
        return new ContactAddModeFragment();
    }

    @Override
    public void initListener() {
        super.initListener();
        mDataBinding.rlSearch.setOnClickListener(v -> start(ContactSearchFragment.newInstance()));
    }
}
