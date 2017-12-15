package com.calix.bseries.gui.panels;

import com.calix.bseries.gui.model.CalixB6Device;
import com.calix.system.gui.lookandfeel.LookAndFeelMgr;
import com.calix.system.gui.model.BaseEMSDevice;
import com.calix.system.gui.util.CalixMessageUtils;
import com.objectsavvy.base.gui.panels.ValueException;
import com.objectsavvy.base.gui.util.GuiUtil;
import com.objectsavvy.base.util.debug.Code;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JComponent;
import javax.swing.JDialog;
/**
 * bug 51355 fix by James Wang 
 * @author jawang
 * @Date 20111219
 */
public class BseriesWebCutThroughDialog implements Runnable {
    private BaseEMSDevice network = null;
    private JDialog dialog = null;
    private BseriesCutThroughWebPanel m_mainPanel = null;

    private static void initTheme() {
        LookAndFeelMgr.setLookAndFeel();
    }

    public BseriesWebCutThroughDialog(JComponent pParent, BaseEMSDevice network) {
        this.network = network;
        initTheme();
        new Thread(this).start();
    }

    @Override
    public void run() {
        String title = "CMS Cut-Through Web to " + network.getName();
        try {
            dialog = new JDialog(GuiUtil.getTopFrame(), true);
            dialog.setTitle(title);
            initPanel();
            dialog.add(m_mainPanel);
            dialog.pack();
            dialog.setModal(true);
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
            dialog.setAlwaysOnTop(true);
            isRunning = true;
            dialog.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    disconnect();
                }
            });
        } catch (ValueException e) {
//            e.printStackTrace();
            Code.warning(e.getMessage(), e);
        } catch (Exception ex) {
            CalixMessageUtils.showErrorMessage("Unable to launch browser. Please contact Calix Support.");
        }
    }

    private void initPanel() throws ValueException, Exception {
        m_mainPanel = new BseriesCutThroughWebPanel();
        m_mainPanel.setPreferredSize(new Dimension(1024, 768));
        m_mainPanel.populateFieldsFromData((CalixB6Device)network);
    }

    public void disconnect() {
        isRunning = false;
        dialog.dispose();
        m_mainPanel.destoryBrowser();
    }

    private boolean isRunning;

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }

    public BaseEMSDevice getNetwork() {
        return network;
    }

}
