package com.geektech.a2lesson1taskapp.ui.profile;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.ActivityResultRegistry;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.telephony.ims.RegistrationManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.geektech.a2lesson1taskapp.R;


public class ProfileFragment extends Fragment {
    private ProfileViewModel profileViewModel;

    private ImageView imageClick;
    private ImageView imageCheck;

    private ActivityResultLauncher<String> mGetContent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        profileViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);
        return  inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageClick = view.findViewById(R.id.addImage);
        imageCheck=view.findViewById(R.id.imageCheck);

        imageClick.setOnClickListener(v -> {
            openGallery();
        });

        mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),new ActivityResultCallback<Uri>(){
            @Override
            public void onActivityResult(Uri result) {
                imageCheck.setImageURI(result);
            }
        });
    }

    private void openGallery() {
        mGetContent.launch("image/*");
    }
}