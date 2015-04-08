package cn.bitlove.babylive.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.bitlove.babylive.R;
import cn.bitlove.babylive.fragment.ConfigFragment;
import cn.bitlove.babylive.fragment.ProfileFragment;
import cn.bitlove.babylive.fragment.RecordListFragment;
import cn.bitlove.babylive.fragment.TimeLineFragment;
import cn.bitlove.remind.ToastReminder;

public class MainFragmentActivity extends BaseFragmentActivity implements OnClickListener {
    View layoutRecords;
    View layoutCategory;
    View layoutAddNote;
    View layoutTags;
    View layoutConfig;

    Fragment recordFragment;
    Fragment categoryFrament;
    Fragment tagFragment;
    Fragment configFragment;

    List<View> viewsToChange = new ArrayList<View>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_fragment);
        init();
    }

    private void init() {
        layoutRecords = findViewById(R.id.layoutRecords);
        layoutRecords.setOnClickListener(this);
        layoutCategory = findViewById(R.id.layoutCategory);
        layoutCategory.setOnClickListener(this);
        layoutAddNote = findViewById(R.id.layoutAddNote);
        layoutAddNote.setOnClickListener(this);
        layoutTags = findViewById(R.id.layoutTags);
        layoutTags.setOnClickListener(this);
        layoutConfig = findViewById(R.id.layoutConfig);
        layoutConfig.setOnClickListener(this);

        viewsToChange.add(layoutRecords);
        viewsToChange.add(layoutCategory);
        viewsToChange.add(layoutTags);
        viewsToChange.add(layoutConfig);

        recordFragment = new RecordListFragment();
        categoryFrament = new TimeLineFragment();
        tagFragment = new ProfileFragment();
        configFragment = new ConfigFragment();
    }

    /**
     * 改变导航文本字体颜色
     *
     * @param view    ,要修改的view
     * @param isFocus boolean 是否为焦点状态
     */
    private void changeTextColor(View view, Boolean isFocus) {

        TextView label = (TextView) view.findViewWithTag("navLabel");
        if (isFocus) {
            label.setTextColor(getResources().getColor(R.color.green));
        } else {
            label.setTextColor(getResources().getColor(R.color.grey));
        }
    }

    /**
     * 切换导航背景图片以及内容fragment
     *
     * @param view    导航的view
     * @param isFocus boolean 是否为焦点状态
     */
    private void changeImgAndFragment(View view, Boolean isFocus) {
        ImageView imageView = (ImageView) view.findViewWithTag("navImg");
        if (isFocus) {
            switch (view.getId()) {
                case R.id.layoutRecords:
                    imageView.setImageResource(R.drawable.record_focus);
                    switchFragment(recordFragment);
                    break;
                case R.id.layoutCategory:
                    imageView.setImageResource(R.drawable.time_line_focus);
                    switchFragment(categoryFrament);
                    break;
                case R.id.layoutTags:
                    imageView.setImageResource(R.drawable.profile_focus);
                    switchFragment(tagFragment);
                    break;
                case R.id.layoutConfig:
                    imageView.setImageResource(R.drawable.config_focus);
                    switchFragment(configFragment);

                    break;
                default:
            }
        } else {
            switch (view.getId()) {
                case R.id.layoutRecords:
                    imageView.setImageResource(R.drawable.record_unfocus);
                    break;
                case R.id.layoutCategory:
                    imageView.setImageResource(R.drawable.time_line_unfocus);
                    break;
                case R.id.layoutTags:
                    imageView.setImageResource(R.drawable.profile_unfocus);
                    break;
                case R.id.layoutConfig:
                    imageView.setImageResource(R.drawable.config_unfocus);
                    break;
                case R.id.layoutAddNote:
                    Intent intent = new Intent(this, NewRecordActivity.class);
                    startActivity(intent);
                    break;
                default:

            }
        }
    }

    /**
     * 切换内容Fragment
     *
     * @param fragment 要切换的Fragment
     */
    private void switchFragment(Fragment fragment) {
        FragmentTransaction transaction = mFM.beginTransaction();
        transaction.replace(R.id.contentFragment, fragment);
        transaction.commit();
    }

    /**
     * 切换Nav
     */

    private void changeNav(View curView) {
        if (curView.getId() != layoutAddNote.getId()) {
            int size = viewsToChange.size();
            for (int i = 0; i < size; i++) {
                View temp = viewsToChange.get(i);
                if (temp.equals(curView)) {
                    changeTextColor(temp, true);
                    changeImgAndFragment(temp, true);
                } else {
                    changeTextColor(temp, false);
                    changeImgAndFragment(temp, false);
                }
            }
        } else {
            Intent intent = new Intent(this, NewRecordActivity.class);
            startActivity(intent);
        }

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        changeNav(v);
    }
    
    long lastTime=System.currentTimeMillis();
    final long exitDifTime = 1000;		//两次退出时间间隔
    @Override
    public void onBackPressed() {
    	//退出确认
    	long curTime = System.currentTimeMillis();
    	if((curTime - lastTime)>exitDifTime){
    		lastTime = curTime;
    		ToastReminder.showToast(this, "再次点击退出", Toast.LENGTH_LONG);
    	}else{
    		super.onBackPressed();
    	}
    }
}
