package ashraf.example.com.communication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class PostAdd extends AppCompatActivity {
    private ImageButton iv_post;
    private Button b_submit;
    private EditText et_title;
    private DatabaseReference dashboard_db;
    private Uri imageUri = null;
    private ProgressDialog progressDialog;
    private StorageReference storageReference;
    DatabaseReference user_db;
    private String currentUserJob,currentUsreID;
    private Map postData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_add);
        iv_post = findViewById(R.id.iv_post);
        b_submit = findViewById(R.id.b_submit);
        et_title = findViewById(R.id.et_title);
        dashboard_db = FirebaseDatabase.getInstance().getReference().child("dashboard").push();
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Uploading post");
        iv_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gallery = new Intent(Intent.ACTION_GET_CONTENT);
                gallery.setType("image/*");
                startActivityForResult(gallery,1);
            }
        });
        b_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Post();
            }
        });
        currentUsreID= FirebaseAuth.getInstance().getCurrentUser().getUid();
        user_db=FirebaseDatabase.getInstance().getReference().child( "Users" ).child( currentUsreID );
        user_db.addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public
            void onDataChange(DataSnapshot dataSnapshot) {
                currentUserJob=dataSnapshot.child( "job" ).getValue().toString();
            }
            @Override
            public
            void onCancelled(DatabaseError databaseError) {

            }
        } );

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1&&resultCode==RESULT_OK){
            imageUri = data.getData();
            iv_post.setImageURI(imageUri);
        }
    }
    private void Post(){
        postData = new HashMap();
        String title = et_title.getText().toString();
        if(!title.trim().isEmpty())
            postData.put("title",title);
        if(imageUri != null){
            progressDialog.show();
            storageReference = FirebaseStorage.getInstance().getReference().child("posts_images").child(ServerValue.TIMESTAMP.toString());
            storageReference.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if(task.isSuccessful()){
                        postData.put("image",task.getResult().getDownloadUrl().toString());
                        postData.put("uri", FirebaseAuth.getInstance().getCurrentUser().getUid());
                        postData.put("time", ServerValue.TIMESTAMP);
                        postData.put("post_id",dashboard_db.getKey());
                        postData.put("job",currentUserJob);
                        dashboard_db.setValue(postData).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                progressDialog.dismiss();
                                PostAdd.this.onBackPressed();
                            }
                        });
                    }
                }
            });
        }
        if(!postData.isEmpty()){
            postData.put("uri", FirebaseAuth.getInstance().getCurrentUser().getUid());
            postData.put("time", ServerValue.TIMESTAMP);
            postData.put("post_id",dashboard_db.getKey());
            postData.put("job",currentUserJob);
            dashboard_db.setValue(postData).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    progressDialog.dismiss();
                    PostAdd.this.onBackPressed();
                }
            });
        }
    }
}