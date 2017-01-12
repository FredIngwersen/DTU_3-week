package MeyerGame;

import java.util.LinkedList;
/**
 * Created by William Ben Embarek on 04/01/2017.
 */
public class messages {
    private LinkedList<String> MessageList;
    private int MsgBuffer;
    private StringBuilder sb;
    public messages() {
        MsgBuffer = 15;
        MessageList = new LinkedList<String>();
    }
    public messages(int size)
    {
        MsgBuffer = size;
        MessageList = new LinkedList<String>();
    }
    public String getLatestMessage()
    {
        return MessageList.getFirst();
    }
    public String getAllMessages()
	{
		sb = new StringBuilder();
		for (int i = 0; i < MessageList.size(); i++)
		{
			sb.append(MessageList.get(i) + "\n");
		}
		return sb.toString();
	}
    public void newMessage(String msg)
	{
		if (MessageList.size() == MsgBuffer)
		{
			MessageList.removeLast();
			MessageList.addFirst(msg);
		}
		else
		{
			MessageList.addFirst(msg);
		}
	}

}