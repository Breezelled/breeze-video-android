package com.example.cby2112114536.ui.person.user;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.cby2112114536.R;
import com.example.cby2112114536.VO.UserVO;
import com.example.cby2112114536.activity.MainActivity;
import com.example.cby2112114536.databinding.ActivityMainBinding;
import com.example.cby2112114536.databinding.FragmentHomeBinding;
import com.example.cby2112114536.databinding.FragmentLoginBinding;
import com.example.cby2112114536.ui.home.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * @author breeze
 */
public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;
    private EditText editName;
    private EditText editPwd;
    private View root;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        root = binding.getRoot();
        editName = binding.editName;
        editPwd = binding.editPwd;
        TextView register = binding.register;
        register.setOnClickListener(view -> Navigation.findNavController(view)
                .navigate(R.id.action_loginFragment_to_registerFragment));
        TextView forgotPwd = binding.forgotPwd;
        forgotPwd.setOnClickListener(view -> Navigation.findNavController(view)
                .navigate(R.id.action_loginFragment_to_findPwdFragment));
        Button button = binding.loginButton;
        button.setOnClickListener(this::login);
        return root;
    }

    private void login(final View view) {
        final UserVO user = new UserVO();
        String name = editName.getText().toString();
        String pwd = editPwd.getText().toString();
        if (TextUtils.isEmpty(name)) {
            editName.setError(getString(R.string.error_username));
            return;
        }
        if (TextUtils.isEmpty(pwd)) {
            editPwd.setError(getString(R.string.error_pwd));
            return;
        }
        //此处替换为你的用户名
        user.setUsername(name);
        //此处替换为你的密码
        user.setPassword(pwd);
        user.login(new SaveListener<UserVO>() {
            @Override
            public void done(UserVO bmobUser, BmobException e) {
                UserVO user = BmobUser.getCurrentUser(UserVO.class);
                if (e == null) {
                    Snackbar.make(view, getString(R.string.login_succeeded) + user.getUsername(), Snackbar.LENGTH_LONG).show();
//                    Navigation.findNavController(view).navigateUp();
                } else {
                    Snackbar.make(view, getString(R.string.login_failed) + e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
                if ("Breeze".equals(user.getUsername())){
                    ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
                    BottomNavigationView navView = binding.navView;
                    navView.getMenu().findItem(R.id.navigation_chart).setVisible(true);
                    getActivity().finish();
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                } else {

                }
            }
        });
    }
}