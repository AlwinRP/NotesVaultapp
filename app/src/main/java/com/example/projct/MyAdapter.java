package com.example.projct;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Parcelable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context content;
    ArrayList<firebasemodel> userArrayList;

    FirebaseFirestore firebaseFirestore;
    FirebaseUser firebaseUser;


    public MyAdapter(Context content,ArrayList<firebasemodel>userArrayList){
        this.content=content;
        this.userArrayList=userArrayList;

    }




    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(content).inflate(R.layout.card,parent,false);
        return new MyViewHolder(v);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ImageView popupbutton=holder.itemView.findViewById(R.id.menupopup);
        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();

        firebasemodel user=userArrayList.get(position);


        holder.title.setText(user.title);
        holder.content.setText(user.content);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(view.getContext(),notedetails.class);
                intent.putExtra("title",user.getTitle());
                intent.putExtra("content",user.getContent());
                view.getContext().startActivity(intent);

            }
        });


        popupbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu=new PopupMenu(view.getContext(),view);
                popupMenu.setGravity(Gravity.END);
                popupMenu.getMenu().add("Edit").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        Intent intent=new Intent(view.getContext(),editnoteactivity.class);
                        intent.putExtra("title",user.getTitle());
                        intent.putExtra("content",user.getContent());

                        view.getContext().startActivity(intent);
                        return false;
                    }
                });
                popupMenu.getMenu().add("Delete").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        DocumentReference documentReference=firebaseFirestore.collection("Notes").document(firebaseUser.getUid()).collection("my_notes").document();
                        documentReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(view.getContext(),"Deleted",Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(view.getContext(),"note Deleted",Toast.LENGTH_SHORT).show();

                            }
                        });

                        return false;
                    }
                });

                popupMenu.show();
            }
        });

    }

    @Override
    public int getItemCount(){
        return userArrayList.size();

    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView title,content;


        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            title=itemView.findViewById(R.id.notetitle);
            content=itemView.findViewById(R.id.notecontent);



        }

    }



}

