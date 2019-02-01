package com.yaoguang.company.operator.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.yaoguang.company.R;
import com.yaoguang.company.operator.OperatorFragment;
import com.yaoguang.greendao.entity.company.AppCompanyBanDanSonoWrapper;
import com.yaoguang.greendao.entity.company.AppCompanyBanDanWrapper;
import com.yaoguang.lib.adapter.BaseLoadMoreRecyclerAdapter;
import com.yaoguang.lib.appcommon.widget.recyclerview.LinearLayoutManagerAutoMeasure;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhongjh on 2018/7/26.
 */
public class OperatorAdapter extends BaseLoadMoreRecyclerAdapter<AppCompanyBanDanWrapper, RecyclerView.ViewHolder> {

    private Context mContext;
    private RecyclerView.RecycledViewPool mRecycledViewPool;// 从窗体传递过来的共享池
    private CallBack mCallBack;
    private ArrayList<Boolean> mIsCheckeds = new ArrayList<>();
    public ArrayList<ArrayList<Boolean>> mIsCheckedChilds = new ArrayList<>();
    private boolean isChecked = false;
    private String mBizType; // 0是货代，1是拖车

    public void setmRecycledViewPool(Context context, RecyclerView.RecycledViewPool mRecycledViewPool, CallBack callBack,String bizType) {
        this.mContext = context;
        this.mRecycledViewPool = mRecycledViewPool;
        this.mCallBack = callBack;
        this.mBizType = bizType;
    }

    /**
     * 回调事件 - 回调给主页面的是否全选的单选框
     */
    public interface CallBack {
        /**
         * 当前适配器的选择是否全选
         *
         * @param isChecked 是否全选
         */
        void isCheckBoxAll(boolean isChecked);
    }

    @Override
    public void appendToList(List<AppCompanyBanDanWrapper> list) {
        super.appendToList(list);
        for (AppCompanyBanDanWrapper item : list) {
            mIsCheckeds.add(isChecked);
            ArrayList<Boolean> isCheckeds = new ArrayList<>();
            for (AppCompanyBanDanSonoWrapper ignored : item.getSonoList()) {
                isCheckeds.add(isChecked);
            }
            mIsCheckedChilds.add(isCheckeds);
        }
    }

    @Override
    public void clear() {
        super.clear();
        mIsCheckeds.clear();
        mIsCheckedChilds.clear();
    }

    /**
     * 设置当前选择模式
     *
     * @param checked 是否选择
     */
    public void setChecked(boolean checked) {
        isChecked = checked;
        for (int i = 0; i < mIsCheckeds.size(); i++) {
            mIsCheckeds.set(i, checked);
            for (int k = 0; k < mIsCheckedChilds.get(i).size(); k++) {
                mIsCheckedChilds.get(i).set(k, checked);
            }
        }
    }

    /**
     * 获取当前被选择的
     */
    public ArrayList<ArrayList<Boolean>> getmIsCheckedChilds() {
        return mIsCheckedChilds;
    }

    @Override
    public int getItemViewTypeCustom(int position) {
        return OperatorFragment.PARENT_VIEW_TYPE;
    }

