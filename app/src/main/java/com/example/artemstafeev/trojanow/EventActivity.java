package com.example.trojanow;

import android.R;
import android.app.Activity;
import android.os.Bundle;

public class EventActivity extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		System.out.println("Event Activity is created.");
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_item);
	
	    // TODO Auto-generated method stub
	}
	/**
     * This method calls the getAllEvents() method of Event class to fetch all
     * the events and display on the Events Activity and would populate the
     * list view when the page loads.
     */
    public void getEventsForEventActivity()
    {
    	
    }
    
    /**
     * This method calls the saveEvent() method of Event class to save
     * the new event in the database.
     */
    public void saveNewEvent()
    {
    	
    }
	

}
