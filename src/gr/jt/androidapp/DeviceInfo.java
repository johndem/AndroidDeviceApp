package gr.jt.androidapp;


import java.io.File;


import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
//import android.os.Environment;
//import android.os.StatFs;
import android.os.SystemClock;
import android.widget.TextView;


public class DeviceInfo extends Activity{
	
    Activity activity;
    ConnectivityManager connectivityManager;
    
    public DeviceInfo(Activity activity, ConnectivityManager connectivityManager) {
    	this.activity = activity;
    	this.connectivityManager = connectivityManager;
    }
    
    
	@SuppressWarnings({ "deprecation" })
	@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
	@SuppressLint("NewApi")
	public void setTextValues() {
		
    	// Kernel
		String kernel = "Kernel: " + System.getProperty("os.version");
		TextView tvkernel = (TextView) activity.findViewById(R.id.kernel);
		tvkernel.setText(kernel);
		
		
		//Build
		String build = "Build: " + android.os.Build.VERSION.INCREMENTAL;
		TextView tvbuild = (TextView) activity.findViewById(R.id.build);
		tvbuild.setText(build);
		
		
		// Model
		String mdl = "Model (and Product): " + android.os.Build.MODEL + " ("+ android.os.Build.PRODUCT + ")";
		TextView model = (TextView) activity.findViewById(R.id.model_prod);
		model.setText(mdl);
			
		
		// Android Version
		String and_ver = "Android Version: " + android.os.Build.VERSION.RELEASE;
		TextView android = (TextView) activity.findViewById(R.id.android_ver);
		android.setText(and_ver);
		
		
		// Battery
		IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
		Intent batteryStatus = activity.registerReceiver(null, ifilter);
		int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
		TextView battery = (TextView) activity.findViewById(R.id.battery);
		battery.setText("Battery Level: "+ Integer.toString(level) + "%");

		
	    final long SIZE_KB = 1024;
	    final long SIZE_MB = SIZE_KB * SIZE_KB;
		File path;
		StatFs stat;
		long blockSize;
		long availableBlocks;
		
	    // Internal available memory space
		path = Environment.getDataDirectory();
        stat = new StatFs(path.getPath());
        blockSize = stat.getBlockSize();
        availableBlocks = stat.getAvailableBlocks();
        
		String inmen = "Free internal memory space: " + (availableBlocks * blockSize)/SIZE_MB + " MBs";
		TextView internalMemory = (TextView) activity.findViewById(R.id.internal);
		internalMemory.setText(inmen); /**/
		
		// External available memory space
		path = Environment.getExternalStorageDirectory();
        stat = new StatFs(path.getPath());
        blockSize = stat.getBlockSize();
        availableBlocks = stat.getAvailableBlocks();
        
		String exmem = "Free external memory space: " + (availableBlocks * blockSize)/SIZE_MB + " MBs";
		TextView externalMemory = (TextView) activity.findViewById(R.id.external);
		externalMemory.setText(exmem);/* */
		
		
		// Uptime
		String upt = "Uptime: " + SystemClock.uptimeMillis() + " milliseconds";
		TextView uptime = (TextView) activity.findViewById(R.id.uptime);
		uptime.setText(upt);
	
		
		// Connection
		NetworkInfo mobile,wifi;
		mobile= connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		wifi= connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		
		if (mobile.isConnectedOrConnecting()) {
			String mob = "3G: ON";
			TextView connection = (TextView) activity.findViewById(R.id.mobile);
			connection.setText(mob);	
		}
		else {
			String mob = "3G: OFF";
			TextView connection = (TextView) activity.findViewById(R.id.mobile);
			connection.setText(mob);
			
		}
		if (wifi.isConnectedOrConnecting() || wifi.isAvailable()) {
			String wf = "WIFI: ON";
			TextView connection = (TextView) activity.findViewById(R.id.wifi);
			connection.setText(wf);
		}
		else {
			String wf = "WIFI: OFF";
			TextView connection = (TextView) activity.findViewById(R.id.wifi);
			connection.setText(wf);
		}
		
		
	}

}
