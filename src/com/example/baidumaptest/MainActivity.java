package com.example.baidumaptest;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;


public class MainActivity extends Activity {

	private MapView mapview;
	private BaiduMap baidumap;
	private LocationManager locationManager;
	private Location location;
	private String provider;
	private boolean isFirstLocate=true;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //要在setContentView之前调用，不然会出错
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        
        mapview=(MapView) findViewById(R.id.mapview);
        baidumap=mapview.getMap();
        locationManager=(LocationManager) getSystemService(Context.LOCATION_SERVICE);
        List<String> providerList=locationManager.getProviders(true);
        
        if(providerList.contains(LocationManager.NETWORK_PROVIDER))
        {
        	provider=LocationManager.NETWORK_PROVIDER;
        }
        else if(providerList.contains(LocationManager.GPS_PROVIDER))
        {
        	provider=LocationManager.GPS_PROVIDER;
        }
        else
        {
        	Toast.makeText(this, "没有可用的位置提供器", Toast.LENGTH_SHORT).show();
        	return ;
        }
        location=locationManager.getLastKnownLocation(provider);
        
        if(location!=null)
        	navigateTo(location);
        else
        {
        	Toast.makeText(this, provider, Toast.LENGTH_SHORT).show();
        //	return ;
        }
        
        locationManager.requestLocationUpdates(provider, 5000, 5, locationListener);
        
    } 
    
    private void navigateTo(Location location)
    {
    	if(isFirstLocate)
    	{
    		LatLng ll=new LatLng(location.getLatitude(), location.getLongitude());
    		
    		MapStatusUpdate mapUpdate=MapStatusUpdateFactory.newLatLng(ll);
    		baidumap.animateMapStatus(mapUpdate);
    		mapUpdate=MapStatusUpdateFactory.zoomTo(16f);
    		baidumap.animateMapStatus(mapUpdate);
    		isFirstLocate=false;
    	}
    }
    
    
    private LocationListener locationListener=new LocationListener() {
		
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			
		}
		
		@Override
		public void onProviderEnabled(String provider) {
			// TODO 自动生成的方法存根
			
		}
		
		@Override
		public void onProviderDisabled(String provider) {
			// TODO 自动生成的方法存根
			
		}
		
		@Override
		public void onLocationChanged(Location location) {
			if(location!=null)
				navigateTo(location);
			
		}
	};
    
      
    protected void onDestroy()
    {
    	super.onDestroy();
    	mapview.onDestroy();
    	if(locationManager!=null)
    		locationManager.removeUpdates(locationListener);
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
