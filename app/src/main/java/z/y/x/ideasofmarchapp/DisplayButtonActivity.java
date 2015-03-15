package z.y.x.ideasofmarchapp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import org.json.JSONObject;


public class DisplayButtonActivity extends ActionBarActivity {

    private JSONObject jsonObject = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_button);
        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        String obj = b.getString("JSON");
        try{
            jsonObject = new JSONObject(obj);
            Log.i("Classes: ", jsonObject.get("classes").toString());
            Log.i("assignmentGrade: ", jsonObject.get("assignmentGrade").toString());
            Log.i("assignments: ", jsonObject.get("assignments").toString());
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_display_button, menu);
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
