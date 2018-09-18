package com.wiliams.base.loglib;

import android.os.Environment;

/**
 * @author haibin.yuan.o.
 * @date 2018/9/17.
 * - Description：
 */

public class CustomDiskLogStrategy {
  public boolean isOutputLocal;
  public boolean isOutputAll;
  public String  mTag;
  public String  mLogPath;
  public int     mMax;
  public int     mPer;
  /**
   * 默认 release包， 非调试模式
   */
  public CustomDiskLogStrategy() {
    this(false, false, "wiliams", Environment.getExternalStorageDirectory()
        .getAbsolutePath() + "/" + "com.wiliams.log/" + "/" + "logger", 2 * 1024 * 1024, 400 * 1024);
  }

  /**
   * @param isOutputLocal 是否输出到本地
   * 默认非调试模式 > 只输出error日志
   */
  public CustomDiskLogStrategy(boolean isOutputLocal) {
    this(isOutputLocal, false, "wiliams", Environment.getExternalStorageDirectory()
        .getAbsolutePath() + "/" + "com.wiliams.log/" + "/" + "logger", 2 * 1024 * 2014, 400 * 1024);
  }

  /**
   * @param isOutputLocal 是否输出到本地
   * @param isOutputAll 是否
   */
  public CustomDiskLogStrategy(boolean isOutputLocal, boolean isOutputAll) {
    this(isOutputLocal, isOutputAll, "wiliams", Environment.getExternalStorageDirectory()
        .getAbsolutePath() + "/" + "com.wiliams.log/" + "/" + "logger", 2 * 1024 * 1024, 400 * 1024);
  }

  /**
   * @param isOutputLocal 是否输出到本地
   * @param isOutputAll debug调试模式
   * @param mTag log名称
   * @param mLogPath log输出日志路径
   * @param mMax 日志保存大小
   * @param mPer 每个日志文件的大小
   */
  public CustomDiskLogStrategy(boolean isOutputLocal, boolean isOutputAll, String mTag, String mLogPath, int mMax, int mPer) {
    this.mTag = mTag;
    this.mLogPath = mLogPath;
    this.mMax = mMax;
    this.mPer = mPer;
    this.isOutputLocal = isOutputLocal;
    this.isOutputAll = isOutputAll;
  }


}
