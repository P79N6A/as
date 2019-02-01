package com.yaoguang.driver.phone.order.nodeEdit.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yaoguang.driver.R;
import com.yaoguang.greendao.entity.driver.DriverOrderNodeWrapper;
import com.yaoguang.lib.adapter.BaseLoadMoreRecyclerAdapter;
import com.yaoguang.lib.common.ObjectUtils;
import com.yaoguang.lib.common.debounceclick.NoDoubleClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.carbs.android.expandabletextview.library.ExpandableTextView;

/**
 * 适配器
 * Created by zhongjh on 2018/6/7.
 */
public class NodeEditAdapter extends BaseLoadMoreRecyclerAdapter<DriverOrderNodeWrapper, RecyclerView.ViewHolder> implements ExpandableTextView.OnExpandListener {

    private boolean mIsEdit = false; // 是否可编辑
    private static final int TYPE_ITEM_TOP = 222; // 正常的item顶部
    private static final int TYPE_ITEM = 22;// 正常的item
    private static final int TYPE_ITEM_BOTTOM = 2222;// 正常的item底部
    private static final int TYPE_ITEM_TOP_BOTTOM = 22222;// 正常的item底部
    private static final int TYPE_CABINET_TOP = 111;// 柜子的顶部
    private static final int TYPE_CABINET = 11; // 柜子的item
    private static final int TYPE_CABINET_BOTTOM = 1111;// 柜子的底部
    private static final int TYPE_CABINET_TOP_BOTTOM = 11111;// 即是柜子的顶部又是柜子的底部

    private SparseArray<Integer> mPositionsAndStates = new SparseArray<>();// 记录收缩还是展开的textView
    //只要在getview时为其赋值为准确的宽度值即可，无论采用何种方法
    private int etvWidth;

    /**
     * 回调事件
     */
    public void setmOnNodeEditListener(OnNodeEditListener mOnNodeEditListener) {
        this.mOnNodeEditListener = mOnNodeEditListener;
    }

    public NodeEditAdapter(boolean isEdit) {
        mIsEdit = isEdit;
    }

    private OnNodeEditListener mOnNodeEditListener;

    @Override
    public void onExpand(ExpandableTextView view) {
        Object obj = view.getTag();
        if (obj != null && obj instanceof Integer) {
            mPositionsAndStates.put((Integer) obj, view.getExpandState());
        }
    }

    @Override
    public void onShrink(ExpandableTextView view) {
        Object obj = view.getTag();
        if (obj != null && obj instanceof Integer) {
            mPositionsAndStates.put((Integer) obj, view.getExpandState());
        }
    }

    public interface OnNodeEditListener {

        /**
         * 修改上下一个
         *
         * @param view                      view
         * @param driverOrderNodeWrapper    当前实体
         * @param position                  当前索引
         * @param driverOrderNodeWrapperNew 需要更换的driverOrderNodeWrapperNew
         */
        void exchange(View view, DriverOrderNodeWrapper driverOrderNodeWrapper, int position, DriverOrderNodeWrapper driverOrderNodeWrapperNew);

        /**
         * 打开地图
         *
         * @param view                   view
         * @param driverOrderNodeWrapper 当前实体
         * @param position               当前索引
         */
        void startMap(View view, DriverOrderNodeWrapper driverOrderNodeWrapper, int position);

        /**
         * 打开编辑港口
         * @param view view
         * @param driverOrderNodeWrapper 当前实体
         * @param position 当前索引
         */
        void startEditPort(View view,DriverOrderNodeWrapper driverOrderNodeWrapper,int position);

    }

