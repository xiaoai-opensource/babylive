package cn.bitlove.babylive.fragment;

import java.util.ArrayList;
import java.util.List;

import cn.bitlove.babylive.R;
import cn.bitlove.babylive.activity.TagRecordsActivity;
import cn.bitlove.babylive.data.TagData;
import cn.bitlove.babylive.entity.TagCate;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 标签Fragment
 * @author luoaz
 *
 */
public class TagsFragment extends Fragment {

	Context mContext;
	LayoutInflater mInflater;
	View mMainView;
	ListView mListView;
	List<TagCate> mTagCates = new ArrayList<TagCate>();
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mMainView = inflater.inflate(R.layout.fragment_tags, container,false);
		mInflater = inflater;
		init();
		return mMainView;
	}
	
	private void init(){
		mContext = getActivity();
		mListView = (ListView) mMainView.findViewById(R.id.tagsListView);
		initTagCateDatas();
		initListView();
	}
	/**
	 * 初始化listview数据
	 */
	private void initTagCateDatas(){
		mTagCates = TagData.totalTagCate(mContext);
		Log.i("s","s");
	}
	/**
	 * 初始化Listview
	 */
	private void initListView(){
		mListView.setAdapter(new BaseAdapter() {
			
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				TagCate tc = mTagCates.get(position);
				View itemView = convertView;
				ViewHolder vh = new ViewHolder();
				
				if(itemView==null){
					itemView = mInflater.inflate(R.layout.item_tags_listview, parent,false);
					vh.tvTag = (TextView) itemView.findViewById(R.id.tvTag);
					vh.tvTagCount = (TextView) itemView.findViewById(R.id.tvTagCount);
				}else{
					vh = (ViewHolder) itemView.getTag();
				}
				vh.tvTag.setText(tc.tagName);
				vh.tvTagCount.setText(String.valueOf(tc.tagCount));
				
				return itemView;
			}
			
			@Override
			public long getItemId(int position) {
				return position;
			}
			
			@Override
			public Object getItem(int position) {
				return mTagCates.get(position);
			}
			
			@Override
			public int getCount() {
				return mTagCates.size();
			}
		});
		
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				TagCate tagCate = (TagCate) mListView.getItemAtPosition(position);
				Intent intent = new Intent(getActivity(),TagRecordsActivity.class);
				intent.putExtra("tag", tagCate.tagName);
				startActivity(intent);
			}
		});
	}

	class ViewHolder{
		TextView tvTag;
		TextView tvTagCount;
	}
}
