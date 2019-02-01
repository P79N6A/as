package com.yaoguang.driver.phone.my.contact.contactnew;

import android.view.MenuItem;

import com.yaoguang.appcommon.phone.contact2.contactnew.BaseContactNewFragment;
import com.yaoguang.appcommon.databinding.FragmentContactNewBinding;
import com.yaoguang.driver.R;
import com.yaoguang.driver.phone.my.contact.contactaddmode.ContactAddModeFragment;
import com.yaoguang.driver.phone.my.contact.contactcompanydetail.CompanyDetailFragment;
import com.yaoguang.greendao.entity.common.DriverFollowCompany;

/**
 * Created by zhongjh on 2018/5/29.
 */
public class ContactNewFragment extends BaseContactNewFragment<FragmentContactNewBinding> {


    public static ContactNewFragment newInstance() {
        return new ContactNewFragment();
    }

    @Override
    public void initListener() {
        super.initListener();
        mBaseLoadMoreRecyclerAdapter.setOnItemClickListener((itemView, item, position) -> {
            DriverFollowCompany driverContactCompany = ((DriverFollowCompany) (item));
            start(CompanyDetailFragment.newInstance(driverContactCompany.getUserOffice()));
        });
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (item.getItemId() == R.id.action_add) {
            start(ContactAddModeFragment.newInstance());
        }
        return super.onMenuItemClick(item);
    }
}
