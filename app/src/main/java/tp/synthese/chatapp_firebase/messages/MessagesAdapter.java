package tp.synthese.chatapp_firebase.messages;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import tp.synthese.chatapp_firebase.Chat.Chat;
import tp.synthese.chatapp_firebase.R;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MyViewHolder> {
    private  List<MessagesList> messagesLists;
    private final Context context;

    public MessagesAdapter(List<MessagesList> messagesListList, Context context) {
        this.messagesLists = messagesListList;
        this.context = context;
    }

    @NonNull
    @Override
    public MessagesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.messages_adapter_layout,null));
    }

    @Override
    public void onBindViewHolder(@NonNull MessagesAdapter.MyViewHolder holder, int position) {
        MessagesList list2=messagesLists.get(position);
        holder.name.setText(list2.getName());
        holder.lastMsg.setText(list2.getLastMessage());


        if(list2.getUnseenMsg()==0){
           holder.unseenMsgs.setVisibility(View.GONE);
           holder.lastMsg.setTextColor(Color.parseColor("#959595"));
        }else{
            holder.unseenMsgs.setVisibility(View.VISIBLE);
            holder.unseenMsgs.setText(list2.getUnseenMsg()+"");
            holder.lastMsg.setTextColor(context.getResources().getColor(R.color.theme_color_80));
        }
        holder.rootLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, Chat.class);
                intent.putExtra("name",list2.getName());
                intent.putExtra("phone",list2.getMobile());
                //intent.putExtra("profile_pic",list2.getProfilepic());
                intent.putExtra("chat_key",list2.getChatkey());
                context.startActivity(intent);
            }
        });

    }

    public void updateData(List<MessagesList> messagesLists){
        this.messagesLists=messagesLists;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return messagesLists.size();
    }
    static class MyViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView profilpic;
        private TextView name;
        private TextView lastMsg;
        private TextView unseenMsgs;
        private LinearLayout rootLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            profilpic=itemView.findViewById(R.id.profilPic);
            name=itemView.findViewById(R.id.name);
            lastMsg=itemView.findViewById(R.id.lastMsg);
            unseenMsgs=itemView.findViewById(R.id.unseenMsg);
            rootLayout=itemView.findViewById(R.id.rootLayout);
        }
    }
}
