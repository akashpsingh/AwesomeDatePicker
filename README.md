# AwesomeDatePicker

An awesome and different date picker for android 4.0+ devices.

**DEMO**

![alt tag](https://cloud.githubusercontent.com/assets/12594899/10558587/1aa231a4-74f3-11e5-8d30-7e616f454b91.gif)

**USAGE**

This date picker makes use of `RecyclerView` so you need to add the following dependency to your `build.gradle` file:

    compile 'com.android.support:recyclerview-v7:23.0.1'


The picker can be used as a `Fragment`. The main Fragment used here is `DatePickerFragment`. So to set it up on an Activity just use the following code. The following code will set up the DatePicker with Today's date on it.

```
mDatePicker = new DatePickerFragment();
transaction.replace(R.id.fragment_container, mDatePickerFragment);
transaction.commit();
```


If you want to pass custom date to set on it, you can put the values in arguments and pass like the following:

```
DatePickerFragment fragment = new DatePickerFragment();

Bundle args = new Bundle();
args.putInt(DatePickerFragment.DAY, 9);
args.putInt(DatePickerFragment.MONTH, 11);
args.putInt(DatePickerFragment.YEAR, 1992);

fragment.setArguments(args);

  getFragmentManager().beginTransaction()
      .add(R.id.fragment_container, fragment)
      .commit();
                
```
**Getting the value back from the DatePicker**

```
DateSelectListener listener = new DateSelectListener() {
            @Override
            public void onDateSelected(int day, int month, int year) {

                Toast.makeText(SampleActivity.this, "day : " + day + " month : " + month + " year : " + year,      Toast.LENGTH_SHORT).show();
            }
        };
        
        fragment.setDateSelectedListener(listener); 
  ```
  
  **TODO**
  
  1. Handle change in orientation
  2. More customization
