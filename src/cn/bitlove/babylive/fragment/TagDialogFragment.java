package cn.bitlove.babylive.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import cn.bitlove.babylive.R;

/**
 * 标签输入
 *
 * @author luoaz
 */
public class TagDialogFragment extends DialogFragment {

    private OnClickListener mCancelListener;
    private OnClickListener mOKListener;
    private IDialog mIDialog;
    private EditText etTags;

    public TagDialogFragment() {
    }
    public TagDialogFragment(OnClickListener cancelListener, OnClickListener okListener) {
        if (cancelListener == null) {
            mCancelListener = new OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            };
        } else {
            mCancelListener = cancelListener;
        }

        if (okListener == null) {
            okListener = new OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            };
        } else {
            mOKListener = okListener;
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View content = inflater.inflate(R.layout.fragment_dialog_tag, null);
        etTags = (EditText) content.findViewById(R.id.etTags);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(content)
                .setMessage("请输入标签")
                .setPositiveButton(R.string.ok, mOKListener)
                .setNegativeButton(R.string.cancel, mCancelListener);

        return builder.create();
    }

    @Override
    public void onResume() {
        super.onResume();
        mIDialog.onShowConetent();
    }

    /**
     * 获取输入的Tag
     *
     * @return
     */
    public String getTags() {
        return etTags.getText().toString();
    }

    public void setTags(String tags){
        etTags.setText(tags);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    public void setmIDialog(IDialog iDialog) {
        mIDialog = iDialog;
    }
    public interface  IDialog{
      public void  onShowConetent();
    };
}
