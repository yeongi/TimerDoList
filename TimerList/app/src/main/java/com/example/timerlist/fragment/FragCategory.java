package com.example.timerlist.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.timerlist.data.MyCategory;
import com.example.timerlist.R;
import com.felipecsl.asymmetricgridview.library.Utils;
import com.felipecsl.asymmetricgridview.library.widget.AsymmetricGridView;
import com.felipecsl.asymmetricgridview.library.widget.AsymmetricGridViewAdapter;

import java.util.ArrayList;

public class FragCategory extends Fragment {
    private View view;
    AsymmetricGridView listView;
    Context ct;
    public static FragCategory newInstance(){
        FragCategory fragCategory = new FragCategory();
        return fragCategory;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_frag_category,container,false);
        ct = view.getContext();

        listView = (AsymmetricGridView) view.findViewById(R.id.categoryView);
        // Choose your own preferred column width
        listView.setRequestedColumnWidth(Utils.dpToPx(ct, 120));
        ArrayList<MyCategory> Categorys = new ArrayList<>();

        // initialize your items array
        CategoryListAdapter myCLadapter = new CategoryListAdapter(Categorys,ct,this);
        AsymmetricGridViewAdapter asymmetricAdapter =
                new AsymmetricGridViewAdapter<MyCategory>(ct, listView,myCLadapter);
        listView.setAdapter(asymmetricAdapter);

        Categorys.add(new MyCategory("임시"));
        Categorys.add(new MyCategory("임시"));
        Categorys.add(new MyCategory("임시"));
        Categorys.add(new MyCategory("임시"));
        Categorys.add(new MyCategory("임시"));
        Categorys.add(new MyCategory("임시"));


        return view;
    }
}
