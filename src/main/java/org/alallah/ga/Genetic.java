package org.alallah.ga;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Genetic {

    public static List<Chromosome> nextGeneration(List<Chromosome> currentPopulation) {
        List<Chromosome> eliteList = EliteFinder.find(currentPopulation);

        List<Chromosome> newPopulation = new ArrayList<>();

        Integer thirdDivision = Math.round(eliteList.size() / 3);
        System.out.println("thirdDivision:" + thirdDivision + " size:" + eliteList.size());


        if (thirdDivision == 0) {
            if (eliteList.size() == 2) {
                newPopulation.add(eliteList.get(0));
                newPopulation.add(eliteList.get(1));
            }
            if (eliteList.size() == 1) {
                newPopulation.add(eliteList.get(0));
            }
            return newPopulation;
        }

        for (Integer eliteIndex = 0; eliteIndex <= thirdDivision; eliteIndex++) {
            newPopulation.add(eliteList.get(eliteIndex));
        }

        System.out.println("Crossover....");

        for (Integer childIndex = 0; childIndex < thirdDivision; childIndex++) {
            newPopulation.addAll(mutation(crossover(eliteList)));
        }


        return newPopulation;
    }

    private static Random r = new Random();

    public static List<Chromosome> crossover(List<Chromosome> population) {
        if (population.isEmpty())
            return new ArrayList<>();

        if (population.size() == 1) {
            return population;
        }

        List<Chromosome> children = new ArrayList<>();

        int Low = 0;
        int High = population.size();


        Integer parentIndex1 = r.nextInt(High - Low) + Low;
        Integer parentIndex2 = r.nextInt(High - Low) + Low;


        if (parentIndex1 == parentIndex2) {
            if ((parentIndex2 + 1) < population.size()) {
                parentIndex2 = parentIndex2 + 1;
            } else if ((parentIndex2 - 1) >= 0) {
                parentIndex2 = parentIndex2 - 1;
            }

        }


        Chromosome parent1 = population.get(parentIndex1);
        Chromosome parent2 = population.get(parentIndex2);

        System.out.println(String.format("crossover %d with %d", parentIndex1, parentIndex2));

        System.out.println(String.format("parent1 %s", parent1.dispGens()));
        System.out.println(String.format("parent2 %s", parent2.dispGens()));


        Chromosome.Builder childBuilder1 = Chromosome.builder();
        Chromosome.Builder childBuilder2 = Chromosome.builder();


        childBuilder1.addGen(parent1.getGens().get(0));
        childBuilder1.addGen(parent2.getGens().get(1));
        childBuilder1.addGen(parent2.getGens().get(2));
        childBuilder1.addGen(parent2.getGens().get(3));
        childBuilder1.addGen(parent1.getGens().get(4));


        System.out.println(String.format("child1 %s", childBuilder1.build().dispGens()));


        childBuilder2.addGen(parent2.getGens().get(0));
        childBuilder2.addGen(parent1.getGens().get(1));
        childBuilder2.addGen(parent1.getGens().get(2));
        childBuilder2.addGen(parent1.getGens().get(3));
        childBuilder2.addGen(parent2.getGens().get(4));

        System.out.println(String.format("child2 %s", childBuilder2.build().dispGens()));


        children.add(childBuilder1.build());
        children.add(childBuilder2.build());

        return children;
    }

    public static List<Chromosome> mutation(List<Chromosome> childern) {

        System.out.println("Mutation....");

        if (childern.isEmpty())
            return new ArrayList<>();

        if (childern.size() == 1) {
            return childern;
        }

        List<Chromosome> newChildren = childern;

        int Low = 0;
        int High = 1;


        Integer index = r.nextInt(High - Low) + Low;

        newChildren.get(index).setGens(mutat(newChildren.get(index).getGens()));

        return newChildren;
    }

    public static List<Float> mutat(List<Float> a) {
        List<Float> res1 = null, parent1, child;

        parent1 = a;

        Random randomGen = new Random();
        int rand = randomGen.nextInt(4);
        int rand1 = randomGen.nextInt(4);

        if (rand == rand1)
            if (rand > 1) rand1 = rand - 1;
            else rand1 = rand + 1;

//	  System.out.println(" "+ parent1[0]+" "+ parent1[1]+" "+ parent1[2]+" "+ parent1[3]+" "+ parent1[4]);
        //    System.out.println("********* end of Parent 1");

        //   System.out.println("the random num are "+rand+"  "+rand1);

        child = parent1;

        if ((rand == 0) || (rand1 == 0)) {
            float t1 = randomGen.nextFloat();
            int t2 = randomGen.nextInt(6);
            if (t1 > 0.2) t1 = (float) 0.15;
            if (t2 == 0) t2 = 6;

            child.set(0, t1 * t2);
        }

        if ((rand == 1) || (rand1 == 1)) {
            int t1 = randomGen.nextInt(4);
            int t2 = randomGen.nextInt(14);
            if (t1 == 0) t1 = 4;
            if (t2 == 0) t2 = 4;
            child.set(1, (float) ((float) t1 / (float) t2));
        }
        if ((rand == 2) || (rand1 == 2)) {
            float t1 = randomGen.nextFloat();
            int t2 = randomGen.nextInt(1);
            if (t1 > 0.2) t1 = (float) 0.16;
            if (t2 == 0) t2 = (int) 1;

            child.set(2, t1 * t2);


        }
        if ((rand == 3) || (rand1 == 3)) {
            float t1 = randomGen.nextFloat();
            if (t1 > 0.2) t1 = (float) 0.2;

            child.set(3, t1 * 2);

        }
        if ((rand == 4) || (rand1 == 4)) {

            float t1 = randomGen.nextFloat();
            int t2 = randomGen.nextInt(1);
            if (t1 > 0.2) t1 = (float) 0.15;
            if (t2 == 0) t2 = (int) 1;

            child.set(4, t1 * t2);
        }

        //  child=parent1;

        res1 = child;

        //   System.out.println("child of mutat "+ child[0]+" "+ child[1]+" "+ child[2]+" "+ child[3]+" "+ child[4]);

        //   System.out.println("******child 2*** ");

        return res1;
    }


}
