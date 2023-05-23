package com.example.test;
// 23-05-24 04:27자
import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    // 선언 부분
    private DatabaseReference mDatabaseRef;  // firebase DB 객체 선언
    private EditText editText, editText2, editText3, editText4;
    private TextView textView, textView2, textView3, textView4;
    private Button button;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDatabaseRef = FirebaseDatabase.getInstance().getReference();  // firebase 객체 생성

        editText = findViewById(R.id.edit_text);
        editText2 = findViewById(R.id.edit_text2);
        editText3 = findViewById(R.id.edit_text3);
        editText4 = findViewById(R.id.edit_text4);

        textView = findViewById(R.id.text_view);
        textView2 = findViewById(R.id.text_view2);
        textView3 = findViewById(R.id.text_view3);
        textView4 = findViewById(R.id.text_view4);

        button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {  // Button 클릭 시 실행문
            @Override
            public void onClick(View v) {
                String user_id = editText.getText().toString();
                String school = editText2.getText().toString();
                String sex = editText3.getText().toString();
                String card_url = editText4.getText().toString();

                // 데이터 삽입
                DatabaseReference newData = mDatabaseRef.child("users").push();

                newData.child("user_id").setValue(user_id);
                newData.child("school").setValue(school);
                newData.child("sex").setValue(sex);
                newData.child("card_url").setValue(card_url);
            }
        });

        mDatabaseRef.child("users").addValueEventListener(new ValueEventListener() { // 데이터 값 관련 메소드
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) { // 데이터가 변할 때마다 실행 됨
                Log.d("Firebase", "Data has changed");

                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    String user_id = userSnapshot.child("user_id").getValue(String.class);
                    String school = userSnapshot.child("school").getValue(String.class);
                    String sex = userSnapshot.child("sex").getValue(String.class);
                    String card_url = userSnapshot.child("card_url").getValue(String.class);

                    textView.setText(user_id);
                    textView2.setText(school);
                    textView3.setText(sex);
                    textView4.setText(card_url);


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) { // 에러 처리를 수행하는 코드
                Log.e("Firebase", "Data retrieval cancelled: " + databaseError.getMessage());
            }
        });
    }
}
