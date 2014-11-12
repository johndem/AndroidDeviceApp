package gr.jt.androidapp;


import java.util.List;


import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.util.Log;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


public class ActiveProcesses {
	
	Activity activity;
	ActivityManager activityManager;
	
	
	public ActiveProcesses(Activity activity, ActivityManager activityManager) {
		this.activity = activity;
		this.activityManager = activityManager; 
		
	}
	
	public void getActiveProcesses() {
		 
		// get running processes with Activity Manager
		final List <RunningServiceInfo> services = activityManager.getRunningServices(100);
		
		// create a table to hold the data
		TableLayout tl = (TableLayout) activity.findViewById(R.id.tl);
		tl.setColumnShrinkable(1,true);
		
		int[] pids = new int[100];
		for (int i = 0; i < services.size(); i++) {
			 pids[i]= services.get(i).pid;
		}
		   
		
		android.os.Debug.MemoryInfo[] mi = activityManager.getProcessMemoryInfo(pids);
		
		// for each process add a table row with pid, name and memory usage
		for (int i = 0; i < services.size(); i++) 
		{
		       TableRow tr = new TableRow(activity);
		       TextView tvid = new TextView(activity);
		       TextView tvname = new TextView(activity);
		       TextView tvmemory = new TextView(activity);
		       
		       tvid.setText("PID: " + services.get(i).pid + "  ");
		       tvname.setText(services.get(i).process);
		       tvmemory.setText(mi[i].getTotalPss()+" KB");
		       
		       tr.setPadding(5,1,0,10);
		       
		       // add views to table row
		       tr.addView(tvid);
		       tr.addView(tvname); 
		       tr.addView(tvmemory); 
		       // add row to table
		       tl.addView(tr);
		       
		       Log.d("Executed app", "Application executed : " +services.get(i).process+ "\t\t ID: "+services.get(i).pid);         
		}
		
	}
}
