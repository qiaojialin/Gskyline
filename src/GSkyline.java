import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.function.LongToDoubleFunction;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.w3c.dom.css.ElementCSSInlineStyle;

import algorithm.PointWise;
import algorithm.UnitWise;
import common.result.ResultSet;

/**
 * GSkyline provides platform to implement and evaluate PointWise and UnitWise
 * @author thinkpad
 *
 */

public class GSkyline extends JFrame{
	
	//file location
	JPanel panel1;
	JTextField filepath;
	JButton open;
	
	//k
	JPanel panel2;
	JTextField kField;
	
	//algorithm
	JPanel panel3;
	JRadioButton pwise;
	JRadioButton uwise;
	
	//button
	JPanel panel4;
	JButton start;
	JButton end;
	
	//time and result
	JPanel panel5;
	JTextField time;
	JTextField result;
	JTextArea fullresult;
	
	public GSkyline() {
		setTitle("GSkyline Compute");
		setSize(370,300);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new FlowLayout());
		
		panel1 = new JPanel();
		panel1.setLayout(new BoxLayout(panel1, BoxLayout.LINE_AXIS));
		panel1.add(new JLabel("文件路径"));
		filepath = new JTextField();
		filepath.setColumns(20);
		panel1.add(filepath);
		open = new JButton("打开");
		open.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JFileChooser chooser = new JFileChooser();

				int returnVal = chooser.showOpenDialog(GSkyline.this);
			    if(returnVal == JFileChooser.APPROVE_OPTION) {
			  //  	FileNameExtensionFilter filter = new FileNameExtensionFilter("txt");
			  //  	chooser.setFileFilter(filter);
			    	File file = chooser.getSelectedFile();
			    	filepath.setText(file.getPath());
			    }
				
				
			}
		});
		panel1.add(open);
		add(panel1);
		
		panel2 = new JPanel();
		panel2.setLayout(new BoxLayout(panel2, BoxLayout.X_AXIS));
		panel2.add(new JLabel("输入k值"));
		kField = new JTextField();
		kField.setColumns(25);
		panel2.add(kField);
		add(panel2);
		
		panel3 = new JPanel();
		panel3.setLayout(new BoxLayout(panel3, BoxLayout.LINE_AXIS));
	//	panel3.setAlignmentX(LEFT_ALIGNMENT);
		panel3.add(new JLabel("算法选择"));
		panel3.add(Box.createHorizontalStrut(50));
		pwise = new JRadioButton("PointWise");
		panel3.add(pwise);
		panel3.add(Box.createHorizontalStrut(30));
		uwise = new JRadioButton("UnitWise");
		panel3.add(uwise);
		panel3.add(Box.createHorizontalStrut(40));
		ButtonGroup group = new ButtonGroup();
		group.add(pwise);
		group.add(uwise);
		add(panel3);
		
		panel4 = new JPanel();
		panel4.setLayout(new BoxLayout(panel4, BoxLayout.LINE_AXIS));
		start = new JButton("开始计算");
		
		start.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String path = filepath.getText();
				String kString = kField.getText();
				int k = 0;
				if(isInteger(kString)) {
					k = Integer.parseInt(kString);
					if(k < 2) {
						JOptionPane.showMessageDialog(null, "k的范围2-5");
						return;
					}
				}
				else {
					JOptionPane.showMessageDialog(null, "k是数字");
					return;
				}
				if (!new File(path).exists())
				{
					JOptionPane.showMessageDialog(null, "文件路径有误");
					return;
				}
				
				
				
				
				if(pwise.isSelected()) {
					
					
					PointWise tmPointWise = new PointWise();
					ResultSet resultSet2 = tmPointWise.pointWise(path, k);

					String dura = Long.toString(tmPointWise.time)+"ms";
					time.setText(dura);
					
					long resultsize = resultSet2.sGroups.size();
					result.setText(Long.toString(resultsize));
					
					String res = resultSet2.toString();
					fullresult.setText(res);
					
					
					
				}else if(uwise.isSelected()) {

					UnitWise tmUnitWise = new UnitWise();
					ResultSet resultSet2 = tmUnitWise.unitWise(path, k);

					String dura = Long.toString(tmUnitWise.time)+"ms";
					time.setText(dura);
					
					long resultsize = resultSet2.sGroups.size();
					result.setText(Long.toString(resultsize));
					
					String res = resultSet2.toString();
					fullresult.setText(res);
					
				}else {
					JOptionPane.showMessageDialog(null, "请选择一个算法");
				}
				// TODO Auto-generated method stub
				
			}
		});
		
		end = new JButton("全部清空");
		
		end.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				filepath.setText("");
				kField.setText("");
				result.setText("");
				fullresult.setText("");
				time.setText("");
				pwise.setSelected(false);
				uwise.setSelected(false);
				
			}
		});
		
		panel4.add(start);
		panel4.add(Box.createHorizontalStrut(45));
		panel4.add(end);
		add(panel4);
		
		panel5 = new JPanel();
		panel5.setLayout(new BoxLayout(panel5, BoxLayout.LINE_AXIS));
		time = new JTextField(8);
		panel5.add(new JLabel("运行时间"));
		panel5.add(Box.createHorizontalStrut(5));
		panel5.add(time);
		panel5.add(Box.createHorizontalStrut(35));
		result = new JTextField(8);
		
		panel5.add(new JLabel("结果数量"));
		panel5.add(Box.createHorizontalStrut(5));
		panel5.add(result);
		add(panel5);
		
		JPanel panel6 = new JPanel();
		panel6.setLayout(new BoxLayout(panel6, BoxLayout.LINE_AXIS));
		panel6.add(new JLabel("结果显示"));
		panel6.add(Box.createHorizontalStrut(5));
		fullresult = new JTextArea(6,25);
		JScrollPane scroll = new JScrollPane (fullresult, 
				   JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		panel6.add(scroll);
		add(panel6);
		
		
		
		
	}
	
	public boolean isInteger(String s) {
	    try { 
	        Integer.parseInt(s); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    } catch(NullPointerException e) {
	        return false;
	    }
	    // only got here if we didn't return false
	    return true;
	}

	
	

    public static void main(String[] args) {
    	GSkyline testwindow = new GSkyline();
    	testwindow.setVisible(true);
    	
        String path = "datasets\\corr_4.txt";
        int k = 3;

        
        
 //       ResultSet resultSet2 = new PointWise().pointWise(path, k);

 //       ResultSet resultSet = new UnitWise().unitWise(path, k);
               
        System.out.println(path+" "+"k"+k);

  //      resultSet.print();
  //      resultSet2.print();
    }
}
