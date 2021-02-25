package solveur;

import instance.Instance;
import instance.reseau.Client;
import io.InstanceReader;
import io.exception.ReaderException;
import solution.Solution;

import java.util.List;

public class InsertionSimple implements Solveur{

    @Override
    public String getNom() {
        return "Insertion Simple";
    }

    @Override
    public Solution solve(Instance instance) {
        Solution solution = new Solution(instance);

        for(Client client : instance.getClients()){
            if(solution.addClient(client)){
                System.out.println("ajout dans une tournée existante");
            } else{
                if(solution.addClientNewTournee(client)){
                    System.out.println("Ajout dans une nouvelle tournée");
                }
                else System.out.println("Ajout impossible");
            }
        }

        return solution;
    }

    public static void main(String[] args) {
        InsertionSimple solveur = new InsertionSimple();

        Instance instance = null;
        try {
            InstanceReader reader = new InstanceReader("instances/A-n32-k5.vrp");
            instance = reader.readInstance();
            System.out.println("Instance lue avec success !");
        } catch (ReaderException ex) {
            System.out.println(ex.getMessage());
        }

        Solution solution = solveur.solve(instance);

        System.out.println(solution);
    }
};
