/*
 * Created by JFormDesigner on Thu Jul 14 14:41:10 PDT 2011
 */

package com.calix.bseries.gui.tool;

import com.calix.bseries.gui.utils.B6SecurityHelper;
import com.calix.bseries.util.IPUtils;
import com.calix.system.gui.util.CalixMessageUtils;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

/**
 * @author Henning Els
 */
public class BseriesRangeDiscoveryPanel extends JPanel {
    public BseriesRangeDiscoveryPanel() {
        initComponents();
        setOpaque(false);
        scrollPane1.getViewport().setOpaque(false);
    }

    private void addActionPerformed(ActionEvent e) {
    	try {
    		String startIpStr  = startIp.getText().trim();
    		if ( !IPUtils.isValidIp(startIpStr) ){
    			throw new Exception("invalid IP address: " + startIpStr);
    		}

    		String endIpStr  = endIp.getText().trim();
    		if ( !IPUtils.isValidIp(endIpStr) ){
    			throw new Exception("invalid IP address: " + endIpStr);
    		}
    		
    		String netmaskStr  = netMask.getText().trim();
    		if ( !IPUtils.isValidIp(netmaskStr) ){
    			throw new Exception("invalid IP address: " + netmaskStr);
    		}
        
            String network1 = IPUtils.getNetworkAddr(startIpStr, netmaskStr);
            String network2 = IPUtils.getNetworkAddr(endIpStr, netmaskStr);
            if ( !network1.equals(network2) ){
                throw new Exception(startIpStr + " and " + endIpStr + " have different network addresses") ;
            }
            
            long startIpL = IPUtils.getAddrLong(startIpStr);
            long endIpL = IPUtils.getAddrLong(endIpStr);
            
            //check if endIp > startIp
            if (startIpL > endIpL){
                throw new Exception("startIP: [" + startIpStr + "] is greater than endIP: [" + endIpStr + "], \nyou need change order" );
            }
            
            DefaultTableModel tableModel = (DefaultTableModel)table1.getModel();
            
            // Check if duplicate IP range
            Vector vec = tableModel.getDataVector();
            for (int i = 0; i < vec.size(); i++) {
            	Object[] obj = ((Vector) vec.get(i)).toArray();
            	// This array should be composed by three variables: ip range, netmask and boolean. 
            	if (obj.length == 3 && obj[2]!=null) {
            		if ((startIpStr + "/" + endIpStr).equals(obj[0])
            				&& netmaskStr.equals(obj[1])
            				&& String.valueOf(checkBox1.isSelected()).equals(obj[2].toString())) {
            			throw new Exception("Duplicate with existing IP range: " +
            					"\n IP range: " + obj[0] +
            					"\n NetMask: " + obj[1] +
            					"\n Exclude: " + obj[2]);
            		}
            	}
            }
            
            tableModel.addRow(new Object[ ]{startIpStr + "/" + endIpStr,netmaskStr, checkBox1.isSelected()});
        } catch (Exception e1) {
            CalixMessageUtils.showMessageAndWait(e1.getMessage());
        }
    }

    private void removeActionPerformed(ActionEvent e) {
        DefaultTableModel tableModel = (DefaultTableModel)table1.getModel();
        tableModel.removeRow(table1.getSelectedRow());
    }
    
    public Vector getTableData(){
        return ((DefaultTableModel)table1.getModel()).getDataVector();
    }
    
    public String getReadCommunity(){
        return community.getText().trim();
    }
    
    public boolean getWhetherAutoChassis(){
        return checkBox_chassis.isSelected();
    }

    public boolean getAllowBseries(){
        return checkBox_Bseries.isSelected();
    }
    
    public boolean getAllowExa(){
        return checkBox_Exa.isSelected();
    }
    
    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        panel1 = new JPanel();
        
        label_Bseries = new JLabel();
        checkBox_Bseries = new JCheckBox();
        checkBox_Bseries.setSelected(true);
        if(!B6SecurityHelper.isCreate()) {
        	checkBox_Bseries.setSelected(false);
        	checkBox_Bseries.setEnabled(false);
          }        
        label_Exa = new JLabel();
        checkBox_Exa = new JCheckBox();
        checkBox_Exa.setSelected(true);
        
        label3 = new JLabel();
        startIp = new JTextField();
        label4 = new JLabel();
        endIp = new JTextField();
        label5 = new JLabel();
        netMask = new JTextField();
        label2 = new JLabel();
        checkBox1 = new JCheckBox();
        button1 = new JButton();
        button2 = new JButton();
        panel2 = new JPanel();
        scrollPane1 = new JScrollPane();
        table1 = new JTable();
        panel3 = new JPanel();
        label1 = new JLabel();
        community = new JTextField();
        CellConstraints cc = new CellConstraints();
        label_chassis = new JLabel();
        checkBox_chassis = new JCheckBox();

        //======== this ========
        setLayout(new BorderLayout());

