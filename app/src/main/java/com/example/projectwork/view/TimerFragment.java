package com.example.projectwork.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler; 
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.projectwork.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TimerFragment extends Fragment {
    private Button btnExit;




    private TextView tvTimer, tvFirstLogin;
    private Handler handler = new Handler();
    private long totalTime;        
    private long startTime;       

    private SharedPreferences prefs;

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            long current = System.currentTimeMillis();
            long elapsed = current - startTime;
            long displayTime = totalTime + elapsed;

            tvTimer.setText(formatTime(displayTime));

            handler.postDelayed(this, 1000);
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = null;


        view = inflater.inflate(R.layout.fragment_timer, container, false);
        btnExit = view.findViewById(R.id.btn_back);
        btnExit.setOnClickListener(v -> {
               
            new AlertDialog.Builder(getContext())
                    .setTitle("Exit App")
                    .setMessage("Are you sure you want to exit?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        getActivity().finishAffinity(); //Exit the Application
                    })
                    .setNegativeButton("No", null)
                    .show();
        });

        tvTimer = view.findViewById(R.id.tv_timer);
        tvFirstLogin = view.findViewById(R.id.tv_first_login);

        prefs = requireContext()
                .getSharedPreferences("timer_prefs", Context.MODE_PRIVATE);

        totalTime = prefs.getLong("TOTAL_TIME", 0);
        startTime = System.currentTimeMillis();

        // First login date
        long firstLogin = prefs.getLong("FIRST_LOGIN", 0);
        if (firstLogin == 0) {
            firstLogin = System.currentTimeMillis();
            prefs.edit().putLong("FIRST_LOGIN", firstLogin).apply();
        }

        tvFirstLogin.setText("First login: " + formatDate(firstLogin));

        handler.post(runnable);

        return view;
    }


    @Override
    public void onPause() {
        super.onPause();
        saveTime();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        saveTime();
    }

    private void saveTime() {
        long now = System.currentTimeMillis();
        long session = now - startTime;
        totalTime += session;

        prefs.edit().putLong("TOTAL_TIME", totalTime).apply();
    }

    private String formatTime(long millis) {
        int seconds = (int) (millis / 1000) % 60;
        int minutes = (int) ((millis / (1000 * 60)) % 60);
        int hours = (int) (millis / (1000 * 60 * 60));

        return String.format(Locale.getDefault(),
                "%02d:%02d:%02d", hours, minutes, seconds);
    }

    private String formatDate(long millis) {
        SimpleDateFormat sdf =
                new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        return sdf.format(new Date(millis));
    }

}
