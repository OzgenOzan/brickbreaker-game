package mainPack;

import constants.MapGenConstants;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class MapGen {
	
	// tüm private field lar diğer class lardan erişilebilsin diye get ve set metotları tanımlanıyor.
	private int map [][];
	private int brickWidth;
	private int brickHeight;
	
	public int[][] getMap() {
		return map;
	}
	public int getBrickWidth() {
		return brickWidth;
	}
	public int getBrickHeight() {
		return brickHeight;
	}
	
	public void setMap(int map[][]) {
		this.map = map;
	}
	public void setBrickWidth(int brickWidth) {
		this.brickWidth = brickWidth;
	}
	public void setBrickHeight(int brickHeight) {
		this.brickHeight = brickHeight;
	}
	
	// Harita taslağı
	public MapGen (int row, int col) {
		map = (new int [row][col]);
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				map[i][j] = 1;
			}
		}
		brickWidth = MapGenConstants.BRICK_WIDTH/col;
		brickHeight = MapGenConstants.BRICK_HEIGHT/row;
		
	}
	
	// Tuğlaların çizimi, GamePlay java içerisinden çağırılıyor (.mapGen.draw)
	public void draw(Graphics2D g) {
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				if (map[i][j] > 0) {
					g.setColor(Color.black);
					g.fillRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);
					
					g.setStroke(new BasicStroke(3));
					g.setColor(Color.white);
					g.drawRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);
				}
			}
		}
	}
	
	// Tuğlaların, 0 ve 1 değerleri ile var olup olmama durumları kontrol ediliyor
	public void setBrickVal(int value, int row, int col) {
		map[row][col] = value;
	}

}
