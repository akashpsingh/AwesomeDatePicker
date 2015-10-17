package akash.com.awesomedatepicker;

import android.app.Fragment;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import akash.com.awesomedatepicker.center_lock_utils.CenterLockListener;
import akash.com.awesomedatepicker.center_lock_utils.LockListener;

/**
 * Created by akashsingh on 10/10/15.
 */
public class DatePickerFragment extends Fragment implements LockListener {

    public static final String DAY = "day";
    public static final String MONTH = "month";
    public static final String YEAR = "year";

    private static final String SELECTED_DAY = "selected_day";
    private static final String SELECTED_MONTH = "selected_month";
    private static final String SELECTED_YEAR = "selected_year";


    private Context mContext;

    private RecyclerView mDayList;
    private RecyclerView mMonthList;
    private RecyclerView mYearList;

    private TextView mDayHandle;
    private TextView mMonthHandle;
    private TextView mYearHandle;

    private int mSelectedDayPosition;
    private int mSelectedMonthPosition;
    private int mSelectedYearPosition;

    private List<CustomListItem> mDays;
    private List<CustomListItem> mMonths;
    private List<CustomListItem> mYears;

    private DateSelectListener mDateSelectListener;

    private View mToolbarDone;

    private int mInitDay;
    private int mInitMonth;
    private int mInitYear;

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

        mInitDay = mInitMonth = mInitYear = -1;

        if(getArguments() != null) {

            mInitDay = getArguments().getInt(DAY);
            mInitMonth = getArguments().getInt(MONTH);
            mInitYear = getArguments().getInt(YEAR);

        }

        initViews(view);

