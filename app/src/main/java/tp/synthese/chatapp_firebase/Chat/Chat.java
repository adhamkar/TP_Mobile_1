package tp.synthese.chatapp_firebase.Chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import tp.synthese.chatapp_firebase.MemoryData;
import tp.synthese.chatapp_firebase.R;

public class Chat extends AppCompatActivity {
    DatabaseReference databaseReference= FirebaseDatabase
            .getInstance()
            .getReferenceFromUrl("https://chatapp-tp-synthese-default-rtdb.firebaseio.com/");
    private String chatkey;
    private final List<ChatList> chatLists=new ArrayList<>();
    String getuserMobile="";
    private RecyclerView chattingRecycleView;
    private ChatAdapter chatAdapter;
    private boolean loadingFirstTime=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        final ImageView backbtn=findViewById(R.id.backBtn);
        final EditText MsgEditText=findViewById(R.id.MsgEditText);
        final TextView name=findViewById(R.id.name);
        //final CircleImageView profilpic=findViewById(R.id.profilPic);
        final ImageView sendBtn=findViewById(R.id.sendBtn);
        chattingRecycleView=findViewById(R.id.chttingRecycleView);
        //get data from
        final String getName=getIntent().getStringExtra("name");
        chatkey=getIntent().getStringExtra("chat_key");

        final String getMobile=getIntent().getStringExtra("mobile");

        getuserMobile=MemoryData.getData(Chat.this);

        name.setText(getName);
        chattingRecycleView.setHasFixedSize(true);
        chattingRecycleView.setLayoutManager(new LinearLayoutManager(Chat.this));

        chatAdapter=new ChatAdapter(chatLists,Chat.this);
        chattingRecycleView.setAdapter(chatAdapter);

            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(chatkey.isEmpty()) {
                        //generate chat key. by default is 1
                        chatkey = "1";

                        if (snapshot.hasChild("chat")) {
                            chatkey = String.valueOf(snapshot.child("chat").getChildrenCount() + 1);
                        }
                    }
                    if(snapshot.hasChild("chat")){
                        if(snapshot.child("chat").child(chatkey).hasChild("message")){
                            chatLists.clear();
                            for(DataSnapshot messagesSnapshot: snapshot.child("chat").child(chatkey).child("message").getChildren()){

                                if(messagesSnapshot.hasChild("msg") && messagesSnapshot.hasChild("mobile")){

                                    final String messageTimeTamps=messagesSnapshot.getKey();
                                    final String getMobile=messagesSnapshot.child("mobile").getValue(String.class);
                                    final String getMsg=messagesSnapshot.child("msg").getValue(String.class);

                                    Timestamp timestamp=new Timestamp(Long.parseLong(messageTimeTamps));
                                    Date date=new Date(timestamp.getTime());
                                    SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                                    SimpleDateFormat simpleTimeFormat=new SimpleDateFormat("hh:mm aa", Locale.getDefault());
                                    ChatList chatList=new ChatList(getMobile,getName,getMsg,simpleDateFormat.format(date),simpleTimeFormat.format(date));
                                    chatLists.add(chatList);

                                    if(loadingFirstTime || Long.parseLong(messageTimeTamps)>Long.parseLong(MemoryData.getLastMsg(Chat.this,chatkey))){
                                        loadingFirstTime=false;

                                        MemoryData.saveLastMsg(messageTimeTamps,chatkey,Chat.this);
                                        chatAdapter.updateChatList(chatLists);

                                        chattingRecycleView.scrollToPosition(chatLists.size()-1);
                                    }

                                }
                            }
                        }
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String getTxtMsg=MsgEditText.getText().toString();
                final String currentTimesTamps=String.valueOf(System.currentTimeMillis()).substring(0,10);
                getuserMobile = MemoryData.getData(Chat.this);


                //MemoryData.saveLastMsg(currentTimesTamps,chatkey,Chat.this);

                databaseReference.child("chat").child(chatkey).child("user_1").setValue(getuserMobile);
                databaseReference.child("chat").child(chatkey).child("user_2").setValue(getMobile);
                databaseReference.child("chat").child(chatkey).child("message").child(currentTimesTamps).child("msg").setValue(getTxtMsg);
                databaseReference.child("chat").child(chatkey).child("message").child(currentTimesTamps).child("mobile").setValue(getuserMobile);
                MsgEditText.setText("");
            }
        });


        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

}
