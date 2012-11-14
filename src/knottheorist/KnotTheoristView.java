/*
 * KnotTheoristView.java
 */
package knottheorist;

import java.awt.GridLayout;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import org.jdesktop.application.Action;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import java.util.Random;
import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * The application's main frame.
 */
public class KnotTheoristView extends FrameView {

    private static final int GRID_COLS = 14;
    private static final int GRID_ROWS = 14;
    private static final int SQUARE_WIDTH = 31;
    private static final int SQUARE_HEIGHT = 31;
    private KnotGrid grid = null;
    public Icon[] knotIcons = null;
    //public Icon[] btnColorsDown = null;
    public Random rng = new Random();
    public Knot knot = null;
    public JPanel gridPanel = null;

    public void addKnotGrid() {
        this.grid = new KnotGrid((Integer) spinCols.getValue(), (Integer) spinRows.getValue());

        grid.init(knotIcons, rng, this, SQUARE_WIDTH, SQUARE_HEIGHT);

        GridLayout gridLayout = new GridLayout(grid.rows, grid.cols, 0, 0);
        JPanel gridPanel = new JPanel(gridLayout);
        gridPanel.setSize(grid.cols * SQUARE_WIDTH, grid.rows * SQUARE_HEIGHT);

        Random rng = new Random();

        for (int y = 0; y < grid.rows; y++) {
            for (int x = 0; x < grid.cols; x++) {
                gridPanel.add(grid.squares[x][y].body);
            }
        }

//        jScrollPane2.add(gridPanel);
        //mainPanel.add(gridPanel);
        jPanel1.add(gridPanel);
        jPanel1.setMinimumSize(gridPanel.getSize());
        jPanel1.setPreferredSize(gridPanel.getSize());

        this.gridPanel = gridPanel;
    }

    public void addExistingKnotGrid(KnotGrid knotGrid) {
        this.grid = knotGrid;

        GridLayout gridLayout = new GridLayout(grid.rows, grid.cols, 0, 0);
        JPanel gridPanel = new JPanel(gridLayout);
        gridPanel.setSize(grid.cols * SQUARE_WIDTH, grid.rows * SQUARE_HEIGHT);

        Random rng = new Random();

        for (int y = 0; y < grid.rows; y++) {
            for (int x = 0; x < grid.cols; x++) {
                gridPanel.add(grid.squares[x][y].body);
            }
        }

//        jScrollPane2.add(gridPanel);
//        jScrollPane2.updateUI();
//        jScrollPane2.validate();
//        mainPanel.add(gridPanel);
        jPanel1.add(gridPanel);
        jPanel1.setMinimumSize(gridPanel.getSize());
        jPanel1.setPreferredSize(gridPanel.getSize());
        this.gridPanel = gridPanel;
    }

    public KnotTheoristView(SingleFrameApplication app) {
        super(app);

        initComponents();

        // status bar initialization - message timeout, idle icon and busy animation, etc
        ResourceMap resourceMap = getResourceMap();




        knotIcons = new Icon[9];
        knotIcons[0] = resourceMap.getIcon("KnotGrid.knotIcons[0]");
        knotIcons[1] = resourceMap.getIcon("KnotGrid.knotIcons[1]");
        knotIcons[2] = resourceMap.getIcon("KnotGrid.knotIcons[2]");
        knotIcons[3] = resourceMap.getIcon("KnotGrid.knotIcons[3]");
        knotIcons[4] = resourceMap.getIcon("KnotGrid.knotIcons[4]");
        knotIcons[5] = resourceMap.getIcon("KnotGrid.knotIcons[5]");
        knotIcons[6] = resourceMap.getIcon("KnotGrid.knotIcons[6]");
        knotIcons[7] = resourceMap.getIcon("KnotGrid.knotIcons[7]");
        knotIcons[8] = resourceMap.getIcon("KnotGrid.knotIcons[8]");

        for (int i = 0; i < 9; i++) {
        }
        /*        btnColorsDown = new Icon[10];
        btnColorsDown[0] = resourceMap.getIcon("KnotGrid.knotIconsDown[0]");
        btnColorsDown[1] = resourceMap.getIcon("KnotGrid.knotIconsDown[1]");
        btnColorsDown[2] = resourceMap.getIcon("KnotGrid.knotIconsDown[2]");
        btnColorsDown[3] = resourceMap.getIcon("KnotGrid.knotIconsDown[3]");
        btnColorsDown[4] = resourceMap.getIcon("KnotGrid.knotIconsDown[4]");
        btnColorsDown[5] = resourceMap.getIcon("KnotGrid.knotIconsDown[5]");
        btnColorsDown[6] = resourceMap.getIcon("KnotGrid.knotIconsDown[6]");
        btnColorsDown[7] = resourceMap.getIcon("KnotGrid.knotIconsDown[7]");
        btnColorsDown[8] = resourceMap.getIcon("KnotGrid.knotIconsDown[8]");
        btnColorsDown[9] = resourceMap.getIcon("KnotGrid.knotIconsDown[9]");*/
        addKnotGrid();
    }

