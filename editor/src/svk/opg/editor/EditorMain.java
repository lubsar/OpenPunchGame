package svk.opg.editor;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglAWTCanvas;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import javax.swing.JFileChooser;
import svk.opg.game.character.skeletal.Skelet;
import svk.opg.game.character.skeletal.serialization.SkeletLegacyIO;

/**
 *
 * @author Lubomir Hlavko
 */
public class EditorMain extends javax.swing.JFrame {

    private LwjglApplication app;
    
    /**
     * Creates new form EditorMain
     */
    public EditorMain() {
        initComponents();
        
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = 800;
        config.height = 600;
        
        ApplicationListener listener = new ApplicationListener() {
            @Override
            public void create() {
               // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void resize(int width, int height) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void render() {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void pause() {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void resume() {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void dispose() {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
        
         app = new LwjglApplication(listener, config, editorCanvas );
         
        Gdx.graphics.setContinuousRendering(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        fileChooser = new javax.swing.JFileChooser();
        treeEditorPane = new javax.swing.JSplitPane();
        editorPropertiesPane = new javax.swing.JSplitPane();
        editorCanvas = new java.awt.Canvas();
        skeletonTreePane = new javax.swing.JScrollPane();
        skeletonTree = new javax.swing.JTree();
        menuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        newMenuBttn = new javax.swing.JMenuItem();
        openMenuBttn = new javax.swing.JMenuItem();
        saveMenuBttn = new javax.swing.JMenuItem();
        exitSeparator = new javax.swing.JPopupMenu.Separator();
        exitMenuBttn = new javax.swing.JMenuItem();
        editMenu = new javax.swing.JMenu();

        fileChooser.setApproveButtonText("Open");
        fileChooser.setCurrentDirectory(null);
        fileChooser.setDialogTitle("Open skeleton");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("OPG Skeletal Editor");

        treeEditorPane.setDividerLocation(200);
        treeEditorPane.setDividerSize(2);

        editorPropertiesPane.setDividerLocation(1000);
        editorPropertiesPane.setDividerSize(2);

        editorCanvas.setPreferredSize(new java.awt.Dimension(1000, 0));
        editorPropertiesPane.setLeftComponent(editorCanvas);

        treeEditorPane.setRightComponent(editorPropertiesPane);

        skeletonTree.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        skeletonTreePane.setViewportView(skeletonTree);

        treeEditorPane.setLeftComponent(skeletonTreePane);

        fileMenu.setText("File");

        newMenuBttn.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        newMenuBttn.setText("New");
        newMenuBttn.setToolTipText("");
        fileMenu.add(newMenuBttn);

        openMenuBttn.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        openMenuBttn.setText("Open");
        openMenuBttn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openMenuBttnActionPerformed(evt);
            }
        });
        fileMenu.add(openMenuBttn);

        saveMenuBttn.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        saveMenuBttn.setText("Save");
        saveMenuBttn.setToolTipText("");
        fileMenu.add(saveMenuBttn);
        fileMenu.add(exitSeparator);

        exitMenuBttn.setText("Exit");
        fileMenu.add(exitMenuBttn);

        menuBar.add(fileMenu);

        editMenu.setText("Edit");
        menuBar.add(editMenu);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(treeEditorPane, javax.swing.GroupLayout.DEFAULT_SIZE, 1280, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(treeEditorPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 699, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void openMenuBttnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openMenuBttnActionPerformed
        int status = fileChooser.showOpenDialog(this);
        System.out.println(Gdx.graphics.getFramesPerSecond());
        if(status == JFileChooser.APPROVE_OPTION) {
            
            app.postRunnable(new Runnable() {
                public void run() {
                    Skelet skelet = SkeletLegacyIO.deserializeSkelet(app.getFiles().absolute(fileChooser.getSelectedFile().getAbsolutePath()));
                    System.out.println(skelet.boneIds);//Gdx.files.absolute(fileChooser.getSelectedFile().getPath());
            
                    System.out.println(skelet.boneIds);
                }
            });
            
            Gdx.graphics.requestRendering();
          
        }
    }//GEN-LAST:event_openMenuBttnActionPerformed

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
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(EditorMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EditorMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EditorMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EditorMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
   
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EditorMain().setVisible(true);
            }
        });
           
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu editMenu;
    private java.awt.Canvas editorCanvas;
    private javax.swing.JSplitPane editorPropertiesPane;
    private javax.swing.JMenuItem exitMenuBttn;
    private javax.swing.JPopupMenu.Separator exitSeparator;
    private javax.swing.JFileChooser fileChooser;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuItem newMenuBttn;
    private javax.swing.JMenuItem openMenuBttn;
    private javax.swing.JMenuItem saveMenuBttn;
    private javax.swing.JTree skeletonTree;
    private javax.swing.JScrollPane skeletonTreePane;
    private javax.swing.JSplitPane treeEditorPane;
    // End of variables declaration//GEN-END:variables
}