    @Override
    public RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_operator, parent, false);
        ViewHolder holder = new ViewHolder(view, mRecycledViewPool);
        if (mBizType.equals("0")){
            holder.imgType.setVisibility(View.GONE);
        }else{
            holder.imgType.setVisibility(View.VISIBLE);
        }
        view.setOnClickListener(v -> holder.checkBox.performClick());
        view.findViewById(R.id.flMore).setOnClickListener(v -> {
            int adapterPosition = holder.getAdapterPosition();
            if (adapterPosition != RecyclerView.NO_POSITION && mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(view, getItem(adapterPosition), adapterPosition);
            }
        });
        return holder;
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        AppCompanyBanDanWrapper appCompanyBanDanWrapper = getItem(position);
        viewHolder.tvOrderNo.setText(appCompanyBanDanWrapper.getmBlNo());
        viewHolder.tvShipName.setText(appCompanyBanDanWrapper.getVessel() + "/" + appCompanyBanDanWrapper.getVoyage());// 船名 / 航次
        viewHolder.tvCompany.setText(appCompanyBanDanWrapper.getShipCompany()); // 船公司
        viewHolder.tvType.setText(appCompanyBanDanWrapper.getContId());

        // 获取当前最新数据，防止数据错乱
        List<AppCompanyBanDanSonoWrapper> childInfos = appCompanyBanDanWrapper.getSonoList();

        // 先判断一下是不是已经设置了Adapter
        if (viewHolder.rlOrderNo.getAdapter() == null) {
            OperatorItemAdapter operatorItemAdapter = new OperatorItemAdapter(mContext, mBizType,(isChecked, isCheckedsCallBack, positionParent,mViewHolderParent) -> {
                // 设置这个标签是为了告诉是因为子checkBox影响的当前父checkBox，所以不需要这个父checkBox影响到子checkBox，否则会出现这种场合bug:子checkBox取消选择后，导致父checkBox也设置成不选，如果不加这个tag做一些措施，会因为这个父checkBox不选后，里面的所有子checkBox都变成不选的状态
                if (mViewHolderParent.checkBox.isChecked() != isChecked){
                    mViewHolderParent.checkBox.setTag("ChildAction"); // 不同的值才起效
                }
                mViewHolderParent.checkBox.setChecked(isChecked); // google的setOnCheckedChangeListener 也是不同值才起效的
                mIsCheckedChilds.set(positionParent, isCheckedsCallBack);
            });
            operatorItemAdapter.setData(childInfos);
            operatorItemAdapter.setCheckeds(mIsCheckedChilds.get(position));
            operatorItemAdapter.setPositionParent(position);
            operatorItemAdapter.setmViewHolderParent(viewHolder);
            viewHolder.rlOrderNo.setAdapter(operatorItemAdapter);
        } else {
            ((OperatorItemAdapter) viewHolder.rlOrderNo.getAdapter()).setData(childInfos);
            ((OperatorItemAdapter) viewHolder.rlOrderNo.getAdapter()).setCheckeds(mIsCheckedChilds.get(position));
            ((OperatorItemAdapter) viewHolder.rlOrderNo.getAdapter()).setPositionParent(position);
            ((OperatorItemAdapter) viewHolder.rlOrderNo.getAdapter()).setmViewHolderParent(viewHolder);
            viewHolder.rlOrderNo.getAdapter().notifyDataSetChanged();
        }

        // 装拆箱
        if (appCompanyBanDanWrapper.getOtherservice().equals("0")){
            viewHolder.imgType.setImageResource(R.drawable.ic_z_11);
        }else{
            viewHolder.imgType.setImageResource(R.drawable.ic_x_11);
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
                    mCallBack.isCheckBoxAll(true);
                } else {
                    mCallBack.isCheckBoxAll(false);
                }
            } else {
                // 回调
                mCallBack.isCheckBoxAll(false);
            }

            // 设置子控件随着改变而改变
            if (!buttonView.getTag().equals("ChildAction")) {
                ((OperatorItemAdapter) viewHolder.rlOrderNo.getAdapter()).setChecked(isChecked);
                viewHolder.rlOrderNo.getAdapter().notifyDataSetChanged();
            }
            // 重置tag
            buttonView.setTag("");

        });

    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.checkBox)
        CheckBox checkBox;
        @BindView(R.id.imgCheck)
        ImageView imgCheck;
        @BindView(R.id.tvOrderNoTitle)
        TextView tvOrderNoTitle;
        @BindView(R.id.tvOrderNo)
        TextView tvOrderNo;
        @BindView(R.id.flType)
        FrameLayout flType;
        @BindView(R.id.tvShipName)
        TextView tvShipName;
        @BindView(R.id.vLineShipName)
        View vLineShipName;
        @BindView(R.id.tvCompany)
        TextView tvCompany;
        @BindView(R.id.flMore)
        FrameLayout flMore;
        @BindView(R.id.vLineBottom)
        View vLineBottom;
        @BindView(R.id.rlOrderNo)
        RecyclerView rlOrderNo;
        @BindView(R.id.tvType)
        TextView tvType;
        @BindView(R.id.imgType)
        ImageView imgType;

        ViewHolder(View view, RecyclerView.RecycledViewPool recycledViewPool) {
            super(view);
            ButterKnife.bind(this, view);

            LinearLayoutManagerAutoMeasure manager = new LinearLayoutManagerAutoMeasure(itemView.getContext());
            // 需要注意：要使用RecycledViewPool的话,如果使用的LayoutManager是LinearLayoutManager或其子类（如GridLayoutManager），需要手动开启这个特性
            manager.setRecycleChildrenOnDetach(true);
            // 嵌套的子RecyclerView,需要将LinearLayoutManager设置setAutoMeasureEnabled(true)成自适应高度
//            manager.setAutoMeasureEnabled(true);
            // 子RecyclerView没必要滚动本身
            rlOrderNo.setNestedScrollingEnabled(false);
            rlOrderNo.setLayoutManager(manager);
            // 子RecyclerView现在和父RecyclerView在同一个共享池了
            rlOrderNo.setRecycledViewPool(recycledViewPool);
        }

    }
}
