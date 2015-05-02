package com.example.sensor;

import android.app.ActivityManager;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;





/**
 * This class will implement the Sensor component to get 
 * temperature using device sensors.
 * @author User
 *
 */
public class TemperatureSensor implements SensorEventListener{
	private static final String SENSOR_SERVICE = "sensor";
	private SensorManager sensorManager;
    private Sensor temperatureSensor;
    Context appContext;
    
    public TemperatureSensor() {
    	
    }
    
    
    /**
     * This method gets the temperature of the location of user.
     * If the temperature sensor is found in the device it will process 
     * the get request for temperature if not found will give message to the user.
     * 
     */
    public String getTemperature(Context context)
    {
    	
    	//temperatureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
    	//ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.SENSOR_SERVICE);
    	
    	appContext= context; 
    	sensorManager = (SensorManager)context.getSystemService(Context.SENSOR_SERVICE);
    	temperatureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
    		if (null != temperatureSensor) {
    		    /* get the temperature using SensorManager */
    			return "80 F";
    		}
    		else {
    			
    		    /* Else return 'No Sensor' message to user*/
    			return "No Sensor detected on Device";
    		}
    }


	public void onSensorChanged(int sensor, float[] values) {
		// TODO Auto-generated method stub
		
	}


	public void onAccuracyChanged(int sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}

}
