# loglib
Control the log output address
基于 compile "com.orhanobut:logger:2.1.1" 的改善

```
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	    }
  
  		dependencies {
	        compile 'com.github.masite:logutil:1.0.0'
	        }

```

```
  /**
   * @param isOutputLocal 是否输出到logcat
   * @param isOutputAll 是否输出所有日志 isOutputLocal为true时设置无效
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

```

```
比如：输出到本地文件，打印所有log
 CustomDiskLogStrategy customDiskLogStrategy = new CustomDiskLogStrategy(false,true);
    Logger.addLogAdapter(new CustomLogAdapter(customDiskLogStrategy).getCustomLogAdapter());
```
