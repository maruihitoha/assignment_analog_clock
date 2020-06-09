package analog_clock;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class analog_clock extends JFrame {
	
	public static JFrame frame;
	public double rad_num = (double)(Math.PI/ -6);
	public int clk_size = 400;
	public int center_x = 200;
	public int center_y = 200;
	
	Timer time = new Timer();
	TimeZone timeZone = TimeZone.getDefault();
	
	Calendar cal;
	
	int h, min, sec;
	
	Image double_buffer = null;
	Graphics buff = null;
	
	public analog_clock()
	{		
		super("아날로그 시계");
		frame = new JFrame();
		setSize(400,400);
		Container c = getContentPane();

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
		c.setBackground(Color.white);
		
		time.schedule(new TickTimerTask(), 0, 1000); //1초마다 다시 페인트.
        double_buffer = createImage(400,400);
		buff = double_buffer.getGraphics();
	}
	
	class TickTimerTask extends TimerTask
	{
		@Override
		public void run() 
		{
			// TODO Auto-generated method stub
			cal = (Calendar) Calendar.getInstance(timeZone);
			repaint();
		}		
	}
	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		// 실제 add된 컴포넌트가 paint되는 부분
		// 여기선 이중 버퍼링 용으로 buff 먼저
		super.paint(buff);

		write_num_main(buff);
										
		
		// 시간 가져오기
		h = cal.get(Calendar.HOUR);
		min = cal.get(Calendar.MINUTE);
		sec = cal.get(Calendar.SECOND);	

		draw_lines(buff,h,min,sec);

		g.drawImage(double_buffer,0,0,this);
	}

	// 숫자 출력
	private void write_num_main(Graphics g) 
	{
		// TODO Auto-generated method stub
		
		write_num_sub(g, rad_num*12, 12);
		
		for(int i = 1 ; i  < 12; i++)
		{		
			write_num_sub(g, rad_num*i, i);	
		}
	}
	
	// 숫자 출력 기능 
	private void write_num_sub(Graphics g, double angle, int hour) 
	{
		// TODO Auto-generated method stub
		double sin = (double)Math.sin(angle);
		double cosin = (double)Math.cos(angle);
		
		int num_x = (int)((clk_size/2-15-25) * (-sin));
		int num_y = (int)((clk_size/2-15-25) * (-cosin));		
		
		g.setFont(new Font("TimesRoman", Font.BOLD, 15));
		g.drawString(Integer.toString(hour),  num_x + center_x - 5, num_y + center_y + 5);
	}
	
	
	private void draw_lines(Graphics g, double hour, double minute, double second) 
	{
		// TODO Auto-generated method stub
		double sec_pos = (second*6)*(Math.PI)/180;
		double min_pos = ((minute + (second / 60)) * 6) * (Math.PI) / 180;
		double h_pos = ((hour + (minute / 60)) * 30) * (Math.PI) / 180;
			
		g.setColor(Color.RED);
		g.drawLine(center_x, center_y, center_x + (int) (140 * Math.cos(sec_pos - (Math.PI / 2))), 
				center_y + (int) (140 * Math.sin(sec_pos - (Math.PI / 2))));
		
		g.setColor(Color.BLACK);
		g.drawLine(center_x, center_y, center_x + (int) (120 * Math.cos(min_pos - (Math.PI / 2))), 
				center_y + (int) (120 * Math.sin(min_pos - (Math.PI / 2))));
		
		g.drawLine(center_x, center_y, center_x + (int) (80 * Math.cos(h_pos - (Math.PI / 2))), 
				center_y + (int) (80 * Math.sin(h_pos - (Math.PI / 2))));
		
		g.drawOval(center_x - 5, center_y - 5 , 10, 10);
		g.drawOval(center_x - 170, center_y - 170 , 340, 340);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new analog_clock();  
	}
	
	
}
