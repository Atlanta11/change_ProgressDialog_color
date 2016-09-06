package com.example.enterprise.changeprogressdialogcolor;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.app.Activity;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.os.Handler;
import android.app.ProgressDialog;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;


public class MainActivity extends Activity {
    private int progressStatus = 0;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the widgets reference from XML layout
        final RelativeLayout rl = (RelativeLayout) findViewById(R.id.rl);
        final Button btn = (Button) findViewById(R.id.btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create a TextView programmatically.
                TextView tv = new TextView(getApplicationContext());

                // Set the layout parameters for TextView
                LayoutParams lp = new RelativeLayout.LayoutParams(
                        LayoutParams.WRAP_CONTENT, // Width of TextView
                        LayoutParams.WRAP_CONTENT); // Height of TextView
                tv.setLayoutParams(lp);
                tv.setPadding(10, 15, 10, 15);
                tv.setGravity(Gravity.CENTER);
                tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
                tv.setText("TITLE OF PROGRESS DIALOG.");
                tv.setTextColor(Color.parseColor("#FF808562"));
                tv.setBackgroundColor(Color.parseColor("#dee5ae"));

                // Initialize a new instance of progress dialog
                final ProgressDialog pd = new ProgressDialog(MainActivity.this);

                // Set progress dialog style horizontal
                pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

                // Set the custom drawable for progress bar
                pd.setProgressDrawable(getDrawable(R.drawable.progressbar_states));

                // Change the background color of progress dialog
                pd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.YELLOW));

                // Set the TextView as custom title for progress dialog
                pd.setCustomTitle(tv);

                // Message for progress dialog
                pd.setMessage("Please wait until finish....");

                pd.setIndeterminate(false);

                // Finally, show the progress dialog
                pd.show();

                // Set the progress status zero on each button click
                progressStatus = 0;

                // Start the lengthy operation in a background thread
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while(progressStatus < 100){
                            // Update the progress status
                            progressStatus +=1;

                            // Try to sleep the thread for 20 milliseconds
                            try{
                                Thread.sleep(20);
                            }catch(InterruptedException e){
                                e.printStackTrace();
                            }

                            // Update the progress bar
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    // Update the progress status
                                    pd.setProgress(progressStatus);
                                    // If task execution completed
                                    if(progressStatus == 100){
                                        // Dismiss/hide the progress dialog
                                        pd.dismiss();
                                    }
                                }
                            });
                        }
                    }
                }).start(); // Start the operation
            }
        });
    }
}
