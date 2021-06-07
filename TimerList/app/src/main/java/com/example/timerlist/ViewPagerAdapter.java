package com.example.timerlist;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    public ViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }


    // 프래그먼트를 보여주는 처리를 구현한 곳
    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return FragMain.newInstance();
            case 1:
                return FragCal.newInstance();
            case 2:
                return FragCategory.newInstance();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {

        return 3;
    }

    //하단의 탭 레이아웃 인디케이터 쪽에 텍스트 선언해주는 곳
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Timer";
            case 1:
                return "Calendar";
            case 2:
                return "DoList";
            default:
                return null;
        }
    }


}
