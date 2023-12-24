package com.soul.utils;

import android.view.View;
import android.widget.TextView;
import com.soul.R;

public class FlashBar {

    public static void showFlashBar(View flashBarView, String message) {
        if (flashBarView != null && flashBarView.getVisibility() != View.VISIBLE) {
            TextView flashBarText = flashBarView.findViewById(R.id.flashMsg);
            flashBarText.setText(message);
            flashBarView.setVisibility(View.VISIBLE);
            flashBarView.postDelayed(
                    () -> flashBarView.setVisibility(View.GONE), 2000); // Adjust duration as needed
        }
    }
}
