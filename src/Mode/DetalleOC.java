package Mode;
public class DetalleOC {
    private int idDetalleOc;
    private int idOc; 
    private int idProd;
    private int cantidad;
    private double precio;

    public DetalleOC() {
    }

    public DetalleOC(int idDetalleOc, int idOc, int idProd, int cantidad, double precio) {
        this.idDetalleOc = idDetalleOc;
        this.idOc = idOc;
        this.idProd = idProd;
        this.cantidad = cantidad;
        this.precio = precio;
    }

    public int getIdDetalleOc() {
        return idDetalleOc;
    }

    public void setIdDetalleOc(int idDetalleOc) {
        this.idDetalleOc = idDetalleOc;
    }

    public int getIdOc() {
        return idOc;
    }

    public void setIdOc(int idOc) {
        this.idOc = idOc;
    }

    public int getIdProd() {
        return idProd;
    }

    public void setIdProd(int idProd) {
        this.idProd = idProd;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
    
}
