package com.example.trojanow;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.post.Post;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class MainPostFeedActivity extends ActionBarActivity implements OnClickListener {

	 final List<Post> listOfPosts = new ArrayList<Post>();
	 String urlForGET = "http://cs578.roohy.me/status/list/";
	 String urlForDELETE = "http://cs578.roohy.me/status/";
	 String aStr="",bStr="";
	 
	 
	@Override
    protected void onCreate(Bundle savedInstanceState) {
    	System.out.println("Main Post Fed Activity is created.");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_post_feed);
               
        ListView list = (ListView) findViewById(R.id.PostList);
        list.setClickable(true);
        
        GetDataFromREST data = new GetDataFromREST();
       	data.execute(new String[]{urlForGET});
         
        list.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int position, long index) {
                System.out.println("sadsfsf");
               
                int postPK = listOfPosts.get(position).getPostID();
                
                showToast(listOfPosts.get(position).getPostMessage()+" pk is "+postPK);
                String urlDel = urlForDELETE+postPK+"/";
                
                DeletePostFromREST delPost = new DeletePostFromREST();
                delPost.execute(new String[]{urlDel});
                
            }
        });
               
        Button newPostBtn = (Button) findViewById(R.id.newPostBtn);
        newPostBtn.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
     * This method calls the getAllPosts() method of Post class to fetch all
     * the posts and display on the Main Activity and would populate the
     * list view when the page loads.
     */
    public void getPostsForMainActivity()
    {
    	
    }

  		private void showToast(String message) {
		    Toast.makeText(this, message, Toast.LENGTH_LONG).show();
		}


		@Override
		public void onClick(View view) {
			System.out.println("new post button clicked");
			// TODO Auto-generated method stub
			switch(view.getId()) {
	    	case R.id.newPostBtn :
	    		Intent selectedPostIntent = new Intent(getBaseContext(), NewPostActivityActivity.class);
			   startActivity(selectedPostIntent);
	    	}
			
		}

	/**
	 * Class to get data from REST API
	 * @author User
	 *
	 */
		private class GetDataFromREST extends AsyncTask<String, Void,String>{

		    @Override
		    protected String doInBackground(String... params) {
		    	
		    	String url = params[0];
		    	String urlPerUser = "";
		          try {

		        	  	for(int i=14; i<= 18; i++)
		        	  	{
		        	  		urlPerUser = url+ i+"/";
		        		  	System.out.println("url # " + i +" is "+ urlPerUser);
		        	        URL urlStr = new URL(urlPerUser);
				            HttpURLConnection httpConn = (HttpURLConnection) urlStr.openConnection();
				            httpConn.setRequestMethod("GET");
				            httpConn.connect();
				           
				            aStr="";bStr="";
				            if(httpConn.getResponseCode() == HttpURLConnection.HTTP_OK)
				            {
				                BufferedReader buffReader = new BufferedReader(new InputStreamReader(httpConn.getInputStream()));
				                while ((aStr = buffReader.readLine()) != null) {
				                	System.out.println("aStr is " + aStr);
				            	    bStr += aStr;
				   				}
				            }
				            addToList(bStr);
		        	  	}
		            System.out.println("json as string is-> "+ bStr);
		           
		        }catch (Exception exp)
		        {
		        	return exp.getMessage();
		        }
		          return "Parsed";
		      
		    }

		    protected void onPostExecute(String result)
		    {
		    	
		    		 	System.out.println("size = " +listOfPosts.size());
		    		 	ListView list = (ListView) findViewById(R.id.PostList);
		    	        PostAdapter adapter = new PostAdapter(getApplicationContext(), listOfPosts);
		    	        list.setAdapter(adapter);
		    	}
		    
		    public void addToList(String arStrResult)
		    {
		    	String msg = "";
		    	int pk_post = 0;
		    	String info = "";
		    	String outInfoStr = "";
		    	String usr = "";
		        System.out.println("before async is over.... string is "+arStrResult);
		    	 try{
		    		 JSONArray jsonAr = new JSONArray(arStrResult);
		    		 
		    		 for ( int i = 0; i < jsonAr.length() ; i++)
		    		 {
		    			//this object inside array you can do whatever you want   
		    		    JSONObject object = jsonAr.getJSONObject(i);
		    		    pk_post = object.getInt("pk");
		    		    msg = object.getString("text");
		    		    info = object.getString("info");
		    		    usr = object.getString("user");
		    		   
		    		    if (info.contains(","))
		    		    	{
		    		    	 	String[] infoAr1 = info.split(",");
		    		    	 	outInfoStr = infoAr1[1];
		    		    	 	if(infoAr1[0].contains(":"))
		    		    	 	{
					    		    String [] infoAr2 = infoAr1[0].split(":");
					    		    if ( infoAr2[0].equalsIgnoreCase("Anonym") && infoAr2[1].equalsIgnoreCase("True"))
					    		    {
					    		    	usr= "User: Anonymous";
					    		    }
					    		    else if ( infoAr2[0].equalsIgnoreCase("Anonym") && infoAr2[1].equalsIgnoreCase("False"))
					    		    {
					    		    	usr = "User: "+usr;
					    		    }
		    		    	 	}
		    		    	 	else{usr = "User: "+usr;}
		    		    	}
		    		    else{
		    		    	outInfoStr = info;
		    		    	usr = "User: "+usr;
		    		    }
		    		    
		    		    listOfPosts.add(new Post(pk_post,msg, usr, outInfoStr));
		    	          
		    		 }
		    		 	
		 		}catch (JSONException jsonEx){
		 			
		 		}
		    }
		}

		/**
		 * Class to delete the selected post from REST API
		 * @author User
		 *
		 */
			private class DeletePostFromREST extends AsyncTask<String, Void,String>{

			    @Override
			    protected String doInBackground(String... params) {
			    	String resultStr = "Failure";
			    	String urlForDelete = params[0];
			    	System.out.println("delete url is -> "+urlForDelete);
			    	try {
			        	  	URL urlStr = new URL(urlForDelete);
					        HttpURLConnection httpConn = (HttpURLConnection) urlStr.openConnection();
					        httpConn.setRequestMethod("DELETE");
					        httpConn.setRequestProperty("Content-Type","application/json");
					        httpConn.connect();
					        int respCode = httpConn.getResponseCode() ;
					        System.out.println("response code after Delete -> "+ respCode);
					        if(respCode == HttpURLConnection.HTTP_OK || respCode == HttpURLConnection.HTTP_ACCEPTED || respCode == HttpURLConnection.HTTP_NO_CONTENT)
					            {
					            	resultStr = "Success";	 
					            	showToast("Post Successfully Deleted!");
					   			}

				        }catch (Exception exp)
				        {
				        	return exp.getMessage();
				        }
			          return resultStr;
			      
			    }

			    protected void onPostExecute(String result)
			    {
			    	
	    		 	if(result.equals("Success"))
	    		 	{
	    		 		//populate the list again
	    		 		//GetDataFromREST data = new GetDataFromREST();
	    		       	//data.execute(new String[]{urlForGET});
	    		 		 Intent intent= new Intent(MainPostFeedActivity.this, MainPostFeedActivity.class);
	    		         intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
	    		         startActivity(intent);
	    		 		//startActivity(this, MainPostFeedActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	    		 	}
	    		 	else
	    		 	{
	    		 		showToast("Not Success!");
	    		 	}
			    }
			   
			}

		
}




