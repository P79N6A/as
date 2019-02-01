package com.yaoguang.appcommon.multimedia.upload;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.yaoguang.appcommon.R;
import com.yaoguang.appcommon.common.finalrequest.DriverRequest;
import com.yaoguang.lib.base.BaseApplication;
import com.yaoguang.lib.common.file.UriUtils;
import com.yaoguang.lib.common.imagepicker.GlideManager;
import com.yaoguang.lib.base.BaseFragment2;
import com.yaoguang.lib.common.AppClickUtil;
import com.yaoguang.lib.common.ConvertUtils;
import com.yaoguang.lib.common.ObjectUtils;
import com.yaoguang.lib.common.ULog;
import com.yaoguang.lib.common.file.VideoUrl;
import com.yaoguang.lib.common.debounceclick.NoDoubleClickListener;
import com.yaoguang.lib.permissions.impl.PermissionsManager;
import com.yaoguang.lib.appcommon.utils.EditTextUtils;
import com.yaoguang.greendao.entity.DriverOrderNodeDetail;
import com.yaoguang.greendao.entity.DriverOrderNodeDetailWrapper;
import com.yaoguang.lib.qinui.QiNiuManager;
import com.yaoguang.map.location.impl.LocationManager;
import com.yaoguang.taobao.common.BitmapCompressUtils;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

/**
 * 节点的富文本数据提交：例如图片,视频,语音...
 * Created by zhongjh on 2017/4/28.
 */
public abstract class BaseNodeUploadFragment extends BaseFragment2 {
    public static final int NODEOne = DriverRequest.REQUESTCODE_NODE_UPLOAD + 0; // 选择上传节点图片1
    public static final int NODETwo = DriverRequest.REQUESTCODE_NODE_UPLOAD + 1; // 选择上传节点图片1
    public static final int NODEThree = DriverRequest.REQUESTCODE_NODE_UPLOAD + 2; // 选择上传节点图片1
    public static final int NODEVIDEO = DriverRequest.REQUESTCODE_NODE_UPLOAD + 3; // 选择上传节点视频1
    public static final int NODEAUDIO = DriverRequest.REQUESTCODE_NODE_UPLOAD + 4; // 选择上传节点语音1

    //  拍照1
    private View flCamera1;
    //  拍照2
    private View flCamera2;
    //  拍照3
    private View flCamera3;
    //  录像4
    public View flVideo1;
    //  录音5
    public View flAudio1;
    private TextView etRemark;
    private TextView tvNumberSum;

    private ImageView ivAudioFlag;
    private ImageView ivVideoFlag;
    private ImageView ivVideoPlayFlag;
    private ImageView ivAudioPlayPlag;
    private LinearLayout llUpload;

    private String nodeOneUrl;
    private String nodeTwoUrl;
    private String nodeThreeUrl;
    private String videoUrl;
    private String audioUrl;
    //TakePhoto
    private int mMaxLength = 100;

    //拍照
    public static final int REQUESTCODE = 1;
    //视频
    protected static final String NODESBEAN = "DriverOrderNodeDetail";
    protected SparseArray arrayListImageUrl = new SparseArray<>();
    // 选择上传的类型，用于上传后进行刷新
    protected int select;

    // 是否显示本地选择
    private boolean showLocalChoice = true;

    @Override
    public void initDataBind(View view, LayoutInflater inflater) {
        llUpload = view.findViewById(R.id.llUpload);
        flCamera1 = view.findViewById(R.id.flCamera1);
        flCamera2 = view.findViewById(R.id.flCamera2);
        flCamera3 = view.findViewById(R.id.flCamera3);
        flVideo1 = view.findViewById(R.id.flVideo1);
        flAudio1 = view.findViewById(R.id.flAudio1);
        ivAudioFlag = view.findViewById(R.id.ivAudioFlag);
        ivVideoFlag = view.findViewById(R.id.ivVideoFlag);
        ivVideoPlayFlag = view.findViewById(R.id.ivVideoPlayFlag);
        ivAudioPlayPlag = view.findViewById(R.id.ivAudioPlayPlag);
        tvNumberSum = view.findViewById(R.id.tvNumberSum);
    }

