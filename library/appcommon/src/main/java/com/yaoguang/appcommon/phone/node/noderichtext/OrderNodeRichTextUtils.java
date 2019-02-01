package com.yaoguang.appcommon.phone.node.noderichtext;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.yaoguang.appcommon.R;
import com.yaoguang.appcommon.common.finalrequest.DriverRequest;
import com.yaoguang.appcommon.multimedia.upload.MediaMenuUtils;
import com.yaoguang.datasource.driver.OrderNodeDataSource;
import com.yaoguang.greendao.entity.driver.DriverOrderNodeDetail;
import com.yaoguang.lib.appcommon.utils.TextViewUtils;
import com.yaoguang.lib.common.ObjectUtils;
import com.yaoguang.lib.common.file.UriUtils;
import com.yaoguang.lib.common.imagepicker.GlideManager;
import com.yaoguang.lib.appcommon.utils.EditTextUtils;
import com.yaoguang.lib.base.BaseApplication;
import com.yaoguang.lib.common.AppClickUtil;
import com.yaoguang.lib.common.ULog;
import com.yaoguang.lib.common.file.VideoUrl;
import com.yaoguang.lib.common.debounceclick.NoDoubleClickListener;
import com.yaoguang.lib.net.BaseObserver;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.lib.permissions.impl.PermissionsManager;
import com.yaoguang.lib.qinui.QiNiuManager;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import me.yokeyword.fragmentation.SupportFragment;

import static me.yokeyword.fragmentation.ISupportFragment.RESULT_OK;

/**
 * 富文本的工具
 * Created by zhongjh on 2018/5/4.
 */
public class OrderNodeRichTextUtils {

    private int mPosition; // 第几个，因为同个界面会有多个货柜，区分开来是不让返回事件一起触发两个

    public static final int NODEOne = DriverRequest.REQUESTCODE_NODE_UPLOAD + 0; // 选择上传节点图片1
    public static final int NODETwo = DriverRequest.REQUESTCODE_NODE_UPLOAD + 1; // 选择上传节点图片2
    public static final int NODEThree = DriverRequest.REQUESTCODE_NODE_UPLOAD + 2; // 选择上传节点图片3
    public static final int NODEVIDEO = DriverRequest.REQUESTCODE_NODE_UPLOAD + 3; // 选择上传节点视频1
    public static final int NODEAUDIO = DriverRequest.REQUESTCODE_NODE_UPLOAD + 4; // 选择上传节点语音1

    // 需要用到的类
    private OrderNodeRichTextFragement.ViewHolder mViewHolder;
    private Activity mActivity;
    private SupportFragment mFragment;
    private Context mContext;
    private DriverOrderNodeDetail mDriverOrderNodeDetail;// 数据源
    CompositeDisposable mCompositeDisposable;
    OrderNodeDataSource mOrderNodeDataSource;
    String mNodeId;

    /**
     * 图片的集合
     */
    protected SparseArray<String> arrayListImageUrl = new SparseArray<>();
    private String nodeOneUrl;
    private String nodeTwoUrl;
    private String nodeThreeUrl;

    // 语音url
    private String videoUrl;
    // 视频url
    private String audioUrl;

    // 是否显示本地选择
    private boolean showLocalChoice = true;

    protected int select;// 当前选择的上传


    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    private int maxLength = 100; // 文字最大值


    public void setOnListener(OnListener onListener) {
        this.mOnListener = onListener;
    }

    private OnListener mOnListener;

    public interface OnListener {
        void OK();
    }

    /**
     * 构造
     */
    public OrderNodeRichTextUtils(OrderNodeRichTextFragement.ViewHolder viewHolder, Activity activity, SupportFragment fragment,
                                  Context context, OrderNodeDataSource orderNodeDataSource, CompositeDisposable compositeDisposable, String nodeId, int position) {
        mViewHolder = viewHolder;
        mActivity = activity;
        mFragment = fragment;
        mContext = context;
        mOrderNodeDataSource = orderNodeDataSource;
        mCompositeDisposable = compositeDisposable;
        mNodeId = nodeId;
        mPosition = position;

        //柜号只能输入数字和字母
        TextViewUtils.setAlphaNumeric(viewHolder.etContNo);
        // 自动小写转大写
        viewHolder.etContNo.setTransformationMethod(TextViewUtils.replacementTransformationMethod);
        //封号只能输入数字和字母
        TextViewUtils.setAlphaNumeric(viewHolder.etSealNo);
    }

