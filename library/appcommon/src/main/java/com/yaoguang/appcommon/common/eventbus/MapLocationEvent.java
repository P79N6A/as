package com.yaoguang.appcommon.common.eventbus;

import com.yaoguang.greendao.entity.Location;

/**
 * 作者：韦理英
 * 时间： 2017/5/8 0008.
 */

public class MapLocationEvent {
    private Location location;

    public MapLocationEvent(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
