package com.example.scientadmin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

public class ShowProject extends AppCompatActivity {
    String message, Token;
    private DatabaseReference mDatabaseReference;
    TextView tvName, tvRollNo, tvContact, tvEmail, tvProjectTitle, tvProjectIdea, tvResponse;
    private int mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_project);
        mode=getIntent().getIntExtra("mode",0);
        Token=getIntent().getStringExtra("token");

        tvName=findViewById(R.id.tvName);
        tvRollNo= findViewById(R.id.tvRollNo);
        tvContact= findViewById(R.id.tvContactNo);
        tvEmail= findViewById(R.id.tvEmail);
        tvProjectTitle= findViewById(R.id.tvProjectTitle);
        tvProjectIdea= findViewById(R.id.tvProjectIdea);
        tvResponse= findViewById(R.id.tvResponse);

        mDatabaseReference= FirebaseDatabase.getInstance().getReference();
        if(mode==0) {
            mDatabaseReference.child("new").child(Token).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Log.i("tag",""+Token);
                    Project project= dataSnapshot.getValue(Project.class);
                    tvName.setText(project.name);
                    tvRollNo.setText(project.roll_No);
                    tvContact.setText(project.contact_No);
                    tvEmail.setText(project.email);
                    tvProjectTitle.setText(project.projectTitle);
                    tvProjectIdea.setText(project.projectDesc);
                    tvResponse.setText(message);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                    Toast.makeText(getApplicationContext(), "Failed to load", Toast.LENGTH_SHORT);
                }
            });
        }
        else if(mode==1) {
            mDatabaseReference.child("accepted").child(Token).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Project project= dataSnapshot.getValue(Project.class);
                            tvName.setText(project.name);
                            tvRollNo.setText(project.roll_No);
                            tvContact.setText(project.contact_No);
                            tvEmail.setText(project.email);
                            tvProjectTitle.setText(project.projectTitle);
                            tvProjectIdea.setText(project.projectDesc);
                            tvResponse.setText(message);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                            Toast.makeText(getApplicationContext(), "Failed to load", Toast.LENGTH_SHORT);
                        }
                    });
        }
        else mDatabaseReference.child("rejected").child(Token).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Project project= dataSnapshot.getValue(Project.class);
                            tvName.setText(project.name);
                            tvRollNo.setText(project.roll_No);
                            tvContact.setText(project.contact_No);
                            tvEmail.setText(project.email);
                            tvProjectTitle.setText(project.projectTitle);
                            tvProjectIdea.setText(project.projectDesc);
                            tvResponse.setText(message);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                            Toast.makeText(getApplicationContext(), "Failed to load", Toast.LENGTH_SHORT);
                        }
                    });
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences sharedPreferences= getSharedPreferences("preference", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= sharedPreferences.edit();

        editor.putString("message", message);
        editor.apply();
    }

}
