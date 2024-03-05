package edu.esprit.traduction;
import com.google.gson.*;
import okhttp3.*;
import java.io.IOException;



public class TranslatorText {
    private static final String key = "6dba5383534845cb80d821dc333f21bb";
    private static String location = "westeurope";
    private static TranslatorText instance;
    public static Response post(String textToTranslate,String fromLanguage,String toLanguage) throws IOException {
        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/json");
        String jsonBody = "[{\"Text\": \"" + textToTranslate + "\"}]";
        RequestBody body = RequestBody.create(mediaType, jsonBody);

        // Utilisation de variables pour les langues source et cible


        String translationEndpoint = "https://api.cognitive.microsofttranslator.com/translate?api-version=3.0&from=" + fromLanguage + "&to=" + toLanguage;

        Request request = new Request.Builder()
                .url(translationEndpoint)
                .post(body)
                .addHeader("Ocp-Apim-Subscription-Key", key)
                .addHeader("Ocp-Apim-Subscription-Region", location)
                .addHeader("Content-type", "application/json")
                .build();

        return client.newCall(request).execute();
    }


    public static String extractTranslatedText(String responseBody) {
        JsonParser parser = new JsonParser();
        JsonArray jsonArray = parser.parse(responseBody).getAsJsonArray();

        if (jsonArray.size() > 0) {
            JsonObject firstTranslation = jsonArray.get(0).getAsJsonObject();

            if (firstTranslation.has("translations")) {
                JsonArray translationsArray = firstTranslation.getAsJsonArray("translations");

                if (translationsArray.size() > 0) {
                    JsonObject translationObject = translationsArray.get(0).getAsJsonObject();

                    if (translationObject.has("text")) {
                        return translationObject.get("text").getAsString();
                    }
                }
            }
        }

        return "Impossible d'extraire le texte traduit de la réponse JSON.";
    }
    public static TranslatorText getInstance(){
        if(instance == null)
            instance = new TranslatorText();
        return instance;
    }


//    public static void main(String[] args) {
//        try {
//            String textToTranslate = "bonjour mon amis";
//
//
//            String from="fr";
//            String to="en";
//
//            System.out.println("Envoi de la requête à l'API de traduction...");
//            Response response = post(textToTranslate,from,to);
//
//            System.out.println("Réponse brute de l'API :");
//            String responseBody = response.body().string();
//            System.out.println(responseBody);
//
//            // Additional logging for debugging
//            int statusCode = response.code();
//            System.out.println("\nCode de statut HTTP : " + statusCode);
//
//            // Afficher uniquement le texte traduit
//            String translatedText = extractTranslatedText(responseBody);
//            System.out.println("\nTexte traduit :");
//            System.out.println(translatedText);
//
//        } catch (IOException e) {
//            System.out.println(e.getMessage());
//        }
//    }

}
