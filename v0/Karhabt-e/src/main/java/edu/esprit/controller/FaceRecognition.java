package edu.esprit.controller;

import com.github.sarxos.webcam.Webcam;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import edu.esprit.entities.User;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.ResourceBundle;

public class FaceRecognition implements Initializable {

    @FXML
    private ImageView camDisplay;

    private Webcam webcam;

    public void initialize() {

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        webcam = Webcam.getDefault();
        webcam.open();

        Thread updateThread = new Thread(() -> {
            while (true) {
                try {
                    if (webcam.isOpen()) {
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        ImageIO.write(webcam.getImage(), "PNG", baos);
                        camDisplay.setImage(SwingFXUtils.toFXImage(ImageIO.read(new ByteArrayInputStream(baos.toByteArray())), null));
                    }
                    Thread.sleep(100);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        updateThread.setDaemon(true);
        updateThread.start();

        // Add shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (webcam != null && webcam.isOpen()) {
                webcam.close();
            }
        }));

    }

    User user = new User();

    @FXML
    void capture(ActionEvent event) throws IOException, InterruptedException {
        String imagePath = "src/main/resources/img/firstimag.jpg";
        ImageIO.write(webcam.getImage(),"JPG",new File(imagePath));
        webcam.close();

        System.setProperty("javax.net.ssl.trustStore","app.keystore");
        System.setProperty("javax.net.ssl.trustStorePassword","azerty");


        String imagePath2 = user.getImageUser();
        byte[] imageBytes1 = Files.readAllBytes(Paths.get(imagePath));
        byte[] imageBytes2 = Files.readAllBytes(Paths.get(imagePath2));

        String base64Image1 = Base64.getEncoder().encodeToString(imageBytes1);
        String base64Image2 = Base64.getEncoder().encodeToString(imageBytes2);

        String jsonPayload = String.format("{ \"encoded_image1\": \"%s\", \"encoded_image2\": \"%s\" }", base64Image1, base64Image2);

        HttpClient client = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://mfaceapi.xface.ai/api/v3/face/verify"))
                .header("Content-Type", "application/json")
                .header("Subscription-Key", "2zjceoE1gm5jp8ZXEw-VqnXnw3uF62324") // Replace with your actual subscription key
                .POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("Status Code: " + response.statusCode());
        System.out.println("Response Body: " + response.body());

        // Parsing the JSON response
        JsonObject jsonObject = JsonParser.parseString(response.body()).getAsJsonObject();
        if (jsonObject.has("errorMessage")) {
            System.out.println("Face is not detectable");
        } else {
            JsonArray matchedFaces = jsonObject.getAsJsonArray("matchedFaces");
            for (int i = 0; i < matchedFaces.size(); i++) {
                JsonObject matchedFace = matchedFaces.get(i).getAsJsonObject();
                int matchResult = matchedFace.get("matchResult").getAsInt();
                System.out.println("Match Result: " + matchResult);

                // Implement your logic based on match result
                // Example: if(matchResult == 1) { // successful match }
            }
        }
    }
    public void getuser(User user )
    {
        this.user=user;
    }

}