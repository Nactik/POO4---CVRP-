package test;

import instance.Instance;
import instance.reseau.Client;
import instance.reseau.Depot;
import operateur.InterDeplacement;
import operateur.IntraDeplacement;
import operateur.TypeOperateurLocal;
import solution.Tournee;

import static operateur.TypeOperateurLocal.INTER_DEPLACEMENT;
import static operateur.TypeOperateurLocal.INTRA_DEPLACEMENT;

public class TestInterDeplacement {

    public static void main(String[] args) {
        int id = 1;
        Depot d = new Depot(id++, 0, 0);
        Instance inst = new Instance("test", 100, d);
        Client c1 = new Client(id++, 0, 5, 10);
        Client c2 = new Client(id++, 0, 10, 10);
        Client c3 = new Client(id++, 0, 10, 10);
        Client c4 = new Client(id++, 0, 15, 10);
        Client c5 = new Client(id++, 10, 0, 60);
        Client c6 = new Client(id++, 10, 10, 10);
        Client c7 = new Client(id++, 10, 15, 10);

        inst.ajouterClient(c1);
        inst.ajouterClient(c2);
        inst.ajouterClient(c3);
        inst.ajouterClient(c4);
        inst.ajouterClient(c5);
        inst.ajouterClient(c6);
        inst.ajouterClient(c7);

        Tournee t = new Tournee(inst);
        t.ajouterClient(c1);
        t.ajouterClient(c2);
        t.ajouterClient(c6);
        t.ajouterClient(c3);
        t.ajouterClient(c4);
        Tournee u = new Tournee(inst);
        u.ajouterClient(c5);
        u.ajouterClient(c7);

        InterDeplacement interDep1 = new InterDeplacement(t, u, 2, 1);
        InterDeplacement interDep2 = new InterDeplacement(u, t, 0, 2);
        System.out.println("interDep1 :"+interDep1.toString());
        System.out.println("interDep2 :"+interDep2.toString());
        System.out.println(interDep1.getDeltaCout());
        System.out.println(interDep2.getDeltaCout());

        //On cherche le meilleur opérateur
        InterDeplacement best = (InterDeplacement) t.getMeilleurOperateurInter(u,INTER_DEPLACEMENT);
        InterDeplacement best2 = (InterDeplacement) u.getMeilleurOperateurInter(t,INTER_DEPLACEMENT);

        System.out.println(best);
        System.out.println(best2);
    }
}
