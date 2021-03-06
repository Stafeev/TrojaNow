package com.example.trojanow;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.EditText;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class MainActivity extends ActionBarActivity {

    String myURL = "http://cs578.roohy.me/user/";
    String result = "";
    public final static String EXTRA_MESSAGE = "com.example.artemstafeev.trojanow.MESSAGE";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }

    /** Called when the user touches the button */
    public void sendLogin(View view) {
        EditText usernameText = (EditText)findViewById(R.id.editText);
        EditText passwordText = (EditText)findViewById(R.id.editText2);

        String username = usernameText.getText().toString();
        String password = passwordText.getText().toString();

        String str=myURL+password;
        GetDataTask getData = new GetDataTask();
        try {
            String name = getData.execute(str).get();

            if(name.equals(password))
            {
                //redirect to another activity
                Intent intent = new Intent(MainActivity.this, MainPostFeedActivity.class);
                intent.putExtra(EXTRA_MESSAGE, username);
                startActivity(intent);
            }
            else
            {
                AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                alertDialog.setTitle("Login Error");
                alertDialog.setMessage("Incorrect login or password");
                alertDialog.show();
            }

        } catch (Exception e){
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Login Error");
            alertDialog.setMessage("Incorrect login or password");
            alertDialog.show();
        }

    }

    /** Called when the user clicks sign up */
    public void signupBtn(View view) {
        try {
                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intent);

        } catch (Exception e){
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Error");
            alertDialog.setMessage("There was an error while submitting your request");
            alertDialog.show();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }

    public class GetDataTask extends AsyncTask<String,Void,String> {

        public String readMessage(JSONObject reader) throws IOException {
            try {
                return reader.getString("uid");
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }

        public String convertStreamToString(java.io.InputStream is) {
            java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
            return s.hasNext() ? s.next() : "";
        }

        @Override
        protected String doInBackground(String... myURL) {
            try{
                HttpURLConnection urlConnection = null;
                URL url = new URL(myURL[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                //System.out.println("got something");
                // Read the input stream into a String
                InputStream inputStream;
                inputStream = urlConnection.getInputStream();
                JSONObject reader;
                //reader = new JSONObject(inputStream.toString());

                reader = new JSONObject(convertStreamToString(inputStream));

                return readMessage(reader);

            }
            catch (Exception e) {
                return null;
            }
        }

    }

}
