package assignment1.assignment1;

public interface SortingStrategy extends Runnable {
    default void sort(int[] numbers) { }
}
