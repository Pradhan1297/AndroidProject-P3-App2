package com.example.project3cs478app2;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class TitlesFragment extends ListFragment {
    private static final String TAG = "TitlesFragment";
    private ListViewModel listViewModel;


    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        Log.i(TAG, getClass().getSimpleName() + ":entered onAttach()");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, getClass().getSimpleName() + ":entered onCreate()");
       setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG, getClass().getSimpleName() + ":entered onCreateView()");
        return super.onCreateView(inflater, container, savedInstanceState);
    }
    //replaces deprecated method anActivityCreated()
    @Override
    public void onViewCreated (View view, Bundle savedInstanceState){

        Log.i(TAG, getClass().getSimpleName() + ":entered onViewCreated()");
        super.onViewCreated(view, savedInstanceState);

        listViewModel = new ViewModelProvider(requireActivity()).get(ListViewModel.class);

        // Set the list choice mode to allow only one selection at a time
        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        // Set the list adapter for the ListView
        // Discussed in more detail in the user interface classes lesson
        if(getActivity().getLocalClassName().equalsIgnoreCase("Attractions")){
            setListAdapter(new ArrayAdapter<String>(getActivity(),
                    R.layout.fragment_titles, Attractions.attractionTitles));
        }
        else{
            setListAdapter(new ArrayAdapter<String>(getActivity(),
                    R.layout.fragment_titles, Hotels.hotelTitles));
        }
        setRetainInstance(true);
    }

    // Called when the user selects an item from the List
    @Override
    public void onListItemClick(ListView listView, View itemView, int pos, long id) {

        // Indicates the selected item has been checked
            getListView().setItemChecked(pos, true);
            listViewModel.selectItem(pos);
    }
}