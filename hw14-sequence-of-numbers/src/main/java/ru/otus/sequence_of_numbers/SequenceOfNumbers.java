package ru.otus.sequence_of_numbers;

public class SequenceOfNumbers {
   private boolean flag;
   private static final String LEFT = "LEFT";
   private static final String RIGHT = "RIGHT";
   private static final SequenceOfNumbers sequence = new SequenceOfNumbers();

   public static void main(String[] args) throws InterruptedException {
      Thread left = new Thread(() -> go());
      Thread right = new Thread(() -> go());
      left.setName(LEFT);
      right.setName(RIGHT);
      left.start();
      right.start();
      left.join();
      right.join();
   }

   private static void go() {
      for (int i = 1; i < 10; i++) {
         try {
            step(Thread.currentThread().getName(), i);
         } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
         }
      }
      for (int j = 10; j > 0; j--) {
         try {
            step(Thread.currentThread().getName(), j);
         } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
         }
      }
   }

   private static void step(String step, int stepNumber) throws InterruptedException {
      synchronized (sequence) {
         while (whichStep(step)) {
            sequence.wait();
         }
         Thread.sleep(500);
         System.out.println(Thread.currentThread().getName() + ": " + stepNumber);
         if (step.equals(LEFT))
            sequence.flag = true;
         else if (step.equals(RIGHT))
            sequence.flag = false;
         sequence.notifyAll();
      }
   }

   private static boolean whichStep(String threadName) {
      return threadName.equals(LEFT) && sequence.flag || threadName.equals(RIGHT) && !sequence.flag;
   }
}
