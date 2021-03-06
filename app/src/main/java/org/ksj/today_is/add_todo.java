package org.ksj.today_is;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

public class add_todo extends AppCompatActivity {
    Integer year, month, day, default_year, default_month, default_day;
    Button save_button, cancel_button;
    EditText todo_editText;//할 일 입력하는 EditText
    String todo_string;//할 일 입력하는 EditText에서 받아온 값
    String temp_month, temp_day, temp_date;//temp_date == 년,월,일 붙여 String으로 바꾼 값
    String temp_start_time = "0900", temp_notice_time = "0900";//9시 00분으로 초기화

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todo);

        /////SQLite(DB 연결)
        DBHelper dbHelper = new DBHelper(add_todo.this, "today_is.db", null, 1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        dbHelper.onCreate(db);
        /////SQLite(DB 연결)

        /////findViewById
        save_button = (Button) findViewById(R.id.save_button);
        cancel_button = (Button) findViewById(R.id.cancel_button);
        todo_editText = (EditText) findViewById(R.id.todo_editText);
        /////findViewById

        /////todo_editText
//        포커스 벗어나면 키보드 숨기기
        /////todo_editText

        long now = System.currentTimeMillis();
        Date date = new Date(now);

        default_year = date.getYear()+1900;
        default_month = date.getMonth()+1;
        default_day = date.getDate();

        /////MainActivity로부터 값 받아옴
        Intent intent = getIntent();
        year = intent.getIntExtra("year",default_year);
        month = intent.getIntExtra("month",default_month);
        day = intent.getIntExtra("day",default_day);

        if(month.toString().length() == 1){
            temp_month = "0" + month.toString();
        }//1~9월
        else{
            temp_month = month.toString();
        }//10~12월
        
        if(day.toString().length() == 1){
            temp_day = "0" + day.toString();
        }//1~9일
        else{
            temp_day = day.toString();
        }//10~31일
        
//        Log.d("년월",year+"년 "+month+"월 "+day+"일");
        /////MainActivity로부터 값 받아옴

        /////save_button
        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /////입력한 ToDo 값 받아와서 처리
                todo_string = todo_editText.getText().toString();
                temp_date = year.toString()+temp_month+temp_day;

                if(todo_string.isEmpty()){
                    Toast.makeText(getApplicationContext(), "할 일을 입력해 주세요.", Toast.LENGTH_SHORT).show();
                }//할 일 아무것도 입력하지 않은 경우
                else{

//                    try {
//                        //DB INSERT
//                        //완료 여부 디폴트값 false
//                        String sql3 = "DROP TABLE main_table;";
////                    String sql3 = "DELETE FROM main_table;";
//                        db.execSQL(sql3); //sql3 삭제해야 함/////////////////////////////////
//
//                        String sql = "INSERT INTO main_table('Date_name','Group_name','Contents','Check_name','Place','Start_time','Notice_time') values('" + temp_date + "','그룹1','" + todo_string + "','false','없음');";
//                        db.execSQL(sql);
//
//                        String sql2 = "SELECT * FROM main_table;";
//                        Cursor cursor = db.rawQuery(sql2, null);
//                        while (cursor.moveToNext()) {
//                            String _id = cursor.getString(0);
//                            String Date_name = cursor.getString(1);
//                            String Group_name = cursor.getString(2);
//                            String Contents = cursor.getString(3);
//                            Integer Check_name = cursor.getInt(4);
//                            String place = cursor.getString(5);//ArrayList로 바꿔야함
//                            String Start_time = cursor.getString(6);
//                            String Notice_time = cursor.getString(7);
//
//                            System.out.println(_id + " " + Date_name + " " + Group_name + " " + Contents + " " + Check_name + " " + place + " " + temp_start_time + " " + temp_notice_time + "\n");
//                        }
//
//                        Intent intent = new Intent();
//                        setResult(RESULT_OK, intent);
//                        finish();//인텐트 종료
//                    }
//                    catch (Exception e){
//                        e.printStackTrace();
//                        Toast.makeText(getApplicationContext(),"오류가 발생했습니다.",Toast.LENGTH_SHORT).show();
//                    }//DB 오류 생긴 경우
                }//할 일 입력한 경우
                /////입력한 ToDo 값 받아와서 처리
            }
        });
        /////okay_button

        /////cancel_button
        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //취소할건지 묻는 다이얼로그
                AlertDialog.Builder builder = new AlertDialog.Builder(add_todo.this);
                builder.setMessage("취소하시겠습니까?");

                builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });//예
                
                builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        /////다이얼로그 종료
                    }
                });//아니오

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        /////cancel_button
    }
}