    @Action
    public void showAboutBox() {
        if (aboutBox == null) {
            JFrame mainFrame = KnotTheoristApp.getApplication().getMainFrame();
            aboutBox = new KnotTheoristAboutBox(mainFrame);
            aboutBox.setLocationRelativeTo(mainFrame);
        }
        KnotTheoristApp.getApplication().show(aboutBox);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        jInternalFrame1 = new javax.swing.JInternalFrame();
        editRepresentation = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        editModified = new javax.swing.JTextField();
        btnRemLoops = new javax.swing.JButton();
        btnRemSwaps = new javax.swing.JButton();
        btnDowngrade = new javax.swing.JButton();
        bntSHU = new javax.swing.JButton();
        btnSHR = new javax.swing.JButton();
        btnSHD = new javax.swing.JButton();
        btnSHL = new javax.swing.JButton();
        btnRegurgitate = new javax.swing.JButton();
        spinCols = new javax.swing.JSpinner();
        spinRows = new javax.swing.JSpinner();
        btnDigest = new javax.swing.JButton();
        btnToNotesBottom = new javax.swing.JButton();
        btnToNotesTop = new javax.swing.JButton();
        btnTris = new javax.swing.JButton();
        btnFirstTri = new javax.swing.JButton();
        btnRandTri = new javax.swing.JButton();
        btnAllTri = new javax.swing.JButton();
        btnSolve = new javax.swing.JButton();
        btnValidate = new javax.swing.JButton();
        btnFull12 = new javax.swing.JButton();
        btnRandomFlip = new javax.swing.JButton();
        btnFlipOver = new javax.swing.JButton();
        btnFlipBack = new javax.swing.JButton();
        btnCopy = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu fileMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        javax.swing.JMenu helpMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem aboutMenuItem = new javax.swing.JMenuItem();

        mainPanel.setName("mainPanel"); // NOI18N

        jInternalFrame1.setName("jInternalFrame1"); // NOI18N
        jInternalFrame1.setVisible(true);

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(knottheorist.KnotTheoristApp.class).getContext().getResourceMap(KnotTheoristView.class);
        editRepresentation.setText(resourceMap.getString("editRepresentation.text")); // NOI18N
        editRepresentation.setName("editRepresentation"); // NOI18N

        jButton1.setText(resourceMap.getString("jButton1.text")); // NOI18N
        jButton1.setName("jButton1"); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        btnClear.setText(resourceMap.getString("btnClear.text")); // NOI18N
        btnClear.setName("btnClear"); // NOI18N
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });

        editModified.setText(resourceMap.getString("editModified.text")); // NOI18N
        editModified.setName("editModified"); // NOI18N

        btnRemLoops.setText(resourceMap.getString("btnRemLoops.text")); // NOI18N
        btnRemLoops.setName("btnRemLoops"); // NOI18N
        btnRemLoops.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemLoopsActionPerformed(evt);
            }
        });

        btnRemSwaps.setText(resourceMap.getString("btnRemSwaps.text")); // NOI18N
        btnRemSwaps.setName("btnRemSwaps"); // NOI18N
        btnRemSwaps.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemSwapsActionPerformed(evt);
            }
        });

        btnDowngrade.setText(resourceMap.getString("btnDowngrade.text")); // NOI18N
        btnDowngrade.setToolTipText(resourceMap.getString("btnDowngrade.toolTipText")); // NOI18N
        btnDowngrade.setName("btnDowngrade"); // NOI18N
        btnDowngrade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDowngradeActionPerformed(evt);
            }
        });

        bntSHU.setText(resourceMap.getString("bntSHU.text")); // NOI18N
        bntSHU.setName("bntSHU"); // NOI18N
        bntSHU.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntSHUActionPerformed(evt);
            }
        });

        btnSHR.setText(resourceMap.getString("btnSHR.text")); // NOI18N
        btnSHR.setName("btnSHR"); // NOI18N
        btnSHR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSHRActionPerformed(evt);
            }
        });

        btnSHD.setText(resourceMap.getString("btnSHD.text")); // NOI18N
        btnSHD.setName("btnSHD"); // NOI18N
        btnSHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSHDActionPerformed(evt);
            }
        });

        btnSHL.setText(resourceMap.getString("btnSHL.text")); // NOI18N
        btnSHL.setName("btnSHL"); // NOI18N
        btnSHL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSHLActionPerformed(evt);
            }
        });

        btnRegurgitate.setText(resourceMap.getString("btnRegurgitate.text")); // NOI18N
        btnRegurgitate.setName("btnRegurgitate"); // NOI18N
        btnRegurgitate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegurgitateActionPerformed(evt);
            }
        });

        spinCols.setName("spinCols"); // NOI18N
        spinCols.setValue(GRID_COLS);

        spinRows.setName("spinRows"); // NOI18N
        spinRows.setValue(GRID_ROWS);

        btnDigest.setText(resourceMap.getString("btnDigest.text")); // NOI18N
        btnDigest.setToolTipText(resourceMap.getString("btnDigest.toolTipText")); // NOI18N
        btnDigest.setName("btnDigest"); // NOI18N
        btnDigest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDigestActionPerformed(evt);
            }
        });

        btnToNotesBottom.setText(resourceMap.getString("btnToNotesBottom.text")); // NOI18N
        btnToNotesBottom.setName("btnToNotesBottom"); // NOI18N
        btnToNotesBottom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnToNotesBottomActionPerformed(evt);
            }
        });

        btnToNotesTop.setText(resourceMap.getString("btnToNotesTop.text")); // NOI18N
        btnToNotesTop.setName("btnToNotesTop"); // NOI18N
        btnToNotesTop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnToNotesTopActionPerformed(evt);
            }
        });

        btnTris.setText(resourceMap.getString("btnTris.text")); // NOI18N
        btnTris.setName("btnTris"); // NOI18N
        btnTris.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTrisActionPerformed(evt);
            }
        });

        btnFirstTri.setText(resourceMap.getString("btnFirstTri.text")); // NOI18N
        btnFirstTri.setName("btnFirstTri"); // NOI18N
        btnFirstTri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFirstTriActionPerformed(evt);
            }
        });

        btnRandTri.setText(resourceMap.getString("btnRandTri.text")); // NOI18N
        btnRandTri.setName("btnRandTri"); // NOI18N
        btnRandTri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRandTriActionPerformed(evt);
            }
        });

        btnAllTri.setText(resourceMap.getString("btnAllTri.text")); // NOI18N
        btnAllTri.setName("btnAllTri"); // NOI18N
        btnAllTri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAllTriActionPerformed(evt);
            }
        });

        btnSolve.setText(resourceMap.getString("btnSolve.text")); // NOI18N
        btnSolve.setName("btnSolve"); // NOI18N
        btnSolve.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSolveActionPerformed(evt);
            }
        });

        btnValidate.setText(resourceMap.getString("btnValidate.text")); // NOI18N
        btnValidate.setName("btnValidate"); // NOI18N
        btnValidate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnValidateActionPerformed(evt);
            }
        });

        btnFull12.setText(resourceMap.getString("btnFull12.text")); // NOI18N
        btnFull12.setName("btnFull12"); // NOI18N
        btnFull12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFull12ActionPerformed(evt);
            }
        });

        btnRandomFlip.setText(resourceMap.getString("btnRandomFlip.text")); // NOI18N
        btnRandomFlip.setName("btnRandomFlip"); // NOI18N
        btnRandomFlip.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRandomFlipActionPerformed(evt);
            }
        });

        btnFlipOver.setText(resourceMap.getString("btnFlipOver.text")); // NOI18N
        btnFlipOver.setName("btnFlipOver"); // NOI18N
        btnFlipOver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFlipOverActionPerformed(evt);
            }
        });

        btnFlipBack.setText(resourceMap.getString("btnFlipBack.text")); // NOI18N
        btnFlipBack.setName("btnFlipBack"); // NOI18N
        btnFlipBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFlipBackActionPerformed(evt);
            }
        });

        btnCopy.setText(resourceMap.getString("btnCopy.text")); // NOI18N
        btnCopy.setName("btnCopy"); // NOI18N
        btnCopy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCopyActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jInternalFrame1Layout = new javax.swing.GroupLayout(jInternalFrame1.getContentPane());
        jInternalFrame1.getContentPane().setLayout(jInternalFrame1Layout);
        jInternalFrame1Layout.setHorizontalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jInternalFrame1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jInternalFrame1Layout.createSequentialGroup()
                        .addComponent(editModified, javax.swing.GroupLayout.DEFAULT_SIZE, 887, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnClear))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jInternalFrame1Layout.createSequentialGroup()
                        .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(editRepresentation, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 784, Short.MAX_VALUE)
                            .addGroup(jInternalFrame1Layout.createSequentialGroup()
                                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jInternalFrame1Layout.createSequentialGroup()
                                        .addComponent(btnRemLoops)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnRemSwaps))
                                    .addComponent(btnToNotesTop)
                                    .addComponent(btnFull12))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnRandTri)
                                    .addComponent(btnRandomFlip, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jInternalFrame1Layout.createSequentialGroup()
                                        .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jInternalFrame1Layout.createSequentialGroup()
                                                .addComponent(btnTris)
                                                .addGap(47, 47, 47)
                                                .addComponent(btnSolve)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnValidate)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 149, Short.MAX_VALUE)
                                                .addComponent(btnToNotesBottom)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnDigest))
                                            .addGroup(jInternalFrame1Layout.createSequentialGroup()
                                                .addComponent(btnFirstTri)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnAllTri)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 291, Short.MAX_VALUE)
                                                .addComponent(btnCopy)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnFlipBack)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(btnFlipOver)
                                            .addComponent(btnDowngrade))))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jInternalFrame1Layout.createSequentialGroup()
                                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(spinRows, javax.swing.GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE)
                                    .addComponent(spinCols))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btnSHD, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jInternalFrame1Layout.createSequentialGroup()
                                        .addComponent(btnSHL, 0, 0, Short.MAX_VALUE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnSHR, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(bntSHU, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(btnRegurgitate, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jButton1)))))
                .addContainerGap())
        );
        jInternalFrame1Layout.setVerticalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jInternalFrame1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(editRepresentation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jInternalFrame1Layout.createSequentialGroup()
                        .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(bntSHU)
                            .addComponent(spinCols, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnToNotesTop)
                            .addComponent(btnRandomFlip))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnSHR)
                            .addComponent(btnSHL)
                            .addComponent(spinRows, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jInternalFrame1Layout.createSequentialGroup()
                        .addComponent(btnRandTri)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jInternalFrame1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btnFirstTri)
                                .addComponent(btnAllTri))
                            .addComponent(btnFull12)
                            .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btnFlipOver)
                                .addComponent(btnFlipBack)
                                .addComponent(btnCopy)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnRemLoops, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btnRemSwaps)
                                .addComponent(btnRegurgitate)
                                .addComponent(btnDowngrade)
                                .addComponent(btnDigest)
                                .addComponent(btnToNotesBottom)
                                .addComponent(btnTris)
                                .addComponent(btnSolve)
                                .addComponent(btnValidate)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(jInternalFrame1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSHD)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnClear)
                    .addComponent(editModified, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(13, Short.MAX_VALUE))
        );

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        jPanel1.setName("jPanel1"); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 950, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 240, Short.MAX_VALUE)
        );

        jScrollPane2.setViewportView(jPanel1);

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 968, Short.MAX_VALUE)
                    .addComponent(jInternalFrame1, javax.swing.GroupLayout.Alignment.LEADING))
                .addContainerGap())
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jInternalFrame1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        menuBar.setName("menuBar"); // NOI18N

        fileMenu.setText(resourceMap.getString("fileMenu.text")); // NOI18N
        fileMenu.setName("fileMenu"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(knottheorist.KnotTheoristApp.class).getContext().getActionMap(KnotTheoristView.class, this);
        exitMenuItem.setAction(actionMap.get("quit")); // NOI18N
        exitMenuItem.setName("exitMenuItem"); // NOI18N
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        jMenu1.setText(resourceMap.getString("jMenu1.text")); // NOI18N
        jMenu1.setName("jMenu1"); // NOI18N

        jMenuItem1.setText(resourceMap.getString("jMenuItem1.text")); // NOI18N
        jMenuItem1.setName("jMenuItem1"); // NOI18N
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setText(resourceMap.getString("jMenuItem2.text")); // NOI18N
        jMenuItem2.setName("jMenuItem2"); // NOI18N
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        menuBar.add(jMenu1);

        helpMenu.setText(resourceMap.getString("helpMenu.text")); // NOI18N
        helpMenu.setName("helpMenu"); // NOI18N

        aboutMenuItem.setAction(actionMap.get("showAboutBox")); // NOI18N
        aboutMenuItem.setName("aboutMenuItem"); // NOI18N
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        setComponent(mainPanel);
        setMenuBar(menuBar);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.knot = grid.readGrid(Knot.REP_BKa);
        this.editRepresentation.setText(knot.toString());
        editModified.setText(knot.toString());
    }//GEN-LAST:event_jButton1ActionPerformed

