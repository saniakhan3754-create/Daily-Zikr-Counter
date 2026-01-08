package com.example.dailyzikrcounter;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.PointerIcon;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class HighestZikrFragment extends Fragment {

    private TextView textViewHighestScore;
    private SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_highest_zikr, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textViewHighestScore = view.findViewById(R.id.textViewHighestScore);
        sharedPreferences = requireActivity().getSharedPreferences("DailyZikrCounterPrefs", Context.MODE_PRIVATE);

        updateHighestScore();

        Button buttonClearHighScore = view.findViewById(R.id.buttonClearHighScore);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            buttonClearHighScore.setPointerIcon(PointerIcon.getSystemIcon(requireContext(), PointerIcon.TYPE_HAND));
        }
        buttonClearHighScore.setOnClickListener(v -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putLong("highestScore", 0L); // Use putLong and 0L for long
            editor.apply();
            updateHighestScore();
        });
    }

    private void updateHighestScore() {
        long highestScore = sharedPreferences.getLong("highestScore", 0L); // Use getLong and 0L for long
        textViewHighestScore.setText(String.valueOf(highestScore));
    }

    @Override
    public void onResume() {
        super.onResume();
        updateHighestScore();
    }
}
