package ezcast.view;

import ezcast.WebServer.SimpleWebServer;
import ezcast.WebServer.jSocketWebServer;
import ezcast.controller.Control;
import ezcast.model.Devices;
import ezcast.utils.Constantes;
import ezcast.utils.LoggerHelper;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author AyA
 */
public class JFEzCast extends javax.swing.JFrame {

    /**
     * Creates new form JFEzCast
     */
    DefaultTableModel tmodel;
    Control ctrl;
    File faccepted;
    JPopupMenu jpmenu;
    SimpleWebServer sws;
    TimerTask taskLogger;
    LoggerHelper logs;
    jSocketWebServer jsws;
    private boolean serverStarted;
    private boolean pressedRigthClick;

    public JFEzCast() {
        initComponents();
        pressedRigthClick = false;
        serverStarted = false;
        faccepted = null;
        ctrl = new Control();
        logs = new LoggerHelper();
        sws = new SimpleWebServer();
        jsws = new jSocketWebServer();
        lookoutLogs();

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem11 = new javax.swing.JMenuItem();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenuItem8 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("EzCast for DLNA");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Devices"));

        jScrollPane1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jScrollPane1.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        jTable1.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Model", "Serial Number", "UDN"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 545, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane2.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        jTabbedPane2.setToolTipText("Logs");
        jTabbedPane2.setAutoscrolls(true);
        jTabbedPane2.setName(""); // NOI18N
        jTabbedPane2.setOpaque(true);

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(1);
        jTextArea1.setRows(1);
        jTextArea1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextArea1MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTextArea1);

        jTabbedPane2.addTab("Logs", jScrollPane2);

        jMenu1.setText("File");

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ezcast/images/open.png"))); // NOI18N
        jMenuItem1.setText("Load");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ezcast/images/save.png"))); // NOI18N
        jMenuItem2.setText("Save");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuItem3.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ezcast/images/shutdown_lg.png"))); // NOI18N
        jMenuItem3.setText(" Exit");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Tools");

