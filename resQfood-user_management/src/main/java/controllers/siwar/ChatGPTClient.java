package controllers.siwar;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import javafx.scene.control.TextArea;

public class ChatGPTClient {

    private static final String apiKey = "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";
    private static final String apiUrl = "https://api.openai.com/v1/chat/completions";
    private static final TextArea chatDisplayArea = new TextArea();
    private static final TextArea userInputArea = new TextArea();

    public static String chatgpt(String message) {
        String requestBody = "{\"model\": \"text-davinci-003\", \"prompt\": \"" + message + "\"}";

        try {
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", "Bearer " + apiKey);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = requestBody.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            StringBuilder response = new StringBuilder();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
            }

            String aiResponse = response.toString();
            // Return AI response
            return aiResponse;
        } catch (IOException e) {
            e.printStackTrace();
            // Handle error
            return "Error: Failed to generate response";
        }
    }
}
