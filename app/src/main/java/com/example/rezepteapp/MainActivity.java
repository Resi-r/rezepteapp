package com.example.rezepteapp;

import static com.example.rezepteapp.R.*;

import android.content.pm.PackageManager;
import android.os.Bundle;

import com.example.rezepteapp.controller.RecipeListFragment;
import com.example.rezepteapp.controller.ShoppinglistFragment;
import com.example.rezepteapp.controller.WelcomeScreenFragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.rezepteapp.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private AppBarConfiguration appBarConfiguration;
    private final static int REQUEST_CODE_PERMISSIONS = 101;
    private final static String[] REQUIRED_PERMISSIONS = {
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.INTERNET
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.navbarBottom.setSelectedItemId(R.id.home);
        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(MainActivity.this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS);
        }

        replaceFragment(new com.example.rezepteapp.controller.WelcomeScreenFragment());

        binding.navbarBottom.setSelectedItemId(R.id.home);

        binding.navbarBottom.setOnItemSelectedListener(item -> {

            int itemId = item.getItemId();

            if (itemId == R.id.recipelist) {

                replaceFragment(new RecipeListFragment());
                return true;
            } else if (itemId == R.id.home) {
                replaceFragment(new WelcomeScreenFragment());
                return true;
            } else if (itemId == R.id.ingredients) {
                replaceFragment(new ShoppinglistFragment());
                return true;
            }
            return false;
        });
    }

    private boolean allPermissionsGranted() {
        for (String permission : REQUIRED_PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                new AlertDialog.Builder(this)
                    .setTitle("Berechtigungen erforderlich")
                    .setMessage("Einige Berechtigungen wurden nicht erteilt. Diese App benötigt alle Berechtigungen, um korrekt zu funktionieren.")
                    .setPositiveButton("Erneut versuchen", (dialog, which) -> ActivityCompat.requestPermissions(MainActivity.this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS))
                    .setNegativeButton("App schließen", (dialog, which) -> {
                        dialog.dismiss();
                        System.exit(0);
                    })
                    .create()
                    .show();
            }
        }
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(id.frame_layout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, id.navHostFragment);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}