package edu.wit.mobileapp.myapplication;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipDrawable;
import com.google.android.material.chip.ChipGroup;
import java.util.ArrayList;
import java.util.Calendar;

public class AddAssignmentActivity extends AppCompatActivity implements View.OnClickListener{
    private ChipGroup chipGroup;
    private ArrayList<String> chipList;
    private int mYear, mMonth, mDay;
    Button addBtn;
    EditText txtDate;
    private static final String DATABASE_NAME = "/database";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_assignment_layout);

        // Linked views to their ID
        chipGroup = findViewById(R.id.class_type_group);
        txtDate=(EditText)findViewById(R.id.input_due);
        addBtn = (Button)findViewById(R.id.button_add);

        // Set on click listeners
        addBtn.setOnClickListener(this);
        txtDate.setOnClickListener(this);

        // Retrieves bundled ArrayList of classes as strings then sets it to a chip choice
        chipList = getIntent().getExtras().getStringArrayList("classList");
        for(int i = 0; i < chipList.size(); i++){
            final Chip chip = new Chip(this);
            ChipDrawable drawable = ChipDrawable.createFromAttributes(this,null,0,R.style.Widget_MaterialComponents_Chip_Choice);
            chip.setChipDrawable(drawable);
            chip.setCheckable(true);
            chip.setClickable(true);
            chip.setCheckedIconVisible(true);
            chip.setPadding(0,10,0,10);
            chip.setChipBackgroundColorResource(R.color.default_chip_color);
            chip.setText(chipList.get(i));
            chipGroup.addView(chip);
        }
    }

    @Override
    public void onClick(View v) {

        // Get Due Date
        if (v == txtDate) {
            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            txtDate.setText((monthOfYear + 1) + "/" + dayOfMonth + "/" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }

        // Add Button
        if( v == addBtn){
            Intent intent = new Intent();
            intent.setClass(AddAssignmentActivity.this, CardViewAssignmentActivity.class);

            EditText titleText = (EditText) findViewById(R.id.input_title);
            String title = titleText.getText().toString();

            EditText dueText = (EditText) findViewById(R.id.input_due);
            String due = dueText.getText().toString();

            String classes = "";
            String assignment = "";
            String priority = "";

            ChipGroup chipGroupClass = findViewById(R.id.class_type_group);
            ChipGroup chipGroupAssignment = findViewById(R.id.assignment_type_group);
            ChipGroup chipGroupPriority = findViewById(R.id.priority_type_group);

            for(int i = 0; i < chipGroupClass.getChildCount();i++){
                Chip chip = (Chip)chipGroupClass.getChildAt(i);
                if(chip.isChecked()) {
                    Log.v("myApp", i + " " + chip.getText().toString());
                    classes = chip.getText().toString();
                }
            }

            for(int i = 0; i < chipGroupAssignment.getChildCount();i++){
                Chip chip1 = (Chip)chipGroupAssignment.getChildAt(i);
                if(chip1.isChecked()) {
                    Log.v("myApp", i + " " + chip1.getText().toString());
                    assignment = chip1.getText().toString();
                }
            }

            for(int i = 0; i < chipGroupPriority.getChildCount();i++){
                Chip chip2 = (Chip)chipGroupPriority.getChildAt(i);
                if(chip2.isChecked()) {
                    Log.v("myApp", i + " " + chip2.getText().toString());
                    priority = chip2.getText().toString();
                }
            }


            String path = "/data/data/" + getPackageName() + DATABASE_NAME + ".db";
            Context context = getApplicationContext();
            //Use context and path to create SQLlite helper class object
            SQLlite dbHelper = SQLlite.dbHelper(context, path);
            dbHelper.insertAssignment(title, priority,classes,due,assignment);
            //dbHelper.close();
            startActivity(intent);
        }
    }
}
