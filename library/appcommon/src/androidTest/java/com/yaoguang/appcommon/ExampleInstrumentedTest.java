package com.yaoguang.appcommon;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    private int uploadI;
    private List<String> uploadList;

    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.yaoguang.appcommon.test", appContext.getPackageName());
    }


    @Test
    public void test_upload() throws Exception {
        List list = new ArrayList();
        list.add("file:///android_asset/1.png");
        list.add("file:///android_asset/1.png");
//        uploadWanTu();
    }

//    private void uploadWanTu() {
//        String uploadUrl = uploadList.get(uploadI);
//        ULog.i("uploadI=" + uploadI + "  upload url:" + uploadUrl);
//        DialogUtil.hideProgressDialog();
//        DialogUtil.showProgressDialog(InstrumentationRegistry.getTargetContext());
//        WanTuManager.getInstance().uploadFile(uploadUrl, Constants.APP_DRIVER, false, new UploadListener.BaseUploadListener() {
//            @Override
//            public void onUploadComplete(UploadTask uploadTask) {
//                if (uploadI >= uploadList.size()) {
//                    DialogUtil.hideProgressDialog();
//                    ULog.i("开始注册");
//                    return;
//                }
//                uploadI++;
//                uploadWanTu();
//                super.onUploadComplete(uploadTask);
//            }
//
//            @Override
//            public void onUploadFailed(UploadTask uploadTask, FailReason failReason) {
//                super.onUploadFailed(uploadTask, failReason);
//                DialogUtil.hideProgressDialog();
//            }
//
//            @Override
//            public void onUploading(UploadTask uploadTask) {
//                super.onUploading(uploadTask);
//                DialogUtil.setProgress(uploadTask.getTotal(), uploadTask.getType());
//            }
//        });
//    }
}
