package gr.jt.androidapp;


import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


@SuppressLint("SimpleDateFormat")
public class MemoryHistory {
	
	private Database db;
	private Activity activity;
	
	public MemoryHistory(Context c,Activity activity){
		db = new Database(c); // create a new database
		this.activity = activity;
	}
	
	
	// show contents of database
	@SuppressWarnings("deprecation")
	public void show() {
		
		db.open();
		
		// calculate free internal and external memory
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
        
		String inmen =  "Internal:" + (availableBlocks * blockSize)/SIZE_MB + "MBs";
		
		// External available memory space
		path = Environment.getExternalStorageDirectory();
        stat = new StatFs(path.getPath());
        blockSize = stat.getBlockSize();
        availableBlocks = stat.getAvailableBlocks();
		
        String exmem = "External:" + (availableBlocks * blockSize)/SIZE_MB + "MBs";
        

        // determine date
        Calendar c = Calendar.getInstance();

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String date = df.format(c.getTime());
        
		// add entry to database
		db.createEntry(date, inmen, exmem);
		
		
		ArrayList<String> data = db.getData(); // array list of entries

		// table to display data in app
		TableLayout tl = (TableLayout) activity.findViewById(R.id.tl3);
		tl.setColumnShrinkable(1,false);
		
		int pos;
		
		for (String item: data) {
			
			// create table row for each entry
			TableRow tr = new TableRow(activity);
			tr.setPadding(5,1,0,10);
			// create 4 text views(entry id, date, free internal memory, free external memory)
			TextView[] tv = new TextView[4];
			
		    pos = item.indexOf(" ");
		    int counter = 0;
		    
		    while (pos>=0) {
				tv[counter] = new TextView(activity);
		    	tv[counter].setText(item.substring(0, pos) + "   "); // add each element of entry to a view
		    	tr.addView(tv[counter]); // add view to table row
		    	item = item.substring(pos + 1); // get next element
		    	pos = item.indexOf(" ");
		    	counter++;
		    }
		    
		    tv[counter] = new TextView(activity);
			tv[counter].setText(item + "   "); // add last element of entry(free external memory) to a view
	    	tr.addView(tv[counter]); // add view to table row
	    	// add row to table
	    	tl.addView(tr);
 
		}

		db.close();
		
	}

}
