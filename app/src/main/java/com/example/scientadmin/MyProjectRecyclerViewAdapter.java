package com.example.scientadmin;

import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.scientadmin.ProjectFragment.OnListFragmentInteractionListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Project} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 */
public class MyProjectRecyclerViewAdapter extends RecyclerView.Adapter<MyProjectRecyclerViewAdapter.ViewHolder> {

    private final List<Project> mValues;
    private int mode;
    private Context context;
    private DatabaseReference databaseReference;

    public MyProjectRecyclerViewAdapter(List<Project> items, int mode, Context context) {
        mValues = items;
        this.mode= mode;
        this.context= context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if(mode==0) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_project, parent, false);
        }

        else {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_accept, parent, false);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).projectTitle);
        holder.mContentView.setText(mValues.get(position).projectDesc);
        if(mode==0) {
            holder.btnAccept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new AlertDialog.Builder(context).setTitle("Accept").setMessage("Do you want to accept the Project ?")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    databaseReference= FirebaseDatabase.getInstance().getReference().
                                            child("accepted");
                                    databaseReference.child(holder.mItem.token).setValue(holder.mItem);
                                }
                            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    }).show();
                }
            });
            holder.btnReject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new AlertDialog.Builder(context).setTitle("Reject").setMessage("Do you want to reject the Project ?")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    databaseReference= FirebaseDatabase.getInstance().getReference().
                                            child("rejected");
                                    databaseReference.child(holder.mItem.token).setValue(holder.mItem);

                                }
                            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    }).show();
                }
            });
        }


        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(context, ShowProject.class);
                intent.putExtra("mode",mode);
                intent.putExtra("token",holder.mItem.token);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public void add(Project project) {
        mValues.add(project);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public Button btnAccept;
        public Button btnReject;

        public Project mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.item_number);
            mContentView = (TextView) view.findViewById(R.id.content);
            btnAccept= view.findViewById(R.id.am_btn_accept);
            btnReject= view.findViewById(R.id.am_btn_reject);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
