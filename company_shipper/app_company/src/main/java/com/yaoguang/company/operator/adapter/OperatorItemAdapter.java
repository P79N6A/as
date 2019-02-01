package com.yaoguang.company.operator.adapter;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.Guideline;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.yaoguang.company.R;
import com.yaoguang.company.operator.OperatorFragment;
import com.yaoguang.greendao.entity.company.AppCompanyBanDanSonoWrapper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhongjh on 2018/7/26.
 */
public class OperatorItemAdapter extends RecyclerView.Adapter<OperatorItemAdapter.ViewHolder> {

    private Context mContext;
    private List<AppCompanyBanDanSonoWrapper> list; // 数据源
    private CallBack mCallBack;
    private ArrayList<Boolean> mIsCheckeds = new ArrayList<>();
    private int positionParent; // 父的索引
    private OperatorAdapter.ViewHolder mViewHolderParent; // 父的控件
    private String mBizType; // 0是货代，1是拖车


    OperatorItemAdapter(Context context,String bizType, CallBack callBack) {
        this.mContext = context;
        this.mBizType = bizType;
        this.mCallBack = callBack;
    }

    /**
     * 赋值选择数据源
     *
     * @param checkeds
     */
    public void setCheckeds(ArrayList<Boolean> checkeds) {
        this.mIsCheckeds = checkeds;
    }

    /**
     * 父的索引
     *
     * @param positionParent 父的索引
     */
    public void setPositionParent(int positionParent) {
        this.positionParent = positionParent;
    }

    /**
     * 父的控件
     *
     * @param mViewHolderParent 父的控件
     */
    public void setmViewHolderParent(OperatorAdapter.ViewHolder mViewHolderParent) {
        this.mViewHolderParent = mViewHolderParent;
    }

    /**
     * 回调事件 - 回调给父部分
     */
    public interface CallBack {
        /**
         * 当前适配器的选择是否全选
         *
         * @param isChecked         是否全选
         * @param isCheckeds        数据源
         * @param positionParent    影响的父索引
         * @param mViewHolderParent 父的控件
         */
        void isCheckBoxAll(boolean isChecked, ArrayList<Boolean> isCheckeds, int positionParent, OperatorAdapter.ViewHolder mViewHolderParent);
    }

    /**
     * @param list 数据源
     */
    public void setData(List<AppCompanyBanDanSonoWrapper> list) {
        this.list = list;
    }

