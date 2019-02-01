package com.yaoguang.appcommon.multimedia;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.czt.mp3recorder.MP3Recorder;
import com.shuyu.waveview.AudioPlayer;
import com.shuyu.waveview.FileUtils;
import com.yaoguang.appcommon.R;
import com.yaoguang.appcommon.common.finalrequest.DriverRequest;
import com.yaoguang.appcommon.databinding.FragmentAudioRecordBinding;
import com.yaoguang.lib.base.BaseApplication;
import com.yaoguang.lib.base.BaseFragmentDataBind;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.common.AppClickUtil;
import com.yaoguang.lib.common.ULog;
import com.yaoguang.lib.common.constants.Constants;
import com.yaoguang.lib.common.debounceclick.NoDoubleClickListener;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;

/**
 * 录音mp3
 * Created by zhongjh on 2018/6/27.
 */
public class AudioRecordFragment extends BaseFragmentDataBind<FragmentAudioRecordBinding> {

    String filePath; // 当前的录音文件地址
    RecordStatus status = RecordStatus.STOP;// 当前播放状态
    MP3Recorder mRecorder; // mp3工具类
    AudioPlayer audioPlayer;// 播放工具类
    boolean mIsRecord = false;// 是否正在录音
    boolean mIsPlay = false; // 是否正在播放

    private Thread mThread;// 控制圆环的线程
    private ProcessHandler mHandlerProcess = new ProcessHandler(this);// 圆环的ui线程

    enum RecordStatus {
        RECORD, //  录音开始
        STOP,   //  录音结束
        PLAY,   //  录音播放
        PLAY_PAUSE,   //  暂停播放
    }

