import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import java.util.ArrayList;
import java.util.List;


public class Interface extends Application 
{
   public ArrayList<String> horario = new ArrayList<>();
   public ArrayList<Casillero> cs = new ArrayList<>();
   public String hora1 = null;
   public String hora2 = null;
   public Label lb1 = new Label();;

   @Override
   public void start(Stage primaryStage) 
   {  
      GridPane gp2 = new GridPane(); // Pane to contain the hours of the day
  
      Insets i1 = new Insets(10, 10, 10, 10); // Separations between the buttons
      
      // Data to create the buttons and locate them in the gridpane
      String am = "am";
      String pm = "pm";
      int col = 0;
      
      // Initialization of the buttons
      Casillero c1 = null;
      Casillero c2 = null;
      Casillero c3 = null;
      
      //Colors the buttons are gonna have
      ArrayList<ArrayList<String>> colors = new ArrayList<>();
      colors.add(new ArrayList<>(List.of("#ffdab9", "#87cefa"))); // 0 am -  12 pm
      colors.add(new ArrayList<>(List.of("#ffdab9", "#87cefa"))); // 1 pm - am
      colors.add(new ArrayList<>(List.of("#ffdab9", "#00bfff"))); // 2 pm - am
      colors.add(new ArrayList<>(List.of("#ffdab9", "#00bfff"))); // 3 pm - am
      colors.add(new ArrayList<>(List.of("#ffefd5", "#00bfff"))); // 4 pm - am
      colors.add(new ArrayList<>(List.of("#ffefd5", "#00bfff"))); // 5 pm - am  
      colors.add(new ArrayList<>(List.of("#fffacd", "#add8e6"))); // 6 am - pm
      colors.add(new ArrayList<>(List.of("#fffacd", "#add8e6"))); // 7 am - pm
      colors.add(new ArrayList<>(List.of("#ffe4b5", "#87cefa"))); // 8 am - pm
      colors.add(new ArrayList<>(List.of("#ffe4b5", "#87cefa"))); // 9 am - pm
      colors.add(new ArrayList<>(List.of("#ffe4b5", "#87cefa"))); // 10 am - pm
      colors.add(new ArrayList<>(List.of("#ffe4b5", "#87cefa"))); // 11 am - pm
      
      //Creation of the all buttons
      for (int i = 0; i < 2; i++)
      {
         if (i == 1)
         {
            am = "pm";
            pm = "am";
         }
         
         for (int j = 6; j < 18; j++)
         {
            if (j < 12)
            {
               c1 = new Casillero(String.valueOf(j%12) + am, colors.get(j%12).get(i));
               gp2.add(c1.getCasillero(), col, 0);
               gp2.setMargin(c1.getCasillero(), i1);
               cs.add(c1);
            }
               
            if (j == 12)
            {
               c2 = new Casillero(String.valueOf(j) + pm, colors.get(j%12).get(i));
               gp2.add(c2.getCasillero(), col, 0);
               gp2.setMargin(c2.getCasillero(), i1);
               cs.add(c2);
            }
               
            if (j > 12)
            {
               c3 = new Casillero(String.valueOf(j%12) + pm, colors.get(j%12).get(i));
               gp2.add(c3.getCasillero(), col, 0);
               gp2.setMargin(c3.getCasillero(), i1);
               cs.add(c3);
            } 
            
            col = col + 1;
         }
      }
      
      // The gridpane goes inside a scroll pane
      ScrollPane scp1 = new ScrollPane();
      scp1.setContent(gp2);
          
      // The scrollpane goes inside a border pane
      BorderPane bp1 = new BorderPane();
      bp1.setCenter(scp1);
      
      // Button to add an schedule
      HBox hb1= new HBox();
      Button add = new Button("Add Schedule");
      hb1.getChildren().addAll(lb1, add);
      hb1.setAlignment(Pos.CENTER_RIGHT);
      bp1.setMargin(hb1, i1);
      hb1.setSpacing(20);
      bp1.setBottom(hb1);
      
      // Tab with buttons with the hours of the day
      Tab tab1 = new Tab("Horario", bp1);
      tab1.setClosable(false);
      
      // All the Tabs goes inside a Tab pane
      TabPane tabPane = new TabPane();
      tabPane.getTabs().add(tab1);
      
      add.setOnAction(e -> 
      {
         horario.clear();
         
         for (int i = 0; i < cs.size(); i++)
         {
            if (cs.get(i).getActivate0Min())
            {
               horario.add(cs.get(i).getHora() + " " + cs.get(i).get0Min());
            }
            
            if (cs.get(i).getActivate30Min())
            {
               horario.add(cs.get(i).getHora() + " " + cs.get(i).get30Min());
            }
         }
         
         if (horario.isEmpty())
         {
            lb1.setText("");
         }
         else
         {
            if (horario.size()%2 == 1)
            {
               System.out.println("Un horario tiene hora de inicio, pero no de fin");
            }
            else
            {
               String [] parts = null;
               String digits_h = null;
               String letters_h = null;
               String digits_m = null;
               ArrayList<Integer> horario_num = new ArrayList<>();
               String all = null;
               Integer num_hour= null;
               Integer hour = null;

               
               for (int i = 0; i < horario.size(); i++)
               {
                  parts = horario.get(i).split(" ");
                  digits_h = parts[0].replaceAll("\\D", "");
                  letters_h = parts[0].replaceAll("\\d", "");

                  //if ("am".equals(letters_h))
                  //{
                     hour = Integer.parseInt(digits_h);
                  //}

                  if ("pm".equals(letters_h) && hour != 12)
                  {
                     hour = Integer.parseInt(digits_h) + 12;
                  }
                  
                  digits_m = parts[1].replaceAll("\\D", "");
                  
                  if (digits_m.length() == 1)
                  {
                     num_hour = hour * 100;
                  }
                  else
                  {
                     num_hour = hour*100 + Integer.parseInt(digits_m);
                  }
                  
                  horario_num.add(num_hour);
               }
               System.out.println(horario_num);
            }
            //System.out.println(horario.get(i) + " -> " + horario.get(i+1));
            //lb1.setText(horario.get(0) + " -> " + horario.get(1)); // No borrar
         }
      });
      
      // Create a scene and place it in the stage
      Scene scene = new Scene(tabPane, 400, 180);
      primaryStage.setTitle("Hidroponico"); // Set the stage title
      primaryStage.setScene(scene); // Place the scene in the stage
      primaryStage.setResizable(false); // The window is not resizable
      primaryStage.show(); // Display the stage
  }
  
}

