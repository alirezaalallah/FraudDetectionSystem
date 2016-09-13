package org.alallah.ga;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class Transaction {

    private String customerId;
    private String authenticationType;
    private String currentBalance;
    private String averageBankBalance;
    private String timesOfOverdraft;
    private String creditCardAge;
    private String deductedAmount;
    private String locationOfCCUsed;
    private String timeOfTheCCUsedWithRespectToLocation;
    private String averageDailyOverDraft;
    private String overDraftToday;
    private String amountOfTransaction;
    private String creditCardType;
    private String theTimeOfUsingCreditCard;
    private String cardHolderIncome;
    private String cardHolderAge;
    private String cardHolderPosition;
    private String cardHolderProfession;
    private String cardHolderMartialStatus;
    private String averageDailySpending;
    private String cardFrequency;

    List<Suspect> suspectList = new ArrayList<>();

    public void addSuspected(Suspect suspect) {
        suspectList.add(suspect);
    }

    public List<Suspect> getSuspectList() {
        return suspectList;
    }

    private Float fitness = 0F;

    public Float getFitness() {
        return fitness;
    }

    public String description() {
        StringJoiner stringJoiner = new StringJoiner(",");

        stringJoiner.add("FITNESS:[" + fitness.toString() + "]");

        for (Suspect s : suspectList) {
            stringJoiner.add(s.getSuspectType().name());
        }

        return stringJoiner.toString();
    }

    public void setFitness(Float fitness) {
        this.fitness = fitness;
    }

    public String getOverDraftToday() {
        return overDraftToday;
    }

    public void setOverDraftToday(String overDraftToday) {
        this.overDraftToday = overDraftToday;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getAuthenticationType() {
        return authenticationType;
    }

    public void setAuthenticationType(String authenticationType) {
        this.authenticationType = authenticationType;
    }

    public String getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(String currentBalance) {
        this.currentBalance = currentBalance;
    }

    public String getAverageBankBalance() {
        return averageBankBalance;
    }

    public void setAverageBankBalance(String averageBankBalance) {
        this.averageBankBalance = averageBankBalance;
    }

    public String getTimesOfOverdraft() {
        return timesOfOverdraft;
    }

    public void setTimesOfOverdraft(String timesOfOverdraft) {
        this.timesOfOverdraft = timesOfOverdraft;
    }

    public String getCreditCardAge() {
        return creditCardAge;
    }

    public void setCreditCardAge(String creditCardAge) {
        this.creditCardAge = creditCardAge;
    }

    public String getDeductedAmount() {
        return deductedAmount;
    }

    public void setDeductedAmount(String deductedAmount) {
        this.deductedAmount = deductedAmount;
    }

    public String getLocationOfCCUsed() {
        return locationOfCCUsed;
    }

    public void setLocationOfCCUsed(String locationOfCCUsed) {
        this.locationOfCCUsed = locationOfCCUsed;
    }

    public String getTimeOfTheCCUsedWithRespectToLocation() {
        return timeOfTheCCUsedWithRespectToLocation;
    }

    public void setTimeOfTheCCUsedWithRespectToLocation(String timeOfTheCCUsedWithRespectToLocation) {
        this.timeOfTheCCUsedWithRespectToLocation = timeOfTheCCUsedWithRespectToLocation;
    }

    public String getAverageDailyOverDraft() {
        return averageDailyOverDraft;
    }

    public void setAverageDailyOverDraft(String averageDailyOverDraft) {
        this.averageDailyOverDraft = averageDailyOverDraft;
    }

    public String getAmountOfTransaction() {
        return amountOfTransaction;
    }

    public void setAmountOfTransaction(String amountOfTransaction) {
        this.amountOfTransaction = amountOfTransaction;
    }

    public String getCreditCardType() {
        return creditCardType;
    }

    public void setCreditCardType(String creditCardType) {
        this.creditCardType = creditCardType;
    }

    public String getTheTimeOfUsingCreditCard() {
        return theTimeOfUsingCreditCard;
    }

    public void setTheTimeOfUsingCreditCard(String theTimeOfUsingCreditCard) {
        this.theTimeOfUsingCreditCard = theTimeOfUsingCreditCard;
    }

    public String getCardHolderIncome() {
        return cardHolderIncome;
    }

    public void setCardHolderIncome(String cardHolderIncome) {
        this.cardHolderIncome = cardHolderIncome;
    }

    public String getCardHolderAge() {
        return cardHolderAge;
    }

    public void setCardHolderAge(String cardHolderAge) {
        this.cardHolderAge = cardHolderAge;
    }

    public String getCardHolderPosition() {
        return cardHolderPosition;
    }

    public void setCardHolderPosition(String cardHolderPosition) {
        this.cardHolderPosition = cardHolderPosition;
    }

    public String getCardHolderProfession() {
        return cardHolderProfession;
    }

    public void setCardHolderProfession(String cardHolderProfession) {
        this.cardHolderProfession = cardHolderProfession;
    }

    public String getCardHolderMartialStatus() {
        return cardHolderMartialStatus;
    }

    public void setCardHolderMartialStatus(String cardHolderMartialStatus) {
        this.cardHolderMartialStatus = cardHolderMartialStatus;
    }

    public String getAverageDailySpending() {
        return averageDailySpending;
    }

    public void setAverageDailySpending(String averageDailySpending) {
        this.averageDailySpending = averageDailySpending;
    }

    public String getCardFrequency() {
        return cardFrequency;
    }

    public void setCardFrequency(String cardFrequency) {
        this.cardFrequency = cardFrequency;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Transaction transaction = new Transaction();

        public Builder from(String line) {

            String[] data = line.split(",");
            this.transaction.customerId = data[0];
            this.transaction.authenticationType = data[1];
            this.transaction.currentBalance = data[2];
            this.transaction.averageBankBalance = data[3];
            this.transaction.timesOfOverdraft = data[4];
            this.transaction.creditCardAge = data[5];
            this.transaction.deductedAmount = data[6];
            this.transaction.locationOfCCUsed = data[7];
            this.transaction.timeOfTheCCUsedWithRespectToLocation = data[8];
            this.transaction.averageDailyOverDraft = data[9];
            this.transaction.overDraftToday = data[10];
            this.transaction.amountOfTransaction = data[11];

            this.transaction.creditCardType = "0";
            this.transaction.theTimeOfUsingCreditCard = "0";
            this.transaction.cardHolderIncome = "0";
            this.transaction.cardHolderAge = "0";
            this.transaction.cardHolderPosition = "0";
            this.transaction.cardHolderProfession = "0";
            this.transaction.cardHolderMartialStatus = "0";
            this.transaction.averageDailySpending = "0";
            this.transaction.cardFrequency = "0";

            return this;
        }

        public Builder customerId(String customerId) {
            transaction.customerId = customerId;
            return this;
        }

        public Builder authenticationType(String authenticationType) {
            transaction.authenticationType = authenticationType;
            return this;
        }

        public Builder currentBalance(String currentBalance) {
            transaction.currentBalance = currentBalance;
            return this;
        }

        public Builder averageBankBalance(String averageBankBalance) {
            transaction.averageBankBalance = averageBankBalance;
            return this;
        }

        public Builder timesOfOverdraft(String timesOfOverdraft) {
            transaction.timesOfOverdraft = timesOfOverdraft;
            return this;
        }

        public Builder creditCardAge(String creditCardAge) {
            transaction.creditCardAge = creditCardAge;
            return this;
        }

        public Builder deductedAmount(String deductedAmount) {
            transaction.deductedAmount = deductedAmount;
            return this;
        }

        public Builder locationOfCCUsed(String locationOfCCUsed) {
            transaction.locationOfCCUsed = locationOfCCUsed;
            return this;
        }

        public Builder overDraftToday(String s) {
            transaction.overDraftToday = s;
            return this;
        }

        public Builder timeOfTheCCUsedWithRespectToLocation(String timeOfTheCCUsedWithRespectToLocation) {
            transaction.timeOfTheCCUsedWithRespectToLocation = timeOfTheCCUsedWithRespectToLocation;
            return this;
        }

        public Builder averageDailyOverDraft(String averageDailyOverDraft) {
            transaction.averageDailyOverDraft = averageDailyOverDraft;
            return this;
        }

        public Builder amountOfTransaction(String amountOfTransaction) {
            transaction.amountOfTransaction = amountOfTransaction;
            return this;
        }

        public Builder creditCardType(String creditCardType) {
            transaction.creditCardType = creditCardType;
            return this;
        }

        public Builder theTimeOfUsingCreditCard(String theTimeOfUsingCreditCard) {
            transaction.theTimeOfUsingCreditCard = theTimeOfUsingCreditCard;
            return this;
        }

        public Builder cardHolderIncome(String cardHolderIncome) {
            transaction.cardHolderIncome = cardHolderIncome;
            return this;
        }

        public Builder cardHolderAge(String cardHolderAge) {
            transaction.cardHolderAge = cardHolderAge;
            return this;
        }

        public Builder cardHolderPosition(String cardHolderPosition) {
            transaction.cardHolderPosition = cardHolderPosition;
            return this;
        }

        public Builder cardHolderProfession(String cardHolderProfession) {
            transaction.cardHolderProfession = cardHolderProfession;
            return this;
        }

        public Builder cardHolderMartialStatus(String cardHolderMartialStatus) {
            transaction.cardHolderMartialStatus = cardHolderMartialStatus;
            return this;
        }

        public Builder averageDailySpending(String averageDailySpending) {
            transaction.averageDailySpending = averageDailySpending;
            return this;
        }

        public Builder cardFrequency(String cardFrequency) {
            transaction.cardFrequency = cardFrequency;
            return this;
        }

        public Transaction build() {

            transaction.fitness = CalculatorUtil.calculateScore(transaction);
            return transaction;
        }

    }


}
