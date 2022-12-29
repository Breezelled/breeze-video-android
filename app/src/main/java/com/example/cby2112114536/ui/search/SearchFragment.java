package com.example.cby2112114536.ui.search;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.example.cby2112114536.DTO.InfoPageDTO;
import com.example.cby2112114536.R;
import com.example.cby2112114536.adapter.SearchAdapter;
import com.example.cby2112114536.databinding.FragmentSearchBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * @author breeze
 */
public class SearchFragment extends Fragment {

    private FragmentSearchBinding binding;
    private int pageNum = 1;
    private final int pageSize = 15;
    private SearchAdapter searchAdapter;
    private SearchViewModel searchViewModel;
    private List<InfoPageDTO.DataDTO.RecordsDTO> searchRecords = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        searchViewModel = new ViewModelProvider(this).get(SearchViewModel.class);

        binding = FragmentSearchBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        SearchView searchView = binding.searchView;
        RecyclerView recyclerView = binding.searchRecyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        searchAdapter = new SearchAdapter(null);
        pageNum = 1;
        recyclerView.setAdapter(searchAdapter);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                getSearchContent(pageNum, pageSize, s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        searchAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("item", searchRecords.get(position));
                bundle.putString("itemType", "item");
                Navigation.findNavController(view)
                        .navigate(R.id.action_navigation_search_to_videoItemFragment, bundle);
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void getSearchContent(Integer pageNum, Integer pageSize, String content) {
        searchViewModel.getInfoPageListByContent(pageNum, pageSize, content)
                .observe(getViewLifecycleOwner(), infoPageDTO -> {
                    searchRecords.clear();
                    searchRecords.addAll(infoPageDTO.getData().getRecords());
                    searchAdapter.setList(searchRecords);
                });
    }
}