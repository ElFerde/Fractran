package gui;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;


public class Main extends Application {
	int sizex = 30;
	int sizey = 30;
	Canvas canvas = new Canvas(sizex*20+20, sizey*20+20+60);
	GraphicsContext gc = canvas.getGraphicsContext2D();
	BigInteger state = BigInteger.valueOf(2);
	static ArrayList<Integer> primes = new ArrayList<>(Arrays.asList(2, 3));
	Button run = new Button("RUN");
	
	EventHandler<MouseEvent> clickedBoard = mouseEvent ->
	{
		System.out.println(mouseEvent.getSceneX());
		if (mouseEvent.getSceneX() > 10 && mouseEvent.getSceneX() < 10 + sizex*20)
		{
			if (mouseEvent.getSceneY() > 10 && mouseEvent.getSceneY() < 10 + sizey*20)
			{
				int x = (int) ((mouseEvent.getSceneX()-10)/20.0);
				int y = (int) ((mouseEvent.getSceneY()-10)/20.0);
				
				if (state.mod(BigInteger.valueOf(primes.get(x * sizey + y + 2))).equals(BigInteger.valueOf(0)))
				{
					state = state.divide(BigInteger.valueOf(primes.get(x * sizey + y + 2)));
					gc.setFill(Color.WHITE);
				}
				else
				{
					state = state.multiply(BigInteger.valueOf(primes.get(x * sizey + y + 2)));
					gc.setFill(Color.BLACK);
				}
				gc.fillRect(x*20+10, y*20+10, 20, 20);
				System.out.println(state);
			}
		}
	};
	
	@Override
	public void start(Stage stage) {
		Pane root = new Pane();
		String title = "Conway games";
		stage.setTitle(title);
		stage.setScene(new Scene(root, sizex*20+20, sizey*20+20+60));
		stage.show();
		
		root.getChildren().add(canvas);
		stage.setResizable(false);
		
		canvas.setOnMousePressed(clickedBoard);
		gc.setFill(Color.ANTIQUEWHITE);
		gc.fillRect(0, 0, sizex*20+20, sizey*20+20+60);
		gc.setFill(Color.WHITE);
		gc.fillRect(10, 10, sizex*20, sizey*20);
		
		while (primes.size() < sizex*sizey + 2)
		{
			nextPrime();
		}
	}
	
	static void nextPrime()
	{
		int p = primes.get(primes.size() - 1);
		while (true)
		{
			p += 2;
			if(checkPrime(p))
			{
				primes.add(p);
				return;
			}
		}
	}
	
	static boolean checkPrime(int p)
	{
		for (int q : primes)
		{
			if(p%q == 0)
			{
				return false;
			}
		}
		return true;
	}
	
	
	public static void main(String[] args) {
		launch(args);
	}

}