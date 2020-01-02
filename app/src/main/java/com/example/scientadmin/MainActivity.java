package com.example.scientadmin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity  {

    private List<Project> newProject, acceptedProject, rejectedProject;
    private DatabaseReference databaseReference, mNew, mAccepted, mRejected;
    private MyProjectRecyclerViewAdapter newAdapter, acceptedAdapter, rejectedAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newProject= new ArrayList<>();
        acceptedProject= new ArrayList<>();
        rejectedProject= new ArrayList<>();

        ViewPager viewPager= findViewById(R.id.am_pager);
        TabLayout tabLayout= findViewById(R.id.am_pager_strip);

        newAdapter= new MyProjectRecyclerViewAdapter(newProject,  0, this);
        acceptedAdapter= new MyProjectRecyclerViewAdapter(acceptedProject,  1, this);

        mAccepted.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Project project= dataSnapshot.getValue(Project.class);
                acceptedAdapter.add(project);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        mRejected.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Project project= dataSnapshot.getValue(Project.class);
                rejectedAdapter.add(project);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(new PagerAdapter(getSupportFragmentManager()));
    }

    public void onListFragmentInteraction(Project item) {
        Intent intent= new Intent(this, ShowProject.class);
        intent.putExtra("mode",0 );

    }

    private class PagerAdapter extends FragmentPagerAdapter {
        PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0: return new ProjectFragment(newAdapter);
                case 1: return new ProjectFragment(acceptedAdapter);
                case 2: return new ProjectFragment(rejectedAdapter);

                default: return null;
            }
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0: return "New Project";
                case 1: return "Accepted";
                case 2: return "Rejected";

                default:return null;
            }
        }
    }
}
