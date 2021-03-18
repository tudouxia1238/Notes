package com.geliang.notes.data;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.geliang.notes.bean.SQLBean;
import com.geliang.notes.db.DatabaseOperation;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class CallAlarm extends BroadcastReceiver {
	private SQLiteDatabase db;
	private DatabaseOperation dop;
	@Override
	public void onReceive(Context context, Intent intent) {
		SimpleDateFormat formatter = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm");
		Date curDate = new Date(System.currentTimeMillis());
		String str = formatter.format(curDate);
		dop = new DatabaseOperation(context, db);
		dop.create_db();//打开数据库
		Cursor cursor = dop.query_db();//select查询全部
		if (cursor.getCount() > 0) {
			List<SQLBean> list = new ArrayList<SQLBean>();//实例化数据列表
			while (cursor.moveToNext()) {// 光标移动成功
				SQLBean bean = new SQLBean();//创建数据库实体类
				//保存日记信息id到实体类
				bean.set_id("" + cursor.getInt(cursor.getColumnIndex("_id")));
				//保存日记内容到实体类
				bean.setContext(cursor.getString(cursor
						.getColumnIndex("context")));
				//保存日记标题到实体类
				bean.setTitle(cursor.getString(cursor.getColumnIndex("title")));
				//保存日记记录时间到实体类
				bean.setTime(cursor.getString(cursor.getColumnIndex("time")));
				//保存日记是否设置提醒时间到实体类
				bean.setDatatype(cursor.getString(cursor
						.getColumnIndex("datatype")));
				//保存日记提醒时间到实体类
				bean.setDatatime(cursor.getString(cursor
						.getColumnIndex("datatime")));
				//保存日记是否设置了日记锁到实体类
				bean.setLocktype(cursor.getString(cursor
						.getColumnIndex("locktype")));
				//保存日记锁秘密到实体类
				bean.setLock(cursor.getString(cursor.getColumnIndex("lock")));
				if (bean.getDatatype().equals("1")&&str.equals(bean.getDatatime())) {//若设置了到时提醒且当前时间与提醒时间一致
					dop.update_db(bean.getTitle(), bean.getContext(), bean.getTime(), "0", "0",bean.getLocktype() ,bean.getLock(),Integer.parseInt(bean.get_id()));
					Intent myIntent = new Intent(context, AlarmAlert.class);//跳转到闹钟播放界面
					Bundle bundleRet = new Bundle();
					bundleRet.putString("remindMsg", bean.getTitle());
					bundleRet.putBoolean("shake", true);
					bundleRet.putBoolean("ring", true);
					myIntent.putExtras(bundleRet);
					myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					context.startActivity(myIntent);
				}
			}
		}
		dop.close_db();
	}
}
