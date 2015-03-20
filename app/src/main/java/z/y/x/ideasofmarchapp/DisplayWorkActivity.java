package z.y.x.ideasofmarchapp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;


public class DisplayWorkActivity extends ActionBarActivity {
    private JSONObject jsonObject = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_work);
        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        String obj = b.getString("JSON");

        try{
            jsonObject = new JSONObject(obj);
        }catch (Exception e){
            e.printStackTrace();
        }

        printAssignments();
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

    public void printAssignments(){
        ListView list = (ListView) findViewById(R.id.mainWork);

        ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> map = new HashMap<String, String>();

        Iterator<String> iter = jsonObject.keys();
        while (iter.hasNext()) {
            String key = iter.next();
            try {
                map.put("class", jsonObject.optJSONObject(key).get("className").toString());
                map.put("assignment", jsonObject.optJSONObject(key).get("assignmentName").toString());
                map.put("due", jsonObject.optJSONObject(key).get("date").toString());
                mylist.add(map);
                map = new HashMap<String, String>();
            } catch (JSONException e) {
                // Something went wrong!
            }
        }

        SimpleAdapter mSchedule = new SimpleAdapter(this, mylist, R.layout.activity_assignments,new String[] {"class", "assignment", "due"}, new int[] {R.id.CLASS_CELL, R.id.ASSIGNMENT_CELL, R.id.DUE_CELL});
        list.setAdapter(mSchedule);
    }
}
