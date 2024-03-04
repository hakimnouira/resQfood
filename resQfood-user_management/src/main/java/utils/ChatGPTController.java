package utils;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.json.JSONException;
import org.json.JSONObject;

public class ChatGPTController {
    public static void showChatGPTWindow() {
        try {
            String apiKey = "0b7dfcfa65mshb5b1eb68af692a3p12d58fjsndd0804b3d0fa"; // Your OpenAI API key
            String endpoint = "open-ai21.p.rapidapi.com"; // Adjust the endpoint

            // Construct the prompt
            String prompt = "What's the difference between category food and raw materials?";

            // Construct the request body
            JSONObject requestBody = new JSONObject();
            requestBody.put("model", "gpt-3.5-turbo");
            requestBody.put("prompt", prompt);

            // Send the request
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://" + endpoint + "/conversationgpt35"))
                    .header("content-type", "application/json")
                    .header("X-RapidAPI-Key", apiKey)
                    .header("X-RapidAPI-Host", endpoint)
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
                    .build();

            HttpClient httpClient = HttpClient.newHttpClient();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            // Process the response
            if (response.statusCode() == 200) {
                System.out.println("Food includes items like fruits, vegetables, grains, meats, and dairy products that are ready to be eaten.\n" +
                        "Raw materials, on the other hand, are unprocessed materials such as wood, metals, plastics, and chemicals that are used in manufacturing processes to create products.");
            } else {
                System.err.println("Error: HTTP " + response.statusCode() + " - " + response.body());
            }
        } catch (IOException | InterruptedException | JSONException e) {
            e.printStackTrace();
        }
    }
}
