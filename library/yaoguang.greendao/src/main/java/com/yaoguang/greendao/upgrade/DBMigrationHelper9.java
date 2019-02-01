package com.yaoguang.greendao.upgrade;


import org.greenrobot.greendao.database.Database;

/**
 * Created by zhongjh on 2017/3/31.
 */
public class DBMigrationHelper9 extends AbstractMigratorHelper {

    public void onUpgrade(Database db) {
        // Location
        // 修改表名
        db.execSQL("ALTER TABLE \"LOCATION\" RENAME TO \"LOCATION_20160505\"");

        //创建新表
        db.execSQL("CREATE TABLE \"LOCATION\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"LON\" REAL NOT NULL ," + // 1: lon
                "\"LAT\" REAL NOT NULL ," + // 2: lat
                "\"POSITION_TIME\" TEXT," + // 3: positionTime
                "\"ERROR\" TEXT," + // 4: error
                "\"PROVINCE\" TEXT," + // 5: province
                "\"CITY\" TEXT," + // 6: city
                "\"DISTRICT\" TEXT," + // 7: district
                "\"STREET\" TEXT," + // 8: street
                "\"ADDRESS\" TEXT," + // 9: address
                "\"USER_DRIVER_ID\" TEXT," + // 10: userDriverId
                "\"AZIMUTH\" TEXT," + // 11: azimuth
                "\"SPEED\" TEXT);"); // 12: speed

        //5.删除临时表
        db.execSQL("DROP TABLE \"LOCATION_20160505\" ");

    }

}
