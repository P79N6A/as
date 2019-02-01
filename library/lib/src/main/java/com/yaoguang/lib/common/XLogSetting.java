package com.yaoguang.lib.common;


import com.elvishew.xlog.LogLevel;
import com.elvishew.xlog.XLog;
import com.yaoguang.lib.BuildConfig;

/**
 * Project Name:driver
 * Created by weiliying
 * on 2017 2017/4/10.16:05
 *
 * 高级方式

 LogConfiguration config = new LogConfiguration.Builder()
 .logLevel(BuildConfig.DEBUG ? LogLevel.ALL             // 指定日志级别，低于该级别的日志将不会被打印，默认为 LogLevel.ALL
 : LogLevel.NONE)
 .tag("MY_TAG")                                         // 指定 TAG，默认为 "X-LOG"
 .t()                                                   // 允许打印线程信息，默认禁止
 .st(2)                                                 // 允许打印深度为2的调用栈信息，默认禁止
 .b()                                                   // 允许打印日志边框，默认禁止
 .jsonFormatter(new MyJsonFormatter())                  // 指定 JSON 格式化器，默认为 DefaultJsonFormatter
 .xmlFormatter(new MyXmlFormatter())                    // 指定 XML 格式化器，默认为 DefaultXmlFormatter
 .throwableFormatter(new MyThrowableFormatter())        // 指定可抛出异常格式化器，默认为 DefaultThrowableFormatter
 .threadFormatter(new MyThreadFormatter())              // 指定线程信息格式化器，默认为 DefaultThreadFormatter
 .stackTraceFormatter(new MyStackTraceFormatter())      // 指定调用栈信息格式化器，默认为 DefaultStackTraceFormatter
 .borderFormatter(new MyBoardFormatter())               // 指定边框格式化器，默认为 DefaultBorderFormatter
 .addObjectFormatter(AnyClass.class,                    // 为指定类添加格式化器
 new AnyClassObjectFormatter())                     // 默认使用 Object.toString()
 .addInterceptor(new BlacklistTagsFilterInterceptor(    // 添加黑名单 TAG 过滤器
 "blacklist1", "blacklist2", "blacklist3"))
 .addInterceptor(new MyInterceptor())                   // 添加一个日志拦截器
 .build();

 Printer androidPrinter = new AndroidPrinter();             // 通过 android.util.Log 打印日志的打印器
 Printer consolePrinter = new ConsolePrinter();             // 通过 System.out 打印日志到控制台的打印器
 Printer filePrinter = new FilePrinter                      // 打印日志到文件的打印器
 .Builder("/sdcard/xlog/")                              // 指定保存日志文件的路径
 .fileNameGenerator(new DateFileNameGenerator())        // 指定日志文件名生成器，默认为 ChangelessFileNameGenerator("log")
 .backupStrategy(new NeverBackupStrategy()              // 指定日志文件备份策略，默认为 FileSizeBackupStrategy(1024 * 1024)
 .logFlattener(new MyFlattener())                       // 指定日志平铺器，默认为 DefaultFlattener
 .build();

 XLog.init(                                                 // 初始化 XLog
 config,                                                // 指定日志配置，如果不指定，会默认使用 new LogConfiguration.Builder().build()
 androidPrinter,                                        // 添加任意多的打印器。如果没有添加任何打印器，会默认使用 AndroidPrinter(Android)/ConsolePrinter(java)
 consolePrinter,
 filePrinter);

 全局用法

 XLog.d("Simple message")
 XLog.d("My name is %s", "Elvis");
 XLog.d("An exception caught", exception);
 XLog.d(object);
 XLog.d(array);
 XLog.json(unformattedJsonString);
 XLog.xml(unformattedXmlString);

 基于单条日志的用法

 进行基于单条日志的配置，然后就可以直接打印日志了，所有打印日志的相关方法都跟 XLog 类里的一模一样。

 XLog.t()    // 允许打印线程信息
 .st(3)  // 允许打印深度为3的调用栈信息
 .b()    // 允许打印日志边框
 ...     // 其他配置
 .d("Simple message 1");

 XLog.tag("TEMP-TAG")
 .st(0)  // 允许打印不限深度的调用栈信息
 ...     // 其他配置
 .d("Simple message 2");

 XLog.nt()   // 禁止打印线程信息
 .nst()  // 禁止打印调用栈信息
 .d("Simple message 3");

 XLog.b().d("Simple message 4");

 带边框的 XLog
 XLog.init(LogLevel.ALL, new LogConfiguration.Builder().b().build());
 XLog.d("Message");
 XLog.d("Message withRounded argument: age=%s", 18);
 XLog.json(jsonString);
 XLog.xml(xmlString);
 XLog.st(5).d("Message withRounded stack trace info");

 更多查看：https://github.com/elvishew/xLog
 */

public class XLogSetting {

    public static void init() {
        //或者如果你想要在正式版中禁止打日志
        XLog.init(BuildConfig.DEBUG ? LogLevel.ALL : LogLevel.NONE);
    }

}
