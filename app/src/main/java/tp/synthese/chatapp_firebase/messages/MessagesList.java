package tp.synthese.chatapp_firebase.messages;

public class MessagesList {
    private String name,mobile,lastMessage,profilepic,chatkey;
    private int unseenMsg;

    public String getProfilepic() {
        return profilepic;
    }

    public void setProfilepic(String profilepic) {
        this.profilepic = profilepic;
    }

    public MessagesList(String name, String mobile, String lastMessage, String profilepic, int unseenMsg) {
        this.name = name;
        this.mobile = mobile;
        this.lastMessage = lastMessage;
        this.unseenMsg = unseenMsg;
        this.profilepic = profilepic;
    }

    public String getChatkey() {
        return chatkey;
    }

    public void setChatkey(String chatkey) {
        this.chatkey = chatkey;
    }

    public MessagesList(String name, String mobile, String lastMessage, int unseenMsg, String chatkey) {
        this.name = name;
        this.mobile = mobile;
        this.lastMessage = lastMessage;
        this.unseenMsg = unseenMsg;
        this.chatkey = chatkey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public int getUnseenMsg() {
        return unseenMsg;
    }

    public void setUnseenMsg(int unseenMsg) {
        this.unseenMsg = unseenMsg;
    }
}
