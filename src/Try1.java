import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;


public class Try1 extends JFrame{
	
	/**
	 */
	private static final long serialVersionUID = 1L;
	
	int px=0 , py=0;
	private DrawingPanel dp;
	private String magicnum= "";
	private int numOfRows=0, numOfCols=0 , maxValue=0;
	private int[] image;
	private Color imageColor [];
	JPanel panel , panel2;
	JButton button;
     File f;
     JFileChooser chooser;
	
	Try1() throws IOException{
		
		button = new JButton("Choose File");
		
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				 chooser = new JFileChooser();
		        FileNameExtensionFilter filter = new FileNameExtensionFilter(
		                "Images","ppm", "pbm","pgm");
		        chooser.setFileFilter(filter);
		        int returnVal = chooser.showOpenDialog(null);
		        if(returnVal == JFileChooser.APPROVE_OPTION) {
		          f = new File(chooser.getSelectedFile().getPath());   
		           
		}
		        try {
		        @SuppressWarnings("resource")
				Scanner sc = new Scanner(f); 
	        	 
		        magicnum= sc.next();
		        if(magicnum.equals("P3") || magicnum.equals("P1")) {
		        	
		        	 sc.next();
		        	 sc.nextLine();
		        }
		        
		        numOfRows=sc.nextInt();
		        numOfCols=sc.nextInt();
		        if(!magicnum.equals("P1"))
		        	maxValue = sc.nextInt();
		        
		        System.out.println("image of "+ magicnum+"\nNumber of rows is "
		        		+ numOfRows + "\nNumber of Columns is "+ numOfCols +
		        		"\nmaxValue is : "+maxValue);
		       
		        image = new int[numOfRows*numOfCols];
		        
		        imageColor =new Color[numOfRows*numOfCols];
		        
		        for(int i = 0; i<numOfRows*numOfCols;i++) {
		        	image[i]=sc.nextInt();
		        	
		        	if(magicnum.equals("P1"))
		        		imageColor [i]=image[i]==1 ? Color.BLACK : Color.WHITE;
		        	
		        	if(magicnum.equals("P2"))
		        		imageColor [i]= new Color(image[i],image[i] ,image[i]);
		        	
		        	if(magicnum.equals("P3")) {
		        		int[] p3Image = new int[2];
		        		for(int j =0;j<2;j++) {
		        			p3Image[j] = sc.nextInt();
		        		}
		        		imageColor [i]= new Color(image[i],p3Image[0] ,p3Image[1]);


		        	}
		        		
		        		
		        }
		        dp.repaint();
		      
			}
		        catch(Exception error) {
				
	
			try {
				
				FileInputStream fileReader = new FileInputStream(f);
				BufferedInputStream bufferedReader =
						new BufferedInputStream(fileReader);
				DataInputStream dis;
				magicnum="";
				
				  while (true) {
			             int b = bufferedReader.read();

			            if (b != -1) {

			                 char c = (char) b;
			             if (!Character.isWhitespace(c)) {
			            	 magicnum+=c;
			                } 
			             else {
			            	 break;
			             }

			            } else {
			                break;
			            }

			        } 
				
				numOfRows=getNum(bufferedReader);

				numOfCols=getNum(bufferedReader);
				
				if(!magicnum.equals("P4"))
					maxValue=getNum(bufferedReader);
				
				System.out.println("image of "+ magicnum+"\nNumber of rows is "
		        		+ numOfRows + "\nNumber of Columns is "+ numOfCols +
		        		"\nmaxValue is : "+maxValue);
		       
				  image = new int[numOfRows*numOfCols];
			        
			       imageColor =new Color[numOfRows*numOfCols];
			        
			        if(magicnum.equals("P5")) {
			        	for (int j = 0; j < numOfRows*numOfCols; ++j) {
			        		image[j]= bufferedReader.read();
			           		imageColor[j] = new Color(image[j],image[j],image[j]);

			         	}
			        	bufferedReader.close();
			        }
			      
			        else if(magicnum.equals("P6"))
			        {
			        	for (int j = 0; j < numOfRows*numOfCols; ++j) {
			        		int[] imageP6 = new int[2];
			        		image[j]= bufferedReader.read();
			        		for(int n=0;n<2;n++) {
			        			imageP6[n]=bufferedReader.read();
			        		}
			        		imageColor[j] = new Color(image[j],
			        				imageP6[0],imageP6[1]);
			        	}
			        	bufferedReader.close();
			        	

			        }
			        
			        else {
				       
			        	
				    dis = new DataInputStream(bufferedReader);
				    
			        int k =numOfRows*numOfCols-1; 	      
			        int bs;
			        
			         while(dis.available() != 0) {
			        	 bs=dis.readUnsignedByte();
			        	 if(Character.isWhitespace(bs)  || bs == '\b')
			        		 continue;
			        	 
			        	 System.out.println(bs + " s ");
					    String data = Integer.toBinaryString(bs);
			 					    
			        	 
			        	 if(data.length() < 8) {
			        		 for(int jk=data.length();jk<8;jk++)
			        		  data=0+data;
			        	 }
						
			        	 System.out.println(data);

			        	 char[] bin = data.toCharArray();

					    for(char binary : bin ) {
			        		imageColor[k] = binary == '0' ? Color.WHITE:Color.BLACK;

					     k--;
			         }
				        	}
			         dis.close();
		             	 System.out.println(k + " length  \n right : " + numOfRows*numOfCols +
		             			 "\nDif = " + (k-(numOfRows*numOfCols)));

			        	}
			        dp.repaint();

			        
				}catch(Exception er) {
					System.out.println(er.getMessage());
				}
			}
		}	
	});
	
	panel = new JPanel();
	panel.add(button);
	this.add(panel , BorderLayout.SOUTH);
	this.setTitle("Read Images");
	this.setSize(600, 600);
	this.setVisible(true);
	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
	  dp = new DrawingPanel();
      
      add(dp);
} 
	/**** read number of Rows, Columns , and maxValue    *****/ 
	
	public  int getNum(BufferedInputStream bufferedReader) throws IOException {
		char[] c = new char[3];
		int num ;
		int i =0;
		while((num=bufferedReader.read()) != -1 && i<3) {
			c[i]= (char) num;
			i++;
			
		}
		return Integer.parseInt(new String(c));
		
	}
	
	
	@SuppressWarnings("unused")
	public static void main(String[] args) throws IOException {
		Try1 t = new Try1();
	}
	
	class DrawingPanel extends JPanel{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			
			for(int y = 0; y < numOfRows; y++)
				for(int x = 0; x < numOfCols; x++) {
					g.setColor(imageColor[y*numOfCols+x]);
					g.fillRect(x, y, 1, 1);
				}
		}
		}
		
			
		
}
