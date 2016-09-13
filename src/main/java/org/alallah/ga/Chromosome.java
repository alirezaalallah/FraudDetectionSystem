package org.alallah.ga;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Chromosome {
    private List<Float> gens = new ArrayList<>();

    private Float fitness;

    public List<Float> getGens() {
        return gens;
    }

    public void setGens(List<Float> gens) {
        this.gens = gens;
        calculateFitness();
    }

    public Float getFitness() {
        return fitness;
    }

    public String dispGens() {
        StringBuilder stringBuilder = new StringBuilder();
        Integer count = 0;
        for (Float gen : gens) {
            if (count > 0) {

                stringBuilder.append(",");
            }

            stringBuilder.append(gen.toString());

            count++;
        }

        return stringBuilder.toString();
    }


    public Chromosome calculateFitness() {
        this.fitness = 0F;

        this.gens.forEach(gen ->
                        this.fitness += gen
        );

        return this;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Chromosome chromosome = new Chromosome();

        public Builder addGen(Float gen) {

            chromosome.gens.add(gen);
            return this;

        }

        public Builder from(String[] gens) {

            for (String gen : gens) {


                chromosome.gens.add(Float.parseFloat(gen));

            }

            return this;
        }

        public Builder from(String value) {


            String[] gens = value.substring(value.indexOf("|") + 1).split(",");


            from(gens);

            return this;
        }

        public Chromosome build() {
            chromosome.calculateFitness();

            return chromosome;
        }


    }

}
