package com.yaoguang.lib.common.imagepicker;

import android.app.Activity;
import android.content.Intent;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;
import com.yaoguang.lib.common.displaymetrics.ProportionUtils;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * 封装拍照
 * Created by zhongjh on 2017/10/25.
 */
public class ImagePickerUtils {

    /**
     * 得到根Fragment
     */
    private static SupportFragment getRootFragment(SupportFragment fragment) {
        if (fragment.getPreFragment() != null) {
            fragment = (SupportFragment) fragment.getPreFragment();
            while (fragment.getPreFragment() != null) {
                fragment = (SupportFragment) fragment.getPreFragment();
            }
        }
        return fragment;

    }

    /**
     * 拍照
     *
     * @param activity            当前窗体
     * @param fragment            当前窗体
     * @param isCrop              允许裁剪
     * @param requestCode         请求码
     * @param extras_take_pickers 是否直接打开相机，如果是选择图片就是false
     * @param width               裁剪宽度
     * @param oldHeight           高度的比例
     * @param oldWidth            宽度的比例
     */
    public static void startActivityForResult(Activity activity, SupportFragment fragment, boolean isCrop, int requestCode, boolean extras_take_pickers, int width, int oldHeight, int oldWidth) {
        int height = ProportionUtils.getHeight(oldHeight, oldWidth, width);
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);  //显示拍照按钮
        imagePicker.setCrop(isCrop);        //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true); //是否按矩形区域保存
        imagePicker.setMultiMode(false);
        imagePicker.setSelectLimit(1);    //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(width);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(height);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(width);//保存文件的宽度。单位像素
        imagePicker.setOutPutY(height);//保存文件的高度。单位像素

        if (fragment == null) {
            Intent intent = new Intent(activity, ImageGridActivity.class);
            if (extras_take_pickers)
                intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
            activity.startActivityForResult(intent, requestCode);
        } else {
            fragment = getRootFragment(fragment);
            Intent intent = new Intent(fragment.getContext(), ImageGridActivity.class);
            if (extras_take_pickers)
                intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
            fragment.startActivityForResult(intent, requestCode);
        }
    }


}