    /**
     * 初始化数据源
     *
     * @param driverOrderNodeDetail
     */
    public void initData(DriverOrderNodeDetail driverOrderNodeDetail) {
        mDriverOrderNodeDetail = driverOrderNodeDetail;

        //柜号封号
        mViewHolder.etContNo.setText(driverOrderNodeDetail.getContNo());
        mViewHolder.etSealNo.setText(driverOrderNodeDetail.getSealNo());

        if (!TextUtils.isEmpty(driverOrderNodeDetail.getContNo())) {
            mViewHolder.etContNo.setEnabled(false);
        }else{
            mViewHolder.etContNo.setEnabled(true);
        }

        if (!TextUtils.isEmpty(driverOrderNodeDetail.getSealNo())) {
            mViewHolder.etSealNo.setEnabled(false);
        }else{
            mViewHolder.etSealNo.setEnabled(true);
        }

        // 备注
        mViewHolder.etRemark.setText(driverOrderNodeDetail.getRemark());
        // 当前备注多少文字
        mViewHolder.tvNumber.setText(ObjectUtils.parseString(driverOrderNodeDetail.getRemark().length()));
        mViewHolder.tvNumberSum.setText(ObjectUtils.parseString(maxLength));
        // 视频
        if (!TextUtils.isEmpty(driverOrderNodeDetail.getVideoUrl())) {
            Bitmap mBitmapVideo = VideoUrl.createVideoThumbnail(driverOrderNodeDetail.getVideoUrl(), MediaStore.Images.Thumbnails.MICRO_KIND);
            if (mBitmapVideo != null) {
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                mBitmapVideo.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] bytes = stream.toByteArray();
                Glide.with(mContext)
                        .load(bytes)
                        .into(mViewHolder.ivVideo);
                videoUrl = driverOrderNodeDetail.getVideoUrl();
                if (mViewHolder.ivVideoFlag != null) {
                    mViewHolder.ivVideoFlag.setImageResource(R.drawable.ic_del_02);
                }
                mViewHolder.ivVideoPlayFlag.setVisibility(View.VISIBLE);
            }
        }

        if (!TextUtils.isEmpty(driverOrderNodeDetail.getAudioUrl())) {
            audioUrl = driverOrderNodeDetail.getAudioUrl();
            mViewHolder.ibAudio.setBackgroundResource(R.drawable.ic_bfyybg);

            if (mViewHolder.ivAudioFlag != null) {
                mViewHolder.ivAudioFlag.setImageResource(R.drawable.ic_del_02);
            }
            mViewHolder.ivAudioPlayPlag.setVisibility(View.VISIBLE);
        }

