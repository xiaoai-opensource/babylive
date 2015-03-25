package cn.bitlove.babylive.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

import cn.bitlove.babylive.R;

/**
 * 设置界面
 */
public class ConfigFragment extends Fragment implements OnClickListener {

    FragmentManager mFM;
    View mLayout;
    View layoutProfile;
    View layoutCheckUpdate;
    View layoutAbout;

    SoftReference<Fragment> profileFragment;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mLayout = inflater.inflate(R.layout.fragment_config,null);
        init();
        return mLayout;
    }

    private void init(){
        mFM = getFragmentManager();
        layoutProfile = mLayout.findViewById(R.id.layoutProfile);
        layoutProfile.setOnClickListener(this);
        layoutCheckUpdate = mLayout.findViewById(R.id.layoutCheckUpdate);
        layoutCheckUpdate.setOnClickListener(this);
        layoutAbout = mLayout.findViewById(R.id.layoutAbout);
        layoutAbout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.layoutProfile:
                if(profileFragment==null){
                    profileFragment = new SoftReference<Fragment>(new ProfileFragment());
                }
                startFragment(R.id.contentFragment,profileFragment.get());
                break;
            case R.id.layoutCheckUpdate:
                break;
            case R.id.layoutAbout:
                break;
        }
    }
    /**
     * 切换Fragment到指定的id区域
     * @param contentId int Fragment展示区域Id
     * @param fragment Fragment 要切换的Fragment
     * */
   private void startFragment(int contentId,Fragment fragment){
       FragmentTransaction transaction = mFM.beginTransaction();
       transaction.setCustomAnimations(R.anim.slide_in_from_right,R.anim.slide_out_to_left,0,android.R.anim.slide_out_right);
       transaction.replace(contentId, fragment);
       transaction.addToBackStack(null);
       transaction.commit();
   }
}
