package proj.synteam.easybtc;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;

public class Activity_Tab3 extends Activity {
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		setContentView(R.layout.activity_tab3);

	}
	public boolean onKeyDown(int keyCode, KeyEvent event){
//		if((keyCode == KeyEvent.KEYCODE_BACK) && (e_to_eDic.canGoBack())){
//			e_to_eDic.goBack();
//			return true;
//		}
		return super.onKeyDown(keyCode, event);
	}
}