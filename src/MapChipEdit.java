import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;


public class MapChipEdit {
	static BufferedImage mapChipImags[] = null;
	static int chipX[] = null;
	static int chipY[] = null;
	public static void main(String args[]){

		BufferedReader ibr = null;
		try {
			ibr = new BufferedReader(new FileReader("map/chip.txt"));
			String line = ibr.readLine();

			if(line.indexOf("chipData") >= 0){
			int imgNum = Integer.parseInt(line.split(" ", 0)[1]);
			mapChipImags = new BufferedImage[imgNum];
			chipX = new int[imgNum];
			chipY = new int[imgNum];
		}else{	//ファイルエラー
			JOptionPane.showMessageDialog(null, "エラー");
			System.exit(0);
		}
			String x_y[];
			for (int i = 0; i < mapChipImags.length; i++) {
			mapChipImags[i] = ImageIO.read(new File("map/"+ i + ".png"));
			x_y = ibr.readLine().split(" ", 0);
			chipX[i] = Integer.parseInt(x_y[0]);
			chipY[i] = Integer.parseInt(x_y[1]);
		}
			ibr.close();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "エラー");
			System.exit(0);
		}

		int total = 0;
		for(int i = 0; i < chipX.length; i++){
			total = total + chipX[i] * chipY[i];
		}

		BufferedImage img = new BufferedImage(256 * 16, 256 * 16, BufferedImage.TYPE_3BYTE_BGR);

		for(int i =0; i < total; i++){

			drawChip(img.createGraphics(), 16 * (i % 256), 16 *(i / 256),  i);
		}
		OutputStream out;
		try {
			out = new FileOutputStream("map/map.bmp");
			ImageIO.write(img, "bmp", out);
			out.close();
		} catch (FileNotFoundException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

	}
	public static void drawChip(Graphics2D g, int x, int y, int chipID){
		int i = 0;
		while(chipID >= (chipX[i] * chipY[i])){
			chipID = chipID - (chipX[i] * chipY[i]);
			i++;
		}
		g.drawImage(mapChipImags[i], x, y, x + 16, y + 16,
				(chipID % chipX[i]) * 16, (chipID / chipX[i]) * 16, (chipID % chipX[i] + 1) * 16, (chipID / chipX[i] + 1) * 16, null);
	}
}
