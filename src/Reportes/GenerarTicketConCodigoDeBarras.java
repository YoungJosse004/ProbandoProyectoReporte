import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class GenerarTicketConCodigoDeBarras {

    public static void main(String[] args) {
        Rectangle smallPage = new Rectangle(216, 600); // Tamaño pequeño para ticket
        Document document = new Document(smallPage, 10, 10, 10, 10);

        try {
            String ruta = System.getProperty("user.home") + "/Documents/almacenPDF/ticket.pdf";
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(ruta));
            document.open();

            // Fuentes y encabezado
            Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
            Font contentFont = FontFactory.getFont(FontFactory.HELVETICA, 10);

            // Encabezado de la tienda
            Paragraph header = new Paragraph("Supermercado Ejemplo", headerFont);
            header.setAlignment(Element.ALIGN_CENTER);
            document.add(header);

            Paragraph address = new Paragraph("Av. Ejemplo 123, Ciudad", contentFont);
            address.setAlignment(Element.ALIGN_CENTER);
            document.add(address);

            Paragraph phone = new Paragraph("Tel: (555) 123-4567", contentFont);
            phone.setAlignment(Element.ALIGN_CENTER);
            document.add(phone);

            document.add(new Paragraph(" "));

            // Información de la venta
            document.add(new Paragraph("Fecha: 2024-10-29", contentFont));
            document.add(new Paragraph("Número de Ticket: 00012345", contentFont));
            document.add(new Paragraph("Atendido por: Juan Pérez", contentFont));
            document.add(new Paragraph(" "));

            // Tabla de productos
            PdfPTable table = new PdfPTable(3);
            table.setWidthPercentage(100);
            table.setWidths(new float[]{2, 1, 1});
            table.addCell(new PdfPCell(new Phrase("Producto", headerFont)));
            table.addCell(new PdfPCell(new Phrase("Cant", headerFont)));
            table.addCell(new PdfPCell(new Phrase("Precio", headerFont)));

            // Productos de ejemplo
            String[][] productos = {
                    {"Paracetamol", "2", "10.00"},
                    {"Ibuprofeno", "1", "15.00"},
                    {"Vitaminas", "1", "25.00"}
            };

            for (String[] producto : productos) {
                table.addCell(new PdfPCell(new Phrase(producto[0], contentFont)));
                table.addCell(new PdfPCell(new Phrase(producto[1], contentFont)));
                table.addCell(new PdfPCell(new Phrase(producto[2], contentFont)));
            }

            document.add(table);

            document.add(new Paragraph(" "));
            Paragraph total = new Paragraph("Total: $60.00", headerFont);
            total.setAlignment(Element.ALIGN_RIGHT);
            document.add(total);

            document.add(new Paragraph(" "));

            // Código de barras con el número de ticket como ejemplo
            String ticketNumber = "00012345";
            Barcode128 barcode = new Barcode128();
            barcode.setCode(ticketNumber);
            barcode.setCodeType(Barcode.CODE128);

            Image barcodeImage = barcode.createImageWithBarcode(writer.getDirectContent(), BaseColor.BLACK, BaseColor.BLACK);
            barcodeImage.scalePercent(150); // Escala para ajustar tamaño
            barcodeImage.setAlignment(Element.ALIGN_CENTER);
            document.add(barcodeImage);

            // Mensaje de agradecimiento
            Paragraph gracias = new Paragraph("¡Gracias por su compra!", contentFont);
            gracias.setAlignment(Element.ALIGN_CENTER);
            document.add(gracias);

            System.out.println("Ticket con código de barras generado en: " + ruta);
            
            //Mostrar o abrrur en pdf
            File pdfFile=new File(ruta); //Objeto tipo archivo que contiene su ruta
            if (pdfFile.exists()) {
                Desktop.getDesktop().open(pdfFile);
                System.out.println("El archivo se abrio automaticamente");
            }else{
                System.out.println("No se encontro el archivo para abrir");
            }
            
            
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        } finally {
            document.close();
        }
    }
}

