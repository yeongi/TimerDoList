package com.example.timerlist.fragment;

import android.content.Context;
import android.icu.util.ULocale;
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
import java.util.Locale;

public class FragCategory extends Fragment {
    private View view;
    AsymmetricGridView listView;
    ArrayList<MyCategory> Categorys;
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
        Categorys = new ArrayList<>();

        // initialize your items array
        CategoryListAdapter myCLadapter = new CategoryListAdapter(Categorys,ct,this);
        AsymmetricGridViewAdapter asymmetricAdapter =
                new AsymmetricGridViewAdapter<MyCategory>(ct, listView,myCLadapter);
        listView.setAdapter(asymmetricAdapter);

        Categorys.add(new MyCategory("운동"));
        Categorys.add(new MyCategory("공부"));
        Categorys.add(new MyCategory("동기부여"));
        Categorys.add(new MyCategory("라면"));
        Categorys.add(new MyCategory("계란"));
        Categorys.add(new MyCategory("미정"));

        return view;
    }

    public ArrayList<String> getCategoryList(){
        ArrayList<String> categorys = new ArrayList<String>();
        for(int i = 0 ; i < Categorys.size() ; i++){
            MyCategory temp = Categorys.get(i);
            categorys.add(temp.getCategoryTitle());
        }
        return categorys;
    }
}
