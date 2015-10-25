package yang;

import java.awt.Color;  
import java.awt.Font;  
import java.awt.FontMetrics;  
import java.awt.Graphics;  
import java.awt.image.BufferedImage;  
import java.io.File;  
import java.io.IOException;  
  
import javax.imageio.ImageIO;  
  
/** 
 * ��״ͼ 
 * 
 * @author lazy_p 
 * @date 2010-3-20 
 */  
public class Test {  
    private final int histogramWidth = 15;// ����ͼ�Ŀ��  
    private final int histogramPitch = 10;// ����ͼ�ļ��  
    private float scaling = 1f;// ���ŵı���  
    private int maxStrWidth = 0; // �ַ�����Ҫ�������  
  
    /** 
     * <pre> 
     *   ����b[i]��str[i]�����Ӧ 
     * </pre> 
     * 
     * @param g 
     * @param title 
     * @param v 
     * @param str 
     * @param color 
     *            ����Ϊ�� 
     */  
    public BufferedImage paintPlaneHistogram(String title, int[] v,  
            String[] str, Color[] color) {  
        int width = str.length * histogramWidth+str.length*histogramPitch+50;  
        int height = 255;  
        scaling = calculateScale(v, height);//�������ű���  
          
        BufferedImage bufferImage = new BufferedImage(width, height,  
                BufferedImage.TYPE_INT_RGB);  
        Graphics g = bufferImage.getGraphics();  
        g.setColor(Color.WHITE);  
        g.fillRect(0, 0, width, height);  
        FontMetrics metrics = null;  
  
        g.setFont(new Font(null, Font.BOLD, 18));  
        g.setColor(Color.RED);  
  
        g.drawString(title, (bufferImage.getWidth() - g.getFontMetrics()  
                .stringWidth(title)) >> 1, 30);// ������  
  
        g.setFont(new Font(null, Font.PLAIN, 12));  
  
        metrics = g.getFontMetrics();  
  
        g.setColor(Color.BLACK);  
  
        g.drawLine(10, 0, 10, height - 15); // ��Y����  
        g.drawLine(10, height - 15, width, height - 15);// ��X����  
          
        int j = 0;  
        int colorCount=color.length;  
          
        for (int i = 0; i < v.length; ++i) {  
  
            if (color != null){  
                g.setColor(color[j]);// ����ǰ��ɫ  
                if(j+1<colorCount){  
                    j++;  
                }else{  
                    j=0;  
                }  
            }else{  
                g.setColor(Color.RED);  
            }  
  
            int x = 20 + i  
                    * (histogramPitch + histogramWidth + (maxStrWidth >> 1));// �����X����  
            int y = height - 16 - (int) (v[i] * scaling); // �����Y����  
  
            // ��ռ�ı���  
            g.drawString(v[i] + "", x  
                    - ((metrics.stringWidth(v[i] + "") - histogramWidth) >> 1),  
                    y);  
  
            // ��ƽ�����״ͼ  
            g.drawRect(x, y, histogramWidth, (int) (v[i] * scaling));  
            g.fillRect(x, y, histogramWidth, (int) (v[i] * scaling));  
  
            // ��ÿһ���ʾ�Ķ���  
            g.drawString(str[i], x  
                    - ((metrics.stringWidth(str[i]) - histogramWidth) >> 1),  
                    height - 2);  
        }  
  
        return bufferImage;  
    }  
      
    /** 
     * �������ű��� 
     * @param v 
     * @param h ͼƬ�ĸ߶� 
     * @return 
     */  
    public float calculateScale(int[] v , int h){  
        float scale = 1f;  
        int max = Integer.MIN_VALUE;  
        for(int i=0 , len=v.length ; i < len ;++i){  
            if(v[i]>h && v[i]>max){  
                max=v[i];  
            }  
        }  
        if(max > h){  
            scale=((int)(h*1.0f/max*1000))*1.0f/1000;  
        }  
        return scale;  
    }  
      
    public static void main(String[] args)  {  
        Test planeHistogram = new Test();  
  
        BufferedImage image = planeHistogram.paintPlaneHistogram("��ɫֱ��ͼ",  
                        new int[]{100,200,300}, new String[]{"a" , "b" , "c"} ,  new Color[] {Color.RED, Color.GREEN, Color.BLACK, Color.BLUE });  
        File output = new File("D:/333.jpg");  
        try {  
            ImageIO.write(image, "jpg", output);  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  
  
}  