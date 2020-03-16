import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Main extends Application
{

    private TextField tfName = new TextField();
    private TextField tfStreet = new TextField();
    private TextField tfCity = new TextField();
    private TextField tfState = new TextField();
    private TextField tfZip = new TextField();


    // Button for sending a student to the server
    private Button btnRegister = new Button("Register to the Server");


    // Host Name or IP Address
    String host = "localhost";





    @Override
    public void start(Stage primaryStage) throws Exception
    {
        GridPane pane = new GridPane();

        pane.add(new Label("Name"), 0, 0);
        pane.add(tfName, 1, 0);

        pane.add(new Label("Street"), 0, 1);
        pane.add(tfStreet, 1,1);


        pane.add(new Label("City"), 0, 2);

        HBox hBox = new HBox(2);
        pane.add(hBox, 1, 2);
        hBox.getChildren().addAll(tfCity, new Label("State"), tfState, new Label("Zip"), tfZip);

        pane.add(btnRegister, 1, 3);

        GridPane.setHalignment(btnRegister, HPos.RIGHT);





        pane.setAlignment(Pos.CENTER);

        tfName.setPrefColumnCount(15);
        tfStreet.setPrefColumnCount(15);
        tfCity.setPrefColumnCount(10);
        tfState.setPrefColumnCount(2);
        tfZip.setPrefColumnCount(3);











        // set event handler for button
        btnRegister.setOnAction(new ButtonListener());


        Scene scene = new Scene(pane, 450, 200);
        primaryStage.setTitle("Student Client");
        primaryStage.setScene(scene);
        primaryStage.show();

    }





    // Handle button action
    private class ButtonListener implements EventHandler<ActionEvent>
    {

        @Override
        public void handle(ActionEvent e)
        {
            try
            {
                // establish connection with server
                Socket socket = new Socket(host, 8000);



                // create an output stream to the server
                ObjectOutputStream toServer = new ObjectOutputStream(socket.getOutputStream());


                // Get text field
                String name = tfName.getText().trim();
                String street = tfStreet.getText().trim();
                String city = tfCity.getText().trim();
                String state = tfState.getText().trim();
                String zip = tfZip.getText().trim();




                // Create a Student Object and send to the server
                StudentAddress studentAddress = new StudentAddress(name, street, city, state, zip);

                toServer.writeObject(studentAddress);

            }
            catch (IOException ex)
            {
                System.out.println(ex.getMessage());
            }
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
