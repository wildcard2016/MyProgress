package wildcard.android.com.myprogress;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class NewTask extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "NewTask";

    ChipsMultiAutoCompleteTextView mu;

    private EditText newTask;
    private EditText StartDate;
    private EditText EndDate;

    SharedPreferences sharedPreferences;

    private DatePickerDialog StartDatePickerDialog;
    private DatePickerDialog EndDatePickerDialog;

    private TextView add;
    private ImageView cancel;

    private SimpleDateFormat dateFormatter;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        dateFormatter = new SimpleDateFormat("dd MMM yyyy", Locale.US);

        findViewById();

        setDateTimeField();

        mu = (ChipsMultiAutoCompleteTextView) findViewById(R.id.multiAutoCompleteTextView1);

        String[] item = getResources().getStringArray(R.array.tags);

        Log.i("", "Tag Count : " + item.length);
        mu.setAdapter(new ArrayAdapter(this,
                android.R.layout.simple_dropdown_item_1line, item));
        mu.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

        setListeners();
    }


    private void findViewById(){
        StartDate = (EditText) findViewById(R.id.start_date);
        StartDate.setInputType(InputType.TYPE_NULL);
        StartDate.requestFocus();
        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.units_array, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        EndDate = (EditText) findViewById(R.id.end_date);
        EndDate.setInputType(InputType.TYPE_NULL);

        newTask = (EditText) findViewById(R.id.NewTask);

        add = (TextView) findViewById(R.id.add);
        cancel = (ImageView) findViewById(R.id.cancel);
    }

    private void setDateTimeField() {
        StartDate.setOnClickListener(this);
        EndDate.setOnClickListener(this);

        Calendar newCalendar = Calendar.getInstance();
        StartDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year,monthOfYear,dayOfMonth);
                StartDate.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        EndDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                EndDate.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

    }

    @Override
    public void onClick(View view) {
        if (view != null) {
            Log.d(TAG, view.toString());
        }else {
            Log.d(TAG, "view is null");
        }

        if(view == StartDate) {
            StartDatePickerDialog.show();
        } else if (view == EndDate) {
            EndDatePickerDialog.show();
        }
    }

    private void setListeners() {
        StartDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
        });

        EndDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NewTask.this, MainActivity.class);
                startActivity(intent);
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String taskName = newTask.getText().toString();
                String startTime = StartDate.getText().toString();
                String endTime = EndDate.getText().toString();
                String tags = mu.getText().toString();
                String tag = tags.split(",")[0];
                Log.d(TAG, tags);

                if (hasBlank(taskName, startTime, endTime, tags)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(NewTask.this);
                    builder.setMessage("Oops! Some fields are missing");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    builder.show();
                }else {
                    MyUtils.AddToPref(sharedPreferences, "Names", taskName);
                    MyUtils.AddToPref(sharedPreferences, "Start", startTime);
                    MyUtils.AddToPref(sharedPreferences, "End", endTime);
                    MyUtils.AddToPref(sharedPreferences, "Tags", tag);
                    MyUtils.AddToPref(sharedPreferences, "Progress", "33");
                    Intent intent = new Intent(NewTask.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private boolean hasBlank(String... args) {
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("")) {
                return true;
            }
        }
        return false;
    }
}