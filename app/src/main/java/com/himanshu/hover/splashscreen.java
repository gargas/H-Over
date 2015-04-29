package com.himanshu.hover;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import com.crashlytics.android.Crashlytics;

public class splashscreen extends Activity {
 
   private static String TAG = splashscreen.class.getName();
   private static long SLEEP_TIME = 4;    // Sleep for some time
 
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      Crashlytics.start(this);
 
      this.requestWindowFeature(Window.FEATURE_NO_TITLE);    // Removes title bar
      this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);    // Removes notification bar
 
      setContentView(R.layout.splash);
 
      // Start timer and launch main activity
      IntentLauncher launcher = new IntentLauncher();
      launcher.start();
   }
 
   private class IntentLauncher extends Thread {
      @Override
      /**
       * Sleep for some time and than start new activity.
       */
      public void run() {
         try {
            // Sleeping
            Thread.sleep(SLEEP_TIME*1000);
         } catch (Exception e) {
            Log.e(TAG, e.getMessage());
         }
 
         // Start main activity
         Intent intent = new Intent(splashscreen.this, MainActivity.class);
         
         splashscreen.this.startActivity(intent);
         splashscreen.this.finish();
      }
   }
}
