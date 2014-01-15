package com.example.stockprice;

import java.util.ArrayList;


import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.opengl.Visibility;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

public class AdminActivity extends Activity{
	
	private ArrayList<String> results;
	ListView listView;
	FrameLayout contentFrame;
	private SQLiteDatabase db;
	private String tableName;
	Activity thisActivity = this;
	
	Fragment fragment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.admin_activity);
		ActionBar actionBar = this.getActionBar();
		actionBar.setTitle("Administrator");

		
		
		listView = (ListView) findViewById(R.id.account_list_view);
		contentFrame = (FrameLayout) findViewById(R.id.content_frame);
		contentFrame.setVisibility(View.INVISIBLE);
		
		openAndQueryDatabase();
		
		displayList();
		
	}

	private void openAndQueryDatabase() {
		results = new ArrayList<String>();
		try {
            AccountDatabase db = new AccountDatabase(this.getApplicationContext());
            tableName = db.getTableName();
            Cursor c = db.selectFromDatabase();
 
            if (c != null ) {
                if  (c.moveToFirst()) {
                    do {
                    	//Change here
                        String email = c.getString(c.getColumnIndex("email"));
                        results.add(email);
                    }while (c.moveToNext());
                } 
            }           
        } catch (SQLiteException se ) {
            Log.e(getClass().getSimpleName(), "Could not create or Open the database");
        } finally {
            /**
        	if (db != null) 
                db.execSQL("DELETE FROM " + tableName);
                db.close();
                **/
        }
 
    }

	private void displayList() {
        listView.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, results));
        listView.setTextFilterEnabled(true);
        
        OnItemClickListener listViewOnItemClickListener = new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {

				String email = (String)parent.getItemAtPosition(position);
				
		    	fragment = new AccountFragment();
	   	    	
		        Bundle args = new Bundle();
		        args.putString("email", email);
		        fragment.setArguments(args);
		    	
	   	    	FragmentManager fragmentManager = getFragmentManager();
	   	        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

	   	        listView.setVisibility(View.INVISIBLE);
	   	        contentFrame.setVisibility(View.VISIBLE);
	   	        
		    	
			}
        	
        };
        
        listView.setOnItemClickListener(listViewOnItemClickListener);
        
	}
	
	@Override
	public void onBackPressed() {
		
		if(listView.getVisibility() == View.INVISIBLE){
			
			listView.setVisibility(View.VISIBLE);
			openAndQueryDatabase();
			displayList();
			
			contentFrame.setVisibility(View.INVISIBLE);
			
		} else if (listView.getVisibility() == View.VISIBLE){
			super.onBackPressed();
		}
		
	}
	
	

}
