package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.codemybrainsout.onboarder.AhoyOnboarderActivity;
import com.codemybrainsout.onboarder.AhoyOnboarderCard;

import java.util.ArrayList;
import java.util.List;

public class SplashAcivity extends AhoyOnboarderActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AhoyOnboarderCard ahoyOnboarderCard1 = new AhoyOnboarderCard("Global News", "World wide news in hand", R.drawable.bg);
        AhoyOnboarderCard ahoyOnboarderCard2 = new AhoyOnboarderCard("Breaking News", "Breaking news in the market", R.drawable.breaking_news);
        AhoyOnboarderCard ahoyOnboarderCard3 = new AhoyOnboarderCard("Speed Newss", "Speed news delivery", R.drawable.news_speed);
        ahoyOnboarderCard1.setBackgroundColor(R.color.black_transparent);
        ahoyOnboarderCard2.setBackgroundColor(R.color.black_transparent);
        ahoyOnboarderCard3.setBackgroundColor(R.color.black_transparent);

        List<AhoyOnboarderCard> pages = new ArrayList<>();

        pages.add(ahoyOnboarderCard1);
        pages.add(ahoyOnboarderCard2);
        pages.add(ahoyOnboarderCard3);

        for (AhoyOnboarderCard page : pages) {
            page.setTitleColor(R.color.white);
            page.setDescriptionColor(R.color.white);
        }

        setFinishButtonTitle("News On");
        showNavigationControls(true);
        setGradientBackground();
        setInactiveIndicatorColor(R.color.grey_600);
        setActiveIndicatorColor(R.color.white);
        setOnboardPages(pages);
    }

    @Override
    public void onFinishButtonPressed() {

        startActivity(new Intent(SplashAcivity.this,MainActivity.class));
        finish();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}