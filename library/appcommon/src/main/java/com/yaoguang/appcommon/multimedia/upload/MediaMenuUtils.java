package com.yaoguang.appcommon.multimedia.upload;

import android.widget.FrameLayout;

import com.mingle.entity.MenuEntity;
import com.mingle.sweetpick.SweetSheet;
import com.yaoguang.appcommon.common.base.LookPhotoActivity;
import com.yaoguang.appcommon.multimedia.AudioRecordFragment;
import com.yaoguang.lib.R;
import com.yaoguang.lib.appcommon.utils.SweetSheetUtils;
import com.yaoguang.lib.common.displaymetrics.DisplayMetricsSPUtils;
import com.yaoguang.lib.common.file.MediaUtils;
import com.yaoguang.lib.common.imagepicker.ImagePickerUtils;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * 拍照和视频，还有录音，查看与拍照类
 * Created by 韦理英
 * on 2017/7/20 0020.
 */

public class MediaMenuUtils {

    private CallBack mCallBack;

    public interface CallBack {

        /**
         * @param videoFile 新建后的视频地址
         */
        void actionVideoSuccess(String videoFile);

    }

    /**
     * 拍照和视频，还有录音
     */
    public static void open(final SupportFragment fragment, final MenuType menuType, FrameLayout frameView, final int requestCode, final Object url, CallBack callBack) {
        open(fragment, menuType, frameView, requestCode, url, true, callBack);
    }

    /**
     * 拍照和视频，还有录音
     */
    public static void open(final SupportFragment fragment, final MenuType menuType, FrameLayout frameView, final int requestCode, final Object url, final boolean showLocalChoice, CallBack callBack) {
        // 没有图片就只显示两个菜单
        GetHeightAndMenu getHeightAndMenu = new GetHeightAndMenu().build(menuType, showLocalChoice);
        int menu = getHeightAndMenu.getMenu();
        int height = getHeightAndMenu.getHeight();

        String title = "";
        if (menuType == MenuType.PHOTO) if (showLocalChoice) title = "选择图片来源";
        else title = "相机";
        else if (menuType == MenuType.VIDEO) if (showLocalChoice) title = "选择视频来源";
        else title = "相机";
        else if (menuType == MenuType.AUDIO) if (showLocalChoice) title = "选择录音来源";
        else title = "录音机";

        SweetSheetUtils.show(fragment.getContext(), frameView, title, menu, height, requestCode, new SweetSheet.OnMenuItemClickListener() {
            @Override
            public boolean onItemClick(int position, MenuEntity menuEntity) {
                switch (menuType) {
                    case PHOTO:
                        // 拍照
                        if (position == 0)
                            ImagePickerUtils.startActivityForResult(fragment.getActivity(), fragment, false,
                                    requestCode, true, DisplayMetricsSPUtils.getScreenWidth(fragment.getContext()), 1, 1);
                        else // 查看
                            // 文件选择
                            if (position == 1 && showLocalChoice)
                                ImagePickerUtils.startActivityForResult(fragment.getActivity(), fragment, false,
                                        requestCode, false, DisplayMetricsSPUtils.getScreenWidth(fragment.getContext()), 1, 1);
                            else
                                LookPhotoActivity.newInstance(fragment.getActivity(), url == null ? "" : (String) url);
                        break;
                    case VIDEO:
                        //  视频
                        // 录像
                        if (position == 0) {
                            String videoFile = MediaUtils.actionVideo(fragment.getActivity(), requestCode);
                            if (callBack != null)
                                callBack.actionVideoSuccess(videoFile);
                        } else { // 查看
                            // 文件选择
                            if (position == 1 && showLocalChoice)
                                MediaUtils.selectFile(fragment.getActivity(), requestCode);
                            else
                                MediaUtils.openVideo(fragment.getContext(), url == null ? "" : (String) url);
                        }
                        break;
                    case AUDIO:
                        // 音频
                        // 录音
                        if (position == 0)
                            fragment.startForResult(AudioRecordFragment.newInstance(), requestCode);
                        else // 查看
                            // 文件选择
                            if (position == 1 && showLocalChoice)
                                MediaUtils.selectFile(fragment.getActivity(), requestCode);
                            else
                                MediaUtils.openAudio(fragment.getContext(), url == null ? "" : (String) url);
                        break;
                }

                return true;
            }
        });
    }


    /**
     * 获取菜单和菜单id
     */
    public static class GetHeightAndMenu {
        private int height;
        private int menu;

        public int getHeight() {
            return height;
        }

        public int getMenu() {
            return menu;
        }

        public GetHeightAndMenu build(MenuType menuType, boolean showLocalChoice) {
            // 菜单高度
            if (showLocalChoice) height = 250;
            else height = 200;

            switch (menuType) {
                case PHOTO:
                    if (showLocalChoice) menu = R.menu.sheet_head_portrait_phone;
                    else menu = R.menu.sheet_head_portrait_phone2;
                    break;
                case VIDEO:
                    if (showLocalChoice) menu = R.menu.sheet_head_portrait_video;
                    else menu = R.menu.sheet_head_portrait_video2;
                    break;
                case AUDIO:
                    if (showLocalChoice) menu = R.menu.sheet_head_portrait_audio;
                    else menu = R.menu.sheet_head_portrait_audio2;
                    break;
            }

            return this;
        }
    }

    public enum MenuType {
        PHOTO, VIDEO, AUDIO
    }
}