        //======== panel1 ========
        {
            panel1.setBorder(new TitledBorder("IP for discovery"));
            panel1.setOpaque(false);
            panel1.setLayout(new FormLayout(
                "28dlu, $lcgap, 49dlu, $lcgap, 38dlu, $lcgap, 49dlu, $lcgap, 68dlu",
                "5dlu, 3*($lgap, 19dlu), $lgap, 1dlu, 2*($lgap, default)"));

            //---- label_Bseries ----
            label_Bseries.setText("BSERIES:");
            panel1.add(label_Bseries, cc.xy(1, 3));

            //---- checkBox_Bseries ----
            checkBox_Bseries.setText("");
            panel1.add(checkBox_Bseries, cc.xy(3, 3));

            //---- label_Exa ----
            label_Exa.setText("E5-306/308/520:");
            panel1.add(label_Exa, cc.xy(5, 3));

            //---- checkBox_Exa ----
            checkBox_Exa.setText("");
            panel1.add(checkBox_Exa, cc.xy(7, 3));
            
            //---- label3 ----
            label3.setText("IP Range:");
            panel1.add(label3, cc.xy(1, 5));

            //---- startIp ----
            startIp.setText("");
            panel1.add(startIp, cc.xy(3, 5));

            //---- label4 ----
            label4.setText("To");
            panel1.add(label4, cc.xy(5, 5));

            //---- endIp ----
            endIp.setText("");
            panel1.add(endIp, cc.xy(7, 5));

            //---- label5 ----
            label5.setText("Netmask:");
            panel1.add(label5, cc.xy(1, 7));

            //---- netMask ----
            netMask.setText("255.255.255.0");
            panel1.add(netMask, cc.xy(3, 7));

            //---- label2 ----
            label2.setText("Exclude:");
            panel1.add(label2, cc.xy(1, 11));
            panel1.add(checkBox1, cc.xy(3, 11));

            //---- button1 ----
            button1.setText("Add");
            button1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    addActionPerformed(e);
                }
            });
            panel1.add(button1, cc.xy(7, 13));

            //---- button2 ----
            button2.setText("Remove");
            button2.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    removeActionPerformed(e);
                }
            });
            panel1.add(button2, cc.xy(9, 13));
        }
        add(panel1, BorderLayout.NORTH);

        //======== panel2 ========
        {
            panel2.setOpaque(false);
            panel2.setBorder(LineBorder.createBlackLineBorder());
            panel2.setLayout(new FormLayout(
                "256dlu",
                "fill:193dlu"));

            //======== scrollPane1 ========
            {
                scrollPane1.setOpaque(false);

                //---- table1 ----
                table1.setModel(new DefaultTableModel(
                    new Object[][] {
                    },
                    new String[] {
                        "IP Range", "Netmask", "Exclude"
                    }
                ) {
                    Class<?>[] columnTypes = new Class<?>[] {
                        Object.class, Object.class, Boolean.class
                    };
                    boolean[] columnEditable = new boolean[] {
                        false, false, true
                    };
                    @Override
                    public Class<?> getColumnClass(int columnIndex) {
                        return columnTypes[columnIndex];
                    }
                    @Override
                    public boolean isCellEditable(int rowIndex, int columnIndex) {
                        return columnEditable[columnIndex];
                    }
                });
                {
                    TableColumnModel cm = table1.getColumnModel();
                    cm.getColumn(0).setPreferredWidth(50);
                    cm.getColumn(1).setPreferredWidth(50);
                    cm.getColumn(2).setPreferredWidth(50);
                }
                table1.setBorder(null);
                table1.setOpaque(false);
                scrollPane1.setViewportView(table1);
            }
            panel2.add(scrollPane1, cc.xy(1, 1));
        }
        add(panel2, BorderLayout.CENTER);

        //======== panel3 ========
        {
            panel3.setOpaque(false);
            panel3.setLayout(new FormLayout(
                "64dlu, $lcgap, 43dlu, $lcgap, 64dlu, $lcgap, 43dlu", 
                "17dlu"));

            //---- label1 ----
            label1.setText("Read Community:");
            panel3.add(label1, cc.xy(1, 1));

            //---- community ----
            community.setText("public");
            panel3.add(community, cc.xy(3, 1));
            
            //---- label_chassis ----
            label_chassis.setText("Discover Chassis Automatically:");
            checkBox_chassis.setSelected(true);
            panel3.add(label_chassis, cc.xy(5, 1));
            panel3.add(checkBox_chassis, cc.xy(7, 1));

        }
        add(panel3, BorderLayout.SOUTH);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JPanel panel1;
    
    private JLabel label_Bseries;
    private JCheckBox checkBox_Bseries;
    private JLabel label_Exa;
    private JCheckBox checkBox_Exa;
    
    private JLabel label3;
    private JTextField startIp;
    private JLabel label4;
    private JTextField endIp;
    private JLabel label5;
    private JTextField netMask;
    private JLabel label2;
    private JCheckBox checkBox1;
    private JButton button1;
    private JButton button2;
    private JPanel panel2;
    private JScrollPane scrollPane1;
    private JTable table1;
    private JPanel panel3;
    private JLabel label1;
    private JTextField community;
    private JLabel label_chassis;
    private JCheckBox checkBox_chassis;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
    
    public static void main(String[] args) throws Exception, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
        JFrame f = new JFrame();
        f.setTitle("Range Discovery");
        f.getContentPane().add(new BseriesRangeDiscoveryPanel());
        UIManager.setLookAndFeel(
                "com.sun.java.swing.plaf.windows.WindowsLookAndFeel");

        f.setSize(590, 600);
        f.setVisible(true);
    }
}