        jMenuItem11.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ezcast/images/new.png"))); // NOI18N
        jMenuItem11.setText("Add custom device");
        jMenuItem11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem11ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem11);

        jMenuItem7.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ezcast/images/find.png"))); // NOI18N
        jMenuItem7.setText("Find devices");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem7);

        jMenuItem8.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_W, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ezcast/images/global.png"))); // NOI18N
        jMenuItem8.setText("Start Web Server");
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem8);

        jMenuBar1.add(jMenu2);

        jMenu3.setText("Help");

        jMenuItem4.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, 0));
        jMenuItem4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ezcast/images/help_documentation_page.png"))); // NOI18N
        jMenuItem4.setText("Help Contents");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem4);

        jMenuItem5.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_U, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ezcast/images/refresh.png"))); // NOI18N
        jMenuItem5.setText("Check for Updates");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem5);

        jMenuItem6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ezcast/images/icon_info.png"))); // NOI18N
        jMenuItem6.setText("About");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem6);

        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTabbedPane2))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane2)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        // TODO add your handling code here:
        JOptionPane.showMessageDialog(rootPane, "Available soon", "Information", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_jMenuItem5ActionPerformed


    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        //left click
        if (evt.getButton() == MouseEvent.BUTTON1) {
            //only select an elemnt of the table
            showNMenu(jTable1.getSelectedRow(), evt.getXOnScreen(), evt.getYOnScreen());
        }
        //right click
        if (evt.getButton() == MouseEvent.BUTTON3) {
            showNMenu(jTable1.getSelectedRow(), evt.getXOnScreen(), evt.getYOnScreen());
        }
    }//GEN-LAST:event_jTable1MouseClicked

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed

        JOptionPane.showMessageDialog(rootPane, "jEzCast Ver. 1.0\nMade by Allan Ayes\n(10/07/2022 - 03:48am)", "About jEzCast", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
        saveAllData();
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        loadAllData();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        // TODO add your handling code here:
        JOptionPane.showMessageDialog(rootPane, "Espere mientras se detectan los dispositivos DLNA/UPNP en la red...");
        ctrl.lookupDevices();
        ctrl.loadMainConfig();
        loadTableData(ctrl.getDevices());
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void jMenuItem11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem11ActionPerformed
        // TODO add your handling code here:
        String url = JOptionPane.showInputDialog(rootPane, "Type the URL of custom device ", "Attention", JOptionPane.INFORMATION_MESSAGE);
        ctrl.addUrlToDevLost(url);
        ctrl.loadMainConfig();
        loadTableData(ctrl.getDevices());
    }//GEN-LAST:event_jMenuItem11ActionPerformed

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed
        // TODO add your handling code here:
        if (!serverStarted) {
            int opc = JOptionPane.showConfirmDialog(rootPane, "Do you want to start the WebService?", "Attention", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (opc == JOptionPane.YES_OPTION) {
                startServer();
                serverStarted = true;
                JOptionPane.showMessageDialog(rootPane, "WebService listening for connections\nURL: http://" + ctrl.getUtils().getMyIP() + ":8081\nTo RTP service:\nhttp://" + ctrl.getUtils().getMyIP() + ":8081/video.mp4", "Information", JOptionPane.INFORMATION_MESSAGE);
            } else if (opc == JOptionPane.NO_OPTION) {
                JOptionPane.showMessageDialog(rootPane, "Remember, to use EzCast properly, you must start the WebService first.", "Attention", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(rootPane, "WebService is already running", "Information", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jMenuItem8ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        // TODO add your handling code here:
        JOptionPane.showMessageDialog(rootPane, "Available soon", "Information", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        // TODO add your handling code here:
        Exit(evt);
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jTextArea1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextArea1MouseClicked
        // TODO add your handling code here:

        JPopupMenu logsMenu = new JPopupMenu("Logs Menu");
        JMenuItem clear = new JMenuItem("Clear logs");
        if (evt.getButton() == MouseEvent.BUTTON1) {
            int x = evt.getXOnScreen();
            int y = evt.getYOnScreen();
            int xSquare = logsMenu.getLocation().x + logsMenu.getWidth();
            int ySquare = logsMenu.getLocation().y + logsMenu.getHeight();
            System.out.println(x + "," + y + "\n" + xSquare + "," + ySquare);
            if ((evt.getXOnScreen() < x || evt.getXOnScreen() > xSquare) && (evt.getY() < y || evt.getY() > ySquare)) {
                logsMenu.setVisible(false);
            }
        }
        if (evt.getButton() == MouseEvent.BUTTON3) {
            if (pressedRigthClick) {
                logsMenu.setVisible(false);
                pressedRigthClick = false;
            }
            clear.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    logsMenu.setVisible(false);
                    jTextArea1.setText("");
                }
            });
            logsMenu.add(clear);
            logsMenu.setLocation(evt.getXOnScreen(), evt.getYOnScreen());
            logsMenu.setVisible(true);
            pressedRigthClick = true;
        }
    }//GEN-LAST:event_jTextArea1MouseClicked

    private void lookoutLogs() {
        taskLogger = new TimerTask() {

            @Override
            public void run() {
                //Control logs
                logs = ctrl.getLogs();
                addToLog(logs);
                ctrl.getLogs().reset();
                //Connector logs                
                logs = ctrl.getConnector().getLogs();
                addToLog(logs);
                ctrl.getConnector().getLogs().reset();
                //Utils logs
                logs = ctrl.getUtils().getLogs();
                addToLog(logs);
                ctrl.getUtils().getLogs().reset();
                //WebServer logs
                if (jsws != null) {
                    logs = jsws.getLogs();
                    addToLog(logs);
                    jsws.getLogs().reset();
                }
            }
        };
        Timer t = new Timer();
        t.scheduleAtFixedRate(taskLogger, 0, 1000);
    }

    private void addToLog(LoggerHelper logs) {
        int count = 0;
        if (logs != null && !logs.getLogs().isEmpty()) {
            String[] jTAContent = jTextArea1.getText().split("\n");

            for (String parts : jTAContent) {
                if (parts.contains(logs.getLogs()) || parts.equalsIgnoreCase(logs.getLogs())) {
                    count++;
                }
            }
            if (count == 0) {
                jTextArea1.append(logs.getLogs() + "\n");
            }
        }
    }

    private void loadTableData(LinkedList<Devices> devs) {
        tmodel = new DefaultTableModel(new String[]{"Name", "Model", "Serial Number", "UDN"}, 0);
        //System.out.println(devs.size());
        if (devs.size() > 0) {
            LinkedList<Devices> llist = devs;
            //tmodel.setRowCount(devs.size());
            for (Devices obj : llist) {
                tmodel.addRow(new Object[]{
                    obj.getName(),
                    obj.getModelName(),
                    obj.getSerialNumber(),
                    obj.getUdn()});
            }
            jTable1.setModel(tmodel);
        }
    }

    private void Exit(ActionEvent evt) {
        int res = JOptionPane.showConfirmDialog(rootPane, "Do you want to exit?", "Attention", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (res == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    private void startServer() {
        Thread t = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    jsws.startServer();
                } catch (IOException ex) {
                    logs.addLog(Level.SEVERE, "IOE at JFEzCast constructor method: " + ex.getMessage());
                }
            }
        });
        t.start();
        serverStarted = true;
    }

    private void saveAllData() {
        JFileChooser chooser = new JFileChooser();
        int opc = chooser.showSaveDialog(null);
        if (opc == JFileChooser.APPROVE_OPTION) {
            try {
                File sFile = chooser.getSelectedFile();
                FileOutputStream fos = new FileOutputStream(sFile);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(ctrl.getDevices());
                oos.close();
                fos.close();
                JOptionPane.showMessageDialog(rootPane, "Data saved!", "Attention", JOptionPane.INFORMATION_MESSAGE);
            } catch (FileNotFoundException ex) {
                System.out.println(ex.getMessage());
                logs.addLog(Level.SEVERE, "File Not Found at save method: " + ex.getMessage());
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
                logs.addLog(Level.SEVERE, "IOE at save method: " + ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(rootPane, "Data could not be save", "Attention", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadAllData() {
        try {
            JFileChooser jfc = new JFileChooser();
            int res = jfc.showOpenDialog(this);
            if (res == JFileChooser.APPROVE_OPTION) {
                File fsave = jfc.getSelectedFile();
                if (fsave.canRead()) {
                    FileInputStream fis = new FileInputStream(fsave);
                    ObjectInputStream ois = new ObjectInputStream(fis);
                    ctrl = new Control();
                    ctrl.LoadDeviceList((LinkedList<Devices>) ois.readObject());
                    ois.close();
                    fis.close();
                    System.out.println("Data loaded");
                    loadTableData(ctrl.getDevices());
                    JOptionPane.showMessageDialog(rootPane, "Data loaded", "Attention", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
            logs.addLog(Level.SEVERE, "File Not Found at load method: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            logs.addLog(Level.SEVERE, "IOE at load method: " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
            logs.addLog(Level.SEVERE, "Class Not Found at load method: " + ex.getMessage());
        }
    }

    private void showNMenu(int index, int posx, int posy) {
        if (index >= 0) {
            jpmenu = new JPopupMenu("Options");
            JMenuItem mitem1 = new JMenuItem("Show all info");
            mitem1.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    jpmenu.setVisible(false);
                    ctrl.getDevices().get(index).printAllData();
                }
            });
            JMenuItem mitem2 = new JMenuItem("Load Local Multimedia file");
            mitem2.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ctrl.loadStaticVideoLink();
                    jsws.stopServer();
                    jpmenu.setVisible(false);
                    //Getting selected device on table
                    Devices captured = ctrl.getDevices().get(index);
                    if (!serverStarted) {
                        int opc = JOptionPane.showConfirmDialog(rootPane, "Do you want to start the WebService?", "Attention", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                        if (opc == JOptionPane.YES_OPTION) {
                            startServer();
                            serverStarted = true;
                            sendDLNARequest(captured);
                        } else if (opc == JOptionPane.NO_OPTION) {
                            JOptionPane.showMessageDialog(rootPane, "To use this function you must start the WebService", "Attention", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        sendDLNARequest(captured);
                    }
                }
            });
            JMenuItem mitem3 = new JMenuItem("Load Multimedia from URL");
            mitem3.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    jpmenu.setVisible(false);
                    //Getting selected device on table
                    Devices captured = ctrl.getDevices().get(index);
                    String urlLink = JOptionPane.showInputDialog(rootPane, "Type the URL of custom multimedia ", "Attention", JOptionPane.INFORMATION_MESSAGE);
                    if (urlLink!=null) {
                        ctrl.loadVideoLink(urlLink);
                        sendDLNARequest(captured);
                    }

                }
            });
            JMenuItem mitem4 = new JMenuItem("Edit Device");
            mitem4.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    jpmenu.setVisible(false);
                    JFEditDev jv = new JFEditDev(ctrl, index);
                    jv.setVisible(true);
                    loadTableData(ctrl.getDevices());

                }
            });
            jpmenu.add(mitem1);
            jpmenu.add(mitem2);
            jpmenu.add(mitem3);
            jpmenu.add(mitem4);
            jpmenu.setLocation(posx, posy);
            jpmenu.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(rootPane, "Must select an element of the table", "Attention", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void sendDLNARequest(Devices captured) {
        //Stop last reproduction
        String data;
        data = Constantes.getXmlHead() + Constantes.getXmlStop() + Constantes.getXmlFoot();
        ctrl.SendDataToDevice(data, captured.getSoapActions()[4], captured);

        //Send a new URL to load
        data = Constantes.getXmlHead() + ctrl.getUriLoad() + Constantes.getXmlFoot();
        ctrl.SendDataToDevice(data, captured.getSoapActions()[0], captured);

        //Play action
        data = Constantes.getXmlHead() + Constantes.getUriPlay() + Constantes.getXmlFoot();
        ctrl.SendDataToDevice(data, captured.getSoapActions()[2], captured);
    }

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
            java.util.logging.Logger.getLogger(JFEzCast.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JFEzCast.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JFEzCast.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JFEzCast.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JFEzCast().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem11;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextArea jTextArea1;
    // End of variables declaration//GEN-END:variables
}
