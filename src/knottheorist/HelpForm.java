/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * HelpFrame.java
 *
 * Created on Nov 2, 2012, 5:06:47 AM
 */
package knottheorist;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowStateListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JViewport;
import javax.swing.ScrollPaneConstants;
import javax.swing.ScrollPaneLayout;
import javax.swing.Scrollable;
import javax.swing.SwingUtilities;

/**
 *
 * @author erhannis
 */
public class HelpForm extends javax.swing.JFrame {
    
    /**
     * resourceFileName should be the name of the file in your resources folder,
     * like "help.html".
     * @param resourceFileName
     * @return 
     */
    public static HelpForm makeHelpFromResource(String resourceFileName) {
        String helpText = "Error.  Help resource could not be loaded.";
        try {
            InputStream is = KnotTheoristView.class.getResourceAsStream("resources/" + resourceFileName);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line = null;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            helpText = sb.toString();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(KnotTheoristView.class.getName()).log(Level.SEVERE, null, ex);
        }   catch (IOException ex) {
            Logger.getLogger(KnotTheoristView.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new HelpForm(helpText);
    }
    
    public int labelHeight = 0;
    public JLabel labelHelp;
    public ScrollPaneWidthTrackingPanel jPanel1;
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (labelHeight != labelHelp.getHeight()) {
            labelHeight = labelHelp.getHeight();
            //jPanel1.setPreferredSize(new Dimension(400, labelHeight + 100));
        }
    }
    
    /**
     * Thanks to Russ Hayward on StackOverflow.com for this ScrollPaneWidthTrackingPanel class.
     */
    public class ScrollPaneWidthTrackingPanel extends JPanel implements Scrollable {

        private static final long serialVersionUID = 1L;
        public Component component;

        public ScrollPaneWidthTrackingPanel(LayoutManager layoutManager, Component component) {
            super(layoutManager);
            this.component = component;
        }

        public Dimension getPreferredScrollableViewportSize() {
            return getPreferredSize();
        }

        public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
            return Math.max(visibleRect.height * 9 / 10, 1);
        }

        public boolean getScrollableTracksViewportHeight() {
            if (getParent() instanceof JViewport) {
                JViewport viewport = (JViewport) getParent();
                return component.getPreferredSize().height < viewport.getHeight();
            }
            return false;
        }

        public boolean getScrollableTracksViewportWidth() {
            return true;
        }

        public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
            return Math.max(visibleRect.height / 10, 1);
        }
    }
    
    /** Creates new form HelpFrame */
    public HelpForm(String helpText) {
        initComponents();
        
//        jLabel1.getText();
        labelHelp = new JLabel();
        labelHelp.setVerticalAlignment(JLabel.TOP);
        jPanel1 = new ScrollPaneWidthTrackingPanel(new GridLayout(0, 1), labelHelp);
        jPanel1.add(labelHelp);
        jScrollPane1.setViewportView(jPanel1);
        //labelHelp.setSize(new Dimension(100,25));
        //this.validate();
        labelHelp.setText(helpText);
        //labelHelp.setLayout(new FlowLayout());
        //Dimension d = new Dimension(400, 500);
        //jPanel1.setMaximumSize(d);
        //jPanel1.setPreferredSize(new Dimension(400, 1000));
        //jScrollPane1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        //((ScrollPaneLayout)jScrollPane1.getLayout()).
        
        jPanel1.addComponentListener(new ComponentListener() {

            public void componentResized(ComponentEvent e) {
                //System.out.println(labelHelp.getSize().height);
                //jPanel1.setPreferredSize(new Dimension(400, labelHelp.getSize().height + 100));
                //Aaaugh, this is a pain.  Why can't it layout BEFORE here?
//                SwingUtilities.invokeLater(new Runnable() {
//                    public void run() {
//                        jPanel1.setPreferredSize(new Dimension(400, labelHelp.getSize().height + 100));   
//                    }
//                });
            }

            public void componentMoved(ComponentEvent e) {
                
            }

            public void componentShown(ComponentEvent e) {
                
            }

            public void componentHidden(ComponentEvent e) {
                
            }
            
        });
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("knottheorist/resources/HelpForm"); // NOI18N
        setTitle(bundle.getString("Form.title")); // NOI18N
        setName("Form"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 884, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
        );

        setSize(new java.awt.Dimension(902, 647));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(HelpForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(HelpForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(HelpForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HelpForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new HelpForm("").setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
