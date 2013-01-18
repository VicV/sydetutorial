package com.tutorial.syde;

import java.lang.reflect.Array;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class PageTwo extends Activity implements OnClickListener {

	private String mFirstName;
	private String mLastName;
	private JSONArray jsonList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pagetwo_layout);

		jsonList = new JSONArray();
		Bundle extras = getIntent().getExtras();
		mFirstName = extras.getString("firstName");
		mLastName = extras.getString("lastName");

		((TextView) findViewById(R.id.pt_fn)).setText(mFirstName);
		((TextView) findViewById(R.id.p2_ln)).setText(mLastName);
		((Button) findViewById(R.id.button_p2)).setOnClickListener(this);
		((Button) findViewById(R.id.button_p2_next)).setOnClickListener(this);

		// getJSON();
	}

	private void postJSON() {

		// JSON object to hold the information, which is sent to the server
		JSONObject jsonObjSend = new JSONObject();

		try {
			// Add key/value pairs
			jsonObjSend.put("first_name", mFirstName);
			jsonObjSend.put("last_name", mLastName);
			jsonObjSend.put("is_awesome", true);

			// Output the JSON object we're sending to Logcat:
			Log.i("JSON", jsonObjSend.toString());

		} catch (JSONException e) {
			e.printStackTrace();
		}

		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);

		// Send the HttpPostRequest and receive a JSONObject in return
		JSONObject jsonObjRecv = HttpClient.SendHttpPost(
				"http://syxtems-android-tutorial.herokuapp.com/people",
				jsonObjSend);

		/*
		 * From here on do whatever you want with your JSONObject, e.g. 1) Get
		 * the value for a key: jsonObjRecv.get("key"); 2) Get a nested
		 * JSONObject: jsonObjRecv.getJSONObject("key") 3) Get a nested
		 * JSONArray: jsonObjRecv.getJSONArray("key")
		 */

	}

	@Override
	public void onClick(View v) {

		if (v.getId() == R.id.button_p2) {
			postJSON();
			getJSON();
		} else if (v.getId() == R.id.button_p2_next) {
			if (jsonList.length() > 0) {
				try {
					createArray();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

	private void createArray() throws JSONException {
		ArrayList<String> names = new ArrayList<String>();
		JSONObject person = new JSONObject();

		for (int i = 0; i < jsonList.length(); i++) {

			person = jsonList.getJSONObject(i);
			try {
			names.add(person.getString("first_name")
					+ " " + person.getString("last_name"));
			} catch (Exception e) {
				//
			}
		}
		
		((TextView) findViewById(R.id.result)).setText("");
		
		for (int i = 0; i < names.size(); i++) {
			((TextView) findViewById(R.id.result)).append(", "+names.get(i));
		}
	}

	private void getJSON() {
		// JSON object to hold the information, which is sent to the server

		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);

		// Send the HttpPostRequest and receive a JSONObject in return
		JSONArray jsonObjRecv = HttpClient
				.SendHttpGet("http://syxtems-android-tutorial.herokuapp.com/people");

		((TextView) findViewById(R.id.result)).setText(jsonObjRecv.toString());
		jsonList = jsonObjRecv;

	}
}
