package com.example.lights;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import com.pubnub.api.Callback;
import com.pubnub.api.Pubnub;
import com.pubnub.api.PubnubError;
import org.json.JSONException;
import org.json.JSONObject;

public class Main2Activity extends AppCompatActivity {
    SeekBar RedBar, GreenBar, BlueBar;
    int redV; int greenV; int blueV;

    private Pubnub myPubNub;
    public static final String PUBLISH_KEY = "pub-c-20dd2f14-3556-4fec-b979-79a906919966";
    public static final String SUBSCRIBE_KEY = "sub-c-0cdaa1ba-5805-11ea-80a4-42690e175160";
    public static final String CHANNEL = "phue";

    private boolean pHueOn = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        initPubNub();

        RedBar   = (SeekBar) findViewById(R.id.Red);
        GreenBar = (SeekBar) findViewById(R.id.Green);
        BlueBar  = (SeekBar) findViewById(R.id.Blue);
    }
    public void initPubNub(){
        this.myPubNub = new Pubnub(
                PUBLISH_KEY,
                SUBSCRIBE_KEY
        );
        this.myPubNub.setUUID("AndroidP");
        subscribe();
    }
    public void subscribe(){
        try {
            this.myPubNub.subscribe(CHANNEL, new Callback() {
                @Override
                public void connectCallback(String channel, Object message) {
                    Log.d("PUBNUB","SUBSCRIBE : CONNECT on channel:" + channel
                            + " : " + message.getClass() + " : "
                            + message.toString());
                }

                @Override
                public void disconnectCallback(String channel, Object message) {
                    Log.d("PUBNUB","SUBSCRIBE : DISCONNECT on channel:" + channel
                            + " : " + message.getClass() + " : "
                            + message.toString());
                }

                public void reconnectCallback(String channel, Object message) {
                    Log.d("PUBNUB","SUBSCRIBE : RECONNECT on channel:" + channel
                            + " : " + message.getClass() + " : "
                            + message.toString());
                }

                @Override
                public void successCallback(String channel, Object message) {
                    Log.d("PUBNUB","SUBSCRIBE : " + channel + " : "
                            + message.getClass() + " : " + message.toString());
                }

                @Override
                public void errorCallback(String channel, PubnubError error) {
                    Log.d("PUBNUB","SUBSCRIBE : ERROR on channel " + channel
                            + " : " + error.toString());
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void publish(int red, int green, int blue){
        JSONObject js = new JSONObject();
        try {
            js.put("RED",   red);
            js.put("GREEN", green);
            js.put("BLUE",  blue);
        } catch (JSONException e) { e.printStackTrace(); }

        Callback callback = new Callback() {
            public void successCallback(String channel, Object response) {
                Log.d("PUBNUB",response.toString());
            }
            public void errorCallback(String channel, PubnubError error) {
                Log.d("PUBNUB",error.toString());
            }
        };
        this.myPubNub.publish(CHANNEL, js, callback);
    }
    public void setRGBSeeks(int red, int green, int blue){
        RedBar.setProgress(red);
        GreenBar.setProgress(green);
        BlueBar.setProgress(blue);
    }
    public void DIFF(View view){
        redV = RedBar.getProgress();
        greenV = GreenBar.getProgress();
        blueV = BlueBar.getProgress();
        publish(redV, greenV, blueV);
    }
    public void OFF(View view){
        publish(0, 0, 0);
        setRGBSeeks(0, 0, 0);
    }
    public void ON(View view){
        publish(255, 255, 255);
        setRGBSeeks(0, 0, 0);
    }

}