    @Override
    public int getItemViewType(int position) {
        if (getItem(position).getParentNumber().equals("1") || getItem(position).getParentNumber().equals("3")) {
            // 判断是否组合中的第一个或者最后一个，判断跟上一个是否一样系列就行了
            if (position == 0) {
                if (!getItem(position).getParentNumber().equals(getItem(position + 1).getParentNumber())) {
                    // 即是第一个又是柜子的最后一个
                    return TYPE_CABINET_TOP_BOTTOM;
                } else {
                    // 第一个肯定显示柜子顶部的
                    return TYPE_CABINET_TOP;
                }
            } else if (position == getItemCount() - 1) {
                if (!getItem(position).getParentNumber().equals(getItem(position - 1).getParentNumber())) {
                    // 即是最后一个又是柜子的第一个
                    return TYPE_CABINET_TOP_BOTTOM;
                } else {
                    // 最后一个肯定是柜子底部
                    return TYPE_CABINET_BOTTOM;
                }
            } else {
                if (!getItem(position).getParentNumber().equals(getItem(position - 1).getParentNumber())) {
                    // 如果跟上一个不是一样的,那就是柜子顶部的
                    return TYPE_CABINET_TOP;
                } else if (!getItem(position).getParentNumber().equals(getItem(position + 1).getParentNumber())) {
                    // 如果跟下一个不是一样的,那就是柜子底部
                    return TYPE_CABINET_BOTTOM;
                }
            }
            return TYPE_CABINET;
        } else {
            if (!getItem(position).getParentNumber().equals(getItem(position - 1).getParentNumber()) && !getItem(position).getParentNumber().equals(getItem(position + 1).getParentNumber())) {
                // 如果跟上一个不一样 并且 跟下一个不一样，那就是唯一一个
                return TYPE_ITEM_TOP_BOTTOM;
            } else if (!getItem(position).getParentNumber().equals(getItem(position - 1).getParentNumber())) {
                // 如果跟上一个不是一样的,那就是地址顶部的
                return TYPE_ITEM_TOP;
            } else if (!getItem(position).getParentNumber().equals(getItem(position + 1).getParentNumber())) {
                // 如果跟下一个不是一样的,那就是地址底部
                return TYPE_ITEM_BOTTOM;
            }
            return TYPE_ITEM;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        RecyclerView.ViewHolder holder = null;
        RecyclerView.ViewHolder finalHolder;

        switch (viewType) {
            case TYPE_ITEM_TOP:
                view = View.inflate(parent.getContext(), R.layout.item_order_node_edit_address, null);
                holder = new ViewHolderAdress(view);
                finalHolder = holder;
                ((ViewHolderAdress) holder).vTopBottom.setVisibility(View.GONE);
                if (mIsEdit) {
                    ((ViewHolderAdress) holder).imgActionTop.setImageResource(R.drawable.ic_daoxu);
                    ((ViewHolderAdress) holder).imgActionTop.setOnClickListener(new NoDoubleClickListener() {
                        @Override
                        public void onNoDoubleClick(View v) {
                            // 跟下面的交换
                            final int position = finalHolder.getAdapterPosition();
                            DriverOrderNodeWrapper driverOrderNodeWrapper = getList().get(position + 1);
                            exchange(v, getList().get(position), position, driverOrderNodeWrapper);
                        }
                    });
                    ((ViewHolderAdress) holder).imgActionTop.setTag(View.VISIBLE);
                } else {
                    ((ViewHolderAdress) holder).imgActionTop.setVisibility(View.INVISIBLE);
                    ((ViewHolderAdress) holder).imgActionTop.setTag(View.INVISIBLE);
                }
                // 打开地图
                ((ViewHolderAdress) holder).imgNavigation.setOnClickListener(new NoDoubleClickListener() {
                    @Override
                    public void onNoDoubleClick(View v) {
                        final int position = finalHolder.getAdapterPosition();
                        mOnNodeEditListener.startMap(v, getList().get(position), position);
                    }
                });
                break;
            case TYPE_ITEM:
                view = View.inflate(parent.getContext(), R.layout.item_order_node_edit_address, null);
                holder = new ViewHolderAdress(view);
                finalHolder = holder;
                ((ViewHolderAdress) holder).vTop.setVisibility(View.GONE);
                ((ViewHolderAdress) holder).vTopBottom.setVisibility(View.GONE);
                if (mIsEdit) {
                    ((ViewHolderAdress) holder).imgActionTop.setOnClickListener(new NoDoubleClickListener() {
                        @Override
                        public void onNoDoubleClick(View v) {
                            // 跟上面的交换
                            final int position = finalHolder.getAdapterPosition();
                            DriverOrderNodeWrapper driverOrderNodeWrapper = getList().get(position - 1);
                            exchange(v, getList().get(position), position, driverOrderNodeWrapper);
                        }
                    });
                    ((ViewHolderAdress) holder).imgActionTop.setTag(View.VISIBLE);
                } else {
                    ((ViewHolderAdress) holder).imgActionTop.setVisibility(View.INVISIBLE);
                    ((ViewHolderAdress) holder).imgActionTop.setTag(View.INVISIBLE);
                }
                // 打开地图
                ((ViewHolderAdress) holder).imgNavigation.setOnClickListener(new NoDoubleClickListener() {
                    @Override
                    public void onNoDoubleClick(View v) {
                        final int position = finalHolder.getAdapterPosition();
                        mOnNodeEditListener.startMap(v, getList().get(position), position);
                    }
                });
                break;
            case TYPE_ITEM_BOTTOM:
                view = View.inflate(parent.getContext(), R.layout.item_order_node_edit_address, null);
                holder = new ViewHolderAdress(view);
                finalHolder = holder;
                ((ViewHolderAdress) holder).vTop.setVisibility(View.GONE);
                if (mIsEdit) {
                    ((ViewHolderAdress) holder).imgActionTop.setOnClickListener(new NoDoubleClickListener() {
                        @Override
                        public void onNoDoubleClick(View v) {
                            // 跟上面的交换
                            final int position = finalHolder.getAdapterPosition();
                            DriverOrderNodeWrapper driverOrderNodeWrapper = getList().get(position - 1);
                            exchange(v, getList().get(position), position, driverOrderNodeWrapper);
                        }
                    });
                    ((ViewHolderAdress) holder).imgActionTop.setTag(View.VISIBLE);
                } else {
                    ((ViewHolderAdress) holder).imgActionTop.setVisibility(View.INVISIBLE);
                    ((ViewHolderAdress) holder).imgActionTop.setTag(View.INVISIBLE);
                }
                // 打开地图
                ((ViewHolderAdress) holder).imgNavigation.setOnClickListener(new NoDoubleClickListener() {
                    @Override
                    public void onNoDoubleClick(View v) {
                        final int position = finalHolder.getAdapterPosition();
                        mOnNodeEditListener.startMap(v, getList().get(position), position);
                    }
                });
                break;
            case TYPE_ITEM_TOP_BOTTOM:
                view = View.inflate(parent.getContext(), R.layout.item_order_node_edit_address, null);
                holder = new ViewHolderAdress(view);
                finalHolder = holder;
                ((ViewHolderAdress) holder).vTopBottom.setVisibility(View.GONE);
                // 不交换，隐藏
                ((ViewHolderAdress) holder).imgActionTop.setTag(View.INVISIBLE);
                ((ViewHolderAdress) holder).imgActionTop.setVisibility(View.INVISIBLE);
                // 打开地图
                ((ViewHolderAdress) holder).imgNavigation.setOnClickListener(new NoDoubleClickListener() {
                    @Override
                    public void onNoDoubleClick(View v) {
                        final int position = finalHolder.getAdapterPosition();
                        mOnNodeEditListener.startMap(v, getList().get(position), position);
                    }
                });
                break;
            case TYPE_CABINET_TOP:
                view = View.inflate(parent.getContext(), R.layout.item_order_node_edit_cabinet, null);
                holder = new ViewHolderCabinet(view);
                finalHolder = holder;
                ((ViewHolderCabinet) holder).vTop.setVisibility(View.GONE);
                if (mIsEdit) {
                    ((ViewHolderCabinet) holder).imgActionTop.setImageResource(R.drawable.ic_daoxu);
                    ((ViewHolderCabinet) holder).imgActionTop.setOnClickListener(new NoDoubleClickListener() {
                        @Override
                        public void onNoDoubleClick(View v) {
                            // 跟下面的交换
                            final int position = finalHolder.getAdapterPosition();
                            DriverOrderNodeWrapper driverOrderNodeWrapper = getList().get(position + 1);
                            exchange(v, getList().get(position), position, driverOrderNodeWrapper);
                        }
                    });
                    ((ViewHolderCabinet) holder).imgActionTop.setTag(View.VISIBLE);
                } else {
                    ((ViewHolderCabinet) holder).imgActionTop.setVisibility(View.INVISIBLE);
                    ((ViewHolderCabinet) holder).imgActionTop.setTag(View.INVISIBLE);
                }
                // 打开地图
                ((ViewHolderCabinet) holder).imgNavigation.setOnClickListener(new NoDoubleClickListener() {
                    @Override
                    public void onNoDoubleClick(View v) {
                        final int position = finalHolder.getAdapterPosition();
                        mOnNodeEditListener.startMap(v, getList().get(position), position);
                    }
                });
                break;
            case TYPE_CABINET:
                view = View.inflate(parent.getContext(), R.layout.item_order_node_edit_cabinet, null);
                holder = new ViewHolderCabinet(view);
                finalHolder = holder;
                ((ViewHolderCabinet) holder).vTop.setVisibility(View.GONE);
                ((ViewHolderCabinet) holder).vTopBottom.setVisibility(View.GONE);
                if (mIsEdit) {
                    ((ViewHolderCabinet) holder).imgActionTop.setOnClickListener(new NoDoubleClickListener() {
                        @Override
                        public void onNoDoubleClick(View v) {
                            // 跟上面的交换
                            final int position = finalHolder.getAdapterPosition();
                            DriverOrderNodeWrapper driverOrderNodeWrapper = getList().get(position - 1);
                            exchange(v, getList().get(position), position, driverOrderNodeWrapper);
                        }
                    });
                    ((ViewHolderCabinet) holder).imgActionTop.setTag(View.VISIBLE);
                } else {
                    ((ViewHolderCabinet) holder).imgActionTop.setVisibility(View.INVISIBLE);
                    ((ViewHolderCabinet) holder).imgActionTop.setTag(View.INVISIBLE);
                }
                // 打开地图
                ((ViewHolderCabinet) holder).imgNavigation.setOnClickListener(new NoDoubleClickListener() {
                    @Override
                    public void onNoDoubleClick(View v) {
                        final int position = finalHolder.getAdapterPosition();
                        mOnNodeEditListener.startMap(v, getList().get(position), position);
                    }
                });
                break;
            case TYPE_CABINET_BOTTOM:
                view = View.inflate(parent.getContext(), R.layout.item_order_node_edit_cabinet, null);
                holder = new ViewHolderCabinet(view);
                finalHolder = holder;
                ((ViewHolderCabinet) holder).vTop.setVisibility(View.GONE);
                if (mIsEdit) {
                    ((ViewHolderCabinet) holder).imgActionTop.setOnClickListener(new NoDoubleClickListener() {
                        @Override
                        public void onNoDoubleClick(View v) {
                            // 跟上面的交换
                            final int position = finalHolder.getAdapterPosition();
                            DriverOrderNodeWrapper driverOrderNodeWrapper = getList().get(position - 1);
                            exchange(v, getList().get(position), position, driverOrderNodeWrapper);
                        }
                    });
                    ((ViewHolderCabinet) holder).imgActionTop.setTag(View.VISIBLE);
                } else {
                    ((ViewHolderCabinet) holder).imgActionTop.setVisibility(View.INVISIBLE);
                    ((ViewHolderCabinet) holder).imgActionTop.setTag(View.INVISIBLE);
                }
                // 打开地图
                ((ViewHolderCabinet) holder).imgNavigation.setOnClickListener(new NoDoubleClickListener() {
                    @Override
                    public void onNoDoubleClick(View v) {
                        final int position = finalHolder.getAdapterPosition();
                        mOnNodeEditListener.startMap(v, getList().get(position), position);
                    }
                });
                break;
            case TYPE_CABINET_TOP_BOTTOM:
                view = View.inflate(parent.getContext(), R.layout.item_order_node_edit_cabinet, null);
                holder = new ViewHolderCabinet(view);
                finalHolder = holder;

                // 不交换，隐藏
                ((ViewHolderCabinet) holder).imgActionTop.setVisibility(View.INVISIBLE);
                ((ViewHolderCabinet) holder).imgActionTop.setTag(View.INVISIBLE);
                // 打开地图
                ((ViewHolderCabinet) holder).imgNavigation.setOnClickListener(new NoDoubleClickListener() {
                    @Override
                    public void onNoDoubleClick(View v) {
                        final int position = finalHolder.getAdapterPosition();
                        mOnNodeEditListener.startMap(v, getList().get(position), position);
                    }
                });
                break;

        }

        // 默认点击事件，码头不可编辑
        if (view != null) {
            RecyclerView.ViewHolder finalHolderItem = holder;
            view.setOnClickListener(new NoDoubleClickListener() {
                @Override
                public void onNoDoubleClick(View v) {
                    if (viewType != TYPE_CABINET_TOP && viewType != TYPE_CABINET && viewType != TYPE_CABINET_BOTTOM && viewType != TYPE_CABINET_TOP_BOTTOM) {
                        final int position = finalHolderItem.getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mOnItemClickListener.onItemClick(v, getList().get(position), position);
                        }
                    }else{
                        final int position = finalHolderItem.getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mOnNodeEditListener.startEditPort(v, getList().get(position), position);
                        }
                    }
                }
            });
        }

