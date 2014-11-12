package gr.jt.androidapp;


import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;


public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// set background color to black
		View view = this.getWindow().getDecorView();
		view.setBackgroundColor(Color.BLACK);
		
		// create  a tabhost to hold all tabs
		TabHost tabhost = (TabHost) findViewById(R.id.tabhost);
		tabhost.setup();
		
		TabSpec specs = tabhost.newTabSpec("tag1");
		specs.setContent(R.id.tab1);
		specs.setIndicator("Active Processes");
		tabhost.addTab(specs);
		
		specs = tabhost.newTabSpec("tag2");
		specs.setContent(R.id.tab2);
		specs.setIndicator("Device Info");
		tabhost.addTab(specs);
		
		specs = tabhost.newTabSpec("tag3");
		specs.setContent(R.id.tab3);
		specs.setIndicator("Free Memory Space");
		tabhost.addTab(specs);
		
		// set tab title text color to white
		for (int i = 0 ; i < tabhost.getTabWidget().getTabCount() ; i ++) {
	        View tab = tabhost.getTabWidget().getChildTabViewAt(i);
	        TextView t = (TextView)tab.findViewById(android.R.id.title);
	        t.setTextColor(Color.WHITE);
	    }
		
		
		// manage connection and device information
		final ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		DeviceInfo devinfo = new DeviceInfo(this,connectivityManager);
		devinfo.setTextValues();
		
		
		// manage running activities/processes
		final   ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		ActiveProcesses activeProcesses = new ActiveProcesses(this,activityManager);
		activeProcesses.getActiveProcesses();
		

		// history of memory usage
		MemoryHistory mh = new MemoryHistory(this,this);
		mh.show();
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
