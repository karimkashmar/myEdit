package myEdit_Client;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Toaster extends JFrame {

	//String of toast
	String s;

	// JWindow
	JWindow w;

	public Toaster(String s, int x, int y, int toastType)
	{
		w = new JWindow();

		// make the background transparent
		w.setBackground(new Color(0, 0, 0, 0));

		// create a panel
		JPanel p = new JPanel() {
			public void paintComponent(Graphics g)
			{
				// set size of text 
				Font font = g.getFont().deriveFont( 16.0f );
				g.setFont( font );
				
				int wid = g.getFontMetrics().stringWidth(s);
				int hei = g.getFontMetrics().getHeight();

				// draw the boundary of the toast and fill it
				
				if (toastType == 1) {
					g.setColor(Color.lightGray);
					g.fillRect(10, 10, wid + 30, hei + 10);
					g.setColor(Color.lightGray);
					g.drawRect(10, 10, wid + 30, hei + 10);
				} else if (toastType == 2) {
					g.setColor(Color.green);
					g.fillRect(10, 10, wid + 30, hei + 10);
					g.setColor(Color.green);
					g.drawRect(10, 10, wid + 30, hei + 10);
				} else if (toastType == 3) {
					g.setColor(Color.red);
					g.fillRect(10, 10, wid + 30, hei + 10);
					g.setColor(Color.red);
					g.drawRect(10, 10, wid + 30, hei + 10);
				}
				

				// set the color of text
				g.setColor(Color.black);
				g.drawString(s, 25, 27);
				int t = 250;

				// draw the shadow of the toast
				for (int i = 0; i < 10; i++) {
					t -= 25;
					g.setColor(new Color(0, 0, 0, t));
					g.drawRect(10 - i, 10 - i, wid + 30 + i * 2,
							hei + 10 + i * 2);
				}
			}
		};

		w.add(p);
		w.setLocation(x, y);
		w.setSize(500, 150);
	}

	// function to pop up the toast
	void ShowToast(int duration)
	{
		try {
			w.setOpacity(1);
			w.setVisible(true);

			// wait for some time
			Thread.sleep(duration);

			// make the message disappear  slowly
			for (double d = 1.0; d > 0.2; d -= 0.1) {
				Thread.sleep(100);
				w.setOpacity((float)d);
			}

			// set the visibility to false
			w.setVisible(false);
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}