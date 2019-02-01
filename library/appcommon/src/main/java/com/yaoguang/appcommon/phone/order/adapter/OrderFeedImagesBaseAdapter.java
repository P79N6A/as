package com.yaoguang.appcommon.phone.order.adapter;

import android.app.Activity;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yaoguang.appcommon.R;
import com.yaoguang.appcommon.common.base.LookPhotoActivity;
import com.yaoguang.lib.common.file.MediaUtils;
import com.yaoguang.lib.common.imagepicker.GlideManager;
import com.yaoguang.lib.common.debounceclick.NoDoubleClickListener;
import com.yaoguang.greendao.entity.MediaBean;

import java.util.List;

/**
 * Created by 韦理英
 * on 2017/7/5 0005.
 */

public class OrderFeedImagesBaseAdapter extends BaseAdapter {
    final List<MediaBean> list;
    private Handler handler = new Handler();

    public OrderFeedImagesBaseAdapter(List<MediaBean> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.view_imageview, null);

            viewHolder = new ViewHolder(convertView);
            viewHolder.ivImage = (ImageView) convertView.findViewById(R.id.ivImage);
            viewHolder.ivImageFlag = (ImageView) convertView.findViewById(R.id.ivImageFlag);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final MediaBean bean = list.get(position);
        if (bean.getType() == 0) {
            // 图片
            GlideManager.getInstance().withSquare(parent.getContext(), bean.getUrl(), viewHolder.ivImage);
        } else if (bean.getType() == 1 && bean.getVideo() != null) {
            // 视频
            Glide.with(parent.getContext())
                    .load(bean.getVideo())
                    .into(viewHolder.ivImage);
            viewHolder.ivImageFlag.setImageResource(R.drawable.ic_bf_03);
        }

        convertView.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                if (bean.getType() == 0) {
                    // 图片
                    LookPhotoActivity.newInstance((Activity) parent.getContext(), bean.getUrl());
                } else {
                    // 视频
                    MediaUtils.openVideo(parent.getContext(), bean.getUrl());
                }
            }
        });

        return convertView;
    }

    public class ViewHolder {
        public View rootView;
        public ImageView ivImage;
        public ImageView ivImageFlag;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.ivImage = (ImageView) rootView.findViewById(R.id.ivImage);
            this.ivImageFlag = (ImageView) rootView.findViewById(R.id.ivImageFlag);
        }

    }
}
