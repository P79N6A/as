package com.yaoguang.shipper.my.contact.contactSearch;

import com.yaoguang.appcommon.databinding.FragmentContact2SearchBinding;
import com.yaoguang.appcommon.phone.contact2.contactSearch.BaseContactSearchFragment;
import com.yaoguang.greendao.entity.common.UserOfficeWrapper;
import com.yaoguang.lib.net.bean.PageList;
import com.yaoguang.shipper.my.contact.contactcompanydetail.CompanyDetailFragment;

/**
 * Created by zhongjh on 2018/5/29.
 */
public class ContactSearchFragment extends BaseContactSearchFragment<FragmentContact2SearchBinding> {

    public static ContactSearchFragment newInstance() {
        return new ContactSearchFragment();
    }

    @Override
    public void initListener() {
        super.initListener();
        // 点击搜索列表的时候
        mBaseLoadMoreRecyclerAdapter.setOnItemClickListener((itemView, item, position) -> {
            UserOfficeWrapper userOfficeWrapper = ((UserOfficeWrapper) (item));
            start(CompanyDetailFragment.newInstance(userOfficeWrapper));
            // 添加入缓存
            comitQuery(userOfficeWrapper.getName(), mInflater);
        });
    }

    @Override
    public void isOneStartFragment(PageList<UserOfficeWrapper> result) {
        super.isOneStartFragment(result);
        if (result.getResult().size() == 1) {
            UserOfficeWrapper userOfficeWrapper = result.getResult().get(0);
            start(CompanyDetailFragment.newInstance(userOfficeWrapper));
            // 添加入缓存
            comitQuery(userOfficeWrapper.getName(), mInflater);
        }
    }

}
