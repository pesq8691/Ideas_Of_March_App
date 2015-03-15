package z.y.x.ideasofmarchapp;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nuchis on 3/15/2015.
 */
public class LoginAsyncTask extends AsyncTask<String, Void, String> {
    @Override
    protected String doInBackground(String... params) {



        try {
            HttpClient client = new DefaultHttpClient();
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            InputStream inputStream = null;
            HttpPost post = new HttpPost("http://10.11.140.177/~edsan/IOM_iLearnDaily_DataBase/jsamples.php");

            nameValuePairs.add(new BasicNameValuePair("username", params[0]));
            nameValuePairs.add(new BasicNameValuePair("password", params[1]));
            post.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = client.execute(post);
            inputStream = response.getEntity().getContent();
            //textView.setText(convertInputStreamToString(inputStream));
                    /*Checking response */
            if(response!=null){
                InputStream in = response.getEntity().getContent(); //Get the data in the entity
                return in.toString();
            }

        } catch(Exception e) {
            e.printStackTrace();
            Log.i("Error", "Cannot Estabilish Connection");
        }

        return null;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onCancelled(String s) {
        super.onCancelled(s);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }
}
