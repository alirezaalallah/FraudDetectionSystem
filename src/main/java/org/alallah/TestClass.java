package org.alallah;

import java.util.Arrays;

public class TestClass {

    public static void main(String[] args) {

        for (Integer i = 0; i < 50; i++) {
            System.out.println(String.format("%f      |%f,%f,%f,%f,%f"
                    ,getRand()
                    ,getRand()
                    ,getRand()
                    ,getRand()
                    ,getRand()
                    ,getRand()
                    ));
        }


    }


    private static Float getRand() {


        return Double.valueOf(Math.random()).floatValue() * 20;


    }
}
