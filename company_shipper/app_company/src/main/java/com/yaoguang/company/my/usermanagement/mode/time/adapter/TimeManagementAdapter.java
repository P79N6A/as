package com.yaoguang.company.my.usermanagement.mode.time.adapter;

import android.support.constraint.Guideline;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yaoguang.company.R;
import com.yaoguang.greendao.entity.company.userLoginTimeWrapper.UserLoginTime;
import com.yaoguang.lib.adapter.BaseLoadMoreRecyclerAdapter;
import com.yaoguang.lib.common.ObjectUtils;

/**
 * Created by zhongjh on 2018/12/6.
 */

public class TimeManagementAdapter extends BaseLoadMoreRecyclerAdapter<UserLoginTime, RecyclerView.ViewHolder> {

    //点击事件
    public interface OnItemDeleteClick {
        void onItemDeleteClick(View itemView, UserLoginTime item, int position);
    }

    protected OnItemDeleteClick mOnItemDeleteClickListener = null;

    public void setOnItemDeleteClick(OnItemDeleteClick listener) {
        this.mOnItemDeleteClickListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_usermanagement_time, parent, false);
        ViewHolder holder = new ViewHolder(view);
        view.setOnClickListener(v -> {
            final int position = holder.getAdapterPosition();

            if (position == 0) {
                // 如果是第一个，那么其他就清空
                for (int i = 1; i < list.size(); i++) {
                    list.get(i).setCheck(false);
                }
                this.notifyDataSetChanged();
            }
            if (position != RecyclerView.NO_POSITION) {
                if (ObjectUtils.parseString(holder.imgCheck.getTag(), "0").equals("0")) {
                    holder.imgCheck.setImageResource(R.drawable.ic_ymr_yellow);
                    holder.imgCheck.setTag("1");
                    for (int i = 0; i < list.size(); i++) {
                        list.get(i).setCheck(false);
                    }
                    list.get(position).setCheck(true);
                    this.notifyDataSetChanged();
                } else {
                    holder.imgCheck.setImageResource(R.drawable.ic_ymr_empty);
                    holder.imgCheck.setTag("0");
                    list.get(position).setCheck(false);
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder itemViewHolder = (ViewHolder) holder;
        UserLoginTime userLoginTime = getItem(position);

        if (list.get(position).isCheck()) {
            itemViewHolder.imgCheck.setImageResource(R.drawable.ic_ymr_yellow);
            itemViewHolder.imgCheck.setTag("1");
        } else {
            itemViewHolder.imgCheck.setImageResource(R.drawable.ic_ymr_empty);
            itemViewHolder.imgCheck.setTag("0");
        }

        // 从周一开始判断是否勾选
        if (ObjectUtils.parseInt(userLoginTime.getIsMon()) == 1) {
            setValue(itemViewHolder, "周一", userLoginTime.getMonTime().split(",")[0] + "~" + userLoginTime.getMonTime().split(",")[1]);
        }
        if (ObjectUtils.parseInt(userLoginTime.getIsTue()) == 1) {
            setValue(itemViewHolder, "周二", userLoginTime.getTueTime().split(",")[0] + "~" + userLoginTime.getTueTime().split(",")[1]);
        }
        if (ObjectUtils.parseInt(userLoginTime.getIsWes()) == 1) {
            setValue(itemViewHolder, "周三", userLoginTime.getWesTime().split(",")[0] + "~" + userLoginTime.getWesTime().split(",")[1]);
        }
        if (ObjectUtils.parseInt(userLoginTime.getIsThi()) == 1) {
            setValue(itemViewHolder, "周四", userLoginTime.getThiTime().split(",")[0] + "~" + userLoginTime.getThiTime().split(",")[1]);
        }
        if (ObjectUtils.parseInt(userLoginTime.getIsFri()) == 1) {
            setValue(itemViewHolder, "周五", userLoginTime.getFriTime().split(",")[0] + "~" + userLoginTime.getFriTime().split(",")[1]);
        }
        if (ObjectUtils.parseInt(userLoginTime.getIsSat()) == 1) {
            setValue(itemViewHolder, "周六", userLoginTime.getSatTime().split(",")[0] + "~" + userLoginTime.getSatTime().split(",")[1]);
        }
        if (ObjectUtils.parseInt(userLoginTime.getIsSun()) == 1) {
            setValue(itemViewHolder, "周日", userLoginTime.getSunTime().split(",")[0] + "~" + userLoginTime.getSunTime().split(",")[1]);
        }
        // 检查所有，检查是否隐藏，然后设置tag回0
        if (ObjectUtils.parseInt(ObjectUtils.parseString(itemViewHolder.tvOneDay.getTag()), 0) != 1) {
            itemViewHolder.tvOneDay.setVisibility(View.GONE);
            itemViewHolder.tvOneDate.setVisibility(View.GONE);
        }
        if (ObjectUtils.parseInt(ObjectUtils.parseString(itemViewHolder.tvTwoDay.getTag()), 0) != 1) {
            itemViewHolder.tvTwoDay.setVisibility(View.GONE);
            itemViewHolder.tvTwoDate.setVisibility(View.GONE);
        }
        if (ObjectUtils.parseInt(ObjectUtils.parseString(itemViewHolder.tvThreeDay.getTag()), 0) != 1) {
            itemViewHolder.tvThreeDay.setVisibility(View.GONE);
            itemViewHolder.tvThreeDate.setVisibility(View.GONE);
        }
        if (ObjectUtils.parseInt(ObjectUtils.parseString(itemViewHolder.tvFourDay.getTag()), 0) != 1) {
            itemViewHolder.tvFourDay.setVisibility(View.GONE);
            itemViewHolder.tvFourDate.setVisibility(View.GONE);
        }
        if (ObjectUtils.parseInt(ObjectUtils.parseString(itemViewHolder.tvFiveDay.getTag()), 0) != 1) {
            itemViewHolder.tvFiveDay.setVisibility(View.GONE);
            itemViewHolder.tvFiveDate.setVisibility(View.GONE);
        }
        if (ObjectUtils.parseInt(ObjectUtils.parseString(itemViewHolder.tvSixDay.getTag()), 0) != 1) {
            itemViewHolder.tvSixDay.setVisibility(View.GONE);
            itemViewHolder.tvSixDate.setVisibility(View.GONE);
        }
        if (ObjectUtils.parseInt(ObjectUtils.parseString(itemViewHolder.tvSevenDay.getTag()), 0) != 1) {
            itemViewHolder.tvSevenDay.setVisibility(View.GONE);
            itemViewHolder.tvSevenDate.setVisibility(View.GONE);
        }
        itemViewHolder.tvOneDay.setTag(0);
        itemViewHolder.tvTwoDay.setTag(0);
        itemViewHolder.tvThreeDay.setTag(0);
        itemViewHolder.tvFourDay.setTag(0);
        itemViewHolder.tvFiveDay.setTag(0);
        itemViewHolder.tvSixDay.setTag(0);
        itemViewHolder.tvSevenDay.setTag(0);
        itemViewHolder.ProjectNameA.setText(userLoginTime.getName());
    }

    /**
     * 赋值,tag赋值position,用于标记属于当前这行内容的，如果不属于，直接隐藏
     *
     * @param holder 控件
     * @param day    天数
     * @param time   时间
     */
    private void setValue(ViewHolder holder, String day, String time) {
        if (ObjectUtils.parseInt(ObjectUtils.parseString(holder.tvOneDay.getTag()), 0) != 1) {
            holder.tvOneDay.setVisibility(View.VISIBLE);
            holder.tvOneDate.setVisibility(View.VISIBLE);
            holder.tvOneDate.setText(time);
            holder.tvOneDay.setTag(1);
            holder.tvOneDay.setText(day);
            return;
        }
        if (ObjectUtils.parseInt(ObjectUtils.parseString(holder.tvTwoDay.getTag()), 0) != 1) {
            holder.tvTwoDay.setVisibility(View.VISIBLE);
            holder.tvTwoDate.setVisibility(View.VISIBLE);
            holder.tvTwoDate.setText(time);
            holder.tvTwoDay.setTag(1);
            holder.tvTwoDay.setText(day);
            return;
        }
        if (ObjectUtils.parseInt(ObjectUtils.parseString(holder.tvThreeDay.getTag()), 0) != 1) {
            holder.tvThreeDay.setVisibility(View.VISIBLE);
            holder.tvThreeDate.setVisibility(View.VISIBLE);
            holder.tvThreeDate.setText(time);
            holder.tvThreeDay.setTag(1);
            holder.tvThreeDay.setText(day);
            return;
        }
        if (ObjectUtils.parseInt(ObjectUtils.parseString(holder.tvFourDay.getTag()), 0) != 1) {
            holder.tvFourDay.setVisibility(View.VISIBLE);
            holder.tvFourDate.setVisibility(View.VISIBLE);
            holder.tvFourDate.setText(time);
            holder.tvFourDay.setTag(1);
            holder.tvFourDay.setText(day);
            return;
        }
        if (ObjectUtils.parseInt(ObjectUtils.parseString(holder.tvFiveDay.getTag()), 0) != 1) {
            holder.tvFiveDay.setVisibility(View.VISIBLE);
            holder.tvFiveDate.setVisibility(View.VISIBLE);
            holder.tvFiveDate.setText(time);
            holder.tvFiveDay.setTag(1);
            holder.tvFiveDay.setText(day);
            return;
        }
        if (ObjectUtils.parseInt(ObjectUtils.parseString(holder.tvSixDay.getTag()), 0) != 1) {
            holder.tvSixDay.setVisibility(View.VISIBLE);
            holder.tvSixDate.setVisibility(View.VISIBLE);
            holder.tvSixDate.setText(time);
            holder.tvSixDay.setTag(1);
            holder.tvSixDay.setText(day);
            return;
        }
        if (ObjectUtils.parseInt(ObjectUtils.parseString(holder.tvSevenDay.getTag()), 0) != 1) {
            holder.tvSevenDay.setVisibility(View.VISIBLE);
            holder.tvSevenDate.setVisibility(View.VISIBLE);
            holder.tvSevenDate.setText(time);
            holder.tvSevenDay.setTag(1);
            holder.tvSevenDay.setText(day);
            return;
        }
    }

    public  class ViewHolder  extends RecyclerView.ViewHolder {
        public View rootView;
        public ImageView imgCheck;
        public TextView ProjectNameA;
        public Guideline guideline1V50;
        public TextView tvOneDay;
        public TextView tvOneDate;
        public TextView tvTwoDay;
        public TextView tvTwoDate;
        public TextView tvThreeDay;
        public TextView tvThreeDate;
        public TextView tvFourDay;
        public TextView tvFourDate;
        public TextView tvFiveDay;
        public TextView tvFiveDate;
        public TextView tvSixDay;
        public TextView tvSixDate;
        public TextView tvSevenDay;
        public TextView tvSevenDate;

        public ViewHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.imgCheck = (ImageView) rootView.findViewById(R.id.imgCheck);
            this.ProjectNameA = (TextView) rootView.findViewById(R.id.ProjectNameA);
            this.guideline1V50 = (Guideline) rootView.findViewById(R.id.guideline1V50);
            this.tvOneDay = (TextView) rootView.findViewById(R.id.tvOneDay);
            this.tvOneDate = (TextView) rootView.findViewById(R.id.tvOneDate);
            this.tvTwoDay = (TextView) rootView.findViewById(R.id.tvTwoDay);
            this.tvTwoDate = (TextView) rootView.findViewById(R.id.tvTwoDate);
            this.tvThreeDay = (TextView) rootView.findViewById(R.id.tvThreeDay);
            this.tvThreeDate = (TextView) rootView.findViewById(R.id.tvThreeDate);
            this.tvFourDay = (TextView) rootView.findViewById(R.id.tvFourDay);
            this.tvFourDate = (TextView) rootView.findViewById(R.id.tvFourDate);
            this.tvFiveDay = (TextView) rootView.findViewById(R.id.tvFiveDay);
            this.tvFiveDate = (TextView) rootView.findViewById(R.id.tvFiveDate);
            this.tvSixDay = (TextView) rootView.findViewById(R.id.tvSixDay);
            this.tvSixDate = (TextView) rootView.findViewById(R.id.tvSixDate);
            this.tvSevenDay = (TextView) rootView.findViewById(R.id.tvSevenDay);
            this.tvSevenDate = (TextView) rootView.findViewById(R.id.tvSevenDate);
        }

    }
}
