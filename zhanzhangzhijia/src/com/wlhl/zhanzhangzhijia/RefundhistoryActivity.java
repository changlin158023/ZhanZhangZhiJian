package com.wlhl.zhanzhangzhijia;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class RefundhistoryActivity extends Activity implements OnClickListener {
	private AlertDialog dialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_refundhistory);
		initUI();
	}
	private void initUI() {
		ImageView tvback = (ImageView) findViewById(R.id.tvback);
		TextView tvsetting = (TextView) findViewById(R.id.tvsetting);
		ListView lv = (ListView) findViewById(R.id.listView1);
		Ladapter adapter=new Ladapter();
		lv.setAdapter(adapter);
		tvsetting.setOnClickListener(this);
		tvback.setOnClickListener(this);
	}
	class Ladapter extends BaseAdapter{
		TextView tv=null;
		@Override
		public int getCount() {
			return 30;
		}
		@Override
		public Object getItem(int position) {
			return null;
		}
		@Override
		public long getItemId(int position) {
			return 0;
		}
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View inflate;
			if(convertView==null){
			inflate = getLayoutInflater().inflate(R.layout.listitem_refundhistory, null);
			tv = (TextView) inflate.findViewById(R.id.textView5);
			}else{
			inflate=convertView;
			}
			if(position==0){
				tv.setVisibility(View.VISIBLE);
			}else{
				tv.setVisibility(View.GONE);
			}
			return inflate; 
		}
		@Override
		public int getItemViewType(int position) {
			return position;   
		}
		@Override
		public int getViewTypeCount() {
			return 30;
		}
	}
	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.tvback:
			finish();
		break;
		case R.id.tvclose:
			dialog.dismiss();
			break;
		case R.id.tvsetting:
			Builder builder = new AlertDialog.Builder(RefundhistoryActivity.this);
			dialog= builder.show();
			Window window = dialog.getWindow();
			View view=getLayoutInflater().inflate(R.layout.dialog_refundhistory,null);//dialog自定义布局  
			TextView tvclose = (TextView) view.findViewById(R.id.tvclose);
			tvclose.setOnClickListener(this);
			window.setGravity(Gravity.TOP|Gravity.FILL_HORIZONTAL);
			window.setContentView(view);
			break;
		}
	}
}
