import Bean.Message;
import com.google.gson.Gson;
import com.imudges.j2se.network.Client;

public class Main {
    public static void main(String args[]) {

        Message message = new Message();
        message.setMsg("Hello");
        message.setClientID("001");

        Gson gson = new Gson();
        String msg = gson.toJson(message);
        Client c = new Client();
        c.setMsg(msg);
        c.connect();

    }
}
