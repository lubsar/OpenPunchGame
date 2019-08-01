package svk.opg.editor.treeeditor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Enumeration;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import svk.opg.editor.EditorMain;
import svk.opg.editor.NewBoneDialog;
import svk.opg.editor.RenameBoneDialog;
import svk.opg.game.character.skeletal.Bone;
import svk.opg.game.character.skeletal.Skelet;
import java.awt.Color;
import javax.swing.AbstractCellEditor;
import javax.swing.JLabel;
import javax.swing.tree.TreeCellEditor;

/**
 *
 * @author Lubomir Hlavko
 */
public class TreeViewHandler {
    private EditorMain editor;
    
    private JTree tree;
    private DefaultTreeModel model;
    private DefaultMutableTreeNode treeRoot;
    
    public TreeViewHandler(JTree tree, EditorMain editor) {
        this.tree = tree;
        this.editor = editor;
        tree.addMouseListener(new MouseHandler());
    }

    public void setSkelet(Skelet skelet) {
         Bone rootBone = skelet.getBone(Skelet.ROOT_BONE_NAME);
         
         treeRoot = new DefaultMutableTreeNode(rootBone);
         model = new DefaultTreeModel(treeRoot);
         
         addChildNodes(treeRoot, rootBone);
         tree.setModel(model);
         
         tree.setCellRenderer(new BoneNodeRenderer());
         tree.setCellEditor(new BoneNodeTreeCellEditor());
    }
    
    private void addChildNodes(DefaultMutableTreeNode node, Bone bone) {
        if(bone.children.isEmpty()) {
            return;
        }
        
        for(Bone child: bone.children) {
            DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(child);
            node.add(childNode);
            addChildNodes(childNode, child);
        }
    }
    
    private TreePath findNode(String name) {
        @SuppressWarnings("unchecked")
        Enumeration<DefaultMutableTreeNode> e = treeRoot.depthFirstEnumeration();
        while (e.hasMoreElements()) {
            DefaultMutableTreeNode node = e.nextElement();
            if (node.toString().equals(name)) {
                return new TreePath(node.getPath());
            }
        }
        return null;
    }
    
    public void removeNode(String name) {
        TreePath currentSelection = findNode(name);
        if (currentSelection != null) {
            DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode)(currentSelection.getLastPathComponent());
            MutableTreeNode parent = (MutableTreeNode)(currentNode.getParent());
            if (parent != null) {
                model.removeNodeFromParent(currentNode);
            }
        } 
    }
    
    public void addNode(Bone bone) {
        DefaultMutableTreeNode parrent = (DefaultMutableTreeNode) findNode(bone.parrent.getName()).getLastPathComponent();
        DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(bone);
        
        model.insertNodeInto(newNode, parrent, parrent.getChildCount());
        tree.scrollPathToVisible(new TreePath(newNode.getPath()));
    }
    
    public void updateNodeText(Bone bone) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) findNode(bone.getName()).getLastPathComponent();
        model.nodeChanged(node);
    }
    
    private class BoneNodeRenderer implements TreeCellRenderer {
        private DefaultTreeCellRenderer defaultRenderer;
        
        private JPanel component;
        private JLabel label;
        
        
        public BoneNodeRenderer() {
            defaultRenderer = new DefaultTreeCellRenderer();
            
            component = new JPanel(new BorderLayout());
            label = new JLabel();
            
            label.setBackground(new Color(204, 204, 204));
            label.setPreferredSize(new Dimension(90, 16));
            label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            label.setOpaque(true);
            
            component.add(label, BorderLayout.CENTER);
            component.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1), new javax.swing.border.LineBorder(new java.awt.Color(119, 119, 119), 1, true)));
        }
        
        @Override
        public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
            Bone bone = (Bone) ((DefaultMutableTreeNode) value).getUserObject();
            
            label.setText(bone.getName());
            return component;
        }
    }
    
    private class BoneNodeTreeCellEditor extends AbstractCellEditor implements TreeCellEditor {
        private BonePropPanel bpp;
        private Bone current;
        
        public BoneNodeTreeCellEditor() {
            bpp = new BonePropPanel();
        }
        
        @Override
        public Object getCellEditorValue() {
           current.setAngle(bpp.getAngle());
           current.setLength(bpp.getLength());
           
           return current;
        }

        @Override
        public Component getTreeCellEditorComponent(JTree tree, Object value, boolean isSelected, boolean expanded, boolean leaf, int row) {
            current = (Bone) ((DefaultMutableTreeNode) value).getUserObject();
            
            bpp.setValues(current.getName(), current.getLength(), current.getAngle());
            
            return bpp;
        }   
    }
    
    private class MouseHandler extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            if(e.getButton() == MouseEvent.BUTTON3) {
                int mouseX = e.getX();
                int mouseY = e.getY();
                
                TreePath path = tree.getPathForLocation(mouseX, mouseY);
                 if (path == null) {
                     return;
                 }

                tree.setSelectionPath(path);
                DefaultMutableTreeNode selected = (DefaultMutableTreeNode)path.getLastPathComponent();
                
                PopupMenu popup = new PopupMenu(selected);
                popup.show(tree.getParent(), mouseX, mouseY);
            }
        }
    }
    
    private class PopupMenu extends JPopupMenu {
        private JMenuItem addChild;
        private JMenuItem remove;
        private JMenuItem rename;
        
        private final DefaultMutableTreeNode node;
        
        public PopupMenu(final DefaultMutableTreeNode node) {
            super();
            
            this.node = node;
            this.addChild = new JMenuItem("Add child");
            this.remove = new JMenuItem("Remove");
            this.rename = new JMenuItem("Rename");
                    
            addChild.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                   editor.showNewBoneDialog(new NewBoneDialog.NewBoneDialogHandler() {
                       @Override
                       public void okPressed(String name, float length, float angle) {
                           //System.out.print("New dialog OK pressed");
                           editor.addBone(name, node.toString(), length, angle);
                       }

                       @Override
                       public void cancelPressed() {
                           //System.out.print("New dialog Cancel pressed");
                       }
                   });
                }
            });
            
            remove.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {
                    editor.removeBone(node.toString());
                }
            });
            
            rename.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                   editor.showRenameBoneDialog(new RenameBoneDialog.RenameBoneDialogHandler() {
                       @Override
                       public void okPressed(String name) {
                          editor.renameBone((Bone) node.getUserObject(), name);
                       }

                       @Override
                       public void cancelPressed() {
                          
                       }
                   });
                }
            });
            
            add(addChild);
            add(rename);
            add(remove);
        }
    }
}
