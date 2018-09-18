package com.wiliams.base.loglib;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.orhanobut.logger.LogStrategy;
import com.orhanobut.logger.Logger;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @author haibin.yuan.o.
 * @date 2018/9/17.
 * - Description：
 */

public class DiskLogStrategy implements LogStrategy {

  private final Handler handler;

  public DiskLogStrategy(Handler handler) {
    this.handler = handler;
  }

  @Override
  public void log(int level, String tag, String message) {
    // do nothing on the calling thread, simply pass the tag/msg to the background thread
    handler.sendMessage(handler.obtainMessage(level, message));
  }

  public static class WriteHandler extends Handler {

    private final String folder;
    private final int    maxFileSize;
    private final int    mPerFilesSize;
    private String mLogPath = null;

    public WriteHandler(Looper looper, String folder, String mLogPath, int maxFileSize, int mPerFilesSize) {
      super(looper);
      this.folder = folder;
      this.maxFileSize = maxFileSize;
      this.mPerFilesSize = mPerFilesSize;
      this.mLogPath = mLogPath;
    }

    @SuppressWarnings("checkstyle:emptyblock")
    @Override
    public void handleMessage(Message msg) {
      String content = (String) msg.obj;

      FileWriter fileWriter = null;
      File logFile = getLogFile(folder, "logs");

      try {
        fileWriter = new FileWriter(logFile, true);
        writeLog(fileWriter, content);

        fileWriter.flush();
        fileWriter.close();
      } catch (IOException e) {
        if (fileWriter != null) {
          try {
            fileWriter.flush();
            fileWriter.close();
          } catch (IOException e1) { /* fail silently */ }
        }
      }
    }

    /**
     * This is always called on a single background thread.
     * Implementing classes must ONLY write to the fileWriter and nothing more.
     * The abstract class takes care of everything else including close the stream and catching
     * IOException
     *
     * @param fileWriter an instance of FileWriter already initialised to the correct file
     */
    private void writeLog(FileWriter fileWriter, String content) throws IOException {
      fileWriter.append(content + "<br>");
    }

    private File getLogFile(String folderName, String fileName) {

      File folder = new File(folderName);
      if (!folder.exists()) {
        //TODO: What if folder is not created, what happens then?
        try {
          folder.mkdirs();
        } catch (Exception e) {
          Logger.e(e.toString());
          e.printStackTrace();
        }
      }

      int newFileCount = 0;
      File newFile;
      File existingFile = null;
      //log文件命名为 logs_2017-12-20_0
      newFile = new File(folder, String.format("%s_%s_%s.html", fileName, getNowYMDData(), newFileCount));
      while (newFile.exists()) {
        existingFile = newFile;
        newFileCount++;
        newFile = new File(folder, String.format("%s_%s_%s.html", fileName, getNowYMDData(), newFileCount));
      }
      //如果所有文件大小超过上限，删掉最早的
      if (FileUtils.getDirLength(mLogPath) >= maxFileSize) {
        List<File> files = FileUtils.listFilesInDir(mLogPath);
        FileUtils.deleteFile(files.get(0));
      }
      //超过单个文件大小
      if (existingFile != null) {
        if (existingFile.length() >= mPerFilesSize) {
          //如果单个log文件超出上限，输出新的log文件
          return newFile;
        }
        //否则还在之前的文件输出
        return existingFile;
      }
      return newFile;
    }
  }

  /**
   * 获取日期  年-月-日
   */
  public static String getNowYMDData() {
    SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    String date = sDateFormat.format(new java.util.Date());
    return date;
  }
}

