package fiveInRow;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class FiveInRow extends JFrame{
	private static final long serialVersionUID = 1L;
	private JPanel topPanel,mainPanel;
	private JLabel jLabelForResult,jLabelForResultColor;
	private int count;
	private String nameOfCurrentButton;
	private static Map<JButton, int[]> mapOfButtonsPlusItsPositionInArray = new HashMap<JButton, int[]>();
	private static ArrayList<JButton> arrayOfButtons = new ArrayList<JButton>();
	private int[][] arrayOfAlreadyPushedButtons;
//	private int columnsQuantity=15, rowsQuantity=14;
	private int columnsQuantity=25, rowsQuantity=24;



	
	FiveInRow(){
		super("Five In Row");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		arrayOfAlreadyPushedButtons=new int[columnsQuantity+2][rowsQuantity+2];
		
		ActionListener bAction = new action(); //остальные кнопки такие как Result, Clear
		jLabelForResult=new JLabel("LETS GO! Please put  ");
		jLabelForResult.setFont(new Font("Arial Bold",0,16));
		jLabelForResultColor=new JLabel("      ");
		jLabelForResultColor.setBorder(BorderFactory.createLineBorder(Color.RED));
		
		
		new JLabel("Step: ");
		
		topPanel=new JPanel();
		mainPanel=new JPanel();
		GridLayout mainGL=new GridLayout(rowsQuantity,1);
		mainGL.setHgap(0);
		mainGL.setVgap(0);
		mainPanel.setLayout(mainGL);
		
		topPanel.add(jLabelForResult);
		topPanel.add(jLabelForResultColor);	
		

			int positionX,positionY,buttonNumber=0;
			
	        for(int row=1; row<=rowsQuantity;row++){
		        for(int column=1; column<=columnsQuantity;column++){

		        	buttonNumber++;

					JButton btn = new JButton();
			        btn.addActionListener(bAction);
			        nameOfCurrentButton="B"+buttonNumber;
			        btn.setName(nameOfCurrentButton);

			        positionX=column;
		        	positionY=row;
		        	arrayOfButtons.add(btn);
		        	mapOfButtonsPlusItsPositionInArray.put(btn, new int[]{positionX,positionY});
		        	mainPanel.add(btn);     	        
		        }
	        }
		
		getContentPane().add(topPanel,"North");
		getContentPane().add(mainPanel);		

		setResizable(false); // Запретить изменять размер фрейма
		setSize(650,650); // задаём размер фрейма
		setVisible(true); //отобразить фрейм
		setLocationRelativeTo(null); //расположение фрейма в центре окна
		
	}
	
	
	public class action implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			JButton activeButton= (JButton)arg0.getSource();
			int posX=mapOfButtonsPlusItsPositionInArray.get(activeButton)[0];
			int posY=mapOfButtonsPlusItsPositionInArray.get(activeButton)[1];		
			count++; 
			if(arrayOfAlreadyPushedButtons[posX][posY]!=1 && arrayOfAlreadyPushedButtons[posX][posY]!=2){
				if (count%2==0){
					activeButton.setBackground(Color.BLUE);
					arrayOfAlreadyPushedButtons[posX][posY]=1;
					performCheck(activeButton,1);				
				
				} else {
					activeButton.setBackground(Color.RED);
					arrayOfAlreadyPushedButtons[posX][posY]=2;
					performCheck(activeButton,2);
				}
				System.out.println("Step: "+count+";"+" Current button: "+activeButton.getName()+";   Position: x"+posX+" y"+posY);
//				activeButton.setFont(new Font("Arial Bold",0,20));
			}
		}
	}
	private void performCheck(JButton currentButton, int pointerXorY){
		int posX=mapOfButtonsPlusItsPositionInArray.get(currentButton)[0];
		int posY=mapOfButtonsPlusItsPositionInArray.get(currentButton)[1];		
		jLabelForResult.setText("Please put: ");
		
		if(!checkLeftRight(pointerXorY,posX,posY)) 
			if(!checkUpDown(pointerXorY,posX,posY))
				if(!check1stSlash(pointerXorY,posX,posY))
					if(!check2ndSlash(pointerXorY,posX,posY)){	
						if(pointerXorY==1){
							jLabelForResultColor.setBorder(BorderFactory.createLineBorder(Color.RED));
						}else {
							jLabelForResultColor.setBorder(BorderFactory.createLineBorder(Color.BLUE));
						}
					}else winner(pointerXorY);
				else winner(pointerXorY);
			else winner(pointerXorY);
		else winner(pointerXorY);
					
	
	}

	private boolean checkLeftRight(int pointerXorY, int posX, int posY){
		int currentPointer=0;
		int howManySimilarPointsInRow=0,similarPoints=0;
		
		do{
			currentPointer++; 
		}while(arrayOfAlreadyPushedButtons[posX-currentPointer][posY]==pointerXorY && currentPointer<=4);			
		if(currentPointer==5) {
			return true;
		}
		
		howManySimilarPointsInRow=currentPointer;

		currentPointer=0;
		do{
			currentPointer++; similarPoints=currentPointer+howManySimilarPointsInRow;
		}while(arrayOfAlreadyPushedButtons[posX+currentPointer][posY]==pointerXorY && similarPoints<=5);			
		if((howManySimilarPointsInRow+currentPointer)==6){
			return true;
		}
		
		return false;
	}
	
	
	private boolean checkUpDown(int pointerXorY, int posX, int posY){
		int currentPointer=0;
		int howManySimilarPointsInRow=0,similarPoints=0;
		
		do{
			currentPointer++; 
		}while(arrayOfAlreadyPushedButtons[posX][posY-currentPointer]==pointerXorY && currentPointer<=4);			
		if(currentPointer==5) {
			return true;
		}
		
		howManySimilarPointsInRow=currentPointer;

		currentPointer=0;
		do{
			currentPointer++; similarPoints=currentPointer+howManySimilarPointsInRow;
		}while(arrayOfAlreadyPushedButtons[posX][posY+currentPointer]==pointerXorY && similarPoints<=5);			
		if((howManySimilarPointsInRow+currentPointer)==6){
			return true;
		}
		
		return false;
	}

	private boolean check1stSlash(int pointerXorY, int posX, int posY){
		int currentPointer=0;
		int howManySimilarPointsInRow=0,similarPoints=0;
		
		do{
			currentPointer++; 
		}while(arrayOfAlreadyPushedButtons[posX-currentPointer][posY-currentPointer]==pointerXorY && currentPointer<=4);			
		if(currentPointer==5) {
			return true;
		}
		
		howManySimilarPointsInRow=currentPointer;

		currentPointer=0;
		do{
			currentPointer++; similarPoints=currentPointer+howManySimilarPointsInRow;
		}while(arrayOfAlreadyPushedButtons[posX+currentPointer][posY+currentPointer]==pointerXorY && similarPoints<=5);			
		if((howManySimilarPointsInRow+currentPointer)==6){
			return true;
		}
		
		return false;
	}

	private boolean check2ndSlash(int pointerXorY, int posX, int posY){
		int currentPointer=0;
		int howManySimilarPointsInRow=0,similarPoints=0;
		
		do{
			currentPointer++; 
		}while(arrayOfAlreadyPushedButtons[posX+currentPointer][posY-currentPointer]==pointerXorY && currentPointer<=4);			
		if(currentPointer==5) {
			return true;
		}
		
		howManySimilarPointsInRow=currentPointer;

		currentPointer=0;
		do{
			currentPointer++; similarPoints=currentPointer+howManySimilarPointsInRow;
		}while(arrayOfAlreadyPushedButtons[posX-currentPointer][posY+currentPointer]==pointerXorY && similarPoints<=5);			
		if((howManySimilarPointsInRow+currentPointer)==6){
			return true;
		}
		
		return false;
	}


		
	private void winner(int pointerXorY){
		String win;
		if(pointerXorY==1){
			win="BLUE"; 
			jLabelForResult.setForeground(Color.BLUE);

		}else{
			win="RED";
			jLabelForResult.setForeground(Color.RED);
		}
		jLabelForResult.setText("OUR CONGRATULATIONS!!! THE WINNER IS "+win+"!");
		jLabelForResultColor.setBorder(BorderFactory.createEmptyBorder() );
		for(JButton jBErase: arrayOfButtons){
			int findXofPressedButton=0, findYofPressedButton=0 ;
			findXofPressedButton=mapOfButtonsPlusItsPositionInArray.get(jBErase)[0];
			findYofPressedButton=mapOfButtonsPlusItsPositionInArray.get(jBErase)[1];
			if(arrayOfAlreadyPushedButtons[findXofPressedButton][findYofPressedButton]!=1 && 
			   arrayOfAlreadyPushedButtons[findXofPressedButton][findYofPressedButton]!=2){
				jBErase.setBackground(Color.LIGHT_GRAY);
			}
								
		}
		
	}
	
	
	
	public static void main(String[] args){
		new FiveInRow();
		
		
	}

}