private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
    if (gridPanel != null) {
        knot = null;
        //mainPanel.remove(gridPanel);
//        jScrollPane2.remove(gridPanel);
        jPanel1.remove(gridPanel);
        addKnotGrid();
        editRepresentation.setText("");
        editModified.setText("");
        this.mainPanel.revalidate();
//        jScrollPane2.revalidate();
//        jScrollPane2.updateUI();//jScrollPane2.validate()
//        jScrollPane2.validate();
    }//mainPanel.revalidate();jScrollPane2.valid
}//GEN-LAST:event_btnClearActionPerformed

private void btnRemLoopsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemLoopsActionPerformed
    if (knot != null) {
        knot.remLoops();
        knot.represent();
        editModified.setText(knot.toString());
    }
}//GEN-LAST:event_btnRemLoopsActionPerformed

private void btnRemSwapsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemSwapsActionPerformed
    if (knot != null) {
        knot.remSwaps();
        knot.represent();
        editModified.setText(knot.toString());
    }
}//GEN-LAST:event_btnRemSwapsActionPerformed

private void btnDowngradeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDowngradeActionPerformed
    if (knot != null) {
        knot.downgrade();
        knot.represent();
        editModified.setText(knot.toString());
    }
}//GEN-LAST:event_btnDowngradeActionPerformed

