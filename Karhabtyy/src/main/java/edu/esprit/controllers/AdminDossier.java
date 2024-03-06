package edu.esprit.controllers;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import edu.esprit.entities.etatDeDossier;
import edu.esprit.services.ServiceEtatDossier;
import javafx.scene.control.Alert;
import edu.esprit.entities.Dossier;
import edu.esprit.services.ServiceDossier;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class AdminDossier {

    @FXML
    private Button button_valider;

    @FXML
    private ComboBox<?> combo;
    @FXML
    private TableColumn<?, ?> etat_col;
    @FXML
    private TableColumn<?, ?> idEtat_col;
    @FXML
    private TableView<etatDeDossier> etat_table;
    @FXML
    private Button but_demande;

    @FXML
    private Button but_etat;

    @FXML
    private Button butt_dos;

    @FXML
    private Button button_nvalider;


    @FXML
    private ImageView qrCodeImage;
    @FXML
    private TableColumn<Dossier, Integer> cin_col;

    @FXML
    private TableColumn<Dossier, Integer> id_dossier_col;

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





ServiceEtatDossier s = new ServiceEtatDossier();

    @FXML
    void Etatdossier(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/AdminEtatdossier.fxml"));
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

    public void demandedoss(ActionEvent actionEvent) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/AdminDemandeDossier.fxml"));
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
    @FXML
    public void showdossier() throws SQLException {
        ServiceDossier sp = new ServiceDossier();
        ServiceEtatDossier s = new ServiceEtatDossier();
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



        ObservableList<etatDeDossier> etatList = FXCollections.observableArrayList(s.getAll());

        try {
            for (etatDeDossier etatDeDossier : etatList) {
                System.out.println(etatList);
            }
            etat_table.setItems(etatList);
            etat_col.setCellValueFactory(new PropertyValueFactory<>("etat"));
            idEtat_col.setCellValueFactory(new PropertyValueFactory<>("id_etat"));

        } catch (Exception e) {
            System.err.println(e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Exception");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }


    }


    @FXML
    public void showEtatdossier() throws SQLException {

        ServiceEtatDossier sp = new ServiceEtatDossier();

        ObservableList<etatDeDossier> etatList = FXCollections.observableArrayList(sp.getAll());

        try {
            for (etatDeDossier etatDeDossier : etatList) {
                System.out.println(etatList);
            }
            etat_table.setItems(etatList);
            etat_col.setCellValueFactory(new PropertyValueFactory<>("etat"));
            idEtat_col.setCellValueFactory(new PropertyValueFactory<>("id_etat"));

        } catch (Exception e) {
            System.err.println(e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Exception");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
    public void initialize() throws SQLException {

        id_dossier_col.setVisible(false);
        idEtat_col.setVisible(false);
    }



    @FXML
    void SaveEtatDossier(ActionEvent event) throws SQLException {
        String etat = String.valueOf(this.combo.getValue());

        if (etat != null && !etat.isEmpty()) {
            try {

                edu.esprit.entities.etatDeDossier d = new etatDeDossier(etat);
                ServiceEtatDossier s = new ServiceEtatDossier();
                this.s.ajouter(d);

                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Success");
                successAlert.setContentText("Etat saved successfully!");
                successAlert.showAndWait();
            } catch (Exception e) {
                // Handle exceptions (e.g., database errors)
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Error");
                errorAlert.setContentText("An error occurred while saving: " + e.getMessage());
                errorAlert.showAndWait();
            }
        } else {
            // Value is null or empty, show an alert
            Alert nullValueAlert = new Alert(Alert.AlertType.WARNING);
            nullValueAlert.setTitle("Warning");
            nullValueAlert.setContentText("Please select an etat before saving.");
            nullValueAlert.showAndWait();
        }

    }
    @FXML
    void showdossier(ActionEvent event) throws SQLException {
        ServiceDossier sp = new ServiceDossier();
       // System.out.println("show");

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


        ServiceEtatDossier s = new ServiceEtatDossier();

        ObservableList<etatDeDossier> etatList = FXCollections.observableArrayList(s.getAll());

        try {
            for (etatDeDossier etatDeDossier : etatList) {
                System.out.println(etatList);
            }
            etat_table.setItems(etatList);
            etat_col.setCellValueFactory(new PropertyValueFactory<>("etat"));
            idEtat_col.setCellValueFactory(new PropertyValueFactory<>("id_etat"));

        } catch (Exception e) {
            System.err.println(e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Exception");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
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
    public void displayDetailsInTextField() {
        dossier_table.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && (!dossier_table.getSelectionModel().isEmpty())) {
                // Get the selected item
                Dossier selectedItem = dossier_table.getSelectionModel().getSelectedItem();

                System.out.println(selectedItem);


                Dossier d = dossier_table.getSelectionModel().getSelectedItem();

                generateAndDisplayQRCode(d, qrCodeImage);


                // d.getId_dossier();
            }

        });
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







