package instance.reseau;

public class Depot extends Point{

    public Depot(int id, int abscisse, int ordonnee) {
        super(id, abscisse, ordonnee);
    }

    @Override
    public String toString() {
        return "Depot{ \n" +
                "\t" + super.toString() + "\n" +
                "}";
    }

    public static void main(String[] args) {

    }
}
