package com.example.trojanow;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import android.os.Build;

public class NewPostActivityActivity extends ActionBarActivity implements OnClickListener{

	String textStr ="";
	String infoStr= "";
	String urlForSave = "http://cs578.roohy.me/status/list/";
	String temp1 ="false", temp2 ="NA";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_post_activity);
		/*if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}*/
	
		Button newPostSaveBtn = (Button) findViewById(R.id.savePostBtn);
		newPostSaveBtn.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_post_activity, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	
	/**
     * This will save the new post on server
     * @param view
     */
	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		System.out.println("save button clicked");
		switch(view.getId()) {
    	case R.id.savePostBtn :
    		
    		EditText postMsg = (EditText) findViewById(R.id.postMsgTextArea);
    		CheckBox anonFlag = (CheckBox) findViewById(R.id.chkBoxAnon);
    		if( anonFlag.isChecked()){
    			temp1 = "true";
    		}
    		textStr = postMsg.getText().toString();
    		infoStr = "Anon:"+ temp1 +",Temperature:"+ temp2;
    		//call function for sending data to restful api
    		sendDataToRestfulWebService(textStr, infoStr, "14");
    		
    	}
    	
    }
    
    /**
     * This method will send the data to Restful Web service 
     */
    public void sendDataToRestfulWebService(String text, String info, String usr)
    {
    	HttpParams params = new BasicHttpParams();
    	params.setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
    	HttpClient httpClient = new DefaultHttpClient(params);
    	urlForSave += usr+"/";
    	System.out.println("http post url is >>> "+urlForSave);
        HttpPost postReq = new HttpPost(urlForSave);
        postReq.setHeader("Accept", "application/json");
        postReq.setHeader("content-type", "application/json;charset=UTF-8");
    	
    	//for sending data in JSON format
        JSONObject dataToJSON = new JSONObject();
        try{
        		dataToJSON.put("text", text); 
        		dataToJSON.put("info", info);
        		dataToJSON.put("user", usr);

	        	System.out.println("json obj" + dataToJSON.toString());
	            StringEntity entity = new StringEntity(dataToJSON.toString());
	            System.out.println("string entity-> "+ entity);
	            postReq.setEntity(entity);
	
	            HttpResponse response = httpClient.execute(postReq);
	            System.out.println("response is ----> "+ response);
	            int resCode = response.getStatusLine().getStatusCode();
	            System.out.println("response code = "+ resCode);
	            Toast.makeText(getApplicationContext(),response.getStatusLine().getStatusCode()+"", Toast.LENGTH_LONG).show();   
	
	            if (resCode == 200) {
	            	//on SUCCESS -> navigate user back to main activity screen
	        		Intent mainActivityIntent = new Intent(getBaseContext(), MainActivity.class);
	        		startActivity(mainActivityIntent);
	            }
	        	
	           
        }catch (Exception e) {
            // TODO: handle exception
        }  
    }
	
    
    /**
     * This method calls the savePost() method of Post class to save
     * the new post in the database.
     */
    public void saveNewPost()
    {
    	
    }
    
    /**
     * This method will share the selected post on Facebook of the logged in user.
     * It will call the share() method of Share class and validate the logged in
     * user and share the post on his/her Facebook feed.
     */
    public void sharePost()
    {
    	
    }
    
    /**
     * This method will retrieve the current location of the user by 
     * calling the LocationFinder component's getLocation() method.
     */
    public void getUserLocation()
    {
    	
    }
    
    /**
     * This method will retrieve the current temperature by 
     * calling the TemperatureSensor component's getTemperature() method.
     * This will be implemented if device supports sensor services
     */
    public void getCurrentTemperature()
    {
    	
    }

	
	/**
	 * A placeholder fragment containing a simple view.
	 *//*
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(
					R.layout.fragment_new_post_activity, container, false);
			return rootView;
		}
	}*/
}
