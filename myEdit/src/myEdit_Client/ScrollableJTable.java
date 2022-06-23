package myEdit_Client;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ScrollableJTable extends JPanel {
	public static JTable table;
	
    public ScrollableJTable() {
        initializeUI();
    }

    private static void showFrame() {
        JPanel panel = new ScrollableJTable();
        panel.setOpaque(true);

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setTitle("Scrollable JTable");
        frame.setContentPane(panel);
        frame.pack();
        frame.setVisible(true);
        
        // Turn off JTable's auto resize so that JScrollPane will show a horizontal
        // scroll bar.
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ScrollableJTable::showFrame);
    }

    private void initializeUI() {
    	table = new JTable();
    	
        setLayout(new BorderLayout());



        JScrollPane pane = new JScrollPane(table);
        add(pane, BorderLayout.CENTER);
        
        table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mousePressed(e);
				Client.FileClicked(e);
			}
			
			
		});
    }

	public void setModel(DefaultTableModel filesTableDefaultModel) {
		table.setModel(filesTableDefaultModel);
	}

	public int getSelectedRow() {
		return table.getSelectedRow();
	}

	public int getSelectedColumn() {
		return table.getSelectedColumn();
	}

}