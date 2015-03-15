package z.y.x.ideasofmarchapp;

import android.content.Intent;
import android.os.Looper;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.loopj.android.http.*;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

//log in activity page

public class LoginActivity extends ActionBarActivity {
    TextView responseTextView;
    EditText userName;
    EditText passWord;
    Button loginbutton;
    AsyncHttpClient client = new AsyncHttpClient();
    protected String returnString = "";
    protected Object mResults = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        responseTextView = (TextView) findViewById(R.id.responseTextView);
        userName = (EditText) findViewById(R.id.username);
        passWord = (EditText) findViewById(R.id.password);
        loginbutton = (Button) findViewById(R.id.btnLogin);


        loginbutton.setOnClickListener(new View.OnClickListener()
            {
                public void onClick(View v) {
                    RequestParams params = new RequestParams();
                    params.put("username", userName.getText().toString());
                    params.put("password", passWord.getText().toString());
                    client.post("http://10.11.140.177/~edsan/IOM_iLearnDaily_DataBase/jsamples.php",params, new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            super.onSuccess(statusCode, headers, response);
                            Log.i("response",response.toString());
                           Intent i = new Intent(LoginActivity.this, DisplayButtonActivity.class);
                           i.putExtra("JSON", response.toString());
                           startActivity(i);
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                            super.onFailure(statusCode, headers, throwable, errorResponse);
                        }
                    });
                    //sendJson(userName.getText().toString(), passWord.getText().toString(), responseTextView);
//
//
//                      Intent i = new Intent(LoginActivity.this, DisplayButtonActivity.class);
//                      i.putExtra("JSON", mResults);
//                      startActivity(i);
                }
            }
        );

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

 protected void sendJson(final String username, final String pwd, final TextView textView) {
        Thread t = new Thread() {

            public void run() {
                Looper.prepare(); //For Preparing Message Pool for the child Thread



                try {
                    HttpClient client = new DefaultHttpClient();
                    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                    InputStream inputStream = null;
                    HttpPost post = new HttpPost("http://10.11.140.177/~edsan/IOM_iLearnDaily_DataBase/jsamples.php");

                    nameValuePairs.add(new BasicNameValuePair("username", username));
                    nameValuePairs.add(new BasicNameValuePair("password", pwd));
                    post.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                    // Execute HTTP Post Request
                    HttpResponse response = client.execute(post);
                    inputStream = response.getEntity().getContent();
                    textView.setText(convertInputStreamToString(inputStream));
                    /*Checking response */
                    if(response!=null){
                        InputStream in = response.getEntity().getContent(); //Get the data in the entity
                        mResults = in.toString();
                    }

                } catch(Exception e) {
                    e.printStackTrace();
                    Log.i("Error", "Cannot Estabilish Connection");
                }

                Looper.loop(); //Loop in the message queue
            }
        };

        t.start();
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException{
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }
}
