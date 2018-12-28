package ashraf.example.com.communication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Posts extends AppCompatActivity {
    private Button b_add;
    private RecyclerView rv_posts;
    private FirebaseRecyclerAdapter<DashboarViewModel,DashboardViewHolder> FRA;
    private DatabaseReference posts_db,user_db;
    private LinearLayoutManager layoutManager;
    private String currentUserJob,currentUsreID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.posts);
        b_add = findViewById(R.id.b_add);
        rv_posts = findViewById(R.id.rv_posts);
        posts_db = FirebaseDatabase.getInstance().getReference().child("dashboard");
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);
        rv_posts.setLayoutManager(layoutManager);
        b_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Posts.this,PostAdd.class));
            }
        });
        currentUsreID= FirebaseAuth.getInstance().getCurrentUser().getUid();
        user_db=FirebaseDatabase.getInstance().getReference().child( "Users" ).child( currentUsreID );
        currentUserJob=getIntent().getStringExtra("userJob");
    }

    @Override
    protected void onStart() {
        super.onStart();

        FRA = new FirebaseRecyclerAdapter<DashboarViewModel, DashboardViewHolder>(
                DashboarViewModel.class,
                R.layout.post_layout,
                DashboardViewHolder.class,
                posts_db
        ) {
            @Override
            protected void populateViewHolder(DashboardViewHolder viewHolder, DashboarViewModel model, int position) {
                String job=model.getJob();
                if ( job!=null ) {
                    Log.e("gg3", job+" "+currentUserJob );
                    if ( job.equals( currentUserJob ) ) {
                        viewHolder.showUser();
                        viewHolder.setImage( model.getImage() );
                        viewHolder.setName( model.getUri() );
                        viewHolder.setProfileImage( model.getUri() );
                        viewHolder.setTime( model.getTime() );
                        viewHolder.setTitle( model.getTitle() );
                        viewHolder.setButtons( model.getPost_id() );
                    } else {
                        viewHolder.hideUnWantedUser();
                    }
                }
                /*
                viewHolder.setImage( model.getImage() );
                viewHolder.setName( model.getUri() );
                viewHolder.setProfileImage( model.getUri() );
                viewHolder.setTime( model.getTime() );
                viewHolder.setTitle( model.getTitle() );
                viewHolder.setButtons( model.getPost_id() );*/
            }

        };
        rv_posts.setAdapter(FRA);
    }
}
