package com.android.settings.Psycho;

import android.app.IThemeCallback;
import android.app.ThemeManager;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings.Secure;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.content.res.TypedArray;
import android.view.MenuItem;
import android.provider.Settings;
import android.content.ContentResolver;

import com.android.settings.R;

import com.android.settings.fragments.StatusFragment;

public class status extends AppCompatActivity {

  private int mTheme;

  private ThemeManager mThemeManager;
  private final IThemeCallback mThemeCallback = new IThemeCallback.Stub() {

      @Override
      public void onThemeChanged(int themeMode, int color) {
          onCallbackAdded(themeMode, color);
          status.this.runOnUiThread(() -> {
              status.this.recreate();
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
        setContentView(R.layout.frags);
        int accent = getAccent(accentColor, status.this);

        Toolbar toolbar5 = (Toolbar) findViewById(R.id.toolbar5);
        toolbar5.setTitle(R.string.status);
        setSupportActionBar(toolbar5);
        if (themeMode == 0 || themeMode == 1) {
            toolbar5.setBackgroundColor(getResources().getColor(R.color.colorStock));
            toolbar5.setTitleTextColor(getResources().getColor(R.color.text_main_dark));
        }
        if (themeMode == 2) {
            toolbar5.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            toolbar5.setTitleTextColor(accent);
        }
        if (themeMode == 3) {
            toolbar5.setBackgroundColor(getResources().getColor(R.color.black));
            toolbar5.setTitleTextColor(getResources().getColor(R.color.text_main_dark));
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

        getFragmentManager().beginTransaction()
                .replace(R.id.pref, new StatusFragment())
                .commitAllowingStateLoss();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
