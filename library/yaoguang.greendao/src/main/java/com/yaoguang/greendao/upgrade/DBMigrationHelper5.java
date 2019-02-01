package com.yaoguang.greendao.upgrade;


import org.greenrobot.greendao.database.Database;

/**
 * Created by zhongjh on 2017/3/31.
 */
public class DBMigrationHelper5 extends AbstractMigratorHelper {

    public void onUpgrade(Database db) {
        //货主端的用户添加列
        db.execSQL("ALTER TABLE USER_OWNER ADD COLUMN TEL TEXT");
    }

}
