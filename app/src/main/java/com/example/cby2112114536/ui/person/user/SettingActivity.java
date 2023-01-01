package com.example.cby2112114536.ui.person.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.cby2112114536.R;
import com.example.cby2112114536.activity.MainActivity;
import com.example.cby2112114536.databinding.ActivityMainBinding;
import com.example.cby2112114536.databinding.ActivitySettingBinding;
import com.example.cby2112114536.utils.LocaleUtil;

import org.angmarch.views.NiceSpinner;
import org.angmarch.views.OnSpinnerItemSelectedListener;

import java.util.Locale;

/**
 * @author breeze
 */
public class SettingActivity extends AppCompatActivity {

    private ActivitySettingBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Spinner spinner = binding.languageSpinner;
        Locale userLocale = LocaleUtil.getUserLocale(this);
        if (LocaleUtil.LOCALE_CHINESE.equals(userLocale)) {
            spinner.setSelection(0);
        } else if (LocaleUtil.LOCALE_ENGLISH.equals(userLocale)) {
            spinner.setSelection(1);
        }

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

            @Override
            public void onItemSelected(AdapterView parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        if (LocaleUtil.needUpdateLocale(SettingActivity.this, LocaleUtil.LOCALE_CHINESE)) {
                            LocaleUtil.updateLocale(SettingActivity.this, LocaleUtil.LOCALE_CHINESE);
                            restartAct();
                        }
                        break;
                    case 1:
                        if (LocaleUtil.needUpdateLocale(SettingActivity.this, LocaleUtil.LOCALE_ENGLISH)) {
                            LocaleUtil.updateLocale(SettingActivity.this, LocaleUtil.LOCALE_ENGLISH);
                            restartAct();
                        }
                        break;
                    default:
                        break;
                }

            }

            private void restartAct() {
                finish();
                Intent intent = new Intent(SettingActivity.this, MainActivity.class);
                startActivity(intent);
                //清除Activity退出和进入的动画
                SettingActivity.this.overridePendingTransition(0, 0);
            }
        });
    }
}