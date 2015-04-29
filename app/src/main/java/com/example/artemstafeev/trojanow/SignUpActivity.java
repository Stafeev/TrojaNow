package com.example.trojanow;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class SignUpActivity extends ActionBarActivity {

    String myURL = "http://cs578.roohy.me/user/";
    String result = "";
    public final static String EXTRA_MESSAGE = "com.example.artemstafeev.trojanow.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sign_up, menu);
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
            View rootView = inflater.inflate(R.layout.fragment_sign_up, container, false);
            return rootView;
        }
    }


    /** Called when the user touches the button */
    public void sendLogin(View view) {
        EditText usernameText = (EditText)findViewById(R.id.editText4);
        EditText passwordText = (EditText)findViewById(R.id.editText5);

        String username = usernameText.getText().toString();
        String password = passwordText.getText().toString();

        String str=myURL+password;
        UpdateDataTask getData = new UpdateDataTask();
        try {
            //String name = getData.execute(str).get();
            //redirect to another activity
            Intent intent = new Intent(SignUpActivity.this, MainPostFeedActivity.class);
            intent.putExtra(EXTRA_MESSAGE, username);
            startActivity(intent);
            }

        catch (Exception e){
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Login Error");
            alertDialog.setMessage("Incorrect login or password");
            alertDialog.show();
        }

    }

    public class UpdateDataTask extends AsyncTask<String,Void,String> {

        public String readMessage(JSONObject reader) throws IOException {
            try {
                return reader.getString("name");
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
                urlConnection.setRequestMethod("PUT");
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
