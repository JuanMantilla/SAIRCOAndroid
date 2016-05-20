package com.example.juanse.sairco.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.juanse.sairco.R;

/**
 * Created by juanse on 20/05/2016.
 */
public class FragmentMain extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.content_main,container,false);


        return v;
    }
}
