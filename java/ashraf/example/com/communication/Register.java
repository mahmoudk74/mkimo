package ashraf.example.com.communication;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Register extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private
    DatabaseReference nDatabase;
    EditText mDisplayname, mEmail, mPassword;
    Button mCrateButton;
    private FirebaseAuth mAuth;
// progress dialoug to show loading
    private ProgressDialog mregprogress;
    private Toolbar mToolbar;
    private Spinner spinner;
    private List<String> jobList;
    private String job;
    @Override
    protected
    void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_register );
        //Toolbar Set
        mToolbar = (Toolbar) findViewById(R.id.register_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Create Account");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mAuth = FirebaseAuth.getInstance();
        mDisplayname = findViewById( R.id.Reg_display_name );
        mEmail = findViewById( R.id.reg_display_email );
        mPassword = findViewById( R.id.reg_display_password );
        mCrateButton = findViewById( R.id.creat_account );
        mCrateButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public
            void onClick(View v) {
                reg();
            }


        } );
        job="Engineer";
        spinner=(Spinner)findViewById(R.id.reg_job);
        jobList=new ArrayList<>(  );
        jobList.add( "Engineer" );
        jobList.add( "Doctor" );
        jobList.add( "Software developer" );
        jobList.add( "Lawyer" );
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, jobList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(this);
        mregprogress=new ProgressDialog( this );
    }
    private
    void reg() {
        mAuth.createUserWithEmailAndPassword( mEmail.getText().toString(), mPassword.getText().toString() )
                .addOnCompleteListener( this, new OnCompleteListener<AuthResult>() {
                    @SuppressLint("ShowToast")
                    @Override
                    public
                    void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser Current_user = FirebaseAuth.getInstance().getCurrentUser();
                            String uid=Current_user.getUid();
                            nDatabase=FirebaseDatabase.getInstance().getReference().child( "Users" ).child( uid );
                            String device_token= FirebaseInstanceId.getInstance().getToken();
                            HashMap<String, String> userMap=new HashMap<>(  );
                            userMap.put( "name", mDisplayname.getText().toString());
                            userMap.put( "Status","hi iam use soicoskills");
                            userMap.put( "image","default");
                            userMap.put( "thumb_image","default");
                            userMap.put("device_token", device_token);
                            userMap.put( "job",job );
                            Log.e("gg3", job);
                            nDatabase.setValue( userMap ).addOnCompleteListener( new OnCompleteListener<Void>() {
                                @Override
                                public
                                void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Intent in=new Intent( Register.this,MainActivity.class );
                                        in.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK );
                                        startActivity( in );
                                    }
                                }
                            } );

                            /*
                            Toast.makeText( getApplication(), "Register Complete", Toast.LENGTH_LONG );

                        */
                        }else
                            Toast.makeText( getApplication(), " Error" + task.getException(), Toast.LENGTH_SHORT );

                        Intent in=new Intent( Register.this,StartActivity.class );
                        startActivity( in );



                    }



                } );
    }

    @Override
    public
    void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
         job = parent.getItemAtPosition(position).toString();
         Log.e("gg3", job );
    }

    @Override
    public
    void onNothingSelected(AdapterView<?> parent) {

    }
}

