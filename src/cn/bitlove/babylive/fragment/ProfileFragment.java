package cn.bitlove.babylive.fragment;

import cn.bitlove.babylive.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ProfileFragment extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
        /**
         * 初始化
         * */
		View fragmentView = inflater.inflate(R.layout.fragment_profile, null);

		return fragmentView;
	}
}
