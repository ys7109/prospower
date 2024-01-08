package com.example.student_point

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner

class Register : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register)

        val spinnerSchool: Spinner = findViewById(R.id.spinner_school)
        val spinnerGrade: Spinner = findViewById(R.id.spinner_grade)

        // 학교 옵션 데이터 불러오기
        val schoolOptions = resources.getStringArray(R.array.school)

        // 첫 번째 스피너 어댑터 설정
        val schoolAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, schoolOptions)
        schoolAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerSchool.adapter = schoolAdapter

        // 두 번째 스피너 어댑터 설정
        val gradeAdapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item)
        gradeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerGrade.adapter = gradeAdapter

        // 첫 번째 스피너 선택 리스너 설정
        spinnerSchool.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: android.view.View?,
                position: Int,
                id: Long
            ) {
                // 선택된 항목에 따라서 두 번째 스피너의 아이템 리스트를 결정
                val selectedSchool = parent?.getItemAtPosition(position) as String

                val gradeOptions = if (selectedSchool == "초등학교") {
                    resources.getStringArray(R.array.grade_edu)
                } else {
                    resources.getStringArray(R.array.grade)
                }

                // 두 번째 스피너의 아이템 업데이트
                gradeAdapter.clear()
                gradeAdapter.addAll(gradeOptions.toList())
                gradeAdapter.notifyDataSetChanged()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // 아무 것도 선택되지 않았을 때의 동작(비어있으므로 기다림)
            }
        }
        val main: Button = findViewById(R.id.btn_main)
        main.setOnClickListener {
            val move: Intent = Intent(this, Login::class.java)
            startActivity(move)
            finish()
        }
    }

    override fun onBackPressed() {
        // 뒤로가기 버튼을 무시하고 아무 동작도 수행하지 않음
    }
}