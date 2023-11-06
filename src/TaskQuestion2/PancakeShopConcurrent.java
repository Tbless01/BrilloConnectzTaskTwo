package src.TaskQuestion2;

import java.security.SecureRandom;
import java.time.LocalTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class PancakeShopConcurrent {
    public static void main(String[] args) {
        int shopkeeperPancakes = 0;
        final int[] userPancakes = {0};
        final int[] pancakesEatenByUsers = {0};
        int pancakeOrdersNotMet = 0;
        SecureRandom random = new SecureRandom();

        ExecutorService executor = Executors.newFixedThreadPool(3);

        for (int slot = 1; slot <= 2; slot++) {
            int shopkeeperMaxPancakes = 12;
            int[] userPancakeRequests = new int[3];

            LocalTime startTime = LocalTime.now();
            LocalTime endTime = startTime.plusSeconds(30);

            System.out.println("Slot " + slot + " start time: " + startTime);

            shopkeeperPancakes = random.nextInt(shopkeeperMaxPancakes + 1);
            shopkeeperMaxPancakes -= shopkeeperPancakes;
            for (int userCount = 0; userCount < 3; userCount++) {
                final int index = userCount;
                executor.submit(() -> {
                    userPancakeRequests[index] = random.nextInt(6);
                    synchronized (PancakeShopConcurrent.class) {
                        pancakesEatenByUsers[0] += userPancakeRequests[index];
                        userPancakes[0] += userPancakeRequests[index];
                    }
                });
            }

            executor.shutdown();
            try {
                executor.awaitTermination(30, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (shopkeeperPancakes >= pancakesEatenByUsers[0]) {
                shopkeeperPancakes -= pancakesEatenByUsers[0];
            } else {
                pancakeOrdersNotMet += (pancakesEatenByUsers[0] - shopkeeperPancakes);
                shopkeeperPancakes = 0;
            }

            System.out.println("Slot " + slot + " end time: " + endTime);
            System.out.println("Pancakes made by the shopkeeper: " + (shopkeeperPancakes));
            System.out.println("Pancakes eaten by the 3 users: " + userPancakes[0]);
            System.out.println("Shopkeeper met the needs of the users: " + (shopkeeperPancakes >= pancakesEatenByUsers[0]));
            System.out.println("Pancakes wasted: " + pancakeOrdersNotMet);
            System.out.println("Pancake orders not met: " + (pancakesEatenByUsers[0] - shopkeeperPancakes));
            System.out.println();

            // Reset the executor for the next slot
            executor = Executors.newFixedThreadPool(3);
        }
    }
}
