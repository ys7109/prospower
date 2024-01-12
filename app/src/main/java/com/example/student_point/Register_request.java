package com.example.student_point;

import android.util.Log;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import java.util.HashMap;
import java.util.Map;

public class Register_request extends StringRequest {

    final static private String URL = "http://cmarco.dothome.co.kr/UserRegister.php";
    private Map<String, String> parameters;

    public Register_request(String Name, String ID, String Password, String Gender, String School, String Grade, String Number, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("Name", Name);
        parameters.put("ID", ID);
        parameters.put("Password", Password);
        parameters.put("Gender", Gender);
        parameters.put("School", School);
        parameters.put("Grade", Grade);
        parameters.put("Number", Number);

        // 로그 추가
        Log.d("Register_request", "Params: " + parameters.toString());
    }

    @Override
    public Map<String, String> getParams() {
        return parameters;
    }
}
