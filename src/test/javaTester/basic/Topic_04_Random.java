package javaTester.basic;

import java.util.Random;

public class Topic_04_Random {
    public void main(String[] args) {
        Random rand = new Random();
      rand.nextInt(99999);
      System.out.println("mail" +rand.nextInt(99999)+"@mailinator.com");
    }
}
