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

/**
 * This calculator does all necessary functions with GUI format
 * Also, added extra features such as MC, MR, M+, M-, Math.pow and Math.sqrt
 * 
 * @Author: Anaberdi Meredov
 * @Date: 04.28.22
 * @Code: From https://www3.ntu.edu.sg/home/ehchua/programming/java/Javafx1_intro.html
 * @github: https://github.com/sherlock59/CalculatorJavaFX.git
*/
public class CalculatorFX extends Application {
	
	/**
	 * displays text field
	 */
private TextField tfDisplay;

   /**
    * 16 buttons 
    */
private Button[] btns; 

   /**
    * labels of 16 buttons 
    */
private String[] btnLabels = { 
      "7", "8", "9", "+",
      "4", "5", "6", "-",
      "1", "2", "3", "*",
      "C", "0", "=", "/",
      "^", "SPC", "SQRT", "POW",
      "MC", "MR", "M+", "M-"
   };

   /**
    * Result of computation
    */
private int result = 0;

   /**
    * Number in memory 
    */
private int memoryNum = 0;

   /**
    * input as String 
    */
private String inStr = "0";

   /**
    *  Previous operator: " "(nothing), "+", "-", "*", "/", "=", "^",
    * "SPC", "POW", "SQRT", "MR", "MC", "M+", "M-" 
    */
private String lastOperator = " ";

   /**
    * Event handler for all the 16 Buttons
    */
EventHandler handler = evt -> {
      String currentBtnLabel = ((Button)evt.getSource()).getText();
      
      /**
       * currentNum is used for our single digit operations
       */
      int currentNum = Integer.parseInt(tfDisplay.getText());
      String space = null;
      int rand = value(0-9);
      switch (currentBtnLabel) {
   
      /**
       * Number of buttons
       */
         case "0": case "1": case "2": case "3": case "4":
         case "5": case "6": case "7": case "8": case "9":
        	 
        	 /**
        	  * no leading zero
        	  */
            if (inStr.equals("0")) {
               inStr = currentBtnLabel; 
            } else {
               inStr += currentBtnLabel;
            }
            tfDisplay.setText(inStr);
            
            /**
             * Clearing buffer if last operator is '='
             */
            if (lastOperator == "=") {
               result = 0;
               lastOperator = "SPC";
            }
            break;
            
            /**
             * Space button 
             */
         case "SPC":
        	 space = "";
        	 Button btns;
        	 inStr = String.valueOf(space);
        	 break;

        	 /**
        	  * SQRT operation stays under the switch because it only uses one number
        	  */
         case "SQRT":
        	 currentNum = (int) Math.sqrt(currentNum);
        	 tfDisplay.setText(currentNum + "");
        	 inStr = String.valueOf(currentNum);
        	 break;
        	
        	 /**
        	  * Memory clear
        	  */
         case "MC":
        	 memoryNum = 0;
             break;
        
             /**
              * memory recall
              */
         case "MR":
        	 inStr = String.valueOf(memoryNum);
        	 tfDisplay.setText(memoryNum + "");
        	 break;
        	    
        	 /**
        	  * Memory add
        	  */
         case "M+":
        	 memoryNum += result;
        	 break;
        	 /**
        	  * memory subtract
        	  */
         case "M-":
        	 memoryNum -= result;
        	 break;

        	 /**
        	  * Clear button
        	  */
         case "C":
            result = 0;
            inStr = "0";
            lastOperator = " ";
            tfDisplay.setText("0");
            break; 

            /**
             * default operator for buttons that require 2 numbers to run 
             */
         default:
        	 compute();
        	 lastOperator = currentBtnLabel;
        	 break; 
      }
   };

   /**
    * User pushes '+', '-', '*', '/', 'POW', or '=' button.
    * Perform computation on the previous result and the current input number.
    * based on previous number 
    */
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
    	  
      }
    	  /**
    	   * Keeps the result for the next operation
    	   */
      tfDisplay.setText(result + "");
   }

   /**
    * @param i
    * @return
    */
private int value(int i) {
	return 0;
}

   /**
    * Setting up the UI
    */
@Override
   public void start(Stage primaryStage) {

	/**
	 * Setting up the Display TextField
	 */
      tfDisplay = new TextField("0");
      tfDisplay.setEditable(false);
      tfDisplay.setAlignment(Pos.CENTER_RIGHT);

      /**
       * Setting up GridPane for 4 columns of buttons
       */
      int numCols = 4;
      GridPane paneButton = new GridPane();
      
      /**
       * top, right, bottom, and left
       */
      paneButton.setPadding(new Insets(15, 0, 15, 0));
      
      /**
       * Vertical gap between nodes
       */
      paneButton.setVgap(5);
      
      /**
       * Horizontal gap between nodes
       */
      paneButton.setHgap(5);

      /**
       * Setting up 4 columns of equal width, fill parent
       */
      ColumnConstraints[] columns = new ColumnConstraints[numCols];
      for (int i = 0; i < numCols; ++i) {
         columns[i] = new ColumnConstraints();
         
         /**
          * Allows column to grow
          */
         columns[i].setHgrow(Priority.ALWAYS); 
         
         /**
          * Asks nodes to fill space for column
          */
         columns[i].setFillWidth(true);
         paneButton.getColumnConstraints().add(columns[i]);
      }

      /**
       * Setting up 24 buttons and adding to GridPane & event handler
       */
      btns = new Button[24];
      for (int i = 0; i < btns.length; ++i) {
         btns[i] = new Button(btnLabels[i]);
         btns[i].setOnAction(handler);
         btns[i].setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE); 
         paneButton.add(btns[i], i % numCols, i / numCols);
      }

      /**
       * Setting up the scene graph rooted at a BorderPane (5 zones)
       */
      BorderPane root = new BorderPane();
      
      /**
       * top, right , bottom, and left
       */
      root.setPadding(new Insets(15, 15, 15, 15)); 
      
      /**
       * top zone contains the text field
       */
      root.setTop(tfDisplay);
      
      /**
       * Center zone contains the GridPane of Buttons
       */
      root.setCenter(paneButton);

      /**
       * Setting up scene and stage 
       */
      primaryStage.setScene(new Scene(root, 300, 300));
      primaryStage.setTitle("JavaFX Calculator");
      primaryStage.show();
   }

   /**
    * @param main args
    */
public static void main(String[] args) {
      launch(args);
   }
}
