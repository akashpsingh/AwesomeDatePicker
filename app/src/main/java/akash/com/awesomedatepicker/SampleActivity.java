package akash.com.awesomedatepicker;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class SampleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // Only for post LOLLIPOP versions.
            getWindow().setStatusBarColor(getResources().getColor(R.color.black_base));
        }

        DatePickerFragment fragment = new DatePickerFragment();

        fragment.setDateSelectedListener(new DateSelectListener() {
            @Override
            public void onDateSelected(int day, int month, int year) {

                Toast.makeText(SampleActivity.this, "day : " + day + " month : " + month + " year : " + year, Toast.LENGTH_SHORT).show();
            }
        });

        Bundle args = new Bundle();
        args.putInt(DatePickerFragment.DAY, 9);
        args.putInt(DatePickerFragment.MONTH, 11);
        args.putInt(DatePickerFragment.YEAR, 1992);

        fragment.setArguments(args);

        getFragmentManager().beginTransaction()
                .add(R.id.fragment_container, fragment)
                .commit();

    }
    

}
