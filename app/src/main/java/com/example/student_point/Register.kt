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
import org.json.JSONObject
import java.util.jar.Attributes.Name

public class Register : AppCompatActivity() {

    private var UserName: String = ""
    private var UserID: String = ""
    private var UserPassword: String = ""
    private var Gender: String = ""
    private var UserSchool: String = ""
    private var UserGrade: String = ""
    private var UserNumber: String = ""
    private var validate: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register)

        val nameText: EditText = findViewById(R.id.nameText)
        val idText: EditText = findViewById(R.id.idText)
        val passwordText: EditText = findViewById(R.id.passwordText)
        val male: RadioButton = findViewById(R.id.male)
        val female: RadioButton = findViewById(R.id.female)
        val spinner_school: Spinner = findViewById(R.id.spinner_school)
        val spinner_grade: Spinner = findViewById(R.id.spinner_grade)
        val phonenumberText: EditText = findViewById(R.id.phonenumberText)

        val spinnerSchool: Spinner = findViewById(R.id.spinner_school)
        val spinnerGrade: Spinner = findViewById(R.id.spinner_grade)

        //male 버튼이 눌린경우 Gender에 '남성'을 저장
        male.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                Gender = "남성"
            }
        }
        //female 버튼이 눌린경우 Gender에 '여성'을 저장
        female.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                Gender = "여성"
            }
        }

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

        //btn_main 버튼을 누르면 로그인 창으로 이동
        val main: Button = findViewById(R.id.btn_main)
        main.setOnClickListener {
            val move: Intent = Intent(this, Login::class.java)
            startActivity(move)
            finish()
        }

        //가입하기(btn_register) 버튼을 누른 경우 입력된 내용을 받아옴
        val btn_register: Button = findViewById(R.id.register)
        btn_register.setOnClickListener {
            // 클릭 이벤트 처리
            val Name = nameText.text.toString()
            val ID = idText.text.toString()
            val PW = passwordText.text.toString()
            val Gender = Gender
            val School: String = spinner_school.selectedItem.toString()
            val Grade: String = spinner_grade.selectedItem.toString()
            val Number: String = phonenumberText.text.toString()

            //유저가 입력항목을 다 채우지 않았을 경우
            if(Name.isEmpty() || ID.isEmpty() || PW.isEmpty() || Gender.isEmpty() || School.isEmpty() ||
                Grade.isEmpty() || Number.isEmpty()){
                println("모든 항목을 입력해주세요.")
                Toast.makeText(this@Register, "모든 항목을 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
            //입력항목을 모두 채운 경우
            else{
                //아이디 중복 확인 로직
                if (!validate) {
                    println("사용중인 아이디 입력")
                    val builder = AlertDialog.Builder(this)
                    val dialog = builder.setMessage("이미 사용 중인 아이디입니다.")
                        .setNegativeButton("확인", null)
                        .create()
                    dialog.show()
                    validate = true;
                }
                //입력항목을 모두 채웠으며 아이디가 중복되지 않은 경우 가입완료 메세지를 출력하고 현재 레이아웃을 종료후 로그인창으로 이동
                else{
                    Toast.makeText(this@Register, "가입이 완료되었습니다.", Toast.LENGTH_SHORT).show()
                    val move: Intent = Intent(this, Login::class.java)
                    startActivity(move)
                    finish()
                }
            }
        }
        val registerRequest = Register_request(
            UserName, UserID, UserPassword, Gender, UserSchool, UserGrade, UserNumber,
            Response.Listener<String> { response ->
                try {
                    val jsonResponse = JSONObject(response)
                    val success = jsonResponse.getBoolean("success")
                    if (success) {
                        // 중복 확인 성공
                        validate = true
                        Toast.makeText(
                            this@Register,
                            "사용 가능한 아이디입니다.",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        // 중복 확인 실패
                        validate = false
                        Toast.makeText(
                            this@Register,
                            "이미 사용 중인 아이디입니다.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        )
        val queue = Volley.newRequestQueue(this@Register)
        queue.add(registerRequest)
    }

    override fun onBackPressed() {
        //이 레이아웃 전체에서 뒤로가기 버튼을 무시하고 아무 동작도 수행하지 않음
    }
}