package com.example.cby2112114536.ui.person.user;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.cby2112114536.R;
import com.example.cby2112114536.VO.UserVO;
import com.example.cby2112114536.databinding.FragmentRegisterBinding;
import com.google.android.material.snackbar.Snackbar;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * @author breeze
 */
public class RegisterFragment extends Fragment {

    private FragmentRegisterBinding binding;
    private EditText editName;
    private EditText editPwd;
    private EditText editEmail;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRegisterBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        editName = binding.editName;
        editPwd = binding.editPwd;
        editEmail = binding.editEmail;
        Button button = binding.loginButton;
        button.setOnClickListener(this::signUp);
        return root;
    }
    private void signUp(final View view) {
        final UserVO user = new UserVO();
        String name = editName.getText().toString();
        String pwd = editPwd.getText().toString();
        String email = editEmail.getText().toString();
        if (TextUtils.isEmpty(name)) {
            editName.setError(getString(R.string.error_username));
            return;
        }
        if (TextUtils.isEmpty(pwd)) {
            editPwd.setError(getString(R.string.error_pwd));
            return;
        }
        if (TextUtils.isEmpty(email)) {
            editEmail.setError(getString(R.string.error_email));
            return;
        }
        user.setUsername(name);
        user.setPassword(pwd);
        user.setNickname(name);
        user.setEmail(email);
        user.setSex(true);
        user.setIntro(getString(R.string.intro_text));
        user.signUp(new SaveListener<UserVO>() {
            @Override
            public void done(UserVO user, BmobException e) {
                if (e == null) {
                    Snackbar.make(view, getString(R.string.register_succeeded), Snackbar.LENGTH_LONG).show();
                    Navigation.findNavController(view).navigateUp();
                } else {
                    Snackbar.make(view, getString(R.string.register_failed) + e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }
}