package com.yaoguang.company.my.contact;

import android.view.MenuItem;

import com.yaoguang.appcommon.databinding.FragmentContactBinding;
import com.yaoguang.appcommon.phone.contact2.BaseContactFragment;
import com.yaoguang.company.R;
import com.yaoguang.company.my.contact.contactaddmode.ContactAddModeFragment;
import com.yaoguang.company.my.contact.contactcompanydetail.CompanyDetailFragment;
import com.yaoguang.company.my.contact.contactnew.ContactNewFragment;
import com.yaoguang.greendao.entity.common.DriverContactCompany;

/**
 * Created by zhongjh on 2018/5/29.
 */
public class ContactFragment extends BaseContactFragment<FragmentContactBinding> {

    public static ContactFragment newInstance() {
        return new ContactFragment();
    }

    @Override
    public void initListener() {
        super.initListener();


        // 打开我的关联
        mDataBinding.rlAdd.setOnClickListener(v -> start(ContactNewFragment.newInstance()));

        mBaseLoadMoreRecyclerAdapter.setOnItemClickListener((itemView, item, position) -> {
            DriverContactCompany driverContactCompany = ((DriverContactCompany) (item));
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
