class Galeria {
    private Map<String, Sala> mapaSalas;
    private Map<String, Bodega> mapaBodegas;

    public Galeria() {
        mapaSalas = new HashMap<>();
        mapaBodegas = new HashMap<>();
    } 
    
    //Getters
    
    public Map<String, Sala> getMapaSalas() {
        return mapaSalas;
    }

    public Map<String, Bodega> getMapaBodegas() {
        return mapaBodegas;
    }
}