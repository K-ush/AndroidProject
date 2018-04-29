package com.example.kush.androidproject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // 변수 선언
    TextView tv_id, tv_name;

    EditText id, pass, name;
    Button signup, signin;

    SharedPreferences sp;

    // 회원가입버튼 클릭 여부
    int checkSignUP = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 변수들 값 할당
        tv_id = (TextView) findViewById(R.id.textId);
        tv_name = (TextView) findViewById(R.id.textName);

        id = (EditText) findViewById(R.id.insertID);
        pass = (EditText) findViewById(R.id.insertPASS);
        name = (EditText) findViewById(R.id.insertName);

        signup = (Button) findViewById(R.id.signUP);
        signin = (Button) findViewById(R.id.signIN);

        // 버튼에 클릭리스너 달아줌
        signup.setOnClickListener(this);
        signin.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        // 변수선언, 값 할당
        String st_id, st_pass, st_name;
        st_id = id.getText().toString();
        st_pass = pass.getText().toString();
        st_name = name.getText().toString();

        // 공유 프리퍼런스 생성
        sp = getSharedPreferences("user", Activity.MODE_PRIVATE);

        if (view == signup) {
            // checkSignUP값이 0 => signup 버튼 처음누름
            if (checkSignUP == 0) {

                name.setText("");

                // name입력 EditText, TextView height 키워서 보이게함
                tv_name.getLayoutParams().height = tv_id.getLayoutParams().height;
                tv_name.requestLayout();
                name.getLayoutParams().height = id.getLayoutParams().height;
                name.requestLayout();

                checkSignUP = 1;

                // checkSignUP 값이 1 => signup버튼 눌렀었음
            } else if (checkSignUP == 1) {

                // 입력요소 값 여부 체크
                if (st_id.isEmpty()) {
                    Toast.makeText(this, "id가 비어있습니다.", Toast.LENGTH_SHORT).show();
                } else if (st_pass.isEmpty()) {
                    Toast.makeText(this, "pass가 비어있습니다.", Toast.LENGTH_SHORT).show();
                } else if (st_name.isEmpty()) {
                    Toast.makeText(this, "name이 비어있습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    // 값이 다 들어있을때

                    checkSignUP = 0;

                    // 공유프리퍼런스에 id를 키값으로 저장된 값이 없을때
                    if ("".equals(sp.getString(st_id, ""))) {
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString(st_id, st_pass);
                        editor.putString(st_id+"_name", st_name);

                        editor.commit();
                        Toast.makeText(this, "회원가입 완료!", Toast.LENGTH_SHORT).show();

                        // name관련요소들 불가시화
                        tv_name.getLayoutParams().height = 0;
                        tv_name.requestLayout();
                        name.getLayoutParams().height = 0;
                        name.requestLayout();
                    }
                    // 저장된 값이 있을때D
                    else{
                        Toast.makeText(this, "이미 있는 아이디입니다.", Toast.LENGTH_SHORT).show();
                        id.setText("");
                    }
                }
            }
        }
        if (view == signin) {
            // id, pass 공백체크
            if (st_id.isEmpty()) {
                Toast.makeText(this, "id가 비어있습니다.", Toast.LENGTH_SHORT).show();
            } else if (st_pass.isEmpty()) {
                Toast.makeText(this, "pass가 비어있습니다.", Toast.LENGTH_SHORT).show();
            } else{
                // 공유프리퍼런스에서 가져오는 값과 입력된 비밀번호가 같을때 true
                boolean login = st_pass.equals(sp.getString(st_id, "")) ? true : false;

                st_name = sp.getString(st_id+"_name", "");
                if(login){
                    Toast.makeText(this, "어서오세요. " + st_name + "님", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), DirectoryActivity.class);
                    // intent에 로그인한 유저의 정보를 넣어서 전달
                    intent.putExtra("user", st_id);
                    startActivity(intent);

                } else{
                    Toast.makeText(this, "잘못된 정보입니다. 다시 입력하세요.", Toast.LENGTH_SHORT).show();
                    id.setText("");
                    pass.setText("");
                }
            }

        }
    }
}
