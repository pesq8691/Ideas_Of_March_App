package z.y.x.ideasofmarchapp;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class LoginActivity extends ActionBarActivity {
    TextView responseTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        responseTextView = (TextView) findViewById(R.id.responseTextView);

        postData();
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

    public void postData() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() {
                HttpClient client = new DefaultHttpClient();
                HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000);
                try {
                    //Server connection
                    HttpPost post = new HttpPost("http://10.11.173.205/iom/helloworld.php");
                    // Add your data
                    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                    nameValuePairs.add(new BasicNameValuePair("id", "12345"));
                    nameValuePairs.add(new BasicNameValuePair("stringdata", "Hi"));
                    post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    // Execute HTTP Post Request
                    HttpResponse response = client.execute(post);
                    String postRes = response.toString();
                    return postRes;
                } catch (ClientProtocolException e) {
                    // TODO Auto-generated catch block
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                }
                return null;
            }
        };
        Future<String> future = executor.submit(callable);
        // future.get() returns 2
        try{
           responseTextView.setText(future.get());
        }
        catch(Exception e){

        }

        executor.shutdown();
    }
}
