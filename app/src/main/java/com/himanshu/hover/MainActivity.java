package com.himanshu.hover;

import java.sql.Timestamp;

import android.app.Activity;
import android.app.KeyguardManager;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.os.SystemClock;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;


public class MainActivity extends Activity implements SensorEventListener{

	ToggleButton toggle_main;
	CheckBox music,lock,call,wifi,blue;
	int musicflag=0,lockflag=0,wififlag=0,callflag=0,currentaccuracy,bluetoothflag=0;
	String check;
	BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter(); 
	SensorManager sensormanager;
	Sensor sensor;
	SensorEvent ev;
	TextView steps;

	WifiManager wm;
	TextView tv_test;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		sensormanager=(SensorManager) getSystemService(SENSOR_SERVICE);
		sensor=sensormanager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
		//tv_test.setVisibility(View.INVISIBLE);

		toggle_main=(ToggleButton) findViewById(R.id.toggle_On);
		toggle_main.setText("Turn On");
		//aboutus =(Button) findViewById(R.id.button_aboutus);
		music=(CheckBox) findViewById(R.id.check_music);
		call=(CheckBox) findViewById(R.id.check_call);
		lock=(CheckBox) findViewById(R.id.check_unlock);
		wifi=(CheckBox) findViewById(R.id.check_wifi);
		wm=(WifiManager) getSystemService(WIFI_SERVICE);
		blue = (CheckBox)findViewById(R.id.check_blue);
		tv_test=(TextView)findViewById(R.id.test_sensor);
		steps = (TextView)findViewById(R.id.steps);

		toggle_initialize();
		//aboutus_initialize();
			check_music();
		check_call();
		check_lock();
		check_wifi();
		check_bluetooth();

		//Toast.makeText(getApplicationContext(), "Toggle Button OFF", Toast.LENGTH_LONG).show();
		/*music.setVisibility(View.INVISIBLE);
		call.setVisibility(View.INVISIBLE);*/
		lock.setVisibility(View.INVISIBLE);
		wifi.setVisibility(View.INVISIBLE);
		blue.setVisibility(View.INVISIBLE);
		steps.setVisibility(View.VISIBLE);
		/*music.setClickable(false);
		call.setClickable(false);
		lock.setClickable(false);
		wifi.setClickable(false);
		blue.setClickable(false);*/
		
