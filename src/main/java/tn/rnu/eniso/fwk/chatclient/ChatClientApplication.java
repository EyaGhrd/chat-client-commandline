package tn.rnu.eniso.fwk.chatclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tn.rnu.eniso.fwk.chatclient.service.ChatClientService;

@SpringBootApplication
public class ChatClientApplication implements CommandLineRunner {

    private final ChatClientService chatClientService;

    @Autowired
    public ChatClientApplication(ChatClientService chatClientService) {
        this.chatClientService = chatClientService;
    }

    public static void main(String[] args) {
        SpringApplication.run(ChatClientApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        if (args.length == 0) {
            System.out.println("Usage:");
            System.out.println("java -jar chat-client.jar send --from <sender> --to <receiver> --message <message>");
            System.out.println("java -jar chat-client.jar receive --to <receiver>");
            return;
        }

        String command = args[0];

        switch (command) {
            case "send":
                String sender = null;
                String receiver = null;
                String message = null;

                for (int i = 1; i < args.length; i++) {
                    if ("--from".equals(args[i])) sender = args[++i];
                    else if ("--to".equals(args[i])) receiver = args[++i];
                    else if ("--message".equals(args[i])) message = args[++i];
                }

                if (sender != null && receiver != null && message != null) {
                    chatClientService.sendMessage(sender, receiver, message);
                } else {
                    System.out.println("Arguments invalides pour la commande send.");
                }
                break;

            case "receive":
                receiver = null;
                for (int i = 1; i < args.length; i++) {
                    if ("--to".equals(args[i])) receiver = args[++i];
                }

                if (receiver != null) {
                    chatClientService.receiveMessages(receiver);
                } else {
                    System.out.println("Arguments invalides pour la commande receive.");
                }
                break;

            default:
                System.out.println("Commande inconnue: " + command);
                break;
        }
    }
}
