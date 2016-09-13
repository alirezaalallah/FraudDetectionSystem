package org.alallah.ga;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class EliteFinder {

    public static List<Chromosome> find(List<Chromosome> chromosomes) {
        List<Chromosome> sortedList = chromosomes.stream().sorted((ch1, ch2) -> {

            ch1.calculateFitness();
            ch2.calculateFitness();

            if (ch1.getFitness() > ch2.getFitness())
                return 1;
            if (ch1.getFitness() == ch2.getFitness())
                return 0;
            if (ch1.getFitness() < ch2.getFitness())
                return -1;

            return 0;

        }).collect(Collectors.toList());

        Collections.reverse(sortedList);


        return sortedList;
    }
}
