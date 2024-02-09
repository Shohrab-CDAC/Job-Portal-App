package com.example.jobportal;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;

public class ProfileFragment extends Fragment {

    private FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Initialize FirebaseAuth instance
        mAuth = FirebaseAuth.getInstance();

        // Find the logout button and set its click listener
        Button logoutButton = view.findViewById(R.id.logout_button);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

        return view;
    }

    // Method to handle logout button click
    public void logout() {
        // Sign out the current user
        mAuth.signOut();

        // Navigate the user back to the login screen or any appropriate destination
        startActivity(new Intent(getActivity(), MainActivity.class));
        getActivity().finish(); // Close the profile fragment
    }
}
