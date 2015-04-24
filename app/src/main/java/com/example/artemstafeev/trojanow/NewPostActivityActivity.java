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
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.post.Post;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;


public class NewPostActivityActivity extends ActionBarActivity implements OnClickListener{

	private String textStr ="";
	private String infoStr= "";
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
    		setTextStr(postMsg.getText().toString());
    		setInfoStr("Anonym:"+ temp1 +",Temperature:"+ temp2);
    		//call function for sending data to restful api
    		//sendDataToRestfulWebService(getTextStr(), getInfoStr(), 14);
    		SendDataToREST sendObj = new SendDataToREST();
    		sendObj.execute(new String[]{urlForSave+"15/"});
    		
    	}
    	
    }
    
    /**
     * This method will send the data to Restful Web service 
     */
    public void sendDataToRestfulWebService(String text, String info, int usr)
    {
    	HttpClient httpClient = new DefaultHttpClient();
    	urlForSave += usr+"/";
    	System.out.println("POST url-> "+urlForSave);
    	System.out.println("http post url is >>> "+urlForSave);
        HttpPost postReq = new HttpPost(urlForSave);
        postReq.setHeader("Accept", "application/json");
        postReq.setHeader("Content-type", "application/json");
    	
    	//for sending data in JSON format
        JSONObject dataToJSON = new JSONObject();
        try{
        		//dataToJSON.put("pk", usr); 
        		dataToJSON.put("text", text); 
        		dataToJSON.put("info", info);
        		dataToJSON.put("user", usr);

	        	System.out.println("json obj" + dataToJSON.toString());
	            StringEntity entity = new StringEntity(dataToJSON.toString());
	            System.out.println("string entity-> "+ entity);
	            postReq.setEntity(entity);
	        	System.out.println("entity is set ");
	           // BasicResponseHandler bsh = new BasicResponseHandler();
	            HttpResponse response = (HttpResponse) httpClient.execute(postReq);
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
        finally{
        	
        	//httpClient.close();
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
  
    
     public String getTextStr() {
		return textStr;
	}

	public void setTextStr(String textStr) {
		this.textStr = textStr;
	}


	public String getInfoStr() {
		return infoStr;
	}

	public void setInfoStr(String infoStr) {
		this.infoStr = infoStr;
	}


	/** 
     
	 * Class to send data to REST API
	 * @author User
	 */
	 
		private class SendDataToREST extends AsyncTask<String, Void,String>{

		    @Override
		    protected String doInBackground(String... params) {

		          try {

		           /* URL urlStr = new URL(params[0]);
		            HttpURLConnection httpConn = (HttpURLConnection) urlStr.openConnection();
		            httpConn.setRequestMethod("POST");
		            httpConn.setDoOutput(true);
		           // httpConn.connect();*/
		            //String aStr="",bStr="";
		            
		        	  HttpClient httpClient = new DefaultHttpClient();
		          	//urlForSave += usr+"/";
		          	//System.out.println("POST url-> "+urlForSave);
		          	//System.out.println("http post url is >>> "+urlForSave);
		              HttpPost postReq = new HttpPost(params[0]);
		              postReq.setHeader("Accept", "application/json");
		              postReq.setHeader("Content-type", "application/json");
		        	  
		            JSONObject dataToJSON = new JSONObject();
		            //dataToJSON.put("pk", usr); 
		            dataToJSON.put("text", getTextStr()); 
		            dataToJSON.put("info", getInfoStr());
		            dataToJSON.put("user", 15);
		    	    System.out.println("json obj" + dataToJSON.toString());

		    	    StringEntity entity = new StringEntity(dataToJSON.toString());
		            postReq.setEntity(entity);
		        	HttpResponse response = (HttpResponse) httpClient.execute(postReq);
		            System.out.println("response is ----> "+ response);
		            int resCode = response.getStatusLine().getStatusCode();
		            System.out.println("response code = "+ resCode);
		           // Toast.makeText(getApplicationContext(),response.getStatusLine().getStatusCode()+"", Toast.LENGTH_LONG).show();  
		           
					/*OutputStreamWriter writer = new OutputStreamWriter(httpConn.getOutputStream());
					writer.write(dataToJSON.toString());
					writer.flush();
		           
					String aStr="",bStr="";
		            if(httpConn.getResponseCode() == HttpURLConnection.HTTP_OK)
		            {
		            	System.out.println("HTTP OK");
		                BufferedReader buffReader = new BufferedReader(new InputStreamReader(httpConn.getInputStream()));
		                while ((aStr = buffReader.readLine()) != null) {
		            	   bStr += aStr;
		   				}
		            }
		            System.out.println("response string is-> "+ bStr);
		            return bStr;*/
		            return "Post Success!";
		        }catch (Exception exp)
		        {
		        	System.out.println(exp.getMessage());
		        	return exp.getMessage();
		        }
		      
		    }

		    protected void onPostExecute(String result)
		    {
		    	Toast.makeText(getApplicationContext(),result, Toast.LENGTH_LONG).show();
		    	Intent mainActivityIntent = new Intent(getBaseContext(), MainPostFeedActivity.class);
        		startActivity(mainActivityIntent);
		 			
		 	}
	}
		

		


}
