package edu.esprit.controllers;

import edu.esprit.entities.Arrivage;
import edu.esprit.services.ServiceArrivage;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.fx.FXGraphics2D;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class StatController {


    @FXML
    private Canvas chartCanvas;

    private final ServiceArrivage serviceArrivage = new ServiceArrivage();

    public void initialize() {

        drawChart();
    }

    void drawChart() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        // Assume serviceArrivage.getAll() returns all Arrivage instances
        Set<Arrivage> arrivages = serviceArrivage.getAll();

        // Create a map to accumulate quantities by model
        Map<String, Integer> modelQuantities = new HashMap<>();

        for (Arrivage arrivage : arrivages) {
            String modele = arrivage.getV().getModele();
            int currentQuantity = arrivage.getQuantite();

            // Add the current quantity to the existing total (if any)
            modelQuantities.merge(modele, currentQuantity, Integer::sum);
        }

        // Populate the dataset with accumulated quantities
        modelQuantities.forEach((modele, totalQuantity) -> {
            dataset.addValue(totalQuantity, "Quantité", modele);
        });

        JFreeChart barChart = ChartFactory.createBarChart(
                "Quantité par Modèle de Voiture", // Chart title
                "Modèle", // Domain axis label
                "Quantité", // Range axis label
                dataset,
                PlotOrientation.VERTICAL,
                false, true, false);

        GraphicsContext gc = chartCanvas.getGraphicsContext2D();
        FXGraphics2D g2d = new FXGraphics2D(gc);
        g2d.clearRect(0, 0, (int) chartCanvas.getWidth(), (int) chartCanvas.getHeight());
        barChart.draw(g2d, new java.awt.Rectangle((int) chartCanvas.getWidth(), (int) chartCanvas.getHeight()));
    }
}
