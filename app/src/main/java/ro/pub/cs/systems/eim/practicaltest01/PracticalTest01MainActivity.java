package ro.pub.cs.systems.eim.practicaltest01;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class PracticalTest01MainActivity extends AppCompatActivity {

    Button button_1, button_2, second_act;
    EditText text_1, text_2;
    int serviceStatus = 0;

//  BROADCAST RECEIVER
    private MessageBroadcastReceiver messageBroadcastReceiver = new MessageBroadcastReceiver();
    private class MessageBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("[Message]", intent.getStringExtra("message"));
        }
    }

    private IntentFilter intentFilter = new IntentFilter();

//    **************************************


//  LISTENERS
    private ButtonClickListener buttonClickListener = new ButtonClickListener();
    private class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.button_1) {
                text_1.setText(String.valueOf(Integer.parseInt(text_1.getText().toString()) + 1));
            } else if (view.getId() == R.id.button_2) {
                text_2.setText(String.valueOf(Integer.parseInt(text_2.getText().toString()) + 1));

            } else if (view.getId() == R.id.second_act) {

//  SECOND ACTIVITY
                Intent intent = new Intent(getApplicationContext(), PracticalTest01SecondaryActivity.class);
                intent.setAction("ro.pub.cs.systems.eim.practicaltest01.intent.action.second");
                intent.putExtra("extra", String.valueOf(Integer.parseInt(text_1.getText().toString()) + Integer.parseInt(text_2.getText().toString())));
                startActivityForResult(intent, 22);
            }

//  SERVICE
            if (Integer.parseInt(text_1.getText().toString())+ Integer.parseInt(text_2.getText().toString()) > Constants.NUMBER_OF_CLICKS_THRESHOLD
                    && serviceStatus == Constants.SERVICE_STOPPED) {
                Intent intent = new Intent(getApplicationContext(), ComputeService.class);
                intent.putExtra("firstNumber", Integer.parseInt(text_1.getText().toString()));
                intent.putExtra("secondNumber", Integer.parseInt(text_2.getText().toString()));
                getApplicationContext().startService(intent);
                serviceStatus = Constants.SERVICE_STARTED;
            }

        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_main);

//  BROADCAST RECEIVER ACTION
        for (int index = 0; index < Constants.actionTypes.length; index++) {
            intentFilter.addAction(Constants.actionTypes[index]);
        }

//  GUI
        button_1 = (Button) findViewById(R.id.button_1);
        button_2 = (Button) findViewById(R.id.button_2);
        second_act = (Button) findViewById(R.id.second_act);

        text_1 = (EditText) findViewById(R.id.textView_1);
        text_2 = (EditText) findViewById(R.id.textView_2);

//  SAVE STATE
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey("first")) {
                text_1.setText(savedInstanceState.getString("first"));
            }
            if (savedInstanceState.containsKey("second")) {
                text_2.setText(savedInstanceState.getString("second"));
            }
        }

//   LISTENERS
        button_1.setOnClickListener(buttonClickListener);
        button_2.setOnClickListener(buttonClickListener);
        second_act.setOnClickListener(buttonClickListener);

    }

//  SAVE STATE
    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("first", text_1.getText().toString());
        savedInstanceState.putString("second", text_2.getText().toString());
    }

//  SECOND ACTIVITY
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        switch(requestCode) {
            case 22:
                if (resultCode == 0) {
                    Toast.makeText(getApplication(), "OK", Toast.LENGTH_LONG).show();
                }
                if (resultCode == -1) {
                    Toast.makeText(getApplication(), "CANCEL", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

//    BROADCAST RECEIVER
    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(messageBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        unregisterReceiver(messageBroadcastReceiver);
        super.onPause();
    }
//  ***********************


//  SERVICE
    @Override
    protected void onDestroy() {
        Intent intent = new Intent(this, ComputeService.class);
        stopService(intent);
        super.onDestroy();
    }

}
