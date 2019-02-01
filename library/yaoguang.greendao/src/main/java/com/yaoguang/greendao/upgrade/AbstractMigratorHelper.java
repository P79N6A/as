package com.yaoguang.greendao.upgrade;

import android.database.sqlite.SQLiteDatabase;

import org.greenrobot.greendao.database.Database;

/* *
 * Created by zhongjh on 2017/3/31.
 * 更新数据库抽象类
 */
public abstract class AbstractMigratorHelper {

    public abstract void onUpgrade(Database db);

}