        // 判断多个图片的
        if (!TextUtils.isEmpty(driverOrderNodeDetail.getPicture())) {
            String[] pictures = driverOrderNodeDetail.getPicture().split(",");
            for (int i = 0; i < pictures.length; i++) {
                switch (i) {
                    case 0:
                        if (arrayListImageUrl.get(0) != null)
                            arrayListImageUrl.remove(0);
                        arrayListImageUrl.put(0, pictures[0]);
                        GlideManager.getInstance().withCanCancle(mContext, pictures[0], mViewHolder.ivPhotographOne, R.drawable.ic_loading_tp);
                        if (mViewHolder.tvAlertUploadOne != null) {
                            mViewHolder.tvAlertUploadOne.setPadding(0, 0, 0, 0);
                            mViewHolder.tvAlertUploadOne.setImageResource(R.drawable.ic_del_02);
                        }
                        break;
                    case 1:
                        if (arrayListImageUrl.get(1) != null)
                            arrayListImageUrl.remove(1);
                        arrayListImageUrl.put(1, pictures[1]);
                        GlideManager.getInstance().withCanCancle(mContext, pictures[1], mViewHolder.ivPhotographTwo, R.drawable.ic_loading_tp);
                        if (mViewHolder.tvAlertUploadTwo != null) {
                            mViewHolder.tvAlertUploadTwo.setPadding(0, 0, 0, 0);
                            mViewHolder.tvAlertUploadTwo.setImageResource(R.drawable.ic_del_02);
                        }
                        mViewHolder.flCamera2.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        if (arrayListImageUrl.get(2) != null)
                            arrayListImageUrl.remove(2);
                        arrayListImageUrl.put(2, pictures[2]);
                        GlideManager.getInstance().withCanCancle(mContext, pictures[2], mViewHolder.ivPhotographThree, R.drawable.ic_loading_tp);
                        if (mViewHolder.tvAlertUploadThree != null) {
                            mViewHolder.tvAlertUploadThree.setPadding(0, 0, 0, 0);
                            mViewHolder.tvAlertUploadThree.setImageResource(R.drawable.ic_del_02);
                        }
                        mViewHolder.flCamera3.setVisibility(View.VISIBLE);
                        break;
                }
            }
        }


    }

    public void initViewHolderListener() {
        // 删除图片1
        mViewHolder.tvAlertUploadOne.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                if (AppClickUtil.isDuplicateClick()) return;

                if (arrayListImageUrl.size() > 0 && arrayListImageUrl.get(0) != null) {
                    mViewHolder.tvAlertUploadOne.setImageResource(0);
                    arrayListImageUrl.remove(0);
                }

                mViewHolder.ivPhotographOne.setImageResource(0);
                mViewHolder.tvAlertUploadOne.setImageResource(0);
                check();
            }
        });
        // 删除图片2
        mViewHolder.tvAlertUploadTwo.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                if (AppClickUtil.isDuplicateClick()) return;

                mViewHolder.ivPhotographTwo.setImageResource(0);
                mViewHolder.tvAlertUploadTwo.setImageResource(0);
                arrayListImageUrl.remove(1);
                check();
            }
        });
        // 删除图片3
        mViewHolder.tvAlertUploadThree.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                if (AppClickUtil.isDuplicateClick()) return;

                mViewHolder.ivPhotographThree.setImageResource(0);
                mViewHolder.tvAlertUploadThree.setImageResource(0);
                arrayListImageUrl.remove(2);
                check();
            }
        });

        // 选择图片1
        mViewHolder.ivPhotographOne.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                if (AppClickUtil.isDuplicateClick()) return;

                select = NODEOne + mPosition;
                open(mFragment, mViewHolder.flMain, MediaMenuUtils.MenuType.PHOTO, arrayListImageUrl.get(0), select);
            }
        });
        // 选择图片2
        mViewHolder.ivPhotographTwo.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                if (AppClickUtil.isDuplicateClick()) return;

                select = NODETwo + mPosition;
                open(mFragment, mViewHolder.flMain, MediaMenuUtils.MenuType.PHOTO, arrayListImageUrl.get(1), select);
            }
        });
        // 选择图片3
        mViewHolder.ivPhotographThree.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                if (AppClickUtil.isDuplicateClick()) return;

                select = NODEThree + mPosition;
                open(mFragment, mViewHolder.flMain, MediaMenuUtils.MenuType.PHOTO, arrayListImageUrl.get(2), select);
            }
        });

        // 上传视频
        mViewHolder.ivVideo.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                if (AppClickUtil.isDuplicateClick()) return;

                if (PermissionsManager.getInstance().checkCameraPermissions(mActivity, true) && PermissionsManager.getInstance().checkWriteExternalPermission(mActivity, true)) {
                    select = NODEVIDEO + mPosition;
                    MediaMenuUtils.open(mFragment, MediaMenuUtils.MenuType.VIDEO,
                            mViewHolder.flMain, select, videoUrl, showLocalChoice, videoFile -> videoUrl = videoFile);
                }
            }
        });

        // 删除视频
        mViewHolder.ivVideoFlag.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                if (AppClickUtil.isDuplicateClick()) return;

                mViewHolder.ivVideo.setImageResource(0);
                mViewHolder.ivVideoFlag.setImageResource(0);
                videoUrl = null;
                check();
            }
        });

        // 上传语音
        mViewHolder.ibAudio.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                if (AppClickUtil.isDuplicateClick()) return;

                if (PermissionsManager.getInstance().checkRecordAudioPermission(mActivity, true) && PermissionsManager.getInstance().checkWriteExternalPermission(mActivity, true)) {
                    select = NODEAUDIO + mPosition;
                    MediaMenuUtils.open(mFragment, MediaMenuUtils.MenuType.AUDIO,
                            mViewHolder.flMain, select, audioUrl, showLocalChoice, videoFile -> {

                            });
                }
            }
        });
        // 删除录音
        mViewHolder.ivAudioFlag.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                if (AppClickUtil.isDuplicateClick()) return;

                mViewHolder.ibAudio.setImageResource(0);
                mViewHolder.ivAudioFlag.setImageResource(0);
                audioUrl = null;
                check();
            }
        });
        EditTextUtils.setRemark(mViewHolder.etRemark, mViewHolder.tvNumber, maxLength);


        // 提交内容
        mViewHolder.cpbSubmit.setOnClickListener(v -> {
            if (mDriverOrderNodeDetail == null)
                mDriverOrderNodeDetail = new DriverOrderNodeDetail();

            mDriverOrderNodeDetail.setNodeId(mNodeId);
            // 图片
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < arrayListImageUrl.size(); i++) {
                stringBuilder.append(arrayListImageUrl.get(i));
                // 最后一个不加 逗号
                if (i != arrayListImageUrl.size() - 1) {
                    stringBuilder.append(",");
                }
            }
            mDriverOrderNodeDetail.setPicture(stringBuilder.toString());
            // 其他杂项
            mDriverOrderNodeDetail.setAudioUrl(audioUrl);
            mDriverOrderNodeDetail.setVideoUrl(videoUrl);
            mDriverOrderNodeDetail.setContNo(mViewHolder.etContNo.getText().toString());
            mDriverOrderNodeDetail.setSealNo(mViewHolder.etSealNo.getText().toString());
            mDriverOrderNodeDetail.setRemark(mViewHolder.etRemark.getText().toString());

            // 提交数据源
            mOrderNodeDataSource.detailubmit(mDriverOrderNodeDetail)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new BaseObserver<BaseResponse<String>>(mCompositeDisposable) {
                        @Override
                        public void onSuccess(BaseResponse<String> response) {
                            Toast.makeText(BaseApplication.getInstance(), response.getResult(), Toast.LENGTH_LONG).show();
                            mFragment.setFragmentResult(RESULT_OK, new Bundle());

                            if (mOnListener != null) {
                                mOnListener.OK();
                            }

                        }
                    });
        });

    }

    /**
     * fragment的处理事件
     */
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        if (requestCode == NODEAUDIO + mPosition && data != null) {
            // 上传语音
            audioUrl = data.getString("url");
            new Handler().postDelayed(() -> uploadFile(audioUrl), 1000);
        }
    }

    /**
     * 上传文件
     */
    private void uploadFile(String file) {
        ULog.i("上传文件地址：" + file);
        QiNiuManager.getInstance().upload(mActivity, mContext, file, true, new QiNiuManager.ComeBack() {
            @Override
            public void result(boolean result, String url) {
                if (result) {
                    ULog.i("上传成功：" + url);
                    refreshPhotograph(url);
                } else {
                    ULog.i("上传失败：");
                    Toast.makeText(BaseApplication.getInstance(), "上传失败", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void progress(String speed, int progress) {

            }
        });
    }

    /**
     * activity的处理事件
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null) {
                ArrayList<ImageItem> images = data.getParcelableArrayListExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if (images == null || images.size() <= 0) {
                    return;
                }

                if (requestCode == NODEOne + mPosition) {
                    nodeOneUrl = images.get(0).path;
                    uploadFile(nodeOneUrl);
                } else if (requestCode == NODETwo + mPosition) {
                    nodeTwoUrl = images.get(0).path;
                    uploadFile(nodeTwoUrl);
                } else if (requestCode == NODEThree + mPosition) {
                    nodeThreeUrl = images.get(0).path;
                    uploadFile(nodeThreeUrl);
                }
            }
        } else if (requestCode == NODEVIDEO + mPosition && data != null) {
            String newUri = UriUtils.getUriPath(mActivity, data.getData());
            if (newUri != null)
                // 如果转换失败，那就是拍摄的，拍摄的用回调所获取的就可以了，如果转换成功，那就是选择的
                videoUrl = newUri;
            uploadFile(videoUrl);
        }

    }

    /**
     * 检测是否有1张以上图片，如果有就去掉*
     */
    private void check() {
        // 删除检测
        int size = arrayListImageUrl.size();
        if (size == 0) {
            mViewHolder.flCamera1.setVisibility(View.VISIBLE);
            mViewHolder.flCamera2.setVisibility(View.GONE);
            mViewHolder.flCamera3.setVisibility(View.GONE);
        } else if (size == 1) {
            mViewHolder.flCamera2.setVisibility(View.VISIBLE);
            mViewHolder.flCamera3.setVisibility(View.GONE);
        } else if (size == 2) {

        }
        mViewHolder.llUpload.requestFocus();
        mViewHolder.llUpload.invalidate();

        // 检测星符号
        // 如果1有图片，就不要去星了
        if (arrayListImageUrl.get(0) != null) {
            return;
        }
        if (arrayListImageUrl.size() > 0 || videoUrl != null) {
            mViewHolder.tvAlertUploadOne.setPadding(0, 0, 0, 0);
            mViewHolder.tvAlertUploadOne.setImageResource(0);
        }
//        else {
//            mViewHolder.tvAlertUploadOne.setPadding(ConvertUtils.dp2px(5), ConvertUtils.dp2px(5), ConvertUtils.dp2px(5), ConvertUtils.dp2px(5));
//            mViewHolder.tvAlertUploadOne.setImageResource(R.drawable.ic_xinghao);
//        }
    }

    /**
     * 拍照和视频，还有录音
     */
    public void open(SupportFragment fragment, FrameLayout flMain, MediaMenuUtils.MenuType menuType, Object url, int requestCode) {
        MediaMenuUtils.open(fragment, menuType,
                flMain, requestCode, url, showLocalChoice, videoFile -> {

                });
    }

    private void refreshPhotograph(String url) {
        if (mContext == null) {
            return;
        }

        ULog.i("refreshPhotograph");
        if (select == NODEOne + mPosition) {
            ULog.i("显示图片1");
            if (arrayListImageUrl.get(0) != null)
                arrayListImageUrl.remove(0);
            arrayListImageUrl.put(0, url);
            GlideManager.getInstance().withCanCancle(mContext, url, mViewHolder.ivPhotographOne, com.yaoguang.appcommon.R.drawable.ic_loading_tp);
            if (mViewHolder.tvAlertUploadOne != null) {
                mViewHolder.tvAlertUploadOne.setPadding(0, 0, 0, 0);
                mViewHolder.tvAlertUploadOne.setImageResource(com.yaoguang.appcommon.R.drawable.ic_del_02);
            }
            mViewHolder.flCamera2.setVisibility(View.VISIBLE);
        } else if (select == NODETwo + mPosition) {
            ULog.i("显示图片2");
            if (arrayListImageUrl.get(1) != null)
                arrayListImageUrl.remove(1);
            arrayListImageUrl.put(1, url);
            GlideManager.getInstance().withCanCancle(mContext, url, mViewHolder.ivPhotographTwo, com.yaoguang.appcommon.R.drawable.ic_loading_tp);

            if (mViewHolder.tvAlertUploadTwo != null)
                mViewHolder.tvAlertUploadTwo.setImageResource(com.yaoguang.appcommon.R.drawable.ic_del_02);
            mViewHolder.flCamera3.setVisibility(View.VISIBLE);
        } else if (select == NODEThree + mPosition) {
            ULog.i("显示图片3");
            if (arrayListImageUrl.get(2) != null)
                arrayListImageUrl.remove(2);
            arrayListImageUrl.put(2, url);
            GlideManager.getInstance().withCanCancle(mContext, url, mViewHolder.ivPhotographThree, com.yaoguang.appcommon.R.drawable.ic_loading_tp);

            if (mViewHolder.tvAlertUploadThree != null)
                mViewHolder.tvAlertUploadThree.setImageResource(com.yaoguang.appcommon.R.drawable.ic_del_02);
        } else if (select == NODEVIDEO + mPosition) {
            ULog.i("显示视频");
            Bitmap mBitmapVideo = VideoUrl.createVideoThumbnail(videoUrl, MediaStore.Images.Thumbnails.MICRO_KIND);
            if (mBitmapVideo != null) {
                final ByteArrayOutputStream stream = new ByteArrayOutputStream();
                mBitmapVideo.compress(Bitmap.CompressFormat.PNG, 100, stream);
                final byte[] bytes = stream.toByteArray();
                Glide.with(mContext)
                        .load(bytes)
                        .into(mViewHolder.ivVideo);
                videoUrl = url;

                if (mViewHolder.ivVideoFlag != null) {
                    mViewHolder.ivVideoFlag.setImageResource(com.yaoguang.appcommon.R.drawable.ic_del_02);
                }
                mViewHolder.ivVideoPlayFlag.setVisibility(View.VISIBLE);
            }
        } else if (select == NODEAUDIO + mPosition) {
            ULog.i("显示录音");
            audioUrl = url;
            mViewHolder.ibAudio.setBackgroundResource(com.yaoguang.appcommon.R.drawable.ic_bfyybg);

            if (mViewHolder.ivAudioFlag != null) {
                mViewHolder.ivAudioFlag.setImageResource(com.yaoguang.appcommon.R.drawable.ic_del_02);
            }
            mViewHolder.ivAudioPlayPlag.setVisibility(View.VISIBLE);
        }

        // 如果是1就不要去星了
        if (select == 0) {
            return;
        }
        check();
    }


}
