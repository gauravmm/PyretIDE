package edu.brown.cs.cutlass;

import edu.brown.cs.cutlass.config.ConfigEngine;
import edu.brown.cs.cutlass.editor.Editor;
import edu.brown.cs.cutlass.editor.EditorClient;
import edu.brown.cs.cutlass.editor.PnlEditor;
import edu.brown.cs.cutlass.editor.callgraph.CallGraphEntry;
import edu.brown.cs.cutlass.editor.callgraph.CallGraphEntryRenderer;
import edu.brown.cs.cutlass.sys.SystemAbstraction;
import edu.brown.cs.cutlass.sys.io.AbstractIO;
import edu.brown.cs.cutlass.sys.io.AbstractIOException;
import edu.brown.cs.cutlass.sys.io.AbstractIdentifier;
import edu.brown.cs.cutlass.sys.pyret.AbstractPyretAccess;
import edu.brown.cs.cutlass.ui.FindClient;
import edu.brown.cs.cutlass.ui.FrmFinder;
import edu.brown.cs.cutlass.util.Lumberjack;
import edu.brown.cs.cutlass.util.Option;
import edu.brown.cs.cutlass.util.Pair;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Gaurav Manek
 * @param <T>
 */
public class FrmMain<T extends AbstractIdentifier> extends javax.swing.JFrame implements EditorClient<T>, FindClient {

    private final Launcher launcher;
    private final ConfigEngine config;
    private final AbstractIO<T> io;
    private final SystemAbstraction<T> systemAbstraction;
    private boolean isSK = false;
    private final FrmFinder finder;

    /**
     * Creates new main form.
     *
     * @param launcher
     * @param configEngine The configuration information is stored here.
     * @param optLaunchState The state to restore the program to, if one is
     * available, is stored here.
     */
    FrmMain(Launcher launcher, ConfigEngine configEngine, Option<LaunchState> optLaunchState, SystemAbstraction<T> sys) {
        initComponents();

        this.launcher = launcher;
        this.config = configEngine;
        this.io = sys.getIO();
        this.systemAbstraction = sys;

        this.setIconImage(loadImageIcon("/edu/brown/cs/cutlass/assets/icon.png"));

        this.lstCallGraph.setCellRenderer(new CallGraphEntryRenderer());
        this.lstCallGraph.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
                    /*
                     * http://stackoverflow.com/a/4344762
                     * Prevents JList from thinking clicks on the trailing space
                     * occurred on the last element.
                     */
                    Rectangle r = lstCallGraph.getCellBounds(lstCallGraph.getFirstVisibleIndex(), lstCallGraph.getLastVisibleIndex());
                    if (r != null && r.contains(e.getPoint())) {
                        int index = lstCallGraph.locationToIndex(e.getPoint());
                        CallGraphEntry elt = lstCallGraph.getModel().getElementAt(index);
                        if (elt != null) {
                            // Instruct the viewer to jump to the index
                            elt.jumpTo.jump();
                        }
                    }
                }
            }
        });

        //<editor-fold defaultstate="collapsed" desc="Load Toolbar Icons">
        // Load this dimension from configEngine:
        Dimension toolbarIconSize = config.getDimension("ui.toolbar.iconsize");
        List<Pair<JLabel, String>> labelIcons = Arrays.asList(new Pair<>(tbSave, "document-save-5.png"), new Pair<>(tbRun, "arrow-right-3.png"), new Pair<>(tbAutoIndent, "format-indent-more-3.png"), new Pair<>(tbBookmarkStop, "dialog-cancel-5.png"), new Pair<>(tbRedo, "edit-redo-3.png"), new Pair<>(tbUndo, "edit-undo-3.png"));
        for (Pair<JLabel, String> p : labelIcons) {
            JLabel lbl = p.getX();
            lbl.setText("");
            lbl.setMinimumSize(toolbarIconSize);
            lbl.setPreferredSize(toolbarIconSize);
            lbl.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            ImageIcon tmpImg = new ImageIcon(getClass().getResource("/edu/brown/cs/cutlass/assets/icons/" + p.getY()));

            // Calculate the dimensions
            Dimension thisIconSize = null;//new Dimension(tmpImg.getIconWidth(), tmpImg.getIconHeight());
            // If the icon is larger than the space for it in at least one dimension, resize it:
            if (tmpImg.getIconHeight() > toolbarIconSize.height || tmpImg.getIconWidth() > toolbarIconSize.width) {
                float aspectRatio = tmpImg.getIconHeight() * 1.0f / tmpImg.getIconWidth(); // Height/Width
                int tmpHeight = Math.round(aspectRatio * toolbarIconSize.width);
                // If the width is not the limiting dimension:
                if (tmpHeight > toolbarIconSize.height) {
                    thisIconSize = new Dimension(Math.round(toolbarIconSize.height / aspectRatio), toolbarIconSize.height);
                } else {
                    thisIconSize = new Dimension(toolbarIconSize.width, tmpHeight);
                }
            }

            // If we don't need to resize the icon:
            if (thisIconSize == null) {
                lbl.setIcon(tmpImg);
            } else {
                lbl.setIcon(new ImageIcon(tmpImg.getImage().getScaledInstance(thisIconSize.width, thisIconSize.height, java.awt.Image.SCALE_SMOOTH)));
            }
        }
