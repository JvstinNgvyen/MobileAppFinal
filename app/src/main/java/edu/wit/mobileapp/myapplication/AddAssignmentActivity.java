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
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipDrawable;
import com.google.android.material.chip.ChipGroup;
import java.util.ArrayList;
import java.util.Calendar;

public class AddAssignmentActivity extends AppCompatActivity implements View.OnClickListener{
    private ChipGroup chipGroup;
    private int mYear, mMonth, mDay;
    Button addBtn;
    EditText txtDate;
    private static final String DATABASE_NAME = "/database";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_assignment_layout);

        //Set DB PATH
        String path = "/data/data/" + getPackageName() + DATABASE_NAME + ".db";
        Context context = getApplicationContext();
        //Use context and path to create SQLlite helper class object
        SQLlite dbHelper = SQLlite.dbHelper(context, path);

        // Linked views to their ID
        chipGroup = findViewById(R.id.class_type_group);
        txtDate=(EditText)findViewById(R.id.input_due);
        addBtn = (Button)findViewById(R.id.button_add);

        // Set on click listeners
        addBtn.setOnClickListener(this);
        txtDate.setOnClickListener(this);
        ArrayList<String> chipList;

        // Retrieves bundled ArrayList of classes as strings then sets it to a chip choice
        chipList = dbHelper.getCourseNames();
        dbHelper.close();
       // chipList = getIntent().getExtras().getStringArrayList("classList");

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
            Boolean titleValidator = stringValidator(titleText.getText().toString(), titleText);

            EditText dueText = (EditText) findViewById(R.id.input_due);
            Boolean dateValidator = stringValidator(dueText.getText().toString(), dueText);

            TextView assignmentText = findViewById(R.id.textView2);
            TextView classText = findViewById(R.id.textView);
            TextView priorityText = findViewById(R.id.textView3);


            String classes = "";
            String assignment = "";
            String priority = "";

            ChipGroup chipGroupClass = findViewById(R.id.class_type_group);
            ChipGroup chipGroupAssignment = findViewById(R.id.assignment_type_group);
            ChipGroup chipGroupPriority = findViewById(R.id.priority_type_group);
            Boolean isClassChipChecked = false;
            Boolean isAssignmentChipChecked = false;
            Boolean isPrioityChipChecked = false;

            int chipCounter1 = 0;
            for(int i = 0; i < chipGroupClass.getChildCount();i++){
                Chip chip = (Chip)chipGroupClass.getChildAt(i);
                if(chip.isChecked()) {
                    Log.v("myApp", i + " " + chip.getText().toString());
                    isClassChipChecked = true;
                    classes = chip.getText().toString();
                    chipCounter1++;

                }
            }
            if (chipCounter1 == 0) {
                classText.setError("Please select chip");
            }

            int chipCounter2 = 0;
            for(int i = 0; i < chipGroupAssignment.getChildCount();i++){
                Chip chip1 = (Chip)chipGroupAssignment.getChildAt(i);
                if(chip1.isChecked()) {
                    Log.v("myApp", i + " " + chip1.getText().toString());
                    isAssignmentChipChecked = true;
                    assignment = chip1.getText().toString();
                    chipCounter2++;
                }
            }
            if (chipCounter2 == 0) {
                assignmentText.setError("Please select chip");
            }

            int chipCounter3 = 0;
            for(int i = 0; i < chipGroupPriority.getChildCount();i++){
                Chip chip2 = (Chip)chipGroupPriority.getChildAt(i);
                if(chip2.isChecked()) {
                    Log.v("myApp", i + " " + chip2.getText().toString());
                    isPrioityChipChecked = true;
                    priority = chip2.getText().toString();
                    chipCounter3++;
                }
            }
            if (chipCounter3 == 0) {
                priorityText.setError("Please select chip");
            }

            String path = "/data/data/" + getPackageName() + DATABASE_NAME + ".db";
            Context context = getApplicationContext();
            //Use context and path to create SQLlite helper class object
            SQLlite dbHelper = SQLlite.dbHelper(context, path);

            //If everything validates correctly submit assignment to DB and move to next activity
            if (titleValidator && dateValidator && isAssignmentChipChecked && isClassChipChecked && isPrioityChipChecked) {
                String title = titleText.getText().toString();
                String due = dueText.getText().toString();

                dbHelper.insertAssignment(title, priority, classes, due, assignment);
                dbHelper.close();
                finish();
                startActivity(intent);
            }
        }
    }
    public boolean chipValidator(Chip chip) {
        if (!chip.isChecked()) {
            chip.setBackgroundColor(1);
            return false;
        }
        else {
            return true;
        }
    }
    public boolean stringValidator(String string, EditText textInput) {
        if (string == null || string.isEmpty()) {
            textInput.setError("Please enter some text");
            return false;
        }
        else {
            return true;
        }
    }
}
