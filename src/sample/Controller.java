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
    JFXButton btCos;

    @FXML
    TextField tfBar;

    @FXML
    GridPane grOpt;
    boolean visibleOp = false;

    @FXML
    GridPane grNum;
    boolean visibleNum = true;

    private int lastOp;

    @FXML
    public void writeContent(ActionEvent event){
        JFXButton bt = (JFXButton) event.getSource();
        lastOp = bt.getText().length();
        tfBar.setText(tfBar.getText() + bt.getText());
    }

    @FXML
    public void deleteTextBoxElement(){
        try{
            tfBar.setText(tfBar.getText().substring(0, tfBar.getText().length() - lastOp));
        } catch (Exception e){
            System.out.println("Blocco vuoto");
        }
    }

    @FXML
    public void deleteTextBoxContent(){
        try{
            tfBar.setText("");
        } catch (Exception e){
            System.out.println("Blocco vuoto");
        }
    }

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

    @FXML
    public void expressionExecuter(){
        try {
            c = new Calculator(tfBar.getText(), tfBar);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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