private void bntSHUActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntSHUActionPerformed
    grid.shu();
    gridPanel.repaint();
}//GEN-LAST:event_bntSHUActionPerformed

private void btnSHLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSHLActionPerformed
    grid.shl();
    gridPanel.repaint();
}//GEN-LAST:event_btnSHLActionPerformed

private void btnSHRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSHRActionPerformed
    grid.shr();
    gridPanel.repaint();
}//GEN-LAST:event_btnSHRActionPerformed

private void btnSHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSHDActionPerformed
    grid.shd();
    gridPanel.repaint();
}//GEN-LAST:event_btnSHDActionPerformed

private void btnRegurgitateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegurgitateActionPerformed
    if (gridPanel != null) {
        //mainPanel.remove(gridPanel);
//        jScrollPane2.remove(gridPanel);
        jPanel1.remove(gridPanel);
        final PreGrid preGrid = new PreGrid();

        new Thread(new Runnable() {

            public void run() {
                if (rForm != null) {
                    rForm.grid = preGrid;
                    preGrid.boxContinue = rForm.boxContinue;
                    preGrid.boxStep = rForm.boxStep;
                    preGrid.panel = rForm.panel;
                }
                preGrid.readKnot(knot);
                addExistingKnotGrid(preGrid.toKnotGrid(knotIcons, rng, KnotTheoristView.this, SQUARE_WIDTH, SQUARE_HEIGHT));
                KnotTheoristView.this.mainPanel.revalidate();
                KnotTheoristView.this.mainPanel.repaint();
//                jScrollPane2.revalidate();
//                jScrollPane2.updateUI();
//                jScrollPane2.validate();
//                jScrollPane2.repaint();
            }
        }).start();
        editRepresentation.setText("");
        editModified.setText("");
        this.mainPanel.revalidate();
        this.mainPanel.repaint();
//        jScrollPane2.revalidate();
//        jScrollPane2.updateUI();
//        jScrollPane2.validate();
//        jScrollPane2.repaint();
    }
}//GEN-LAST:event_btnRegurgitateActionPerformed
    public RegurgitationForm rForm = null;