//</editor-fold>

        if (optLaunchState.hasData()) {
            LaunchState launchState = optLaunchState.getData();
            // Load data from the launchState
            List<T> openFiles = launchState.getOpenFiles();
            int cTabId = launchState.getCurrentTabId();
            int tabCount = 0;

            for (T id : openFiles) {
                if (this.openTab(id)) {
                    tabCount++;
                } else {
                    if (tabCount < cTabId) {
                        cTabId--;
                    }
                }
            }
            if (cTabId >= tabEditors.getTabCount()) {
                cTabId = tabEditors.getTabCount() - 1;
            } else if (cTabId < 0) {
                cTabId = 0; // This should not be possible.
            }

            tabEditors.setSelectedIndex(cTabId);
        }

        if (tabEditors.getTabCount() == 0) {
            // Load default
            addClosableTab(tabEditors, new PnlDefaultEditor(this), "Default");
        }

        // Prepare find and replace window
        finder = new FrmFinder(this);
        finder.setLocationRelativeTo(this);

        // SK Easter egg
        String username = System.getProperty("user.name").toLowerCase();
        isSK = username.equals("sk") || username.startsWith("shriram");
        if (!isSK) {
            try {
                String localMachine = java.net.InetAddress.getLocalHost().getHostName();
                isSK = localMachine.equals("sk") || localMachine.startsWith("shriram");
            } catch (UnknownHostException ex) {
            }
        }
        if (isSK) {
            this.setTitle(this.getTitle() + " - Aspire Higher");
        }
    }

    /**
     * Returns an ImageIcon, or null if the path was invalid.
     * http://stackoverflow.com/a/6420563
     */
    private static Image loadImageIcon(String path) {
        try {
            InputStream img = FrmMain.class.getResourceAsStream(path);
            if (img == null) {
                Lumberjack.log(Lumberjack.Level.ERROR, "Icon not found.");
                return null;
            }
            return ImageIO.read(img);
        } catch (IOException ex) {
            Lumberjack.log(Lumberjack.Level.ERROR, "Icon not loaded.");
            return null;
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        spltPrimary = new javax.swing.JSplitPane();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        lstCallGraph = new javax.swing.JList<CallGraphEntry>();
        jLabel1 = new javax.swing.JLabel();
        tabEditors = new javax.swing.JTabbedPane();
        jToolBar1 = new javax.swing.JToolBar();
        tbSave = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        tbRun = new javax.swing.JLabel();
        tbBookmarkStop = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        tbUndo = new javax.swing.JLabel();
        tbRedo = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        tbAutoIndent = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JToolBar.Separator();
        jMenuBar1 = new javax.swing.JMenuBar();
        mnuFile = new javax.swing.JMenu();
        mnuFileNew = new javax.swing.JMenuItem();
        jSeparator6 = new javax.swing.JPopupMenu.Separator();
        mnuFileOpen = new javax.swing.JMenuItem();
        jSeparator7 = new javax.swing.JPopupMenu.Separator();
        mnuFileSave = new javax.swing.JMenuItem();
        mnuFileSaveAs = new javax.swing.JMenuItem();
        mnuSaveAll = new javax.swing.JMenuItem();
        jSeparator9 = new javax.swing.JPopupMenu.Separator();
        mnuFileNextTab = new javax.swing.JMenuItem();
        mnuFilePrevTab = new javax.swing.JMenuItem();
        mnuCloseCurrentTab = new javax.swing.JMenuItem();
        mnuCloseAllTabs = new javax.swing.JMenuItem();
        jSeparator10 = new javax.swing.JPopupMenu.Separator();
        mnuExit = new javax.swing.JMenuItem();
        mnuEdit = new javax.swing.JMenu();
        mnuUndo = new javax.swing.JMenuItem();
        mnuRedo = new javax.swing.JMenuItem();
        jSeparator11 = new javax.swing.JPopupMenu.Separator();
        mnuCut = new javax.swing.JMenuItem();
        mnuCopy = new javax.swing.JMenuItem();
        mnuPaste = new javax.swing.JMenuItem();
        jSeparator8 = new javax.swing.JPopupMenu.Separator();
        mnuDeleteSelected = new javax.swing.JMenuItem();
        mnuDeleteLine = new javax.swing.JMenuItem();
        jSeparator13 = new javax.swing.JPopupMenu.Separator();
        mnuAutoIndent = new javax.swing.JMenuItem();
        mnuBlockComment = new javax.swing.JMenuItem();
        jSeparator12 = new javax.swing.JPopupMenu.Separator();
        mnuFindAndReplace = new javax.swing.JMenuItem();
        mnuSelectAll = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        mnuPyretRun = new javax.swing.JMenuItem();
        mnuPyretStop = new javax.swing.JMenuItem();
        jSeparator5 = new javax.swing.JPopupMenu.Separator();
        mnuPyretDocs = new javax.swing.JMenuItem();
        mnuHelp = new javax.swing.JMenu();
        mnuHelpAbout = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Cutlass - Your Weapon of Choice");
        setMinimumSize(new java.awt.Dimension(400, 120));
        setPreferredSize(new java.awt.Dimension(900, 700));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        spltPrimary.setDividerLocation(700);
        spltPrimary.setResizeWeight(1.0);

        jPanel2.setMinimumSize(new java.awt.Dimension(250, 100));
        jPanel2.setPreferredSize(new java.awt.Dimension(150, 665));

        lstCallGraph.setFont(new java.awt.Font("Monospaced", 0, 13)); // NOI18N
        lstCallGraph.setModel(new DefaultListModel<CallGraphEntry>());
        lstCallGraph.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jScrollPane3.setViewportView(lstCallGraph);

        jLabel1.setText("Quick Navigation");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 226, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 576, Short.MAX_VALUE)
                .addContainerGap())
        );

        spltPrimary.setRightComponent(jPanel2);

        tabEditors.setMinimumSize(new java.awt.Dimension(450, 100));
        spltPrimary.setLeftComponent(tabEditors);

        getContentPane().add(spltPrimary, java.awt.BorderLayout.CENTER);

        jToolBar1.setRollover(true);
        jToolBar1.setPreferredSize(new java.awt.Dimension(383, 42));

        tbSave.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        tbSave.setText("[Icon]");
        tbSave.setToolTipText("Save");
        tbSave.setName(""); // NOI18N
        tbSave.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbSaveMouseClicked(evt);
            }
        });
        jToolBar1.add(tbSave);
        jToolBar1.add(jSeparator1);

        tbRun.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        tbRun.setText("[Icon]");
        tbRun.setToolTipText("Run");
        tbRun.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbRunMouseClicked(evt);
            }
        });
        jToolBar1.add(tbRun);

        tbBookmarkStop.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        tbBookmarkStop.setText("[Icon]");
        tbBookmarkStop.setToolTipText("Stop Current Execution");
        tbBookmarkStop.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbBookmarkStopMouseClicked(evt);
            }
        });
        jToolBar1.add(tbBookmarkStop);
        jToolBar1.add(jSeparator3);

        tbUndo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        tbUndo.setText("[Icon]");
        tbUndo.setToolTipText("Undo");
        tbUndo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbUndoMouseClicked(evt);
            }
        });
        jToolBar1.add(tbUndo);

        tbRedo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        tbRedo.setText("[Icon]");
        tbRedo.setToolTipText("Redo");
        tbRedo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbRedoMouseClicked(evt);
            }
        });
        jToolBar1.add(tbRedo);
        jToolBar1.add(jSeparator2);

        tbAutoIndent.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        tbAutoIndent.setText("[Icon]");
        tbAutoIndent.setToolTipText("Auto-Indent");
        tbAutoIndent.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbAutoIndentMouseClicked(evt);
            }
        });
        jToolBar1.add(tbAutoIndent);
        jToolBar1.add(jSeparator4);

        getContentPane().add(jToolBar1, java.awt.BorderLayout.NORTH);

        mnuFile.setMnemonic('F');
        mnuFile.setText("File");

        mnuFileNew.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        mnuFileNew.setMnemonic('N');
        mnuFileNew.setText("New File");
        mnuFileNew.setToolTipText("Create a new file");
        mnuFileNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuFileNewActionPerformed(evt);
            }
        });
        mnuFile.add(mnuFileNew);
        mnuFile.add(jSeparator6);

        mnuFileOpen.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        mnuFileOpen.setMnemonic('O');
        mnuFileOpen.setText("Open File");
        mnuFileOpen.setToolTipText("Open an existing file");
        mnuFileOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuFileOpenActionPerformed(evt);
            }
        });
        mnuFile.add(mnuFileOpen);
        mnuFile.add(jSeparator7);

        mnuFileSave.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        mnuFileSave.setMnemonic('S');
        mnuFileSave.setText("Save");
        mnuFileSave.setToolTipText("Save the current file");
        mnuFileSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuFileSaveActionPerformed(evt);
            }
        });
        mnuFile.add(mnuFileSave);

        mnuFileSaveAs.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        mnuFileSaveAs.setMnemonic('v');
        mnuFileSaveAs.setText("Save As...");
        mnuFileSaveAs.setToolTipText("Save the current file under a provided name");
        mnuFileSaveAs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuFileSaveAsActionPerformed(evt);
            }
        });
        mnuFile.add(mnuFileSaveAs);

        mnuSaveAll.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        mnuSaveAll.setMnemonic('l');
        mnuSaveAll.setText("Save All");
        mnuSaveAll.setToolTipText("Save the contents of all open tabs");
        mnuSaveAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuSaveAllActionPerformed(evt);
            }
        });
        mnuFile.add(mnuSaveAll);
        mnuFile.add(jSeparator9);

        mnuFileNextTab.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_2, java.awt.event.InputEvent.CTRL_MASK));
        mnuFileNextTab.setMnemonic('T');
        mnuFileNextTab.setText("Next Tab");
        mnuFileNextTab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuFileNextTabActionPerformed(evt);
            }
        });
        mnuFile.add(mnuFileNextTab);

        mnuFilePrevTab.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_1, java.awt.event.InputEvent.CTRL_MASK));
        mnuFilePrevTab.setMnemonic('v');
        mnuFilePrevTab.setText("Previous Tab");
        mnuFilePrevTab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuFilePrevTabActionPerformed(evt);
            }
        });
        mnuFile.add(mnuFilePrevTab);

        mnuCloseCurrentTab.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_W, java.awt.event.InputEvent.CTRL_MASK));
        mnuCloseCurrentTab.setMnemonic('C');
        mnuCloseCurrentTab.setText("Close Current Tab");
        mnuCloseCurrentTab.setToolTipText("Close the current tab");
        mnuCloseCurrentTab.setDisplayedMnemonicIndex(6);
        mnuCloseCurrentTab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuCloseCurrentTabActionPerformed(evt);
            }
        });
        mnuFile.add(mnuCloseCurrentTab);

        mnuCloseAllTabs.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_W, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        mnuCloseAllTabs.setMnemonic('A');
        mnuCloseAllTabs.setText("Close All Tabs");
        mnuCloseAllTabs.setToolTipText("Close all open tabs");
        mnuCloseAllTabs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuCloseAllTabsActionPerformed(evt);
            }
        });
        mnuFile.add(mnuCloseAllTabs);
        mnuFile.add(jSeparator10);

        mnuExit.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, java.awt.event.InputEvent.ALT_MASK));
        mnuExit.setMnemonic('x');
        mnuExit.setText("Exit");
        mnuExit.setToolTipText("Exit Cutlass");
        mnuExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuExitActionPerformed(evt);
            }
        });
        mnuFile.add(mnuExit);

        jMenuBar1.add(mnuFile);

        mnuEdit.setMnemonic('E');
        mnuEdit.setText("Edit");

        mnuUndo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Z, java.awt.event.InputEvent.CTRL_MASK));
        mnuUndo.setMnemonic('U');
        mnuUndo.setText("Undo");
        mnuUndo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuUndoActionPerformed(evt);
            }
        });
        mnuEdit.add(mnuUndo);

        mnuRedo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Y, java.awt.event.InputEvent.CTRL_MASK));
        mnuRedo.setMnemonic('R');
        mnuRedo.setText("Redo");
        mnuRedo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuRedoActionPerformed(evt);
            }
        });
        mnuEdit.add(mnuRedo);
        mnuEdit.add(jSeparator11);

        mnuCut.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.CTRL_MASK));
        mnuCut.setMnemonic('t');
        mnuCut.setText("Cut");
        mnuCut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuCutActionPerformed(evt);
            }
        });
        mnuEdit.add(mnuCut);

        mnuCopy.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_MASK));
        mnuCopy.setMnemonic('y');
        mnuCopy.setText("Copy");
        mnuCopy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuCopyActionPerformed(evt);
            }
        });
        mnuEdit.add(mnuCopy);

        mnuPaste.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, java.awt.event.InputEvent.CTRL_MASK));
        mnuPaste.setMnemonic('p');
        mnuPaste.setText("Paste");
        mnuPaste.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuPasteActionPerformed(evt);
            }
        });
        mnuEdit.add(mnuPaste);
        mnuEdit.add(jSeparator8);

        mnuDeleteSelected.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_DELETE, 0));
        mnuDeleteSelected.setMnemonic('D');
        mnuDeleteSelected.setText("Delete Selected Text");
        mnuDeleteSelected.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuDeleteSelectedActionPerformed(evt);
            }
        });
        mnuEdit.add(mnuDeleteSelected);

        mnuDeleteLine.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.CTRL_MASK));
        mnuDeleteLine.setMnemonic('L');
        mnuDeleteLine.setText("Delete Line");
        mnuDeleteLine.setToolTipText("Deletes the entire line at the cursor's current position");
        mnuDeleteLine.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuDeleteLineActionPerformed(evt);
            }
        });
        mnuEdit.add(mnuDeleteLine);
        mnuEdit.add(jSeparator13);

        mnuAutoIndent.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_I, java.awt.event.InputEvent.CTRL_MASK));
        mnuAutoIndent.setMnemonic('I');
        mnuAutoIndent.setText("Auto-Indent");
        mnuAutoIndent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuAutoIndentActionPerformed(evt);
            }
        });
        mnuEdit.add(mnuAutoIndent);

        mnuBlockComment.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_7, java.awt.event.InputEvent.CTRL_MASK));
        mnuBlockComment.setMnemonic('B');
        mnuBlockComment.setText("Block Comment Selected");
        mnuBlockComment.setToolTipText("Comment out anything on selected line, or current line if no text selected");
        mnuBlockComment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuBlockCommentActionPerformed(evt);
            }
        });
        mnuEdit.add(mnuBlockComment);
        mnuEdit.add(jSeparator12);

        mnuFindAndReplace.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F, java.awt.event.InputEvent.CTRL_MASK));
        mnuFindAndReplace.setMnemonic('F');
        mnuFindAndReplace.setText("Find And Replace");
        mnuFindAndReplace.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuFindAndReplaceActionPerformed(evt);
            }
        });
        mnuEdit.add(mnuFindAndReplace);

        mnuSelectAll.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.CTRL_MASK));
        mnuSelectAll.setMnemonic('S');
        mnuSelectAll.setText("Select All");
        mnuSelectAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuSelectAllActionPerformed(evt);
            }
        });
        mnuEdit.add(mnuSelectAll);

        jMenuBar1.add(mnuEdit);

        jMenu1.setMnemonic('y');
        jMenu1.setText("Pyret");

        mnuPyretRun.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.CTRL_MASK));
        mnuPyretRun.setMnemonic('R');
        mnuPyretRun.setText("Run");
        mnuPyretRun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuPyretRunActionPerformed(evt);
            }
        });
        jMenu1.add(mnuPyretRun);

        mnuPyretStop.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        mnuPyretStop.setMnemonic('S');
        mnuPyretStop.setText("Stop Current Execution");
        mnuPyretStop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuPyretStopActionPerformed(evt);
            }
        });
        jMenu1.add(mnuPyretStop);
        jMenu1.add(jSeparator5);

        mnuPyretDocs.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, 0));
        mnuPyretDocs.setMnemonic('D');
        mnuPyretDocs.setText("View Pyret Documentation");
        mnuPyretDocs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuPyretDocsActionPerformed(evt);
            }
        });
        jMenu1.add(mnuPyretDocs);

        jMenuBar1.add(jMenu1);

        mnuHelp.setMnemonic('H');
        mnuHelp.setText("Help");
        mnuHelp.setToolTipText("");

        mnuHelpAbout.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.ALT_MASK));
        mnuHelpAbout.setMnemonic('A');
        mnuHelpAbout.setText("About");
        mnuHelpAbout.setToolTipText("More information about Cutlass");
        mnuHelpAbout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuHelpAboutActionPerformed(evt);
            }
        });
        mnuHelp.add(mnuHelpAbout);

        jMenuBar1.add(mnuHelp);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private void fileSaveAsAction() {
        try {
            //get the contents of their editor and store as seq - either CharSequence or List<CharSequence>
            Editor editor = getCurrentEditor();
            String seq = editor.getBuffer();
            Option<T> destination = io.requestUserFileDestination();
            if (destination.hasData()) {
                T destId = destination.getData();
                io.setUserFile(destId, seq);
                editor.setIdentifier(destId);
                tabEditors.remove(tabEditors.getSelectedIndex());
                addClosableTab(tabEditors, editor, destId.getDisplayName());
            }
        } catch (AbstractIOException ex) {
            Lumberjack.log(Lumberjack.Level.ERROR, ex);
        }
    }

    private void fileSaveAction() {
        Editor e = getCurrentEditor();
        if (e.isEditorWindow()) {
            if (e.isChangedSinceLastSave()) {
                // TODO: Save the file
                Option<T> id = e.getIdentifier();
                if (id.hasData()) {
                    try {
                        //Save
                        id.getData();
                        io.setUserFile(id.getData(), e.getBuffer());
                        //e.setChangedSinceLastSave(false);
                    } catch (AbstractIOException ex) {
                        Lumberjack.log(Lumberjack.Level.ERROR, ex);
                    }
                } else {
                    fileSaveAsAction();
                }
            }
        }
    }
    private void tbSaveMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbSaveMouseClicked
        this.mnuFileSaveActionPerformed(null);
    }//GEN-LAST:event_tbSaveMouseClicked

    private void tbRunMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbRunMouseClicked
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        this.mnuPyretRunActionPerformed(null);
        //getCurrentEditor().close();
    }//GEN-LAST:event_tbRunMouseClicked

    private void tbBookmarkStopMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbBookmarkStopMouseClicked
        // TODO add your handling code here:
        this.mnuPyretStopActionPerformed(null);
    }//GEN-LAST:event_tbBookmarkStopMouseClicked

    private void tbUndoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbUndoMouseClicked
        // TODO add your handling code here:
        this.mnuUndoActionPerformed(null);
    }//GEN-LAST:event_tbUndoMouseClicked

    private void tbRedoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbRedoMouseClicked
        // TODO add your handling code here:
        this.mnuRedoActionPerformed(null);
    }//GEN-LAST:event_tbRedoMouseClicked

    private void tbAutoIndentMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbAutoIndentMouseClicked
        // TODO add your handling code here:
        this.mnuAutoIndentActionPerformed(null);
    }//GEN-LAST:event_tbAutoIndentMouseClicked

    private void mnuHelpAboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuHelpAboutActionPerformed
        JOptionPane.showMessageDialog(this,
                "Cutlass - Pyret IDE\n"
                + "For CSCI 0320 Spring 2014, Term Project\n"
                + "By: Dilip Arumugam, Gaurav Manek,\n"
                + "      Miles Holland, Zachary Zagorski",
                "About", JOptionPane.INFORMATION_MESSAGE);

    }//GEN-LAST:event_mnuHelpAboutActionPerformed

    private void mnuFileNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuFileNewActionPerformed
        //Open a new empty tab
        addClosableTab(tabEditors, new PnlEditor(this, isSK ? "# I'm sorry, Dave. I'm afraid I can't do that." : "#lang pyret"), "New Tab");
    }//GEN-LAST:event_mnuFileNewActionPerformed

    private void mnuFileSaveAsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuFileSaveAsActionPerformed
        try {
            //get the contents of their editor and store as seq - either CharSequence or List<CharSequence>
            Editor editor = getCurrentEditor();
            String seq = editor.getBuffer();
            Option<T> destination = io.requestUserFileDestination();
            if (destination.hasData()) {
                T destId = destination.getData();
                io.setUserFile(destId, seq);
                editor.setIdentifier(destId);
                editor.setChangedSinceLastSave(false);
                tabEditors.remove(tabEditors.getSelectedIndex());
                addClosableTab(tabEditors, editor, destId.getDisplayName());
            }
        } catch (AbstractIOException ex) {
            Lumberjack.log(Lumberjack.Level.ERROR, ex);
        }
    }//GEN-LAST:event_mnuFileSaveAsActionPerformed

    private void mnuFileSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuFileSaveActionPerformed
        Editor e = getCurrentEditor();
        if (e.isEditorWindow()) {
            if (e.isChangedSinceLastSave()) {
                this.save(e);
            }
        }
    }//GEN-LAST:event_mnuFileSaveActionPerformed

    private void mnuFileOpenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuFileOpenActionPerformed
        try {
            Option<T> destination = io.requestUserFileSource();
            if (destination.hasData()) {
                openTab(destination.getData());
            }
        } catch (AbstractIOException ex) {
            Lumberjack.log(Lumberjack.Level.ERROR, ex);
        }
    }//GEN-LAST:event_mnuFileOpenActionPerformed

    private void mnuExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuExitActionPerformed
        //save launch state!
        mnuCloseAllTabsActionPerformed(evt);
        this.dispose();
    }//GEN-LAST:event_mnuExitActionPerformed

    private void mnuSaveAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuSaveAllActionPerformed
        int tabs = tabEditors.getTabCount();
        for (int i = 0; i < tabs; i++) {
            Editor<T> ed = (Editor<T>) tabEditors.getComponentAt(i);
            if (ed.isEditorWindow()) {
                if (ed.isChangedSinceLastSave()) {
                    save(ed);
//                    // TODO: Save the file
//                    Option<T> id = ed.getIdentifier();
//                    if (id.hasData()) {
//                        // Save
//                        id.getData();
//                        throw new UnsupportedOperationException();
//                    } else {
//                        // Save as
//                        throw new UnsupportedOperationException();
//                    }
                }
            }
        }
    }//GEN-LAST:event_mnuSaveAllActionPerformed

    private void mnuCloseCurrentTabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuCloseCurrentTabActionPerformed
//        mnuFileSaveActionPerformed(evt);
        closeTab(this.getCurrentEditor());
//        int selected = tabEditors.getSelectedIndex();
//        if (selected >= 0) {
//            tabEditors.remove(selected);
//        }
    }//GEN-LAST:event_mnuCloseCurrentTabActionPerformed

    private void mnuCloseAllTabsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuCloseAllTabsActionPerformed
        int tabs = tabEditors.getTabCount();
        for (int i = tabs - 1; i >= 0; i--) {
            tabEditors.setSelectedIndex(i);
            Editor<T> ed = (Editor<T>) tabEditors.getComponentAt(i);
            if (ed.isEditorWindow()) {
                if (ed.isChangedSinceLastSave()) {
                    // TODO: Save the file
                    closeTab(ed);
                }
            }
            //ed.close();
//            tabEditors.remove(i);
        }
    }//GEN-LAST:event_mnuCloseAllTabsActionPerformed

    private void mnuUndoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuUndoActionPerformed
        // TODO add your handling code here:
        this.getCurrentEditor().undo();
    }//GEN-LAST:event_mnuUndoActionPerformed

    private void mnuRedoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuRedoActionPerformed
        // TODO add your handling code here:
        this.getCurrentEditor().redo();
    }//GEN-LAST:event_mnuRedoActionPerformed

    private void mnuCutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuCutActionPerformed
        Editor<T> e = getCurrentEditor();
        if (e.isEditorWindow()) {
            systemAbstraction.getClipboard().put(e.getSelection());
            e.deleteSelection();
        }
    }//GEN-LAST:event_mnuCutActionPerformed

    private void mnuCopyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuCopyActionPerformed
        Editor<T> e = getCurrentEditor();
        if (e.isEditorWindow()) {
            systemAbstraction.getClipboard().put(e.getSelection());
        }
    }//GEN-LAST:event_mnuCopyActionPerformed

    private void mnuPasteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuPasteActionPerformed
        Editor<T> e = getCurrentEditor();
        if (e.isEditorWindow()) {
            e.paste(systemAbstraction.getClipboard().get());
        }
    }//GEN-LAST:event_mnuPasteActionPerformed

    private void mnuDeleteSelectedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuDeleteSelectedActionPerformed
        Editor<T> e = getCurrentEditor();
        if (e.isEditorWindow()) {
            e.deleteSelection();
        }
    }//GEN-LAST:event_mnuDeleteSelectedActionPerformed

    private void mnuSelectAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuSelectAllActionPerformed
        Editor<T> e = getCurrentEditor();
        if (e.isEditorWindow()) {
            e.selectAll();
        }
    }//GEN-LAST:event_mnuSelectAllActionPerformed

    private void mnuBlockCommentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuBlockCommentActionPerformed
        Editor<T> e = getCurrentEditor();
        if (e.isEditorWindow()) {
            e.toggleComment();
        }
    }//GEN-LAST:event_mnuBlockCommentActionPerformed

    private void mnuDeleteLineActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuDeleteLineActionPerformed
        // TODO add your handling code here:
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }//GEN-LAST:event_mnuDeleteLineActionPerformed

    private void mnuPyretDocsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuPyretDocsActionPerformed
        if (Desktop.isDesktopSupported()) {
            try {
                File docsPath = new File("docs/index.html");
                if (docsPath.exists() && docsPath.canRead()) {
                    Desktop.getDesktop().browse(docsPath.toURI());
                    return;
                }
            } catch (IOException | SecurityException ex) {
                Lumberjack.log(Lumberjack.Level.WARN, ex);
            }
            try {
                Desktop.getDesktop().browse(new URI("http://www.pyret.org/docs/"));
            } catch (URISyntaxException | IOException | SecurityException ex) {
                Lumberjack.log(Lumberjack.Level.WARN, ex);
            }
        }
    }//GEN-LAST:event_mnuPyretDocsActionPerformed

    private void mnuPyretStopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuPyretStopActionPerformed
        // TODO add your handling code here:
        getCurrentEditor().pyretClose();
    }//GEN-LAST:event_mnuPyretStopActionPerformed

    private void mnuPyretRunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuPyretRunActionPerformed
        // TODO add your handling code here:
        fileSaveAction();
        getCurrentEditor().run();
    }//GEN-LAST:event_mnuPyretRunActionPerformed

    private void mnuAutoIndentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuAutoIndentActionPerformed
        // TODO add your handling code here:
        this.getCurrentEditor().reindent();
    }//GEN-LAST:event_mnuAutoIndentActionPerformed

    private void mnuFilePrevTabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuFilePrevTabActionPerformed
        // TODO add your handling code here:
        this.tabEditors.setSelectedIndex((this.tabEditors.getSelectedIndex() - 1 + this.tabEditors.getTabCount()) % this.tabEditors.getTabCount());
    }//GEN-LAST:event_mnuFilePrevTabActionPerformed

    private void mnuFileNextTabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuFileNextTabActionPerformed
        // TODO add your handling code here:
        this.tabEditors.setSelectedIndex((this.tabEditors.getSelectedIndex() + 1) % this.tabEditors.getTabCount());
    }//GEN-LAST:event_mnuFileNextTabActionPerformed

    private void mnuFindAndReplaceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuFindAndReplaceActionPerformed
        finder.setVisible(true);
        finder.requestFocus();
    }//GEN-LAST:event_mnuFindAndReplaceActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        LinkedList<T> closingList = new LinkedList<>();
        Editor<T> currEditor = getCurrentEditor();
        int openTabIndex = 0;
        boolean countTabIndex = false;
        for (int i = tabEditors.getTabCount() - 1; i >= 0; --i) {
            Editor<T> ed = (Editor<T>) tabEditors.getComponentAt(i);
            if (ed.isEditorWindow()) {
                if (ed.isChangedSinceLastSave()) {
                    int selection = this.showCloseConfirmDialog(ed.getIdentifier());
                    switch (selection) {
                        case JOptionPane.YES_OPTION:
                            // Save this
                            this.save(ed);
                        // NO BREAK HERE!
                        case JOptionPane.NO_OPTION:
                            tabEditors.removeTabAt(i);
                            ed.close();
                            break;
                        default:
                            return; // Abort the shutdown
                    }
                } else {
                    tabEditors.removeTabAt(i);
                    ed.close();
                }
                Option<T> identifier = ed.getIdentifier();
                if (identifier.hasData()) {
                    closingList.addFirst(identifier.getData());
                    if (countTabIndex) {
                        openTabIndex++;
                    }
                }
            } else {
                tabEditors.removeTabAt(i);
            }
            if (ed == currEditor) {
                countTabIndex = true;
            }
        }
        openTabIndex = Math.min(openTabIndex, closingList.size() - 1);
        launcher.quit(LaunchState.toState(closingList, openTabIndex));
    }//GEN-LAST:event_formWindowClosing

    public void newTab(Editor<T> def) {
        this.mnuFileNewActionPerformed(null);
        this.closeTab(def);
    }

    public void closeTab(Editor<T> e) {
        if (e.isEditorWindow() && e.isChangedSinceLastSave()) {
            this.closeTabMessagePrompt(e);
        } else {
            closeTabAndSave(e, false);
        }
    }

    public void closeTabAndSave(Editor<T> e, boolean save) {
        if (e.isEditorWindow() && save) {
            this.save(e);
        }
        e.close();
        this.tabEditors.remove(e);
        if (this.tabEditors.getTabCount() == 0) {
            addClosableTab(tabEditors, new PnlDefaultEditor(this), "Default");
        }
    }

    private void save(Editor e) {
        Option<T> id = e.getIdentifier();
        if (id.hasData()) {
            try {
                //Save
                id.getData();
                io.setUserFile(id.getData(), e.getBuffer());
                e.setChangedSinceLastSave(false);
            } catch (AbstractIOException ex) {
                Lumberjack.log(Lumberjack.Level.ERROR, ex);
            }
        } else {
            mnuFileSaveAsActionPerformed(null);
        }
    }

    private Editor<T> getCurrentEditor() {
        return (Editor<T>) tabEditors.getSelectedComponent();
    }

    /**
     * Adds a component to a JTabbedPane with a little "close tab" button on the
     * right side of the tab.
     *
     * @param tabbedPane the JTabbedPane
     * @param c An Editor
     * @param title the title for the tab
     */
    private void addClosableTab(final JTabbedPane tabbedPane, final Editor<T> c, final String title) {
        // Add the tab to the pane without any label
        tabbedPane.addTab(null, c);
        int pos = tabbedPane.indexOfComponent(c);

        // Create a FlowLayout that will spaaaaaace things 5px apart
        FlowLayout f = new FlowLayout(FlowLayout.CENTER, 5, 0);

        // Make a small JPanel with the layout and make it non-opaque
        JPanel pnlTab = new JPanel(f);
        pnlTab.setAlignmentY(SwingConstants.CENTER);
        pnlTab.setOpaque(false);

        // Add a JLabel with title and the left-side tab icon
        final JLabel lblTitle = new JLabel(title);

        // Create a JButton for the close tab button
        JButton btnClose = new JButton();
        btnClose.setOpaque(false);
        btnClose.setForeground(Color.GRAY);
        btnClose.setFont(btnClose.getFont().deriveFont(Font.BOLD, 8));
        btnClose.setText("X");
        btnClose.setMargin(new Insets(0, 0, 0, 0));
        btnClose.setAlignmentX(SwingConstants.CENTER);
        btnClose.setAlignmentY(SwingConstants.CENTER);
        btnClose.setPreferredSize(new Dimension(15, 18));

        // Set border null so the button doesn't make the tab too big
        btnClose.setBorder(BorderFactory.createEtchedBorder());
        btnClose.setBorderPainted(false);
        btnClose.setContentAreaFilled(false);

        // Make sure the button can't get focus, otherwise it looks funny
        btnClose.setFocusable(false);

        // Put the panel together
        pnlTab.add(lblTitle);
        pnlTab.add(btnClose);

        // Add a thin border to keep the image below the top edge of the tab
        // when the tab is selected
        pnlTab.setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 0));

        // Add MouseOver listener that displays the call graph
        pnlTab.addMouseListener(new MouseAdapterImpl(this, c));
        c.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (c.isChangedSinceLastSave()) {
                    lblTitle.setFont(lblTitle.getFont().deriveFont(Font.BOLD));
                } else {
                    lblTitle.setFont(lblTitle.getFont().deriveFont(Font.PLAIN));
                }
            }
        });

        // Now assign the component for the tab
        tabbedPane.setTabComponentAt(pos, pnlTab);

        // Add the listener that removes the tab
        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // The component parameter must be declared "final" so that it can be
                // referenced in the anonymous listener class like this.
                closeTab(c);
            }
        };
        btnClose.addActionListener(listener);

        MouseListener buttonMouseListener = new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                Component component = e.getComponent();
                if (component instanceof AbstractButton) {
                    AbstractButton button = (AbstractButton) component;
                    button.setBorderPainted(true);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                Component component = e.getComponent();
                if (component instanceof AbstractButton) {
                    AbstractButton button = (AbstractButton) component;
                    button.setBorderPainted(false);
                }
            }
        };
        btnClose.addMouseListener(buttonMouseListener);

        // Optionally bring the new tab to the front
        tabbedPane.setSelectedComponent(c);
        c.requestFocusInWindow();

        //-------------------------------------------------------------
        // Bonus: Adding a <Ctrl-W> keystroke binding to close the tab
        //-------------------------------------------------------------
        AbstractAction closeTabAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mnuCloseCurrentTabActionPerformed(e);
            }
        };

        // Create a keystroke
        KeyStroke controlW = KeyStroke.getKeyStroke("control W");

        // Get the appropriate input map using the JComponent constants.
        // This one works well when the component is a container. 
        InputMap inputMap = c.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        // Add the key binding for the keystroke to the action name
        inputMap.put(controlW, "closeTab");

        // Now add a single binding for the action name to the anonymous action
        c.getActionMap().put("closeTab", closeTabAction);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator10;
    private javax.swing.JPopupMenu.Separator jSeparator11;
    private javax.swing.JPopupMenu.Separator jSeparator12;
    private javax.swing.JPopupMenu.Separator jSeparator13;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JToolBar.Separator jSeparator4;
    private javax.swing.JPopupMenu.Separator jSeparator5;
    private javax.swing.JPopupMenu.Separator jSeparator6;
    private javax.swing.JPopupMenu.Separator jSeparator7;
    private javax.swing.JPopupMenu.Separator jSeparator8;
    private javax.swing.JPopupMenu.Separator jSeparator9;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JList<CallGraphEntry> lstCallGraph;
    private javax.swing.JMenuItem mnuAutoIndent;
    private javax.swing.JMenuItem mnuBlockComment;
    private javax.swing.JMenuItem mnuCloseAllTabs;
    private javax.swing.JMenuItem mnuCloseCurrentTab;
    private javax.swing.JMenuItem mnuCopy;
    private javax.swing.JMenuItem mnuCut;
    private javax.swing.JMenuItem mnuDeleteLine;
    private javax.swing.JMenuItem mnuDeleteSelected;
    private javax.swing.JMenu mnuEdit;
    private javax.swing.JMenuItem mnuExit;
    private javax.swing.JMenu mnuFile;
    private javax.swing.JMenuItem mnuFileNew;
    private javax.swing.JMenuItem mnuFileNextTab;
    private javax.swing.JMenuItem mnuFileOpen;
    private javax.swing.JMenuItem mnuFilePrevTab;
    private javax.swing.JMenuItem mnuFileSave;
    private javax.swing.JMenuItem mnuFileSaveAs;
    private javax.swing.JMenuItem mnuFindAndReplace;
    private javax.swing.JMenu mnuHelp;
    private javax.swing.JMenuItem mnuHelpAbout;
    private javax.swing.JMenuItem mnuPaste;
    private javax.swing.JMenuItem mnuPyretDocs;
    private javax.swing.JMenuItem mnuPyretRun;
    private javax.swing.JMenuItem mnuPyretStop;
    private javax.swing.JMenuItem mnuRedo;
    private javax.swing.JMenuItem mnuSaveAll;
    private javax.swing.JMenuItem mnuSelectAll;
    private javax.swing.JMenuItem mnuUndo;
    private javax.swing.JSplitPane spltPrimary;
    private javax.swing.JTabbedPane tabEditors;
    private javax.swing.JLabel tbAutoIndent;
    private javax.swing.JLabel tbBookmarkStop;
    private javax.swing.JLabel tbRedo;
    private javax.swing.JLabel tbRun;
    private javax.swing.JLabel tbSave;
    private javax.swing.JLabel tbUndo;
    // End of variables declaration//GEN-END:variables

    @Override
    public void handleQuickNavigationChange(Collection<CallGraphEntry> quickNav) {
        DefaultListModel<CallGraphEntry> defaultListModel = new DefaultListModel<>();
        for (CallGraphEntry q : quickNav) {
            defaultListModel.addElement(q);
        }
        lstCallGraph.setModel(defaultListModel);
    }

    @Override
    public AbstractPyretAccess<T> getPyretAccess(Editor<T> ed) throws AbstractIOException {
        if (ed.isEditorWindow()) {
            // Check if it is saved...
            if (ed.getIdentifier().hasData()) {
                return systemAbstraction.getPyretAccess(ed.getIdentifier().getData());
            } else {
                // Ask users to save
                throw new AbstractIOException("User needs to select a place to store the file.");
            }
        } else {
            throw new IllegalStateException();
        }
    }

    @Override
    public boolean findNext(FrmFinder.FindType type, boolean matchCase, boolean forwards, boolean wholeWords, String find) {
        Editor<T> ed = getCurrentEditor();
        if (ed.isEditorWindow()) {
            ed.requestFocus();
            return ed.findNext(type, matchCase, forwards, wholeWords, find);
        } else {
            return false;
        }
    }

    @Override
    public boolean replaceNext(FrmFinder.FindType type, boolean matchCase, boolean forwards, boolean wholeWords, String find, String replace) {
        Editor<T> ed = getCurrentEditor();
        if (ed.isEditorWindow()) {
            return ed.replaceNext(type, matchCase, forwards, wholeWords, find, replace);
        } else {
            return false;
        }
    }

    @Override
    public boolean replaceAll(FrmFinder.FindType type, boolean matchCase, boolean forwards, boolean wholeWords, String find, String replace) {
        Editor<T> ed = getCurrentEditor();
        if (ed.isEditorWindow()) {
            return ed.replaceAll(type, matchCase, forwards, wholeWords, find, replace);
        } else {
            return false;
        }
    }

    private void closeTabMessagePrompt(Editor<T> editor) {
        if (!editor.isEditorWindow()) {
            this.closeTabAndSave(editor, false);
            return;
        }
        Option<T> currentID = editor.getIdentifier();
        int selection = showCloseConfirmDialog(currentID);

        switch (selection) {
            case JOptionPane.YES_OPTION:
                this.closeTabAndSave(editor, true);
                break;
            case JOptionPane.NO_OPTION:
                this.closeTabAndSave(editor, false);
                break;
            default:
                break;
        }
    }

    private int showCloseConfirmDialog(Option<T> currentID) {
        return JOptionPane.showConfirmDialog(this,
                "Would you like to save your unsaved work" + (currentID.hasData() ? " in " + currentID.getData().getDisplayName() + "?" : "?"),
                "Cutlass",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.WARNING_MESSAGE);
    }

    private boolean openTab(T destId) {
        try {
            StringBuilder contents = new StringBuilder();
            Iterator<String> it = io.getUserFile(destId).iterator();
            while (true) {
                contents.append(it.next());
                if (it.hasNext()) {
                    contents.append("\n");
                } else {
                    break;
                }
            }
            Editor e = new PnlEditor(this, contents.toString());
            e.setIdentifier(destId);
            e.setChangedSinceLastSave(false);
            addClosableTab(tabEditors, e, destId.getDisplayName());
            return true;
        } catch (AbstractIOException ex) {
            Lumberjack.log(Lumberjack.Level.WARN, ex);
            return false;
        }
    }

    private void showDefaultCallGraph() {
        Editor<T> currentEditor = this.getCurrentEditor();
        if (currentEditor.isEditorWindow()) {
            currentEditor.showCallGraph();
        }
    }

    private void switchTo(Editor<T> ed) {
        tabEditors.setSelectedComponent(ed);
    }

    private class MouseAdapterImpl implements MouseListener {

        private final Editor<T> ed;
        private final FrmMain<T> main;

        private MouseAdapterImpl(FrmMain<T> aThis, Editor<T> c) {
            this.main = aThis;
            this.ed = c;
        }

        @Override
        public void mouseExited(MouseEvent e) {
            main.showDefaultCallGraph();
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            ed.showCallGraph();
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            /*
             Consuming the mouse event appears to be a Swing issue (can't find the exact bug).
             This is a simple enough workaround.
             */
            main.switchTo(ed);
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }
    }
}
