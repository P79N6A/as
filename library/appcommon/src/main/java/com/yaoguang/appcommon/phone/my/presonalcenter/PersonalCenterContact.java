package com.yaoguang.appcommon.phone.my.presonalcenter;

import android.view.View;

import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.base.interfaces.BaseView;

import io.reactivex.Observable;

/**
 * 个人中心
 * Created by zhongjh on 2017/7/5.
 */
public class PersonalCenterContact {

    public interface PersonalCenterInteractor<T> {

        /**
         * 获取当前登录的个人信息
         *
         * @return 实体类
         */
        T getInfo();

        /**
         * 网络修改手机
         *
         * @param s 值
         * @return 服务器信息
         */
        Observable<BaseResponse<String>> modifyPhoto(String s);

        void modifySqlPhoto(String s);

        /**
         * 网络修改电话
         *
         * @param s 值
         * @return 服务器信息
         */
        Observable<BaseResponse<String>> modifyMobile(String s);

        /**
         * 本地修改手机
         *
         * @param s 值
         */
        void modifySqlMobile(String s);

        /**
         * 网络修改邮箱
         *
         * @param s 值
         * @return 服务器信息
         */
        Observable<BaseResponse<String>> modifyEmail(String s);

        /**
         * 本地修改邮箱
         *
         * @param s 值
         */
        void modifySqlEmail(String s);

        /**
         * 网络修改QQ
         *
         * @param s 值
         * @return 服务器信息
         */
        Observable<BaseResponse<String>> modifyQQ(String s);

        /**
         * 本地修改QQ
         *
         * @param s 值
         */
        void modifySqlQQ(String s);

        /**
         * 网络修改签名
         *
         * @param s 值
         * @return 服务器信息
         */
        Observable<BaseResponse<String>> modifySign(String s);

        /**
         * 网络修改昵称
         *
         * @param s 值
         * @return 服务器信息
         */
        Observable<BaseResponse<String>> modifyNickName(String s);

        /**
         * 本地修改签名
         *
         * @param s 值
         */
        void modifySqlSign(String s);

        /**
         * 修改密码
         *
         * @param id       id
         * @param newPass1 新密码
         * @param oldPass  旧密码
         * @return 服务器信息
         */
        Observable<BaseResponse<String>> changePassword(String id, String newPass1, String oldPass);

        /**
         * 修改手机号
         *
         * @param newPhone 新手机
         * @param code     验证码
         * @param password 旧手机
         * @return 服务器信息
         */
        Observable<BaseResponse<String>> modifyPhone(String newPhone, String code, String password);

        /**
         * 本地修改手机号
         *
         * @param newPhone 新手机
         * @param code     验证码
         */
        void modifySqlPhone(String newPhone, String code);

        /**
         * 修改背景图片
         *
         * @param s 背景参数
         * @return 返回的值
         */
        Observable<BaseResponse<String>> modifyBackgroundPicture(String s);
    }

    public interface PersonalCenterPresenter<T> extends BasePresenter {

        /**
         * 初始化数据
         */
        void initData();

        /**
         * 验证手机
         *
         * @param newMobile 新的手机号
         * @return 是否正确
         */
        boolean isRightPhone(String newMobile);

        /**
         * 修改图片
         *
         * @param s 值
         */
        void modifyPhoto(String s);

        /**
         * 修改背景图片
         *
         * @param s 参数
         */
        void modifyBackgroundPicture(String s);

        /**
         * 修改手机号
         *
         * @param s
         */
        void modifyMobile(String s);

        /**
         * 修改邮箱
         *
         * @param s 值
         */
        void modifyEmail(String s);

        /**
         * 修改qq
         *
         * @param s 值
         */
        void modifyQQ(String s);

        /**
         * 修改签名
         *
         * @param s 值
         */
        void modifySign(String s);


    }

    public interface PersonalCenterView<T> extends BaseView {

        /**
         * 显示个人信息
         *
         * @param info 实体
         */
        void showData(T info);

        /**
         * 取消dialog
         *
         * @param v
         */
        void cancleDialog(View v);

        /**
         * 跳转登录
         */
        void toLoginActivity();

        /**
         * 验证码倒数
         */
        void startCountDown();

        /**
         * 停止验证码
         */
        void stopCountDown();
    }

}
