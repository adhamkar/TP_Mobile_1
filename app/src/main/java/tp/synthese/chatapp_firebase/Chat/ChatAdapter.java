package tp.synthese.chatapp_firebase.Chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

import tp.synthese.chatapp_firebase.MemoryData;
import tp.synthese.chatapp_firebase.R;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {

    private  List<ChatList> chatLists;
    private final Context context;
    private String userMobile;

    public ChatAdapter(List<ChatList> chatLists, Context context) {
        this.chatLists = chatLists;
        this.context = context;
        this.userMobile= MemoryData.getData(context);
    }
    @NonNull
    @Override
    public ChatAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_adapter_layout,null));
    }

    @Override
    public void onBindViewHolder(@NonNull ChatAdapter.MyViewHolder holder, int position) {
        ChatList list=chatLists.get(position);
        if(list.getMobile().equals(userMobile)){

            holder.mylayout.setVisibility(View.VISIBLE);
            holder.oppoLayout.setVisibility(View.GONE);

            holder.myMsg.setText(list.getMessage());
            holder.myTime.setText(list.getDate()+" "+list.getTime());

        }else {

            holder.mylayout.setVisibility(View.GONE);
            holder.oppoLayout.setVisibility(View.VISIBLE);

            holder.oppoMsg.setText(list.getMessage());
            holder.oppoTime.setText(list.getDate()+" "+list.getTime());
        }
    }

    @Override
    public int getItemCount() {
        return chatLists.size();
    }
    public void updateChatList(List<ChatList> chatLists){
        this.chatLists=chatLists;
    }
    static class MyViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout oppoLayout,mylayout;
        private TextView oppoMsg,myMsg;
        private TextView oppoTime,myTime;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            oppoLayout=itemView.findViewById(R.id.oppoLayout);
            mylayout=itemView.findViewById(R.id.myLayout);
            oppoMsg=itemView.findViewById(R.id.oppoMessage);
            myMsg=itemView.findViewById(R.id.myMessage);
            oppoTime=itemView.findViewById(R.id.oppoMsgTime);
            myTime=itemView.findViewById(R.id.myMsgTime);
        }
    }
}