		steps.setVisibility(View.VISIBLE);

		

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()){
			case R.id.action_help:
				Intent in = new Intent(getApplicationContext(),HelpOptions.class);
				startActivity(in);
				break;
			case R.id.action_about_us:
				Intent intent = new Intent(getApplicationContext(),AboutUsActivity.class);
				startActivity(intent);
				break;

		}
		return super.onOptionsItemSelected(item);
	}

	public void toggle_initialize(){

		toggle_main.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(toggle_main.isChecked()){
					toggle_main.setText("Turn Off");
					//Toast.makeText(getApplicationContext(), "Toggle Button ON", Toast.LENGTH_LONG).show();
					
					/*music.setVisibility(View.VISIBLE);
					call.setVisibility(View.VISIBLE);*/
					lock.setVisibility(View.VISIBLE);
					wifi.setVisibility(View.VISIBLE);
					blue.setVisibility(View.VISIBLE);
					steps.setVisibility(View.INVISIBLE);
					sensormanager.registerListener(MainActivity.this, sensor, SensorManager.SENSOR_DELAY_FASTEST);
				}
				else{
					toggle_main.setText("Turn On");
					//Toast.makeText(getApplicationContext(), "Toggle Button OFF", Toast.LENGTH_LONG).show();
					/*music.setVisibility(View.INVISIBLE);
					steps.setVisibility(View.VISIBLE);*/
					call.setVisibility(View.INVISIBLE);
					lock.setVisibility(View.INVISIBLE);
					wifi.setVisibility(View.INVISIBLE);
					blue.setVisibility(View.INVISIBLE);
					steps.setVisibility(View.VISIBLE);
					sensormanager.unregisterListener(MainActivity.this);
				}
			}
		});
	}



	public void check_music(){

		music.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(music.isChecked()){
					//Toast.makeText(getApplicationContext(), "music button checked", Toast.LENGTH_LONG).show();
					musicflag=1;
					disableOthers(v.getId());
				}
				else
					musicflag=0;
				enableAll();
			}
		});
	}

	public void check_call(){

		call.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(call.isChecked()){
					//Toast.makeText(getApplicationContext(), "call button checked", Toast.LENGTH_LONG).show();
					callflag=1;
					disableOthers(v.getId());
				}
				else
					callflag=0;
				enableAll();
			}
		});
	}

	public void check_lock()
	{
		lock.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(lock.isChecked()){
					//Toast.makeText(getApplicationContext(), "unlock button checked", Toast.LENGTH_LONG).show();
					unlock_phone();
					lockflag=1;
					disableOthers(v.getId());
				}
				else
					lockflag=0;
					enableAll();
			}
		});
	}

	public void check_wifi(){
		wifi.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(wifi.isChecked()){
					//Toast.makeText(getApplicationContext(), "wifi button checked", Toast.LENGTH_LONG).show();
				//	wifi_on();
					wififlag=1;
					disableOthers(v.getId());
				}
				else{
					//Toast.makeText(getApplicationContext(), "wifi button unchecked", Toast.LENGTH_LONG).show();
				//	wifi_off();
					wififlag=0;
					enableAll();
				}
			}
		});
	}
	public void check_bluetooth()
	{
		blue.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(blue.isChecked()){
					//Toast.makeText(getApplicationContext(), "Bluetooth button checked", Toast.LENGTH_LONG).show();
				//Bon();
					bluetoothflag=1;
					disableOthers(v.getId());
					
				}
				else{
					//Toast.makeText(getApplicationContext(), "Bluetooth button unchecked", Toast.LENGTH_LONG).show();
				//	Boff();
					bluetoothflag=0;
					enableAll();
				}
			}

			
		});
	}
	public void Boff() {
		// TODO Auto-generated method stub
		mBluetoothAdapter.disable();
	
		
	}

	public void Bon() {
		// TODO Auto-generated method stub
		mBluetoothAdapter.enable();
		 if (mBluetoothAdapter.getScanMode() != BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
		        Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
		        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
		        startActivity(discoverableIntent);
		    }
		
	}
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		currentaccuracy=accuracy;
	}


	int count=0,near=0,far=0,play=0,pause=0;

	Timestamp timestamp ;
	String gesture;
	
	CountDownTimer t = new CountDownTimer(2000,1000) {

		@Override
		public void onTick(long millisUntilFinished) {
			// TODO Auto-generated method stub
			//tv_test.setText("seconds remaining: " + millisUntilFinished / 1000);


		}

		@SuppressWarnings("deprecation")
		@Override
		public void onFinish() {
			// TODO Auto-generated method stub

			count=far-near;
			//tv_test.setText("");
			Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
			Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
			
			if(near==1 && far <1)
			{
				Toast.makeText(getApplicationContext(), "HOLD", Toast.LENGTH_SHORT).show();
				near=0;
				gesture="hold";
				
			}
			else if((near <=1 && far>=1) )
			{
				Toast.makeText(getApplicationContext(), "SWIPE", Toast.LENGTH_SHORT).show();
				near=0;
				far=0;
				gesture="swipe";
			}
			else 
			{
				Toast.makeText(getApplicationContext(), "DOUBLE SWIPE" , Toast.LENGTH_SHORT).show();
				near=0;
				far=0;
				gesture="double swipe";

			}

			if(Math.abs(timestamp.getSeconds()-(System.currentTimeMillis()/1000))>1)
			{
				far=0;
				near=0;
				check="";
			}
			
			if (musicflag==1)
			{
				Log.i("swiped","done");
				
				if(gesture.equals("swipe"))
				{
					play_music("NEXT");
					
					
				}
				else if(gesture.equals("double swipe"))
			    {
					play_music("PREVIOUS");
			    }
				else if(gesture.equals("hold"))
			    {
					if((play==0&& pause==0) || (play==1 && pause ==0))
							play_music("PLAY");
					else
						play_music("PAUSE");
			    }
				
				
			}
			
			if(lockflag == 1)
			{
				if(gesture.equals("swipe"))
				{
					unlock_phone();
				}
			}
			if(wififlag==1)
			{
				if(gesture.equals("swipe"))
				{
					wifi_on();
				}
				else if(gesture.equals("double swipe"))
				{
					wifi_off();
				}
			}
			if(bluetoothflag==1)
			{
				if(gesture.equals("swipe"))
				{
					Bon();
					
				}
				else if(gesture.equals("double swipe"))
				{
				Boff();
				}
			}



		}
	};


	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
		ev=event;
		timestamp = new Timestamp(event.timestamp);
		if(event.values[0]>=0.2f && event.values[0]<4.0f){
			Log.d("in range", "in range" +"");
			//tv_test.setText("in range"+" \t "+sensor.getName()+"\t max range:"+sensor.getMaximumRange());

			t.start();
			near+=1;
			check+="n";



		}

		else if (event.values[0]>=4.0f){
			//tv_test.setText("not in range");
			far+=1;
			check+="f";
		}


	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	//	sensormanager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_FASTEST);
	}

	/*	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		sensormanager.unregisterListener(this);
	}
	 */

	//unlock phone
	@SuppressWarnings("deprecation")
	public void unlock_phone(){
		KeyguardManager km=(KeyguardManager)getSystemService(KEYGUARD_SERVICE);
		@SuppressWarnings("deprecation")
		final KeyguardManager.KeyguardLock kl = km .newKeyguardLock("MyKeyguardLock"); 
		kl.disableKeyguard(); 
		PowerManager pm = (PowerManager) getSystemService(POWER_SERVICE); 
		@SuppressWarnings("deprecation")
		WakeLock wakeLock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.ON_AFTER_RELEASE, "MyWakeLock"); 
		wakeLock.acquire(); 
		wakeLock.release();

	}

	//play music player
	public void play_music(String code){
		long eventtime = SystemClock.uptimeMillis();

		//Play

		if(code.equals("PLAY"))
		{	
			Intent downIntent = new Intent(Intent.ACTION_MEDIA_BUTTON, null);
			KeyEvent downEvent = new KeyEvent(eventtime, eventtime, KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE, 0);
			downIntent.putExtra(Intent.EXTRA_KEY_EVENT, downEvent);
			sendOrderedBroadcast(downIntent, null);
			play=1;
			pause=0;
			Log.i("PLAY","Bitch");
		}
		if(code.equals("PAUSE"))
		{
			//Pause
			Intent upIntent = new Intent(Intent.ACTION_MEDIA_BUTTON, null);
			KeyEvent upEvent = new KeyEvent(eventtime, eventtime, KeyEvent.ACTION_UP, KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE, 0);
			upIntent.putExtra(Intent.EXTRA_KEY_EVENT, upEvent);
			sendOrderedBroadcast(upIntent, null);
			pause=1;
			play=0;
			Log.i("Pause","Bitch");
		}
		if(code.equals("NEXT"))
		{
			/*NEXT*/
			Intent nextIntent = new Intent(Intent.ACTION_MEDIA_BUTTON, null);
			KeyEvent downEvent1 = new KeyEvent(eventtime, eventtime, KeyEvent.ACTION_DOWN,   KeyEvent.KEYCODE_MEDIA_NEXT, 0);
			nextIntent.putExtra(Intent.EXTRA_KEY_EVENT, downEvent1);
			sendOrderedBroadcast(nextIntent, null);
			Log.i("Next","Bitch");
		}
		if(code.equals("PREVIOUS"))
		{/*PREVIOUS*/
			Intent previousIntent = new Intent(Intent.ACTION_MEDIA_BUTTON, null);
			KeyEvent downEvent2 = new KeyEvent(eventtime, eventtime, KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_MEDIA_PREVIOUS, 0);
			previousIntent.putExtra(Intent.EXTRA_KEY_EVENT, downEvent2);
			sendOrderedBroadcast(previousIntent, null);
			Log.i("Prev","Bitch");
		}
	}


	public void wifi_on(){
		wm.setWifiEnabled(true);
	}

	public void wifi_off(){
		wm.setWifiEnabled(false);
	}

	public void disableOthers(int id ){
		music.setEnabled(false);
		call.setEnabled(false);
		wifi.setEnabled(false);
		lock.setEnabled(false);
		blue.setEnabled(false);
		findViewById(id).setEnabled(true);
	}public void enableAll( ){
		music.setEnabled(true);
		call.setEnabled(true);
		wifi.setEnabled(true);
		lock.setEnabled(true);
		blue.setEnabled(true);
	}
}
