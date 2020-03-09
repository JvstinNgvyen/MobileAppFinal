package edu.wit.mobileapp.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipDrawable;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;

public class AddAssignmentActivity extends AppCompatActivity {
    private ChipGroup chipGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_assignment_layout);

        chipGroup = findViewById(R.id.class_type_group);

        ArrayList<String> chipList = getIntent().getExtras().getStringArrayList("classList");
        for(int i = 0; i < chipList.size(); i++){
            final Chip chip = new Chip(this);
            ChipDrawable drawable = ChipDrawable.createFromAttributes(this,null,0,R.style.Widget_MaterialComponents_Chip_Choice);
            chip.setChipDrawable(drawable);
            chip.setCheckable(true);
            chip.setClickable(true);
            chip.setPadding(60,10,60,10);
            chip.setText(chipList.get(i));

            chipGroup.addView(chip);
        }

        Button add_btn = (Button)findViewById(R.id.button_add);
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

                Bundle bundle = new Bundle();
                bundle.putString("title", title);
                Log.v("myApp", title);

                bundle.putString("due", due);
                Log.v("myApp", due);

                bundle.putString("class", classes);
                Log.v("myApp", classes);

                bundle.putString("assignment", assignment);
                Log.v("myApp", assignment);

                bundle.putString("priority", priority);
                Log.v("myApp", priority);

                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}
