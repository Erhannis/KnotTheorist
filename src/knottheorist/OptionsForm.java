/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * OptionsForm.java
 *
 * Created on Nov 17, 2012, 7:56:38 AM
 */
package knottheorist;

/**
 *
 * @author erhannis
 */
public class OptionsForm extends javax.swing.JFrame {
    public KnotTheoristView parent;
    
    /** Creates new form OptionsForm */
    public OptionsForm(KnotTheoristView parent) {
        this.parent = parent;
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        boxInvisibleGrid = new javax.swing.JCheckBox();

        setName("Form"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(knottheorist.KnotTheoristApp.class).getContext().getResourceMap(OptionsForm.class);
        boxInvisibleGrid.setText(resourceMap.getString("boxInvisibleGrid.text")); // NOI18N
        boxInvisibleGrid.setName("boxInvisibleGrid"); // NOI18N
        boxInvisibleGrid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boxInvisibleGridActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(boxInvisibleGrid)
                .addContainerGap(277, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(boxInvisibleGrid)
                .addContainerGap(264, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void boxInvisibleGridActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boxInvisibleGridActionPerformed
        if (boxInvisibleGrid.isSelected()) {
            parent.knotIcons = parent.knotIconsClean;
        } else {
            parent.knotIcons = parent.knotIconsGrid;
        }
        parent.grid.knotIcons = parent.knotIcons;
        parent.grid.refreshImages();
    }//GEN-LAST:event_boxInvisibleGridActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new OptionsForm(null).setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox boxInvisibleGrid;
    // End of variables declaration//GEN-END:variables
}
