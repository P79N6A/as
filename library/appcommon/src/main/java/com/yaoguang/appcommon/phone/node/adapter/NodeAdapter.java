package com.yaoguang.appcommon.phone.node.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yaoguang.appcommon.R;
import com.yaoguang.greendao.entity.driver.DriverNodeAddrVo;
import com.yaoguang.greendao.entity.driver.DriverOrderMergeNodeWrapper;
import com.yaoguang.greendao.entity.driver.DriverOrderNodeWrapper;
import com.yaoguang.lib.adapter.BaseLoadMoreRecyclerAdapter;
import com.yaoguang.lib.common.DateUtils;
import com.yaoguang.lib.common.debounceclick.NoDoubleClickListener;
import com.yaoguang.lib.common.view.RingsView;

/**
 * 节点适配器
 * Created by zhongjh on 2018/4/27.
 */
public class NodeAdapter extends BaseLoadMoreRecyclerAdapter<DriverOrderMergeNodeWrapper, RecyclerView.ViewHolder> {

    private static final int TYPE_ITEM_TOP = 11; // item顶部
    private static final int TYPE_ITEM_WHARF = 22; // 码头
    private static final int TYPE_ITEM_DOOR = 3000;// 门点
    private static final int TYPE_ITEM_BOTTOM = 44;// 完成
    private Context mContext;
    private CallBack mCallBack;

    public NodeAdapter(Context context) {
        mContext = context;
    }

    public void setmCallBack(CallBack mCallBack) {
        this.mCallBack = mCallBack;
    }

