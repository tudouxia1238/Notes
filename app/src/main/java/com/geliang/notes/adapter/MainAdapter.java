package com.geliang.notes.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.geliang.notes.bean.SQLBean;
import com.geliang.notes.R;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class MainAdapter extends BaseAdapter {
	private List<SQLBean> list;
	private String type;
	private int setsum;
	private Context context;
	private LayoutInflater inflater;
	private DisplayMetrics dm;

	class Holder {
		public TextView tv_note_id, tv_note_title, tv_note_time,tv_locktype,tv_lock;
		public RelativeLayout ll_bg;
		public ImageView iv_imge, im_datatime;
	}

	public MainAdapter(List<SQLBean> list, Context context) {//动态装载日记信息到首页
		super();
		this.list = list;
		this.context = context;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	@Override
	public int getCount() {
		return list == null ? 0 : list.size();
	}
	@Override
	public Object getItem(int position) {
		return position;
	}
	@Override
	public long getItemId(int position) {
		return position;
	}
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		Holder holder;
		if (convertView == null) {
			holder = new Holder();
			convertView = inflater.inflate(R.layout.note_item, null);
			holder.tv_note_id = (TextView) convertView
					.findViewById(R.id.tv_note_id);
			holder.tv_locktype= (TextView) convertView
					.findViewById(R.id.tv_locktype);
			holder.tv_lock= (TextView) convertView
					.findViewById(R.id.tv_lock);
			holder.tv_note_title = (TextView) convertView
					.findViewById(R.id.tv_note_title);
			holder.tv_note_time = (TextView) convertView
					.findViewById(R.id.tv_note_time);
			holder.iv_imge = (ImageView) convertView.findViewById(R.id.iv_imge);
			holder.im_datatime = (ImageView) convertView
					.findViewById(R.id.im_datatime);
			holder.ll_bg = (RelativeLayout) convertView
					.findViewById(R.id.ll_bg);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		dm = context.getResources().getDisplayMetrics();//获得系统屏幕信息
		LinearLayout.LayoutParams imagebtn_params = new LinearLayout.LayoutParams(
				WindowManager.LayoutParams.WRAP_CONTENT,
				WindowManager.LayoutParams.WRAP_CONTENT);
		imagebtn_params.width = dm.widthPixels ;//
		imagebtn_params.height = dm.widthPixels / 2;//
		holder.ll_bg.setLayoutParams(imagebtn_params);
		holder.tv_note_id.setText(list.get(position).get_id());
		holder.tv_note_title.setText(list.get(position).getTitle());
		holder.tv_note_time.setText(list.get(position).getTime());
		holder.tv_locktype.setText(list.get(position).getLocktype());
		holder.tv_lock.setText(list.get(position).getLock());

		if (list.get(position).getDatatype().equals("0")) {//判断是否开启提醒功能
			holder.im_datatime.setVisibility(View.GONE);
		} else {
			holder.im_datatime.setVisibility(View.VISIBLE);
		}
		if ("0".equals(list.get(position).getLocktype())) {//判断是否上锁
			String context = list.get(position).getContext();
			// 定义正则表达式，用于匹配路径
			Pattern p = Pattern.compile("/([^\\.]*)\\.\\w{3}");
			Matcher m = p.matcher(context);
			while (m.find()) {
				String path = m.group().toString();
				String type = path.substring(path.length() - 3, path.length());
				Bitmap bm = null;
				Bitmap rbm = null;

					if (holder.iv_imge.getDrawable() == null) {
						FileInputStream f;
						try {
							f = new FileInputStream(m.group());
							BitmapFactory.Options options = new BitmapFactory.Options();
							options.inJustDecodeBounds = true;
							Bitmap bmp = BitmapFactory.decodeFile(m.group(),
									options);
							options.inSampleSize = options.outHeight / 100;
							options.inJustDecodeBounds = false;
							options.inDither = false;
							options.inPreferredConfig = null;
							options.inPurgeable = true;
							options.inInputShareable = true;
							bm = BitmapFactory.decodeFile(m.group(), options);
						} catch (FileNotFoundException e) {

							e.printStackTrace();
						}
						holder.iv_imge.setVisibility(View.VISIBLE);
						holder.tv_note_title.setLines(1);//一行
						holder.iv_imge.setImageBitmap(bm);
					}

			}
		} else {//若上锁
			holder.tv_note_title.setVisibility(View.GONE);
			holder.iv_imge.setVisibility(View.VISIBLE);
			holder.iv_imge.setBackgroundResource(R.drawable.note_bg);
		}
		return convertView;
	}

}
