package com.calix.bseries.gui.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.calix.bseries.gui.model.B6Settings;
import com.calix.system.common.constants.CalixConstants;
import com.calix.system.gui.components.panels.CalixFormGeneratorPanel;
import com.calix.system.gui.lookandfeel.LookAndFeelMgr;
import com.calix.system.gui.util.CalixMessageUtils;
import com.objectsavvy.base.gui.panels.ConstrainedStringModel;
import com.objectsavvy.base.gui.panels.IValueModel;
import com.objectsavvy.base.gui.panels.OSDocument;
import com.objectsavvy.base.gui.panels.ParameterHolder;
import com.objectsavvy.base.gui.panels.ValueException;
import com.objectsavvy.base.persistence.OSBaseAction;
import com.objectsavvy.base.persistence.meta.Attribute;
import com.objectsavvy.base.persistence.meta.IValueType;
import com.objectsavvy.base.persistence.meta.RecordType;
import com.objectsavvy.base.persistence.meta.TypeRegistry;

public class B6SettingsPanel extends CalixFormGeneratorPanel {
	
	protected JLabel warning = new JLabel(""); //CMS-12190, by ivy
        
    public B6SettingsPanel() {
        super("default B6 setting", "DEFAULT B6 LOGIN", null, true);
        m_customNonVisual = true;
        setDesiredWidth(CalixConstants.DEFAULT_FORM_WIDTH);
        setIdentified(false);
        
        //CMS-12190, by ivy
        warning.setText(CalixConstants.WARNING_LABEL);
        Font f = LookAndFeelMgr.getTableFont().deriveFont(Font.BOLD, 15);
        warning.setFont(f);
        warning.setOpaque(true);
        warning.setBackground(Color.yellow);
        add(warning,BorderLayout.SOUTH);
    }

    @Override
    protected RecordType getRecordType() {
        return TypeRegistry.getInstance().getRecordType("B6Settings");
    }
    
    @Override
    protected JComponent getEditingComponent(IValueType type,   Attribute attr) throws ValueException {
        if (B6Settings.ATTR_WEBPASSWORD.equals(attr.getName()) || 
                B6Settings.ATTR_CLIPASSWORD.equals(attr.getName()) ||
                B6Settings.ATTR_READCOMMUNITY.equals(attr.getName()) ||
                B6Settings.ATTR_WRITECOMMUNITY.equals(attr.getName()) ||
                B6Settings.ATTR_ENABLEPASSWORD.equals(attr.getName())){
            JPasswordField passwordField = new JPasswordField();
            IValueModel model = new ConstrainedStringModel(type, attr, passwordField);
            ((JTextField) passwordField).setDocument((OSDocument) model);
            return passwordField;
        }else {
            return super.getEditingComponent(type, attr);
        }
    }
    
    @Override
    public void populateDataFromFields(java.util.List<OSBaseAction> pActions) throws ValueException {	
    	super.populateDataFromFields(pActions);
    	for (int iCount = 0; iCount < m_params.length; iCount++) {
				ParameterHolder holder = ((ParameterHolder) m_params[iCount]);
				if((holder!= null && "AnaSocketProtocol".equals(holder.getName()) && holder.isModified())||((holder!= null && "AnaSocketPort".equals(holder.getName()) && holder.isModified()))){
                    if (CalixMessageUtils.showYesNoDialog( "Warning: Modify Ana Socket Protocol or Ana Socket Port will temporarily make B6 service interrupted! Do you want to continue ?") == JOptionPane.NO_OPTION)
						throw new ValueException("Cannot modify \"Ana Socket Protocol\" \"Ana Socket Port\"", 2);
                    break;
				}				
		}
	}
}