package com.himanshu.hover;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;


public class HelpOptions extends Activity{

	Spinner spin_help;
	TextView tv_help;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.help_layout);
		spin_help=(Spinner) findViewById(R.id.spinner_help);
		tv_help =(TextView)findViewById(R.id.tv_help);

		List<String> list = new ArrayList<String>();
//		list.add("Music Control");
//		list.add("Call Management");
		list.add("Phone Unlock");
		list.add("Wifi On/Off");
		list.add("Bluetooth On/Off");

		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
		(this, android.R.layout.simple_spinner_item,list);

		dataAdapter.setDropDownViewResource
		(android.R.layout.simple_spinner_dropdown_item);

		spin_help.setAdapter(dataAdapter);

		spin_help.setOnItemSelectedListener(new OnItemSelectedListener()

		{
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, 
					int arg2, long arg3)

			{

				int index = arg0.getSelectedItemPosition();

				// storing string resources into Array 
				/*if(index==0){
					tv_help.setText("MUSIC:"+"\n\n\n"+"1. Swipe over Proximity Sensor (near earpiece) for next song."+"\n\n"+"2. Double Swipe  for previous song"
				+"\n\n"+"3. Hold over  for Pause/Play");
				}else if(index==1){
					tv_help.setText("CALL :"+"\n\n\n"+"1. Swipe over Proximity Sensor to pick a call.\n\n2. Double Swipe to reject the call");
				}else*/
				if(index==0){
					tv_help.setText("SCREEN LOCK:"+"\n\n\n"+"1. Swipe over phone to Unlock the phone (Only Once)");
				}
				else if(index==1){
					tv_help.setText("WIFI CONTROL:"+"\n\n\n"+"1. Swipe over phone to Turn On the Wifi"+"\n\n"+"2. Double Swipe to Turn Off Wifi");
				}
				else if(index==2){
					tv_help.setText("BLUETOOTH CONTROL:"+"\n\n\n"+"1. Swipe over phone to Turn On the Bluetooth"+"\n\n"+"2. Double Swipe to Turn Off Bluetooth");
				}
			}

			public void onNothingSelected(AdapterView<?> arg0) { 
				// do nothing

			}

		});


	}



}
