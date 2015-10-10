package akash.com.awesomedatepicker;

import android.app.Fragment;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import java.util.ArrayList;
import java.util.List;

import akash.com.awesomedatepicker.center_lock_utils.CenterLockListener;
import akash.com.awesomedatepicker.center_lock_utils.LockListener;

/**
 * Created by akashsingh on 10/10/15.
 */
public class DatePickerFragment extends Fragment implements LockListener {

    private Context mContext;

    private RecyclerView mDayList;
    private RecyclerView mMonthList;
    private RecyclerView mYearList;

    private View mDayHandle;
    private View mMonthHandle;
    private View mYearHandle;

    private int mSelectedDayPosition;
    private int mSelectedMonthPosition;
    private int mSelectedYearPosition;

    private List<CustomListItem> mDays;
    private List<CustomListItem> mMonths;
    private List<CustomListItem> mYears;

    static class CustomListItem {

        private String value;
        private boolean selected;

        public CustomListItem(String value, boolean selected) {
            this.value = value;
            this.selected = selected;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public boolean isSelected() {
            return selected;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_date_picker, container, false);

        mContext = getActivity();

        initViews(view);

        return view;
    }

    private void initViews(View view) {

        mDayList = (RecyclerView)view.findViewById(R.id.day_list);
        mMonthList = (RecyclerView)view.findViewById(R.id.month_list);
        mYearList = (RecyclerView)view.findViewById(R.id.year_list);

        mDayHandle = view.findViewById(R.id.day_handle);
        mMonthHandle = view.findViewById(R.id.month_handle);
        mYearHandle = view.findViewById(R.id.year_handle);

        setupRecyclerViews();

    }

    private void setupRecyclerViews() {

        setupDayRecyclerView();
        setupMonthRecyclerView();
        setupYearRecyclerView();

    }

    private void setupDayRecyclerView() {

        mDays = getDayList();

        mDays.get(mSelectedDayPosition).setSelected(true);

        mDayList.setLayoutManager(new LinearLayoutManager(getActivity()));
        mDayList.setAdapter(new DateAdapter(mDays, mContext));


        mDayHandle.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                int center = (mDayHandle.getTop() + mDayHandle.getBottom()) / 2;

                CenterLockListener centerLockListener = new CenterLockListener(center, DatePickerFragment.this);
                mDayList.addOnScrollListener(centerLockListener);

                mDayList.scrollToPosition(mSelectedDayPosition);

                int paddingTop = mDayHandle.getTop();
                int paddingBottom = mDayList.getHeight() - mDayHandle.getBottom();

                mDayList.setPadding(mDayList.getPaddingLeft(), paddingTop, mDayList.getPaddingRight(), paddingBottom);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    mDayHandle.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    mDayHandle.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }

            }
        });

    }

    private void setupMonthRecyclerView() {

        mMonths = getMonthList();

        mMonths.get(mSelectedMonthPosition).setSelected(true);

        mMonthList.setLayoutManager(new LinearLayoutManager(getActivity()));
        mMonthList.setAdapter(new DateAdapter(mMonths, mContext));

        mMonthHandle.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                int center = (mMonthHandle.getTop() + mMonthHandle.getBottom()) / 2;

                CenterLockListener centerLockListener = new CenterLockListener(center, DatePickerFragment.this);
                mMonthList.addOnScrollListener(centerLockListener);

                mMonthList.scrollToPosition(mSelectedMonthPosition);

                int paddingTop = mMonthHandle.getTop();
                int paddingBottom = mMonthList.getHeight() - mMonthHandle.getBottom();

                mMonthList.setPadding(mMonthList.getPaddingLeft(), paddingTop, mMonthList.getPaddingRight(), paddingBottom);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    mMonthHandle.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    mMonthHandle.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }

            }
        });

    }

    private void setupYearRecyclerView() {

        mYears = getYearList();

        mYears.get(mSelectedYearPosition).setSelected(true);

        mYearList.setLayoutManager(new LinearLayoutManager(getActivity()));
        mYearList.setAdapter(new DateAdapter(mYears, mContext));


        mYearHandle.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                int center = (mYearHandle.getTop() + mYearHandle.getBottom()) / 2;

                CenterLockListener centerLockListener = new CenterLockListener(center, DatePickerFragment.this);
                mYearList.addOnScrollListener(centerLockListener);

                mYearList.scrollToPosition(mSelectedYearPosition);

                int paddingTop = mYearHandle.getTop();
                int paddingBottom = mYearList.getHeight() - mYearHandle.getBottom();

                mYearList.setPadding(mYearList.getPaddingLeft(), paddingTop, mYearList.getPaddingRight(), paddingBottom);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    mYearHandle.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    mYearHandle.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }

            }
        });

    }


    private List<CustomListItem> getDayList() {

        List<CustomListItem> dayList = new ArrayList<>();

        for(int i = 1; i <= 31; i++) {
            dayList.add(new CustomListItem(i + "", false));
        }

        return dayList;
    }

    private List<CustomListItem> getMonthList() {

        List<CustomListItem> monthList = new ArrayList<>();

        monthList.add(new CustomListItem("Jan", false));
        monthList.add(new CustomListItem("Feb", false));
        monthList.add(new CustomListItem("Mar", false));
        monthList.add(new CustomListItem("Apr", false));
        monthList.add(new CustomListItem("May", false));
        monthList.add(new CustomListItem("Jun", false));
        monthList.add(new CustomListItem("Jul", false));
        monthList.add(new CustomListItem("Aug", false));
        monthList.add(new CustomListItem("Sep", false));
        monthList.add(new CustomListItem("Oct", false));
        monthList.add(new CustomListItem("Nov", false));
        monthList.add(new CustomListItem("Dec", false));

        return monthList;
    }

    private List<CustomListItem> getYearList() {

        List<CustomListItem> yearList = new ArrayList<>();

        for(int i = 1900; i <= 2100; i ++) {

            yearList.add(new CustomListItem(i + "", false));
        }

        return yearList;
    }

    @Override
    public void handlePositionChanged(int pos, int id) {

        switch (id) {

            case R.id.day_list:

                mDays.get(mSelectedDayPosition).setSelected(false);
                mDays.get(pos).setSelected(true);
                mSelectedDayPosition = pos;
                mDayList.getAdapter().notifyDataSetChanged();

                break;

            case R.id.month_list:

                mMonths.get(mSelectedMonthPosition).setSelected(false);
                mMonths.get(pos).setSelected(true);
                mSelectedMonthPosition = pos;
                mMonthList.getAdapter().notifyDataSetChanged();

                break;

            case R.id.year_list:

                mYears.get(mSelectedYearPosition).setSelected(false);
                mYears.get(pos).setSelected(true);
                mSelectedYearPosition = pos;
                mYearList.getAdapter().notifyDataSetChanged();

                break;
        }
    }
}