private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
    if (rForm == null) {
        rForm = new RegurgitationForm();
        rForm.show();
    } else {
        rForm.show();
    }
}//GEN-LAST:event_jMenuItem1ActionPerformed

private void btnDigestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDigestActionPerformed
    ArrayList<HalfCrossing> crossings = new ArrayList<HalfCrossing>();
    String knotString = editModified.getText();
    if (knotString.isEmpty()) {
        knot = new Knot();
        return;
    }
    Knot result = new Knot();
    result.representMode = Knot.REP_ATL;
    if (knotString.indexOf("<") > -1) {
        result.representMode = Knot.REP_BKa;
    }
    if ("success".equals(result.digest(editModified.getText()))) {
        knot = result;
    } else {
        JOptionPane.showMessageDialog(mainPanel, result);
    }
}//GEN-LAST:event_btnDigestActionPerformed
    public NotesForm notes = new NotesForm();

private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
    if (notes != null) {
        notes.show();
    } else {
        notes = new NotesForm();
        notes.show();
    }
}//GEN-LAST:event_jMenuItem2ActionPerformed

private void btnToNotesTopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnToNotesTopActionPerformed
    notes.jTextArea1.setText(notes.jTextArea1.getText() + editRepresentation.getText() + "\n");
}//GEN-LAST:event_btnToNotesTopActionPerformed

