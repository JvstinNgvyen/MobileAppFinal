package edu.wit.mobileapp.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipDrawable;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.File;
import java.util.ArrayList;

public class AddInfoActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText editText;
    private Button next_btn;
    private Button button;
    private ChipGroup chipGroup;

    private TextInputLayout yearText;
    private TextInputLayout schoolText;

    Chip quarterlyChip;
    Chip semesterlyChip;
    Chip trimesterlyChip;
    private final String TAG = "MAIN";
    private static final String DATABASE_NAME = "database";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.information_entry_layout);

        //Set DB PATH
        String path = "/data/data/" + getPackageName() + "/database.db";
        SQLiteDatabase db;
        //Open or Create DB
        db = SQLiteDatabase.openOrCreateDatabase(path, null);
        //Get Application context then use it in SQLlite helper class
        Context context = getApplicationContext();
        final SQLlite dbHelper = new SQLlite(context);
        File f = context.getDatabasePath(DATABASE_NAME);
        long dbSize = f.length();
        if (dbSize == 0) {
            dbHelper.onCreate(db);
        }

        // Linked Views to their ID
        editText = findViewById(R.id.edit_text_add_chip);
        button = findViewById(R.id.btn_add_chip_class);
        chipGroup = findViewById(R.id.class_group);
        next_btn = findViewById(R.id.buttonNext);


        next_btn.setOnClickListener(this);

        //locate static elements on screen
        yearText = findViewById(R.id.textInputYear);

        schoolText = findViewById(R.id.textInputSchoolName);

        quarterlyChip = findViewById(R.id.chip_quarter);

        semesterlyChip = findViewById(R.id.chip_semester);

        trimesterlyChip = findViewById(R.id.chip_trimester);

        // OnClickListener in next_btn to Bundle and Intent to AddAssignmentActivity
        // This bundles the chip texts inputted from the user
        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(AddInfoActivity.this, AddAssignmentActivity.class);
                Integer year = Integer.parseInt(yearText.getEditText().getText().toString());
                String school = schoolText.getEditText().getText().toString();
                int semesters;
                if (quarterlyChip.isActivated()){
                    semesters = 4;
                }
                else if (semesterlyChip.isActivated()) {
                    semesters = 2;
                }
                else {
                    semesters = 3;
                }
                dbHelper.insertYear(semesters, school, year);
                ChipGroup chipGroupClass = findViewById(R.id.class_group);
                Integer query = dbHelper.getYear().getColumnCount();
                String stringQuery = query.toString();
                Log.d(TAG, stringQuery);

                ArrayList<String > chipClassList = new ArrayList<String>();

                for(int i = 0; i < chipGroupClass.getChildCount();i++){
                    Chip chip = (Chip)chipGroup.getChildAt(i);
                        Log.v("myApp",i + " "+ chip.getText().toString());
                        chipClassList.add(chip.getText().toString());
                }
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("classList", chipClassList);
                intent.putExtras(bundle);
                startActivity(intent);
            }

        });
    }

    public void btnClickAddClass(View view){
        final Chip chip = new Chip(this);
        // Sets chip properties and add it the the chipGroup of classes
        ChipDrawable drawable = ChipDrawable.createFromAttributes(this,null,0,R.style.Widget_MaterialComponents_Chip_Entry);
        chip.setChipDrawable(drawable);
        chip.setCheckable(false);
        chip.setClickable(false);
        chip.setPadding(60,10,60,10);
        chip.setText(editText.getText().toString());

        chip.setOnCloseIconClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chipGroup.removeView(chip);
            }
        });
        chipGroup.addView(chip);
        editText.setText("");
    }

    @Override
    public void onClick(View v) {
        // OnClickListener in next_btn to Bundle and Intent to AddAssignmentActivity
        // This bundles the chip texts inputted from the user
        if(v == next_btn) {
            Intent intent = new Intent();
            intent.setClass(AddInfoActivity.this, AddAssignmentActivity.class);

            ChipGroup chipGroupClass = findViewById(R.id.class_group);

            ArrayList<String> chipClassList = new ArrayList<String>();

            for (int i = 0; i < chipGroupClass.getChildCount(); i++) {
                Chip chip = (Chip) chipGroup.getChildAt(i);
                Log.v("myApp", i + " " + chip.getText().toString());
                chipClassList.add(chip.getText().toString());
            }
            Bundle bundle = new Bundle();
            bundle.putStringArrayList("classList", chipClassList);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }
}
