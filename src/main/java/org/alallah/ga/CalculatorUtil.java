package org.alallah.ga;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CalculatorUtil {

    private static Random r = new Random();
    private static Random c = new Random();

    public static Float calculateScore(Transaction transaction) {
        Float value = 0.0F;

        float[] res = null;

        res = Detection.ccfreq(transaction);
        value += res[1];
        if (res[0] >= 1) {
            transaction.addSuspected(Suspect.builder()
                    .suspectType(SuspectType.FREQUENCY).value(res[1])
                    .build());
        }
        res = Detection.ccloc(transaction);
        value += res[1];
        if (res[0] >= 1) {
            transaction.addSuspected(Suspect.builder()
                    .suspectType(SuspectType.LOCATION).value(res[1])
                    .build());
        }

        res = Detection.ccod(transaction);
        value += res[1];
        if (res[0] >= 1) {
            transaction.addSuspected(Suspect.builder()
                    .suspectType(SuspectType.OVER_DRAFT).value(res[1])
                    .build());
        }

        res = Detection.ccbb(transaction);
        value += res[1];
        if (res[0] >= 1) {
            transaction.addSuspected(Suspect.builder()
                    .suspectType(SuspectType.BANK_BALANCE).value(res[1])
                    .build());
        }


        res = Detection.ccds(transaction);
        value += res[1];
        if (res[0] >= 1) {
            transaction.addSuspected(Suspect.builder()
                    .suspectType(SuspectType.DAILY_SPENDING).value(res[1])
                    .build());
        }

        return value;
    }

    public static List<Integer> buildChromosomeBy(String value) {

        char[] f = value.replace("[", "").replace("]", "").replace(",", "").replace(" ", "").replace(")","").toCharArray();


        List<Integer> gens = new ArrayList<>();


        for (char ff : f) {
            gens.add(Integer.parseInt(String.valueOf(ff)));
        }

        return gens;
    }

    public static Transaction toTransaction(String binary) {
        StringBuilder original = new StringBuilder();
        original.append(Integer.parseInt(binary.substring(0, 20), 2)).append(",")
                .append(Integer.parseInt(binary.substring(20, 40), 2)).append(",")
                .append(Integer.parseInt(binary.substring(40, 60), 2)).append(",")
                .append(Integer.parseInt(binary.substring(60, 80), 2)).append(",")
                .append(Integer.parseInt(binary.substring(80, 100), 2)).append(",")
                .append(Integer.parseInt(binary.substring(100, 120), 2)).append(",")
                .append(Integer.parseInt(binary.substring(120, 140), 2)).append(",")
                .append(Integer.parseInt(binary.substring(140, 160), 2)).append(",")
                .append(Integer.parseInt(binary.substring(160, 180), 2)).append(",")
                .append(Integer.parseInt(binary.substring(180, 200), 2));

        return Transaction.builder().from("0,0," + original.toString()).build();
    }


    public static double distance(Transaction transaction1, List<Integer> integers) {
        return distance(transaction1, Transaction.builder()
                .currentBalance(integers.get(0).toString())
                .averageBankBalance(integers.get(1).toString())
                .timesOfOverdraft(integers.get(2).toString())
                .creditCardAge(integers.get(3).toString())
                .deductedAmount(integers.get(4).toString())
                .locationOfCCUsed(integers.get(5).toString())
                .timeOfTheCCUsedWithRespectToLocation(integers.get(6).toString())
                .averageDailyOverDraft(integers.get(7).toString())
                .overDraftToday(integers.get(8).toString())
                .amountOfTransaction(integers.get(9).toString())
                .build());
    }

    public static double distance(Transaction transaction1, Transaction transaction2) {
        return distance(new Double[]{
                Double.parseDouble(transaction1.getCurrentBalance()),
                Double.parseDouble(transaction1.getAverageBankBalance()),
                Double.parseDouble(transaction1.getTimesOfOverdraft()),
                Double.parseDouble(transaction1.getCreditCardAge()),
                Double.parseDouble(transaction1.getDeductedAmount()),
                Double.parseDouble(transaction1.getLocationOfCCUsed()),
                Double.parseDouble(transaction1.getTimeOfTheCCUsedWithRespectToLocation()),
                Double.parseDouble(transaction1.getAverageDailyOverDraft()),
                Double.parseDouble(transaction1.getOverDraftToday()),
                Double.parseDouble(transaction1.getAmountOfTransaction()),
        }
                , new Double[]{
                Double.parseDouble(transaction2.getCurrentBalance()),
                Double.parseDouble(transaction2.getAverageBankBalance()),
                Double.parseDouble(transaction2.getTimesOfOverdraft()),
                Double.parseDouble(transaction2.getCreditCardAge()),
                Double.parseDouble(transaction2.getDeductedAmount()),
                Double.parseDouble(transaction2.getLocationOfCCUsed()),
                Double.parseDouble(transaction2.getTimeOfTheCCUsedWithRespectToLocation()),
                Double.parseDouble(transaction2.getAverageDailyOverDraft()),
                Double.parseDouble(transaction2.getOverDraftToday()),
                Double.parseDouble(transaction2.getAmountOfTransaction()),
        });
    }

    public static double distance(Double[] a, Double[] b) {
        Double diff_square_sum = 0.0;
        for (int i = 0; i < a.length; i++) {
            diff_square_sum += (a[i] - b[i]) * (a[i] - b[i]);
        }
        return Math.sqrt(diff_square_sum);
    }


    public static String padLeftSpaces(String str, int n) {
        return String.format("%1$" + n + "s", str);
    }

}
