package com.mobprog.promee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.mobprog.promee.model.TaskDataClass;
import com.mobprog.promee.service.TaskCrudService;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {


    String username, email, userId;
    TaskCrudService taskCrudService;
    Dialog dialog;
    TextView detailTitle, detailDate, startTime, endTime, notes;
    EditText editTaskName, editTaskNote, editDate, editStartTime, editEndTime;
    ImageView edit, delete;
    Button editCancelBtn, editUpdateBtn;

    RecyclerView recyclerView;
    List<TaskDataClass> dataList;
    MyAdapter adapterCT;

    String key = "";

    private DatabaseReference root, user, backlog;
    ValueEventListener readUserData;
    //FloatingActionButton deleteButton;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        detailTitle = findViewById(R.id.detailTitle);
        detailDate = findViewById(R.id.detailDate);
        startTime = findViewById(R.id.startTime);
        endTime = findViewById(R.id.endTime);
        notes = findViewById(R.id.notes);
        delete = findViewById(R.id.delete);
        edit = findViewById(R.id.edit);
        //deleteButton = findViewById(R.id.delete);

        dialog = new Dialog(this);
        dialog.setContentView(R.layout.edit_task_dialog);
        dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.dialog_background));

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            detailTitle.setText(bundle.getString("Task name"));
            detailDate.setText(bundle.getString("Date"));
            startTime.setText(bundle.getString("start time"));
            endTime.setText(bundle.getString("end time"));
            notes.setText(bundle.getString("Notes"));

        }
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DetailActivity.this, "delete", Toast.LENGTH_SHORT).show();
            }
        });
       /* editUpdateBtn.setOnClickListener(view -> {
            dialog.dismiss();
        });

        */
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                FirebaseStorage storage = FirebaseStorage.getInstance();

                reference.child(key).removeValue();
                //Toast.makeText(HomeActivity.this, Toast.LENGTH_SHORT).show(),};
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));

                finish();

        /*
        editCancelBtn.setOnClickListener(view -> {
            dialog.dismiss();
            editTaskName.getText().clear();
            editDate.getText().clear();
            editTaskNote.getText().clear();
            editStartTime.getText().clear();
            editEndTime.getText().clear();
        });

         */


            }

        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.show();

            }
        });
        /*
        editUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

         */
/*
        editUpdateBtn.setOnClickListener(view -> {
                    String task_name = editTaskName.getText().toString();
                    String task_note = editTaskNote.getText().toString();
                    String task_date = editDate.getText().toString();
                    String task_start = editStartTime.getText().toString();
                    String task_end = editEndTime.getText().toString();

                    if(task_name.equals("") || task_date.equals("")){
                        Toast.makeText(getApplicationContext(), "Some fields are empty", Toast.LENGTH_SHORT).show();
                    }else{
                        taskCrudService.createNewTask(task_name, task_date, task_start, task_end, task_note);
                        readTask();
                    }

                    dialog.dismiss();

                    editTaskName.getText().clear();
                    editDate.getText().clear();
                    editTaskNote.getText().clear();
                    editStartTime.getText().clear();
                    editEndTime.getText().clear();
                });

 */
    }


    }
