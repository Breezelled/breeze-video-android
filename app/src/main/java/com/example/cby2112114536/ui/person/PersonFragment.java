package com.example.cby2112114536.ui.person;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.cby2112114536.R;
import com.example.cby2112114536.VO.UserVO;
import com.example.cby2112114536.databinding.FragmentPersonBinding;
import com.example.cby2112114536.ui.person.user.SettingActivity;

import cn.bmob.v3.BmobUser;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author breeze
 */
public class PersonFragment extends Fragment {

    private FragmentPersonBinding binding;
    private boolean isLogin;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentPersonBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        TextView username = binding.personName;
        CircleImageView circleImageView = binding.personImage;
        circleImageView.setOnClickListener(this::click);
        username.setOnClickListener(this::click);
        LinearLayout setting = binding.settingLinearLayout;
        setting.setOnClickListener(this::clickSetting);
        if (BmobUser.isLogin()) {
            UserVO user = BmobUser.getCurrentUser(UserVO.class);
            username.setText(user.getUsername());
            isLogin = true;
        } else {
            username.setText(getString(R.string.person_hit_to_login));
            isLogin = false;
        }
        return root;
    }

    private void clickSetting(View view) {
//        Navigation.findNavController(view).navigate(R.id.action_navigation_person_to_settingFragment);
        Intent intent = new Intent();
        intent.setClass(getActivity(), SettingActivity.class);
        startActivity(intent);
    }

    private void click(View view) {
        if (isLogin) {
            Navigation.findNavController(view).navigate(R.id.action_navigation_person_to_infoFragment);
        } else {
            Navigation.findNavController(view).navigate(R.id.action_navigation_person_to_loginFragment);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}