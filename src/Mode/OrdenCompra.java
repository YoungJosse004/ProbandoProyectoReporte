package Mode;

import java.sql.Date;
import java.util.List;

public class OrdenCompra {
    private int idOc; 
    private int idProv;
    private Date fecha;
    private List<DetalleOC> detalles; // Lista de detalles de la orden

    public OrdenCompra() {
    }

    public OrdenCompra(int idOc, int idProv, Date fecha, List<DetalleOC> detalles) {
        this.idOc = idOc;
        this.idProv = idProv;
        this.fecha = fecha;
        this.detalles = detalles;
    }

    public int getIdOc() {
        return idOc;
    }

    public void setIdOc(int idOc) {
        this.idOc = idOc;
    }

    public int getIdProv() {
        return idProv;
    }

    public void setIdProv(int idProv) {
        this.idProv = idProv;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public List<DetalleOC> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleOC> detalles) {
        this.detalles = detalles;
    }
    
}
