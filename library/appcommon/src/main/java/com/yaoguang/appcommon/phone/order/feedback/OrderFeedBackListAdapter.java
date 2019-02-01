package com.yaoguang.appcommon.phone.order.feedback;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yaoguang.appcommon.R;
import com.yaoguang.appcommon.phone.order.adapter.OrderFeedImagesBaseAdapter;
import com.yaoguang.lib.adapter.BaseLoadMoreRecyclerAdapter;
import com.yaoguang.lib.common.debounceclick.NoDoubleClickListener;
import com.yaoguang.lib.appcommon.widget.CustomGridView;
import com.yaoguang.greendao.entity.driver.DriverOrderNodeFeedback;
import com.yaoguang.greendao.entity.MediaBean;
import com.yaoguang.lib.common.file.MediaUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * 订单反馈的节点
 * Created by 韦理英 on 2017/4/21.
 *
 * update zhongjh
 * data 2018-03-25
 */
public class OrderFeedBackListAdapter extends BaseLoadMoreRecyclerAdapter<DriverOrderNodeFeedback, RecyclerView.ViewHolder> {

    private final int type;
    private Context mContext;
    public OrderFeedBackListAdapter(int type) {
        this.type = type;
    }

    static final String ORDER_FEEDBACK_IMAGES = "images";
    private static final String ORDER_FEEDBACK_VIDEO = "video";

    private String getTitle(int position) {
        int tmp = position + 1;
        switch (tmp) {
            case 1:
                return "反馈一";
            case 2:
                return "反馈二";
            case 3:
                return "反馈三";
            case 4:
                return "反馈四";
            case 5:
                return "反馈五";
            case 6:
                return "反馈六";
            case 7:
                return "反馈七";
            case 8:
                return "反馈八";
            case 9:
                return "反馈九";
            case 10:
                return "反馈十";
        }
        return null;
    }

