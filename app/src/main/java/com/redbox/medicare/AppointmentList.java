package com.redbox.medicare;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;

import java.net.MalformedURLException;
import java.net.URL;

public class AppointmentList extends AppCompatActivity {

    private Button join;
    private EditText room;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_list);

        join = findViewById(R.id.join);
        room = findViewById(R.id.roomID);
        String text = join.getText().toString();


        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                videoCall(text);
            }
        });

        try {
            JitsiMeetConferenceOptions options = new JitsiMeetConferenceOptions.Builder()
                    .setServerURL(new URL(""))
                    .setRoom("p456")
                    .setAudioMuted(false)
                    .setVideoMuted(false)
                    .setAudioOnly(false)
                    .setWelcomePageEnabled(false)
                    .build();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public void videoCall(String text){

        if(text.length() > 0){
            JitsiMeetConferenceOptions options
                    = new JitsiMeetConferenceOptions.Builder()
                    .setRoom(text)
                    .build();
            JitsiMeetActivity.launch(this,options);
        }

    }

}
//This is for patient appointment.