    @Override
    public void initListener() {
        // 删除图片1
        getImageFlagOne().setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                if (AppClickUtil.isDuplicateClick()) return;

                if (arrayListImageUrl.size() > 0 && arrayListImageUrl.get(0) != null) {
                    getImageFlagOne().setImageResource(0);
                    arrayListImageUrl.remove(0);
                }

                getIvPhotographOne().setImageResource(0);
                getImageFlagOne().setImageResource(0);
                check();
            }
        });
        // 删除图片2
        getImageFlagTwo().setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                if (AppClickUtil.isDuplicateClick()) return;

                getIvPhotographTwo().setImageResource(0);
                getImageFlagTwo().setImageResource(0);
                arrayListImageUrl.remove(1);
                check();
            }
        });
        // 删除图片3
        getImageFlagThree().setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                if (AppClickUtil.isDuplicateClick()) return;

                getIvPhotographThree().setImageResource(0);
                getImageFlagThree().setImageResource(0);
                arrayListImageUrl.remove(2);
                check();
            }
        });


        // 选择图片1
        getIvPhotographOne().setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                if (AppClickUtil.isDuplicateClick()) return;

                select = NODEOne;
                open(MediaMenuUtils.MenuType.PHOTO, arrayListImageUrl.get(0), select);
            }
        });
        // 选择图片2
        getIvPhotographTwo().setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                if (AppClickUtil.isDuplicateClick()) return;

                select = NODETwo;
                open(MediaMenuUtils.MenuType.PHOTO, arrayListImageUrl.get(1), select);
            }
        });
        // 选择图片3
        getIvPhotographThree().setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                if (AppClickUtil.isDuplicateClick()) return;

                select = NODEThree;
                open(MediaMenuUtils.MenuType.PHOTO, arrayListImageUrl.get(2), select);
            }
        });
        // 上传视频
        getIvVideo().setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                if (AppClickUtil.isDuplicateClick()) return;

                if (PermissionsManager.getInstance().checkCameraPermissions(getActivity(), true) && PermissionsManager.getInstance().checkWriteExternalPermission(getActivity(), true)) {
                    select = NODEVIDEO;
                    MediaMenuUtils.open(BaseNodeUploadFragment.this, MediaMenuUtils.MenuType.VIDEO,
                            getFrameView(), select, videoUrl, showLocalChoice, videoFile -> videoUrl = videoFile);
                }
            }
        });
        // 删除视频
        ivVideoFlag.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                if (AppClickUtil.isDuplicateClick()) return;

                getIvVideo().setImageResource(0);
                ivVideoFlag.setImageResource(0);
                videoUrl = null;
                check();
            }
        });

        // 上传语音
        getIvAudio().setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                if (AppClickUtil.isDuplicateClick()) return;

                if (PermissionsManager.getInstance().checkRecordAudioPermission(getActivity(), true) && PermissionsManager.getInstance().checkWriteExternalPermission(getActivity(), true)) {
                    select = NODEAUDIO;
                    MediaMenuUtils.open(BaseNodeUploadFragment.this, MediaMenuUtils.MenuType.AUDIO,
                            getFrameView(), select, audioUrl, showLocalChoice, new MediaMenuUtils.CallBack() {
                                @Override
                                public void actionVideoSuccess(String videoFile) {

                                }
                            });
                }
            }
        });
        // 删除录音
        ivAudioFlag.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                if (AppClickUtil.isDuplicateClick()) return;

                getIvAudio().setImageResource(0);
                ivAudioFlag.setImageResource(0);
                audioUrl = null;
                check();
            }
        });
        // 地址
        getTvAddress().setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                if (AppClickUtil.isDuplicateClick()) return;

                showAddress();
            }
        });
    }

    @Override
    public void init() {
        // 过滤表情,字数
        EditTextUtils.setRemark(getEtRemark(), getTvNumber(), mMaxLength);
        tvNumberSum.setText("" + mMaxLength);
    }

    @Override
    public void onDestroyView() {
        BitmapCompressUtils.recycleBitmap();
        hideProgressDialog();

        super.onDestroyView();
    }

    public void showAddress() {
        // 获取当前地址
        LocationManager locationManager = new LocationManager();
        locationManager.init(BaseApplication.getInstance().getBaseContext());
        locationManager.setComeback(location -> {
            locationManager.destroyLocation();
            if (location != null && getTvAddress() != null) {
                getTvAddress().setText(location.getAddress());
            }
        });
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        if (resultCode == NODEAUDIO && data != null) {
            audioUrl = data.getString("url");
            new Handler().postDelayed(() -> uploadQiNiu(audioUrl), 1000);
        }
        super.onFragmentResult(requestCode, resultCode, data);
    }

    private void uploadQiNiu(String file) {
        ULog.i("上传文件地址：" + file);
        QiNiuManager.getInstance().upload(getActivity(), getContext(), file, true, new QiNiuManager.ComeBack() {
            @Override
            public void result(boolean result, String url) {
                if (result) {
                    ULog.i("上传成功：" + url);
                    refreshPhotograph(url);
                } else {
                    ULog.i("上传失败：");
                    showToast("上传失败");
                }
            }

            @Override
            public void progress(String speed, int progress) {

            }
        });
    }

    public void refreshPhotograph(String url) {
        if (getContext() == null) {
            return;
        }

        ULog.i("refreshPhotograph");
        switch (select) {
            case NODEOne:
                ULog.i("显示图片1");
                if (arrayListImageUrl.get(0) != null)
                    arrayListImageUrl.remove(0);
                arrayListImageUrl.put(0, url);
                GlideManager.getInstance().withCanCancle(getContext(), url, getIvPhotographOne(), R.drawable.ic_loading_tp);
                if (getImageFlagOne() != null) {
                    getImageFlagOne().setPadding(0, 0, 0, 0);
                    getImageFlagOne().setImageResource(R.drawable.ic_del_02);
                }
                flCamera2.setVisibility(View.VISIBLE);
                break;
            case NODETwo:
                ULog.i("显示图片2");
                if (arrayListImageUrl.get(1) != null)
                    arrayListImageUrl.remove(1);
                arrayListImageUrl.put(1, url);
                GlideManager.getInstance().withCanCancle(getContext(), url, getIvPhotographTwo(), R.drawable.ic_loading_tp);

                if (getImageFlagTwo() != null)
                    getImageFlagTwo().setImageResource(R.drawable.ic_del_02);
                flCamera3.setVisibility(View.VISIBLE);
                break;
            case NODEThree:
                ULog.i("显示图片3");
                if (arrayListImageUrl.get(2) != null)
                    arrayListImageUrl.remove(2);
                arrayListImageUrl.put(2, url);
                GlideManager.getInstance().withCanCancle(getContext(), url, getIvPhotographThree(), R.drawable.ic_loading_tp);

                if (getImageFlagThree() != null)
                    getImageFlagThree().setImageResource(R.drawable.ic_del_02);
                break;
            case NODEVIDEO:
                ULog.i("显示视频");
                Bitmap mBitmapVideo = VideoUrl.createVideoThumbnail(videoUrl, MediaStore.Images.Thumbnails.MICRO_KIND);
                if (mBitmapVideo == null) break;
                final ByteArrayOutputStream stream = new ByteArrayOutputStream();
                mBitmapVideo.compress(Bitmap.CompressFormat.PNG, 100, stream);
                final byte[] bytes = stream.toByteArray();
                Glide.with(getContext())
                        .load(bytes)
                        .into(getIvVideo());
                videoUrl = url;

                if (ivVideoFlag != null) {
                    ivVideoFlag.setImageResource(R.drawable.ic_del_02);
                }
                ivVideoPlayFlag.setVisibility(View.VISIBLE);
                break;
            case NODEAUDIO:
                ULog.i("显示录音");
                audioUrl = url;
                getIvAudio().setBackgroundResource(R.drawable.ic_bfyybg);

                if (ivAudioFlag != null) {
                    ivAudioFlag.setImageResource(R.drawable.ic_del_02);
                }
                ivAudioPlayPlag.setVisibility(View.VISIBLE);
                break;
        }

        // 如果是1就不要去星了
        if (select == 0) {
            return;
        }
        check();
    }

    /**
     * 检测是否有1张以上图片，如果有就去掉*
     */
    public void check() {
        // 删除检测
        int size = arrayListImageUrl.size();
        if (size == 0) {
            flCamera1.setVisibility(View.VISIBLE);
            flCamera2.setVisibility(View.GONE);
            flCamera3.setVisibility(View.GONE);
        } else if (size == 1) {
            flCamera2.setVisibility(View.VISIBLE);
            flCamera3.setVisibility(View.GONE);
        } else if (size == 2) {

        }
        llUpload.requestFocus();
        llUpload.invalidate();

        // 检测星符号
        // 如果1有图片，就不要去星了
        if (arrayListImageUrl.get(0) != null) {
            return;
        }
        if (arrayListImageUrl.size() > 0 || videoUrl != null) {
            getImageFlagOne().setPadding(0, 0, 0, 0);
            getImageFlagOne().setImageResource(0);
        } else {
            getImageFlagOne().setPadding(ConvertUtils.dp2px(5), ConvertUtils.dp2px(5), ConvertUtils.dp2px(5), ConvertUtils.dp2px(5));
            getImageFlagOne().setImageResource(R.drawable.ic_xinghao);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null) {
                ArrayList<ImageItem> images = data.getParcelableArrayListExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if (images == null || images.size() <= 0) {
                    return;
                }

                switch (requestCode) {
                    case NODEOne:
                        nodeOneUrl = images.get(0).path;
                        uploadQiNiu(nodeOneUrl);
                        break;
                    case NODETwo:
                        nodeTwoUrl = images.get(0).path;
                        uploadQiNiu(nodeTwoUrl);
                        break;
                    case NODEThree:
                        nodeThreeUrl = images.get(0).path;
                        uploadQiNiu(nodeThreeUrl);
                        break;
                }
            }
        } else if (requestCode == NODEVIDEO && data != null) {
            String newUri = UriUtils.getUriPath(getContext(), data.getData());
            if (newUri != null)
                // 如果转换失败，那就是拍摄的，拍摄的用回调所获取的就可以了，如果转换成功，那就是选择的
                videoUrl = newUri;
            uploadQiNiu(videoUrl);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 图片传url
     */
    public String getImageUrl() {
        StringBuilder stringBuffer = new StringBuilder();
        for (int i = 0; i < arrayListImageUrl.size(); i++) {
            Object o = arrayListImageUrl.get(i);
            if (o != null) {
                stringBuffer.append(o.toString());
                // 最后一个不加 逗号
                if (i != arrayListImageUrl.size() - 1) {
                    stringBuffer.append(",");
                }
            }
        }
        return stringBuffer.toString();
    }

    /**
     * 图片存入实体
     */
    public void setSuperNodesBean() {
        //拆分图片
        StringBuilder strImageUrl = new StringBuilder();
        for (int i = 0; i < arrayListImageUrl.size(); i++) {
            if (!ObjectUtils.parseString(arrayListImageUrl.valueAt(i)).equals("")) {
                strImageUrl.append(ObjectUtils.parseString(arrayListImageUrl.valueAt(i)));
                // 最后一个不加 逗号
                if (i != arrayListImageUrl.size() - 1) {
                    strImageUrl.append(",");
                }
            }
        }


        if (strImageUrl.length() > 1) {
            strImageUrl = new StringBuilder(strImageUrl.substring(0, strImageUrl.length() - 1));
            getDriverOrderNodeDetail().setPicture(strImageUrl.toString());
        }
        if (!TextUtils.isEmpty(audioUrl)) {
            getDriverOrderNodeDetail().setAudioUrl(audioUrl);
        }
        if (videoUrl != null) {
            getDriverOrderNodeDetail().setVideoUrl(videoUrl);
        }
    }

    /**
     * 拍照和视频，还有录音
     */
    public void open(MediaMenuUtils.MenuType menuType, Object url, int requestCode) {
        MediaMenuUtils.open(BaseNodeUploadFragment.this, menuType,
                getFrameView(), requestCode, url, showLocalChoice, videoFile -> {

                });
    }

    protected abstract FrameLayout getFrameView();

    /**
     * 备注
     *
     * @return TextView
     */
    public abstract EditText getEtRemark();

    /**
     * 字体数量设置
     *
     * @return TextView
     */
    protected abstract TextView getTvNumber();

    //视频的第一个图片
    private DriverOrderNodeDetailWrapper driverOrderNodeDetailWrapper;

    private DriverOrderNodeDetail driverOrderNodeDetail;

    protected abstract ImageView getIvVideo();

    protected abstract ImageView getIvAudio();

    protected abstract ImageView getIvPhotographThree();

    protected abstract ImageView getIvPhotographTwo();

    protected abstract ImageView getIvPhotographOne();

    protected abstract ImageView getImageFlagThree();

    protected abstract ImageView getImageFlagTwo();

    protected abstract ImageView getImageFlagOne();

    protected abstract TextView getTvAddress();

    public DriverOrderNodeDetailWrapper getDriverOrderNodeDetailWrapper() {
        return driverOrderNodeDetailWrapper;
    }

    public void setDriverOrderNodeDetailWrapper(DriverOrderNodeDetailWrapper driverOrderNodeDetailWrapper) {
        this.driverOrderNodeDetailWrapper = driverOrderNodeDetailWrapper;
    }

    public DriverOrderNodeDetail getDriverOrderNodeDetail() {
        return driverOrderNodeDetailWrapper.getDriverOrderNodeDetail();
    }

    public void setDriverOrderNodeDetail(DriverOrderNodeDetail driverOrderNodeDetail) {
        this.driverOrderNodeDetail = driverOrderNodeDetail;
    }

    public void setMaxLength(int mMaxLength) {
        this.mMaxLength = mMaxLength;
    }

    public void setShowLocalChoice(boolean showLocalChoice) {
        this.showLocalChoice = showLocalChoice;
    }
}
