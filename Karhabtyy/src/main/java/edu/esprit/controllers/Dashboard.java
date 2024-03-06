package edu.esprit.controllers;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.*;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import java.util.HashMap;
import java.util.Map;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import com.itextpdf.text.Font;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import edu.esprit.entities.Dossier;
import edu.esprit.services.ServiceDossier;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import com.itextpdf.text.Font;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;

import com.itextpdf.text.pdf.PdfWriter;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;


import java.util.HashMap;
import java.util.Map;
public class Dashboard {

    @FXML
    private ImageView qrCodeImage;
    @FXML
    private static Font titleFont;
    @FXML
    private TextField tfcin;
    @FXML
    private Button but_etat;

    @FXML
    private Pane searchPane;
    @FXML
    private Button qrcode;
    @FXML
    private Button tfshow;
    @FXML
    private DatePicker tfdate;
    @FXML
    private TextField tfmontant;
    @FXML
    private TextField tfnom;
    @FXML
    private Button pdf;
    @FXML
    private TextField tfprenom;
    @FXML
    private TextField tfregion;
    @FXML
    private Button tfvalider;
    @FXML
    private Button tfdelete;
    @FXML
    private Button tfmodify;
    @FXML
    private Button but_demande;
    @FXML
    private Button butt_dos;


    @FXML
    private TableColumn<Dossier, Integer> cin_col;


    @FXML
    private TableColumn<Dossier, Date> date_col;

    @FXML
    private Button show;

    @FXML
    private TableView<Dossier> dossier_table;

    @FXML
    private TableColumn<Dossier, Integer> montant_col;

    @FXML
    private TableColumn<Dossier, String> nom_col;

    @FXML
    private TableColumn<Dossier, String> prenom_col;

    @FXML
    private TableColumn<Dossier, String> region_col;

    @FXML
    private Pane most_inner_pane1;
    @FXML
    private TextField searchField;
    @FXML
    private HBox root;

    @FXML
    private TextField search;

    @FXML
    private Pane tfinnerPane;

    @FXML
    private AnchorPane tfsideBar;
    @FXML
    private TableColumn<Dossier, Integer> id_dossier_col;

    private final ServiceDossier sp = new ServiceDossier();
    private Dossier selectedItem;


    @FXML
    void SaveDossier(ActionEvent actionEvent) throws SQLException {
        int montant = Integer.parseInt(this.tfmontant.getText());
        int cin = Integer.parseInt(this.tfcin.getText());
        String nom = this.tfnom.getText();
        String prenom = this.tfprenom.getText();
        Date date = Date.valueOf(tfdate.getValue());
        String region = this.tfregion.getText();


        try {
            Dossier d = new Dossier(cin, nom, prenom, region, date, montant);
            this.sp.ajouter(d);
            // Validate input (controle de saisie)
            generateAndDisplayQRCode(d, qrCodeImage);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(" Exception");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
        //showdossier();

    }

    public void ModifierDossier(ActionEvent actionEvent) throws IOException, SQLException {
        try {
            int montant = Integer.parseInt(tfmontant.getText());
            int cin = Integer.parseInt(tfcin.getText());
            String nom = tfnom.getText();
            String prenom = tfprenom.getText();
            Date date = Date.valueOf(tfdate.getValue());
            String region = tfregion.getText();

            // Vérification des valeurs saisies
            if (montant <= 0) {
                showAlert("Le montant doit être supérieur à zéro.");
                return; // Exit the method if montant is invalid
            }
            if (cin <= 0) {
                showAlert("Le numéro de CIN doit être supérieur à zéro.");
                return; // Exit the method if cin is invalid
            }
            if (nom.isEmpty()) {
                showAlert("Le nom ne peut pas être vide.");
                return; // Exit the method if nom or prenom is empty
            }

            if (prenom.isEmpty()) {
                showAlert(" le prénom ne peut pas être vide.");
                return; // Exit the method if nom or prenom is empty
            }
            if (region.isEmpty()) {
                showAlert("La région ne peut pas être vide.");
                return; // Exit the method if region is empty
            }

            // Création d'un objet Dossier modifié
            Dossier modifiedDossier = new Dossier(cin, nom, prenom, region, date, selectedItem.getId_dossier(), montant);

            // Appel d'une méthode de service ou de repository pour mettre à jour le dossier dans la base de données
            this.sp.modifier(modifiedDossier);

            // Actualisation de l'affichage
            //showdossier();
        } catch (NumberFormatException e) {
            showAlert("Erreur de saisie : Veuillez entrer des valeurs valides.");
        } catch (IllegalArgumentException e) {
            showAlert("Erreur de saisie : " + e.getMessage());
        } catch (Exception e) {
            showAlert("Erreur lors de la modification du dossier : " + e.getMessage());
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initStyle(StageStyle.UNDECORATED); // Remove window decorations
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        // Apply custom CSS to the alert dialog pane
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("/DashStyle.css").toExternalForm());
        dialogPane.getStyleClass().add("custom-alert"); // Add your custom CSS class

        alert.showAndWait();
    }


    @FXML
    public void showdossier() throws SQLException {
        ServiceDossier sp = new ServiceDossier();
        System.out.println("show");

        ObservableList<Dossier> dossierList = FXCollections.observableArrayList(sp.getAll());
        try {
            for (Dossier dossier : dossierList) {
                System.out.println(dossier);
            }

            dossier_table.setItems(dossierList);
            id_dossier_col.setCellValueFactory(new PropertyValueFactory<>("id_dossier"));
            cin_col.setCellValueFactory(new PropertyValueFactory<>("cin"));
            nom_col.setCellValueFactory(new PropertyValueFactory<>("nom"));
            prenom_col.setCellValueFactory(new PropertyValueFactory<>("prenom"));
            region_col.setCellValueFactory(new PropertyValueFactory<>("region"));
            date_col.setCellValueFactory(new PropertyValueFactory<>("date"));
            montant_col.setCellValueFactory(new PropertyValueFactory<>("montant"));

        } catch (Exception e) {
            System.err.println(e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Exception");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }


    }

    public void displayDetailsInTextField() {
        dossier_table.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && (!dossier_table.getSelectionModel().isEmpty())) {
                // Get the selected item
                selectedItem = dossier_table.getSelectionModel().getSelectedItem();

                System.out.println(selectedItem);

                tfnom.setText(selectedItem.getNom());
                tfprenom.setText(selectedItem.getPrenom());
                tfregion.setText(selectedItem.getRegion());
                tfmontant.setText(Integer.toString(selectedItem.getMontant()));
                tfcin.setText(Integer.toString(selectedItem.getCin()));
                tfdate.setValue(selectedItem.getDate().toLocalDate());
                final ServiceDossier sp = new ServiceDossier();
                Dossier d = dossier_table.getSelectionModel().getSelectedItem();

                generateAndDisplayQRCode(d, qrCodeImage);


               // d.getId_dossier();
            }

        });
    }


