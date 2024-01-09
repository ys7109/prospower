package com.example.student_point;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

//user의 아이디를 체크하여 회원가입이 가능한 아이디인지 확인하는 기능을 수행하는 클래스
public class Validate_request extends StringRequest {

    final static private String URL = "http://cmarco.mycafe24.com/UserCheck.php";
    private Map<String, String> parameters;

    public  Validate_request(String 아이디, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("아이디", 아이디);
    }

    @Override
    public Map<String, String> getParams() {
        return parameters;
    }
}
