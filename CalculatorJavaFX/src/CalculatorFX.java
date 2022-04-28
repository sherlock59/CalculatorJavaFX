/**
 * This calculator does all necessary functions with GUI format
 * 
 * @Author: Annaberdi Meredov
 * @Date: 04.27.22
 * @Code From https://www3.ntu.edu.sg/home/ehchua/programming/java/Javafx1_intro.html
 */

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import java.lang.Math;

public class CalculatorFX extends Application {
   private TextField tfDisplay;    // display textfield
   private Button[] btns;          // 16 buttons
   private String[] btnLabels = {  // Labels of 16 buttons
      "7", "8", "9", "+",
      "4", "5", "6", "-",
      "1", "2", "3", "*",
      "C", "0", "=", "/",
      "^2", "^3", "SQRT", "POW",
      "MC", "MR", "M+", "M-"
   };
   // For computation
   private int result = 0;      // Result of computation
   private int memoryNum = 0;	// Number in memory
   private String inStr = "0";  // Input number as String
   /* Previous operator: " "(nothing), "+", "-", "*", "/", "=", "^2",
    * "^3", "POW", "SQRT", "MR", "MC", "M+", "M-" 
    */
   private String lastOperator = " ";

   // Event handler for all the 16 Buttons
   EventHandler handler = evt -> {
      String currentBtnLabel = ((Button)evt.getSource()).getText();
      
      //currentNum is used for our single digit operations
      int currentNum = Integer.parseInt(tfDisplay.getText());
      switch (currentBtnLabel) {
         // Number buttons
         case "0": case "1": case "2": case "3": case "4":
         case "5": case "6": case "7": case "8": case "9":
          
            if (inStr.equals("0")) {
               inStr = currentBtnLabel;  // no leading zero
            } else {
               inStr += currentBtnLabel; // append input digit
            }
            tfDisplay.setText(inStr);
            // Clear buffer if last operator is '='
            if (lastOperator == "=") {
               result = 0;
               lastOperator = " ";
            }
            break;
            
         //^2 operation stays under the switch because it only uses one number  
         case "^2":
        	 currentNum = (int) Math.pow(currentNum, 2);
        	 tfDisplay.setText(currentNum + "");
        	 inStr = String.valueOf(currentNum);
        	 break;
        
         //^3 operation stays under the switch because it only uses one number 
         case "^3":
        	 currentNum = (int) Math.pow(currentNum, 3);
        	 tfDisplay.setText(currentNum + "");
        	 inStr = String.valueOf(currentNum);
        	 break;
        	
         //SQRT operation stays under the switch because it only uses one number
         case "SQRT":
        	 currentNum = (int) Math.sqrt(currentNum);
        	 tfDisplay.setText(currentNum + "");
        	 inStr = String.valueOf(currentNum);
        	 break;
        	 
         //Memory clear	 
         case "MC":
        	 memoryNum = 0;
             break;
        
         //Memory recall	
         case "MR":
        	 tfDisplay.setText(memoryNum + "");
        	 inStr = String.valueOf(memoryNum);
        	 break;
        	 
         //Memory add	 
         case "M+":
        	 memoryNum += result;
        	 break;
        	 
         //Memory subtract	 
         case "M-":
        	 memoryNum -= result;
        	 break;
    
         // Clear button
         case "C":
            result = 0;
            inStr = "0";
            lastOperator = " ";
            tfDisplay.setText("0");
            break; 
            
         // default operator for buttons that require 2 numbers to run   
         default:
        	 compute();
        	 lastOperator = currentBtnLabel;
        	 break; 
      }
   };

   // User pushes '+', '-', '*', '/', 'POW', or '=' button.
   // Perform computation on the previous result and the current input number,
   // based on the previous operator.
   private void compute() {
      int inNum = Integer.parseInt(inStr);
      inStr = "0";
      if (lastOperator == " ") {
         result = inNum;
      } else if (lastOperator == "+") {
         result += inNum;
      } else if (lastOperator == "-") {
         result -= inNum;
      } else if (lastOperator == "*") {
         result *= inNum;
      } else if (lastOperator == "/") {
         result /= inNum; 
      } else if (lastOperator == "POW") {
         result = (int) Math.pow(result,inNum); 
      }else if (lastOperator == "=") {
         // Keep the result for the next operation
      } 
      tfDisplay.setText(result + "");
   }

   // Setup the UI
   @Override
   public void start(Stage primaryStage) {
      // Setup the Display TextField
      tfDisplay = new TextField("0");
      tfDisplay.setEditable(false);
      tfDisplay.setAlignment(Pos.CENTER_RIGHT);

      // Setup a GridPane for 4 columns and 6 rows of Buttons
      int numCols = 4;
      int numRows = 6;
      GridPane paneButton = new GridPane();
      paneButton.setPadding(new Insets(15, 0, 15, 0));  // top, right, bottom, left
      paneButton.setVgap(5);  // Vertical gap between nodes
      paneButton.setHgap(5);  // Horizontal gap between nodes
      // Setup 4 columns of equal width, fill parent
      ColumnConstraints[] columns = new ColumnConstraints[numCols];
      for (int i = 0; i < numCols; ++i) {
         columns[i] = new ColumnConstraints();
         columns[i].setHgrow(Priority.ALWAYS) ;  // Allow column to grow
         columns[i].setFillWidth(true);  // Ask nodes to fill space for column
         paneButton.getColumnConstraints().add(columns[i]);
      }

      // Setup 24 Buttons and add to GridPane; and event handler
      btns = new Button[24];
      for (int i = 0; i < btns.length; ++i) {
         btns[i] = new Button(btnLabels[i]);
         btns[i].setOnAction(handler);  // Register event handler
         btns[i].setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);  // full-width
         paneButton.add(btns[i], i % numCols, i / numCols);  // control, col, row
      }

      // Setup up the scene graph rooted at a BorderPane (of 5 zones)
      BorderPane root = new BorderPane();
      root.setPadding(new Insets(15, 15, 15, 15));  // top, right, bottom, left
      root.setTop(tfDisplay);     // Top zone contains the TextField
      root.setCenter(paneButton); // Center zone contains the GridPane of Buttons

      // Set up scene and stage
      primaryStage.setScene(new Scene(root, 300, 300));
      primaryStage.setTitle("JavaFX Calculator");
      primaryStage.show();
   }

   public static void main(String[] args) {
      launch(args);
   }
}