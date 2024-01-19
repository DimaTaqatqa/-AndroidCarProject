package birzeit.edu.androidcarproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import birzeit.edu.androidcarproject.databinding.ActivityCustomerHomeBinding;

public class CustomerHomeActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityCustomerHomeBinding binding;
    private SharedViewModel sharedViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCustomerHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarCustomerHome.toolbar);
        binding.appBarCustomerHome.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // Retrieve shared ViewModel from the hosting activity
        sharedViewModel = new ViewModelProvider(this).get(SharedViewModel.class);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_reservations, R.id.nav_favorites, R.id.nav_carmenu, R.id.nav_offers, R.id.nav_profile, R.id.nav_call, R.id.nav_logout)
                .setOpenableLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_customer_home);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        // Retrieve email from the intent and set it in the shared ViewModel
        Intent intent = getIntent();
        if (intent != null) {
            String email = intent.getStringExtra("email");
            sharedViewModel.setUserEmail(email);
        }
        // Check if the intent has an extra indicating the need to navigate to the Profile fragment
        if (getIntent().getBooleanExtra("navigateToProfile", false)) {
            // Example: Navigate to Profile fragment using NavController
            navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_customer_home);
            navController.navigate(R.id.nav_profile);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.customer_home, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_customer_home);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
