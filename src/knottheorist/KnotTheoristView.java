/*
 * KnotTheoristView.java
 */
package knottheorist;

import java.awt.Component;
import java.awt.Container;
import java.awt.GridLayout;
import java.util.ArrayList;
import org.jdesktop.application.Action;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import java.util.Random;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * The application's main frame.
 */
public class KnotTheoristView extends FrameView {

    public ResourceMap resourceMap;
    
    public static final int GRID_COLS = 14;
    public static final int GRID_ROWS = 14;
    public int SQUARE_WIDTH = 31;
    public int SQUARE_HEIGHT = 31;
    public static final boolean DEBUG_INFO = false;
    public KnotGrid grid = null;
    public Icon[] knotIcons = null;
    public Icon[] knotIconsGrid = null;
    public Icon[] knotIconsClean = null;
    public Icon[] knotIconsBWNoGrid = null;
    public Icon[] knotIconsMinimal = null;
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

    public void addKnotGrid(int cols, int rows) {
        this.grid = new KnotGrid(cols, rows);

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

        this.resourceMap = resourceMap;


        knotIconsGrid = new Icon[9];
        knotIconsGrid[0] = resourceMap.getIcon("KnotGrid.knotIconsGrid[0]");
        knotIconsGrid[1] = resourceMap.getIcon("KnotGrid.knotIconsGrid[1]");
        knotIconsGrid[2] = resourceMap.getIcon("KnotGrid.knotIconsGrid[2]");
        knotIconsGrid[3] = resourceMap.getIcon("KnotGrid.knotIconsGrid[3]");
        knotIconsGrid[4] = resourceMap.getIcon("KnotGrid.knotIconsGrid[4]");
        knotIconsGrid[5] = resourceMap.getIcon("KnotGrid.knotIconsGrid[5]");
        knotIconsGrid[6] = resourceMap.getIcon("KnotGrid.knotIconsGrid[6]");
        knotIconsGrid[7] = resourceMap.getIcon("KnotGrid.knotIconsGrid[7]");
        knotIconsGrid[8] = resourceMap.getIcon("KnotGrid.knotIconsGrid[8]");

        knotIconsClean = new Icon[9];
        knotIconsClean[0] = resourceMap.getIcon("KnotGrid.knotIconsClean[0]");
        knotIconsClean[1] = resourceMap.getIcon("KnotGrid.knotIconsClean[1]");
        knotIconsClean[2] = resourceMap.getIcon("KnotGrid.knotIconsClean[2]");
        knotIconsClean[3] = resourceMap.getIcon("KnotGrid.knotIconsClean[3]");
        knotIconsClean[4] = resourceMap.getIcon("KnotGrid.knotIconsClean[4]");
        knotIconsClean[5] = resourceMap.getIcon("KnotGrid.knotIconsClean[5]");
        knotIconsClean[6] = resourceMap.getIcon("KnotGrid.knotIconsClean[6]");
        knotIconsClean[7] = resourceMap.getIcon("KnotGrid.knotIconsClean[7]");
        knotIconsClean[8] = resourceMap.getIcon("KnotGrid.knotIconsClean[8]");

        knotIconsBWNoGrid = new Icon[9];
        knotIconsBWNoGrid[0] = resourceMap.getIcon("KnotGrid.knotIconsBWNG[0]");
        knotIconsBWNoGrid[1] = resourceMap.getIcon("KnotGrid.knotIconsBWNG[1]");
        knotIconsBWNoGrid[2] = resourceMap.getIcon("KnotGrid.knotIconsBWNG[2]");
        knotIconsBWNoGrid[3] = resourceMap.getIcon("KnotGrid.knotIconsBWNG[3]");
        knotIconsBWNoGrid[4] = resourceMap.getIcon("KnotGrid.knotIconsBWNG[4]");
        knotIconsBWNoGrid[5] = resourceMap.getIcon("KnotGrid.knotIconsBWNG[5]");
        knotIconsBWNoGrid[6] = resourceMap.getIcon("KnotGrid.knotIconsBWNG[6]");
        knotIconsBWNoGrid[7] = resourceMap.getIcon("KnotGrid.knotIconsBWNG[7]");
        knotIconsBWNoGrid[8] = resourceMap.getIcon("KnotGrid.knotIconsBWNG[8]");

        knotIconsMinimal = new Icon[9];
        knotIconsMinimal[0] = resourceMap.getIcon("KnotGrid.knotIconsMinimal[0]");
        knotIconsMinimal[1] = resourceMap.getIcon("KnotGrid.knotIconsMinimal[1]");
        knotIconsMinimal[2] = resourceMap.getIcon("KnotGrid.knotIconsMinimal[2]");
        knotIconsMinimal[3] = resourceMap.getIcon("KnotGrid.knotIconsMinimal[3]");
        knotIconsMinimal[4] = resourceMap.getIcon("KnotGrid.knotIconsMinimal[4]");
        knotIconsMinimal[5] = resourceMap.getIcon("KnotGrid.knotIconsMinimal[5]");
        knotIconsMinimal[6] = resourceMap.getIcon("KnotGrid.knotIconsMinimal[6]");
        knotIconsMinimal[7] = resourceMap.getIcon("KnotGrid.knotIconsMinimal[7]");
        knotIconsMinimal[8] = resourceMap.getIcon("KnotGrid.knotIconsMinimal[8]");
        
        knotIcons = knotIconsGrid;
        
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
        btnProcessKnot = new javax.swing.JButton();
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
        btnJumpCrossings = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu fileMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        mitemImageProcessing = new javax.swing.JMenuItem();
        mitemRegurgitation = new javax.swing.JMenuItem();
        mitemOptions = new javax.swing.JMenuItem();
        mitemNotes = new javax.swing.JMenuItem();
        javax.swing.JMenu helpMenu = new javax.swing.JMenu();
        mitemHelp = new javax.swing.JMenuItem();
        javax.swing.JMenuItem aboutMenuItem = new javax.swing.JMenuItem();

        mainPanel.setName("mainPanel"); // NOI18N

        jInternalFrame1.setName("jInternalFrame1"); // NOI18N
        jInternalFrame1.setVisible(true);

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("knottheorist/resources/KnotTheoristView"); // NOI18N
        editRepresentation.setText(bundle.getString("editRepresentation.text")); // NOI18N
        editRepresentation.setName("editRepresentation"); // NOI18N

        btnProcessKnot.setText(bundle.getString("btnProcessKnot.text")); // NOI18N
        btnProcessKnot.setToolTipText(bundle.getString("btnProcessKnot.toolTipText")); // NOI18N
        btnProcessKnot.setName("btnProcessKnot"); // NOI18N
        btnProcessKnot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProcessKnotActionPerformed(evt);
            }
        });

        btnClear.setText(bundle.getString("btnClear.text")); // NOI18N
        btnClear.setToolTipText(bundle.getString("btnClear.toolTipText")); // NOI18N
        btnClear.setName("btnClear"); // NOI18N
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });

        editModified.setText(bundle.getString("editModified.text")); // NOI18N
        editModified.setName("editModified"); // NOI18N

        btnRemLoops.setText(bundle.getString("btnRemLoops.text")); // NOI18N
        btnRemLoops.setName("btnRemLoops"); // NOI18N
        btnRemLoops.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemLoopsActionPerformed(evt);
            }
        });

        btnRemSwaps.setText(bundle.getString("btnRemSwaps.text")); // NOI18N
        btnRemSwaps.setName("btnRemSwaps"); // NOI18N
        btnRemSwaps.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemSwapsActionPerformed(evt);
            }
        });

        btnDowngrade.setText(bundle.getString("btnDowngrade.text")); // NOI18N
        btnDowngrade.setToolTipText(bundle.getString("btnDowngrade.toolTipText")); // NOI18N
        btnDowngrade.setName("btnDowngrade"); // NOI18N
        btnDowngrade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDowngradeActionPerformed(evt);
            }
        });

        bntSHU.setText(bundle.getString("bntSHU.text")); // NOI18N
        bntSHU.setName("bntSHU"); // NOI18N
        bntSHU.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntSHUActionPerformed(evt);
            }
        });

        btnSHR.setText(bundle.getString("btnSHR.text")); // NOI18N
        btnSHR.setName("btnSHR"); // NOI18N
        btnSHR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSHRActionPerformed(evt);
            }
        });

        btnSHD.setText(bundle.getString("btnSHD.text")); // NOI18N
        btnSHD.setName("btnSHD"); // NOI18N
        btnSHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSHDActionPerformed(evt);
            }
        });

        btnSHL.setText(bundle.getString("btnSHL.text")); // NOI18N
        btnSHL.setName("btnSHL"); // NOI18N
        btnSHL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSHLActionPerformed(evt);
            }
        });

        btnRegurgitate.setText(bundle.getString("btnRegurgitate.text")); // NOI18N
        btnRegurgitate.setToolTipText(bundle.getString("btnRegurgitate.toolTipText")); // NOI18N
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

        btnDigest.setText(bundle.getString("btnDigest.text")); // NOI18N
        btnDigest.setToolTipText(bundle.getString("btnDigest.toolTipText")); // NOI18N
        btnDigest.setName("btnDigest"); // NOI18N
        btnDigest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDigestActionPerformed(evt);
            }
        });

        btnToNotesBottom.setText(bundle.getString("btnToNotesBottom.text")); // NOI18N
        btnToNotesBottom.setToolTipText(bundle.getString("btnToNotesBottom.toolTipText")); // NOI18N
        btnToNotesBottom.setName("btnToNotesBottom"); // NOI18N
        btnToNotesBottom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnToNotesBottomActionPerformed(evt);
            }
        });

        btnToNotesTop.setText(bundle.getString("btnToNotesTop.text")); // NOI18N
        btnToNotesTop.setToolTipText(bundle.getString("btnToNotesTop.toolTipText")); // NOI18N
        btnToNotesTop.setName("btnToNotesTop"); // NOI18N
        btnToNotesTop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnToNotesTopActionPerformed(evt);
            }
        });

        btnTris.setText(bundle.getString("btnTris.text")); // NOI18N
        btnTris.setName("btnTris"); // NOI18N
        btnTris.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTrisActionPerformed(evt);
            }
        });

        btnFirstTri.setText(bundle.getString("btnFirstTri.text")); // NOI18N
        btnFirstTri.setName("btnFirstTri"); // NOI18N
        btnFirstTri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFirstTriActionPerformed(evt);
            }
        });

        btnRandTri.setText(bundle.getString("btnRandTri.text")); // NOI18N
        btnRandTri.setName("btnRandTri"); // NOI18N
        btnRandTri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRandTriActionPerformed(evt);
            }
        });

        btnAllTri.setText(bundle.getString("btnAllTri.text")); // NOI18N
        btnAllTri.setName("btnAllTri"); // NOI18N
        btnAllTri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAllTriActionPerformed(evt);
            }
        });

        btnSolve.setText(bundle.getString("btnSolve.text")); // NOI18N
        btnSolve.setToolTipText(bundle.getString("btnSolve.toolTipText")); // NOI18N
        btnSolve.setName("btnSolve"); // NOI18N
        btnSolve.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSolveActionPerformed(evt);
            }
        });

        btnValidate.setText(bundle.getString("btnValidate.text")); // NOI18N
        btnValidate.setName("btnValidate"); // NOI18N
        btnValidate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnValidateActionPerformed(evt);
            }
        });

        btnFull12.setText(bundle.getString("btnFull12.text")); // NOI18N
        btnFull12.setName("btnFull12"); // NOI18N
        btnFull12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFull12ActionPerformed(evt);
            }
        });

        btnRandomFlip.setText(bundle.getString("btnRandomFlip.text")); // NOI18N
        btnRandomFlip.setToolTipText(bundle.getString("btnRandomFlip.toolTipText")); // NOI18N
        btnRandomFlip.setName("btnRandomFlip"); // NOI18N
        btnRandomFlip.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRandomFlipActionPerformed(evt);
            }
        });

        btnFlipOver.setText(bundle.getString("btnFlipOver.text")); // NOI18N
        btnFlipOver.setName("btnFlipOver"); // NOI18N
        btnFlipOver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFlipOverActionPerformed(evt);
            }
        });

        btnFlipBack.setText(bundle.getString("btnFlipBack.text")); // NOI18N
        btnFlipBack.setName("btnFlipBack"); // NOI18N
        btnFlipBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFlipBackActionPerformed(evt);
            }
        });

        btnCopy.setText(bundle.getString("btnCopy.text")); // NOI18N
        btnCopy.setName("btnCopy"); // NOI18N
        btnCopy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCopyActionPerformed(evt);
            }
        });

        btnJumpCrossings.setText(bundle.getString("btnJumpCrossings.text")); // NOI18N
        btnJumpCrossings.setToolTipText(bundle.getString("btnJumpCrossings.toolTipText")); // NOI18N
        btnJumpCrossings.setName("btnJumpCrossings"); // NOI18N
        btnJumpCrossings.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnJumpCrossingsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jInternalFrame1Layout = new javax.swing.GroupLayout(jInternalFrame1.getContentPane());
        jInternalFrame1.getContentPane().setLayout(jInternalFrame1Layout);
        jInternalFrame1Layout.setHorizontalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jInternalFrame1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jInternalFrame1Layout.createSequentialGroup()
                        .addComponent(editModified)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnClear))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jInternalFrame1Layout.createSequentialGroup()
                        .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(editRepresentation, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jInternalFrame1Layout.createSequentialGroup()
                                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnToNotesTop)
                                    .addGroup(jInternalFrame1Layout.createSequentialGroup()
                                        .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(btnRemLoops)
                                            .addComponent(btnFull12))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(btnRemSwaps)
                                            .addComponent(btnJumpCrossings))))
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
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(btnToNotesBottom)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnDigest))
                                            .addGroup(jInternalFrame1Layout.createSequentialGroup()
                                                .addComponent(btnFirstTri)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnAllTri)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                                        .addComponent(btnSHL, 0, 1, Short.MAX_VALUE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnSHR, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(bntSHU, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(btnRegurgitate, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnProcessKnot)))))
                .addContainerGap())
        );
        jInternalFrame1Layout.setVerticalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jInternalFrame1Layout.createSequentialGroup()
                .addContainerGap(56, Short.MAX_VALUE)
                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(editRepresentation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnProcessKnot))
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
                    .addComponent(btnRandTri))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jInternalFrame1Layout.createSequentialGroup()
                        .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btnFirstTri)
                                .addComponent(btnAllTri)
                                .addComponent(btnJumpCrossings))
                            .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btnFlipOver)
                                .addComponent(btnFlipBack)
                                .addComponent(btnCopy))
                            .addComponent(btnFull12))
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
                                .addComponent(btnValidate))))
                    .addComponent(btnSHD))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnClear)
                    .addComponent(editModified, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        jPanel1.setName("jPanel1"); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 986, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 307, Short.MAX_VALUE)
        );

        jScrollPane2.setViewportView(jPanel1);

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jInternalFrame1, javax.swing.GroupLayout.Alignment.LEADING))
                .addContainerGap())
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 275, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jInternalFrame1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        getContentPane().add(mainPanel, java.awt.BorderLayout.CENTER);

        menuBar.setName("menuBar"); // NOI18N

        fileMenu.setText(bundle.getString("fileMenu.text")); // NOI18N
        fileMenu.setName("fileMenu"); // NOI18N

        exitMenuItem.setText(bundle.getString("quit.Action.text")); // NOI18N
        exitMenuItem.setName("exitMenuItem"); // NOI18N
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        jMenu1.setText(bundle.getString("jMenu1.text")); // NOI18N
        jMenu1.setName("jMenu1"); // NOI18N

        mitemImageProcessing.setText(bundle.getString("mitemImageProcessing.text")); // NOI18N
        mitemImageProcessing.setName("mitemImageProcessing"); // NOI18N
        mitemImageProcessing.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mitemImageProcessingActionPerformed(evt);
            }
        });
        jMenu1.add(mitemImageProcessing);

        mitemRegurgitation.setText(bundle.getString("mitemRegurgitation.text")); // NOI18N
        mitemRegurgitation.setName("mitemRegurgitation"); // NOI18N
        mitemRegurgitation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mitemRegurgitationActionPerformed(evt);
            }
        });
        jMenu1.add(mitemRegurgitation);

        mitemOptions.setText(bundle.getString("mitemOptions.text")); // NOI18N
        mitemOptions.setName("mitemOptions"); // NOI18N
        mitemOptions.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mitemOptionsActionPerformed(evt);
            }
        });
        jMenu1.add(mitemOptions);

        mitemNotes.setText(bundle.getString("mitemNotes.text")); // NOI18N
        mitemNotes.setName("mitemNotes"); // NOI18N
        mitemNotes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mitemNotesActionPerformed(evt);
            }
        });
        jMenu1.add(mitemNotes);

        menuBar.add(jMenu1);

        helpMenu.setText(bundle.getString("helpMenu.text")); // NOI18N
        helpMenu.setName("helpMenu"); // NOI18N

        mitemHelp.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, 0));
        mitemHelp.setText(bundle.getString("mitemHelp.text")); // NOI18N
        mitemHelp.setName("mitemHelp"); // NOI18N
        mitemHelp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mitemHelpActionPerformed(evt);
            }
        });
        helpMenu.add(mitemHelp);

        aboutMenuItem.setText(bundle.getString("showAboutBox.Action.text")); // NOI18N
        aboutMenuItem.setName("aboutMenuItem"); // NOI18N
        aboutMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aboutMenuItemActionPerformed(evt);
            }
        });
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        setJMenuBar(menuBar);
    }// </editor-fold>//GEN-END:initComponents

    private void btnProcessKnotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProcessKnotActionPerformed
        this.knot = grid.readGrid(Knot.REP_BKa);
        this.editRepresentation.setText(knot.toString());
        editModified.setText(knot.toString());
    }//GEN-LAST:event_btnProcessKnotActionPerformed

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
        mainPanel.repaint();
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
                if (knot != null && !knot.halfCrossList.isEmpty()) {
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
//                    jScrollPane2.revalidate();
//                    jScrollPane2.updateUI();
//                    jScrollPane2.validate();
//                    jScrollPane2.repaint();
                } else {
                    if (knot == null) {
                        knot = new Knot();
                    }
                    addKnotGrid(4, 4);
                    grid.squares[1][1].setKnot(GridSquare.KNOT_TURNSE);
                    grid.squares[2][1].setKnot(GridSquare.KNOT_TURNSW);
                    grid.squares[2][2].setKnot(GridSquare.KNOT_TURNNW);
                    grid.squares[1][2].setKnot(GridSquare.KNOT_TURNNE);
                    KnotTheoristView.this.mainPanel.revalidate();
                    KnotTheoristView.this.mainPanel.repaint();
                }
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

