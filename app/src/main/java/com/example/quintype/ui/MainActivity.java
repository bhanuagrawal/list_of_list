package com.example.quintype.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.net.Uri;
import android.os.Bundle;

import com.example.quintype.R;

public class MainActivity extends AppCompatActivity implements OnFragmentInteractionListener{

    private NavHostFragment navhostFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navhostFragment = (NavHostFragment)getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void navigateTo(NavDirections navDirections) {
        navhostFragment.getNavController().navigate(navDirections);
    }
}
