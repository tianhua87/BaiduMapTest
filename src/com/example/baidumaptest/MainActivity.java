package com.example.baidumaptest;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.MapView;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends Activity {

	private MapView mapview;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //要在setContentView之前调用，不然会出错
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        
        mapview=(MapView) findViewById(R.id.mapview);
    } 
    
      
    protected void onDestroy()
    {
    	super.onDestroy();
    	mapview.onDestroy();
    }
    
    protected void onPause()
    {
    	super.onPause();
    	mapview.onPause();
    }
    
    protected void onResume()
    {
    	super.onResume();
    	mapview.onResume();
    }

}
