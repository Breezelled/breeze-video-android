package com.example.cby2112114536.ui.person.user;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.cby2112114536.R;
import com.example.cby2112114536.VO.UserVO;
import com.example.cby2112114536.activity.MainActivity;
import com.example.cby2112114536.databinding.ActivityMainBinding;
import com.example.cby2112114536.databinding.FragmentInfoBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * @author breeze
 */
public class InfoFragment extends Fragment {

    private FragmentInfoBinding binding;
    private boolean isUpdate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        isUpdate = false;
        binding = FragmentInfoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        TextView username = binding.infoUsername;
        TextView nickname = binding.infoNickname;
        TextView sex = binding.infoSex;
        TextView email = binding.infoEmail;
        TextView intro = binding.infoIntro;

        if (BmobUser.isLogin()) {
            UserVO user = BmobUser.getCurrentUser(UserVO.class);
            username.setText(user.getUsername());
            nickname.setText(user.getNickname());
            sex.setText(user.isSex() ? getString(R.string.male) : getString(R.string.female));
            email.setText(user.getEmail());
            intro.setText(user.getIntro());
        }
        Button button = binding.loginButton;
        button.setOnClickListener(this::logout);
        LinearLayout changePwd = binding.changePwdLinearLayout;
        changePwd.setOnClickListener(this::click);
        return root;
    }

    private void click(View view) {
        LayoutInflater factory = LayoutInflater.from(getActivity());
        final View textEntryView = factory.inflate(R.layout.dialog_change_pwd,
                null);
        final EditText previousPwd = (EditText) textEntryView.findViewById(R.id.editTextPrevious);
        final EditText newPwd = (EditText) textEntryView.findViewById(R.id.editTextNew);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.previous_pwd_text))
                .setView(textEntryView)
                .setNegativeButton(getString(R.string.cancel_text),
                        new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.setPositiveButton(getString(R.string.confirm_text),
                new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (updatePassword(view, previousPwd.getText().toString(),
                        newPwd.getText().toString())){
                    dialog.dismiss();
                } else {
                    previousPwd.setError(getString(R.string.previous_pwd_error_text));
                }
            }
        });
        builder.show();
    }

    private void logout(View view) {
        BmobUser.logOut();
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        BottomNavigationView navView = binding.navView;
        navView.getMenu().findItem(R.id.navigation_chart).setVisible(false);
        getActivity().finish();
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
        Snackbar.make(view, getString(R.string.logout_succeeded), Snackbar.LENGTH_LONG).show();
    }

    private boolean updatePassword(final View view, String previousPwd, String newPwd){
        BmobUser.updateCurrentUserPassword(previousPwd, newPwd, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    Snackbar.make(view, getString(R.string.change_pwd_succeeded), Snackbar.LENGTH_LONG).show();
                    isUpdate = true;
                } else {
                    Snackbar.make(view, getString(R.string.change_pwd_failed), Snackbar.LENGTH_LONG).show();
                    isUpdate = false;
                }
            }
        });
        return isUpdate;
    }
}