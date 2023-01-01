package com.example.cby2112114536.ui.person.user;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.cby2112114536.R;
import com.example.cby2112114536.databinding.FragmentFindPwdBinding;
import com.google.android.material.snackbar.Snackbar;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * @author breeze
 */
public class FindPwdFragment extends Fragment {

    private FragmentFindPwdBinding binding;
    private EditText editEmail;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFindPwdBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        editEmail = binding.editEmail;
        Button button = binding.forgotPwdButton;
        button.setOnClickListener(this::resetPasswordByEmail);
        return root;
    }

    private void resetPasswordByEmail(View view) {
        String email = editEmail.getText().toString();
        Log.i("EMAIL", email);
        if (TextUtils.isEmpty(email)) {
            editEmail.setError(getString(R.string.error_email));
            return;
        }
        BmobUser.resetPasswordByEmail(email, new UpdateListener() {

            @Override
            public void done(BmobException e) {
                if (e == null) {
                    Snackbar.make(view, getString(R.string.reset_pwd1) + email +
                            getString(R.string.reset_pwd2), Snackbar.LENGTH_LONG).show();
                    Navigation.findNavController(view).navigateUp();
                } else {
                    Log.e("BMOB", e.toString());
                    Snackbar.make(view, e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }
}