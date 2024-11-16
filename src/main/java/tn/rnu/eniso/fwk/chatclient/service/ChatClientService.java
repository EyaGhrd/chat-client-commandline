package tn.rnu.eniso.fwk.chatclient.service;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.springframework.stereotype.Service;

@Service
public class ChatClientService {

    private static final String CHAT_SERVER_BASE_URL = "http://localhost:8080/chat";

    public void sendMessage(String from, String to, String message) {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            String url = CHAT_SERVER_BASE_URL + "/send";
            HttpPost post = new HttpPost(url);

            String jsonBody = String.format(
                    "{\"sender\":\"%s\",\"receiver\":\"%s\",\"message\":\"%s\"}",
                    from, to, message
            );
            post.setEntity(new StringEntity(jsonBody));
            post.setHeader("Content-Type", "application/json");

            try (CloseableHttpResponse response = client.execute(post)) {
                int statusCode = response.getCode();
                System.out.println("Message envoyé avec succès ! Statut : " + statusCode);

                String responseBody = EntityUtils.toString(response.getEntity());
                System.out.println("Réponse du serveur : " + responseBody);
            }
        } catch (Exception e) {
            System.err.println("Erreur lors de l’envoi du message : " + e.getMessage());
        }
    }

    public void receiveMessages(String to) {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            String url = CHAT_SERVER_BASE_URL + "/receive/" + to;
            HttpGet get = new HttpGet(url);

            try (CloseableHttpResponse response = client.execute(get)) {
                int statusCode = response.getCode();
                System.out.println("Statut : " + statusCode);

                String responseBody = EntityUtils.toString(response.getEntity());
                System.out.println("Messages reçus pour " + to + " :");
                System.out.println(responseBody);
            }
        } catch (Exception e) {
            System.err.println("Erreur lors de la réception des messages : " + e.getMessage());
        }
    }
}