    @FXML
    void Etatdossier(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/Etatdossier.fxml"));
        Scene newScene = new Scene(root);

        Stage stage = (Stage) but_etat.getScene().getWindow();


        // Set opacity of new scene's root to 0 to make it invisible initially
        root.setOpacity(0);

        // Create a fade transition for the old scene
        FadeTransition fadeOutTransition = new FadeTransition(Duration.seconds(0.5), stage.getScene().getRoot());
        fadeOutTransition.setToValue(0); // Fade out to fully transparent

        // Set the action to be performed after the transition
        fadeOutTransition.setOnFinished(e -> {
            // Set the new scene and perform the fade in transition
            stage.setScene(newScene);
            // Create a fade transition for the new scene
            FadeTransition fadeInTransition = new FadeTransition(Duration.seconds(0.5), root);
            fadeInTransition.setToValue(1); // Fade in to fully opaque
            fadeInTransition.play();
        });

        // Play the fade out transition
        fadeOutTransition.play();

    }

    @FXML
    void SupprimerDossier(ActionEvent event) {

        final ServiceDossier sp = new ServiceDossier();
        Dossier d = (Dossier) dossier_table.getSelectionModel().getSelectedItem();

        if (d != null) {
            try {

                sp.supprimer(d); // Assuming you have a method to delete a dossier
                //  showdossier();
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Exception");
                alert.setContentText("Error deleting dossier: " + e.getMessage());
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setContentText("Please select a dossier to delete.");
            alert.showAndWait();
        }


    }


    public void demandedoss(ActionEvent actionEvent) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/DemandeDossier.fxml"));
        Scene newScene = new Scene(root);

        Stage stage = (Stage) but_demande.getScene().getWindow();

        // Set opacity of new scene's root to 0 to make it invisible initially
        root.setOpacity(0);

        // Create a fade transition for the old scene
        FadeTransition fadeOutTransition = new FadeTransition(Duration.seconds(0.5), stage.getScene().getRoot());
        fadeOutTransition.setToValue(0); // Fade out to fully transparent

        // Set the action to be performed after the transition
        fadeOutTransition.setOnFinished(e -> {
            // Set the new scene and perform the fade in transition
            stage.setScene(newScene);
            // Create a fade transition for the new scene
            FadeTransition fadeInTransition = new FadeTransition(Duration.seconds(0.5), root);
            fadeInTransition.setToValue(1); // Fade in to fully opaque
            fadeInTransition.play();
        });

        // Play the fade out transition
        fadeOutTransition.play();

    }

    public void initialize() throws SQLException {
        //showdossier();
        id_dossier_col.setVisible(false);
    }

