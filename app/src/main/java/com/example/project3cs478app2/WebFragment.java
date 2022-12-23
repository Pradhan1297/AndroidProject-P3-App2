package com.example.project3cs478app2;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

public class WebFragment extends Fragment{
    private WebView webView = null;
    public int currIdx =-1;
    private int arrLen;
    ListViewModel listViewModel;
    private static final String TAG = "WebFragment";


    public WebFragment() {
        super() ;
        Log.i("WebFragment", "I got created!") ;
    }

    int getShownIndex() {
        return currIdx;
    }

    // load the URL at position newIndex
    void loadURLAtIndex(int newIndex) {
        if (newIndex < 0 || newIndex >= arrLen)
            return;
        currIdx = newIndex;
        webView.loadUrl(Attractions.attractionURLs[currIdx]);
    }

    @Override
    public void onAttach(Context activity) {
        Log.i(TAG, getClass().getSimpleName() + ":entered onAttach()");
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, getClass().getSimpleName() + ":entered onCreate()");
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG, getClass().getSimpleName() + ":entered onCreateView()");
        // Inflate the layout defined in quote_fragment.xml
        // The last parameter is false because the returned view does not need to be attached to the container ViewGroup
        return inflater.inflate(R.layout.fragment_web, container, false);
    }

    @Override
    public void onViewCreated (View view, Bundle savedInstanceState){

        Log.i(TAG, getClass().getSimpleName() + ":entered onViewCreated()");
        super.onViewCreated(view,savedInstanceState);
            listViewModel = new ViewModelProvider(requireActivity()).get(ListViewModel.class);

            // retains last quote shown on config change
            listViewModel.getSelectedItem().observe(getViewLifecycleOwner(), item -> {
                // Update the UI.
                if (item == currIdx || item < 0 || item >= arrLen)
                    return;
                    currIdx = item;
                if (getActivity().getLocalClassName().equalsIgnoreCase("Attractions")) {
                    webView.loadUrl(Attractions.attractionURLs[currIdx]);
                } else {
                    webView.loadUrl(Hotels.hotelURLs[currIdx]);
                }
            });

        webView = getActivity().findViewById(R.id.webFragmentView);
        if(getActivity().getLocalClassName().equalsIgnoreCase("Attractions")){
            arrLen = Attractions.attractionURLs.length;
        }
        else{
            arrLen = Hotels.hotelURLs.length;
        }
        setRetainInstance(true);

    }
}