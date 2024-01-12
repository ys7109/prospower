package com.example.student_point;

import android.util.Log;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import java.util.HashMap;
import java.util.Map;

public class Validate_request extends StringRequest {

    final static private String URL = "http://cmarco.dothome.co.kr/UserCheck.php";
    private Map<String, String> parameters;

    public Validate_request(String ID, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("ID", ID);

        // 로그 추가
        Log.d("Register_request", "Params: " + parameters.toString());
    }

    @Override
    public Map<String, String> getParams() {
        return parameters;
    }
}
