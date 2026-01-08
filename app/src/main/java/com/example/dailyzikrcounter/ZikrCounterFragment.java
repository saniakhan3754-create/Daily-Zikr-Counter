package com.example.dailyzikrcounter;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ZikrCounterFragment extends Fragment {

    private long counter = 0;
    private TextView txtCount;
    private DatabaseReference userDatabase;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_zikr_counter, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        txtCount = view.findViewById(R.id.txtCount);
        Button btnCount = view.findViewById(R.id.btncount);
        Button btnReset = view.findViewById(R.id.btnReset);
        Button btnPlus10 = view.findViewById(R.id.btn_plus_10);
        Button btnPlus100 = view.findViewById(R.id.btn_plus_100);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user == null) {
            Toast.makeText(getContext(), "User not logged in", Toast.LENGTH_LONG).show();
            Log.e("ZIKR_DEBUG", "User is NULL");
            return;
        }

        String uid = user.getUid();
        userDatabase = FirebaseDatabase.getInstance()
                .getReference("users")
                .child(uid);

        Log.d("ZIKR_DEBUG", "User UID: " + uid);

        // 🔥 Always listen for database changes
        userDatabase.child("zikrCount")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            counter = snapshot.getValue(Long.class);
                        } else {
                            counter = 0;
                            userDatabase.child("zikrCount").setValue(counter);
                        }
                        txtCount.setText(String.valueOf(counter));
                        updateHighestZikr();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getContext(),
                                "Database error", Toast.LENGTH_SHORT).show();
                    }
                });

        btnCount.setOnClickListener(v -> increment(1));
        btnPlus10.setOnClickListener(v -> increment(10));
        btnPlus100.setOnClickListener(v -> increment(100));
        btnReset.setOnClickListener(v -> resetCounter());
    }

    private void increment(int value) {
        counter += value;
        userDatabase.child("zikrCount").setValue(counter);
    }

    private void resetCounter() {
        counter = 0;
        userDatabase.child("zikrCount").setValue(counter);
    }

    private void updateHighestZikr() {
        userDatabase.child("highestZikr")
                .get()
                .addOnSuccessListener(snapshot -> {
                    long highest = snapshot.exists()
                            ? snapshot.getValue(Long.class)
                            : 0;
                    if (counter > highest) {
                        userDatabase.child("highestZikr").setValue(counter);
                    }
                });
    }
}
