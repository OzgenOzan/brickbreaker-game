// mainPack package

package mainPack;

import javax.swing.JFrame; // .swing.JFrame sayesinde oyunun içinde çalıştığı çerçeve oluşturuluyor

public class Main {

	public static void main(String[] args) {

		JFrame objJF = new JFrame(); // Çerçeve
		GamePlay gamePlay = new GamePlay(); // GamePlay class'ı
		
		objJF.setBounds(10, 10, 700, 600); // Çerçeve boyu
		objJF.setTitle("The Victory Day");
		objJF.setResizable(false);
		objJF.setVisible(true);
		objJF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Ekranı kapama için varsayılan aksiyon, çarpı işaretine basmak...
		objJF.add(gamePlay);
	}

}
