package ModeloDAO;
import Mode.Conexion;
import Mode.DetalleOC;
import Mode.OrdenCompra;
import Mode.Producto;
import Mode.Proveedor;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.io.File;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class OrdenCompraDAO {
    private Connection cn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;

public OrdenCompra buscarOrdenCompra(int idOc) {
    OrdenCompra oc = new OrdenCompra();
    oc.setDetalles(new ArrayList<DetalleOC>()); // Inicializar la lista de detalles
    String sql = "SELECT * FROM orden_compra WHERE id_oc = ?";
    String sqlDetalles = "SELECT * FROM detalle_oc WHERE id_oc = ?"; // Consulta para obtener detalles

    try {
        cn = Conexion.getConexion();
        ps = cn.prepareStatement(sql);
        ps.setInt(1, idOc);
        rs = ps.executeQuery();
        
        if (rs.next()) {
            oc.setIdOc(rs.getInt("id_oc"));
            oc.setIdProv(rs.getInt("id_prov"));
            oc.setFecha(rs.getDate("fecha"));
            
            // Obtener los detalles de la orden de compra
            ps = cn.prepareStatement(sqlDetalles);
            ps.setInt(1, oc.getIdOc());
            ResultSet rsDetalles = ps.executeQuery();
            
            while (rsDetalles.next()) {
                DetalleOC detalle = new DetalleOC();
                detalle.setIdDetalleOc(rsDetalles.getInt("id_detalle_oc"));
                detalle.setIdOc(rsDetalles.getInt("id_oc"));
                detalle.setIdProd(rsDetalles.getInt("id_prod"));
                detalle.setCantidad(rsDetalles.getInt("cantidad"));
                detalle.setPrecio(rsDetalles.getDouble("precio"));
                
                oc.getDetalles().add(detalle); // Añadir el detalle a la lista
            }
        }
    } catch (Exception e) {
        System.out.println("Error al buscar la orden de compra: " + e.getMessage());
    } finally {
        cerrarConexion();
    }
    return oc;
}


    public void generarReportePDF(int idOc, String filePath) {
        OrdenCompra oc = buscarOrdenCompra(idOc);
        if (oc.getIdOc() == 0) {
            System.out.println("No se encontró la orden de compra con ID " + idOc);
            return;
        }
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();
            // Título
            Font fontTitulo = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD, BaseColor.BLACK);
            Paragraph titulo = new Paragraph("Reporte de Orden de Compra", fontTitulo);
            titulo.setAlignment(Element.ALIGN_CENTER);
            document.add(titulo);
            document.add(new Paragraph(" ")); // Espacio en blanco
            // Tabla de detalles de la orden
            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            // Encabezados de la tabla
            Font fontEncabezado = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.WHITE);
            PdfPCell header1 = new PdfPCell(new Phrase("Campo", fontEncabezado));
            header1.setBackgroundColor(BaseColor.GRAY);
            header1.setHorizontalAlignment(Element.ALIGN_CENTER);
            PdfPCell header2 = new PdfPCell(new Phrase("Valor", fontEncabezado));
            header2.setBackgroundColor(BaseColor.GRAY);
            header2.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(header1);
            table.addCell(header2);
            // Datos de la orden
            table.addCell("ID de Orden de Compra");
            table.addCell(String.valueOf(oc.getIdOc()));
            table.addCell("ID del Proveedor");
            table.addCell(String.valueOf(oc.getIdProv()));
            table.addCell("Fecha");
            table.addCell(String.valueOf(oc.getFecha()));
            // Añadir tabla al documento
            document.add(table);
            System.out.println("Reporte PDF generado exitosamente en: " + filePath);
            //abrir
            File pdfFile=new File(filePath); //Objeto tipo archivo que contiene su ruta
            if (pdfFile.exists()) {
                Desktop.getDesktop().open(pdfFile);
                System.out.println("El archivo se abrio automaticamente");
            }else{
                System.out.println("No se encontro el archivo para abrir");
            }
        } catch (DocumentException | IOException e) {
            System.out.println("Error al generar el reporte PDF: " + e.getMessage());
        } finally {
            document.close();
        }
    }
    private void cerrarConexion() {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (cn != null) cn.close();
        } catch (Exception e) {
            System.out.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }
    
    //DEFINITIVO
