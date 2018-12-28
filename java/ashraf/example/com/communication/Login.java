package ashraf.example.com.communication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

public
class Login extends AppCompatActivity {
    Button btn_log;
    EditText txtemail,txtpassword;
    private FirebaseAuth loginauth;
    private DatabaseReference mUserDatabase;

    @Override
    protected
    void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login );
        txtemail=(EditText) findViewById( R.id. login_email);
        txtpassword=(EditText) findViewById( R.id. login_password);
        btn_log=(Button)findViewById( R.id.login_btn );
        loginauth = FirebaseAuth.getInstance();
        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users");

          btn_log.setOnClickListener( new View.OnClickListener() {
              @Override
              public
              void onClick(View v) {
                  login();
              }
          } );
    }


    private void login()
    {

        loginauth.signInWithEmailAndPassword( txtemail.getText().toString(),txtpassword.getText().toString() )
                .addOnCompleteListener( this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public
                    void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {

                            String current_user_id = loginauth.getCurrentUser().getUid();
                            String deviceToken = FirebaseInstanceId.getInstance().getToken();
                            mUserDatabase.child(current_user_id).child("device_token").setValue(deviceToken).addOnSuccessListener( new OnSuccessListener<Void>() {
                                                                                                                                       @Override
                                                                                                                                       public
                                                                                                                                       void onSuccess(Void aVoid) {
                                                                                                                                           Intent in = new Intent( Login.this, MainActivity.class );
                                                                                                                                           startActivity( in );
                                                                                                                                           finish();

                                                                                                                                       }
                                                                                                                                   });

                        }
                        else {

                            Toast.makeText( Login.this, "please check to your email or password", Toast.LENGTH_SHORT ).show();
                        }

                    }
                } );

    }


}
