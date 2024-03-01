package toolkit;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import models.User;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;


public class PDFGenerator {

    static List<User> usersList1= new ArrayList<>();


    public static void createPdf(List<User> usersList ,String name) {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream("UserTable.pdf"));
            document.open();


            // Add image
            Image image = Image.getInstance("src/main/resources/img/logoRSF.png"); // replace with your image path
            image.scaleToFit(PageSize.A4.getWidth()/16, PageSize.A4.getHeight()/16);
            image.setAlignment(Element.ALIGN_TOP);
            document.add(image);

            // Add title

            Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
            Paragraph title = new Paragraph("User Information  "+ name +"\n \t  \n ", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);



            PdfPTable table = new PdfPTable(8); // 8 columns.
            PdfPCell cell;

            // Adding headers
            String[] headers = {"ID", "Last Name", "First Name", "Email", "Password", "Area", "Role", "Phone"};
            for (String header : headers) {
                cell = new PdfPCell(new Phrase(header));
                cell.setHorizontalAlignment(Element.ALIGN_MIDDLE);
                table.addCell(cell);
            }

            // Adding data
            for (User user : usersList) {
                table.addCell(String.valueOf(user.getId()));
                table.addCell(user.getLName());
                table.addCell(user.getFirstName());
                table.addCell(user.getEmail());
                table.addCell(user.getPwd());
                table.addCell(user.getArea());
                table.addCell(user.getRole());
                table.addCell(String.valueOf(user.getPhone()));
            }

            document.add(table);
            document.close();
            System.out.println("doc created");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        // Create some sample users
        User user1 = new User("ines ","kt","blavbla","mail",234,"area","particip");;
        User user2 = new User ("feriel ","bg","blavbla","mail",234,"area","particip");

        // Add the users to the usersList
        usersList1.add(user1);
        usersList1.add(user2);

        // Call the createPdf method
        createPdf(usersList1,"feriel");
    }





}
