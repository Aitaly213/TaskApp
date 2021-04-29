package com.geektech.a2lesson1taskapp.ui;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.geektech.a2lesson1taskapp.App;
import com.geektech.a2lesson1taskapp.R;
import com.geektech.a2lesson1taskapp.databinding.FragmentFormBinding;
import com.geektech.a2lesson1taskapp.models.HomeModel;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;
import java.util.Locale;

public class FormFragment extends Fragment {

    private FragmentFormBinding binding;

    private int id;


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFormBinding.inflate(inflater, container, false);

        getData();
        return binding.getRoot();
    }

    private void getData() {
        getParentFragmentManager().setFragmentResultListener("edit", getViewLifecycleOwner(), (requestKey, result) -> {
            binding.editTitle.setText(result.getString("title2"));
            binding.editDesc.setText(result.getString("desc2"));
            id = result.getInt("id");
//
//            HomeModel homeModel = new HomeModel(result.getString("title2"),result.getString("desc2"));
//
//            App.appDatabase.homeModelDao().update(homeModel);
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.btnSave.setOnClickListener(v -> save());
    }

    private void save() {
        String text =binding.editTitle.getText().toString();
        String desc =binding.editDesc.getText().toString();

        HomeModel homeModel = new HomeModel(text,desc);
        App.appDatabase.homeModelDao().insert(homeModel);

        Bundle bundle = new Bundle();
        bundle.putString("title", text);
        bundle.putString("desc", desc);
        bundle.putInt("id", id);

        getParentFragmentManager().setFragmentResult("rk_task", bundle);
        close();
    }

    private String getDate(long time) {
        HomeModel homeModel;

        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time * 1000);
        String date = DateFormat.format("HH : mm , dd MMMM yyyy", cal).toString();
        return date;
    }

    private void close() {
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        navController.navigateUp();
    }
}