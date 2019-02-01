package com.yaoguang.appcommon.phone.home.message.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yaoguang.appcommon.R;
import com.yaoguang.appcommon.phone.home.message.eventbus.child.IsDeleteEvent;
import com.yaoguang.lib.adapter.BaseLoadMoreRecyclerAdapter;
import com.yaoguang.lib.common.debounceclick.NoDoubleClickListener;
import com.yaoguang.greendao.entity.common.SysMsg;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhongjh on 2017/7/19.
 */
public class MessageChildAdapter extends BaseLoadMoreRecyclerAdapter<SysMsg, RecyclerView.ViewHolder> {

    private int mType;

    /**
     * @param type 判断类型,0平台公告 1企业消息
     */
    public MessageChildAdapter(int type) {
        mType = type;
    }

    public List<Boolean> checks = new ArrayList<>();

    //确认是否是单选模式
    private boolean isCheck;

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    @Override
    public void appendToList(List<SysMsg> list) {
        for (SysMsg sysMsg : list) {
            checks.add(false);
        }
        super.appendToList(list);
    }

    @Override
    public void clear() {
        checks.clear();
        super.clear();
    }

    /**
     * 选择所有单选框
     */
    public void selectAll() {
        for (int i = 0; i <= checks.size() - 1; i++) {
            checks.set(i, true);
        }

    }

    /**
     * 清除所有单选框
     */
    public void clearAll() {
        for (int i = 0; i <= checks.size() - 1; i++) {
            checks.set(i, false);
        }
    }

    private boolean isItemChecked(int position) {
        return checks.get(position);
    }

    private void checkListener(int position, ItemViewHolder itemViewHolder) {
        if (isItemChecked(position)) {
            checks.set(position, false);
            itemViewHolder.imgIsCheck.setImageResource(R.drawable.ic_weixuanze);
        } else {
            checks.set(position, true);
            itemViewHolder.imgIsCheck.setImageResource(R.drawable.ic_yixuanze);
        }
        //通知检测是否删除可亮
        EventBus.getDefault().post(new IsDeleteEvent(true, mType));
    }

    @Override
    public RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        SysMsg sysMsg = getList().get(position);

        //根据模式是否显示单选框
        if (isCheck) {
            itemViewHolder.imgIsCheck.setVisibility(View.VISIBLE);
        } else {
            itemViewHolder.imgIsCheck.setVisibility(View.GONE);
        }
        //根据模式是否点击选择还是点击详情
        if (isCheck) {
            itemViewHolder.imgIsCheck.setOnClickListener(new NoDoubleClickListener() {
                @Override
                public void onNoDoubleClick(View v) {
                    checkListener(position, itemViewHolder);
                }
            });
            itemViewHolder.rootView.setOnClickListener(new NoDoubleClickListener() {
                @Override
                public void onNoDoubleClick(View v) {
                    checkListener(position, itemViewHolder);
                }
            });
        } else {
            itemViewHolder.rootView.setOnClickListener(new NoDoubleClickListener() {
                @Override
                public void onNoDoubleClick(View v) {
                    mOnItemClickListener.onItemClick(v, getList().get(position), position);
                }
            });
        }

        itemViewHolder.tvDate.setText(sysMsg.getCreated());
        itemViewHolder.tvTitle.setText(sysMsg.getTitle());
        itemViewHolder.tvContent.setText(sysMsg.getContent());

        if (isItemChecked(position)) {
            itemViewHolder.imgIsCheck.setImageResource(R.drawable.ic_yixuanze);
        } else {
            itemViewHolder.imgIsCheck.setImageResource(R.drawable.ic_weixuanze);
        }

        if (sysMsg.getFlag().equals("0")) {
            itemViewHolder.imgUnread.setVisibility(View.VISIBLE);
        } else {
            itemViewHolder.imgUnread.setVisibility(View.GONE);
        }
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        public View rootView;
        public ImageView imgIsCheck;
        public TextView tvDate;
        public TextView tvTitle;
        public TextView tvContent;
        public ImageView imgUnread;

        public ItemViewHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.imgIsCheck = (ImageView) rootView.findViewById(R.id.imgIsCheck);
            this.tvDate = (TextView) rootView.findViewById(R.id.tvDate);
            this.tvTitle = (TextView) rootView.findViewById(R.id.tvTitle);
            this.tvContent = (TextView) rootView.findViewById(R.id.tvContent);
            this.imgUnread = (ImageView) rootView.findViewById(R.id.imgUnread);
        }

    }
}