private void mitemRegurgitationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mitemRegurgitationActionPerformed
    if (rForm == null) {
        rForm = new RegurgitationForm();
        rForm.show();
    } else {
        rForm.show();
    }
}//GEN-LAST:event_mitemRegurgitationActionPerformed

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

private void mitemNotesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mitemNotesActionPerformed
    if (notes != null) {
        notes.show();
    } else {
        notes = new NotesForm();
        notes.show();
    }
}//GEN-LAST:event_mitemNotesActionPerformed

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
        knot = knot.solve(true);
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

    public ImageProcessingForm imageProcessingForm = null;

private void mitemImageProcessingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mitemImageProcessingActionPerformed
    if (imageProcessingForm == null) {
        imageProcessingForm = new ImageProcessingForm();
    }
    imageProcessingForm.setVisible(true);
}//GEN-LAST:event_mitemImageProcessingActionPerformed

    public HelpForm helpForm = null;
    public OptionsForm optionsForm = null;

private void mitemHelpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mitemHelpActionPerformed
    if (helpForm == null) {
        helpForm = HelpForm.makeHelpFromResource("help.txt");
    }
    helpForm.setVisible(true);
}//GEN-LAST:event_mitemHelpActionPerformed

private void btnJumpCrossingsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnJumpCrossingsActionPerformed
    if (knot != null) {
        while (knot.jumpConnections());
        editModified.setText(knot.toString());
    }
}//GEN-LAST:event_btnJumpCrossingsActionPerformed

