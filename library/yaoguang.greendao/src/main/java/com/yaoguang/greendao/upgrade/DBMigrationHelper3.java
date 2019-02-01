package com.yaoguang.greendao.upgrade;

import com.yaoguang.greendao.DaoMaster;

import org.greenrobot.greendao.database.Database;

/* *
 * Created by zhongjh on 2017/3/31.
 *  判断是否存在再创建 作为发布的初始的版本
 */
public class DBMigrationHelper3 extends AbstractMigratorHelper {

    public void onUpgrade(Database db) {
        DaoMaster.dropAllTables(db, true);
        DaoMaster.createAllTables(db, true);
    }

}