    /**
     * 设置当前选择模式
     *
     * @param checked 是否选择
     */
    public void setChecked(boolean checked) {
        for (int i = 0; i < mIsCheckeds.size(); i++) {
            mIsCheckeds.set(i, checked);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return OperatorFragment.CHILD_VIEW_TYPE;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_operator_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        if (mBizType.equals("0")){
            holder.tvIntimidateTitle.setVisibility(View.GONE);
            holder.tvIntimidateValue.setVisibility(View.GONE);
        }else{
            holder.tvIntimidateTitle.setVisibility(View.VISIBLE);
            holder.tvIntimidateValue.setVisibility(View.VISIBLE);
        }
        view.setOnClickListener(v -> holder.checkBox.performClick());
        return holder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ViewHolder viewHolder = holder;
        AppCompanyBanDanSonoWrapper appCompanyBanDanSonoWrapper = list.get(position);
        viewHolder.tvCabinetNumberValue.setText(appCompanyBanDanSonoWrapper.getContNo());
        viewHolder.tvTitleValue.setText(appCompanyBanDanSonoWrapper.getContNo()); // 封号
        if (!TextUtils.isEmpty(appCompanyBanDanSonoWrapper.getCdDateStr())) {
            viewHolder.tvShipToValue.setText(appCompanyBanDanSonoWrapper.getCdDateStr()); // 船到时间
            viewHolder.tvShipToValue.setTextColor(ContextCompat.getColor(mContext, R.color.black333));
        } else {
            viewHolder.tvShipToValue.setText("船未到"); // 船未到
            viewHolder.tvShipToValue.setTextColor(ContextCompat.getColor(mContext, R.color.red500));
        }
        if (!TextUtils.isEmpty(appCompanyBanDanSonoWrapper.getBdDateStr())) {
            viewHolder.tvOrderValue.setText(appCompanyBanDanSonoWrapper.getBdDateStr()); // 办单时间
            viewHolder.tvOrderValue.setTextColor(ContextCompat.getColor(mContext, R.color.black333));
        } else {
            viewHolder.tvOrderValue.setText("未办单"); // 未办单
            viewHolder.tvOrderValue.setTextColor(ContextCompat.getColor(mContext, R.color.red500));
        }
        if (!TextUtils.isEmpty(appCompanyBanDanSonoWrapper.getMtddDateStr())) {
            viewHolder.tvIntimidateValue.setText(appCompanyBanDanSonoWrapper.getMtddDateStr()); // 封号时间
            viewHolder.tvIntimidateValue.setTextColor(ContextCompat.getColor(mContext, R.color.black333));
        } else {
            viewHolder.tvIntimidateValue.setText("未打单"); // 未打单
            viewHolder.tvIntimidateValue.setTextColor(ContextCompat.getColor(mContext, R.color.red500));
        }

        // 如果是最后一个，就隐藏线
        if (position == getItemCount() - 1) {
            viewHolder.vLineBottom.setVisibility(View.GONE);
        } else {
            viewHolder.vLineBottom.setVisibility(View.VISIBLE);
        }

        viewHolder.checkBox.setOnCheckedChangeListener(null);
        viewHolder.checkBox.setChecked(mIsCheckeds.get(position));
        if (mIsCheckeds.get(position)){
            viewHolder.imgCheck.setImageResource(R.drawable.ic_yix_yellow);
        }else{
            viewHolder.imgCheck.setImageResource(R.drawable.ic_weix_grey);
        }
        viewHolder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            mIsCheckeds.set(position, isChecked);
            if (mIsCheckeds.get(position)){
                viewHolder.imgCheck.setImageResource(R.drawable.ic_yix_yellow);
            }else{
                viewHolder.imgCheck.setImageResource(R.drawable.ic_weix_grey);
            }
            if (isChecked) {
                boolean forIsChecked = true;// 检查所有是否选择的依据
                for (boolean item : mIsCheckeds) {
                    if (!item) {
                        // 如果有一个不是选择状态，则break;
                        forIsChecked = false;
                        break;
                    }
                }
                // 判断是否全选
                if (forIsChecked) {
                    mCallBack.isCheckBoxAll(true, mIsCheckeds, positionParent, mViewHolderParent);
                } else {
                    mCallBack.isCheckBoxAll(false, mIsCheckeds, positionParent, mViewHolderParent);
                }
            } else {
                // 回调
                mCallBack.isCheckBoxAll(false, mIsCheckeds, positionParent, mViewHolderParent);
            }
        });
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.checkBox)
        CheckBox checkBox;
        @BindView(R.id.imgCheck)
        ImageView imgCheck;
        @BindView(R.id.tvCabinetNumberTitle)
        TextView tvCabinetNumberTitle;
        @BindView(R.id.tvCabinetNumberValue)
        TextView tvCabinetNumberValue;
        @BindView(R.id.tvTitleTitle)
        TextView tvTitleTitle;
        @BindView(R.id.tvTitleValue)
        TextView tvTitleValue;
        @BindView(R.id.guideline)
        Guideline guideline;
        @BindView(R.id.clOne)
        ConstraintLayout clOne;
        @BindView(R.id.tvShipToTitle)
        TextView tvShipToTitle;
        @BindView(R.id.tvShipToValue)
        TextView tvShipToValue;
        @BindView(R.id.clTwo)
        ConstraintLayout clTwo;
        @BindView(R.id.tvOrderTitle)
        TextView tvOrderTitle;
        @BindView(R.id.tvOrderValue)
        TextView tvOrderValue;
        @BindView(R.id.tvIntimidateTitle)
        TextView tvIntimidateTitle;
        @BindView(R.id.tvIntimidateValue)
        TextView tvIntimidateValue;
        @BindView(R.id.guidelineThree)
        Guideline guidelineThree;
        @BindView(R.id.clThree)
        ConstraintLayout clThree;
        @BindView(R.id.vLineBottom)
        View vLineBottom;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
