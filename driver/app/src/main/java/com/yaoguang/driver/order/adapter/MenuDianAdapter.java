package com.yaoguang.driver.order.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yaoguang.common.adapter.BaseLoadMoreRecyclerAdapter;
import com.yaoguang.driver.R;
import com.yaoguang.driver.order.map.OrderNodeMapFragment;
import com.yaoguang.greendao.entity.DriverOrderNode;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 韦理英
 * on 2017/6/30 0030.
 */
public class MenuDianAdapter extends BaseLoadMoreRecyclerAdapter<DriverOrderNode, MenuDianAdapter.ViewHolder> {

    private final Context mContext;

    public MenuDianAdapter(Context context) {
        mContext = context;
    }

    @Override
    public ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.view_mendian, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(ViewHolder holder, final int position) {
        holder.ll.setPadding(0, 10, 0, 0);
        final DriverOrderNode driverOrderNode = getItem(position);
        holder.tvAddress.setText(OrderNodeMapFragment.getAddress(driverOrderNode));
        OrderNodeMapFragment.initAlertLogo(driverOrderNode, holder.alertImage, mContext);
        holder.tvMobile.setOnClickListener(v -> {
            if (roadPlan != null) {
                roadPlan.callPhone(driverOrderNode.getRemark());
            }
        });
        holder.tvRoadPlan.setOnClickListener(v -> MenuDianAdapter.this.getRoadPlan().result(driverOrderNode.getAddress()));
//        String[] titles = driverOrderNode.getDriverOrderNodeList().get(0).getRemark().split(",");
//        holder.btnNodeTitle.setText(OrderNodeMapFragment.handerBtnTile(titles[1]));
        holder.btnNodeTitle.setText("先去\n这里");
        holder.btnNodeTitle.setOnClickListener(v -> mOnItemClickListener.onItemClick(v, driverOrderNode, position));
    }

    private RoadPlan roadPlan;

    private RoadPlan getRoadPlan() {
        return roadPlan;
    }

    public void setRoadPlan(RoadPlan roadPlan) {
        this.roadPlan = roadPlan;
    }

    public interface RoadPlan {
        void result(String address);

        void callPhone(String phone);
    }


    @Override
    public int getItemCount() {
        return getList().size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvAddress)
        TextView tvAddress;
        @BindView(R.id.tvMobile)
        TextView tvMobile;
        @BindView(R.id.rlMobile)
        RelativeLayout rlMobile;
        @BindView(R.id.tvNavi)
        TextView tvRoadPlan;
        @BindView(R.id.rlRoadPlan)
        RelativeLayout rlRoadPlan;
        @BindView(R.id.llBottom)
        LinearLayout llBottom;
        @BindView(R.id.ll)
        LinearLayout ll;
        @BindView(R.id.alertImage)
        TextView alertImage;
        @BindView(R.id.btnNodeTitle)
        Button btnNodeTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