        return view;
    }

    private void initViews(View view) {

        mDayList = (RecyclerView)view.findViewById(R.id.day_list);
        mMonthList = (RecyclerView)view.findViewById(R.id.month_list);
        mYearList = (RecyclerView)view.findViewById(R.id.year_list);

        mDayHandle = (TextView)view.findViewById(R.id.day_handle);
        mMonthHandle = (TextView)view.findViewById(R.id.month_handle);
        mYearHandle = (TextView)view.findViewById(R.id.year_handle);

        mToolbarDone = view.findViewById(R.id.toolbar_done);

        mToolbarDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int day = Integer.parseInt(mDays.get(mSelectedDayPosition).getValue());
                int month = mSelectedMonthPosition + 1;
                int year = Integer.parseInt(mYears.get(mSelectedYearPosition).getValue());

                mDateSelectListener.onDateSelected(day, month, year);

            }
        });

        setupRecyclerViews();

    }

    private void setupRecyclerViews() {

        setupMonthRecyclerView();
        setupYearRecyclerView();
        setupDayRecyclerView();


    }

    private void setupDayRecyclerView() {

        mDays = getDayList();

        if(mInitDay != -1) {

            mSelectedDayPosition = mInitDay - 1;

        } else {

            mSelectedDayPosition = Calendar.getInstance().get(Calendar.DAY_OF_MONTH) - 1;
        }

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

        if(mInitMonth != -1) {

            mSelectedMonthPosition = mInitMonth - 1;

        } else {

            mSelectedMonthPosition = Calendar.getInstance().get(Calendar.MONTH);

        }

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

        if(mInitYear != -1) {

            mSelectedYearPosition = mInitYear - 1900;

        } else {

            mSelectedYearPosition = Calendar.getInstance().get(Calendar.YEAR) - 1900;

        }

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

        int selectedMonth = mSelectedMonthPosition;
        int selectedYear = Integer.parseInt(mYears.get(mSelectedYearPosition).getValue());

        int daysCount;

        if(selectedMonth == 0 || selectedMonth == 2 || selectedMonth == 4 || selectedMonth == 6 || selectedMonth == 7
                || selectedMonth == 9 || selectedMonth == 11) {

            daysCount = 31;

        } else if(selectedMonth == 1) {

            if(isLeapYear(selectedYear)) {
                daysCount = 29;
            } else {
                daysCount = 28;
            }
        } else {

            daysCount = 30;
        }

        List<CustomListItem> dayList = new ArrayList<>();

        for(int i = 1; i <= daysCount; i++) {
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

                if(pos >= mDays.size()) {
                    pos = mDays.size() - 1;
                }

                mDays.get(mSelectedDayPosition).setSelected(false);
                mDays.get(pos).setSelected(true);
                mSelectedDayPosition = pos;
                mDayList.getAdapter().notifyDataSetChanged();

                break;

            case R.id.month_list:

                if(pos >= mMonths.size()) {
                    pos = mMonths.size() - 1;
                }

                mMonths.get(mSelectedMonthPosition).setSelected(false);
                mMonths.get(pos).setSelected(true);
                mSelectedMonthPosition = pos;
                mMonthList.getAdapter().notifyDataSetChanged();

                if(pos == 0 || pos == 2 || pos == 4 || pos == 6 || pos == 7 || pos == 9 || pos == 11) {

                    if (mDays.size() != 31) {

                        for(int i = mDays.size() + 1; i <= 31; i++) {
                            mDays.add(new CustomListItem(i + "", false));
                        }
                    }

                } else if(pos == 1) {

                    int selectedYear = Integer.parseInt(mYears.get(mSelectedYearPosition).getValue());

                    if(isLeapYear(selectedYear)) {

                        if(mDays.size() < 29) {

                            mDays.add(new CustomListItem("29", false));

                        } else if(mDays.size() > 29) {

                            for(int i = mDays.size() - 1; i > 28; i--) {
                                mDays.remove(i);
                            }

                            if(mSelectedDayPosition > 28) {
                                mSelectedDayPosition = 28;
                            }

                        }

                    } else {

                        if(mDays.size() > 28) {

                            for(int i = mDays.size() - 1; i > 27; i--) {
                                mDays.remove(i);
                            }

                            if(mSelectedDayPosition > 27) {
                                mSelectedDayPosition = 27;
                            }
                        }
                    }

                } else if(mDays.size() != 30) {

                    if(mDays.size() < 30) {

                        //add additional days to the list

                        for(int i = mDays.size() + 1; i <= 30; i++) {
                            mDays.add(new CustomListItem(i + "", false));
                        }

                    } else {

                        if(mDays.size() == 31) {
                            mDays.remove(30);
                        }

                        if (mSelectedDayPosition > 29) {
                            mSelectedDayPosition = 29;
                        }

                    }
                }

                mDays.get(mSelectedDayPosition).setSelected(true);
                mDayList.getAdapter().notifyDataSetChanged();
                mDayList.scrollToPosition(mSelectedDayPosition);

                break;

            case R.id.year_list:

                if(pos >= mYears.size()) {
                    pos = mYears.size() - 1;
                }

                mYears.get(mSelectedYearPosition).setSelected(false);
                mYears.get(pos).setSelected(true);
                mSelectedYearPosition = pos;
                mYearList.getAdapter().notifyDataSetChanged();

                int selectedYear = Integer.parseInt(mYears.get(pos).getValue());

                if(isLeapYear(selectedYear)) {

                    if(mSelectedMonthPosition == 1) {

                        if(mDays.size() < 29) {

                            mDays.add(new CustomListItem("29", false));

                        } else if(mDays.size() > 29) {

                            for(int i = mDays.size() - 1; i > 28; i--) {
                                mDays.remove(i);
                            }

                            if(mSelectedDayPosition > 28) {
                                mSelectedDayPosition = 28;
                            }
                        }
                    }

                } else {

                    if(mSelectedMonthPosition == 1) {

                        if(mDays.size() > 28) {

                            for(int i = mDays.size() - 1; i > 27; i--) {
                                mDays.remove(i);
                            }

                            if(mSelectedDayPosition > 27) {
                                mSelectedDayPosition = 27;
                            }

                        }
                    }

                }


                mDays.get(mSelectedDayPosition).setSelected(true);
                mDayList.getAdapter().notifyDataSetChanged();
                mDayList.scrollToPosition(mSelectedDayPosition);

                break;
        }

        Log.d("====day:", mSelectedDayPosition +"");
        Log.d("====month:", mSelectedMonthPosition+"");
        Log.d("====year:", mSelectedYearPosition+"\n");
    }

    @Override
    public void hideOtherLists(int scrollingListId) {

        switch(scrollingListId) {

            case R.id.day_list :

                mMonthList.setVisibility(View.GONE);
                mYearList.setVisibility(View.GONE);

                mMonthHandle.setText(mMonths.get(mSelectedMonthPosition).getValue());
                mYearHandle.setText(mYears.get(mSelectedYearPosition).getValue());
                break;

            case R.id.month_list :

                mDayList.setVisibility(View.GONE);
                mYearList.setVisibility(View.GONE);

                mDayHandle.setText(mDays.get(mSelectedDayPosition).getValue());
                mYearHandle.setText(mYears.get(mSelectedYearPosition).getValue());
                break;

            case R.id.year_list :

                mDayList.setVisibility(View.GONE);
                mMonthList.setVisibility(View.GONE);

                mDayHandle.setText(mDays.get(mSelectedDayPosition).getValue());
                mMonthHandle.setText(mMonths.get(mSelectedMonthPosition).getValue());

        }

    }

    @Override
    public void makeListsVisible() {

        mDayHandle.setText("");
        mMonthHandle.setText("");
        mYearHandle.setText("");

        mDayList.setVisibility(View.VISIBLE);
        mMonthList.setVisibility(View.VISIBLE);
        mYearList.setVisibility(View.VISIBLE);

    }

    private boolean isLeapYear(int year) {

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        return cal.getActualMaximum(Calendar.DAY_OF_YEAR) > 365;
    }

    public void setDateSelectedListener(DateSelectListener listener) {
        mDateSelectListener = listener;
    }



    /*@Override
    public void onSaveInstanceState(Bundle outState) {

        outState.putInt(SELECTED_DAY, mSelectedDayPosition);
        outState.putInt(SELECTED_MONTH, mSelectedMonthPosition);
        outState.putInt(SELECTED_YEAR, mSelectedYearPosition);

        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(savedInstanceState != null) {

            mSelectedDayPosition = savedInstanceState.getInt(SELECTED_DAY);
            mSelectedMonthPosition = savedInstanceState.getInt(SELECTED_MONTH);
            mSelectedYearPosition = savedInstanceState.getInt(SELECTED_YEAR);
        }
    }*/
}
