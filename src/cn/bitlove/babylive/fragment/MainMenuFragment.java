package cn.bitlove.babylive.fragment;

import cn.bitlove.babylive.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MainMenuFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		return inflater.inflate(R.layout.menu_primary, container);
		//return super.onCreateView(inflater, container, savedInstanceState);
	}
}
