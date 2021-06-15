package com.devsou.formation.springbatch;

import java.util.StringTokenizer;

public class Main {

    public static void main(String ...strings){

        String maChaine = "1214545;ahoefdhazeohf;45646464";

        StringTokenizer tokenizer = new StringTokenizer(maChaine, ";");
        while (tokenizer.hasMoreTokens()){
            System.out.println(tokenizer.nextToken());
        }
    }
}
