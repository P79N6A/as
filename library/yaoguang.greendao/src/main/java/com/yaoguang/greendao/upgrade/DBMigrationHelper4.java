package com.yaoguang.greendao.upgrade;


import org.greenrobot.greendao.database.Database;

/**
 * 作废
 * Created by zhongjh on 2017/3/31.
 */
public class DBMigrationHelper4 extends AbstractMigratorHelper {

    public void onUpgrade(Database db) {
        //货主端添加列
//        db.execSQL("ALTER TABLE DIARY_MAIN ADD COLUMN VIDEO TEXT");
        //物流端的用户添加列
//        db.execSQL("ALTER TABLE COMPANY_USER ADD COLUMN IS_PHONE_VALID INTEGER");
    }

}
