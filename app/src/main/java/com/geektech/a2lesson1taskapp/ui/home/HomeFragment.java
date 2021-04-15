package com.geektech.a2lesson1taskapp.ui.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.geektech.a2lesson1taskapp.R;
import com.geektech.a2lesson1taskapp.databinding.FragmentHomeBinding;
import com.geektech.a2lesson1taskapp.ui.home.homeAdapter.HomeModel;
import com.geektech.a2lesson1taskapp.ui.home.homeAdapter.Listen;
import com.geektech.a2lesson1taskapp.ui.home.homeAdapter.TaskAdapter;

public class HomeFragment extends Fragment implements Listen {
    private TaskAdapter adapter;

    private FragmentHomeBinding binding;
    private NavController navController;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new TaskAdapter(this);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        navController = NavHostFragment.findNavController(this);
        binding.recyclerView.setAdapter(adapter);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.fab.setOnClickListener(v -> {
            openForm();
        });
        getDataInForm();
    }

    private void getDataInForm() {
        getParentFragmentManager().setFragmentResultListener("rk_task", getViewLifecycleOwner(), (requestKey, result) -> {
            String title = result.getString("title");
            int id = result.getInt("id");
            HomeModel model = adapter.getModelTold(id);
            if (model != null) {
                model.setTitle(title);
                adapter.notifyDataSetChanged();
            } else {
                adapter.addItem(new HomeModel(title));
            }
        });
    }

    private void openForm() {
        navController.navigate(R.id.formFragment);
    }


    @Override
    public void setDataForForm(HomeModel homeModel, int position) {
        Bundle bundle = new Bundle();
        bundle.putString("title2", homeModel.getTitle());
        bundle.putInt("id", homeModel.getId());
        getParentFragmentManager().setFragmentResult("edit", bundle);
        navController.navigate(R.id.action_navigation_home_to_formFragment);
    }
}