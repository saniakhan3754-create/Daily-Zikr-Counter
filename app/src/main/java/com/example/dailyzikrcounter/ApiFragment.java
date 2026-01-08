package com.example.dailyzikrcounter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class ApiFragment extends Fragment {

    private TextView textViewTime;
    private TextView textViewTimezone;
    private RequestQueue requestQueue;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_api, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textViewTime = view.findViewById(R.id.textViewTime);
        textViewTimezone = view.findViewById(R.id.textViewTimezone);

        requestQueue = Volley.newRequestQueue(requireContext());
    }

    @Override
    public void onResume() {
        super.onResume();
        fetchTime();
    }

    private void fetchTime() {
        String url = "http://worldtimeapi.org/api/ip";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String datetime = jsonObject.getString("datetime");
                        String timezone = jsonObject.getString("timezone");

                        textViewTime.setText(datetime);
                        textViewTimezone.setText(timezone);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getContext(), "Error parsing data", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(getContext(), "Error fetching data", Toast.LENGTH_SHORT).show());

        requestQueue.add(stringRequest);
    }
}
