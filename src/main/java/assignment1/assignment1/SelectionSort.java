package assignment1.assignment1;

import javafx.application.Platform;

public class SelectionSort implements SortingStrategy{
    // take unsorted array of integers and returns a sorted version of it
    // maintain a reference object of type sortinghubcontroller
    int[] list;
    SortingHubController controller;

    // constructor to maintain reference to controller
    public SelectionSort(SortingHubController test, int[] list){
        this.list = list;
        this.controller = test;
    }

    // run function to allow multithreading
    @Override
    public void run() {
        this.sort(list);
    }

    @Override
    public void sort(int[] numbers) {
        int size = numbers.length;
        // oi = outerIndex
        // ii = innerIndex

        // travels through the array
        for (int oi = 0; oi < size - 1; oi++){
            // declares the index of the smallest element is the first element in the array
            int indexSmallest = oi;

            // iterates through the rest of the array
            for (int ii = oi+1; ii < size; ii++){
                // checks if the element at index ii is smaller than the current element stored at indexSmallest
                if(numbers[ii] < numbers[indexSmallest]){
                    // if the element is smaller, swaps the index
                    indexSmallest = ii;
                }
            }

            // if, at the end of checking, the indexSmallest is no longer equal to the first element
            if(indexSmallest != oi) {
                // swaps the elements
                int temp = numbers[oi];
                numbers[oi] = numbers[indexSmallest];
                numbers[indexSmallest] = temp;

                // updates the graph each time any index is swapped
                Platform.runLater(() -> controller.updateGraph(numbers));

                // delays the thread for a number of milliseconds
                try{
                    Thread.sleep(30);
                } catch (InterruptedException error){
                    error.printStackTrace();
                }
            }
        }
    }
}
