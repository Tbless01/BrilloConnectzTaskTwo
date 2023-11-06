package src.TaskQuestion2;

import java.security.SecureRandom;
import java.time.LocalTime;

public class PancakeShopNonConcurrent {
    public static void main(String[] args) {
        for (int slot = 1; slot <= 2; slot++) {
            int shopkeeperPancakes = 0;
            int userPancakes = 0;
            int ordersNotMet = 0;
            int pancakesEatenByUsers = 0;
            int pancakeOrdersNotMet = 0;

            int shopkeeperMaxPancakes = 12;
            int[] userPancakeRequests = new int[3];
            SecureRandom random = new SecureRandom();

            LocalTime startTime = LocalTime.now();
            LocalTime endTime = startTime.plusSeconds(30);

            System.out.println("Slot " + slot + " start time: " + startTime);

            // Shopkeeper makes pancakes
            if (shopkeeperMaxPancakes > 0) {
                shopkeeperPancakes = random.nextInt(shopkeeperMaxPancakes + 1);
                shopkeeperMaxPancakes -= shopkeeperPancakes;
            }

            for (int userOrderCount = 0; userOrderCount < 3; userOrderCount++) {
                userPancakeRequests[userOrderCount] = random.nextInt(6);
                pancakesEatenByUsers += userPancakeRequests[userOrderCount];
                userPancakes += userPancakeRequests[userOrderCount];
            }


            if (shopkeeperPancakes >= pancakesEatenByUsers) {
                shopkeeperPancakes -= pancakesEatenByUsers;
            } else {
                pancakeOrdersNotMet += (pancakesEatenByUsers - shopkeeperPancakes);
                shopkeeperPancakes = 0;
            }
            ordersNotMet = pancakesEatenByUsers - shopkeeperPancakes;
            if (ordersNotMet < 0) {
                ordersNotMet = 0;
            }

            System.out.println("Slot " + slot + " end time: " + endTime);
            System.out.println("Pancakes made by the shopkeeper: " + (shopkeeperPancakes));
            System.out.println("Pancakes eaten by the 3 users: " + userPancakes);
            System.out.println("Shopkeeper met the needs of the users: " + (shopkeeperPancakes >= pancakesEatenByUsers));
            System.out.println("Pancakes wasted: " + pancakeOrdersNotMet);
            System.out.println("Pancake orders not met: " + (ordersNotMet));
            System.out.println();
        }
    }
}
