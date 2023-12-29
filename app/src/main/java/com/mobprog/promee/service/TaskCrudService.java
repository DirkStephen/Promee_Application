package com.mobprog.promee.service;

import android.app.Activity;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mobprog.promee.model.TaskDataClass;

public class TaskCrudService {
    String userId;
    String tname, tdate, tstart, tend, tnote;
    String taskId;
    Activity activity;

    public TaskCrudService(String userId, Activity activity) {
        this.userId = userId;
        this.activity = activity;
    }

    public void createNewTask(String tname, String tdate, String tstart, String tend, String tnote) {
        TaskDataClass task = new TaskDataClass(tname, tdate, tstart, tend, tnote);
        DatabaseReference root = FirebaseDatabase.getInstance().getReference();
        DatabaseReference user = root.child("users").child(userId);
        DatabaseReference backlog = user.child("backlog").child("todo");
        DatabaseReference newTask = backlog.push();
        taskId = newTask.getKey();
        newTask.setValue(task)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(activity, "Task created.", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(activity, "Task failed to cr eate.", Toast.LENGTH_SHORT).show();
                    }
                });

    }

//    public void readTask() {
//        //DatabaseReference taskref = backlog.child(taskId);
//        ValueEventListener taskListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                TaskDataClass taskItem = snapshot.getValue(TaskDataClass.class);
//                tname = taskItem.getTname();
//                tdate = taskItem.getTdate();
//                tstart = taskItem.getTstart();
//                tend = taskItem.getTend();
//                tnote = taskItem.getTnote();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(activity, "Error.", Toast.LENGTH_SHORT).show();
//            }
//        };
//        //taskref.addValueEventListener(taskListener);
//
//    }

}
