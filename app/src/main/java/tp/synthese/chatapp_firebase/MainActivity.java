package tp.synthese.chatapp_firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import tp.synthese.chatapp_firebase.messages.MessagesAdapter;
import tp.synthese.chatapp_firebase.messages.MessagesList;

public class MainActivity extends AppCompatActivity {
    private final List<MessagesList> Listmessages=new ArrayList<>();
    private String name;
    private String email;
    private Boolean dataset=false;
    private String phone;
    private String lastMsg="";
    private int unseenMsgs=0;
    private MessagesAdapter messagesAdapter;
    private String chatkey="";
    DatabaseReference databaseReference= FirebaseDatabase
            .getInstance()
            .getReferenceFromUrl("https://chatapp-tp-synthese-default-rtdb.firebaseio.com/");

    private RecyclerView messagesRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final CircleImageView userprofilePic=findViewById(R.id.userprofilePic);

        messagesRecyclerView=findViewById(R.id.messageRecyclerView);

        phone=getIntent().getStringExtra("Phone");
        name=getIntent().getStringExtra("Name");
        email=getIntent().getStringExtra("Email");

        messagesRecyclerView.setHasFixedSize(true);
        messagesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //set adapter to recycleview

        messagesAdapter=new MessagesAdapter(Listmessages,MainActivity.this);

        messagesRecyclerView.setAdapter(messagesAdapter);



        ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("loading ...");
        progressDialog.show();

    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
               /* final String profilePicUrl=snapshot.child("users").child(phone).child("profile_pic").getValue(String.class);

                if(profilePicUrl != null && !profilePicUrl.isEmpty()){
                    Picasso.get().load(profilePicUrl).into(userprofilePic);
                }*/
                Picasso.get().load(String.valueOf(userprofilePic));
                progressDialog.dismiss();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.dismiss();
                Log.e("MainActivity", "Database read cancelled: " + error.getMessage());

            }
        });


        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Listmessages.clear();
                unseenMsgs=0;
                lastMsg="";
                chatkey="";
                for(DataSnapshot dataSnapshot : snapshot.child("users").getChildren()){
                    final String getPhone=dataSnapshot.getKey();
                    dataset=false;
                    if(!getPhone.equals(phone)){
                        final String getName=dataSnapshot.child("name").getValue(String.class);

                        //final String getProfilePic=dataSnapshot.child("profile_pic").getValue(String.class);
                        databaseReference.child("chat").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                int getChatAcc=(int)snapshot.getChildrenCount();
                                if(getChatAcc>0){
                                    for(DataSnapshot dataSnapshot1:snapshot.getChildren()){
                                        final String getkey=dataSnapshot1.getKey();
                                        chatkey=getkey;
                                        if(dataSnapshot1.hasChild("user_1") && dataSnapshot1.hasChild("user_2") && dataSnapshot1.hasChild("messages")){
                                            final String getUserOne=dataSnapshot1.child("user_1").getValue(String.class);
                                            final String getUserTwo=dataSnapshot1.child("user_2").getValue(String.class);

                                            if((getUserOne.equals(getPhone) && getUserTwo.equals(phone)) ||(getUserOne.equals(phone) && getUserTwo.equals(getPhone)) ){
                                                for(DataSnapshot chatdataSnapshot:dataSnapshot1.child("messages").getChildren()){
                                                    final long getMsgKey=Long.parseLong(chatdataSnapshot.getKey());
                                                    final long getlastMsg=Long.parseLong(MemoryData.getLastMsg(MainActivity.this,getkey));

                                                    lastMsg=chatdataSnapshot.child("msg").getValue(String.class);
                                                    if(getMsgKey>getlastMsg){
                                                        unseenMsgs++;
                                                    }
                                                }
                                            }
                                        }

                                    }
                                }
                                if(!dataset){
                                    dataset=true;
                                    MessagesList messagesList=new MessagesList(getName,getPhone,lastMsg,unseenMsgs,chatkey);
                                    Listmessages.add(messagesList);
                                    //messagesRecyclerView.setAdapter(new MessagesAdapter(Listmessages,MainActivity.this));
                                    messagesAdapter.updateData(Listmessages);
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });


                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}