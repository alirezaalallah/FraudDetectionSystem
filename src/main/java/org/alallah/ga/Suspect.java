package org.alallah.ga;

public class Suspect {
    private SuspectType suspectType;
    private Float value;

    public SuspectType getSuspectType() {
        return suspectType;
    }

    public void setSuspectType(SuspectType suspectType) {
        this.suspectType = suspectType;
    }

    public Float getValue() {
        return value;
    }

    public void setValue(Float value) {
        this.value = value;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Suspect suspect = new Suspect();

        public Builder suspectType(SuspectType suspectType) {
            suspect.suspectType = suspectType;
            return this;
        }

        public Builder value(Float value) {
            suspect.value = value;
            return this;
        }

        public Suspect build() {
            return this.suspect;
        }
    }
}
