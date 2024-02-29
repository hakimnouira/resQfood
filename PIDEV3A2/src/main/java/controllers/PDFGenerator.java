package controllers;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import models.Donation;

public class PDFGenerator {

    public static String generateDonationListPDF(List<Donation> donations, String pdfFilePath) {
        try (FileWriter writer = new FileWriter(pdfFilePath)) {
            // Write PDF content
            writer.write("Donation List\n\n");

            for (Donation donation : donations) {
                writer.write("Category: " + donation.getDonation_category() + "\n");
                writer.write("Amount: " + donation.getDonation_amount() + "\n");
                writer.write("Food Name: " + donation.getFood_name() + "\n");
                writer.write("Quantity: " + donation.getFood_quantity() + "\n\n");
            }

            return pdfFilePath;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
