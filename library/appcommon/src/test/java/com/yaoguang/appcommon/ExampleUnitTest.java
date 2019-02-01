package com.yaoguang.appcommon;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void test_upload() throws Exception {
//        Constants.SDK_PATH +
//        String uploadUrl = (String) uploadList.get(uploadI);
//        ULog.i("uploadI="+uploadI+"  upload url:" + uploadUrl);
//        DialogUtil.hideProgressDialog();
//        DialogUtil.showProgressDialog(getContext());
//        WanTuManager.getInstance().uploadFile(uploadUrl, Constants.APP_DRIVER, false, new UploadListener.BaseUploadListener(){
//            @Override
//            public void onUploadComplete(UploadTask uploadTask) {
//                if (uploadI >= uploadList.size() ) {
//                    DialogUtil.hideProgressDialog();
//                    presenter.handleRegister(getDriver(), getCode());
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
    }
}