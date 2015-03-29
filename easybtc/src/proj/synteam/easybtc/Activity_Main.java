package proj.synteam.easybtc;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.TabHost;

public class Activity_Main extends TabActivity {

	public static Context context_Main;
	public static boolean shut;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);  
		setContentView(R.layout.activity_main);	

		context_Main = Activity_Main.this;
		TabHost tabHost = getTabHost(); //탭메뉴 액티비티 생성
		TabHost.TabSpec spec; //각 탭의 메뉴와 컨텐츠를 위한 객체선언
		Intent intent_Tab; //각탭에서 사용할 인텐트 선언

		//인텐트 생성
		intent_Tab = new Intent().setClass(this, Activity_Tab1.class);
		//각 탭의 메뉴와 컨텐츠를 위한 객체 생성
		spec = tabHost.newTabSpec("tab1").setIndicator("Main").setContent(intent_Tab);
		tabHost.addTab(spec);

		intent_Tab = new Intent().setClass(this, Activity_Tab2.class);
		spec = tabHost.newTabSpec("tab2").setIndicator("Balance").setContent(intent_Tab);
		tabHost.addTab(spec);

		intent_Tab = new Intent().setClass(this, Activity_Tab3.class);
		spec = tabHost.newTabSpec("tab3").setIndicator("History").setContent(intent_Tab);
		tabHost.addTab(spec);

		intent_Tab = new Intent().setClass(this, Activity_Tab4.class);
		spec = tabHost.newTabSpec("tab4").setIndicator("Setting").setContent(intent_Tab);
		tabHost.addTab(spec);

		tabHost.setCurrentTab(0);
		
		Thread killer = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				while(!shut){
					
				}
				finish();
			}
		});
		
		Intent intent_Service = new Intent(getBaseContext(), Service_WifiScanner.class);
		startService(intent_Service);
	}
	

}
