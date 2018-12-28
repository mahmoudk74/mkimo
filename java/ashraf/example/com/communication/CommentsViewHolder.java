package ashraf.example.com.communication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.alexzh.circleimageview.CircleImageView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created by Mostafa on 4/14/2018.
 */

public class CommentsViewHolder extends RecyclerView.ViewHolder{
    private View view;
    private Context context;
    private CircleImageView civ_image_profile;
    private TextView tv_title_comment;
    private TextView tv_name_profile;
    private TextView tv_time_comment;
    private DatabaseReference profile_db;
    public CommentsViewHolder(View itemView) {
        super(itemView);
        view = itemView;
        context = view.getContext();
        civ_image_profile = view.findViewById(R.id.civ_image_profile);
        tv_name_profile = view.findViewById(R.id.tv_name_profile);
        tv_title_comment = view.findViewById(R.id.tv_title_comment);
        tv_time_comment = view.findViewById(R.id.tv_time_comment);
        profile_db = FirebaseDatabase.getInstance().getReference().child("Users");
    }
    public void setName(String uid){
        profile_db.child(uid).child("name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tv_name_profile.setText(dataSnapshot.getValue().toString());
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }
    public void setProfileImage(String uid){
        profile_db.child(uid).child("image").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Picasso.with(context).load(dataSnapshot.getValue().toString()).into(civ_image_profile);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public void setTime(long time){
        try {
            tv_time_comment.setText(String.valueOf(DateFormat.getDateTimeInstance().format(new Date(time))));
        }catch (Exception e){}
    }
    public void setTitle(String title){
        tv_title_comment.setText(title);
    }
    public void setClicks(String uid){
        tv_name_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context,ProfileActivity.class);
                i.putExtra("user_id",uid);
                context.startActivity(i);
            }
        });
    }
}
