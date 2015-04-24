package cn.bitlove.babylive.widget;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import cn.bitlove.babylive.R;
import cn.bitlove.babylive.entity.Record;

/**
 * 分类列表适配器
 * @author luoaz
 */
public class CategoryListAdapter extends BaseAdapter {

	LayoutInflater mInflater;
	List<Record> mRecords = new ArrayList<Record>();
	String mLastCate="";
	//显示标题的位置
	HashMap<Integer, Object> mCatePositions=new HashMap<Integer, Object>();
	
	public CategoryListAdapter(Context context,List<Record> records) {
		mRecords = records;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return mRecords.size();
	}

	@Override
	public Object getItem(int position) {
		return mRecords.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Record record = mRecords.get(position);
		String cate = record.getCreateDate();
		ViewHolder vh;
		
		if(convertView==null){
			vh = new ViewHolder();
			convertView = mInflater.inflate(R.layout.category_item, parent, false);
			vh.tvCate = (TextView) convertView.findViewById(R.id.tvCate);
			vh.tvContent = (TextView) convertView.findViewById(R.id.tvContent);
			vh.layoutCate = convertView.findViewById(R.id.layoutCate);
			if(!mLastCate.equals(cate)){
				mCatePositions.put(position,true);
			}
			
			convertView.setTag(vh);
		}else{
			vh = (ViewHolder) convertView.getTag();
		}
		
		if(mCatePositions.get(position)==null){
			vh.layoutCate.setVisibility(View.GONE);
		}else{
			mLastCate = cate;
			vh.tvCate.setText(cate);
			vh.layoutCate.setVisibility(View.VISIBLE);
		}
		StringBuilder contentSB = new StringBuilder();
		contentSB.append("<b>");
		contentSB.append(record.getTitle());
		contentSB.append("</b>");
		contentSB.append("<br>");
		contentSB.append(record.getContent());
		
		Spanned content = Html.fromHtml(contentSB.toString());
		vh.tvContent.setText(content);
		
		return convertView;
	}
	
	class ViewHolder{
		public View layoutCate;
		public TextView tvCate;
		public TextView tvContent;
	}

}
