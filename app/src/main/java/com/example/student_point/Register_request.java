package com.example.student_point;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class Register_request extends StringRequest {

    final static private String URL = "https://cmarco.mycafe24.com/UserRegister.php";
    private Map<String, String> parameters;

    public  Register_request(String 이름, String 아이디, String 비밀번호, String 성별, String 학교, String 학년, String 전화번호, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("이름", 이름);
        parameters.put("아이디", 아이디);
        parameters.put("비밀번호", 비밀번호);
        parameters.put("성별", 성별);
        parameters.put("학교", 학교);
        parameters.put("학년", 학년);
        parameters.put("전화번", 전화번호);
    }
}