    public static void generateInvoice(Dossier dossier) throws IOException, DocumentException, SQLException {
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.BLACK);
        Paragraph title = new Paragraph("Liste des dossiers : ", titleFont);

        Document document = new Document();
        String fileName = "Dossier_" + dossier.getId_dossier() + ".pdf";
        PdfWriter.getInstance(document, new FileOutputStream(fileName));

        document.open();


        // Paragraph title = new Paragraph("Invoice for Dossier ID: " + dossier.getId_dossier(), titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);
        LocalDate date = LocalDate.of(2024, 3, 7); // Using factory method


        Paragraph dateParagraph = new Paragraph("Date: " + date);
        dateParagraph.setAlignment(Element.ALIGN_RIGHT);
        document.add(dateParagraph);

        PdfPTable table = new PdfPTable(6);
        table.setWidthPercentage(100);
        table.setWidths(new float[]{2, 3, 3, 2, 2, 3});
        ServiceDossier sd = new ServiceDossier();

        ObservableList<Dossier> dossierList = FXCollections.observableArrayList(sd.getAll());

        for (Dossier d : dossierList) {
            //System.out.println(dossier);

            PdfPCell cell = new PdfPCell(new Phrase("CIN " + d.getCin()));
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY); // Set cell background color
            table.addCell(cell);
            title.setSpacingAfter(200f); // Add space after the title
            table.setSpacingBefore(10f); // Add space before the table


            //  table.addCell(new Phrase("CIN " + d.getCin()));
            PdfPCell cell2 = new PdfPCell(new Phrase("Nom: " + d.getNom()));
            //  cell2.setBackgroundColor(BaseColor.LIGHT_GRAY); // Set cell background color
            table.addCell(cell2);
            title.setSpacingAfter(200f); // Add space after the title
            table.setSpacingBefore(10f); // Add space before the table
            // table.addCell(new Phrase("Nom: " + d.getNom()));
            table.addCell(new Phrase("Prenom: " + d.getPrenom()));
            table.addCell(new Phrase("Region: " + d.getRegion()));
            table.addCell(new Phrase("Montant: " + d.getMontant()));
            table.addCell(new Phrase("Date: " + d.getDate()));

            document.add(table);


        }

        document.close();
        if (Desktop.isDesktopSupported()) {
            try {
                File pdfFile = new File(fileName);
                Desktop.getDesktop().open(pdfFile);
            } catch (IOException e) {
                e.printStackTrace();

            }
        }
    }

    @FXML
    void generateInvoice(ActionEvent event) throws IOException, DocumentException, SQLException {
        Dossier d = new Dossier();
        ServiceDossier sd = new ServiceDossier();
        sd.getAll();

        generateInvoice(d);
    }


        public static void generateAndDisplayQRCode(Dossier dossier, ImageView qrCodeImageView) {
            try {
                String content = dossier.toString(); // Assuming dossier.toString() contains relevant data

                // Set QR code parameters
                int width = 200;
                int height = 200;
                Map<EncodeHintType, Object> hints = new HashMap<>();
                hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
                hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

                // Generate QR code
                QRCodeWriter qrCodeWriter = new QRCodeWriter();
                BitMatrix bitMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, width, height, hints);

                // Convert BitMatrix to a WritableImage
                WritableImage qrCodeImage = new WritableImage(width, height);
                for (int y = 0; y < height; y++) {
                    for (int x = 0; x < width; x++) {
                        qrCodeImage.getPixelWriter().setColor(x, y, bitMatrix.get(x, y) ? javafx.scene.paint.Color.BLACK : javafx.scene.paint.Color.WHITE);
                    }
                }

                // Display the QR code in the ImageView
                qrCodeImageView.setImage(qrCodeImage);
            } catch (WriterException e) {
                e.printStackTrace();
                // Handle any exceptions (e.g., invalid data, encoding issues)
            }
        }
    }
        /*
    public static void generateAndDisplayQRCode(Dossier dossier, ImageView qrCodeImageView) {
        try {
            String content = dossier.toString(); // Assuming dossier.toString() contains relevant data

            // Set QR code parameters
            int width = 200;
            int height = 200;
            Map<EncodeHintType, Object> hints = new HashMap<>();
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

            // Generate QR code
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, width, height, hints);

            // Convert BitMatrix to a WritableImage
            WritableImage qrCodeImage = new WritableImage(width, height);
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    qrCodeImage.getPixelWriter().setColor(x, y, bitMatrix.get(x, y) ? javafx.scene.paint.Color.BLACK : javafx.scene.paint.Color.WHITE);
                }
            }

            // Display the QR code in the ImageView
            qrCodeImageView.setImage(qrCodeImage);
        } catch (WriterException e) {
            e.printStackTrace();
            // Handle any exceptions (e.g., invalid data, encoding issues)
        }
    }*/



