package org.alallah.ga;

public class Detection {
    public static float[] ccfreq(Transaction txn) {
        float[] res = new float[2];

        res[0] = 0;
        res[1] = 0;


        float ccfreq = Float.valueOf(txn.getAverageBankBalance()) / Float.valueOf(txn.getDeductedAmount());

        if (ccfreq > 0.2) {
            if (Float.valueOf(txn.getLocationOfCCUsed()) > (5 * ccfreq)) {
                res[0] = 1;
                res[1] = (Float.valueOf(txn.getLocationOfCCUsed()) * ccfreq);
            }
        }

        if (res[0] < 1) {
            res[1] = (float) ccfreq;
        }
        return res;
    }


    public static float[] ccloc(Transaction txn) {
        float[] res = new float[2];

        res[0] = 0;
        res[1] = 0;
        int loc = Integer.valueOf(txn.getTimeOfTheCCUsedWithRespectToLocation());
        if ((loc <= 5) && (Integer.valueOf(txn.getAverageDailyOverDraft()) > (2 * loc))) {
            res[0] = 1;
            res[1] = (Float.valueOf(loc) / Float.valueOf(txn.getAverageDailyOverDraft()));
        }
        if (res[0] < 1) {
            res[1] = (float) 0.01;
        }

        return res;
    }

    public static float[] ccod(Transaction txn) {
        float[] res = new float[2];
        res[0] = 0;
        res[1] = 0;
        float od = Float.valueOf(txn.getCreditCardAge()) / Float.valueOf(txn.getAverageBankBalance());
        if (od <= 0.2) {
            if (Float.valueOf(txn.getOverDraftToday()) >= 1) {
                res[0] = 1;
                res[1] = (Float.valueOf(txn.getOverDraftToday()) * od);
            }
        }
        if (res[0] < 1) {
            res[1] = (float) od;
        }
        //	Syst 8ghjghjghjhgem.out.println("  "+od+"   "+res[0]+"   "+res[1]);
        return res;
    }


    public static float[] ccbb(Transaction txn) {
        float[] res = new float[2];
        res[0] = 0;
        res[1] = 0;
        float bb = Float.valueOf(txn.getCurrentBalance()) / Float.valueOf(txn.getTimesOfOverdraft());
        if (bb <= 0.25) {
            res[0] = 1;
            res[1] = (Float.valueOf(2) * bb);
        }
        if (res[0] < 1) {
            res[1] = (float) bb;
        }

        return res;
    }


    public static float[] ccds(Transaction txn) {
        float[] res = new float[2];

        res[0] = 0;
        res[1] = 0;
        float mon = Float.valueOf(txn.getDeductedAmount()) / 30;
        float bal = 100000 - Float.valueOf(txn.getTimesOfOverdraft());
        float tot = mon * bal;
        float ds = tot / Float.valueOf(txn.getDeductedAmount());
        if ((10 * ds) < Float.valueOf(txn.getAmountOfTransaction())) {
            res[0] = 1;
            if (Float.valueOf(txn.getAmountOfTransaction()) > 0)
                res[1] = (Float.valueOf(txn.getAmountOfTransaction()) / (10 * ds));
            else
                res[1] = (float) 0.0;

        }
        if (res[0] < 1) {
            res[1] = (float) 0.01;
        }
        //	System.out.println("  "+ds+"   "+res[0]+"   "+res[1]);
        return res;
    }
}
