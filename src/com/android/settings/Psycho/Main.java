package com.android.settings.Psycho;

import android.app.IThemeCallback;
import android.app.ThemeManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings.Secure;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.settings.R;

public class Main extends AppCompatActivity {

    CardView card1, card2, card3, card4;
    int east;

    private int mTheme;

    private ThemeManager mThemeManager;
    private final IThemeCallback mThemeCallback = new IThemeCallback.Stub() {

        @Override
        public void onThemeChanged(int themeMode, int color) {
            onCallbackAdded(themeMode, color);
            Main.this.runOnUiThread(() -> {
                Main.this.recreate();
            });
        }

        @Override
        public void onCallbackAdded(int themeMode, int color) {
            mTheme = color;
        }
    };

    private static int getAccent(int acc, Context context) {
      TypedArray ta = context.getResources().obtainTypedArray(R.array.tint);
      int[] colors = new int[ta.length()];
      for (int i = 0; i < ta.length(); i++) {
          colors[i] = ta.getColor(i, 0);
      }
      ta.recycle();
      return colors[acc];
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
      final int themeMode = Secure.getInt(getContentResolver(),
                Secure.THEME_PRIMARY_COLOR, 0);
        final int accentColor = Secure.getInt(getContentResolver(),
                Secure.THEME_ACCENT_COLOR, 0);
        mThemeManager = (ThemeManager) getSystemService(Context.THEME_SERVICE);
        if (mThemeManager != null) {
            mThemeManager.addCallback(mThemeCallback);
        }
        if (themeMode == 0) {
            getTheme().applyStyle(R.style.stock_theme, true);
        }
        if (themeMode == 1) {
            getTheme().applyStyle(R.style.dark_theme, true);
        }
        if (themeMode == 2) {
            getTheme().applyStyle(R.style.pixel_theme, true);
        }
        if (themeMode == 3) {
            getTheme().applyStyle(R.style.black_theme, true);
        }
        super.onCreate(savedInstanceState);
        east = 0;
        setContentView(R.layout.main);
        int accent = getAccent(accentColor, Main.this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (themeMode == 0 || themeMode == 1) {
            toolbar.setBackgroundColor(getResources().getColor(R.color.colorStock));
            toolbar.setTitleTextColor(getResources().getColor(R.color.text_main_dark));
        }
        if (themeMode == 2) {
            toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            toolbar.setTitleTextColor(accent);
        }
        if (themeMode == 3) {
            toolbar.setBackgroundColor(getResources().getColor(R.color.black));
            toolbar.setTitleTextColor(getResources().getColor(R.color.text_main_dark));
        }
        if (getSupportActionBar() != null) {
            final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_back);
            if (themeMode == 2) {
            upArrow.setColorFilter(accent, PorterDuff.Mode.SRC_ATOP);
            }
            else
            {
              upArrow.setColorFilter(getResources().getColor(R.color.text_main_dark), PorterDuff.Mode.SRC_ATOP);
            }
            getSupportActionBar().setHomeAsUpIndicator(upArrow);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        final ImageView egg = (ImageView) findViewById(R.id.header1);
        egg.setBackgroundColor(accent);
        egg.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                east++;
                if (east==10)
                {
                    Toast.makeText(Main.this, "Hold On, You Will Break Things...", Toast.LENGTH_SHORT).show();
                }
                if (east==20)
                {
                    Toast.makeText(Main.this, "Stop It", Toast.LENGTH_SHORT).show();
                }
                if (east==30)
                {
                    egg.setBackgroundResource(R.drawable.android_gradient_list);
                    AnimationDrawable animationDrawable = (AnimationDrawable) egg.getBackground();
                    animationDrawable.setEnterFadeDuration(2000);
                    animationDrawable.setExitFadeDuration(4000);
                    animationDrawable.start();
                    Toast.makeText(Main.this, "You Broke It. Happy Now?", Toast.LENGTH_LONG).show();
                }
            }
        });

        card1 = (CardView) findViewById(R.id.card1);
        card1.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                Intent myIntent = new Intent(Main.this,
                        theme.class);
                startActivity(myIntent);
            }
        });

        card2 = (CardView) findViewById(R.id.card2);
        card2.setOnClickListener(new OnClickListener() {
          public void onClick(View arg0) {
              Intent myIntent = new Intent(Main.this,
                      display.class);
              startActivity(myIntent);
          }
        });

        card3 = (CardView) findViewById(R.id.card3);
        card3.setOnClickListener(new OnClickListener() {
          public void onClick(View arg0) {
              Intent myIntent = new Intent(Main.this,
                      status.class);
              startActivity(myIntent);
          }
        });

        card4 = (CardView) findViewById(R.id.card4);
        card4.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                final Intent intent = new Intent(Intent.ACTION_MAIN, null);
                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                final ComponentName buttons = new ComponentName("org.cyanogenmod.cmparts", "org.cyanogenmod.cmparts.input.ButtonSettings");
                intent.setComponent(buttons);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
      switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
