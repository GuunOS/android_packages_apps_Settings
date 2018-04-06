package com.android.settings.Psycho;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.transition.ArcMotion;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.provider.Settings.Secure;
import android.provider.Settings;

public class accent extends AppCompatActivity {

    private ViewGroup container;

    private static int getThemeAccentColor(Context context) {
        int colorAttr;
        colorAttr = android.R.attr.colorAccent;
        TypedValue outValue = new TypedValue();
        context.getTheme().resolveAttribute(colorAttr, outValue, true);
        return outValue.data;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accent);

        container = (ViewGroup) findViewById(R.id.dialog);

        //方式一
        setupSharedEelementTransitions1();
        //方式二
        //setupSharedEelementTransitions2();

        View.OnClickListener dismissListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        };
        container.setOnClickListener(dismissListener);

        RelativeLayout teal = (RelativeLayout) findViewById(R.id.card1);
        teal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                  Settings.Secure.putInt(accent.this.getContentResolver(),
                          Settings.Secure.THEME_ACCENT_COLOR, 0);
                Toast.makeText(accent.this, "Teal Selected", Toast.LENGTH_SHORT).show();

            }
        });
        RelativeLayout green = (RelativeLayout) findViewById(R.id.card2);
        green.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                 Settings.Secure.putInt(accent.this.getContentResolver(),
                         Settings.Secure.THEME_ACCENT_COLOR, 1);
                Toast.makeText(accent.this, "Green Selected", Toast.LENGTH_SHORT).show();

            }
        });
        RelativeLayout cyan = (RelativeLayout) findViewById(R.id.card3);
        cyan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Settings.Secure.putInt(accent.this.getContentResolver(),
                        Settings.Secure.THEME_ACCENT_COLOR, 2);
                Toast.makeText(accent.this, "Cyan Selected", Toast.LENGTH_SHORT).show();

            }
        });
        RelativeLayout blue = (RelativeLayout) findViewById(R.id.card4);
        blue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                 Settings.Secure.putInt(accent.this.getContentResolver(),
                         Settings.Secure.THEME_ACCENT_COLOR, 3);
                Toast.makeText(accent.this, "Blue Selected", Toast.LENGTH_SHORT).show();

            }
        });
        RelativeLayout yellow = (RelativeLayout) findViewById(R.id.card5);
        yellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                 Settings.Secure.putInt(accent.this.getContentResolver(),
                         Settings.Secure.THEME_ACCENT_COLOR, 4);
                Toast.makeText(accent.this, "Yellow Selected", Toast.LENGTH_SHORT).show();

            }
        });
        RelativeLayout orange = (RelativeLayout) findViewById(R.id.card6);
        orange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                 Settings.Secure.putInt(accent.this.getContentResolver(),
                         Settings.Secure.THEME_ACCENT_COLOR, 5);
                Toast.makeText(accent.this, "Orange Selected", Toast.LENGTH_SHORT).show();

            }
        });
        RelativeLayout red = (RelativeLayout) findViewById(R.id.card7);
        red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                 Settings.Secure.putInt(accent.this.getContentResolver(),
                         Settings.Secure.THEME_ACCENT_COLOR, 6);
                Toast.makeText(accent.this, "Red Selected", Toast.LENGTH_SHORT).show();

            }
        });
        RelativeLayout pink = (RelativeLayout) findViewById(R.id.card8);
        pink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                 Settings.Secure.putInt(accent.this.getContentResolver(),
                         Settings.Secure.THEME_ACCENT_COLOR, 7);
                Toast.makeText(accent.this, "Pink Selected", Toast.LENGTH_SHORT).show();

            }
        });
        RelativeLayout purple = (RelativeLayout) findViewById(R.id.card9);
        purple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                 Settings.Secure.putInt(accent.this.getContentResolver(),
                         Settings.Secure.THEME_ACCENT_COLOR, 8);
                Toast.makeText(accent.this, "Purple Selected", Toast.LENGTH_SHORT).show();

            }
        });
        RelativeLayout grey = (RelativeLayout) findViewById(R.id.card10);
        grey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Settings.Secure.putInt(accent.this.getContentResolver(),
                       Settings.Secure.THEME_ACCENT_COLOR, 9);
                Toast.makeText(accent.this, "Grey Selected", Toast.LENGTH_SHORT).show();

            }
        });
    }

    /**
     * 使用方式一：调用setupSharedEelementTransitions1方法
     * 使用这种方式的话需要的类是 MorphDrawable, MorphFabToDialog, MorphDialogToFab
     */
    public void setupSharedEelementTransitions1() {
        ArcMotion arcMotion = new ArcMotion();
        arcMotion.setMinimumHorizontalAngle(50f);
        arcMotion.setMinimumVerticalAngle(50f);

        Interpolator easeInOut = AnimationUtils.loadInterpolator(this, android.R.interpolator.fast_out_slow_in);

        MorphFabToDialog sharedEnter = new MorphFabToDialog();
        sharedEnter.setPathMotion(arcMotion);
        sharedEnter.setInterpolator(easeInOut);

        MorphDialogToFab sharedReturn = new MorphDialogToFab();
        sharedReturn.setPathMotion(arcMotion);
        sharedReturn.setInterpolator(easeInOut);

        if (container != null) {
            sharedEnter.addTarget(container);
            sharedReturn.addTarget(container);
        }
        getWindow().setSharedElementEnterTransition(sharedEnter);
        getWindow().setSharedElementReturnTransition(sharedReturn);
    }

    /**
     * 使用方式二：调用setupSharedEelementTransitions2方法
     * 使用这种方式的话需要的类是 MorphDrawable, MorphTransition
     */
    public void setupSharedEelementTransitions2() {
        ArcMotion arcMotion = new ArcMotion();
        arcMotion.setMinimumHorizontalAngle(50f);
        arcMotion.setMinimumVerticalAngle(50f);

        Interpolator easeInOut = AnimationUtils.loadInterpolator(this, android.R.interpolator.fast_out_slow_in);

        //hujiawei 100是随意给的一个数字，可以修改，需要注意的是这里调用container.getHeight()结果为0
        MorphTransition sharedEnter = new MorphTransition(ContextCompat.getColor(this, getThemeAccentColor(accent.this)),
                ContextCompat.getColor(this, R.color.dialog_background_color), 100, getResources().getDimensionPixelSize(R.dimen.dialog_corners), true);
        sharedEnter.setPathMotion(arcMotion);
        sharedEnter.setInterpolator(easeInOut);

        MorphTransition sharedReturn = new MorphTransition(ContextCompat.getColor(this, R.color.dialog_background_color),
                ContextCompat.getColor(this, getThemeAccentColor(accent.this)), getResources().getDimensionPixelSize(R.dimen.dialog_corners), 100, false);
        sharedReturn.setPathMotion(arcMotion);
        sharedReturn.setInterpolator(easeInOut);

        if (container != null) {
            sharedEnter.addTarget(container);
            sharedReturn.addTarget(container);
        }
        getWindow().setSharedElementEnterTransition(sharedEnter);
        getWindow().setSharedElementReturnTransition(sharedReturn);
    }

    @Override
    public void onBackPressed() {
        dismiss();
    }

    public void dismiss() {
        setResult(Activity.RESULT_CANCELED);
        finishAfterTransition();
    }
}
