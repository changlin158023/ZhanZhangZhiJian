package com.wlhl.zhanzhangzhijia;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;

public class DetailActivity extends Activity implements OnClickListener {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);
		initUI();
	}
	private void initUI() {
		ImageView tvback = (ImageView) findViewById(R.id.tvback);
		ListView lv = (ListView) findViewById(R.id.listView1);
		Ladapter adapter=new Ladapter();
		lv.setAdapter(adapter);
		tvback.setOnClickListener(this);
	}
	class Ladapter extends BaseAdapter{
		@Override
		public int getCount() {
			return 10;
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
			inflate = getLayoutInflater().inflate(R.layout.listitem_detail, null);
			}else{
			inflate=convertView;
			}
			return inflate; 
		}
		@Override
		public int getItemViewType(int position) {
			return position;   
		}
		@Override
		public int getViewTypeCount() {
			return 10;
		}
	}
	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.tvback:
			finish();
		break;
		case R.id.tvclose:
			break;
		case R.id.tvsetting:
//			Builder builder = new AlertDialog.Builder(DetailActivity.this);
//			dialog= builder.show();
//			Window window = dialog.getWindow();
//			View view=getLayoutInflater().inflate(R.layout.dialog_rechargehistory,null);//dialog自定义布局  
//			TextView tvclose = (TextView) view.findViewById(R.id.tvclose);
//			tvclose.setOnClickListener(this);
//			window.setGravity(Gravity.TOP|Gravity.FILL_HORIZONTAL);
//			window.setContentView(view);
			break;
		}
	}
}
