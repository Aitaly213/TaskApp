package com.geektech.a2lesson1taskapp.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.geektech.a2lesson1taskapp.R;
import com.geektech.a2lesson1taskapp.databinding.FragmentFormBinding;
import com.geektech.a2lesson1taskapp.databinding.FragmentHomeBinding;

public class FormFragment extends Fragment {

    private FragmentFormBinding binding;
    private NavController navController;

    private int id;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        navController = NavHostFragment.findNavController(this);
        binding = FragmentFormBinding.inflate(inflater,container,false);
        
        getData();
        return binding.getRoot();    }

    private void getData() {
        getParentFragmentManager().setFragmentResultListener("edit", getViewLifecycleOwner(), new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                binding.editText.setText(result.getString("title2"));
                id=result.getInt("id");
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.btnSave.setOnClickListener(v -> {
            save();
        });
    }

    private void save() {
        Bundle bundle = new Bundle();
        bundle.putString("title",binding.editText.getText().toString());
        bundle.putInt("id",id);
        getParentFragmentManager().setFragmentResult("rk_task",bundle);
        close();
    }

    private void close(){
       NavController navController = Navigation.findNavController(requireActivity(),R.id.nav_host_fragment);
       navController.navigateUp();
    }
}