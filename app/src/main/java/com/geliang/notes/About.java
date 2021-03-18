package com.geliang.notes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.geliang.notes.R;

//关于页面
public class About extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
    }

    //返回键功能
    public void onBack(View v) {
        About.this.finish();//关闭关于页面
    }

    //修改登录密码
    public void sheZhi(View v){
        startActivity(new Intent(About.this,XiugaimimaActivity.class));
    }
}
