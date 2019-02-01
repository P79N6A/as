package com.yaoguang.shipper.order.node;

import android.os.Bundle;

import com.yaoguang.appcommon.databinding.FragmentOrderNode2PortBinding;
import com.yaoguang.appcommon.phone.node.BaseNodeFragment;

/**
 * Created by zhongjh on 2019/1/8.
 */
public class NodeFragment extends BaseNodeFragment<FragmentOrderNode2PortBinding> {

    /**
     * @param type     类型 4：装货 5：送货
     * @param pcSonoId 平台货柜id
     * @return
     */
    public static NodeFragment newInstance(String type, String pcSonoId) {
        NodeFragment nodeFragment = new NodeFragment();
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        bundle.putString("pcSonoId", pcSonoId);
        nodeFragment.setArguments(bundle);
        return nodeFragment;
    }

    // 货主端不需要针对这个进行处理
    @Override
    public void refreshingFootView(int orderStatus, String clientId) {

    }

    @Override
    protected void initView() {
        super.initView();
        mIsEdit = false;
        initToolbarNav(mViewHolder.toolbar, "当前任务进度", -1, null);
    }


}
