package com.example.student_point

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject

class Register : AppCompatActivity() {

    private var name: String = ""
    private var id: String = ""
    private var password: String = ""
    private var gender: String = ""
    private var school: String = ""
    private var grade: String = ""
    private var number: String = ""
    private var dialog: AlertDialog? = null
    private var validate: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register)

        val nameText: EditText = findViewById(R.id.nameText)
        val idText: EditText = findViewById(R.id.idText)
        val passwordText: EditText = findViewById(R.id.passwordText)
        val male: RadioButton = findViewById(R.id.male) //male 버튼을 선언
        val female: RadioButton = findViewById(R.id.female) //female 버튼을 선언
        val spinnerSchool: Spinner = findViewById(R.id.spinner_school)
        val spinnerGrade: Spinner = findViewById(R.id.spinner_grade)
        val numberText: EditText = findViewById(R.id.phonenumberText)

        //male 버튼이 눌린 경우 gender 변수에 '남성' 값을 저장
        male.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                gender = "남성"
            }
        }

        //female 버튼이 눌린 경우 gender 변수에 '여성' 값을 저장
        female.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                gender = "여성"
            }
        }

        // 학교 옵션들을 리소스에서 가져오기
        val schoolOptions = resources.getStringArray(R.array.school)

        // 스피너에 사용될 어댑터 생성 및 설정
        val schoolAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, schoolOptions)
        schoolAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerSchool.adapter = schoolAdapter

        // 학년 스피너의 어댑터 생성
        val gradeAdapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item)
        gradeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerGrade.adapter = gradeAdapter

        // 학교 스피너의 선택 이벤트 리스너 설정
        spinnerSchool.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: android.view.View?,
                position: Int,
                id: Long
            ) {
                // 선택된 학교 가져오기
                val selectedSchool = parent?.getItemAtPosition(position) as String

                // 선택된 학교에 따라 학년 옵션들 가져오기
                val gradeOptions = if (selectedSchool == "초등학교") {
                    resources.getStringArray(R.array.grade_edu)
                } else {
                    resources.getStringArray(R.array.grade)
                }

                // 학년 어댑터 업데이트
                gradeAdapter.clear()
                gradeAdapter.addAll(gradeOptions.toList())
                gradeAdapter.notifyDataSetChanged()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // 사용자가 아무것도 선택하지 않았을 때의 동작 (현재는 빈 메서드)
            }
        }

        //main 버튼을 누르면 현재 액티비티를 종료하고 로그인 창으로 이동
        val main: Button = findViewById(R.id.btn_main)
        main.setOnClickListener {
            val move: Intent = Intent(this, Login::class.java)
            startActivity(move)
            finish()
        }

        val btnRegister: Button = findViewById(R.id.register)
        btnRegister.setOnClickListener {
            val name = nameText.text.toString()
            val id = idText.text.toString()
            val password = passwordText.text.toString()
            val gender = gender
            val school: String = spinnerSchool.selectedItem.toString()
            val grade: String = spinnerGrade.selectedItem.toString()
            val number: String = numberText.text.toString()

            if (validate) {
                return@setOnClickListener
            }

            if (name.isEmpty() || id.isEmpty() || password.isEmpty() || gender.isEmpty() || school.isEmpty() ||
                grade.isEmpty() || number.isEmpty()
            ) {
                Toast.makeText(this@Register, "모든 항목을 입력해주세요.", Toast.LENGTH_SHORT).show()
            } else {
                val responseListener = Response.Listener<String> { response ->
                    try {
                        val jsonResponse = JSONObject(response)
                        val success = jsonResponse.getBoolean("success")
                        if (success) {
                            validate = true
                            Toast.makeText(this@Register, "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show()
                            finish()
                        } else {
                            Toast.makeText(this@Register, "사용할 수 없는 아이디입니다.", Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                val validateRequest = Validate_request(id, responseListener)
                val queue = Volley.newRequestQueue(this)
                queue.add(validateRequest)

                // Register_request 객체를 onResponse 함수 내부로 이동
                val registerRequest = Register_request(name, id, password, gender, school, grade, number, responseListener)
                queue.add(registerRequest)
            }
        }
    }

    override fun onBackPressed() {
        //뒤로가기 버튼을 눌렀을 경우 해당 버튼의 입력을 무시함
    }
}