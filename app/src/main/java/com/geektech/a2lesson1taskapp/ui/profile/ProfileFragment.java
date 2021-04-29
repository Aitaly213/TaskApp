package com.geektech.a2lesson1taskapp.ui.profile;

import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.geektech.a2lesson1taskapp.Prefs;
import com.geektech.a2lesson1taskapp.databinding.FragmentProfileBinding;

import org.jetbrains.annotations.NotNull;


public class ProfileFragment extends Fragment {

    FragmentProfileBinding binding;
    Prefs prefs;

    private ActivityResultLauncher<String> mGetContent;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs = new Prefs(getContext());
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.addImage.setOnClickListener(v -> openGallery());
        mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(), result -> binding.addImage.setImageURI(result));

        binding.editName.setText(prefs.getProfileName());
        binding.editName.addTextChangedListener(nameWatcher);
    }

    private void openGallery() {
        mGetContent.launch("image/*");
    }

    private final TextWatcher nameWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            prefs.saveProfileName(binding.editName.getText().toString());
        }
    };
}