public void generarReporteDetalladoPDF(int idOc, String filePath) {
    Document document = new Document();
    
    try {
        PdfWriter.getInstance(document, new FileOutputStream(filePath));
        document.open();
        
        // Título del reporte
        document.add(new Paragraph("Reporte de Orden de Compra", new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD)));
        document.add(new Paragraph("ID Orden de Compra: " + idOc));
        document.add(new Paragraph("Fecha: " + new SimpleDateFormat("dd/MM/yyyy").format(new Date())));
        document.add(new Paragraph("\n"));
        
        // Obtener la orden de compra y su proveedor
        OrdenCompra ordenCompra = buscarOrdenCompra(idOc);
        Proveedor proveedor = obtenerProveedor(ordenCompra.getIdProv());
        
        if (ordenCompra != null && proveedor != null) {
            document.add(new Paragraph("Proveedor: " + proveedor.getNombre()));
            document.add(new Paragraph("Teléfono: " + proveedor.getTelefono()));
            document.add(new Paragraph("Dirección: " + proveedor.getDireccion()));
            document.add(new Paragraph("Fecha de Orden: " + new SimpleDateFormat("dd/MM/yyyy").format(ordenCompra.getFecha())));
            document.add(new Paragraph("\nDetalles de la Orden:"));
            
            // Crear la tabla para los detalles de la orden
            PdfPTable table = new PdfPTable(4); // 4 columnas: ID Producto, Nombre Producto, Cantidad, Precio
            table.addCell("ID Producto");
            table.addCell("Nombre Producto");
            table.addCell("Cantidad");
            table.addCell("Precio Unitario");
            
            // Obtener los detalles de la orden de compra
            for (DetalleOC detalle : ordenCompra.getDetalles()) {
                Producto producto = obtenerProducto(detalle.getIdProd()); // Obtener producto por ID
                if (producto != null) {
                    table.addCell(String.valueOf(producto.getIdProd()));
                    table.addCell(producto.getNombre());
                    table.addCell(String.valueOf(detalle.getCantidad()));
                    table.addCell(String.valueOf(detalle.getPrecio()));
                }
            }
            
            document.add(table);
        } else {
            document.add(new Paragraph("No se encontró la orden de compra o proveedor."));
        }
        
    } catch (DocumentException | IOException e) {
        System.out.println("Error al crear el PDF: " + e.getMessage());
    } finally {
        document.close();
    }
}

// Método para obtener el proveedor por ID
private Proveedor obtenerProveedor(int idProv) {
    Proveedor proveedor = new Proveedor();
    String sql = "SELECT * FROM proveedor WHERE id_prov = ?";

    try {
        cn = Conexion.getConexion();
        ps = cn.prepareStatement(sql);
        ps.setInt(1, idProv);
        rs = ps.executeQuery();

        if (rs.next()) {
            proveedor.setIdProv(rs.getInt("id_prov"));
            proveedor.setNombre(rs.getString("nombre"));
            proveedor.setTelefono(rs.getString("telefono"));
            proveedor.setDireccion(rs.getString("direccion"));
        }
    } catch (Exception e) {
        System.out.println("Error al obtener proveedor: " + e.getMessage());
    }

    return proveedor;
}

// Método para obtener el producto por ID
private Producto obtenerProducto(int idProd) {
    Producto producto = new Producto();
    String sql = "SELECT * FROM producto WHERE id_prod = ?";

    try {
        cn = Conexion.getConexion();
        ps = cn.prepareStatement(sql);
        ps.setInt(1, idProd);
        rs = ps.executeQuery();

        if (rs.next()) {
            producto.setIdProd(rs.getInt("id_prod"));
            producto.setNombre(rs.getString("nombre"));
            producto.setPrecio(rs.getDouble("precio_venta"));
            // Ignorar "imagen" y otros atributos si no son necesarios
        }
    } catch (Exception e) {
        System.out.println("Error al obtener producto: " + e.getMessage());
    }

    return producto;
}
    

}

