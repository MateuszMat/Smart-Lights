package com.example.lights;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
public class MainActivity extends AppCompatActivity {
    private EditText Username;
    private EditText Password;
    private TextView Attempts;
    private Button Login;
    private int counter = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Username = (EditText)findViewById(R.id.username);
        Password = (EditText)findViewById(R.id.password);
        Attempts = (TextView)findViewById(R.id.attempts);
        Login = (Button)findViewById(R.id.login);

        Attempts.setText("No of Attempts remaining: 5");

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate(Username.getText().toString(), Password.getText().toString());
            }
        });
    }

    private void validate(String UserName, String UserPassword){
        if(UserName.equals("admin") && UserPassword.equals("admin")){
            Intent i = new Intent(MainActivity.this, Main2Activity.class);
            startActivity(i);
        }else{
            counter--;
            Attempts.setText("No of Attempts remaining: "+counter);
            if(counter == 0){
                Login.setEnabled(false);
            }
        }
    }
}
