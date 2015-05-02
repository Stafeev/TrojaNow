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

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class SignUpActivity extends ActionBarActivity {

    String myURL = "http://cs578.roohy.me/user/";
    public final static String EXTRA_MESSAGE = "com.example.artemstafeev.trojanow.MESSAGE";
    public String username = "";
    public String password = "";

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
        EditText usernameText = (EditText)findViewById(R.id.editText3);
        EditText passwordText = (EditText)findViewById(R.id.editText5);

        username = usernameText.getText().toString();
        password = passwordText.getText().toString();


        String str=myURL+password+"/";
        UpdateDataTask updateData = new UpdateDataTask();
        updateData.execute(new String[]{str});
        try {
            //String name = getData.execute(str).get();
            //redirect to another activity
            Intent intent = new Intent(SignUpActivity.this, MainPostFeedActivity.class);
            intent.putExtra(EXTRA_MESSAGE, username);
            startActivity(intent);
            }

        catch (Exception e){
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Error");
            alertDialog.setMessage("This user already exists");
            alertDialog.show();
        }

    }

    public class UpdateDataTask extends AsyncTask<String,Void,String> {


        @Override
        protected String doInBackground(String... params) {
            try{

                HttpClient httpClient = new DefaultHttpClient();
                HttpPut putReq = new HttpPut(params[0]);
                putReq.setHeader("Accept", "application/json");
                putReq.setHeader("Content-type", "application/json");
                JSONObject dataToJSON = new JSONObject();

                dataToJSON.put("pk", Integer.parseInt(password));
                dataToJSON.put("uid", Integer.parseInt(password));
                dataToJSON.put("name", username);
                System.out.println("json obj" + dataToJSON.toString());
                StringEntity entity = new StringEntity(dataToJSON.toString());
                putReq.setEntity(entity);
                HttpResponse response = (HttpResponse) httpClient.execute(putReq);
                System.out.println("response is ----> "+ response);
                int resCode = response.getStatusLine().getStatusCode();
                System.out.println("response code = "+ resCode);

                return "Success!";

            }
            catch (Exception e) {
                System.out.println(e.getMessage());
                return e.getMessage();
            }
        }

    }
}
