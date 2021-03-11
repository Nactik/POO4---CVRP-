package test;

import instance.Instance;
import instance.reseau.Client;
import instance.reseau.Depot;
import solution.Tournee;

public class testIntraDeplacement {

    public static void main(String[] args) {
        int id=1;

        Depot d = new Depot(id++, 0, 0);
        Instance inst = new Instance("test", 100, d);

        Client c1 = new Client(id++, 5, 0, 10);
        Client c2 = new Client(id++, 10, 0, 10);
        Client c3 = new Client(id++, 40, 0, 10);
        Client c4 = new Client(id++, 5, 0, 10);
        Client c5 = new Client(id++, 20, 0, 10);

        inst.ajouterClient(c1);
        inst.ajouterClient(c2);
        inst.ajouterClient(c3);
        inst.ajouterClient(c4);
        inst.ajouterClient(c5);

        Tournee t = new Tournee(inst);

        t.ajouterClient(c1);
        t.ajouterClient(c2);
        t.ajouterClient(c3);
        t.ajouterClient(c4);
        t.ajouterClient(c5);

        System.out.println(t.deltaCoutDeplacement(0,1));
    }
}
