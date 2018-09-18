package com.wiliams.base.loglib;

import android.os.Handler;
import android.os.HandlerThread;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.CsvFormatStrategy;
import com.orhanobut.logger.DiskLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.LogAdapter;
import com.orhanobut.logger.LogStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

/**
 * @author haibin.yuan.o.
 * @date 2018/9/17.
 * - Description：
 */

public class CustomLogAdapter {
  /**
   * debug 日志adapter
   */
  private AndroidLogAdapter mDebugLogAdapter;

  /**
   * release 日志adapter
   */
  private DiskLogAdapter mReleaseLogAdapter;

  /**
   * 自定义日志输出配置
   */
  private CustomDiskLogStrategy customDiskLogStrategy;

  /**
   * release 本地日志输出策略
   */
  private FormatStrategy formatStrategyRelease;

  /**
   * @param customDiskLogStrategy 自定义打印策略
   */
  public CustomLogAdapter(CustomDiskLogStrategy customDiskLogStrategy) {
    this.customDiskLogStrategy = customDiskLogStrategy;
  }

  /**
   * customDiskLogStrategy.isDebug =true  >>> debug模式，只在logcat中打印
   *
   * 否则，就本地输出
   */
  public LogAdapter getCustomLogAdapter() {
    if (customDiskLogStrategy.isOutputLocal) {
      return getAndroidLogAdapter();
    } else {
      return getReleaseLogAdapter(customDiskLogStrategy.isOutputAll);
    }
  }

  /**
   * 获取 debug日志模式，进行日志调试
   */
  private AndroidLogAdapter getAndroidLogAdapter() {
    mDebugLogAdapter = new AndroidLogAdapter(PrettyFormatStrategy.newBuilder()
        // (Optional) Whether to show thread info or not. Default true
        .showThreadInfo(true)
        // (Optional) How many method line to show. Default 2
        .methodCount(0)
        // (Optional) Hides internal method calls up to offset. Default 5
        .methodOffset(7)
        .tag(customDiskLogStrategy.mTag)
        .build());
    return mDebugLogAdapter;
  }

  /**
   * 自定义本地日志输出策略
   */
  private void getDisLogStrtegy() {
    String folder = customDiskLogStrategy.mLogPath;
    HandlerThread ht = new HandlerThread("AndroidFileLogger." + folder);
    ht.start();
    Handler handler = new DiskLogStrategy.WriteHandler(ht.getLooper(), folder, customDiskLogStrategy.mLogPath,
        customDiskLogStrategy.mMax, customDiskLogStrategy.mPer);
    LogStrategy logStrategy = new DiskLogStrategy(handler);
    formatStrategyRelease = CsvFormatStrategy.newBuilder()
        .logStrategy(logStrategy)
        .tag(customDiskLogStrategy.mTag)
        .build();
  }

  /**
   * 日志策略
   *
   * @param isLoggerAll 日志 是否全部输出，
   * @return the logger strategy
   */
  private DiskLogAdapter getReleaseLogAdapter(boolean isLoggerAll) {
    //配置本地日志输出策略
    getDisLogStrtegy();
    if (isLoggerAll) {
      mReleaseLogAdapter = new DiskLogAdapter(formatStrategyRelease) {
        @Override
        public boolean isLoggable(int priority, String tag) {
          //打印所有日志等级
          return true;
        }
      };
    } else {
      mReleaseLogAdapter = new DiskLogAdapter(formatStrategyRelease) {
        @Override
        public boolean isLoggable(int priority, String tag) {
          //只输出error
          return (priority == Logger.ERROR);
        }
      };
    }
    return mReleaseLogAdapter;
  }

}
