package test;

import instance.Instance;
import instance.reseau.Client;
import instance.reseau.Depot;
import operateur.IntraDeplacement;
import operateur.IntraEchange;
import operateur.OperateurLocal;
import operateur.TypeOperateurLocal;
import solution.Tournee;

import static operateur.TypeOperateurLocal.INTRA_DEPLACEMENT;
import static operateur.TypeOperateurLocal.INTRA_ECHANGE;

public class testIntraEchange {

    public static void main(String[] args) {
        int id = 1;
        Depot d = new Depot(id++, 0, 0);
        Instance inst = new Instance("test", 100, d);
        Client c1 = new Client(id++, 10, 0, 5);
        Client c2 = new Client(id++, 20, 0, 10);
        Client c3 = new Client(id++, 30, 0, 15);
        Client c4 = new Client(id++, 40, 0, 20);
        Client c5 = new Client(id, 50, 0, 25);

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

        System.out.println(t.deltaCoutEchange(0,1));
        System.out.println(t.deltaCoutEchange(3,4));
        System.out.println(t.deltaCoutEchange(0,4));
        System.out.println(t.deltaCoutEchange(1,3));
        System.out.println(t.deltaCoutEchange(4,3));
        System.out.println(t.deltaCoutEchange(4,5));

        IntraEchange opIntraEch = (IntraEchange) OperateurLocal.getOperateurIntra(TypeOperateurLocal.INTRA_ECHANGE,t,0,1);
        IntraEchange opIntraEch2 = (IntraEchange) OperateurLocal.getOperateurIntra(TypeOperateurLocal.INTRA_ECHANGE,t,3,4);
        IntraEchange opIntraEch3 = (IntraEchange) OperateurLocal.getOperateurIntra(TypeOperateurLocal.INTRA_ECHANGE,t,0,4);
        IntraEchange opIntraEch4 = (IntraEchange) OperateurLocal.getOperateurIntra(TypeOperateurLocal.INTRA_ECHANGE,t,1,3);
        IntraEchange opIntraEch5 = (IntraEchange) OperateurLocal.getOperateurIntra(TypeOperateurLocal.INTRA_ECHANGE,t,4,3);
        IntraEchange opIntraEch6 = (IntraEchange) OperateurLocal.getOperateurIntra(TypeOperateurLocal.INTRA_ECHANGE,t,4,5);

        System.out.println(opIntraEch.getDeltaCout());
        System.out.println(opIntraEch.isMouvementRealisable());
        System.out.println(opIntraEch.isMouvementAmeliorant());

        System.out.println(opIntraEch2.getDeltaCout());
        System.out.println(opIntraEch2.isMouvementRealisable());
        System.out.println(opIntraEch2.isMouvementAmeliorant());

        System.out.println(opIntraEch3.getDeltaCout());
        System.out.println(opIntraEch3.isMouvementRealisable());
        System.out.println(opIntraEch3.isMouvementAmeliorant());

        System.out.println(opIntraEch4.getDeltaCout());
        System.out.println(opIntraEch4.isMouvementRealisable());
        System.out.println(opIntraEch4.isMouvementAmeliorant());

        System.out.println(opIntraEch5.getDeltaCout());
        System.out.println(opIntraEch5.isMouvementRealisable());
        System.out.println(opIntraEch5.isMouvementAmeliorant());

        System.out.println(opIntraEch6.getDeltaCout());
        System.out.println(opIntraEch6.isMouvementRealisable());
        System.out.println(opIntraEch6.isMouvementAmeliorant());

        System.out.println(opIntraEch.isMeilleur(opIntraEch3));
        System.out.println(opIntraEch.isMeilleur(opIntraEch2));

        //On cherche le meilleur opérateur
        IntraEchange best = (IntraEchange) t.getMeilleurOperateurIntra(TypeOperateurLocal.INTRA_ECHANGE);
        System.out.println(best);

    }
}
