package ashraf.example.com.communication;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public
class StatusAcitvity extends AppCompatActivity {
    private android.support.v7.widget.Toolbar mToolbar;
    private
    TextInputLayout mStatus;
    private
    Button msavebtn;

    // firebase
    private
    DatabaseReference mstatusDatabase;
    private
    FirebaseUser mcurrent;
    // progress
    private
    ProgressDialog mprogess;


    @Override
    protected
    void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_status_acitvity );
        // firebase
        mcurrent= FirebaseAuth.getInstance().getCurrentUser();
        String curent_uid=mcurrent.getUid();
        mstatusDatabase= FirebaseDatabase.getInstance().getReference().child( "Users" ).child( curent_uid );


        mToolbar = (Toolbar) findViewById(R.id.status_appBar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Account Status");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //progress
        mprogess=new ProgressDialog( this );


        mStatus= findViewById( R.id.status_input );
        msavebtn= findViewById( R.id.status_save_btn );

        msavebtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public
            void onClick(View v) {
                // progress
                mprogess=new ProgressDialog( StatusAcitvity.this );
                mprogess.setTitle("Saving Change");
                mprogess.setMessage( "Please wait while we save the changes" );
                mprogess.show();
                String status=mStatus.getEditText().getText().toString();
                mstatusDatabase.child( "Status" ).setValue( status ).addOnCompleteListener( new OnCompleteListener<Void>() {
                    @Override
                    public
                    void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){


                            mprogess.dismiss();

                        }else {

                            Toast.makeText( getApplicationContext(), "There was some error in saving changing", Toast.LENGTH_SHORT ).show();
                        }

                    }
                } );


            }
        } );



    }
}
