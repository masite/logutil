package com.wiliams.base.loglib;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author haibin.yuan.o.
 * @date 2018/9/17.
 * - Description：
 */

public class FileUtils {

  private FileUtils() {
    throw new UnsupportedOperationException("u can't instantiate me...");
  }

  private static final String LINE_SEP = System.getProperty("line.separator");

  private static boolean isSpace(final String s) {
    if (s == null) {
      return true;
    }
    for (int i = 0, len = s.length(); i < len; ++i) {
      if (!Character.isWhitespace(s.charAt(i))) {
        return false;
      }
    }
    return true;
  }

  /**
   * 根据文件路径获取文件
   *
   * @param filePath 文件路径
   * @return 文件
   */
  public static File getFileByPath(final String filePath) {
    return isSpace(filePath) ? null : new File(filePath);
  }

  /**
   * 获取目录长度
   *
   * @param dirPath 目录路径
   * @return 目录长度
   */
  public static long getDirLength(final String dirPath) {
    return getDirLength(getFileByPath(dirPath));
  }

  /**
   * 获取目录长度
   *
   * @param dir 目录
   * @return 目录长度
   */
  public static long getDirLength(final File dir) {
    if (!isDir(dir)) {
      return -1;
    }
    long len = 0;
    File[] files = dir.listFiles();
    if (files != null && files.length != 0) {
      for (File file : files) {
        if (file.isDirectory()) {
          len += getDirLength(file);
        } else {
          len += file.length();
        }
      }
    }
    return len;
  }

  /**
   * 判断是否是目录
   *
   * @param dirPath 目录路径
   * @return {@code true}: 是<br>{@code false}: 否
   */
  public static boolean isDir(final String dirPath) {
    return isDir(getFileByPath(dirPath));
  }

  /**
   * 判断是否是目录
   *
   * @param file 文件
   * @return {@code true}: 是<br>{@code false}: 否
   */
  public static boolean isDir(final File file) {
    return file != null && file.exists() && file.isDirectory();
  }

  /**
   * 删除文件
   *
   * @param srcFilePath 文件路径
   * @return {@code true}: 删除成功<br>{@code false}: 删除失败
   */
  public static boolean deleteFile(final String srcFilePath) {
    return deleteFile(getFileByPath(srcFilePath));
  }

  /**
   * 删除文件
   *
   * @param file 文件
   * @return {@code true}: 删除成功<br>{@code false}: 删除失败
   */
  public static boolean deleteFile(final File file) {
    return file != null && (!file.exists() || file.isFile() && file.delete());
  }

  /**
   * 获取目录下所有文件
   * <p>不递归进子目录</p>
   *
   * @param dirPath 目录路径
   * @return 文件链表
   */
  public static List<File> listFilesInDir(final String dirPath) {
    return listFilesInDir(dirPath, false);
  }

  /**
   * 获取目录下所有文件
   *
   * @param dirPath 目录路径
   * @param isRecursive 是否递归进子目录
   * @return 文件链表
   */
  public static List<File> listFilesInDir(final String dirPath, final boolean isRecursive) {
    return listFilesInDir(getFileByPath(dirPath), isRecursive);
  }

  /**
   * 获取目录下所有文件
   *
   * @param dir 目录
   * @param isRecursive 是否递归进子目录
   * @return 文件链表
   */
  public static List<File> listFilesInDir(final File dir, final boolean isRecursive) {
    return listFilesInDirWithFilter(dir, new FileFilter() {
      @Override
      public boolean accept(File pathname) {
        return true;
      }
    }, isRecursive);
  }

  /**
   * 获取目录下所有过滤的文件
   *
   * @param dir 目录
   * @param filter 过滤器
   * @param isRecursive 是否递归进子目录
   * @return 文件链表
   */
  public static List<File> listFilesInDirWithFilter(final File dir, final FileFilter filter,
                                                    final boolean isRecursive) {
    if (!isDir(dir)) {
      return null;
    }
    List<File> list = new ArrayList<>();
    File[] files = dir.listFiles();
    if (files != null && files.length != 0) {
      for (File file : files) {
        if (filter.accept(file)) {
          list.add(file);
        }
        if (isRecursive && file.isDirectory()) {
          //noinspection ConstantConditions
          list.addAll(listFilesInDirWithFilter(file, filter, true));
        }
      }
    }
    return list;
  }

}
