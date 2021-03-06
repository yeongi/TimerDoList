package com.example.timerlist.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.timerlist.data.List;
import com.example.timerlist.data.MyCategory;
import com.example.timerlist.R;
import com.felipecsl.asymmetricgridview.library.Utils;
import com.felipecsl.asymmetricgridview.library.widget.AsymmetricGridView;
import com.felipecsl.asymmetricgridview.library.widget.AsymmetricGridViewAdapter;

import java.util.ArrayList;

public class FragCategory extends Fragment {
    private View view;
    AsymmetricGridView listView;
    ArrayList<MyCategory> Categorys;
    ArrayList<List> todayDo;
    Context ct;

    public static FragCategory newInstance(){
        Bundle todayList = new Bundle();
        FragCategory fragCategory = new FragCategory();
        fragCategory.setArguments(todayList);
        return fragCategory;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        todayDo = new ArrayList<List>();
        //데이터 송신
        Bundle todayList = new Bundle();
        todayList.putParcelableArrayList("todayList",todayDo);
        getParentFragmentManager().setFragmentResult("todayList", todayList);

        view = inflater.inflate(R.layout.fragment_frag_category,container,false);
        ct = view.getContext();


        listView = (AsymmetricGridView) view.findViewById(R.id.categoryView);
        // Choose your own preferred column width
        listView.setRequestedColumnWidth(Utils.dpToPx(ct, 120));
        Categorys = new ArrayList<>();

        // initialize your items array
        CategoryListAdapter myCLadapter = new CategoryListAdapter(Categorys,ct,this,todayDo);
        AsymmetricGridViewAdapter asymmetricAdapter =
                new AsymmetricGridViewAdapter<MyCategory>(ct, listView,myCLadapter);
        listView.setAdapter(asymmetricAdapter);

        Categorys.add(new MyCategory("운동"));
        Categorys.add(new MyCategory("공부"));
        Categorys.add(new MyCategory("동기부여"));
        Categorys.add(new MyCategory("라면"));
        Categorys.add(new MyCategory("계란 조리법"));
        Categorys.add(new MyCategory("파스타 면삶기"));
        Categorys.add(new MyCategory("임시"));
        Categorys.add(new MyCategory("임시1"));
        Categorys.add(new MyCategory("임시2"));
        Categorys.add(new MyCategory("임시3"));

        return view;
    }

}
