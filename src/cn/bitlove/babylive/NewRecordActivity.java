package cn.bitlove.babylive;

import java.util.Calendar;

import cn.bitlove.babylive.R;
import cn.bitlove.babylive.database.SQLiteManager;
import cn.bitlove.babylive.entity.Record;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class NewRecordActivity extends BaseActivity {
	private Button btnSaveRecord;
	private TextView etDate;
	private EditText etContent;
	private SQLiteManager mManager ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_record);
		init();
		//mManager.queryRecords();
		System.out.println("record ... oncreate");
		btnSaveRecord.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Record record = new Record();
				record.setActionDate(etDate.getText().toString());
				record.setContent(etContent.getText().toString());
				Calendar calendar = Calendar.getInstance();
				String createDate = calendar.get(Calendar.YEAR)+"-"+calendar.get(Calendar.MONTH)+"-"+calendar.get(Calendar.DAY_OF_MONTH);
				record.setCreateDate(createDate);
				record.setUserName("");
				System.out.println("into save");
				mManager.saveRecord(record);

			}
		});

	}
	private void init(){
		mContext=this;
		mManager = SQLiteManager.getInstance(mContext);
		btnSaveRecord = (Button)findViewById(R.id.menu_save_Record);
		etDate = (TextView)findViewById(R.id.date);
		etContent = (EditText)findViewById(R.id.content);
	}


}
