package cn.bitlove.babylive.fragment;

import cn.bitlove.babylive.R;
import cn.bitlove.babylive.activity.ProfileActivity;
import cn.bitlove.babylive.data.ProfileData;
import cn.bitlove.babylive.entity.Profile;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ProfileFragment extends Fragment implements View.OnClickListener{

    View mView;
    TextView viewName;
    TextView viewSex;
    TextView viewBirthDay;
    TextView viewBirthTime;
    TextView viewBirthWeight;
    TextView vNote;
    View btnEdit;


    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		mView = inflater.inflate(R.layout.fragment_profile, null);

        init();
		return mView;
	}
    @Override
    public void onResume() {
    	super.onResume();
    	init();
    }
    /**
     * 初始化
     * */
    private void init(){
        viewName =  (TextView) mView.findViewById(R.id.name);
        viewSex =  (TextView) mView.findViewById(R.id.sex);
        viewBirthDay =  (TextView) mView.findViewById(R.id.birthday);
        viewBirthTime =  (TextView) mView.findViewById(R.id.birthTime);
        viewBirthWeight =  (TextView) mView.findViewById(R.id.birthWeight);
        vNote = (TextView) mView.findViewById(R.id.note);
        btnEdit=mView.findViewById(R.id.btnEdit);
        btnEdit.setOnClickListener(this);
        
        Profile profile = getProfile();
        if(profile!=null){
        	viewName.setText(profile.getName());
        	viewSex.setText(profile.getSex());
        	viewBirthDay.setText(profile.getBirthday());
        	viewBirthTime.setText(profile.getBirthTime());
        	viewBirthWeight.setText(String.valueOf(profile.getWeight()));
        	vNote.setText(profile.getNote());
        }
    }
    /**
     * 获取当前用户信息
     * */
    private Profile getProfile(){
    	return ProfileData.queryProfile(this.getActivity());
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnEdit:
                Intent intent = new Intent(ProfileFragment.this.getActivity(), ProfileActivity.class);
                startActivity(intent);
                break;
        }
    }
}
