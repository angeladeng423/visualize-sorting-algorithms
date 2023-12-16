package assignment1.assignment1;

import javafx.application.Platform;

import java.util.Arrays;

public class MergeSort implements SortingStrategy {
    int[] list;
    SortingHubController controller;

    public MergeSort(SortingHubController test, int[] list) {
        this.list = list;
        this.controller = test;
    }

    @Override
    public void run() {
        this.sort(list);
    }

    @Override
    public void sort(int[] numbers) {
        sort(numbers, 0, numbers.length - 1);

        Platform.runLater(() -> controller.updateGraph(numbers));

        // delays the thread for a number of milliseconds
        try {
            Thread.sleep(10);
        } catch (InterruptedException error) {
            error.printStackTrace();
        }
    }

    public void sort(int[] numbers, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;
            sort(numbers, left, mid);
            sort(numbers, mid + 1, right);
            merge(numbers, left, mid, right);

            Platform.runLater(() -> controller.updateGraph(numbers));

            // delays the thread for a number of milliseconds
            try {
                Thread.sleep(10);
            } catch (InterruptedException error) {
                error.printStackTrace();
            }
        }
    }

    // merge method
    public void merge(int[] numbers, int left, int mid, int right) {
        // declares integers
        int i = left;
        int j = mid + 1;

        // begin merging the arrays
        while (i <= mid && j <= right) {
            // if the element on the left is in the correct place
            // iterate the starting value
            if (numbers[i] <= numbers[j]) {
                i++;
            } else {
                // otherwise, store the value in a tempValue and store the value in an index
                int tempValue = numbers[j];
                int index = j;

                // shift all the elements
                while (index != i) {
                    numbers[index] = numbers[index - 1];
                    index--;
                    // updates the graph each time any index is swapped
                    Platform.runLater(() -> controller.updateGraph(numbers));

                    // delays the thread for a number of milliseconds
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException error) {
                        error.printStackTrace();
                    }
                }

                // swap the values
                numbers[i] = tempValue;

                // iterate values
                i++;
                mid++;
                j++;
            }
        }
    }
}