package instance.reseau;

import java.util.Objects;

public class Route {

    private Point depart;
    private Point arrivee;
    private int cout;

    public Route(Point depart, Point arrivee) {
        this.depart = depart;
        this.arrivee = arrivee;
        this.cout = this.calculerCout(depart,arrivee);
    }

    private int calculerCout(Point p1, Point p2){
        double x1 = Math.pow(p1.getAbscisse() - p2.getAbscisse(),2);
        double x2 = Math.pow(p1.getOrdonnee() - p2.getOrdonnee(),2);

        double distance = Math.sqrt(x1 + x2);
        return (int) Math.round(distance);
    }

    public Point getDepart() {
        return depart;
    }

    public Point getArrivee() {
        return arrivee;
    }

    public int getCout() {
        return cout;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Route route = (Route) o;
        return Objects.equals(depart, route.depart) && Objects.equals(arrivee, route.arrivee);
    }

    @Override
    public int hashCode() {
        return Objects.hash(depart, arrivee);
    }

    @Override
    public String toString() {
        return "Route{" +
                "depart=" + depart +
                ", arrivée=" + arrivee +
                ", cout=" + cout +
                '}';
    }

    public static void main(String[] args) {
        Client c1 = new Client(1, 1, 1, 1);
        System.out.println(c1.toString());

        Client c2 = new Client(2, 2,2,1);
        System.out.println(c2.toString());

        Route r1 = new Route(c1,c2);
        System.out.println(r1);

        System.out.println("cout = " + r1.getCout());
    }
}
