package z.y.x.ideasofmarchapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONObject;

//log in activity page

public class LoginActivity extends ActionBarActivity {
    TextView responseTextView;
    EditText userName;
    EditText passWord;
    Button loginbutton;
    AsyncHttpClient client = new AsyncHttpClient();
    protected String returnString = "";

//    private static final long CONN_MGR_TIMEOUT = 10000;
//    private static final int CONN_TIMEOUT = 50000;
//    private static final int SO_TIMEOUT = 50000;

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
                    client.setTimeout(30000);
                    RequestParams params = new RequestParams();
                    params.put("username", userName.getText().toString());
                    params.put("password", passWord.getText().toString());
                    Log.i("USERNAME AND PASSWORD", params.toString());
                    client.post("http://24.130.77.20/iom/IOM_iLearnDaily_DataBase/userCheck.php", params, new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                           super.onSuccess(statusCode, headers, response);
                           Log.i("POST REPONSE",response.toString());
                           Intent i = new Intent(LoginActivity.this, DisplayWorkActivity.class);
                           i.putExtra("JSON", response.toString());
                           startActivity(i);
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                            super.onFailure(statusCode, headers, throwable, errorResponse);
                        }
                    });
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
}
