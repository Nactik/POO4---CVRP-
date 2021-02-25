package instance.reseau;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public abstract class Point {

    private final int id;
    private int abscisse;
    private int ordonnee;
    private Map<Point,Route> routes;

    public Point(int id, int abscisse, int ordonnee) {
        this.id = id;
        this.abscisse = abscisse;
        this.ordonnee = ordonnee;
        this.routes = new HashMap<>();
    }

    public int getId() {
        return id;
    }

    public int getAbscisse() {
        return abscisse;
    }

    public int getOrdonnee() {
        return ordonnee;
    }

    public boolean ajouterRoute(Point destination){
        if(destination == null) return false;

        Route route = new Route(this, destination);
        this.routes.put(destination,route);

        return true;
    }

     public int getCoutVers(Point destination){
        Route route = this.routes.get(destination);
        return (route == null ? Integer.MAX_VALUE : route.getCout());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return id == point.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Point{" +
                "id=" + id +
                ", abscisse=" + abscisse +
                ", ordonnee=" + ordonnee +
                '}';
    }

    public static void main(String[] args) {
        Client c1 = new Client(1, 1, 1, 1);
        Client c2 = new Client(2, 2,2,1);
        Client c3 = new Client(3,4,6,1);
        Client c4 = new Client(3,3,7,1);

        c1.ajouterRoute(c2);
        c1.ajouterRoute(c3);

        System.out.println("Cout vers c2 = " + c1.getCoutVers(c2));
        System.out.println("Cout vers c3 = " + c1.getCoutVers(c3));
        System.out.println("Cout vers c4 = " + c1.getCoutVers(c4));
    }
}