/*
      Casillero c_6am = new Casillero("6 am", "#fffacd");
      gp2.add(c_6am.getCasillero(), 0, 0);
      gp2.setMargin(c_6am.getCasillero(), i1);
      
      Casillero c_7am = new Casillero("7 am", "#fffacd");
      gp2.add(c_7am.getCasillero(), 1, 0);
      gp2.setMargin(c_7am.getCasillero(), i1);
      
      Casillero c_8am = new Casillero("8 am", "#ffe4b5");
      gp2.add(c_8am.getCasillero(), 2, 0);
      gp2.setMargin(c_8am.getCasillero(), i1);
   
      Casillero c_9am = new Casillero("9 am", "#ffe4b5");
      gp2.add(c_9am.getCasillero(), 3, 0);
      gp2.setMargin(c_9am.getCasillero(), i1);
      
      Casillero c_10am = new Casillero("10 am", "#ffe4b5");
      gp2.add(c_10am.getCasillero(), 0, 1);
      gp2.setMargin(c_10am.getCasillero(), i1);
      
      Casillero c_11am = new Casillero("11 am", "#ffe4b5");
      gp2.add(c_11am.getCasillero(), 1, 1);
      gp2.setMargin(c_11am.getCasillero(), i1);
      
      Casillero c_12am = new Casillero("12 am", "#ffdab9");
      gp2.add(c_12am.getCasillero(), 2, 1);
      gp2.setMargin(c_12am.getCasillero(), i1);
      
      Casillero c_1pm = new Casillero("1 pm", "#ffdab9");
      gp2.add(c_1pm.getCasillero(), 3, 1);
      gp2.setMargin(c_1pm.getCasillero(), i1);
      
      Casillero c_2pm = new Casillero("2 pm", "#ffdab9");
      gp2.add(c_2pm.getCasillero(), 0, 2);
      gp2.setMargin(c_2pm.getCasillero(), i1);
      
      Casillero c_3pm = new Casillero("3 pm", "#ffdab9");
      gp2.add(c_3pm.getCasillero(), 1, 2);
      gp2.setMargin(c_3pm.getCasillero(), i1);
      
      Casillero c_4pm = new Casillero("4 pm", "#ffefd5");
      gp2.add(c_4pm.getCasillero(), 2, 2);
      gp2.setMargin(c_4pm.getCasillero(), i1);
      
      Casillero c_5pm = new Casillero("5 pm", "#ffefd5");
      gp2.add(c_5pm.getCasillero(), 3, 2);
      gp2.setMargin(c_5pm.getCasillero(), i1);
      
      Casillero c_6pm = new Casillero("6 pm", "#add8e6");
      gp2.add(c_6pm.getCasillero(), 0, 3);
      gp2.setMargin(c_6pm.getCasillero(), i1);
      
      Casillero c_7pm = new Casillero("7 pm", "#add8e6");
      gp2.add(c_7pm.getCasillero(), 1, 3);
      gp2.setMargin(c_7pm.getCasillero(), i1);
      
      Casillero c_8pm = new Casillero("8 pm", "#87cefa");
      gp2.add(c_8pm.getCasillero(), 2, 3);
      gp2.setMargin(c_8pm.getCasillero(), i1);
      
      Casillero c_9pm = new Casillero("9 pm", "#87cefa");
      gp2.add(c_9pm.getCasillero(), 3, 3);
      gp2.setMargin(c_9pm.getCasillero(), i1);
      
      Casillero c_10pm = new Casillero("10 pm", "#87cefa");
      gp2.add(c_10pm.getCasillero(), 0, 4);
      gp2.setMargin(c_10pm.getCasillero(), i1);
      
      Casillero c_11pm = new Casillero("11 pm", "#87cefa");
      gp2.add(c_11pm.getCasillero(), 1, 4);
      gp2.setMargin(c_11pm.getCasillero(), i1);
      
      Casillero c_12pm = new Casillero("12 pm", "#87cefa");
      gp2.add(c_12pm.getCasillero(), 2, 4);
      gp2.setMargin(c_12pm.getCasillero(), i1);
      
      Casillero c_1am = new Casillero("1 am", "#87cefa");
      gp2.add(c_1am.getCasillero(), 3, 4);
      gp2.setMargin(c_1am.getCasillero(), i1);
      
      Casillero c_2am = new Casillero("2 am", "#00bfff");
      gp2.add(c_2am.getCasillero(), 0, 5);
      gp2.setMargin(c_2am.getCasillero(), i1);
      
      Casillero c_3am = new Casillero("3 am", "#00bfff");
      gp2.add(c_3am.getCasillero(), 1, 5);
      gp2.setMargin(c_3am.getCasillero(), i1);
      
      Casillero c_4am = new Casillero("4 am", "#00bfff");
      gp2.add(c_4am.getCasillero(), 2, 5);
      gp2.setMargin(c_4am.getCasillero(), i1);
      
      Casillero c_5am = new Casillero("5 am", "#00bfff");
      gp2.add(c_5am.getCasillero(), 3, 5);
      gp2.setMargin(c_5am.getCasillero(), i1);
      
      cs.add(c_6am);
      cs.add(c_7am);
      cs.add(c_8am);
      cs.add(c_9am);
      cs.add(c_10am);
      cs.add(c_11am);
      cs.add(c_12am);
      cs.add(c_1pm);
      cs.add(c_2pm);
      cs.add(c_3pm);
      cs.add(c_4pm);
      cs.add(c_5pm);
      cs.add(c_6pm);
      cs.add(c_7pm);
      cs.add(c_8pm);
      cs.add(c_9pm);
      cs.add(c_10pm);
      cs.add(c_11pm);
      cs.add(c_12pm);
      cs.add(c_1am);
      cs.add(c_2am);
      cs.add(c_3am);
      cs.add(c_4am);
      cs.add(c_5am);*/