    @Override
    public RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.item_order_feedback, null);
        mContext = parent.getContext();
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        final DriverOrderNodeFeedback driverOrderNodeFeedback = getList().get(position);

        itemViewHolder.flLineSecond.setVisibility(View.GONE);
        itemViewHolder.etContNoFirst.setEnabled(false);
        itemViewHolder.etSealNoFirst.setEnabled(false);
        itemViewHolder.etContNoSecond.setEnabled(false);
        itemViewHolder.etSealNoSecond.setEnabled(false);

        if (type == 0) {  // 查看反馈
            itemViewHolder.tvTitle.setText(getTitle(position));
            itemViewHolder.llContSeal.setVisibility(View.GONE);
        } else {  // 装卸货动态
            itemViewHolder.tvTitle.setText("详情描述");
            itemViewHolder.llContSeal.setVisibility(View.VISIBLE);
            // 柜号一
            if (!TextUtils.isEmpty(driverOrderNodeFeedback.getContNoFirst())) {
                itemViewHolder.etContNoFirst.setText(driverOrderNodeFeedback.getContNoFirst());

                // 封号一
                itemViewHolder.etSealNoFirst.setText("无");
            } else {
                itemViewHolder.rlContNoFirst.setVisibility(View.GONE);

                itemViewHolder.rlSealNoFirst.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(driverOrderNodeFeedback.getSealNoFirst())) {
                // 封号一
                itemViewHolder.etSealNoFirst.setText(driverOrderNodeFeedback.getSealNoFirst());
            }

            // 柜号二
            if (!TextUtils.isEmpty(driverOrderNodeFeedback.getContNoSecond())) {
                itemViewHolder.etContNoSecond.setText(driverOrderNodeFeedback.getContNoSecond());

                itemViewHolder.rlContNoSecond.setVisibility(View.VISIBLE);
                itemViewHolder.flSealNoFirst.setVisibility(View.VISIBLE);

                // 封号二
                itemViewHolder.etSealNoSecond.setText("无");
                itemViewHolder.llSecondNo2.setVisibility(View.VISIBLE);
                itemViewHolder.rlSealNoSecond.setVisibility(View.VISIBLE);
            } else {
                itemViewHolder.rlContNoSecond.setVisibility(View.GONE);
                itemViewHolder.flSealNoFirst.setVisibility(View.GONE);

                itemViewHolder.llSecondNo2.setVisibility(View.GONE);
                itemViewHolder.rlSealNoSecond.setVisibility(View.GONE);
            }

            if (!TextUtils.isEmpty(driverOrderNodeFeedback.getSealNoSecond())) {
                itemViewHolder.etSealNoSecond.setText(driverOrderNodeFeedback.getSealNoSecond());
            }
        }
        if (!TextUtils.isEmpty(driverOrderNodeFeedback.getRemark())) {
            itemViewHolder.tvRemark.setText(driverOrderNodeFeedback.getRemark());
            itemViewHolder.tvRemark.setVisibility(View.VISIBLE);
        } else {
            itemViewHolder.tvRemark.setVisibility(View.GONE);
        }
        // 音频
        if (!TextUtils.isEmpty(driverOrderNodeFeedback.getAudioUrl()) && driverOrderNodeFeedback.getAudioUrl().startsWith("http")) {
            itemViewHolder.ivAudio.setVisibility(View.VISIBLE);
            itemViewHolder.ivAudio.setOnClickListener(new NoDoubleClickListener() {
                @Override
                public void onNoDoubleClick(View v) {
                    openAudio(driverOrderNodeFeedback);
                }
            });
        } else {
            itemViewHolder.ivAudio.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(driverOrderNodeFeedback.getAddress())) {
            itemViewHolder.tvAddress.setText(driverOrderNodeFeedback.getAddress());
            itemViewHolder.tvAddress.setVisibility(View.VISIBLE);
        } else {
            itemViewHolder.tvAddress.setVisibility(View.GONE);
        }
        itemViewHolder.tvTime.setText(driverOrderNodeFeedback.getCreated());

        List<MediaBean> list = new ArrayList<>();
        if (!TextUtils.isEmpty(driverOrderNodeFeedback.getPicture())) {
            String[] images = driverOrderNodeFeedback.getPicture().split(",");
            if (!Arrays.asList(images).isEmpty()) {
                for (String s : Arrays.asList(images)) {
                    MediaBean bean = new MediaBean();
                    bean.setType(0);
                    bean.setUrl(s);
                    list.add(bean);
                }
            }
        }
        // 视频
        String video;
        if (!TextUtils.isEmpty(driverOrderNodeFeedback.getVideoUrl()) && driverOrderNodeFeedback.getVideoUrl().startsWith("http") && driverOrderNodeFeedback.getVideo() != null) {
            video = driverOrderNodeFeedback.getVideoUrl();
            MediaBean bean = new MediaBean();
            bean.setType(1);
            bean.setUrl(video);
            bean.setVideo(driverOrderNodeFeedback.getVideo());
            list.add(bean);
        }

        itemViewHolder.gvGridView.setAdapter(new OrderFeedImagesBaseAdapter(list));
    }

    private void openAudio(DriverOrderNodeFeedback driverOrderNodeFeedback) {
        MediaUtils.openAudio(mContext,driverOrderNodeFeedback.getAudioUrl());
    }



    protected class ItemViewHolder extends RecyclerView.ViewHolder {
        FrameLayout flLineSecond;
        FrameLayout flSealNoFirst;

        TextView tvTitle;
        TextView tvAddress;
        TextView tvTime;
        TextView tvRemark;

        LinearLayout llContSeal;
        LinearLayout rlContNoFirst;
        LinearLayout rlSealNoFirst;;
        LinearLayout rlContNoSecond;
        LinearLayout rlSealNoSecond;
        LinearLayout llSecondNo2;
        LinearLayout llTitle;

        EditText etContNoFirst;
        EditText etSealNoFirst;
        EditText etContNoSecond;
        EditText etSealNoSecond;

        CustomGridView gvGridView;
        ImageView ivAudio;

        ItemViewHolder(View view) {
            super(view);
            flLineSecond = (FrameLayout) view.findViewById(R.id.flLineSecond);
            flSealNoFirst = (FrameLayout) view.findViewById(R.id.flSealNoFirst);
            tvAddress = (TextView) view.findViewById(R.id.tvAddress);
            tvRemark = (TextView) view.findViewById(R.id.tvRemark);
            tvTitle = (TextView) view.findViewById(R.id.tvTitle);
            tvTime = (TextView) view.findViewById(R.id.tvTime);
            llContSeal = (LinearLayout) view.findViewById(R.id.llContSeal);
            rlContNoFirst = (LinearLayout) view.findViewById(R.id.rlContNoFirst);
            rlSealNoFirst = (LinearLayout) view.findViewById(R.id.rlSealNoFirst);
            rlContNoSecond = (LinearLayout) view.findViewById(R.id.rlContNoSecond);
            rlSealNoSecond = (LinearLayout) view.findViewById(R.id.rlSealNoSecond);
            llSecondNo2 = (LinearLayout) view.findViewById(R.id.llSecondNo2);
            llTitle = (LinearLayout) view.findViewById(R.id.llTitle);

            etContNoFirst = (EditText) view.findViewById(R.id.etContNoFirst);
            etSealNoFirst = (EditText) view.findViewById(R.id.etSealNoFirst);
            etContNoSecond = (EditText) view.findViewById(R.id.etContNoSecond);
            etSealNoSecond = (EditText) view.findViewById(R.id.etSealNoSecond);

            gvGridView = (CustomGridView) view.findViewById(R.id.gvGridView);
            ivAudio = (ImageView) view.findViewById(R.id.ivAudio);
        }
    }

}