        return holder;
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        // 判断类型
        if (getItem(position).getParentNumber().equals("1") || getItem(position).getParentNumber().equals("3")) {
            ViewHolderCabinet itemViewHolder = (ViewHolderCabinet) holder;
            // 区分提柜还是还柜
            switch (getItem(position).getParentNumber()) {
                case "1":
                    itemViewHolder.imgState.setImageResource(R.drawable.ic_bq_ti);
                    break;
                case "3":
                    itemViewHolder.imgState.setImageResource(R.drawable.ic_bq_huan);
                    break;
            }
            itemViewHolder.tvTitle.setText(getItem(position).getChildName());

            // 判断类型，做相应的事件
            switch (getItemViewType(position)) {
                case TYPE_CABINET_TOP:
                    // 图片往下
                    itemViewHolder.imgActionTop.setImageResource(R.drawable.ic_daoxu);
                    break;
                case TYPE_CABINET_TOP_BOTTOM:
                    // 因为只有一个，隐藏
                    break;
            }

            // 判断是否可编辑,必须本身imgActionTop是可显示的
            if (ObjectUtils.parseInt(itemViewHolder.imgActionTop.getTag()) == View.VISIBLE)
                if (ObjectUtils.parseInt(getItem(position).getIsEditable(),0) == 1)
                    itemViewHolder.imgActionTop.setVisibility(View.VISIBLE);
                else
                    itemViewHolder.imgActionTop.setVisibility(View.INVISIBLE);
            if (ObjectUtils.parseInt(getItem(position).getIsEditable(),0) == 1)
                itemViewHolder.imgIsEdit.setVisibility(View.VISIBLE);
            else
                itemViewHolder.imgIsEdit.setVisibility(View.INVISIBLE);

        } else {
            ViewHolderAdress itemViewHolder = (ViewHolderAdress) holder;
            switch (getItem(position).getParentName()) {
                case "装货":
                    itemViewHolder.imgState.setImageResource(R.drawable.ic_bq_zhuang);
                    break;
                case "卸货":
                    itemViewHolder.imgState.setImageResource(R.drawable.ic_bq_xie);
                    break;
            }

            if (etvWidth == 0) {
                itemViewHolder.tvLoadingInstructions.post(() -> etvWidth = itemViewHolder.tvLoadingInstructions.getWidth());
            }

            itemViewHolder.tvTitle.setText(getItem(position).getChildName());
            itemViewHolder.tvAddress.setText(getItem(position).getTruckGoodsAddr().getAddress());
            itemViewHolder.tvContacts.setText("联系人:" + getItem(position).getTruckGoodsAddr().getContacts());
            itemViewHolder.tvContactsMobile.setText(getItem(position).getTruckGoodsAddr().getMobile());
            itemViewHolder.tvContactsPhone.setText(getItem(position).getTruckGoodsAddr().getTel());
            itemViewHolder.tvLoadingInstructions.setTag(position);
            itemViewHolder.tvLoadingInstructions.setExpandListener(this);
            Integer state = mPositionsAndStates.get(position);
            itemViewHolder.tvLoadingInstructions.updateForRecyclerView("卸货说明:" + getItem(position).getTruckGoodsAddr().getRemarks(), etvWidth, state == null ? 0 : state);
//            itemViewHolder.tvLoadingInstructions.setText("卸货说明:" + getItem(position).getTruckGoodsAddr().getRemarks());
//            itemViewHolder.tvLoadingInstructions.setTrimCollapsedText("查看更多");
//            itemViewHolder.tvLoadingInstructions.setTrimExpandedText("隐藏");

            // 判断是否可编辑,必须本身imgActionTop是可显示的
            if (ObjectUtils.parseInt(itemViewHolder.imgActionTop.getTag()) == View.VISIBLE)
                if (ObjectUtils.parseInt(getItem(position).getIsEditable(),0) == 1)
                    itemViewHolder.imgActionTop.setVisibility(View.VISIBLE);
                else
                    itemViewHolder.imgActionTop.setVisibility(View.INVISIBLE);
            if (ObjectUtils.parseInt(getItem(position).getIsEditable(),0) == 1)
                itemViewHolder.imgIsEdit.setVisibility(View.VISIBLE);
            else
                itemViewHolder.imgIsEdit.setVisibility(View.GONE);
        }
    }

    private void exchange(View view, DriverOrderNodeWrapper driverOrderNodeWrapper, int position, DriverOrderNodeWrapper driverOrderNodeWrapperNew) {
        if (mOnNodeEditListener != null)
            mOnNodeEditListener.exchange(view, driverOrderNodeWrapper, position, driverOrderNodeWrapperNew);
    }

    static class ViewHolderAdress extends RecyclerView.ViewHolder {
        @BindView(R.id.vTop)
        View vTop;
        @BindView(R.id.vTop2)
        View vTop2;
        @BindView(R.id.imgActionTop)
        ImageView imgActionTop;
        @BindView(R.id.imgNavigation)
        ImageView imgNavigation;
        @BindView(R.id.imgState)
        ImageView imgState;
        @BindView(R.id.tvTitle)
        TextView tvTitle;
        @BindView(R.id.tvAddress)
        TextView tvAddress;
        @BindView(R.id.tvContacts)
        TextView tvContacts;
        @BindView(R.id.tvContactsMobile)
        TextView tvContactsMobile;
        @BindView(R.id.tvContactsPhone)
        TextView tvContactsPhone;
        @BindView(R.id.tvLoadingInstructions)
        ExpandableTextView tvLoadingInstructions;
        @BindView(R.id.vTopBottom)
        View vTopBottom;
        @BindView(R.id.imgIsEdit)
        ImageView imgIsEdit;

        ViewHolderAdress(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class ViewHolderCabinet extends RecyclerView.ViewHolder {
        @BindView(R.id.vTop)
        View vTop;
        @BindView(R.id.vTop2)
        View vTop2;
        @BindView(R.id.imgActionTop)
        ImageView imgActionTop;
        @BindView(R.id.tvTitle)
        TextView tvTitle;
        @BindView(R.id.imgState)
        ImageView imgState;
        @BindView(R.id.imgNavigation)
        ImageView imgNavigation;
        @BindView(R.id.vTopBottom)
        View vTopBottom;
        @BindView(R.id.imgIsEdit)
        ImageView imgIsEdit;

        ViewHolderCabinet(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
