package com.yaoguang.greendao.biz.driver;

import com.yaoguang.greendao.LocationDao;
import com.yaoguang.greendao.biz.BaseBiz;
import com.yaoguang.greendao.entity.Location;

/**
 * 作者：韦理英
 * 时间： 2017/5/5 0005.
 * <p>
 * update zhongjh
 * 2018-07-11
 */

public class LocationBiz extends BaseBiz<Location, Long> {


    public LocationBiz(LocationDao dao) {
        super(dao);
    }


}