private void mitemOptionsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mitemOptionsActionPerformed
    if (optionsForm == null) {
        optionsForm = new OptionsForm(this);
    }
    optionsForm.setVisible(true);
}//GEN-LAST:event_mitemOptionsActionPerformed

    private void aboutMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aboutMenuItemActionPerformed
        showAboutBox();
    }//GEN-LAST:event_aboutMenuItemActionPerformed

    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed
        this.getApplication().quit(evt);
    }//GEN-LAST:event_exitMenuItemActionPerformed

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
    private javax.swing.JButton btnJumpCrossings;
    private javax.swing.JButton btnProcessKnot;
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
    private javax.swing.JInternalFrame jInternalFrame1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuItem mitemHelp;
    private javax.swing.JMenuItem mitemImageProcessing;
    private javax.swing.JMenuItem mitemNotes;
    private javax.swing.JMenuItem mitemOptions;
    private javax.swing.JMenuItem mitemRegurgitation;
    private javax.swing.JSpinner spinCols;
    private javax.swing.JSpinner spinRows;
    // End of variables declaration//GEN-END:variables
//    private final Timer messageTimer;
//    private final Timer busyIconTimer;
//    private final Icon idleIcon;
//    private final Icon[] busyIcons = new Icon[15];
//    private int busyIconIndex = 0;
    private JDialog aboutBox;

    private Container getContentPane() {
        // Hacky dummy class, because SAF conversion.
        final FrameView parent = this;
        return new Container() {
            @Override
            public void add(Component comp, Object constraints) {
                if (comp instanceof JComponent) {
                    parent.setComponent((JComponent)comp);
                }
            }
        };
    }

    private void setJMenuBar(JMenuBar menuBar) {
        setMenuBar(menuBar);
    }
}
