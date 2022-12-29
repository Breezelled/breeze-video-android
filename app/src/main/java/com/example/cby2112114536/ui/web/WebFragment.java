package com.example.cby2112114536.ui.web;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.LinearLayout;

import com.example.cby2112114536.activity.MainActivity;
import com.example.cby2112114536.databinding.FragmentWebBinding;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.WebChromeClient;

/**
 * @author breeze
 */
public class WebFragment extends Fragment {

    private FragmentWebBinding binding;
    private AgentWeb mAgentWeb;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentWebBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        String url = "https://www.imdb.com";
        if (getArguments() != null) {
            if ("banner".equals(getArguments().get("urlType"))) {
                url = "https://www.imdb.com/title/tt" + getArguments().getString("bannerUrl");
            } else if ("chart".equals(getArguments().get("urlType"))) {
                url = getArguments().getString("chartUrl");
            }
        } else {
            url = "https://www.imdb.com";
        }
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent((LinearLayout) root, new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT))
                .useDefaultIndicator()
                .setWebChromeClient(new WebChromeClient(){
                    @Override
                    public void onReceivedTitle(WebView view, String title) {
                        super.onReceivedTitle(view, title);
                        MainActivity activity = (MainActivity) getActivity();
                        if (activity != null) {
                            ActionBar actionBar = activity.getSupportActionBar();
                            if (actionBar != null) {
                                actionBar.setTitle(title);
                            }
                        }
                    }
                })
                .createAgentWeb()
                .ready()
                .go(url);
        return root;
    }

    @Override
    public void onPause() {
        mAgentWeb.getWebLifeCycle().onPause();
        super.onPause();

    }

    @Override
    public void onResume() {
        mAgentWeb.getWebLifeCycle().onResume();
        super.onResume();
    }
    @Override
    public void onDestroyView() {
        mAgentWeb.getWebLifeCycle().onDestroy();
        super.onDestroyView();
    }
}