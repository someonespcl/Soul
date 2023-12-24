package com.soul.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.os.VibratorManager;
import android.util.Patterns;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.soul.databinding.ActivityLoginIntoAcBinding;
import com.soul.utils.ToastMessage;
import com.soul.viewModel.AuthModel;

public class LoginIntoAcActivity extends AppCompatActivity {
    
    private ActivityLoginIntoAcBinding binding;
    private AuthModel authModel;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginIntoAcBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
        authModel = new ViewModelProvider(this).get(AuthModel.class);
        
        authModel.getUserLiveData().observe(this, user -> {
            if(user != null) {
            	startActivity(new Intent(LoginIntoAcActivity.this, DashboardActivity.class));
            }
        });
        
        authModel.getErrorLiveData().observe(this, error -> {
             showToast(error);
        });
    }
    
    public void validateLoginDetails(View view) {
        String email = binding.inputEmail.getText().toString().trim();
        String password = binding.inputPass.getText().toString().trim();
        
        if (email.isEmpty()) {
            shakeNVibrate(binding.inputEmail);
        } else if (password.isEmpty()) {
            shakeNVibrate(binding.inputEmail);
        } else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
        	showToast("Invalid email address");
            shakeNVibrate(binding.inputEmail);
        } else if(password.length() < 6) {
        	showToast("Password must be atleast 6 characters");
            shakeNVibrate(binding.inputPass);
        } else {
            authModel.loginUser(email, password);
        }
    }
    
    private void showToast(String message) {
        ToastMessage.showToast(LoginIntoAcActivity.this, message);
    }
    
    @SuppressWarnings("depreciation")
    private void shakeNVibrate(View v) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            YoYo.with(Techniques.Shake).duration(700).playOn(v);
            VibratorManager vibratorManager =
                    (VibratorManager) getSystemService(Context.VIBRATOR_MANAGER_SERVICE);
            Vibrator vibrator = vibratorManager.getDefaultVibrator();
            vibrator.vibrate(VibrationEffect.createOneShot(128, VibrationEffect.DEFAULT_AMPLITUDE));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            YoYo.with(Techniques.Shake).duration(700).playOn(v);
            Vibrator _vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            _vibrator.vibrate(
                    VibrationEffect.createOneShot(128, VibrationEffect.DEFAULT_AMPLITUDE));
        }
    }
    
    public void goToRegisterActivity(View nxtScreen) {
        startActivity(new Intent(LoginIntoAcActivity.this, CreateAccountActivity.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(binding != null) {
            binding = null;
        }
    }
}
