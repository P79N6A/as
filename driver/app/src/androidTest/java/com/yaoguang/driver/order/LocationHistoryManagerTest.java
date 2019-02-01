package com.yaoguang.driver.order;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.test.InstrumentationRegistry;

import com.yaoguang.greendao.entity.DriverOrderProgressWrapper;
import com.yaoguang.map.location.impl.LocationHistoryManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * 文件名：
 * 描    述：
 * 作    者：韦理英
 * 时    间：2017/8/1 0001.
 * 版    权：
 */
public class LocationHistoryManagerTest {
    private LocationHistoryManager locationHistoryManager;
    private Context appContext;

    @Before
    public void setUp() throws Exception {
        appContext = InstrumentationRegistry.getTargetContext();
        MultiDex.install(appContext);

        locationHistoryManager = new LocationHistoryManager();
        DriverOrderProgressWrapper driverOrderProgressWrapper = new DriverOrderProgressWrapper();
        driverOrderProgressWrapper.setOrderId("1");
        driverOrderProgressWrapper.setOrderSn("2");
        driverOrderProgressWrapper.setNodeId("3");
        driverOrderProgressWrapper.setNodeName("4");
    }



    @After
    public void tearDown() throws Exception {
        locationHistoryManager.destroyLocation();
    }

    @Test
    public void destroyLocation() throws Exception {
        locationHistoryManager.destroyLocation();
        assertEquals(locationHistoryManager.locationClient, null);
    }

    @Test
    public void getAllCache() throws Exception {
        locationHistoryManager.getAllCache();
    }

    @Test
    public void getCache() throws Exception {

    }

    @Test
    public void getMap() throws Exception {

    }

    @Test
    public void getJson() throws Exception {

    }

    @Test
    public void removeCache() throws Exception {

    }

    @Test
    public void saveCacheAndInit() throws Exception {

    }

    @Test
    public void saveCache() throws Exception {

    }

    @Test
    public void addHistory() throws Exception {

    }

    @Test
    public void sendToServer() throws Exception {

    }

    @Test
    public void getObserver() throws Exception {

    }

    @Test
    public void onLocationChanged() throws Exception {

    }

    @Test
    public void getDriverId() throws Exception {

    }

}