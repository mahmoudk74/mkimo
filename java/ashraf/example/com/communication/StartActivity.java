package ashraf.example.com.communication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public
class StartActivity extends AppCompatActivity {
Button mRegBtn,mLoginBtn;
    @Override
    protected
    void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_start );
        mRegBtn=(Button) findViewById( R.id.start_reg_btn );
        mLoginBtn = (Button) findViewById(R.id.log_bn );


        mRegBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public
            void onClick(View v) {
                Intent in=new Intent( StartActivity.this,Register.class );
                startActivity( in );
            }
        } );

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View view) {

                                             Intent login_intent = new Intent(StartActivity.this, Login.class);
                                             startActivity(login_intent);

                                         }
          } );



}

}
