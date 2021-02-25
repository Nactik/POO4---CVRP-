package instance.reseau;

public class Client extends Point{

    private int demande;

    public Client(int id, int abscisse, int ordonnee, int demande) {
        super(id, abscisse, ordonnee);
        this.demande = demande;
    }

    public int getDemande() {
        return demande;
    }

    @Override
    public String toString() {
        return "Client{ \n" +
                "\tdemande=" + demande + ", \n" +
                 "\t" + super.toString() + "\n" +
                '}';
    }

    public static void main(String[] args) {
        Client c1 = new Client(1, 1, 1, 1);
        System.out.println(c1.toString());

        Client c2 = new Client(2, 1,1,2);
        System.out.println(c2.toString());

        if(c1.equals(c2)){
            System.out.println("Le equals n'est pas bon");
        } else {
            System.out.println("Le equals est bon");
        }

        Client c3 = new Client(2, 2,2,1);
        System.out.println(c3.toString());

        if(c3.equals(c1)){
            System.out.println("C'est pas bon");
        }
    }
}
