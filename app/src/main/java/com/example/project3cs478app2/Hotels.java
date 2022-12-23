package com.example.project3cs478app2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class Hotels extends AppCompatActivity {

    public static String[] hotelTitles;
    public static String[] hotelURLs;

    private WebFragment webFragment = new WebFragment();

    FragmentManager fragmentManager;

    private static final int MATCH_PARENT = LinearLayout.LayoutParams.MATCH_PARENT;
    private FrameLayout titleFrameLayout, webFrameLayout;

    private static final String TAG = "Hotels";
    private ListViewModel listViewModel;
    TitlesFragment titlesFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get the string arrays with the titles and URLs
        hotelTitles = getResources().getStringArray(R.array.HotelTitles);
        hotelURLs = getResources().getStringArray(R.array.HotelURLs);

        setContentView(R.layout.activity_hotels);
        // Get references to the TitleFragment and to the WebFragment
        titleFrameLayout = (FrameLayout) findViewById(R.id.listViewContainer);
        webFrameLayout = (FrameLayout) findViewById(R.id.webViewContainer);

        // Get a reference to the SupportFragmentManager instead of original FragmentManager
        fragmentManager = getSupportFragmentManager();

        // Start a new FragmentTransaction
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        titlesFragment=new TitlesFragment();
        // Add the TitleFragment to the layout
        //replace() avoids overlapping fragments
        fragmentTransaction.replace(R.id.listViewContainer,titlesFragment);

        // Commit the FragmentTransaction
        fragmentTransaction.commit();
        //updating layout whenever back stack changes
        fragmentManager.addOnBackStackChangedListener(
                new FragmentManager.OnBackStackChangedListener() {
                    @Override
                    public void onBackStackChanged() {
                        setLayout();
                    }
                });
        listViewModel = new ViewModelProvider(this).get(ListViewModel.class) ;
        listViewModel.getSelectedItem().observe(this, item -> {
            if (!webFragment.isAdded()) {
                FragmentTransaction fragmentTransaction2 = fragmentManager.beginTransaction() ;

                // add quote fragment to display
                fragmentTransaction2.replace(R.id.webViewContainer,
                        webFragment);

                // Add this FragmentTransaction to the backstack
                fragmentTransaction2.addToBackStack(null);

                // Commit the FragmentTransaction
                fragmentTransaction2.commit();

                // Force Android to execute the committed FragmentTransaction
                fragmentManager.executePendingTransactions();
            }
        });
        setLayout() ;
    }
    private void setLayout() {
        // Determine whether the WebViewFragment has been added
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            if (!webFragment.isAdded()) {
                // Make the TitleFragment occupy the entire layout
                titleFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(
                        MATCH_PARENT, MATCH_PARENT));
                webFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                        MATCH_PARENT));
            } else {
                // Make the TitleLayout take 1/3 of the layout's width
                titleFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                        MATCH_PARENT, 1f));

                // Make the WebViewLayout take 2/3's of the layout's width
                webFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                        MATCH_PARENT, 2f));
            }
        }
        else if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            if (!webFragment.isAdded()) {
                // Make the TitleFragment occupy the entire layout
                titleFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(
                        MATCH_PARENT, MATCH_PARENT));
                webFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                        MATCH_PARENT));
            }
            else{
                titleFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                        0, 0f));

                // Make the WebViewLayout take 2/3's of the layout's width
                webFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT,
                        MATCH_PARENT, 1f));
            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.options_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.attractions:
                Intent aIntent = new Intent(this,Attractions.class);
                startActivity(aIntent);
                return true;
            case R.id.hotels:
                Intent hIntent = new Intent(this,Hotels.class);
                startActivity(hIntent);
                return true;
            default:return false;
        }
    }
    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setLayout();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        titlesFragment.getListView().clearChoices();
    }
}