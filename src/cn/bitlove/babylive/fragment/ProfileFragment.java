package cn.bitlove.babylive.fragment;

import cn.bitlove.babylive.R;
import cn.bitlove.babylive.activity.ProfileActivity;
import cn.bitlove.babylive.entity.Profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class ProfileFragment extends Fragment implements View.OnClickListener{

    View mView;
    RelativeLayout layoutName;
    RelativeLayout layoutSex;
    RelativeLayout layoutBirthDay;
    RelativeLayout layoutBirthTime;
    RelativeLayout layoutBirthWeight;
    View vNote;
    View btnEdit;


    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		mView = inflater.inflate(R.layout.fragment_profile, null);

        init();
		return mView;
	}
    /**
     * 初始化
     * */
    private void init(){
        layoutName = (RelativeLayout) mView.findViewById(R.id.layoutName);
        layoutName.setOnClickListener(this);
        layoutSex = (RelativeLayout) mView.findViewById(R.id.layoutSex);
        layoutSex.setOnClickListener(this);
        layoutBirthDay = (RelativeLayout) mView.findViewById(R.id.layoutBirthday);
        layoutBirthDay.setOnClickListener(this);
        layoutBirthTime = (RelativeLayout) mView.findViewById(R.id.layoutBirthTime);
        layoutBirthTime.setOnClickListener(this);
        layoutBirthWeight = (RelativeLayout) mView.findViewById(R.id.layoutBirthWeight);
        layoutBirthWeight.setOnClickListener(this);
        vNote = mView.findViewById(R.id.note);

        btnEdit=mView.findViewById(R.id.btnEdit);
        btnEdit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.layoutName:
                Toast.makeText(getActivity()," onClick ",Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnEdit:
                Intent intent = new Intent(ProfileFragment.this.getActivity(), ProfileActivity.class);
                startActivity(intent);
                break;
        }
    }
}
