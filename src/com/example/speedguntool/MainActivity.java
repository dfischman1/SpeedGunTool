package com.example.speedguntool;

import android.app.Activity;
import android.os.Bundle;
//import android.os.Environment;
import android.view.View;
//import android.widget.EditText;
import android.widget.TextView;
import android.content.DialogInterface;
import android.app.AlertDialog;
//import android.widget.ImageView;
import android.content.Intent;
import android.widget.Toast;
/*
import java.io.FileReader;
import java.io.IOException;
import java.io.FileReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;*/
public class MainActivity extends Activity {

   // Array of speed frequencies
    int[] speeds;
    // Number of speeds
    int numSpeeds;
    // Average speed
    float averageSpeed;
    // Highest speed
    int highestSpeed;
    // Number of drivers speeding
    int numSpeeding;
    // Number of cars, vans, and trucks/buses
    int numCars, numVans, numTrucksBuses;
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_survey);

        speeds = new int[80];
        numSpeeds = 0;
        averageSpeed = 0;
        highestSpeed = 0;
        numSpeeding = 0;
        numCars = 0;
        numVans = 0;
        numTrucksBuses = 0;

    }

    public void buttonPress(View view) {

        TextView textbox = (TextView) findViewById(R.id.new_speed);
        String newSpeed = textbox.getText().toString();

        int viewID = view.getId();
        switch (viewID) {
        case R.id.button1:
            textbox.setText(newSpeed + "1");
            break;
        case R.id.button2:
            textbox.setText(newSpeed + "2");
            break;
        case R.id.button3:
            textbox.setText(newSpeed + "3");
            break;
        case R.id.button4:
            textbox.setText(newSpeed + "4");
            break;
        case R.id.button5:
            textbox.setText(newSpeed + "5");
            break;
        case R.id.button6:
            textbox.setText(newSpeed + "6");
            break;
        case R.id.button7:
            textbox.setText(newSpeed + "7");
            break;
        case R.id.button8:
            textbox.setText(newSpeed + "8");
            break;
        case R.id.button9:
            textbox.setText(newSpeed + "9");
            break;
        case R.id.button0:
            textbox.setText(newSpeed + "0");
            break;
        case R.id.button_clear:
            textbox.setText("");
            break;
        case R.id.button_add_car:
        case R.id.button_add_van:
        case R.id.button_add_truck_bus:
            if (newSpeed.equals("")) {
                break;
            }
            int nextSpeed = Integer.parseInt(newSpeed);
            if (nextSpeed > (speeds.length - 1)) {
                // Tell the user the speed is too high and don't do anything
                Toast.makeText(getApplicationContext(),
                               "Invalid speed: " + nextSpeed + "\nOnly " +
                               "speeds within the range 0-" +
                               (speeds.length - 1) + " are allowed",
                               Toast.LENGTH_LONG)
                    .show();
                textbox.setText("");
                break;
            }
            
            // Update the data
            speeds[nextSpeed]++;
            if (nextSpeed >= 30) {
                numSpeeding++;
            }
            if (nextSpeed > highestSpeed) {
                highestSpeed = nextSpeed;
            }
            averageSpeed = ((averageSpeed * numSpeeds) + nextSpeed) /
                           (numSpeeds + 1);
            numSpeeds++;

            // Handle the different vehicle types
            if (viewID == R.id.button_add_car) {
                numCars++;
            }
            else if (viewID == R.id.button_add_van) {
                numVans++;
            }
            else {
                numTrucksBuses++;
            }
            
            // Update the widgets
            ((TextView) findViewById(R.id.num_cars))
                .setText(getString(R.string.num_cars) + numSpeeds);
            ((TextView) findViewById(R.id.average_speed))
                .setText(getString(R.string.average_speed) +
                         String.format("%.2f", averageSpeed));
            ((TextView) findViewById(R.id.percent_speeding))
                .setText(getString(R.string.percent_speeding) +
                         String.format("%.2f",
                                       (float) 100 * numSpeeding / numSpeeds) +
                         "%");
            ((TextView) findViewById(R.id.highest_speed))
                .setText(getString(R.string.highest_speed) + highestSpeed);
            textbox.setText("");
            break;
        case R.id.button_data:
        	Intent data = new Intent(this, XYChartBuilder.class);
            // Start the new intent
            startActivity(data);
            break;
        }
        
    }

    public void startSurvey(View view) {

        setContentView(R.layout.main);

    }

    public void alert(String title, String msg) {
        new AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(msg)
            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
            /*
            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
            */
            .show();
    }
    
}
