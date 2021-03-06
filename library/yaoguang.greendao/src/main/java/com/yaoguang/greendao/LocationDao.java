package com.yaoguang.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.yaoguang.greendao.entity.Location;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "LOCATION".
*/
public class LocationDao extends AbstractDao<Location, Long> {

    public static final String TABLENAME = "LOCATION";

    /**
     * Properties of entity Location.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Lon = new Property(1, double.class, "lon", false, "LON");
        public final static Property Lat = new Property(2, double.class, "lat", false, "LAT");
        public final static Property PositionTime = new Property(3, String.class, "positionTime", false, "POSITION_TIME");
        public final static Property Error = new Property(4, String.class, "error", false, "ERROR");
        public final static Property Province = new Property(5, String.class, "province", false, "PROVINCE");
        public final static Property City = new Property(6, String.class, "city", false, "CITY");
        public final static Property District = new Property(7, String.class, "district", false, "DISTRICT");
        public final static Property Street = new Property(8, String.class, "street", false, "STREET");
        public final static Property Address = new Property(9, String.class, "address", false, "ADDRESS");
        public final static Property UserDriverId = new Property(10, String.class, "userDriverId", false, "USER_DRIVER_ID");
        public final static Property Azimuth = new Property(11, String.class, "azimuth", false, "AZIMUTH");
        public final static Property Speed = new Property(12, String.class, "speed", false, "SPEED");
    }


    public LocationDao(DaoConfig config) {
        super(config);
    }
    
    public LocationDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"LOCATION\" (" + //
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
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"LOCATION\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, Location entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindDouble(2, entity.getLon());
        stmt.bindDouble(3, entity.getLat());
 
        String positionTime = entity.getPositionTime();
        if (positionTime != null) {
            stmt.bindString(4, positionTime);
        }
 
        String error = entity.getError();
        if (error != null) {
            stmt.bindString(5, error);
        }
 
        String province = entity.getProvince();
        if (province != null) {
            stmt.bindString(6, province);
        }
 
        String city = entity.getCity();
        if (city != null) {
            stmt.bindString(7, city);
        }
 
        String district = entity.getDistrict();
        if (district != null) {
            stmt.bindString(8, district);
        }
 
        String street = entity.getStreet();
        if (street != null) {
            stmt.bindString(9, street);
        }
 
        String address = entity.getAddress();
        if (address != null) {
            stmt.bindString(10, address);
        }
 
        String userDriverId = entity.getUserDriverId();
        if (userDriverId != null) {
            stmt.bindString(11, userDriverId);
        }
 
        String azimuth = entity.getAzimuth();
        if (azimuth != null) {
            stmt.bindString(12, azimuth);
        }
 
        String speed = entity.getSpeed();
        if (speed != null) {
            stmt.bindString(13, speed);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, Location entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindDouble(2, entity.getLon());
        stmt.bindDouble(3, entity.getLat());
 
        String positionTime = entity.getPositionTime();
        if (positionTime != null) {
            stmt.bindString(4, positionTime);
        }
 
        String error = entity.getError();
        if (error != null) {
            stmt.bindString(5, error);
        }
 
        String province = entity.getProvince();
        if (province != null) {
            stmt.bindString(6, province);
        }
 
        String city = entity.getCity();
        if (city != null) {
            stmt.bindString(7, city);
        }
 
        String district = entity.getDistrict();
        if (district != null) {
            stmt.bindString(8, district);
        }
 
        String street = entity.getStreet();
        if (street != null) {
            stmt.bindString(9, street);
        }
 
        String address = entity.getAddress();
        if (address != null) {
            stmt.bindString(10, address);
        }
 
        String userDriverId = entity.getUserDriverId();
        if (userDriverId != null) {
            stmt.bindString(11, userDriverId);
        }
 
        String azimuth = entity.getAzimuth();
        if (azimuth != null) {
            stmt.bindString(12, azimuth);
        }
 
        String speed = entity.getSpeed();
        if (speed != null) {
            stmt.bindString(13, speed);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public Location readEntity(Cursor cursor, int offset) {
        Location entity = new Location( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getDouble(offset + 1), // lon
            cursor.getDouble(offset + 2), // lat
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // positionTime
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // error
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // province
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // city
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // district
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // street
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // address
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // userDriverId
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // azimuth
            cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12) // speed
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, Location entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setLon(cursor.getDouble(offset + 1));
        entity.setLat(cursor.getDouble(offset + 2));
        entity.setPositionTime(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setError(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setProvince(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setCity(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setDistrict(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setStreet(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setAddress(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setUserDriverId(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setAzimuth(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setSpeed(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(Location entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(Location entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(Location entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
