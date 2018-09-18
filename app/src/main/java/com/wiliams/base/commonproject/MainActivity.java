package com.wiliams.base.commonproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import com.orhanobut.logger.Logger;
//import com.yanzhenjie.permission.Action;
//import com.yanzhenjie.permission.AndPermission;
//import com.yanzhenjie.permission.Permission;
//import java.util.List;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    findViewById(R.id.logger).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        Logger.d("==== logger 集成");

        //AndPermission.with(MainActivity.this).runtime().permission(Permission.Group.STORAGE)
        //    .onGranted(new Action<List<String>>() {
        //      @Override
        //      public void onAction(List<String> data) {
        //        Logger.d("===  同意");
        //      }
        //    }).onDenied(new Action<List<String>>() {
        //  @Override
        //  public void onAction(List<String> data) {
        //    Logger.d("=== 拒绝");
        //  }
        //}).start();
      }
    });
  }
}
