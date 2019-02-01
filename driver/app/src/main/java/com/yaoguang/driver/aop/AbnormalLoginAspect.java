package com.yaoguang.driver.aop;

import com.yaoguang.common.base.BaseApplication;
import com.yaoguang.common.common.Constants;
import com.yaoguang.common.common.SPUtils;
import com.yaoguang.common.common.ULog;
import com.yaoguang.common.net.bean.BaseResponse;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * =====================================
 * 作    者: 韦理英
 * 版    本：1.2.9
 * 创建日期：2018/01/31
 * 描    述：
 * 这是异常登录处理，主要判断服务关返回的107，就是有人登录这个帐号
 * 并退出提示
 * =====================================
 */

@Aspect
public class AbnormalLoginAspect {

    @Pointcut("execution(* io.reactivex.functions.*.*(..))")//方法切入点
    public void methodAnnotated() {
    }

    @Around("methodAnnotated()")//在连接点进行方法替换
    public void aroundJoinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
        ULog.i("AbnormalLoginAspect"+joinPoint.getSignature().getDeclaringTypeName());
        for (Object obj : joinPoint.getArgs()) {
            if (obj instanceof BaseResponse) {
                BaseResponse response = (BaseResponse) obj;
                ULog.i("AbnormalLoginAspect response getState="+response.getState());
                if (response.getState().equals("107")) {
                    // 判断如果已经不是自动登录了，就不需要重启了
                    if (!SPUtils.getInstance().getBoolean(Constants.AUTOLOGIN))
                        return;

                    // 清除token缓存
                    SPUtils.getInstance().put(Constants.TOKEN_ID, "");
                    SPUtils.getInstance().put(Constants.TOKEN_TOKEN, "");
                    // 自动清除自动登录
                    SPUtils.getInstance().put(Constants.USERNAME, "");
//                    SPUtils.getInstance().put(Constants.PASSWORD, "");
                    SPUtils.getInstance().put(Constants.AUTOLOGIN, false);

                    // 打开登录
                    BaseApplication.getInstance().startLogin(response.getMessage());
                    // 全部关闭
                    BaseApplication.getInstance().finishAllActivity();
                    return;
                }
            }
        }
        joinPoint.proceed();
    }
}
