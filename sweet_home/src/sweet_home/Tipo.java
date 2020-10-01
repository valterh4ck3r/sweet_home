package sweet_home;

public enum Tipo {

	CASA(1), APARTAMENTO(2), TERRENO(3);

    public int numTipo;

    Tipo(int valor) {
        this.numTipo = valor;
    }
}
