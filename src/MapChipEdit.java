import java.awt.Color;
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
	BufferedImage mapChipImags[] = null;
	int chipX[] = null;
	int chipY[] = null;
	int width;
	int height;

	public static void main(String args[]){
		BufferedReader ibr = null;
		try {
			ibr = new BufferedReader(new FileReader("mapData/folder.txt"));
			String line = ibr.readLine();
			int folderNum = -1;
			if(line.indexOf("folderData") >= 0){
				folderNum = Integer.parseInt(line.split(" ", 0)[1]);
			}else{//ファイルエラー
				JOptionPane.showMessageDialog(null, "エラー folderData");
				System.exit(0);
			}

			for (int i = 0; i < folderNum; i++) {
				new MapChipEdit(ibr.readLine());
			}
			ibr.close();
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "エラー folderData");
			System.exit(0);
		}
	}

	public MapChipEdit(String folder){
		System.out.println(folder+"作成開始....");
		BufferedReader ibr = null;
		String line = null;
		try {
			ibr = new BufferedReader(new FileReader("mapData/" + folder + "Chip.txt"));
			line = ibr.readLine();
			String datas[];
			if(line.indexOf("chipData") >= 0){
				datas = line.split(" ", 0);
				int imgNum = Integer.parseInt(datas[1]);
				mapChipImags = new BufferedImage[imgNum];
				chipX = new int[imgNum];
				chipY = new int[imgNum];
				width = Integer.parseInt(datas[2]);
				height = Integer.parseInt(datas[3]);
			}else{	//ファイルエラー
				JOptionPane.showMessageDialog(null, "エラー "+ folder);
				System.exit(0);
			}

			for (int i = 0; i < mapChipImags.length; i++) {
				datas = ibr.readLine().split(" ", 0);
				line = datas[0];
				mapChipImags[i] = ImageIO.read(new File("sf-matome20051117/" + folder + "/" + datas[0] + ".png"));
				chipX[i] = Integer.parseInt(datas[1]);
				chipY[i] = Integer.parseInt(datas[2]);
			}
			ibr.close();
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "エラー " + line);
			System.exit(0);
		}


		BufferedImage bmpImg = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
		Graphics2D bg = (Graphics2D)bmpImg.createGraphics();
		for(int i =0; i < mapChipImags.length; i++){
			bg.drawImage(mapChipImags[i], chipX[i], chipY[i], null);
		}
		
		BufferedImage pngImg = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
		Graphics2D pg = (Graphics2D)pngImg.createGraphics();
		int c;
		for(int i =0; i < width; i++){
			for(int j = 0; j < height; j++){
				c = bmpImg.getRGB(i, j);
				
				//System.out.println(Integer.toHexString(c));
				if(c == 0xFF00FF00){
					pg.setColor(new Color(1.0f, 1.0f, 1.0f, 0.0f));
				}else{
					pg.setColor((new Color(c)));
				}
				pg.drawLine(i, j, i, j);
			}
		}
		
		OutputStream out;
		try {
			out = new FileOutputStream("map/" + folder + "Map.bmp");
			ImageIO.write(bmpImg, "bmp", out);
			out.close();
			out = new FileOutputStream("map/" + folder + "Map.png");
			ImageIO.write(pngImg, "png", out);
			out.close();
		} catch (FileNotFoundException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		System.out.println(folder+"作成終了");
	}
}
