package com.soul.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.soul.databinding.ActivityDashboardBinding;
import com.soul.fragments.HomeFragment;
import me.ibrahimsn.lib.OnItemSelectedListener;
import com.soul.R;

public class DashboardActivity extends AppCompatActivity {

    private ActivityDashboardBinding binding;
    HomeFragment homeFrag = new HomeFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
        getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame, homeFrag).commit();

        binding.bottomNav.setOnItemSelectedListener(
                new OnItemSelectedListener() {
                    @Override
                    public boolean onItemSelect(int item) {
                        switch(item){
                            case 0:
                                getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame, homeFrag).commit();
                                return true;
                        }
                        return false;
                    }
                });
    }
    
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseAuth fAuth = FirebaseAuth.getInstance();
        FirebaseUser user = fAuth.getCurrentUser();
        if(user != null) {
        	user.reload();
        } else {
            startActivity(new Intent(DashboardActivity.this, CreateAccountActivity.class));
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
