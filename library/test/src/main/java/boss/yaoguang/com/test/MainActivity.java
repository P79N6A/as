package boss.yaoguang.com.test;

//import android.databinding.DataBindingUtil;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import boss.yaoguang.com.test.databinding.ActivityMainLibBinding;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_lib);
//
//        ActivityMainBinding a =

        ActivityMainLibBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main_lib);

//        binding.toolbar.

//        binding.setVariable().
//        binding.tvtv.setText("123");
    }


}
