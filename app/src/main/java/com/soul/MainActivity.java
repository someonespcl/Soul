
package com.soul;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.soul.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
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
