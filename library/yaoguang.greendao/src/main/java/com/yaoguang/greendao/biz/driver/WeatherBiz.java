package com.yaoguang.greendao.biz.driver;

import com.yaoguang.greendao.biz.BaseBiz;
import com.yaoguang.greendao.entity.Weather;

import org.greenrobot.greendao.AbstractDao;

/**
 * 作者：韦理英
 * 时间： 2017/5/8 0008.
 * <p>
 * <p>
 * update zhongjh
 * 2018-07-11
 */

public class WeatherBiz extends BaseBiz<Weather, Long> {

    public WeatherBiz(AbstractDao dao) {
        super(dao);
    }
}
