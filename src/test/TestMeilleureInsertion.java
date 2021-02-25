package test;

import instance.Instance;
import instance.reseau.Client;
import instance.reseau.Depot;
import io.InstanceReader;
import io.exception.ReaderException;
import operateur.InsertionClient;
import operateur.Operateur;
import solution.Tournee;

public class TestMeilleureInsertion{

    public static void main(String[] args) {
        /*int id = 1;
        Depot d = new Depot(id++, 0, 0);
        Instance inst = new Instance("test", 100, d);
        Client c1 = new Client(id ++, 10, 0, 5);
        Client c2 = new Client(id++, 20, 0, 10);
        Client c3 = new Client(id ++, 30, 0, 20);
        Client cIns = new Client(id ++, 40, 0, 15);
        inst.ajouterClient(c1);
        inst.ajouterClient(c2);
        inst.ajouterClient(c3);
        inst.ajouterClient(cIns);
        Tournee t = new Tournee(inst);
        t.ajouterClient(c1);
        t.ajouterClient(c2);
        t.ajouterClient(c3);

        System.out.println("cout position -1 :"+ t.deltaCoutInsertion(-1, cIns));
        System.out.println("cout position 0 :"+ t.deltaCoutInsertion(0, cIns));
        System.out.println("cout position 1 :"+ t.deltaCoutInsertion(1, cIns));
        System.out.println("cout position 2 :"+ t.deltaCoutInsertion(2, cIns));
        System.out.println("cout position 3 :"+ t.deltaCoutInsertion(3, cIns));
        System.out.println("cout position 4 :"+ t.deltaCoutInsertion(4, cIns));

        InsertionClient insertionClient = new InsertionClient(t,cIns,0);
        System.out.println("cout : "+insertionClient.getDeltaCout());
        System.out.println("Realisable :"+ insertionClient.isMouvementRealisable());

        Operateur meilleur = t.getMeilleureInsertion(cIns);
        System.out.println(meilleur);*/

        try {
            InstanceReader instanceReader = new InstanceReader("instance/A-n32-k5.vrp");
            Instance instance = instanceReader.readInstance();


        } catch (ReaderException e) {
            e.printStackTrace();
        }


    }
}
