package ashraf.example.com.communication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

import java.util.HashMap;
import java.util.Map;

public class Comments extends AppCompatActivity {

    private RecyclerView rv_comments;
    private ImageView ib_send;
    private EditText et_comment;
    private LinearLayoutManager layoutManager;
    private DatabaseReference comments_db;
    private FirebaseRecyclerAdapter<CommentsViewModel,CommentsViewHolder> FRA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        rv_comments = findViewById(R.id.rv_comments);
        ib_send = findViewById(R.id.ib_send);
        et_comment = findViewById(R.id.et_comment);
        layoutManager = new LinearLayoutManager(this);
        rv_comments.setLayoutManager(layoutManager);
        comments_db = FirebaseDatabase.getInstance().getReference()
                .child("dashboard").child(getIntent().getStringExtra("post_id")).child("comments");
        ib_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map comment = new HashMap();
                if (!et_comment.getText().toString().trim().isEmpty()) {
                    comment.put("uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
                    comment.put("time",(ServerValue.TIMESTAMP));
                    comment.put("title",et_comment.getText().toString());
                    comments_db.push().setValue(comment);
                    et_comment.setText("");
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FRA = new FirebaseRecyclerAdapter<CommentsViewModel, CommentsViewHolder>(
                CommentsViewModel.class,
                R.layout.comment_layout,
                CommentsViewHolder.class,
                comments_db
        ) {
            @Override
            protected void populateViewHolder(CommentsViewHolder viewHolder, CommentsViewModel model, int position) {
                viewHolder.setName(model.getUid());
                viewHolder.setProfileImage(model.getUid());
                viewHolder.setTime(model.getTime());
                viewHolder.setTitle(model.getTitle());
                viewHolder.setClicks(model.getUid());
            }
        };
        rv_comments.setAdapter(FRA);
    }
}
