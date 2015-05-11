/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ada.gen;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.BitSet;
import java.util.Scanner;

/**
 *
 * @author Marek
 */
public class Algorithm {

    public Algorithm() {
    }

    public void init() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("input.txt"))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            String everything = sb.toString();
            Scanner scanner = new Scanner(everything);
            double total_capacity = Double.parseDouble(scanner.nextLine());
            int total_packages = Integer.parseInt(scanner.nextLine());
            Item packages[] = new Item[total_packages];
            for (int i = 0; i < total_packages; i++) {
                String[] spl = scanner.nextLine().split(" ");
                double size = Double.parseDouble(spl[0]);
                double value = Double.parseDouble(spl[1]);

                packages[i] = new Item(size, value);
            }
            Backpack bp;
            bp = Backpack.getInstance(total_capacity, packages);
            int population_size = Integer.parseInt(scanner.nextLine());
            Individual population[] = new Individual[population_size];
            for (int i = 0; i < population_size; i++) {
                String[] s = scanner.nextLine().split(" ");
                BitSet bs = new BitSet(total_packages);
                for (int j = 0; j < total_packages; j++) {
                    if (s[j].equals("1")) {
                        bs.set(j);
                    }
                }
                population[i] = new Individual(bs, total_packages);
                population[i].setBackpack(bp);
            }
            Population pop = new Population(population);
            pop.setBackpack(bp);
            String toPrint = "";
            toPrint += "total capacity: " + total_capacity + "\n";
            toPrint += "total packages: " + total_packages + "\n";
            toPrint += bp.toString();
            toPrint += pop.toString();
            System.out.println(toPrint);

            int generationCount = 100;
            Individual fittest = pop.getFittest();
            for (int i = 0; i < generationCount; i++) {
                Individual actFit = pop.getFittest();
                System.out.println("Generation: " + i + " Fittest: "
                        + actFit.getFitness()+", " + actFit);
                if (pop.getFittest().getFitness() > fittest.getFitness()) {
                    fittest = pop.getFittest();
                }
                pop = GeneticAlgorithm.evolvePopulation(pop);
            }
            
            System.out.println("Fitness of the fittest=" + fittest.getFitness() +", " + fittest);
        }

    }
}
