package com.yaoguang.driver.nav;


import com.yaoguang.common.base.BaseApplication;
import com.yaoguang.common.appcommon.utils.AppUtils;
import com.yaoguang.map.R;
import com.yaoguang.common.treerecyclerview.base.ViewHolder;
import com.yaoguang.common.treerecyclerview.item.TreeItem;
import com.yaoguang.driver.nav.bean.Line;


/**
 */
public class RoadLineItem extends TreeItem<Line> {


    @Override
    public int initLayoutId() {
        return R.layout.item_roadline;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder) {
        // 设置 iconType
        int resId = BaseApplication.getInstance().getBaseContext().getResources().getIdentifier("action" + data.getLineIconType(), "drawable", AppUtils.getAppPackageName());
        holder.setImageResource(R.id.ivIcon, resId);

        // 设置 路长
        String roadLeng;
        if (data.getLineLeng() < 999) {
            roadLeng = data.getLineLeng() + "米";
        } else {
            roadLeng = data.getLineLeng() / 1000 + "公里";
        }

        roadLeng += "行驶" + roadLeng;
        if (data.getLineNextName() != null) {
            roadLeng = "进入" + data.getLineNextName();
        }

        // 设置 路名
        holder.setText(R.id.tv_content, roadLeng);
    }
}
