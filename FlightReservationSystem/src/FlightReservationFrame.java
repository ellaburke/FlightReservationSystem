
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.UIManager;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import net.miginfocom.swing.MigLayout;

public class FlightReservationFrame extends JFrame implements ActionListener{

	private JPanel pnl = new JPanel(new MigLayout());
	private FlightReservationModel tableModel = new FlightReservationModel();
	private JTable tbl = new JTable(tableModel);
	private JLabel deptLbl = new JLabel("Departure");
	private JLabel destLbl = new JLabel("Destination");
	private JButton searchBtn = new JButton("Search");
	private JButton showBtn = new JButton("Show All");
	private JButton purchaseBtn = new JButton("Purchase");
	private JTextField ticketTxt = new JTextField(15);
	private String [] cities = {"Copenhagen", "Dublin", "Edinburgh", "London", 
			"New York", "Oslo", "San Francisco"};
	private JLabel ticketLbl = new JLabel("Number of Tickets");
	private TableRowSorter<TableModel> sorter = new TableRowSorter(tbl.getModel());
	private JComboBox deptCb = new JComboBox(cities);
	private JComboBox destCb = new JComboBox(cities);
	private JLabel dateLbl;
	private ArrayList<RowFilter<Object,Object>> filters = new ArrayList<RowFilter<Object,Object>>();
	private RowFilter filter1, filter2;
	private RowFilter<Object,Object> rowFilters;

	
	public FlightReservationFrame() {
		
		TableColumn column = null; 
    	for (int i = 0; i < tbl.getColumnCount(); i++) { 
    		column = tbl.getColumnModel().getColumn(i); 
    		column.setPreferredWidth(80); 
    		}
    
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date date = new Date();
		dateLbl = new JLabel("Today's Date: " + formatter.format(date));
		dateLbl.setForeground(Color.BLUE);
		
		tbl.setPreferredScrollableViewportSize(new Dimension(650, 80));
		tbl.setFillsViewportHeight(true);
		tbl.setRowSorter(sorter);
		
		JScrollPane scroll = new JScrollPane(tbl);
    	deptCb.setSelectedIndex(1);
		actionListeners();
		
		pnl.add(deptLbl, "center, split 6");
		pnl.add(deptCb);
		pnl.add(destLbl);
		pnl.add(destCb);
		pnl.add(searchBtn);
		pnl.add(showBtn, "wrap");
		pnl.add(scroll,"wrap");
		pnl.add(ticketLbl, "center, split 4");
		pnl.add(ticketTxt);
		pnl.add(purchaseBtn);
		pnl.add(dateLbl);
		
		setTitle("Flight Reservation");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(650,200);
		add(pnl);
		setVisible(true);
	}
	
	public void actionListeners(){
		searchBtn.addActionListener(this);
		showBtn.addActionListener(this);
		//Key Listener
		ticketTxt.addKeyListener(new KeyAdapter() { 
			public void keyTyped(KeyEvent key) { 
				char c = key.getKeyChar(); 
				if((!(Character.isDigit(c))) && (c != '\b')){ 
					key.consume(); 
				} 
			} 
		});
	}
	
	public void actionPerformed(ActionEvent e){
		String btnPressed = e.getActionCommand();
		
		if (btnPressed.equals("Search")) {
			tbl.setRowSorter(sorter);
			
			if(destCb.getSelectedIndex()== deptCb.getSelectedIndex()){
				String options[] = {"OK"};
				JOptionPane.showOptionDialog(null,
					    "Departure and destination cities cannot be the same.",
					    "Warning",
					    JOptionPane.ERROR_MESSAGE,
					    JOptionPane.QUESTION_MESSAGE,
					    UIManager.getIcon("OptionPane.warningIcon"),     
					    options,  
					    options[0]); 
			}
			else {	
				filters.clear();
				filter1 = RowFilter.regexFilter("(?i)" + cities[deptCb.getSelectedIndex()],0);
				filter2 = RowFilter.regexFilter("(?i)" + cities[destCb.getSelectedIndex()],1);
				filters.add(filter1);
				filters.add(filter2);
		        rowFilters = RowFilter.andFilter(filters);
		        sorter.setRowFilter(rowFilters);		        
			}
		}
		else if (btnPressed.equals("Show All")) {
			tbl.setRowSorter(null);			
		}
	}
		
		public static void main(String[] args) {
			new FlightReservationFrame(); 
		}
			
		
	
}
