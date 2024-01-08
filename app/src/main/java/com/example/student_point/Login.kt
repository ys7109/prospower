package com.example.student_point

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class Login : AppCompatActivity() {

    private var backPressedTime: Long = 0 //backPressedTime 변수의 값을 0으로 초기화
    private val delay: Long = 2000 // 2초(2000ms) 내에 두 번 눌러야 종료

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        val login: Button = findViewById(R.id.btn_join)
        login.setOnClickListener {
            val move: Intent = Intent(this,Register::class.java)
            startActivity(move)
            finish()
        }
    }

    //뒤로가기 버튼을 누 번 누를 시 종료하는 코드 작성
    override fun onBackPressed() {
        //뒤로가기 버튼을 누를 시 Toast로 메시지를 생성해 보여주고 현재 시간을 기록
        if(System.currentTimeMillis() - backPressedTime >= delay){
            Toast.makeText(this, "한 번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show()
            backPressedTime = System.currentTimeMillis()
        }
        
        //두번째 뒤로가기 버튼이 눌린 시간이 위쪽에 선언된 delay 보다 빠르게 이루어졌을 경우 프로그램을 종료
        else{
            super.onBackPressed()
        }
    }
}