package org.alallah;

import org.alallah.ga.CalculatorUtil;
import org.alallah.ga.Transaction;
import org.apache.commons.math3.genetics.*;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.*;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class GeneticApplication {
    public static class TokenizerMapper extends MapReduceBase
            implements org.apache.hadoop.mapred.Mapper<Object, Text, Text, Text> {

        private final static IntWritable one = new IntWritable(1);

        private Text word = new Text();

        private static Random r;

        TokenizerMapper() {
            r = new Random();
        }

        JobConf conf;

        @Override
        public void configure(JobConf job) {

            conf = job;

        }

        @Override
        public void map(Object o, Text text, OutputCollector<Text, Text> outputCollector, Reporter reporter) throws IOException {
            int Low = 1;
            int High = 10;

            Integer startIndex = text.toString().indexOf("[");

            String fitness = CalculatorUtil.toTransaction(text.toString()
                    .substring(startIndex).replace("[", "").replace("]", "").replace(",", "").replace(" ", "").replace(")", ""))
                    .getFitness().toString();

            Integer result = r.nextInt(High - Low) + Low; //by random key

            word.set(result.toString());

            outputCollector.collect(word, new Text(text.toString()));
        }
    }

    public static class MyReducer
            extends MapReduceBase
            implements org.apache.hadoop.mapred.Reducer<Text, Text, Text, Text> {

        JobConf conf;

        private static final int DIMENSION = 160;
        private static final int POPULATION_SIZE = 1000000;
        private static final int NUM_GENERATIONS = 1000;
        private static final double ELITISM_RATE = 1;
        private static final double CROSSOVER_RATE = 1;
        private static final double MUTATION_RATE = 0.9;
        private static final int TOURNAMENT_ARITY = 1000;

        @Override
        public void configure(JobConf job) {
            conf = job;
        }

        private ElitisticListPopulation generatePopulation(Iterator<Text> values) {
            List<org.apache.commons.math3.genetics.Chromosome> popList = new LinkedList<>();

            while (values.hasNext()) {
                Text value = values.next();

                Integer startIndex = value.toString().indexOf("[");

                BinaryChromosome chromosome = new FindOnes(CalculatorUtil.buildChromosomeBy(value.toString().substring(startIndex)));

                popList.add(chromosome);
            }

            return new ElitisticListPopulation(popList, popList.size(), ELITISM_RATE);

        }



        @Override
        public void reduce(Text key, Iterator<Text> values, OutputCollector<Text, Text> outputCollector, Reporter reporter) {
            System.out.println("Start Reducer");
            // initialize a new genetic algorithm
            GeneticAlgorithm ga = new GeneticAlgorithm(
                    new NPointCrossover<>(20),
                    CROSSOVER_RATE, // all selected chromosomes will be recombined (=crosssover)
                    new RandomKeyMutation(),
                    MUTATION_RATE,
                    new TournamentSelection(TOURNAMENT_ARITY)
            );

            Population initial = generatePopulation(values);
            // stopping conditions
            StoppingCondition stopCond = new FixedGenerationCount(1);

            // run the algorithm
            Population finalPopulation = ga.evolve(initial, stopCond);
            // best chromosome from the final population
            org.apache.commons.math3.genetics.Chromosome bestFinal = finalPopulation.getFittestChromosome();

            Integer startIndex = bestFinal.toString().indexOf("[");

            Transaction transaction = CalculatorUtil.toTransaction(bestFinal.toString()
                    .substring(startIndex).replace("[", "").replace("]", "").replace(",", "").replace(" ", "").replace(")", ""));

            System.out.println(transaction.description());


            finalPopulation.forEach(f -> {
                try {
                    outputCollector.collect(new Text(String.valueOf(f.getFitness())), new Text(f.toString()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }


        private static class FindOnes extends BinaryChromosome {

            //            11111,111,20000,13,60000,4,125,0,3,0,0,0
            private static Transaction transaction
                    = Transaction.builder()
                    .currentBalance("20000")
                    .averageBankBalance("13")
                    .timesOfOverdraft("60000")
                    .creditCardAge("4")
                    .deductedAmount("125")
                    .locationOfCCUsed("0")
                    .timeOfTheCCUsedWithRespectToLocation("3")
                    .averageDailyOverDraft("0")
                    .overDraftToday("0")
                    .amountOfTransaction("0")
                    .build();


            FindOnes(List<Integer> representation) {
                super(representation);
            }

            @Override
            public AbstractListChromosome<Integer> newFixedLengthChromosome(List<Integer> chromosomeRepresentation) {
                return new FindOnes(chromosomeRepresentation);
            }

            public double fitness() {
//                System.out.println(transaction.toString());
                return CalculatorUtil.distance(transaction, CalculatorUtil.toTransaction(this.getRepresentation().toString().replace("[", "").replace("]", "").replace(",", "").replace(" ", "")));

            }

        }


    }

    public static void main(String[] args) throws IOException {

        Integer iteration = 0;
        String input = args[0];
        String outputFormat = input + "-Output-%d";

        String output = String.format(outputFormat, iteration);

        Integer generation = 5;

        Integer mapper = 30;

        for (Integer i = iteration; i < generation; i++) {
            JobConf jobConf = conf(input, output);
            System.out.println("Configuration ok");
            JobClient.runJob(jobConf).waitForCompletion();
            input = output;
            output = String.format(outputFormat, ++iteration);
        }
    }

    //
//
//
    private static JobConf conf(String input, String output) throws IOException {
//
        System.out.println("JobConf");
        // Create the JobConf object
        JobConf conf = new JobConf(GeneticApplication.class);

        System.out.println("SampleJobName");
// Set the name of the Job
        conf.setJobName("SampleJobName");

        System.out.println("setMapOutputKeyClass");
// Set the output Key type for the Mapper
        conf.setMapOutputKeyClass(Text.class);

        System.out.println("setMapOutputValueClass");
// Set the output Value type for the Mapper
        conf.setMapOutputValueClass(Text.class);

        System.out.println("setOutputKeyClass");
// Set the output Key type for the Reducer
        conf.setOutputKeyClass(Text.class);

        System.out.println("setOutputValueClass");
// Set the output Value type for the Reducer
        conf.setOutputValueClass(Text.class);

        System.out.println("setMapperClass");
// Set the Mapper Class
        conf.setMapperClass(TokenizerMapper.class);
//        conf.setCombinerClass(org.apache.hadoop.mapred.Reducer.class);
// Set the Reducer Class
        System.out.println("setReducerClass");
        conf.setReducerClass(MyReducer.class);

        System.out.println("setInputFormat");
// Set the format of the input that will be provided to the program
        conf.setInputFormat(TextInputFormat.class);


        System.out.println("setOutputFormat");
// Set the format of the output for the program
        conf.setOutputFormat(TextOutputFormat.class);


        System.out.println("FileSystem");
        org.apache.hadoop.fs.FileSystem fs = org.apache.hadoop.fs.FileSystem.get(conf);


        System.out.println("exists");
        if (fs.exists(new Path(output)))
            fs.delete(new Path(output));

        System.out.println("setInputPaths");
// Set the location from where the Mapper will read the input
        FileInputFormat.setInputPaths(conf, new Path(input));

        System.out.println("setOutputPath");
// Set the location where the Reducer will write the output
        FileOutputFormat.setOutputPath(conf, new Path(output));

// Run the job on the cluster

        return conf;
    }


}