    public static AudioRecordFragment newInstance() {
        return new AudioRecordFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_audio_record;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void init() {
        status = RecordStatus.STOP;
        mDataBinding.ivRecord.setImageResource(R.drawable.ic_sound_recording);
        mDataBinding.tvTime.setText("00:00:00");
        // 启动圆环
        mDataBinding.ivProcessCircle.startProgress();
        mDataBinding.tvSubmit.setVisibility(View.GONE);
    }

    @Override
    public void onPause() {
        super.onPause();
        // 停止录音
        if (mIsRecord) {
            resolveStopRecord();
        }
        // 停止播放
        if (mIsPlay) {
            audioPlayer.pause();
            audioPlayer.stop();
        }
//        if (wavePopWindow != null) {
//            wavePopWindow.onPause();
//        }
    }

    @Override
    public void initListener() {
        audioPlayer = new AudioPlayer(getActivity(), new AudioPlayerHandler(this));

        // 关闭
        mDataBinding.ivClose.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                pop();
            }
        });

        // 确定
        mDataBinding.tvSubmit.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                ULog.i("url=" + filePath);
                Bundle bundle = null;
                if (!TextUtils.isEmpty(filePath)) {
                    bundle = new Bundle();
                    bundle.putString("url", filePath);
                }
                setFragmentResult(DriverRequest.REQUESTCODE_NODE_UPLOAD + 4, bundle);
                pop();
            }
        });

        // 按钮：录音，暂时，停止
        mDataBinding.ivRecord.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                if (AppClickUtil.isDuplicateClick()) return;

                // 设置状态
                switch (status) {
                    // 如果当前操作是：停止（或者是初始界面）
                    case STOP:
                        if (TextUtils.isEmpty(filePath)) {
                            // 设置当前操作：开始录音
                            status = RecordStatus.RECORD;
                            //  如果没有录音文件，设置图片下一步操作：停止
                            mDataBinding.ivRecord.setImageResource(R.drawable.ic_stop);
                        } else {
                            // 设置当前操作：开始播放
                            status = RecordStatus.PLAY;
                            //  如果有录音文件，设置图片下一步操作：暂停
                            mDataBinding.ivRecord.setImageResource(R.drawable.ic_suspend);
                        }
                        break;
                    // 如果当前操作是：正在录音
                    case RECORD:
                        // 设置当前操作：停止
                        status = RecordStatus.STOP;
                        // 设置图片下一步操作：播放
                        mDataBinding.ivRecord.setImageResource(R.drawable.ic_play);
                        break;
                    // 如果当前操作是：正在播放
                    case PLAY:
                        // 设置成暂停
                        status = RecordStatus.PLAY_PAUSE;
                        // 设置图片下一步操作：播放
                        mDataBinding.ivRecord.setImageResource(R.drawable.ic_play);
                        break;
                    // 如果当前操作是：已停止
                    case PLAY_PAUSE:
                        // 设置成播放
                        status = RecordStatus.PLAY;
                        // 设置图片下一步操作：暂停
                        mDataBinding.ivRecord.setImageResource(R.drawable.ic_suspend);
                        break;
                }

                // 执行操作
                switch (status) {
                    case RECORD:    // 开始录音
                        resolveRecord();
                        break;
                    case PLAY:      // 播放
                        resolvePlayRecord();
                        break;
                    case STOP:      // 停止录音
                        resolveStopRecord();
                        break;
                    case PLAY_PAUSE: // 暂停播放
                        resolvePlayErrorPause();
                        break;
                }
            }
        });

    }


    @Override
    public BasePresenter getPresenter() {
        return null;
    }

    /**
     * 开始录音
     */
    private void resolveRecord() {
        filePath = FileUtils.getAppPath();
        File file = new File(filePath);
        if (!file.exists()) {
            if (!file.mkdirs()) {
                Toast.makeText(BaseApplication.getInstance().getApplicationContext(), "创建文件失败", Toast.LENGTH_SHORT).show();
                return;
            }
        }

//        int offset = DisplayMetricsUtils.dip2px(1);
        // 存储的文件地址
        filePath = Constants.SDK_PATH_AUDIO + "/" + System.currentTimeMillis() + ".mp3";
        mRecorder = new MP3Recorder(new File(filePath));
//        // 这个是用来做波形图的..
//        int size = DisplayMetricsUtils.getScreenWidth(getContext()) / offset;//控件默认的间隔是1
//        mRecorder.setDataList(audioWave.getRecList(), size);

        mRecorder.setErrorHandler(new ErrorHandler(this));

        try {
            mRecorder.start();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(BaseApplication.getInstance().getApplicationContext(), "录音出现异常", Toast.LENGTH_SHORT).show();
            resolveError();
            return;
        }

        mDataBinding.ivProcessCircle.setMaxProgress(59);
        // 圆环线程开启
        mThread = new Thread(() -> {
            int i = 0;
            while (mThread != null && !mThread.isInterrupted()) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // 每秒添加
                i = i + 1;
                updateProcess(i);
            }
        });
        mThread.start();

        mIsRecord = true;
    }

    /**
     * 暂停录音
     */
    private void resolvePause() {
//        if (!mIsRecord)
//            return;
//        if (mRecorder.isPause()) {
//            resolveRecordUI();
//            audioWave.setPause(false);
//            mRecorder.setPause(false);
//            recordPause.setText("暂停");
//        } else {
//            audioWave.setPause(true);
//            mRecorder.setPause(true);
//            recordPause.setText("继续");
//        }
    }

    /**
     * 停止录音
     */
    private void resolveStopRecord() {
        mDataBinding.tvSubmit.setVisibility(View.VISIBLE);
        mDataBinding.ivProcessCircle.setShowProgress(false);

        // 停止圆环转动
        if (mThread != null && !mThread.isInterrupted()) {
            mThread.interrupt();
            mThread = null;
        }

        if (mRecorder != null && mRecorder.isRecording()) {
            mRecorder.setPause(false);
            mRecorder.stop();
        }
        mIsRecord = false;
    }

    /**
     * 录音异常
     */
    private void resolveError() {
        // 重新初始化
        init();
        // 删除路径
        FileUtils.deleteFile(filePath);
        filePath = "";
        // 停止录音
        if (mRecorder != null && mRecorder.isRecording()) {
            mRecorder.stop();
        }
    }

    /**
     * 播放
     */
    private void resolvePlayRecord() {
        if (TextUtils.isEmpty(filePath) || !new File(filePath).exists()) {
            Toast.makeText(BaseApplication.getInstance().getApplicationContext(), "文件不存在", Toast.LENGTH_SHORT).show();
            return;
        }
        mIsPlay = true;
        audioPlayer.playUrl(filePath);
    }

    /**
     * 播放异常
     */
    private void resolvePlayError() {
        // 重新初始化
        init();
        // 停止播放
        if (mIsPlay) {
            mIsPlay = false;
            audioPlayer.pause();
        }
    }

    /**
     * 播放暂停
     */
    private void resolvePlayErrorPause() {
        // 暂停播放
        if (mIsPlay) {
            mIsPlay = false;
            audioPlayer.pause();
        }
    }

    /**
     * 波形播放
     */
    private void resolvePlayWaveRecord() {
//        if (TextUtils.isEmpty(filePath) || !new File(filePath).exists()) {
//            Toast.makeText(BaseApplication.getInstance().getApplicationContext(), "文件不存在", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        Intent intent = new Intent(getActivity(), WavePlayActivity.class);
//        intent.putExtra("uri", filePath);
//        startActivity(intent);
    }

    /**
     * 圆环转动
     *
     * @param i 秒
     */
    private void updateProcess(final int i) {
        Message message = new Message();
        message.what = i;
        mHandlerProcess.sendMessage(message);
    }

    /**
     * 录音异常的handler
     */
    private static class ErrorHandler extends Handler {
        private final WeakReference<AudioRecordFragment> mAudioRecordFragment;

        ErrorHandler(AudioRecordFragment audioRecordFragment) {
            mAudioRecordFragment = new WeakReference<>(audioRecordFragment);
        }

        @Override
        public void handleMessage(Message msg) {
            AudioRecordFragment audioRecordFragment = mAudioRecordFragment.get();
            if (audioRecordFragment != null) {
                if (msg.what == MP3Recorder.ERROR_TYPE) {
                    Toast.makeText(BaseApplication.getInstance().getApplicationContext(), "没有麦克风权限", Toast.LENGTH_SHORT).show();
                    audioRecordFragment.resolveError();
                }
            }
        }
    }

    /**
     * 播放器的handler
     */
    private static class AudioPlayerHandler extends Handler {
        private final WeakReference<AudioRecordFragment> mAudioRecordFragment;

        AudioPlayerHandler(AudioRecordFragment audioRecordFragment) {
            mAudioRecordFragment = new WeakReference<>(audioRecordFragment);
        }

        @Override
        public void handleMessage(Message msg) {
            AudioRecordFragment audioRecordFragment = mAudioRecordFragment.get();
            switch (msg.what) {
                //更新的时间
                case AudioPlayer.HANDLER_CUR_TIME:
//                        curPosition = (int) msg.obj;
//                        playText.setText(toTime(curPosition) + " / " + toTime(duration));
                    break;
                case AudioPlayer.HANDLER_COMPLETE://播放结束
                    // 代码运行点击就变成播放结束了
                    audioRecordFragment.mDataBinding.ivRecord.performClick();
                    audioRecordFragment.mIsPlay = false;
                    break;
                //播放开始
                case AudioPlayer.HANDLER_PREPARED:
//                        duration = (int) msg.obj;
//                        playText.setText(toTime(curPosition) + " / " + toTime(duration));
                    break;
                //播放错误
                case AudioPlayer.HANDLER_ERROR:
                    audioRecordFragment.resolvePlayError();
                    break;
            }
        }

    }

    /**
     * 圆环变化
     */
    private static class ProcessHandler extends Handler{
        private final WeakReference<AudioRecordFragment> mAudioRecordFragment;

        ProcessHandler(AudioRecordFragment audioRecordFragment) {
            mAudioRecordFragment = new WeakReference<>(audioRecordFragment);
        }

        @Override
        public void handleMessage(Message msg) {
            int i = msg.what;
            AudioRecordFragment audioRecordFragment = mAudioRecordFragment.get();
            if (i == 60) {
                audioRecordFragment.resolveStopRecord();
                audioRecordFragment.mDataBinding.ivProcessCircle.closeProgress();
                audioRecordFragment.mDataBinding.tvTime.setText("00:01:00");
                return;
            }
            audioRecordFragment.mDataBinding.tvTime.setText("00:00:" + String.format("%02d", i));
            audioRecordFragment.mDataBinding.ivProcessCircle.setProgress(i);
        }
    }

}
