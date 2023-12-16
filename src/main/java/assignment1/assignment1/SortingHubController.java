package assignment1.assignment1;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.ResourceBundle;

public class SortingHubController implements Initializable {
    /*
    - uses javafx.scene.shape.Rectangle objects
    - program starts w/ initial value array of 64
    - each time reset is clicked, 64 new shuffled bars are displayed, algorithm is set to merge sort, slier set to 64
    - array of rectangles ranges from 32 to 128 integer values
    - height of red bars should represent the array values & scale proportionally to stage height

    CONTROLLER COMPONENT
    - 4 classes needed for this component
    (SortingStrategy interface, SortingHubController, MergeSort, SelectionSort)
    - SortingStrategy interface extends Runnable Java interface
    */

    // sorting hub controller uses sorting strategy interface to define sorting method attribute to call sorting algorithm

    @FXML
    private Button sortButton;

    @FXML
    private Pane pane;

    @FXML
    private ComboBox<String> comboBox;

    @FXML
    private Label tracker;

    @FXML
    private Slider slider;

    @FXML
    private Button resetButton;

    // stores the slider value
    int sliderValue;

    // stores the values of the heights for each rectangle
    int[] intArray;

    @FXML
    void resetPressed(){
        // resets the slider value, the tracker, and the comboBox
        tracker.setText("64");
        slider.setValue(64);
        comboBox.setValue("MergeSort");
        intArray = updateArray(64);
        updateGraph(intArray);
    }

    @FXML
    void setSortStrategy(){
        // sets the sort strategy

        if (comboBox.getSelectionModel().getSelectedItem().toString() == "MergeSort"){
            MergeSort test = new MergeSort(this, intArray);
            Thread thread1 = new Thread(test);
            thread1.start();
        }

        if (comboBox.getSelectionModel().getSelectedItem().toString() == "SelectionSort"){
            SelectionSort test = new SelectionSort(this,intArray);
            Thread thread1 = new Thread(test);
            thread1.start();
        }
    }

    void updateGraph(int[] data){
        // creates an array equal to entry array
        int[] newArray = data;

        // clears the pane before updating
        pane.getChildren().clear();

        // creates necessary variables for calculations
        double x = (int) slider.getValue();

        // calculates width of rectangle
        double widthOfRectangles = (763.0 - x) / x;

        // calculates the height ratio
        double heightCal = (300.0/slider.getValue());

        // loops through to create each rectangle
        for (int i = 0; i < x; i++){
            Rectangle rectangle = new Rectangle(i+i*widthOfRectangles,300-(newArray[i]*heightCal+1),widthOfRectangles-1,newArray[i]*heightCal);
            rectangle.setFill(Color.RED);
            pane.getChildren().add(rectangle);
        }
    }

    int[] updateArray(int entryValue){
        // creates array based on the slider value
        int[] testArray = new int[entryValue];

        // fills the array w/ empty values
        for(int i = 0; i < entryValue; i++){
            testArray[i] = -1;
        }

        // populates the array
        for (int i = 0; i < entryValue; i++){
            // determines a value according to math.random in between 0 and 63
            int value = (int) (Math.random() * entryValue-1);

            // if this value of the array is occupied
            while(testArray[value] != -1){
                // increment value
                value++;

                // if the value increments till the slider value
                if (value == entryValue){

                    // sets value equal to 0
                    value = 0;
                }
            }

            testArray[value] = i;
        }

        // once the array is filled, returns the array
        return testArray;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // listens to the slider value
        slider.valueProperty().addListener(new ChangeListener<Number>(){
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                sliderValue = (int) slider.getValue();

                // updates label text
                tracker.setText(String.valueOf(sliderValue));

                // calls the updateArray function
                intArray = updateArray(sliderValue);

                // calls the graph update
                updateGraph(intArray);
            }
        });

        // creates the text for the combobox
        String[] items = {"MergeSort", "SelectionSort"};
        comboBox.getItems().addAll(items);
        comboBox.setValue("MergeSort");

        //sets the default rectangles
        intArray = updateArray(64);
        updateGraph(intArray);
    }
}