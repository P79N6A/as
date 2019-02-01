package com.yaoguang.driver.nav;

import android.text.TextUtils;
import android.view.View;

import com.yaoguang.common.base.BaseApplication;
import com.yaoguang.common.appcommon.utils.AppUtils;
import com.yaoguang.common.treerecyclerview.base.ViewHolder;
import com.yaoguang.common.treerecyclerview.factory.ItemHelperFactory;
import com.yaoguang.common.treerecyclerview.item.TreeItem;
import com.yaoguang.common.treerecyclerview.item.TreeItemGroup;
import com.yaoguang.map.R;
import com.yaoguang.driver.nav.bean.RoadInfo;

import java.util.List;

/**
 * 路线信息
 * Created by wly on 2016/12/8.
 */
public class RoadInfoItemParent extends TreeItemGroup<RoadInfo> {

    @Override
    public List<TreeItem> initChildsList(RoadInfo data) {
        return ItemHelperFactory.createTreeItemList(data.getLines(), RoadLineItem.class, this);
    }

    @Override
    public int initLayoutId() {
        return R.layout.itme_roadinfo;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder) {
        // 设置 iconType
        int resId = BaseApplication.getInstance().getBaseContext().getResources().getIdentifier("action" + data.getParentIconType(), "drawable", AppUtils.getAppPackageName());
        holder.setImageResource(R.id.ivIcon, resId);

        // 设置 路名
        holder.setText(R.id.tv_content, data.getParentName());
        // 设置 路长
        String roadLeng = "";
        if (data.getParentLeng() < 999) {
            if (data.getParentLeng() != 0) {
                roadLeng = data.getParentLeng() + "米";
            }
        } else {
            roadLeng = data.getParentLeng() / 1000 + "公里";
        }
        // 设置 红绿灯
        if (data.getTrafficLightNumber() > 0) {
            roadLeng = "红绿灯" + data.getTrafficLightNumber();
        }
        if (!TextUtils.isEmpty(roadLeng)) {
            holder.setText(R.id.tvMi, roadLeng);
        } else {
            holder.getView(R.id.tvMi).setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View itemView, List<RoadInfo> data, int itemPosition) {

        super.onClick(itemView, data, itemPosition);
    }
}
