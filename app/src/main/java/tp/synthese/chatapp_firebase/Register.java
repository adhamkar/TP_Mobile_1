package tp.synthese.chatapp_firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Register extends AppCompatActivity {
DatabaseReference databaseReference= FirebaseDatabase.getInstance()
        .getReferenceFromUrl("https://chatapp-tp-synthese-default-rtdb.firebaseio.com/");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        final EditText name=findViewById(R.id.r_name);
        final EditText email=findViewById(R.id.r_email);
        final EditText phone=findViewById(R.id.r_phone);
        final AppCompatButton registerBtn=findViewById(R.id.registerBtn);

        ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("loading ...");

        if(!MemoryData.getData(this).isEmpty()){

            Intent intent=new Intent(Register.this,MainActivity.class);
            intent.putExtra("Phone",MemoryData.getData(this));
            intent.putExtra("Name",MemoryData.getName(this));
            intent.putExtra("Email",MemoryData.getEmail(this));
            startActivity(intent);
            finish();
        }
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();

                final String nameTxt=name.getText().toString();
                final String emailTxt=email.getText().toString();
                final String phonetxt=phone.getText().toString();

                if(nameTxt.isEmpty() || emailTxt.isEmpty() || phonetxt.isEmpty()){
                    Toast.makeText(Register.this,"All Fields Required !!",Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }else{
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            progressDialog.dismiss();
                            if(snapshot.child("users").hasChild(phonetxt)){
                                Toast.makeText(Register.this,"Phone number already exist !!",Toast.LENGTH_SHORT).show();
                            }else{
                                databaseReference.child("users").child(phonetxt).child("email").setValue(emailTxt);
                                databaseReference.child("users").child(phonetxt).child("name").setValue(nameTxt);

                                //save phone to memory
                                MemoryData.saveData(phonetxt,Register.this);
                                //save name to memory
                                MemoryData.saveName(nameTxt,Register.this);
                                //save email to memory
                                MemoryData.saveEmail(emailTxt,Register.this);

                                Toast.makeText(Register.this,"Success",Toast.LENGTH_SHORT).show();

                                Intent intent=new Intent(Register.this,MainActivity.class);
                                intent.putExtra("Name",nameTxt);
                                intent.putExtra("Email",emailTxt);
                                intent.putExtra("Phone",phonetxt);
                                startActivity(intent);
                                finish();
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            progressDialog.dismiss();
                        }
                    });
                }
            }
        });
    }
}