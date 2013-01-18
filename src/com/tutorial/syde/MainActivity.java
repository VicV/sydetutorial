package com.tutorial.syde;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_layout);

		Button button = (Button) findViewById(R.id.button);

		button.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {

		if (v.getId() == R.id.button) {
			EditText firstname = (EditText) findViewById(R.id.fn);
			EditText lastname = (EditText) findViewById(R.id.ln);
			TextView title = (TextView) findViewById(R.id.textView1);

			String first_name = firstname.getText().toString();
			String last_name = lastname.getText().toString();


			Intent intent = new Intent();
			intent.setClass(getApplicationContext(), com.tutorial.syde.PageTwo.class);
			intent.putExtra("firstName", first_name);
			intent.putExtra("lastName", last_name);
			
			startActivity(intent);
		}

	}

}
