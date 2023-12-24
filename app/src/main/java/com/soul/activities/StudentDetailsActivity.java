package com.soul.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.soul.databinding.ActivityStudentDetailsBinding;

public class StudentDetailsActivity extends AppCompatActivity {
    
    private ActivityStudentDetailsBinding binding;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStudentDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(binding != null) {
        	binding = null;
        }
    }
    
}
