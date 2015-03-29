package proj.synteam.easybtc;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.TextView;

public class Activity_Tab1 extends Activity {

	public static TextView tv;
	public static Context context_Tab1;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		setContentView(R.layout.activity_tab1);
		
		tv = (TextView)findViewById(R.id.textView1);
		context_Tab1 = this;

	}
	public boolean onKeyDown(int keyCode, KeyEvent event){
//		if((keyCode == KeyEvent.KEYCODE_BACK) && (e_to_eDic.canGoBack())){
//			e_to_eDic.goBack();
//			return true;
//		}
		return super.onKeyDown(keyCode, event);
	}
}