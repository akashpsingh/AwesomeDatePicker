# AwesomeDatePicker

A unique and great looking Date Picker widget for the android. This can be used in the place of the android's default DatePicker dialog. Supported on devices with android version 4.0+

**DEMO**

![alt tag](https://cloud.githubusercontent.com/assets/12594899/10558587/1aa231a4-74f3-11e5-8d30-7e616f454b91.gif)

**USAGE**

 - This date picker makes use of `RecyclerView` so you need to add the dependency to your `build.gradle` file:

    `compile 'com.android.support:recyclerview-v7:23.0.1'`


 - The picker can be used as a `Fragment`. The main Fragment used here is `DatePickerFragment`. So to set it up on an Activity, use the following code. The following code will set up the DatePicker with Today's date selected.

```java
mDatePicker = new DatePickerFragment();
transaction.replace(R.id.fragment_container, mDatePickerFragment);
transaction.commit();
```


- If you want to pass custom date to set on it, you can put the values in the `Bundle` arguments:

```java
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
**Getting the user set date back from the DatePicker**

```java
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
