package com.yaoguang.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.yaoguang.greendao.entity.Driver;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "DRIVER".
*/
public class DriverDao extends AbstractDao<Driver, Long> {

    public static final String TABLENAME = "DRIVER";

    /**
     * Properties of entity Driver.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Lid = new Property(0, Long.class, "lid", true, "_id");
        public final static Property Id = new Property(1, String.class, "id", false, "ID");
        public final static Property NickName = new Property(2, String.class, "nickName", false, "NICK_NAME");
        public final static Property Mobile = new Property(3, String.class, "mobile", false, "MOBILE");
        public final static Property Password = new Property(4, String.class, "password", false, "PASSWORD");
        public final static Property PasswordStrength = new Property(5, Integer.class, "passwordStrength", false, "PASSWORD_STRENGTH");
        public final static Property Photo = new Property(6, String.class, "photo", false, "PHOTO");
        public final static Property NativePlaceProvince = new Property(7, String.class, "nativePlaceProvince", false, "NATIVE_PLACE_PROVINCE");
        public final static Property NativePlaceCity = new Property(8, String.class, "nativePlaceCity", false, "NATIVE_PLACE_CITY");
        public final static Property NativePlaceDistrict = new Property(9, String.class, "nativePlaceDistrict", false, "NATIVE_PLACE_DISTRICT");
        public final static Property PlaceProvince = new Property(10, String.class, "placeProvince", false, "PLACE_PROVINCE");
        public final static Property PlaceCity = new Property(11, String.class, "placeCity", false, "PLACE_CITY");
        public final static Property PlaceDistrict = new Property(12, String.class, "placeDistrict", false, "PLACE_DISTRICT");
        public final static Property Qq = new Property(13, String.class, "qq", false, "QQ");
        public final static Property Email = new Property(14, String.class, "email", false, "EMAIL");
        public final static Property Sign = new Property(15, String.class, "sign", false, "SIGN");
        public final static Property ServiceArea = new Property(16, String.class, "serviceArea", false, "SERVICE_AREA");
        public final static Property CarProvince = new Property(17, String.class, "carProvince", false, "CAR_PROVINCE");
        public final static Property CarNumber = new Property(18, String.class, "carNumber", false, "CAR_NUMBER");
        public final static Property CarOwner = new Property(19, String.class, "carOwner", false, "CAR_OWNER");
        public final static Property CarRegisterDate = new Property(20, String.class, "carRegisterDate", false, "CAR_REGISTER_DATE");
        public final static Property CarLicencePhoto = new Property(21, String.class, "carLicencePhoto", false, "CAR_LICENCE_PHOTO");
        public final static Property TrailerProvince = new Property(22, String.class, "trailerProvince", false, "TRAILER_PROVINCE");
        public final static Property TrailerNumber = new Property(23, String.class, "trailerNumber", false, "TRAILER_NUMBER");
        public final static Property TrailerOwner = new Property(24, String.class, "trailerOwner", false, "TRAILER_OWNER");
        public final static Property TrailerRegisterDate = new Property(25, String.class, "trailerRegisterDate", false, "TRAILER_REGISTER_DATE");
        public final static Property TrailerPhoto = new Property(26, String.class, "trailerPhoto", false, "TRAILER_PHOTO");
        public final static Property DriverName = new Property(27, String.class, "driverName", false, "DRIVER_NAME");
        public final static Property IdNumber = new Property(28, String.class, "idNumber", false, "ID_NUMBER");
        public final static Property IdPhoto = new Property(29, String.class, "idPhoto", false, "ID_PHOTO");
        public final static Property IdAuthority = new Property(30, String.class, "idAuthority", false, "ID_AUTHORITY");
        public final static Property IdStartDate = new Property(31, String.class, "idStartDate", false, "ID_START_DATE");
        public final static Property IdEndDate = new Property(32, String.class, "idEndDate", false, "ID_END_DATE");
        public final static Property IdPhoto1 = new Property(33, String.class, "idPhoto1", false, "ID_PHOTO1");
        public final static Property IdPhoto2 = new Property(34, String.class, "idPhoto2", false, "ID_PHOTO2");
        public final static Property IdApplyTime = new Property(35, String.class, "idApplyTime", false, "ID_APPLY_TIME");
        public final static Property IdAuditTime = new Property(36, String.class, "idAuditTime", false, "ID_AUDIT_TIME");
        public final static Property IdAuditStatus = new Property(37, Integer.class, "idAuditStatus", false, "ID_AUDIT_STATUS");
        public final static Property LicenceValidDate = new Property(38, String.class, "licenceValidDate", false, "LICENCE_VALID_DATE");
        public final static Property DriverLicencePhoto = new Property(39, String.class, "driverLicencePhoto", false, "DRIVER_LICENCE_PHOTO");
        public final static Property Status = new Property(40, Integer.class, "status", false, "STATUS");
        public final static Property StatusDetailId = new Property(41, String.class, "statusDetailId", false, "STATUS_DETAIL_ID");
        public final static Property LoginFlag = new Property(42, Integer.class, "loginFlag", false, "LOGIN_FLAG");
        public final static Property AuditRemark = new Property(43, String.class, "auditRemark", false, "AUDIT_REMARK");
        public final static Property Remark = new Property(44, String.class, "remark", false, "REMARK");
        public final static Property Uuid = new Property(45, String.class, "uuid", false, "UUID");
        public final static Property SingleToken = new Property(46, String.class, "singleToken", false, "SINGLE_TOKEN");
        public final static Property DeviceToken = new Property(47, String.class, "deviceToken", false, "DEVICE_TOKEN");
        public final static Property MessageFlag = new Property(48, Integer.class, "messageFlag", false, "MESSAGE_FLAG");
        public final static Property BackgroundPicture = new Property(49, String.class, "backgroundPicture", false, "BACKGROUND_PICTURE");
        public final static Property CreatedBy = new Property(50, String.class, "createdBy", false, "CREATED_BY");
        public final static Property Created = new Property(51, String.class, "created", false, "CREATED");
        public final static Property Updated = new Property(52, String.class, "updated", false, "UPDATED");
        public final static Property IsValid = new Property(53, Integer.class, "isValid", false, "IS_VALID");
    }


    public DriverDao(DaoConfig config) {
        super(config);
    }
    
    public DriverDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"DRIVER\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: lid
                "\"ID\" TEXT," + // 1: id
                "\"NICK_NAME\" TEXT," + // 2: nickName
                "\"MOBILE\" TEXT," + // 3: mobile
                "\"PASSWORD\" TEXT," + // 4: password
                "\"PASSWORD_STRENGTH\" INTEGER," + // 5: passwordStrength
                "\"PHOTO\" TEXT," + // 6: photo
                "\"NATIVE_PLACE_PROVINCE\" TEXT," + // 7: nativePlaceProvince
                "\"NATIVE_PLACE_CITY\" TEXT," + // 8: nativePlaceCity
                "\"NATIVE_PLACE_DISTRICT\" TEXT," + // 9: nativePlaceDistrict
                "\"PLACE_PROVINCE\" TEXT," + // 10: placeProvince
                "\"PLACE_CITY\" TEXT," + // 11: placeCity
                "\"PLACE_DISTRICT\" TEXT," + // 12: placeDistrict
                "\"QQ\" TEXT," + // 13: qq
                "\"EMAIL\" TEXT," + // 14: email
                "\"SIGN\" TEXT," + // 15: sign
                "\"SERVICE_AREA\" TEXT," + // 16: serviceArea
                "\"CAR_PROVINCE\" TEXT," + // 17: carProvince
                "\"CAR_NUMBER\" TEXT," + // 18: carNumber
                "\"CAR_OWNER\" TEXT," + // 19: carOwner
                "\"CAR_REGISTER_DATE\" TEXT," + // 20: carRegisterDate
                "\"CAR_LICENCE_PHOTO\" TEXT," + // 21: carLicencePhoto
                "\"TRAILER_PROVINCE\" TEXT," + // 22: trailerProvince
                "\"TRAILER_NUMBER\" TEXT," + // 23: trailerNumber
                "\"TRAILER_OWNER\" TEXT," + // 24: trailerOwner
                "\"TRAILER_REGISTER_DATE\" TEXT," + // 25: trailerRegisterDate
                "\"TRAILER_PHOTO\" TEXT," + // 26: trailerPhoto
                "\"DRIVER_NAME\" TEXT," + // 27: driverName
                "\"ID_NUMBER\" TEXT," + // 28: idNumber
                "\"ID_PHOTO\" TEXT," + // 29: idPhoto
                "\"ID_AUTHORITY\" TEXT," + // 30: idAuthority
                "\"ID_START_DATE\" TEXT," + // 31: idStartDate
                "\"ID_END_DATE\" TEXT," + // 32: idEndDate
                "\"ID_PHOTO1\" TEXT," + // 33: idPhoto1
                "\"ID_PHOTO2\" TEXT," + // 34: idPhoto2
                "\"ID_APPLY_TIME\" TEXT," + // 35: idApplyTime
                "\"ID_AUDIT_TIME\" TEXT," + // 36: idAuditTime
                "\"ID_AUDIT_STATUS\" INTEGER," + // 37: idAuditStatus
                "\"LICENCE_VALID_DATE\" TEXT," + // 38: licenceValidDate
                "\"DRIVER_LICENCE_PHOTO\" TEXT," + // 39: driverLicencePhoto
                "\"STATUS\" INTEGER," + // 40: status
                "\"STATUS_DETAIL_ID\" TEXT," + // 41: statusDetailId
                "\"LOGIN_FLAG\" INTEGER," + // 42: loginFlag
                "\"AUDIT_REMARK\" TEXT," + // 43: auditRemark
                "\"REMARK\" TEXT," + // 44: remark
                "\"UUID\" TEXT," + // 45: uuid
                "\"SINGLE_TOKEN\" TEXT," + // 46: singleToken
                "\"DEVICE_TOKEN\" TEXT," + // 47: deviceToken
                "\"MESSAGE_FLAG\" INTEGER," + // 48: messageFlag
                "\"BACKGROUND_PICTURE\" TEXT," + // 49: backgroundPicture
                "\"CREATED_BY\" TEXT," + // 50: createdBy
                "\"CREATED\" TEXT," + // 51: created
                "\"UPDATED\" TEXT," + // 52: updated
                "\"IS_VALID\" INTEGER);"); // 53: isValid
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"DRIVER\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, Driver entity) {
        stmt.clearBindings();
 
        Long lid = entity.getLid();
        if (lid != null) {
            stmt.bindLong(1, lid);
        }
 
        String id = entity.getId();
        if (id != null) {
            stmt.bindString(2, id);
        }
 
        String nickName = entity.getNickName();
        if (nickName != null) {
            stmt.bindString(3, nickName);
        }
 
        String mobile = entity.getMobile();
        if (mobile != null) {
            stmt.bindString(4, mobile);
        }
 
        String password = entity.getPassword();
        if (password != null) {
            stmt.bindString(5, password);
        }
 
        Integer passwordStrength = entity.getPasswordStrength();
        if (passwordStrength != null) {
            stmt.bindLong(6, passwordStrength);
        }
 
        String photo = entity.getPhoto();
        if (photo != null) {
            stmt.bindString(7, photo);
        }
 
        String nativePlaceProvince = entity.getNativePlaceProvince();
        if (nativePlaceProvince != null) {
            stmt.bindString(8, nativePlaceProvince);
        }
 
        String nativePlaceCity = entity.getNativePlaceCity();
        if (nativePlaceCity != null) {
            stmt.bindString(9, nativePlaceCity);
        }
 
        String nativePlaceDistrict = entity.getNativePlaceDistrict();
        if (nativePlaceDistrict != null) {
            stmt.bindString(10, nativePlaceDistrict);
        }
 
        String placeProvince = entity.getPlaceProvince();
        if (placeProvince != null) {
            stmt.bindString(11, placeProvince);
        }
 
        String placeCity = entity.getPlaceCity();
        if (placeCity != null) {
            stmt.bindString(12, placeCity);
        }
 
        String placeDistrict = entity.getPlaceDistrict();
        if (placeDistrict != null) {
            stmt.bindString(13, placeDistrict);
        }
 
        String qq = entity.getQq();
        if (qq != null) {
            stmt.bindString(14, qq);
        }
 
        String email = entity.getEmail();
        if (email != null) {
            stmt.bindString(15, email);
        }
 
        String sign = entity.getSign();
        if (sign != null) {
            stmt.bindString(16, sign);
        }
 
        String serviceArea = entity.getServiceArea();
        if (serviceArea != null) {
            stmt.bindString(17, serviceArea);
        }
 
        String carProvince = entity.getCarProvince();
        if (carProvince != null) {
            stmt.bindString(18, carProvince);
        }
 
        String carNumber = entity.getCarNumber();
        if (carNumber != null) {
            stmt.bindString(19, carNumber);
        }
 
        String carOwner = entity.getCarOwner();
        if (carOwner != null) {
            stmt.bindString(20, carOwner);
        }
 
        String carRegisterDate = entity.getCarRegisterDate();
        if (carRegisterDate != null) {
            stmt.bindString(21, carRegisterDate);
        }
 
        String carLicencePhoto = entity.getCarLicencePhoto();
        if (carLicencePhoto != null) {
            stmt.bindString(22, carLicencePhoto);
        }
 
        String trailerProvince = entity.getTrailerProvince();
        if (trailerProvince != null) {
            stmt.bindString(23, trailerProvince);
        }
 
        String trailerNumber = entity.getTrailerNumber();
        if (trailerNumber != null) {
            stmt.bindString(24, trailerNumber);
        }
 
        String trailerOwner = entity.getTrailerOwner();
        if (trailerOwner != null) {
            stmt.bindString(25, trailerOwner);
        }
 
        String trailerRegisterDate = entity.getTrailerRegisterDate();
        if (trailerRegisterDate != null) {
            stmt.bindString(26, trailerRegisterDate);
        }
 
        String trailerPhoto = entity.getTrailerPhoto();
        if (trailerPhoto != null) {
            stmt.bindString(27, trailerPhoto);
        }
 
        String driverName = entity.getDriverName();
        if (driverName != null) {
            stmt.bindString(28, driverName);
        }
 
        String idNumber = entity.getIdNumber();
        if (idNumber != null) {
            stmt.bindString(29, idNumber);
        }
 
        String idPhoto = entity.getIdPhoto();
        if (idPhoto != null) {
            stmt.bindString(30, idPhoto);
        }
 
        String idAuthority = entity.getIdAuthority();
        if (idAuthority != null) {
            stmt.bindString(31, idAuthority);
        }
 
        String idStartDate = entity.getIdStartDate();
        if (idStartDate != null) {
            stmt.bindString(32, idStartDate);
        }
 
        String idEndDate = entity.getIdEndDate();
        if (idEndDate != null) {
            stmt.bindString(33, idEndDate);
        }
 
        String idPhoto1 = entity.getIdPhoto1();
        if (idPhoto1 != null) {
            stmt.bindString(34, idPhoto1);
        }
 
        String idPhoto2 = entity.getIdPhoto2();
        if (idPhoto2 != null) {
            stmt.bindString(35, idPhoto2);
        }
 
        String idApplyTime = entity.getIdApplyTime();
        if (idApplyTime != null) {
            stmt.bindString(36, idApplyTime);
        }
 
        String idAuditTime = entity.getIdAuditTime();
        if (idAuditTime != null) {
            stmt.bindString(37, idAuditTime);
        }
 
        Integer idAuditStatus = entity.getIdAuditStatus();
        if (idAuditStatus != null) {
            stmt.bindLong(38, idAuditStatus);
        }
 
        String licenceValidDate = entity.getLicenceValidDate();
        if (licenceValidDate != null) {
            stmt.bindString(39, licenceValidDate);
        }
 
        String driverLicencePhoto = entity.getDriverLicencePhoto();
        if (driverLicencePhoto != null) {
            stmt.bindString(40, driverLicencePhoto);
        }
 
        Integer status = entity.getStatus();
        if (status != null) {
            stmt.bindLong(41, status);
        }
 
        String statusDetailId = entity.getStatusDetailId();
        if (statusDetailId != null) {
            stmt.bindString(42, statusDetailId);
        }
 
        Integer loginFlag = entity.getLoginFlag();
        if (loginFlag != null) {
            stmt.bindLong(43, loginFlag);
        }
 
        String auditRemark = entity.getAuditRemark();
        if (auditRemark != null) {
            stmt.bindString(44, auditRemark);
        }
 
        String remark = entity.getRemark();
        if (remark != null) {
            stmt.bindString(45, remark);
        }
 
        String uuid = entity.getUuid();
        if (uuid != null) {
            stmt.bindString(46, uuid);
        }
 
        String singleToken = entity.getSingleToken();
        if (singleToken != null) {
            stmt.bindString(47, singleToken);
        }
 
        String deviceToken = entity.getDeviceToken();
        if (deviceToken != null) {
            stmt.bindString(48, deviceToken);
        }
 
        Integer messageFlag = entity.getMessageFlag();
        if (messageFlag != null) {
            stmt.bindLong(49, messageFlag);
        }
 
        String backgroundPicture = entity.getBackgroundPicture();
        if (backgroundPicture != null) {
            stmt.bindString(50, backgroundPicture);
        }
 
        String createdBy = entity.getCreatedBy();
        if (createdBy != null) {
            stmt.bindString(51, createdBy);
        }
 
        String created = entity.getCreated();
        if (created != null) {
            stmt.bindString(52, created);
        }
 
        String updated = entity.getUpdated();
        if (updated != null) {
            stmt.bindString(53, updated);
        }
 
        Integer isValid = entity.getIsValid();
        if (isValid != null) {
            stmt.bindLong(54, isValid);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, Driver entity) {
        stmt.clearBindings();
 
        Long lid = entity.getLid();
        if (lid != null) {
            stmt.bindLong(1, lid);
        }
 
        String id = entity.getId();
        if (id != null) {
            stmt.bindString(2, id);
        }
 
        String nickName = entity.getNickName();
        if (nickName != null) {
            stmt.bindString(3, nickName);
        }
 
        String mobile = entity.getMobile();
        if (mobile != null) {
            stmt.bindString(4, mobile);
        }
 
        String password = entity.getPassword();
        if (password != null) {
            stmt.bindString(5, password);
        }
 
        Integer passwordStrength = entity.getPasswordStrength();
        if (passwordStrength != null) {
            stmt.bindLong(6, passwordStrength);
        }
 
        String photo = entity.getPhoto();
        if (photo != null) {
            stmt.bindString(7, photo);
        }
 
        String nativePlaceProvince = entity.getNativePlaceProvince();
        if (nativePlaceProvince != null) {
            stmt.bindString(8, nativePlaceProvince);
        }
 
        String nativePlaceCity = entity.getNativePlaceCity();
        if (nativePlaceCity != null) {
            stmt.bindString(9, nativePlaceCity);
        }
 
        String nativePlaceDistrict = entity.getNativePlaceDistrict();
        if (nativePlaceDistrict != null) {
            stmt.bindString(10, nativePlaceDistrict);
        }
 
        String placeProvince = entity.getPlaceProvince();
        if (placeProvince != null) {
            stmt.bindString(11, placeProvince);
        }
 
        String placeCity = entity.getPlaceCity();
        if (placeCity != null) {
            stmt.bindString(12, placeCity);
        }
 
        String placeDistrict = entity.getPlaceDistrict();
        if (placeDistrict != null) {
            stmt.bindString(13, placeDistrict);
        }
 
        String qq = entity.getQq();
        if (qq != null) {
            stmt.bindString(14, qq);
        }
 
        String email = entity.getEmail();
        if (email != null) {
            stmt.bindString(15, email);
        }
 
        String sign = entity.getSign();
        if (sign != null) {
            stmt.bindString(16, sign);
        }
 
        String serviceArea = entity.getServiceArea();
        if (serviceArea != null) {
            stmt.bindString(17, serviceArea);
        }
 
        String carProvince = entity.getCarProvince();
        if (carProvince != null) {
            stmt.bindString(18, carProvince);
        }
 
        String carNumber = entity.getCarNumber();
        if (carNumber != null) {
            stmt.bindString(19, carNumber);
        }
 
        String carOwner = entity.getCarOwner();
        if (carOwner != null) {
            stmt.bindString(20, carOwner);
        }
 
        String carRegisterDate = entity.getCarRegisterDate();
        if (carRegisterDate != null) {
            stmt.bindString(21, carRegisterDate);
        }
 
        String carLicencePhoto = entity.getCarLicencePhoto();
        if (carLicencePhoto != null) {
            stmt.bindString(22, carLicencePhoto);
        }
 
        String trailerProvince = entity.getTrailerProvince();
        if (trailerProvince != null) {
            stmt.bindString(23, trailerProvince);
        }
 
        String trailerNumber = entity.getTrailerNumber();
        if (trailerNumber != null) {
            stmt.bindString(24, trailerNumber);
        }
 
        String trailerOwner = entity.getTrailerOwner();
        if (trailerOwner != null) {
            stmt.bindString(25, trailerOwner);
        }
 
        String trailerRegisterDate = entity.getTrailerRegisterDate();
        if (trailerRegisterDate != null) {
            stmt.bindString(26, trailerRegisterDate);
        }
 
        String trailerPhoto = entity.getTrailerPhoto();
        if (trailerPhoto != null) {
            stmt.bindString(27, trailerPhoto);
        }
 
        String driverName = entity.getDriverName();
        if (driverName != null) {
            stmt.bindString(28, driverName);
        }
 
        String idNumber = entity.getIdNumber();
        if (idNumber != null) {
            stmt.bindString(29, idNumber);
        }
 
        String idPhoto = entity.getIdPhoto();
        if (idPhoto != null) {
            stmt.bindString(30, idPhoto);
        }
 
        String idAuthority = entity.getIdAuthority();
        if (idAuthority != null) {
            stmt.bindString(31, idAuthority);
        }
 
        String idStartDate = entity.getIdStartDate();
        if (idStartDate != null) {
            stmt.bindString(32, idStartDate);
        }
 
        String idEndDate = entity.getIdEndDate();
        if (idEndDate != null) {
            stmt.bindString(33, idEndDate);
        }
 
        String idPhoto1 = entity.getIdPhoto1();
        if (idPhoto1 != null) {
            stmt.bindString(34, idPhoto1);
        }
 
        String idPhoto2 = entity.getIdPhoto2();
        if (idPhoto2 != null) {
            stmt.bindString(35, idPhoto2);
        }
 
        String idApplyTime = entity.getIdApplyTime();
        if (idApplyTime != null) {
            stmt.bindString(36, idApplyTime);
        }
 
        String idAuditTime = entity.getIdAuditTime();
        if (idAuditTime != null) {
            stmt.bindString(37, idAuditTime);
        }
 
        Integer idAuditStatus = entity.getIdAuditStatus();
        if (idAuditStatus != null) {
            stmt.bindLong(38, idAuditStatus);
        }
 
        String licenceValidDate = entity.getLicenceValidDate();
        if (licenceValidDate != null) {
            stmt.bindString(39, licenceValidDate);
        }
 
        String driverLicencePhoto = entity.getDriverLicencePhoto();
        if (driverLicencePhoto != null) {
            stmt.bindString(40, driverLicencePhoto);
        }
 
        Integer status = entity.getStatus();
        if (status != null) {
            stmt.bindLong(41, status);
        }
 
        String statusDetailId = entity.getStatusDetailId();
        if (statusDetailId != null) {
            stmt.bindString(42, statusDetailId);
        }
 
        Integer loginFlag = entity.getLoginFlag();
        if (loginFlag != null) {
            stmt.bindLong(43, loginFlag);
        }
 
        String auditRemark = entity.getAuditRemark();
        if (auditRemark != null) {
            stmt.bindString(44, auditRemark);
        }
 
        String remark = entity.getRemark();
        if (remark != null) {
            stmt.bindString(45, remark);
        }
 
        String uuid = entity.getUuid();
        if (uuid != null) {
            stmt.bindString(46, uuid);
        }
 
        String singleToken = entity.getSingleToken();
        if (singleToken != null) {
            stmt.bindString(47, singleToken);
        }
 
        String deviceToken = entity.getDeviceToken();
        if (deviceToken != null) {
            stmt.bindString(48, deviceToken);
        }
 
        Integer messageFlag = entity.getMessageFlag();
        if (messageFlag != null) {
            stmt.bindLong(49, messageFlag);
        }
 
        String backgroundPicture = entity.getBackgroundPicture();
        if (backgroundPicture != null) {
            stmt.bindString(50, backgroundPicture);
        }
 
        String createdBy = entity.getCreatedBy();
        if (createdBy != null) {
            stmt.bindString(51, createdBy);
        }
 
        String created = entity.getCreated();
        if (created != null) {
            stmt.bindString(52, created);
        }
 
        String updated = entity.getUpdated();
        if (updated != null) {
            stmt.bindString(53, updated);
        }
 
        Integer isValid = entity.getIsValid();
        if (isValid != null) {
            stmt.bindLong(54, isValid);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public Driver readEntity(Cursor cursor, int offset) {
        Driver entity = new Driver( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // lid
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // id
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // nickName
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // mobile
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // password
            cursor.isNull(offset + 5) ? null : cursor.getInt(offset + 5), // passwordStrength
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // photo
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // nativePlaceProvince
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // nativePlaceCity
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // nativePlaceDistrict
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // placeProvince
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // placeCity
            cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12), // placeDistrict
            cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13), // qq
            cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14), // email
            cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15), // sign
            cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16), // serviceArea
            cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17), // carProvince
            cursor.isNull(offset + 18) ? null : cursor.getString(offset + 18), // carNumber
            cursor.isNull(offset + 19) ? null : cursor.getString(offset + 19), // carOwner
            cursor.isNull(offset + 20) ? null : cursor.getString(offset + 20), // carRegisterDate
            cursor.isNull(offset + 21) ? null : cursor.getString(offset + 21), // carLicencePhoto
            cursor.isNull(offset + 22) ? null : cursor.getString(offset + 22), // trailerProvince
            cursor.isNull(offset + 23) ? null : cursor.getString(offset + 23), // trailerNumber
            cursor.isNull(offset + 24) ? null : cursor.getString(offset + 24), // trailerOwner
            cursor.isNull(offset + 25) ? null : cursor.getString(offset + 25), // trailerRegisterDate
            cursor.isNull(offset + 26) ? null : cursor.getString(offset + 26), // trailerPhoto
            cursor.isNull(offset + 27) ? null : cursor.getString(offset + 27), // driverName
            cursor.isNull(offset + 28) ? null : cursor.getString(offset + 28), // idNumber
            cursor.isNull(offset + 29) ? null : cursor.getString(offset + 29), // idPhoto
            cursor.isNull(offset + 30) ? null : cursor.getString(offset + 30), // idAuthority
            cursor.isNull(offset + 31) ? null : cursor.getString(offset + 31), // idStartDate
            cursor.isNull(offset + 32) ? null : cursor.getString(offset + 32), // idEndDate
            cursor.isNull(offset + 33) ? null : cursor.getString(offset + 33), // idPhoto1
            cursor.isNull(offset + 34) ? null : cursor.getString(offset + 34), // idPhoto2
            cursor.isNull(offset + 35) ? null : cursor.getString(offset + 35), // idApplyTime
            cursor.isNull(offset + 36) ? null : cursor.getString(offset + 36), // idAuditTime
            cursor.isNull(offset + 37) ? null : cursor.getInt(offset + 37), // idAuditStatus
            cursor.isNull(offset + 38) ? null : cursor.getString(offset + 38), // licenceValidDate
            cursor.isNull(offset + 39) ? null : cursor.getString(offset + 39), // driverLicencePhoto
            cursor.isNull(offset + 40) ? null : cursor.getInt(offset + 40), // status
            cursor.isNull(offset + 41) ? null : cursor.getString(offset + 41), // statusDetailId
            cursor.isNull(offset + 42) ? null : cursor.getInt(offset + 42), // loginFlag
            cursor.isNull(offset + 43) ? null : cursor.getString(offset + 43), // auditRemark
            cursor.isNull(offset + 44) ? null : cursor.getString(offset + 44), // remark
            cursor.isNull(offset + 45) ? null : cursor.getString(offset + 45), // uuid
            cursor.isNull(offset + 46) ? null : cursor.getString(offset + 46), // singleToken
            cursor.isNull(offset + 47) ? null : cursor.getString(offset + 47), // deviceToken
            cursor.isNull(offset + 48) ? null : cursor.getInt(offset + 48), // messageFlag
            cursor.isNull(offset + 49) ? null : cursor.getString(offset + 49), // backgroundPicture
            cursor.isNull(offset + 50) ? null : cursor.getString(offset + 50), // createdBy
            cursor.isNull(offset + 51) ? null : cursor.getString(offset + 51), // created
            cursor.isNull(offset + 52) ? null : cursor.getString(offset + 52), // updated
            cursor.isNull(offset + 53) ? null : cursor.getInt(offset + 53) // isValid
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, Driver entity, int offset) {
        entity.setLid(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setId(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setNickName(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setMobile(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setPassword(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setPasswordStrength(cursor.isNull(offset + 5) ? null : cursor.getInt(offset + 5));
        entity.setPhoto(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setNativePlaceProvince(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setNativePlaceCity(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setNativePlaceDistrict(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setPlaceProvince(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setPlaceCity(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setPlaceDistrict(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
        entity.setQq(cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13));
        entity.setEmail(cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14));
        entity.setSign(cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15));
        entity.setServiceArea(cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16));
        entity.setCarProvince(cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17));
        entity.setCarNumber(cursor.isNull(offset + 18) ? null : cursor.getString(offset + 18));
        entity.setCarOwner(cursor.isNull(offset + 19) ? null : cursor.getString(offset + 19));
        entity.setCarRegisterDate(cursor.isNull(offset + 20) ? null : cursor.getString(offset + 20));
        entity.setCarLicencePhoto(cursor.isNull(offset + 21) ? null : cursor.getString(offset + 21));
        entity.setTrailerProvince(cursor.isNull(offset + 22) ? null : cursor.getString(offset + 22));
        entity.setTrailerNumber(cursor.isNull(offset + 23) ? null : cursor.getString(offset + 23));
        entity.setTrailerOwner(cursor.isNull(offset + 24) ? null : cursor.getString(offset + 24));
        entity.setTrailerRegisterDate(cursor.isNull(offset + 25) ? null : cursor.getString(offset + 25));
        entity.setTrailerPhoto(cursor.isNull(offset + 26) ? null : cursor.getString(offset + 26));
        entity.setDriverName(cursor.isNull(offset + 27) ? null : cursor.getString(offset + 27));
        entity.setIdNumber(cursor.isNull(offset + 28) ? null : cursor.getString(offset + 28));
        entity.setIdPhoto(cursor.isNull(offset + 29) ? null : cursor.getString(offset + 29));
        entity.setIdAuthority(cursor.isNull(offset + 30) ? null : cursor.getString(offset + 30));
        entity.setIdStartDate(cursor.isNull(offset + 31) ? null : cursor.getString(offset + 31));
        entity.setIdEndDate(cursor.isNull(offset + 32) ? null : cursor.getString(offset + 32));
        entity.setIdPhoto1(cursor.isNull(offset + 33) ? null : cursor.getString(offset + 33));
        entity.setIdPhoto2(cursor.isNull(offset + 34) ? null : cursor.getString(offset + 34));
        entity.setIdApplyTime(cursor.isNull(offset + 35) ? null : cursor.getString(offset + 35));
        entity.setIdAuditTime(cursor.isNull(offset + 36) ? null : cursor.getString(offset + 36));
        entity.setIdAuditStatus(cursor.isNull(offset + 37) ? null : cursor.getInt(offset + 37));
        entity.setLicenceValidDate(cursor.isNull(offset + 38) ? null : cursor.getString(offset + 38));
        entity.setDriverLicencePhoto(cursor.isNull(offset + 39) ? null : cursor.getString(offset + 39));
        entity.setStatus(cursor.isNull(offset + 40) ? null : cursor.getInt(offset + 40));
        entity.setStatusDetailId(cursor.isNull(offset + 41) ? null : cursor.getString(offset + 41));
        entity.setLoginFlag(cursor.isNull(offset + 42) ? null : cursor.getInt(offset + 42));
        entity.setAuditRemark(cursor.isNull(offset + 43) ? null : cursor.getString(offset + 43));
        entity.setRemark(cursor.isNull(offset + 44) ? null : cursor.getString(offset + 44));
        entity.setUuid(cursor.isNull(offset + 45) ? null : cursor.getString(offset + 45));
        entity.setSingleToken(cursor.isNull(offset + 46) ? null : cursor.getString(offset + 46));
        entity.setDeviceToken(cursor.isNull(offset + 47) ? null : cursor.getString(offset + 47));
        entity.setMessageFlag(cursor.isNull(offset + 48) ? null : cursor.getInt(offset + 48));
        entity.setBackgroundPicture(cursor.isNull(offset + 49) ? null : cursor.getString(offset + 49));
        entity.setCreatedBy(cursor.isNull(offset + 50) ? null : cursor.getString(offset + 50));
        entity.setCreated(cursor.isNull(offset + 51) ? null : cursor.getString(offset + 51));
        entity.setUpdated(cursor.isNull(offset + 52) ? null : cursor.getString(offset + 52));
        entity.setIsValid(cursor.isNull(offset + 53) ? null : cursor.getInt(offset + 53));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(Driver entity, long rowId) {
        entity.setLid(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(Driver entity) {
        if(entity != null) {
            return entity.getLid();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(Driver entity) {
        return entity.getLid() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}