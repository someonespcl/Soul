package com.soul.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import android.os.VibratorManager;
import android.content.Context;
import android.os.Vibrator;
import android.os.VibrationEffect;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.soul.databinding.ActivityCreateAccountBinding;
import com.soul.userdetails.UserDetails;
import com.soul.utils.ToastMessage;
import com.soul.viewModel.AuthModel;

public class CreateAccountActivity extends AppCompatActivity {
    
    private ActivityCreateAccountBinding binding;
    private AuthModel viewModel;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateAccountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
        viewModel = new ViewModelProvider(this).get(AuthModel.class);
        
        viewModel.getUserLiveData().observe(this, user -> {
            if(user != null) {
            	startActivity(new Intent(CreateAccountActivity.this, DashboardActivity.class));
            }
        });
        
        viewModel.getErrorLiveData().observe(this, error -> {
            showToast(error);
        });
        
        
    }

    public void validateDetails(View view) {
        String name = binding.inputName.getText().toString().trim();
        String phnNo = binding.inputPhnNum.getText().toString();
        String email = binding.createMail.getText().toString().trim();
        String password = binding.createPass.getText().toString().trim();

        if (name.isEmpty()) {
            shakeNVibrate(binding.inputName);
        } else if (phnNo.isEmpty()) {
            shakeNVibrate(binding.inputPhnNum);
        } else if (email.isEmpty()) {
            shakeNVibrate(binding.createMail);
        } else if (password.isEmpty()) {
            shakeNVibrate(binding.createPass);
        } else if (name.length() < 3) {
            showToast("Name is too short");
            shakeNVibrate(binding.createMail);
        } else if (phnNo.length() < 10) {
            showToast("Invalid phone number");
            shakeNVibrate(binding.createMail);
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            showToast("Invalid email address");
            shakeNVibrate(binding.createMail);
        } else if (password.length() < 6) {
            showToast("Password must be atleast 6 characters");
            shakeNVibrate(binding.createPass);
        } else {
            //startActivity(new Intent(CreateAccountActivity.this, StudentDetailsActivity.class));
            viewModel.createUser(email, password);
        }
   }         
    
    private void showToast(String message) {
        ToastMessage.showToast(CreateAccountActivity.this, message);
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
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(binding != null) {
        	binding = null;
        }
    }
}