package com.wiliams.base.commonproject.base;

import android.app.Application;
import com.orhanobut.logger.Logger;
import com.wiliams.base.loglib.CustomDiskLogStrategy;
import com.wiliams.base.loglib.CustomLogAdapter;

/**
 * @author haibin.yuan.o.
 * @date 2018/9/17.
 * - Description：
 */

public class BaseApplication extends Application {
  @Override
  public void onCreate() {
    super.onCreate();
    CustomDiskLogStrategy customDiskLogStrategy = new CustomDiskLogStrategy(false,true);
    Logger.addLogAdapter(new CustomLogAdapter(customDiskLogStrategy).getCustomLogAdapter());
  }
}
