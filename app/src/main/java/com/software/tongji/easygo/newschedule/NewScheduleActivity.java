package com.software.tongji.easygo.newschedule;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.software.tongji.easygo.R;

import java.sql.Time;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewScheduleActivity extends AppCompatActivity implements NewScheduleView{

    private NewSchedulePresenter mNewSchedulePresenter;

    public static final String NEW_TYPE = "new_type";
    public static final String NEW_ADDRESS = "new_address";
    public static final String NEW_BEGIN_TIME = "new_begin_time";

    @BindView(R.id.new_schedule_address)
    TextInputEditText mScheduleAddress;
    @BindView(R.id.new_schedule_date)
    TextInputEditText mScheduleDate;
    @BindView(R.id.new_schedule_time)
    TextInputEditText mScheduleTime;
    @BindView(R.id.spinner_schedule_type)
    Spinner mScheduleType;
    @BindView(R.id.new_schedule_cost)
    TextInputEditText mScheduleCost;
    @BindView(R.id.new_schedule_remark)
    TextInputEditText mScheduleRemark;
    @BindView(R.id.new_schedule_ok)
    FloatingActionButton mNewScheduleOk;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_schedule);

        ButterKnife.bind(this);

        mScheduleDate.setInputType(InputType.TYPE_NULL);
        mScheduleDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(hasFocus){
                    showDatePickDialog();
                }
            }
        });
        mScheduleDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickDialog();
            }
        });

        mScheduleTime.setInputType(InputType.TYPE_NULL);
        mScheduleTime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(hasFocus){
                    showTimePickDialog();
                }
            }
        });
        mScheduleTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePickDialog();
            }
        });

        mNewScheduleOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //put extra
                finish();
            }
        });
    }

    @Override
    public void showDatePickDialog() {
        Calendar calendar = Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        mScheduleDate.setText(year + "/" + (month + 1) + "/" + day);
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    @Override
    public void showTimePickDialog() {
        Calendar calendar = Calendar.getInstance();

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                        mScheduleTime.setText(hour + ":" + minute);
                    }
                }, calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE), true);
        timePickerDialog.show();
    }
}