private void btnToNotesBottomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnToNotesBottomActionPerformed
    notes.jTextArea1.setText(notes.jTextArea1.getText() + editModified.getText() + "\n");
}//GEN-LAST:event_btnToNotesBottomActionPerformed

private void btnTrisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTrisActionPerformed
    if (knot != null) {
        knot.tris();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < knot.tris.size(); i++) {
            for (HalfCrossing c : knot.tris.get(i)) {
                sb.append(c.name);
            }
            sb.append("\n");
        }
        JOptionPane.showMessageDialog(null, "Tris are as follows:\n" + sb.toString());
        knot.represent();
        editModified.setText(knot.toString());
    }
}//GEN-LAST:event_btnTrisActionPerformed

private void btnFirstTriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstTriActionPerformed
    if (knot != null) {
        knot.swapTri(knot.tris.get(0));
        knot.represent();
        editModified.setText(knot.toString());
    }
}//GEN-LAST:event_btnFirstTriActionPerformed
    public Random r = new Random();

private void btnRandTriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRandTriActionPerformed
    if (knot != null) {
        knot.swapTri(knot.tris.get(r.nextInt(knot.tris.size())));
        knot.represent();
        editModified.setText(knot.toString());
    }
}//GEN-LAST:event_btnRandTriActionPerformed

private void btnAllTriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAllTriActionPerformed
    if (knot != null) {
        for (int i = 0; i < knot.tris.size(); i++) {
            knot.swapTri(knot.tris.get(i));
        }
        knot.represent();
        editModified.setText(knot.toString());
    }
}//GEN-LAST:event_btnAllTriActionPerformed

