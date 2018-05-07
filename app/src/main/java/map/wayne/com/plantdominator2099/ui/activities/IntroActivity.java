package map.wayne.com.plantdominator2099.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;

import map.wayne.com.plantdominator2099.R;
import map.wayne.com.plantdominator2099.ui.fragments.IntroSlide;

public class IntroActivity extends AppIntro {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState
    ) {
        super.onCreate(savedInstanceState);
        addSlide(IntroSlide.newInstance(R.layout.slide_1));
        addSlide(IntroSlide.newInstance(R.layout.slide_2));
        addSlide(IntroSlide.newInstance(R.layout.slide_3));
        addSlide(IntroSlide.newInstance(R.layout.slide_4));
        addSlide(IntroSlide.newInstance(R.layout.slide_5));
        addSlide(IntroSlide.newInstance(R.layout.slide_6));
        addSlide(IntroSlide.newInstance(R.layout.slide_7));
        addSlide(IntroSlide.newInstance(R.layout.slide_8));
        getSupportActionBar().setTitle("Tutorial 101");

/*
        addSlide(AppIntroFragment.newInstance("Plant Dominator 101", "", R.drawable.tut_1,
            getColor(R.color.colorPrimary)));
        addSlide(AppIntroFragment.newInstance("Plant Dominator 101", "", R.drawable.tut_2,
            getColor(R.color.colorPrimary)));

        addSlide(AppIntroFragment.newInstance("Plant Dominator 101", "", R.drawable.tut_333,
            getColor(R.color.colorPrimary)));

        addSlide(AppIntroFragment.newInstance("Plant Dominator 101", "", R.drawable.tut_4,
            getColor(R.color.colorPrimary)));

        addSlide(AppIntroFragment.newInstance("Plant Dominator 101", "", R.drawable.tut_5,
            getColor(R.color.colorPrimary)));

        addSlide(AppIntroFragment.newInstance("Plant Dominator 101", "", R.drawable.tut_6,
            getColor(R.color.colorPrimary)));
        addSlide(AppIntroFragment.newInstance("Plant Dominator 101", "", R.drawable.tut_7,
            getColor(R.color.colorPrimary)));
        addSlide(AppIntroFragment.newInstance("Plant Dominator 101", "", R.drawable.tut_8,
            getColor(R.color.colorPrimary)));
*/

    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        // Do something when users tap on Skip button.
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
        finish();
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
        // Do something when the slide changes.
    }
}
