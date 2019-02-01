package com.yaoguang.company.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.yaoguang.lib.base.BaseActivity;
import com.yaoguang.lib.base.BaseApplication;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.common.debounceclick.NoDoubleClickListener;
import com.yaoguang.lib.common.displaymetrics.DisplayMetricsSPUtils;
import com.yaoguang.lib.common.imagepicker.ImagePickerUtils;
import com.yaoguang.company.R;
import com.yaoguang.greendao.entity.OCR;
import com.yaoguang.lib.net.ApiDefault;
import com.yaoguang.datasource.api.ThirdApi;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * 测试拍照的
 * Created by zhongjh on 2017/11/7.
 */
public class TestActivity extends BaseActivity {

    @BindView(R.id.button)
    Button button;
    @BindView(R.id.textView5)
    EditText textView5;
    @BindView(R.id.textView6)
    EditText textView6;
    @BindView(R.id.tvMessage)
    TextView tvMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);

        button.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                ImagePickerUtils.startActivityForResult(TestActivity.this, null, false, 1, true, DisplayMetricsSPUtils.getScreenWidth(getBaseContext()), 768, 1024);
            }
        });

    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == 1) {
                ArrayList<ImageItem> images = data.getParcelableArrayListExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                String iconPath = images.get(0).path;

                //进行图片压缩
                Luban.with(this)
                        .load(iconPath)                                   // 传人要压缩的图片列表
                        .ignoreBy(1500)                                  // 忽略不压缩图片的大小
//                        .setTargetDir(getPath())                        // 设置压缩后文件存储位置
                        .setCompressListener(new OnCompressListener() { //设置回调
                            @Override
                            public void onStart() {
                                // TODO 压缩开始前调用，可以在方法内启动 loading UI
                            }

                            @Override
                            public void onSuccess(File file) {
                                String iconTargePath = "";
                                // TODO 压缩成功后调用，返回压缩后的图片文件
                                FileInputStream in = null;
                                try {
                                    in = new FileInputStream(file);
                                    byte[] buffer = new byte[(int) file.length() + 100];
                                    int length = in.read(buffer);
                                    iconTargePath = Base64.encodeToString(buffer, 0, length,
                                            Base64.DEFAULT);
//                                    iconTargePath = iconTargePath.replaceAll("[\\s*\t\n\r]", "");
                                    in.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

//                File file = new File(iconPath);
//                RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-d
// ata"), file);
//                // 将RequestBody封装成MultipartBody.Part类型（同样是okhttp的）
//                MultipartBody.Part part = MultipartBody.Part.
//                        createFormData("1", file.getName(), requestBody);

                                HashMap<String, String> hashMap = new HashMap<String, String>();
                                hashMap.put("pic",iconTargePath);
                                ThirdApi thirdApi = ApiDefault.getInstance().retrofit.create(ThirdApi.class);
                                thirdApi.upLoadsImg
//                                        ("APPCODE 88cb385610b14fd4afd969b42075483f", iconTargePath)
//                                thirdApi.upLoadsImg("APPCODE 88cb385610b14fd4afd969b42075483f", "idcardocrs.market.alicloudapi.com","1510107779991","http","debug","24685188","RELEASE",
        ("APPCODE 88cb385610b14fd4afd969b42075483f",  hashMap)
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(new Observer<OCR>() {
                                            @Override
                                            public void onSubscribe(Disposable d) {

                                            }

                                            @Override
                                            public void onNext(OCR ocr) {
                                                if (ocr.getError_code() == 200) {
                                                    Toast.makeText(BaseApplication.getInstance(), ocr.getReason(), Toast.LENGTH_LONG).show();
                                                    tvMessage.setText(ocr.getResult().getAddress());
                                                } else {
                                                    Toast.makeText(BaseApplication.getInstance(), "失败" + ocr.getReason(), Toast.LENGTH_LONG).show();
//                                                    tvMessage.setText(ocr.getResult());
                                                }
                                            }

                                            @Override
                                            public void onError(Throwable e) {
                                                Toast.makeText(BaseApplication.getInstance(), "失败" + e.toString(), Toast.LENGTH_LONG).show();
                                                tvMessage.setText(e.toString());
                                            }

                                            @Override
                                            public void onComplete() {

                                            }
                                        });
                            }

                            @Override
                            public void onError(Throwable e) {
                                // TODO 当压缩过程出现问题时调用
                            }
                        }).launch();    //启动压缩


//                File file = new File(iconPath);


            }
        }
    }


}