    @Override
    public int getItemViewTypeCustom(int position) {
        if (getItem(position).getNodeType().equals("0")) {
            // 出车
            return TYPE_ITEM_TOP;
        } else if (getItem(position).getNodeType().equals("1") || getItem(position).getNodeType().equals("3")) {
            // 码头
            return TYPE_ITEM_WHARF;
        } else if (getItem(position).getNodeType().equals("2")) {
            // 门点，判断有多少个地址，就+1
            return TYPE_ITEM_DOOR + getItem(position).getNodes().size();
        } else if (getItem(position).getNodeType().equals("4")) {
            // 完成
            return TYPE_ITEM_BOTTOM;
        }
        return TYPE_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        View viewTop;
        View viewWharf;
        View viewDoor;
        View viewBottom;
        if (viewType > 3000) {
            // 超过3000就是门点
            viewDoor = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_node_door, parent, false);
            ViewHolderDoor viewHolderDoor = new ViewHolderDoor(viewDoor);
            viewHolderDoor.imgNavigation.setOnClickListener(new NoDoubleClickListener() {
                @Override
                public void onNoDoubleClick(View v) {
                    final int position = viewHolderDoor.getAdapterPosition();
                    // 循环，有找到值了就打开地图
                    for (DriverOrderNodeWrapper driverOrderNodeWrapper : getItem(position).getNodes()) {
                        if (driverOrderNodeWrapper.getTruckGoodsAddr().getSite() != null || driverOrderNodeWrapper.getTruckGoodsAddr().getAddress() != null) {
                            mCallBack.startMap(driverOrderNodeWrapper.getTruckGoodsAddr());
                            break;
                        }
                    }
                }
            });
            // 设置创建多少个view
            int doorSize = viewType - 3000;
            for (int i = 0; i < doorSize; i++) {
                // 有几个地址，就添加几个view
                View viewDoorDetail = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_item_order_node_door, parent, false);
                viewHolderDoor.llAddress.addView(viewDoorDetail);
                int finalI = i;
                viewDoorDetail.findViewById(R.id.tvTruckGoodsAddrPhone).setOnClickListener(new NoDoubleClickListener() {
                    @Override
                    public void onNoDoubleClick(View v) {
                        final int position = viewHolderDoor.getAdapterPosition();
                        mCallBack.startCallPhone(getItem(position).getNodes().get(finalI).getTruckGoodsAddr().getTel());
                    }
                });
                viewDoorDetail.findViewById(R.id.tvTruckGoodsAddrMobile).setOnClickListener(new NoDoubleClickListener() {
                    @Override
                    public void onNoDoubleClick(View v) {
                        final int position = viewHolderDoor.getAdapterPosition();
                        mCallBack.startCallPhone(getItem(position).getNodes().get(finalI).getTruckGoodsAddr().getMobile());
                    }
                });

                viewDoorDetail.findViewById(R.id.tvRecord).setOnClickListener(new NoDoubleClickListener() {
                    @Override
                    public void onNoDoubleClick(View v) {
                        final int position = viewHolderDoor.getAdapterPosition();
                        mCallBack.startOrderNodeRichTextFragement(getItem(position).getNodes().get(finalI));
                    }
                });


            }
            return viewHolderDoor;
        } else {
            switch (viewType) {
                case TYPE_ITEM_TOP:
                    viewTop = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_node_top, parent, false);
                    holder = new ViewHolderTop(viewTop);
                    break;
                case TYPE_ITEM_WHARF:
                    viewWharf = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_node_wharf, parent, false);
                    holder = new ViewHolderWharf(viewWharf);

                    RecyclerView.ViewHolder finalHolder = holder;
                    viewWharf.findViewById(R.id.tvRecord).setOnClickListener(new NoDoubleClickListener() {
                        @Override
                        public void onNoDoubleClick(View v) {
                            final int position = finalHolder.getAdapterPosition();
                            mCallBack.startOrderNodeRichTextFragement(getItem(position).getNodes().get(0));
                        }
                    });

                    viewWharf.findViewById(R.id.imgNavigation).setOnClickListener(new NoDoubleClickListener() {
                        @Override
                        public void onNoDoubleClick(View v) {
                            final int position = finalHolder.getAdapterPosition();
                            mCallBack.startMap(getItem(position).getNodes().get(0).getTruckGoodsAddr());
                        }
                    });

                    break;
                case TYPE_ITEM_BOTTOM:
                    viewBottom = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_node_bottom, parent, false);
                    holder = new ViewHolderBottom(viewBottom);
                    break;
            }
        }
        return holder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        DriverOrderMergeNodeWrapper driverOrderMergeNodeWrapper = getList().get(position);
        if (getItem(position).getNodeType().equals("0")) {
            // 出车
            ViewHolderTop itemViewHolder = (ViewHolderTop) holder;
            itemViewHolder.tvTitle.setText(driverOrderMergeNodeWrapper.getName());
            itemViewHolder.tvContent.setText("已出车");
            if (driverOrderMergeNodeWrapper.getNodes().get(0).getDriverOrderNodeList().size() > 0 && !TextUtils.isEmpty(driverOrderMergeNodeWrapper.getNodes().get(0).getDriverOrderNodeList().get(0).getUpdated())) {
                itemViewHolder.tvTime.setText(DateUtils.stringToString(driverOrderMergeNodeWrapper.getNodes().get(0).getDriverOrderNodeList().get(0).getUpdated(), "MM-dd HH:mm:ss"));
                itemViewHolder.tvAddress.setText(driverOrderMergeNodeWrapper.getNodes().get(0).getDriverOrderNodeList().get(0).getAddress());
            }
        } else if (getItem(position).getNodeType().equals("1") || getItem(position).getNodeType().equals("3")) {
            // 码头
            ViewHolderWharf itemViewHolder = (ViewHolderWharf) holder;
            itemViewHolder.tvTitle.setText(driverOrderMergeNodeWrapper.getName());

            // 如果是必填并且没填就是感叹号,DetailFlag：1为必填。success：1为已填
            if (driverOrderMergeNodeWrapper.getNodes().get(0).getDetailFlag() != null && driverOrderMergeNodeWrapper.getNodes().get(0).getDetailFlag().equals("1")
                    && driverOrderMergeNodeWrapper.getNodes().get(0).getDetailSuccess() != null && driverOrderMergeNodeWrapper.getNodes().get(0).getDetailSuccess().equals("0")) {
                // 为必填，但是没填，则显示红色的感叹号
                itemViewHolder.tvRecord.setText("!动态记录");
                itemViewHolder.tvRecord.setTextColor(mContext.getResources().getColor(R.color.red500));
                itemViewHolder.tvRecord.setBackgroundResource(R.drawable.background_shape_10_red);
            } else {
                itemViewHolder.tvRecord.setText("动态记录");
                itemViewHolder.tvRecord.setTextColor(mContext.getResources().getColor(R.color.primary));
                itemViewHolder.tvRecord.setBackgroundResource(R.drawable.background_shape_10_primary);
            }

            // 判断4种状态
            switch (driverOrderMergeNodeWrapper.getFinishStatus()) {
                case "0":
                    // 未完成
                    itemViewHolder.ringsView.stop();
                    itemViewHolder.ringsView.setVisibility(View.INVISIBLE);
                    itemViewHolder.imgSpotGrey.setVisibility(View.VISIBLE);
                    itemViewHolder.imgSpotGrey.setImageResource(R.drawable.ic_spot_black);
                    itemViewHolder.ringsView.setVisibility(View.INVISIBLE);
                    itemViewHolder.tvContent.setVisibility(View.GONE);
                    itemViewHolder.tvTime.setVisibility(View.GONE);
                    itemViewHolder.tvAddress.setVisibility(View.GONE);
                    itemViewHolder.tvContent2.setVisibility(View.GONE);
                    itemViewHolder.tvTime2.setVisibility(View.GONE);
                    itemViewHolder.tvAddress2.setVisibility(View.GONE);
                    itemViewHolder.tvRecord.setVisibility(View.GONE);
                    itemViewHolder.vTitle.setVisibility(View.GONE);

                    // 字体变灰色
                    itemViewHolder.tvTitle.setTextColor(ContextCompat.getColor(mContext, R.color.grey777777));
                    itemViewHolder.tvContent.setTextColor(ContextCompat.getColor(mContext, R.color.grey777777));
                    itemViewHolder.tvTime.setTextColor(ContextCompat.getColor(mContext, R.color.grey777777));
                    itemViewHolder.tvAddress.setTextColor(ContextCompat.getColor(mContext, R.color.grey777777));
                    itemViewHolder.tvContent2.setTextColor(ContextCompat.getColor(mContext, R.color.grey777777));
                    itemViewHolder.tvTime2.setTextColor(ContextCompat.getColor(mContext, R.color.grey777777));
                    itemViewHolder.tvAddress2.setTextColor(ContextCompat.getColor(mContext, R.color.grey777777));
                    break;
                case "1":
                    // 正在前往
                    itemViewHolder.ringsView.start();
                    itemViewHolder.ringsView.setVisibility(View.VISIBLE);
                    itemViewHolder.ringsView.setCoreColor(R.color.green_primary);
                    itemViewHolder.ringsView.setColor(R.color.green_primary_dark);
                    itemViewHolder.imgSpotGrey.setVisibility(View.INVISIBLE);
                    itemViewHolder.tvContent.setVisibility(View.GONE);
                    itemViewHolder.tvTime.setVisibility(View.GONE);
                    itemViewHolder.tvAddress.setVisibility(View.GONE);
                    itemViewHolder.tvContent2.setVisibility(View.GONE);
                    itemViewHolder.tvTime2.setVisibility(View.GONE);
                    itemViewHolder.tvAddress2.setVisibility(View.GONE);
                    itemViewHolder.tvRecord.setVisibility(View.GONE);
                    itemViewHolder.vTitle.setVisibility(View.GONE);

                    // 字体变灰色
                    itemViewHolder.tvTitle.setTextColor(ContextCompat.getColor(mContext, R.color.black));
                    itemViewHolder.tvContent.setTextColor(ContextCompat.getColor(mContext, R.color.black));
                    itemViewHolder.tvTime.setTextColor(ContextCompat.getColor(mContext, R.color.black));
                    itemViewHolder.tvAddress.setTextColor(ContextCompat.getColor(mContext, R.color.black));
                    itemViewHolder.tvContent2.setTextColor(ContextCompat.getColor(mContext, R.color.black));
                    itemViewHolder.tvTime2.setTextColor(ContextCompat.getColor(mContext, R.color.black));
                    itemViewHolder.tvAddress2.setTextColor(ContextCompat.getColor(mContext, R.color.black));
                    break;
                case "2":
                    // 已到达，未离开
                    itemViewHolder.ringsView.start();
                    itemViewHolder.ringsView.setVisibility(View.VISIBLE);
                    itemViewHolder.ringsView.setCoreColor(R.attr.colorPrimary);
                    itemViewHolder.ringsView.setColor(R.attr.colorPrimaryDark);
                    itemViewHolder.imgSpotGrey.setVisibility(View.INVISIBLE);
//                    itemViewHolder.imgSpotGrey.setImageResource(R.drawable.ic_spot_yellow);
                    itemViewHolder.tvContent.setVisibility(View.VISIBLE);
                    itemViewHolder.tvTime.setVisibility(View.VISIBLE);
                    itemViewHolder.tvAddress.setVisibility(View.VISIBLE);
                    itemViewHolder.tvContent2.setVisibility(View.GONE);
                    itemViewHolder.tvTime2.setVisibility(View.GONE);
                    itemViewHolder.tvAddress2.setVisibility(View.GONE);
                    itemViewHolder.tvRecord.setVisibility(View.VISIBLE);
                    itemViewHolder.vTitle.setVisibility(View.VISIBLE);
                    itemViewHolder.tvContent.setText("已到达");
                    itemViewHolder.tvTime.setText(driverOrderMergeNodeWrapper.getNodes().get(0).getDriverOrderNodeList().get(0).getUpdated());
                    itemViewHolder.tvAddress.setText(driverOrderMergeNodeWrapper.getNodes().get(0).getDriverOrderNodeList().get(0).getAddress());

                    // 字体变灰色
                    itemViewHolder.tvTitle.setTextColor(ContextCompat.getColor(mContext, R.color.black));
                    itemViewHolder.tvContent.setTextColor(ContextCompat.getColor(mContext, R.color.black));
                    itemViewHolder.tvTime.setTextColor(ContextCompat.getColor(mContext, R.color.black));
                    itemViewHolder.tvAddress.setTextColor(ContextCompat.getColor(mContext, R.color.black));
                    itemViewHolder.tvContent2.setTextColor(ContextCompat.getColor(mContext, R.color.black));
                    itemViewHolder.tvTime2.setTextColor(ContextCompat.getColor(mContext, R.color.black));
                    itemViewHolder.tvAddress2.setTextColor(ContextCompat.getColor(mContext, R.color.black));
                    break;
                case "3":
                    // 已到达，已离开
                    itemViewHolder.ringsView.stop();
                    itemViewHolder.ringsView.setVisibility(View.INVISIBLE);
                    itemViewHolder.imgSpotGrey.setVisibility(View.VISIBLE);
                    itemViewHolder.imgSpotGrey.setImageResource(R.drawable.ic_spot_grey);
                    itemViewHolder.tvContent.setVisibility(View.VISIBLE);
                    itemViewHolder.tvTime.setVisibility(View.VISIBLE);
                    itemViewHolder.tvAddress.setVisibility(View.VISIBLE);
                    itemViewHolder.tvContent2.setVisibility(View.VISIBLE);
                    itemViewHolder.tvTime2.setVisibility(View.VISIBLE);
                    itemViewHolder.tvAddress2.setVisibility(View.VISIBLE);
                    itemViewHolder.tvRecord.setVisibility(View.VISIBLE);
                    itemViewHolder.vTitle.setVisibility(View.VISIBLE);
                    if (driverOrderMergeNodeWrapper.getNodes() != null && driverOrderMergeNodeWrapper.getNodes().size() > 0 &&
                            driverOrderMergeNodeWrapper.getNodes().get(0).getDriverOrderNodeList() != null && driverOrderMergeNodeWrapper.getNodes().get(0).getDriverOrderNodeList().size() > 0) {
                        itemViewHolder.tvContent.setText("已到达");
                        itemViewHolder.tvTime.setText(driverOrderMergeNodeWrapper.getNodes().get(0).getDriverOrderNodeList().get(0).getUpdated());
                        itemViewHolder.tvAddress.setText(driverOrderMergeNodeWrapper.getNodes().get(0).getDriverOrderNodeList().get(0).getAddress());
                    }
                    if (driverOrderMergeNodeWrapper.getNodes() != null && driverOrderMergeNodeWrapper.getNodes().size() > 0 &&
                            driverOrderMergeNodeWrapper.getNodes().get(0).getDriverOrderNodeList() != null && driverOrderMergeNodeWrapper.getNodes().get(0).getDriverOrderNodeList().size() > 1) {
                        itemViewHolder.tvContent2.setText("已离开");
                        itemViewHolder.tvTime2.setText(driverOrderMergeNodeWrapper.getNodes().get(0).getDriverOrderNodeList().get(1).getUpdated());
                        itemViewHolder.tvAddress2.setText(driverOrderMergeNodeWrapper.getNodes().get(0).getDriverOrderNodeList().get(1).getAddress());
                    }

                    // 字体变灰色
                    itemViewHolder.tvTitle.setTextColor(ContextCompat.getColor(mContext, R.color.greyBBB));
                    itemViewHolder.tvContent.setTextColor(ContextCompat.getColor(mContext, R.color.greyBBB));
                    itemViewHolder.tvTime.setTextColor(ContextCompat.getColor(mContext, R.color.greyBBB));
                    itemViewHolder.tvAddress.setTextColor(ContextCompat.getColor(mContext, R.color.greyBBB));
                    itemViewHolder.tvContent2.setTextColor(ContextCompat.getColor(mContext, R.color.greyBBB));
                    itemViewHolder.tvTime2.setTextColor(ContextCompat.getColor(mContext, R.color.greyBBB));
                    itemViewHolder.tvAddress2.setTextColor(ContextCompat.getColor(mContext, R.color.greyBBB));
                    break;
            }

            // 还/提 柜
            switch (driverOrderMergeNodeWrapper.getMark()) {
                case "提柜":
                    itemViewHolder.imgTag.setImageResource(R.drawable.ic_bq_ti);
                    break;
                case "还柜":
                    itemViewHolder.imgTag.setImageResource(R.drawable.ic_bq_huan);
                    break;
            }


        } else if (getItem(position).getNodeType().equals("2")) {
            //判断是否有装货和卸货
            StringBuilder isloadingAndUnloading = new StringBuilder();

            // 门点
            ViewHolderDoor itemViewHolder = (ViewHolderDoor) holder;
            itemViewHolder.tvTitle.setText(driverOrderMergeNodeWrapper.getName());

            // 记录颜色，后面用于循环多个门点赋值颜色
            int color = 0;

            // 判断4种状态
            switch (driverOrderMergeNodeWrapper.getFinishStatus()) {
                case "0":
                    // 未完成
                    itemViewHolder.ringsView.stop();
                    itemViewHolder.ringsView.setVisibility(View.INVISIBLE);
                    itemViewHolder.imgSpotGrey.setVisibility(View.VISIBLE);
                    itemViewHolder.imgSpotGrey.setImageResource(R.drawable.ic_spot_black);
                    itemViewHolder.tvContent.setVisibility(View.GONE);
                    itemViewHolder.tvTime.setVisibility(View.GONE);
                    itemViewHolder.tvAddress.setVisibility(View.GONE);
                    itemViewHolder.tvContent2.setVisibility(View.GONE);
                    itemViewHolder.tvTime2.setVisibility(View.GONE);
                    itemViewHolder.tvAddress2.setVisibility(View.GONE);
                    itemViewHolder.vTitle.setVisibility(View.GONE);

                    // 字体变灰色
                    itemViewHolder.tvTitle.setTextColor(ContextCompat.getColor(mContext, R.color.grey777777));
                    itemViewHolder.tvContent.setTextColor(ContextCompat.getColor(mContext, R.color.grey777777));
                    itemViewHolder.tvTime.setTextColor(ContextCompat.getColor(mContext, R.color.grey777777));
                    itemViewHolder.tvAddress.setTextColor(ContextCompat.getColor(mContext, R.color.grey777777));
                    itemViewHolder.tvContent2.setTextColor(ContextCompat.getColor(mContext, R.color.grey777777));
                    itemViewHolder.tvTime2.setTextColor(ContextCompat.getColor(mContext, R.color.grey777777));
                    itemViewHolder.tvAddress2.setTextColor(ContextCompat.getColor(mContext, R.color.grey777777));

                    color = R.color.grey777777;

                    break;
                case "1":
                    // 正在前往
                    itemViewHolder.ringsView.start();
                    itemViewHolder.ringsView.setVisibility(View.VISIBLE);
                    itemViewHolder.ringsView.setCoreColor(R.color.green_primary);
                    itemViewHolder.ringsView.setColor(R.color.green_primary_dark);
                    itemViewHolder.imgSpotGrey.setVisibility(View.INVISIBLE);
                    itemViewHolder.tvContent.setVisibility(View.GONE);
                    itemViewHolder.tvTime.setVisibility(View.GONE);
                    itemViewHolder.tvAddress.setVisibility(View.GONE);
                    itemViewHolder.tvContent2.setVisibility(View.GONE);
                    itemViewHolder.tvTime2.setVisibility(View.GONE);
                    itemViewHolder.tvAddress2.setVisibility(View.GONE);
                    itemViewHolder.vTitle.setVisibility(View.GONE);

                    // 字体变颜色
                    itemViewHolder.tvTitle.setTextColor(ContextCompat.getColor(mContext, R.color.black));
                    itemViewHolder.tvContent.setTextColor(ContextCompat.getColor(mContext, R.color.black));
                    itemViewHolder.tvTime.setTextColor(ContextCompat.getColor(mContext, R.color.black));
                    itemViewHolder.tvAddress.setTextColor(ContextCompat.getColor(mContext, R.color.black));
                    itemViewHolder.tvContent2.setTextColor(ContextCompat.getColor(mContext, R.color.black));
                    itemViewHolder.tvTime2.setTextColor(ContextCompat.getColor(mContext, R.color.black));
                    itemViewHolder.tvAddress2.setTextColor(ContextCompat.getColor(mContext, R.color.black));

                    color = R.color.black;

                    break;
                case "2":
                    // 已到达，未离开
                    itemViewHolder.ringsView.start();
                    itemViewHolder.ringsView.setVisibility(View.VISIBLE);
                    itemViewHolder.ringsView.setCoreColor(R.attr.colorPrimary);
                    itemViewHolder.ringsView.setColor(R.attr.colorPrimaryDark);
                    itemViewHolder.imgSpotGrey.setVisibility(View.INVISIBLE);
                    itemViewHolder.tvContent.setVisibility(View.VISIBLE);
                    itemViewHolder.tvTime.setVisibility(View.VISIBLE);
                    itemViewHolder.tvAddress.setVisibility(View.VISIBLE);
                    itemViewHolder.tvContent2.setVisibility(View.GONE);
                    itemViewHolder.tvTime2.setVisibility(View.GONE);
                    itemViewHolder.tvAddress2.setVisibility(View.GONE);
                    itemViewHolder.vTitle.setVisibility(View.VISIBLE);
                    itemViewHolder.tvContent.setText("已到达");
                    itemViewHolder.tvTime.setText(driverOrderMergeNodeWrapper.getNodes().get(0).getDriverOrderNodeList().get(0).getUpdated());
                    itemViewHolder.tvAddress.setText(driverOrderMergeNodeWrapper.getNodes().get(0).getDriverOrderNodeList().get(0).getAddress());

                    // 字体变颜色
                    itemViewHolder.tvTitle.setTextColor(ContextCompat.getColor(mContext, R.color.black));
                    itemViewHolder.tvContent.setTextColor(ContextCompat.getColor(mContext, R.color.black));
                    itemViewHolder.tvTime.setTextColor(ContextCompat.getColor(mContext, R.color.black));
                    itemViewHolder.tvAddress.setTextColor(ContextCompat.getColor(mContext, R.color.black));
                    itemViewHolder.tvContent2.setTextColor(ContextCompat.getColor(mContext, R.color.black));
                    itemViewHolder.tvTime2.setTextColor(ContextCompat.getColor(mContext, R.color.black));
                    itemViewHolder.tvAddress2.setTextColor(ContextCompat.getColor(mContext, R.color.black));

                    color = R.color.black;
                    break;
                case "3":
                    // 已到达，已离开
                    itemViewHolder.ringsView.stop();
                    itemViewHolder.ringsView.setVisibility(View.INVISIBLE);
                    itemViewHolder.imgSpotGrey.setVisibility(View.VISIBLE);
                    itemViewHolder.imgSpotGrey.setImageResource(R.drawable.ic_spot_grey);
                    itemViewHolder.tvContent.setVisibility(View.VISIBLE);
                    itemViewHolder.tvTime.setVisibility(View.VISIBLE);
                    itemViewHolder.tvAddress.setVisibility(View.VISIBLE);
                    itemViewHolder.tvContent2.setVisibility(View.VISIBLE);
                    itemViewHolder.tvTime2.setVisibility(View.VISIBLE);
                    itemViewHolder.tvAddress2.setVisibility(View.VISIBLE);
                    itemViewHolder.vTitle.setVisibility(View.VISIBLE);
                    if (driverOrderMergeNodeWrapper.getNodes() != null && driverOrderMergeNodeWrapper.getNodes().size() > 0 &&
                            driverOrderMergeNodeWrapper.getNodes().get(0).getDriverOrderNodeList() != null && driverOrderMergeNodeWrapper.getNodes().get(0).getDriverOrderNodeList().size() > 0) {
                        itemViewHolder.tvContent.setText("已到达");
                        itemViewHolder.tvTime.setText(driverOrderMergeNodeWrapper.getNodes().get(0).getDriverOrderNodeList().get(0).getUpdated());
                        itemViewHolder.tvAddress.setText(driverOrderMergeNodeWrapper.getNodes().get(0).getDriverOrderNodeList().get(0).getAddress());
                    }
                    if (driverOrderMergeNodeWrapper.getNodes() != null && driverOrderMergeNodeWrapper.getNodes().size() > 0 &&
                            driverOrderMergeNodeWrapper.getNodes().get(0).getDriverOrderNodeList() != null && driverOrderMergeNodeWrapper.getNodes().get(0).getDriverOrderNodeList().size() > 1) {
                        itemViewHolder.tvContent2.setText("已离开");
                        itemViewHolder.tvTime2.setText(driverOrderMergeNodeWrapper.getNodes().get(0).getDriverOrderNodeList().get(1).getUpdated());
                        itemViewHolder.tvAddress2.setText(driverOrderMergeNodeWrapper.getNodes().get(0).getDriverOrderNodeList().get(1).getAddress());
                    }
                    // 字体变灰色
                    itemViewHolder.tvTitle.setTextColor(ContextCompat.getColor(mContext, R.color.greyBBB));
                    itemViewHolder.tvContent.setTextColor(ContextCompat.getColor(mContext, R.color.greyBBB));
                    itemViewHolder.tvTime.setTextColor(ContextCompat.getColor(mContext, R.color.greyBBB));
                    itemViewHolder.tvAddress.setTextColor(ContextCompat.getColor(mContext, R.color.greyBBB));
                    itemViewHolder.tvContent2.setTextColor(ContextCompat.getColor(mContext, R.color.greyBBB));
                    itemViewHolder.tvTime2.setTextColor(ContextCompat.getColor(mContext, R.color.greyBBB));
                    itemViewHolder.tvAddress2.setTextColor(ContextCompat.getColor(mContext, R.color.greyBBB));

                    color = R.color.greyBBB;
                    break;
            }


            // 循环门点
            for (int i = 0; i < getItem(position).getNodes().size(); i++) {
                //判断是否有装货和卸货
                isloadingAndUnloading.append(getItem(position).getNodes().get(i).getParentName());

                ((TextView) (itemViewHolder.llAddress.getChildAt(i).findViewById(R.id.tvTruckGoodsAddrAddress))).setText(getItem(position).getNodes().get(i).getTruckGoodsAddr().getAddress());
                ((TextView) (itemViewHolder.llAddress.getChildAt(i).findViewById(R.id.tvTruckGoodsAddrAddress))).setTextColor(ContextCompat.getColor(mContext, color));
                ((TextView) (itemViewHolder.llAddress.getChildAt(i).findViewById(R.id.tvTruckGoodsAddrContacts))).setText("联系人：" + getItem(position).getNodes().get(i).getTruckGoodsAddr().getContacts());
                ((TextView) (itemViewHolder.llAddress.getChildAt(i).findViewById(R.id.tvTruckGoodsAddrContacts))).setTextColor(ContextCompat.getColor(mContext, color));
                ((TextView) (itemViewHolder.llAddress.getChildAt(i).findViewById(R.id.tvTruckGoodsAddrMobile))).setText(getItem(position).getNodes().get(i).getTruckGoodsAddr().getMobile());
                ((TextView) (itemViewHolder.llAddress.getChildAt(i).findViewById(R.id.tvTruckGoodsAddrMobile))).setTextColor(ContextCompat.getColor(mContext, color));
                // 下划线
                ((TextView) (itemViewHolder.llAddress.getChildAt(i).findViewById(R.id.tvTruckGoodsAddrMobile))).getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);

                ((TextView) (itemViewHolder.llAddress.getChildAt(i).findViewById(R.id.tvTruckGoodsAddrPhone))).setText(getItem(position).getNodes().get(i).getTruckGoodsAddr().getTel());
                ((TextView) (itemViewHolder.llAddress.getChildAt(i).findViewById(R.id.tvTruckGoodsAddrPhone))).setTextColor(ContextCompat.getColor(mContext, color));
                // 下划线
                ((TextView) (itemViewHolder.llAddress.getChildAt(i).findViewById(R.id.tvTruckGoodsAddrPhone))).getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);

                if (getItem(position).getNodes().get(i).getParentName().equals("装货")) {
                    ((TextView) (itemViewHolder.llAddress.getChildAt(i).findViewById(R.id.tvTruckGoodsAddrRemarks))).setText("装货说明：" + getItem(position).getNodes().get(i).getTruckGoodsAddr().getRemarks());
                    ((TextView) (itemViewHolder.llAddress.getChildAt(i).findViewById(R.id.tvTruckGoodsAddrRemarks))).setTextColor(ContextCompat.getColor(mContext, color));
                } else {
                    ((TextView) (itemViewHolder.llAddress.getChildAt(i).findViewById(R.id.tvTruckGoodsAddrRemarks))).setText("卸货说明：" + getItem(position).getNodes().get(i).getTruckGoodsAddr().getRemarks());
                    ((TextView) (itemViewHolder.llAddress.getChildAt(i).findViewById(R.id.tvTruckGoodsAddrRemarks))).setTextColor(ContextCompat.getColor(mContext, color));
                }

                // 如果是必填并且没填就是感叹号,DetailFlag：1为必填。success：1为已填
                if (driverOrderMergeNodeWrapper.getNodes().get(i).getDetailFlag() != null && driverOrderMergeNodeWrapper.getNodes().get(i).getDetailFlag().equals("1")
                        && driverOrderMergeNodeWrapper.getNodes().get(i).getDetailSuccess() != null && driverOrderMergeNodeWrapper.getNodes().get(i).getDetailSuccess().equals("0")) {
                    // 为必填，但是没填，则显示红色的感叹号
                    ((TextView) (itemViewHolder.llAddress.getChildAt(i).findViewById(R.id.tvRecord))).setText("!动态记录");
                    ((TextView) (itemViewHolder.llAddress.getChildAt(i).findViewById(R.id.tvRecord))).setTextColor(mContext.getResources().getColor(R.color.red500));
                    itemViewHolder.llAddress.getChildAt(i).findViewById(R.id.tvRecord).setBackgroundResource(R.drawable.background_shape_10_red);
                } else {
                    ((TextView) (itemViewHolder.llAddress.getChildAt(i).findViewById(R.id.tvRecord))).setText("动态记录");
                    ((TextView) (itemViewHolder.llAddress.getChildAt(i).findViewById(R.id.tvRecord))).setTextColor(mContext.getResources().getColor(R.color.primary));
                    itemViewHolder.llAddress.getChildAt(i).findViewById(R.id.tvRecord).setBackgroundResource(R.drawable.background_shape_10_primary);
                }
            }


            // 装\卸 货
            if (isloadingAndUnloading.toString().contains(mContext.getResources().getString(R.string.loading_goods)) && isloadingAndUnloading.toString().contains(mContext.getResources().getString(R.string.unloading_goods))) {
                itemViewHolder.imgTag2.setImageResource(R.drawable.ic_bq_xie);
                itemViewHolder.imgTag.setImageResource(R.drawable.ic_bq_zhuang);
                itemViewHolder.imgTag2.setVisibility(View.VISIBLE);
            } else if (isloadingAndUnloading.toString().contains(mContext.getResources().getString(R.string.loading_goods))) {
                itemViewHolder.imgTag.setImageResource(R.drawable.ic_bq_zhuang);
                itemViewHolder.imgTag2.setVisibility(View.GONE);
            } else if (isloadingAndUnloading.toString().contains(mContext.getResources().getString(R.string.unloading_goods))) {
                itemViewHolder.imgTag.setImageResource(R.drawable.ic_bq_xie);
                itemViewHolder.imgTag2.setVisibility(View.GONE);
            }
        } else if (getItem(position).getNodeType().equals("4")) {
            // 完成
            ViewHolderBottom itemViewHolder = (ViewHolderBottom) holder;
            // 判断4种状态
            switch (driverOrderMergeNodeWrapper.getFinishStatus()) {
                case "0":
                    // 未完成
                    itemViewHolder.ringsView.stop();
                    itemViewHolder.ringsView.setVisibility(View.INVISIBLE);
                    itemViewHolder.imgSpotGrey.setVisibility(View.VISIBLE);
                    itemViewHolder.imgSpotGrey.setImageResource(R.drawable.ic_spot_black);

                    itemViewHolder.tvTitle.setTextColor(ContextCompat.getColor(mContext, R.color.grey777777));
                    break;
                case "1":
                    // 正在前往
                    itemViewHolder.ringsView.start();
                    itemViewHolder.ringsView.setVisibility(View.VISIBLE);
                    itemViewHolder.ringsView.setCoreColor(R.color.green_primary);
                    itemViewHolder.ringsView.setColor(R.color.green_primary_dark);
                    itemViewHolder.imgSpotGrey.setVisibility(View.INVISIBLE);

                    itemViewHolder.tvTitle.setTextColor(ContextCompat.getColor(mContext, R.color.black));
                    break;
                case "2":
                    // 已到达，未离开
                    itemViewHolder.ringsView.start();
                    itemViewHolder.ringsView.setVisibility(View.VISIBLE);
                    itemViewHolder.ringsView.setCoreColor(R.attr.colorPrimary);
                    itemViewHolder.ringsView.setColor(R.attr.colorPrimaryDark);
                    itemViewHolder.imgSpotGrey.setVisibility(View.INVISIBLE);

                    itemViewHolder.tvTitle.setTextColor(ContextCompat.getColor(mContext, R.color.black));
                    break;
                case "3":
                    // 已到达，已离开
                    itemViewHolder.ringsView.stop();
                    itemViewHolder.ringsView.setVisibility(View.INVISIBLE);
                    itemViewHolder.imgSpotGrey.setVisibility(View.VISIBLE);
                    itemViewHolder.imgSpotGrey.setImageResource(R.drawable.ic_spot_grey);

                    itemViewHolder.tvTitle.setTextColor(ContextCompat.getColor(mContext, R.color.greyBBB));
                    break;
            }
        }
    }

    public interface CallBack {

        /**
         * 打开富文本框提交节点
         *
         * @param driverOrderNodeWrapper 节点
         */
        void startOrderNodeRichTextFragement(DriverOrderNodeWrapper driverOrderNodeWrapper);

        /**
         * 打开电话
         *
         * @param mobile 手机
         */
        void startCallPhone(String mobile);

        /**
         * 打开地图
         */
        void startMap(DriverNodeAddrVo driverNodeAddrVo);

    }

    public static class ViewHolderTop extends RecyclerView.ViewHolder {
        public View rootView;
        public ImageView imgSpotGrey;
        public View vLineBottom;
        public TextView tvTitle;
        public View vTitle;
        public TextView tvContent;
        public TextView tvTime;
        public TextView tvAddress;
        public CardView cardView2;

        public ViewHolderTop(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.imgSpotGrey = rootView.findViewById(R.id.imgSpotGrey);
            this.vLineBottom = rootView.findViewById(R.id.vLineBottom);
            this.tvTitle = rootView.findViewById(R.id.tvTitle);
            this.vTitle = rootView.findViewById(R.id.vTitle);
            this.tvContent = rootView.findViewById(R.id.tvContent);
            this.tvTime = rootView.findViewById(R.id.tvTime);
            this.tvAddress = rootView.findViewById(R.id.tvAddress);
            this.cardView2 = rootView.findViewById(R.id.cardView2);
        }

    }

    public static class ViewHolderWharf extends RecyclerView.ViewHolder {
        public View rootView;
        public View vLineTop;
        public View vLineBottom;
        public RingsView ringsView;
        public ImageView imgSpotGrey;
        public TextView tvTitle;
        public ImageView imgNavigation;
        public ImageView imgTag;
        public View vTitle;
        public TextView tvRecord;
        public TextView tvContent;
        public TextView tvTime;
        public TextView tvAddress;
        public TextView tvContent2;
        public TextView tvTime2;
        public TextView tvAddress2;
        public CardView cardView2;

        public ViewHolderWharf(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.vLineTop = rootView.findViewById(R.id.vLineTop);
            this.vLineBottom = rootView.findViewById(R.id.vLineBottom);
            this.ringsView = rootView.findViewById(R.id.ringsView);
            this.imgSpotGrey = rootView.findViewById(R.id.imgSpotGrey);
            this.tvTitle = rootView.findViewById(R.id.tvTitle);
            this.imgNavigation = rootView.findViewById(R.id.imgNavigation);
            this.imgTag = rootView.findViewById(R.id.imgTag);
            this.vTitle = rootView.findViewById(R.id.vTitle);
            this.tvRecord = rootView.findViewById(R.id.tvRecord);
            this.tvContent = rootView.findViewById(R.id.tvContent);
            this.tvTime = rootView.findViewById(R.id.tvTime);
            this.tvAddress = rootView.findViewById(R.id.tvAddress);
            this.tvContent2 = rootView.findViewById(R.id.tvContent2);
            this.tvTime2 = rootView.findViewById(R.id.tvTime2);
            this.tvAddress2 = rootView.findViewById(R.id.tvAddress2);
            this.cardView2 = rootView.findViewById(R.id.cardView2);
        }

    }

    public static class ViewHolderDoor extends RecyclerView.ViewHolder {
        public View rootView;
        public View vLineTop;
        public View vLineBottom;
        public RingsView ringsView;
        public ImageView imgSpotGrey;
        public TextView tvTitle;
        public ImageView imgNavigation;
        public ImageView imgTag;
        public ImageView imgTag2;
        public View vTitle;
        public LinearLayout llAddress;
        public TextView tvContent;
        public TextView tvTime;
        public TextView tvAddress;
        public TextView tvContent2;
        public TextView tvTime2;
        public TextView tvAddress2;
        public CardView cardView2;

        public ViewHolderDoor(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.vLineTop = rootView.findViewById(R.id.vLineTop);
            this.vLineBottom = rootView.findViewById(R.id.vLineBottom);
            this.ringsView = rootView.findViewById(R.id.ringsView);
            this.imgSpotGrey = rootView.findViewById(R.id.imgSpotGrey);
            this.tvTitle = rootView.findViewById(R.id.tvTitle);
            this.imgNavigation = rootView.findViewById(R.id.imgNavigation);
            this.imgTag = rootView.findViewById(R.id.imgTag);
            this.imgTag2 = rootView.findViewById(R.id.imgTag2);
            this.vTitle = rootView.findViewById(R.id.vTitle);
            this.llAddress = rootView.findViewById(R.id.llAddress);
            this.tvContent = rootView.findViewById(R.id.tvContent);
            this.tvTime = rootView.findViewById(R.id.tvTime);
            this.tvAddress = rootView.findViewById(R.id.tvAddress);
            this.tvContent2 = rootView.findViewById(R.id.tvContent2);
            this.tvTime2 = rootView.findViewById(R.id.tvTime2);
            this.tvAddress2 = rootView.findViewById(R.id.tvAddress2);
            this.cardView2 = rootView.findViewById(R.id.cardView2);
        }

    }

    public static class ViewHolderBottom extends RecyclerView.ViewHolder {
        public View rootView;
        public View vLineTop;
        public RingsView ringsView;
        public ImageView imgSpotGrey;
        public TextView tvTitle;
        public CardView cardView2;

        public ViewHolderBottom(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.vLineTop = rootView.findViewById(R.id.vLineTop);
            this.ringsView = rootView.findViewById(R.id.ringsView);
            this.imgSpotGrey = rootView.findViewById(R.id.imgSpotGrey);
            this.tvTitle = rootView.findViewById(R.id.tvTitle);
            this.cardView2 = rootView.findViewById(R.id.cardView2);
        }

    }
}
