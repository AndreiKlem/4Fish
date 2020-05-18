package com.example.a4fish;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CalendarGridFragment} factory method to
 * create an instance of this fragment.
 */
public class CalendarGridFragment extends Fragment {

    CalendarGridAdapter calendarGridAdapter;
    ViewPager2 viewPager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.control_calendar, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        calendarGridAdapter = new CalendarGridAdapter(this);
        viewPager = view.findViewById(R.id.pager);
        viewPager.setAdapter(calendarGridAdapter);
    }
}

class CalendarGridAdapter extends FragmentStateAdapter {
    public CalendarGridAdapter(Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment = new SingleGridFragment();
        Bundle args = new Bundle();
        args.putInt(SingleGridFragment.ARG_OBJECT, position + 1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}

class SingleGridFragment extends Fragment {
    public static final String ARG_OBJECT = "object";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_calendar_grid, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();
        ((GridView) view.findViewById(R.id.calendar_grid))
                .setTooltipText(Integer.toString(args.getInt(ARG_OBJECT)));
    }
}
