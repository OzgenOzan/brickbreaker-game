package mainPack;

import constants.MapGenConstants; // Kullanılan sabitler için ayrı bir paket

// Kullanılan kütüphaneler
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;
import javax.swing.Timer;

// GamePlay class'ı JPanel class'ından oluşmaktadır, bir nevi yavrusu
public class GamePlay extends JPanel implements KeyListener, ActionListener {
	
	private boolean playStat = false; // Oyun durumu
	private int score = 0;
	private int totalBricks = 21;
	
	private Timer timer; // Timer paketinden oluşturulan bir sabit
	private int delay = 15; // 15 ms
	
	private int playerX = 310; // Mavi Pedalın konumu, x ekseni
	
	private int ballposX = 120; // Topun x eksenindeki konumu
	private int ballposY = 350; // Topun y eksenindeki konumu
	private int ballXdir = -1; // x eksenindeki hız
	private int ballYdir = -2; // y eksenindeki hız
	
	private MapGen mapGen; // MapGen class'ı bir sabite alınıyor, buradan erişebilmek için...
	
	public GamePlay() {
		// this keyword ile yazılması, kodun okunmasını kolaylaştırmaktadır. Aynı class içerisindeki değerin alındığını gösteriyor. MapGen class'ındaki constructor metotu kullanılıyor.
		this.mapGen = new MapGen(MapGenConstants.ROW, MapGenConstants.COL);
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer = new Timer(delay, this);
		timer.start();		
	}
	
	// Graphics ve Graphics2D paketleri
	public void paint(Graphics g) {
		
		// Arkaplanın çizimi
		g.setColor(Color.white);
		g.fillRect(1, 1, 692, 592);
		
		// Tuğlaların çizimi
		mapGen.draw((Graphics2D)g);
		
		// Dış çerçevenin çizimi
		g.setColor(Color.orange);
		g.fillRect(0, 0, 3, 592);
		g.fillRect(0, 0, 692, 3);
		g.fillRect(683, 0, 3, 592);
		
		// Pedalın çizimi
		g.setColor(Color.blue);
		g.fillRect(playerX, 550, 100, 8);
		
		// Topun çizimi
		g.setColor(Color.red);
		g.fillOval(ballposX, ballposY, 20, 20);
		
		// Score bilgisi
		g.setColor(Color.black);
		g.setFont(new Font("serif", Font.BOLD, 18));
		g.drawString("Score: " + score, 590, 30);
		
		// Kazanma durumundaki yazılar
		if(totalBricks <= 0) {
			playStat = false;
			ballXdir = 0;
			ballYdir = 0;
			g.setColor(Color.green);
			g.setFont(new Font("serif", Font.BOLD, 30));
			g.drawString("You've Won!, Score: " + score, 190, 300);
			
			g.setFont(new Font("serif", Font.BOLD, 20));
			g.drawString("Press Enter to Restart!", 235, 350);
		}
		
		// Kaybetme durumundaki yazılar
		if(ballposY > 570) {
			playStat = false;
			ballXdir = 0;
			ballYdir = 0;
			g.setColor(Color.red);
			g.setFont(new Font("serif", Font.BOLD, 30));
			g.drawString("Game Over, Score: " + score, 190, 300);
			
			g.setFont(new Font("serif", Font.BOLD, 20));
			g.drawString("Press Enter to Restart!", 235, 350);

		}
		
		// Tüm grafik içeriğini sıfırlar, memory dahil olmak üzere
		g.dispose();
	}

	private static final long serialVersionUID = 1L;

	@Override
	
	// Etkileşim kısmı, çizimde olduğu gibi pozisyonlar dinamik olarak belirleniyor
	public void actionPerformed(ActionEvent e) {
		timer.start();
		
		// Pedal ile top etkileşimi
		if(playStat) {
			if(new Rectangle(ballposX, ballposY, 20, 30).intersects(new Rectangle(playerX, 550, 100, 8))) {
				ballYdir = -ballYdir;
				
			}
			
			// Tuğlalar ile top etkileşimi
			for(int i = 0; i < mapGen.getMap().length; i++) {
				for(int j = 0; j < mapGen.getMap()[0].length; j++) {
					if(mapGen.getMap()[i][j] > 0) {
						int brickX = j*mapGen.getBrickWidth() + 80;
						int brickY = i*mapGen.getBrickHeight() + 50;
						
						int brickWidth = mapGen.getBrickWidth();
						int brickHeight = mapGen.getBrickHeight();
						
						Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
						Rectangle ballRect = new Rectangle(ballposX, ballposY, 20, 20);
						Rectangle brickRect = rect;
						
						if(ballRect.intersects(brickRect)) {
							this.mapGen.setBrickVal(0, i, j);
							this.totalBricks--;
							this.score+= 5;
							
							if(ballposX + 19 <= brickRect.x || ballposX + 1 >= brickRect.x + brickRect.width) {
								ballXdir = -ballXdir;
							} else {
								ballYdir = -ballYdir;
							}
						}
					}
				}
			}
				
				// Top hareketi, hız + o anki pozisyon
				ballposX += ballXdir;
				ballposY += ballYdir;
				
				// Duvardan sekme (Java'da koordinat sistemi sol üstten başlıyor, sağa ve alta doğru pozitif eksen)
				if (ballposX < 0) {
					ballXdir = -ballXdir;		
				}
				if (ballposY < 0) {
					ballYdir = -ballYdir;
				}
				if (ballposX > 670) {
					ballXdir = -ballXdir;
				}
			
		}
		
		// Tekrardan çizimi başlatıyor
		repaint();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
		
	}

	@Override
	
	// Ok tuşları ve enter için ayarlar
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if (playerX >= 582) {
				playerX = 582;
			} else {
				moveRight();
			}
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			if (playerX <= 3) {
				playerX = 3;
			} else {
				moveLeft();
			}
		}
		
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			if (!playStat) {
				playStat = true;
				ballposX = 120;
				ballposY = 350;
				ballXdir = -1;
				ballYdir = -2;
				
				score = 0;
				totalBricks = 21;
				mapGen = new MapGen(3, 7);
				
				repaint();
			}
		}
		
	}
	
	public void moveRight() {
		playStat = true;
		playerX += 20;
	}
	
	public void moveLeft() {
		playStat = true;
		playerX -= 20;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
		
	}

}
