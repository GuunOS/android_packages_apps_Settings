package com.android.settings.Psycho;

import android.app.ActivityOptions;
import android.app.IThemeCallback;
import android.app.ThemeManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.provider.Settings.Secure;
import android.provider.Settings;
import android.content.ContentResolver;

public class theme extends AppCompatActivity {

  private int mTheme;

  private ThemeManager mThemeManager;
  private final IThemeCallback mThemeCallback = new IThemeCallback.Stub() {

     @Override
      public void onThemeChanged(int themeMode, int color) {
          onCallbackAdded(themeMode, color);
          theme.this.runOnUiThread(() -> {
              theme.this.recreate();
          });
      }

      @Override
      public void onCallbackAdded(int themeMode, int color) {
          mTheme = color;
      }
  };

    private int RC_LOGIN = 100;
    private int ui = 0;

    private static int getAccent(int acc, Context context) {
      TypedArray ta = context.getResources().obtainTypedArray(R.array.tint);
      int[] colors = new int[ta.length()];
      for (int i = 0; i < ta.length(); i++) {
          colors[i] = ta.getColor(i, 0);
      }
      ta.recycle();
      return colors[acc];
    }

    public void setLay(int lay) {
        ui = lay;
        ImageButton prev = (ImageButton) findViewById(R.id.prev);
        ImageButton nex = (ImageButton) findViewById(R.id.nex);
        if (ui == 3)
            nex.setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
        else
            nex.setColorFilter(getResources().getColor(R.color.trans), PorterDuff.Mode.SRC_ATOP);
        if (ui == 0)
            prev.setColorFilter(getResources().getColor(R.color.back), PorterDuff.Mode.SRC_ATOP);
        else
            prev.setColorFilter(getResources().getColor(R.color.trans), PorterDuff.Mode.SRC_ATOP);

        switch (ui) {
            case 0:
                final RelativeLayout rl = (RelativeLayout) findViewById(R.id.rl);
                rl.setBackgroundColor(getResources().getColor(R.color.back));
                ImageView stock = (ImageView) findViewById(R.id.bg);
                stock.setImageResource(R.drawable.ic_ui_stock);
                TextView Title = (TextView) findViewById(R.id.head);
                Title.setText(R.string.theme_stock);
                Title.setTextColor(getResources().getColor(R.color.text_main_stock));
                TextView Sub = (TextView) findViewById(R.id.sub);
                Sub.setText(R.string.theme_stock_desc);
                Sub.setTextColor(getResources().getColor(R.color.text_sub));
                break;
            case 1:
                final RelativeLayout rl1 = (RelativeLayout) findViewById(R.id.rl);
                rl1.setBackgroundColor(getResources().getColor(R.color.dark));
                ImageView dark = (ImageView) findViewById(R.id.bg);
                dark.setImageResource(R.drawable.ic_ui);
                TextView Title1 = (TextView) findViewById(R.id.head);
                Title1.setText(R.string.theme_dark);
                Title1.setTextColor(getResources().getColor(R.color.text_main_dark));
                TextView Sub1 = (TextView) findViewById(R.id.sub);
                Sub1.setText(R.string.theme_dark_desc);
                Sub1.setTextColor(getResources().getColor(R.color.text_sub));
                nex.setColorFilter(getResources().getColor(R.color.text_main_dark), PorterDuff.Mode.SRC_ATOP);
                prev.setColorFilter(getResources().getColor(R.color.text_main_dark), PorterDuff.Mode.SRC_ATOP);
                break;
            case 2:
                final RelativeLayout rl2 = (RelativeLayout) findViewById(R.id.rl);
                rl2.setBackgroundColor(getResources().getColor(R.color.back));
                ImageView psycho = (ImageView) findViewById(R.id.bg);
                psycho.setImageResource(R.drawable.ic_ui);
                TextView Title2 = (TextView) findViewById(R.id.head);
                Title2.setText(R.string.theme_psycho);
                Title2.setTextColor(getResources().getColor(R.color.text_main_stock));
                TextView Sub2 = (TextView) findViewById(R.id.sub);
                Sub2.setText(R.string.theme_psycho_desc);
                Sub2.setTextColor(getResources().getColor(R.color.text_sub));
                break;
            case 3:
                final RelativeLayout rl3 = (RelativeLayout) findViewById(R.id.rl);
                rl3.setBackgroundColor(getResources().getColor(R.color.black));
                ImageView black = (ImageView) findViewById(R.id.bg);
                black.setImageResource(R.drawable.ic_ui);
                TextView Title3 = (TextView) findViewById(R.id.head);
                Title3.setText(R.string.theme_black);
                Title3.setTextColor(getResources().getColor(R.color.text_main_dark));
                TextView Sub3 = (TextView) findViewById(R.id.sub);
                Sub3.setText(R.string.theme_black_desc);
                Sub3.setTextColor(getResources().getColor(R.color.text_sub));
                prev.setColorFilter(getResources().getColor(R.color.text_main_dark), PorterDuff.Mode.SRC_ATOP);
                break;
        }
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
        setContentView(R.layout.theme);
        int accent = getAccent(accentColor, theme.this);

        final RelativeLayout cl = (RelativeLayout) findViewById(R.id.container);
        cl.setBackgroundColor(getResources().getColor(R.color.back));
        ImageView init = (ImageView) findViewById(R.id.bg);
        init.setImageResource(R.drawable.ic_ui_stock);
        TextView InitText = (TextView) findViewById(R.id.head);
        InitText.setText(R.string.theme_stock);
        InitText.setTextColor(getResources().getColor(R.color.black));
        TextView SubInit = (TextView) findViewById(R.id.sub);
        SubInit.setText(R.string.theme_stock_desc);
        SubInit.setTextColor(getResources().getColor(R.color.text_sub));

        Toolbar toolbar1 = (Toolbar) findViewById(R.id.toolbar4);
        setSupportActionBar(toolbar1);
        if (themeMode == 0 || themeMode == 1) {
            toolbar1.setBackgroundColor(getResources().getColor(R.color.colorStock));
            toolbar1.setTitleTextColor(getResources().getColor(R.color.text_main_dark));
        }
        if (themeMode == 2) {
            toolbar1.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            toolbar1.setTitleTextColor(accent);
        }
        if (themeMode == 3) {
            toolbar1.setBackgroundColor(getResources().getColor(R.color.black));
            toolbar1.setTitleTextColor(getResources().getColor(R.color.text_main_dark));
        }
        if (getSupportActionBar() != null) {
            final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_back);
            if (themeMode == 2) {
            upArrow.setColorFilter(accent, PorterDuff.Mode.SRC_ATOP);
            }
            else
            {
              upArrow.setColorFilter(R.color.text_main_dark, PorterDuff.Mode.SRC_ATOP);
            }
            getSupportActionBar().setHomeAsUpIndicator(upArrow);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        ImageButton prev = (ImageButton) findViewById(R.id.prev);
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ui != 0)
                    setLay(ui - 1);
            }
        });

        ImageButton nex = (ImageButton) findViewById(R.id.nex);
        prev.setColorFilter(getResources().getColor(R.color.back), PorterDuff.Mode.SRC_ATOP);
        nex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ui != 3)
                    setLay(ui + 1);
            }
        });

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.Secd);
        fab.setBackgroundColor(accent);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(theme.this, accent.class);
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(theme.this, fab, getString(R.string.transition_dialog));
                startActivityForResult(intent, RC_LOGIN, options.toBundle());
            }
        });

        FloatingActionButton apply = (FloatingActionButton) findViewById(R.id.apply);
        apply.setBackgroundColor(accent);
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (ui) {
                    case 0:
                        Settings.Secure.putInt(theme.this.getContentResolver(),
                                Settings.Secure.THEME_PRIMARY_COLOR, 0);
                        Toast.makeText(theme.this, "Stock Theme Applied", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Settings.Secure.putInt(theme.this.getContentResolver(),
                                Settings.Secure.THEME_PRIMARY_COLOR, 1);
                        Toast.makeText(theme.this, "Dark Theme Applied", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Settings.Secure.putInt(theme.this.getContentResolver(),
                                Settings.Secure.THEME_PRIMARY_COLOR, 2);
                        Toast.makeText(theme.this, "Psycho Theme Applied", Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        Settings.Secure.putInt(theme.this.getContentResolver(),
                                Settings.Secure.THEME_PRIMARY_COLOR, 3);
                        Toast.makeText(theme.this, "Black Theme Applied", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
