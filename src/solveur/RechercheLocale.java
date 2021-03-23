package solveur;

import instance.Instance;
import io.InstanceReader;
import io.exception.ReaderException;
import operateur.OperateurLocal;
import operateur.TypeOperateurLocal;
import solution.Solution;

public class RechercheLocale implements Solveur{

    @Override
    public String getNom() {
        return "RechercheLocale";
    }

    @Override
    public Solution solve(Instance instance) {
        InsertionSimple insertionSimple = new InsertionSimple();
        Solution solution = insertionSimple.solve(instance);

        boolean improve = true;

        if(improve){
            improve = false;

            OperateurLocal best = solution.getMeilleurOperateur(TypeOperateurLocal.INTER_DEPLACEMENT);
            if(best.isMouvementAmeliorant()){
                solution.doMouvementRechercheLocale(best);
                improve = true;
            }
        }
        return solution;
    }

    public static void main(String[] args) {
        Instance instance = null;

        try {
            InstanceReader reader = new InstanceReader("instances/A-n32-k5.vrp");
            instance = reader.readInstance();
        } catch (ReaderException ex) {
            System.out.println(ex.getMessage());
        }

        if (instance != null) {
            RechercheLocale rc = new RechercheLocale();
            Solution solution = rc.solve(instance);
            System.out.println("Coût total: " + solution.getCoutTotal());
            if (solution.check()) System.out.println("SOLUTION OK");
            else System.out.println("SOLUTION NOK");
        }
    }
}
