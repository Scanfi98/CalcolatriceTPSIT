package sample;

import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;


public class Controller {

    private Calculator c;

    @FXML
    AnchorPane bgPane;

    @FXML
    JFXToggleButton calcToggle;

    @FXML
    TextField tfBar;

    @FXML
    GridPane grOpt;
    boolean visibleOp = false;

    @FXML
    GridPane grNum;
    boolean visibleNum = true;

    private int lastOp;

    /**
     * Scrive nella TextBox
     * @param event
     */
    @FXML
    public void writeContent(ActionEvent event){
        JFXButton bt = (JFXButton) event.getSource();
        lastOp = bt.getText().length();
        tfBar.setText(tfBar.getText() + bt.getText());
    }

    /**
     * Cancella l'ultimo elemento della TextBox
     */
    @FXML
    public void deleteTextBoxElement(){
        try{
            tfBar.setText(tfBar.getText().substring(0, tfBar.getText().length() - lastOp));
        } catch (Exception e){
            System.out.println("Blocco vuoto");
        }
    }

    /**
     * Cancella tutta la TextBob
     */
    @FXML
    public void deleteTextBoxContent(){
        try{
            tfBar.setText("");
        } catch (Exception e){
            System.out.println("Blocco vuoto");
        }
    }

    /**
     * Utilizzato per il cambio di pannello
     */
    @FXML
    public void changeState(){
        if(visibleOp){
            grOpt.setVisible(false);
            grNum.setVisible(true);
            visibleOp = false;
            visibleNum = true;
        } else {
            grNum.setVisible(false);
            grOpt.setVisible(true);
            visibleOp = true;
            visibleNum = false;
        }
    }

    /**
     * Esegue l'espressione
     */
    @FXML
    public void expressionExecuter(){
        try {
            c = new Calculator();
            c.execute(tfBar.getText());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Event listener che gestisce la scrittura con tastierino
     * @param event
     */
    @FXML
    public void writeOnTF(KeyEvent event){
        String c = event.getText();
        switch (c){
            default:try{
                Integer.parseInt(c);
                tfBar.setText(tfBar.getText() + c);
            } catch (Exception e){}break;
            case "/": lastOp = c.length();
                tfBar.setText(tfBar.getText() + c);
                break;
            case "*": lastOp = c.length();
                tfBar.setText(tfBar.getText() + c);
                break;
            case "-": lastOp = c.length();
                tfBar.setText(tfBar.getText() + c);
                break;
            case "+": lastOp = c.length();
                tfBar.setText(tfBar.getText() + c);
                break;
            case ".": lastOp = c.length();
                tfBar.setText(tfBar.getText() + c);
                break;
            case "\r": expressionExecuter();
                break;
        }
    }

}
