package galeria;

public class Oferta {
	private Comprador comprador;
	private Integer venta; /*Es el hash de la venta (sea simple o en subasta) en la que se está haciendo la oferta*/
	private int monto;
	
	public Comprador getComprador() {
		return comprador;
	}

	public Integer getVenta() {
		return venta;
	}

	public int getMonto() {
		return monto;
	}

	public Oferta(Comprador comprador, Integer venta, int monto) {
		super();
		this.comprador = comprador;
		this.venta = venta;
		this.monto = monto;
	}
	
	
}
