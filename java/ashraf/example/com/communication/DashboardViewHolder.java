package ashraf.example.com.communication;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alexzh.circleimageview.CircleImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.util.Date;

public class DashboardViewHolder extends RecyclerView.ViewHolder{

    private View view;
    private Context context;
    private TextView tv_title;
    private TextView tv_name;
    private TextView tv_time;
    private CircleImageView civ_image;
    private ImageView iv_post_view;
    private DatabaseReference profile_db;
    private DatabaseReference post_db;
    private Button b_like;
    private Button b_comment;
    private boolean like_state = false;
    private ConstraintLayout constraintLayout;
    public DashboardViewHolder(View itemView) {
        super(itemView);
        view = itemView;
        context = view.getContext();
        tv_title = view.findViewById(R.id.tv_title);
        tv_name = view.findViewById(R.id.tv_name);
        tv_time = view.findViewById(R.id.tv_time);
        civ_image = view.findViewById(R.id.civ_image);
        iv_post_view = view.findViewById(R.id.iv_post_view);
        b_like = view.findViewById(R.id.b_like);
        b_comment = view.findViewById(R.id.b_comment);
        constraintLayout = view.findViewById(R.id.post_view);
        profile_db = FirebaseDatabase.getInstance().getReference()
                .child("Users");
        post_db = FirebaseDatabase.getInstance().getReference().child("dashboard");
    }
    public void setTitle(String url){
        tv_title.setText(url);
    }
    public void setImage(String url){
        Picasso.with(context).load(url).into(iv_post_view);
    }
    public void setProfileImage(String uid){
        profile_db.child(uid).child("image").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Picasso.with(context).load(dataSnapshot.getValue().toString()).into(civ_image);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }
    public void setTime(long time){
        try {
            tv_time.setText(String.valueOf(DateFormat.getDateTimeInstance().format(new Date(time))));
        }catch (Exception e){}
    }
    public void setName(String uid){
        profile_db.child(uid).child("name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tv_name.setText(dataSnapshot.getValue().toString());
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }
    public void setButtons(final String post_id){
        final DatabaseReference like_db = post_db.child(post_id).child("likes")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("like");
        like_db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue()!=null) {
                    if ((boolean)dataSnapshot.getValue()) {
                        b_like.setText("liked");
                        like_state = true;
                    } else {
                        b_like.setText("like");
                        like_state = false;
                    }
                }else {
                    b_like.setText("like");
                    like_state = false;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
        b_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (like_state)
                    like_db.setValue(false);
                else
                    like_db.setValue(true);
            }
        });
        b_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context,Comments.class);
                i.putExtra("post_id",post_id);
                context.startActivity(i);
            }
        });
    }
    public void hideUnWantedUser(){
        constraintLayout.setVisibility( View.GONE );
        tv_title.setVisibility( View.GONE );
        tv_name.setVisibility( View.GONE );
        tv_time.setVisibility( View.GONE );
        civ_image.setVisibility( View.GONE );
        iv_post_view.setVisibility( View.GONE );
        b_like.setVisibility( View.GONE );
        b_comment.setVisibility( View.GONE );
    }
    public void showUser(){
        constraintLayout.setVisibility( View.VISIBLE );
        tv_title.setVisibility( View.VISIBLE );
        tv_name.setVisibility( View.VISIBLE );
        tv_time.setVisibility( View.VISIBLE );
        civ_image.setVisibility( View.VISIBLE );
        iv_post_view.setVisibility( View.VISIBLE );
        b_like.setVisibility( View.VISIBLE );
        constraintLayout.setVisibility( View.VISIBLE );
    }
}