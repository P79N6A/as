package com.yaoguang.greendao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.yaoguang.greendao.upgrade.AbstractMigratorHelper;

import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.query.QueryBuilder;


/**
 * Created by zhongjh on 2017/3/31.
 * 数据库操作类
 */
public class DbCore {

    public static final String DEFAULT_DB_NAME = "yaoguang_db.db";
    public static Context mContext;
    private static DaoMaster daoMaster;
    private static DaoSession daoSession;
    private static String DB_NAME;

    public static void init(Context context) {
        init(context, DEFAULT_DB_NAME);
    }

    public static void init(Context context, String dbName) {
        if (context == null) {
            throw new IllegalArgumentException("context can't be null");
        }
        mContext = context.getApplicationContext();
        DB_NAME = dbName;
    }

    public static DaoMaster getDaoMaster() {
        //判断版本
        DaoMaster.OpenHelper helper = new UpgradeHelper(mContext, DB_NAME, null);
        daoMaster = new DaoMaster(helper.getWritableDatabase());
        return daoMaster;
    }

    public static DaoSession getDaoSession() {
        if (daoSession == null) {
            if (daoMaster == null) {
                daoMaster = getDaoMaster();
            }
            daoSession = daoMaster.newSession();
        }
        return daoSession;
    }

    public static void enableQueryBuilderLog() {
        QueryBuilder.LOG_SQL = true;
        QueryBuilder.LOG_VALUES = true;
    }

    /*
     * Created by zhongjh on 2015/12/29.
     * 根据版本判断，更新数据库
     */
    public static class UpgradeHelper extends DaoMaster.OpenHelper {

        public UpgradeHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
            super(context, name, factory);
        }

        /**
         * 调用执行升级
         */
        @Override
        public void onUpgrade(Database db, int oldVersion
                , int newVersion) {

        /* i represent the version where the user is now and the class named with this number implies that is upgrading from i to i++ schema */
            for (int i = (oldVersion); i <= newVersion; i++) {
                try {
                /* New instance of the class that migrates from i version to i++ version named DBMigratorHelper{version that the db has on this moment} */
                    AbstractMigratorHelper migratorHelper = (AbstractMigratorHelper) Class.forName("com.yaoguang.greendao.upgrade.DBMigrationHelper" + i).newInstance();
                    if (migratorHelper != null) {
                        migratorHelper.onUpgrade(db);
                    }

                } catch (ClassNotFoundException | ClassCastException | IllegalAccessException | InstantiationException e) {
//                    PrintToFileLogger printToFileLogger = new PrintToFileLogger();
//                    printToFileLogger.println(e,null);
                    break;
                }
            }
        }
    }

}
