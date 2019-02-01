/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.yaoguang.driver.util;

import android.util.Log;

import com.yaoguang.lib.common.ULog;

import java.util.concurrent.TimeUnit;

/**
 * Class representing a StopWatch for measuring time.
 */
public class StopWatch {
  private long startTime;
  private long endTime;
  private long elapsedTime;
  public static int Accuracy = 1; // 1：毫秒   2：微秒
  public StopWatch() {
    //empty
  }

  private void reset() {
    startTime = 0;
    endTime = 0;
    elapsedTime = 0;
  }

  public void start() {
    reset();
    startTime = System.nanoTime();
  }

  public void stop() {
    if (startTime != 0) {
      endTime = System.nanoTime();
      elapsedTime = endTime - startTime;
    } else {
      reset();
    }
  }
  public void getTotalTime(){
    getTotalTime(1);
  }

  public void getTotalTime(int type){
    if (type == 1){
      ULog.i("TotalTimeMillis=" + getTotalTimeMillis());
    } else{
      ULog.i("TotalTimeMicros=" + getTotalTimeMicros());
    }
  }
  public double getTotalTimeMicros() {
    return (elapsedTime != 0) ? (endTime - startTime) /1000.0 : 0;
  }
  public double getTotalTimeMillis(){
    return (elapsedTime != 0) ? TimeUnit.NANOSECONDS.toMicros(endTime - startTime) /1000.0: 0;
  }
}