private void btnSolveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSolveActionPerformed
    if (knot != null) {
        knot.representMode = Knot.REP_NTL;
        HashMap<String, Knot> knots = new HashMap<String, Knot>();
        knot.branch(knots);
        BigInteger minWeight = null;
        Knot lightest = null;
        for (String s : knots.keySet()) {
            System.out.println("- " + s);
            BigInteger weight = knots.get(s).getWeight();
            if (lightest == null || weight.compareTo(minWeight) == -1) {
                lightest = knots.get(s);
                minWeight = weight;
            } else if (weight.compareTo(minWeight) == 0) {
                System.out.println("Equal weights!");
            }
        }
        System.out.println();
        System.out.println("Lightest: " + lightest.representation);
        System.out.println("at " + minWeight.toString());
        knot = lightest;
        knot.represent();
        editModified.setText(knot.toString());
    }
}//GEN-LAST:event_btnSolveActionPerformed

private void btnValidateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnValidateActionPerformed
    if (knot != null) {
        String result = knot.validate();
        if ("success".equals(result)) {
            JOptionPane.showMessageDialog(mainPanel, "Valid");
        } else {
            JOptionPane.showMessageDialog(mainPanel, result);
        }
    }
}//GEN-LAST:event_btnValidateActionPerformed

private void btnFull12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFull12ActionPerformed
    if (knot != null) {
        knot.full12();
        knot.represent();
        editModified.setText(knot.toString());
    }
}//GEN-LAST:event_btnFull12ActionPerformed

private void btnRandomFlipActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRandomFlipActionPerformed
    for (int x = 0; x < grid.cols; x++) {
        for (int y = 0; y < grid.rows; y++) {
            if (r.nextBoolean()) {
                grid.squares[x][y].flip();
            }
        }
    }
}//GEN-LAST:event_btnRandomFlipActionPerformed

private void btnFlipOverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFlipOverActionPerformed
    if (knot != null) {
        knot.flipOver();
        knot.represent();
        editModified.setText(knot.toString());
    }
}//GEN-LAST:event_btnFlipOverActionPerformed

private void btnFlipBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFlipBackActionPerformed
    if (knot != null) {
        knot.flipBack();
        knot.represent();
        editModified.setText(knot.toString());
    }
}//GEN-LAST:event_btnFlipBackActionPerformed

private void btnCopyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCopyActionPerformed
    if (knot != null) {
        knot = knot.copy();
        knot.represent();
        editModified.setText(knot.toString());
    }
}//GEN-LAST:event_btnCopyActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bntSHU;
    private javax.swing.JButton btnAllTri;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnCopy;
    private javax.swing.JButton btnDigest;
    private javax.swing.JButton btnDowngrade;
    private javax.swing.JButton btnFirstTri;
    private javax.swing.JButton btnFlipBack;
    private javax.swing.JButton btnFlipOver;
    private javax.swing.JButton btnFull12;
    private javax.swing.JButton btnRandTri;
    private javax.swing.JButton btnRandomFlip;
    private javax.swing.JButton btnRegurgitate;
    private javax.swing.JButton btnRemLoops;
    private javax.swing.JButton btnRemSwaps;
    private javax.swing.JButton btnSHD;
    private javax.swing.JButton btnSHL;
    private javax.swing.JButton btnSHR;
    private javax.swing.JButton btnSolve;
    private javax.swing.JButton btnToNotesBottom;
    private javax.swing.JButton btnToNotesTop;
    private javax.swing.JButton btnTris;
    private javax.swing.JButton btnValidate;
    private javax.swing.JTextField editModified;
    private javax.swing.JTextField editRepresentation;
    private javax.swing.JButton jButton1;
    private javax.swing.JInternalFrame jInternalFrame1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JSpinner spinCols;
    private javax.swing.JSpinner spinRows;
    // End of variables declaration//GEN-END:variables
//    private final Timer messageTimer;
//    private final Timer busyIconTimer;
//    private final Icon idleIcon;
//    private final Icon[] busyIcons = new Icon[15];
//    private int busyIconIndex = 0;
    private JDialog aboutBox;
}
