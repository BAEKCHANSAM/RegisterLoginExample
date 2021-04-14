package com.example.registerloginexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class  RegisterActivity extends AppCompatActivity {

    private EditText et_id , et_pass , et_name , et_age;
    private Button btn_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        et_id = (EditText)findViewById(R.id.et_id);
        et_pass = (EditText)findViewById(R.id.et_pass);
        et_name = (EditText)findViewById(R.id.et_name);
        et_age = (EditText)findViewById(R.id.et_age);
        btn_register = (Button)findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userID = et_id.getText().toString();
                String userPass = et_pass.getText().toString();
                String userName = et_name.getText().toString();
                int userAge = Integer.parseInt(et_age.getText().toString());

                Response.Listener<String>responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success =jsonObject.getBoolean("success");
                            if(success) {
                                Toast.makeText(getApplicationContext(), "회원 등록에 성공하셨습니다", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(intent);
                            }else {
                                Toast.makeText(getApplicationContext(),"회원 등록에 실패하셨습니다",Toast.LENGTH_SHORT).show();
                                return;
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                //서버로 볼리를 이용해서 요청하는 구문
                RegisterRequest registerRequest = new RegisterRequest(userID , userPass , userName , userAge , responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequest);

            }
        });
    }
}