package solveur;

import instance.Instance;
import instance.reseau.Client;
import operateur.InsertionClient;
import operateur.Operateur;
import solution.Solution;

import java.util.LinkedList;
import java.util.List;

public class MeilleureInsertion implements Solveur{

    @Override
    public String getNom() {
        return "MeilleureInsertion";
    }

    @Override
    public Solution solve(Instance instance) {
        LinkedList<Client> clients = instance.getClients();
        Solution solution = new Solution(instance);

        while(!clients.isEmpty()){
            InsertionClient best = (InsertionClient) this.getMeilleurOperateur(clients,solution);
            if(solution.doInsertion(best)){
                clients.remove(best.getClientToAdd());
            } else {
                solution.addClientNewTournee(clients.get(0));
                clients.remove(0);
            }
        }
        return solution;
    }

    private Operateur getMeilleurOperateur(List<Client> clients,Solution solution){
        Operateur current = solution.getMeilleureInsertion(clients.get(0));
        if(current != null) {
            for (int i = 1; i < clients.size(); i++) {
                Operateur test = solution.getMeilleureInsertion(clients.get(i));
                if (test.isMeilleur(current))
                    current = test;
            }
        }
        return  current;
    }
}
