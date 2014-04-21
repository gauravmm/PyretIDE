/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */

package edu.brown.cs.cutlass.editor;

/**
 *
 * @author Gaurav Manek
 */
public class PnlEditor extends javax.swing.JPanel implements Editor {
    
    private final EditorClient client;
    
    /**
     * Creates new form EditorPanel
     * @param client
     */
    public PnlEditor(EditorClient client) {
        initComponents();
        
        this.client = client;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSplitPane2 = new javax.swing.JSplitPane();
        scrlEditor = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        scrlCMD = new javax.swing.JScrollPane();
        jTextArea3 = new javax.swing.JTextArea();

        jSplitPane2.setDividerLocation(400);
        jSplitPane2.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPane2.setResizeWeight(1.0);

        scrlEditor.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrlEditor.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jTextArea1.setText("\ndata BinaryTree:\n  | mt\n  | node(value :: Number, left :: BinaryTree, right :: BinaryTree)\nend\n\nfun tree-to-list(tree :: BinaryTree) -> List:\n  doc: \"tree-to-list converts a binary tree into a list, preserving the order of the stored values.\"\n  cases(BinaryTree) tree:\n    | mt => []\n    | node(v, l, r) => tree-to-list(l) + [v] + tree-to-list(r)\n  end\nwhere:\n  tree-to-list(mt) is []\n  tree-to-list(node(4, node(2, mt, mt), node(6, mt, mt))) is [2, 4, 6]\nend\n\nfun is-sorted(lst :: List) -> Bool:\n  doc: \"is-sorted returns true if the list is sorted in ascending order.\"\n   cases(List) lst:\n    | empty => true\n    | link(f, r) => \n      min-rest = for fold(min from f+1, elt from r):\n          if (elt < min):\n            elt\n          else:\n            min\n          end\n      end\n      ((f < min-rest) and is-sorted(r))\n  end\nwhere:\n  is-sorted([]) is true\n  is-sorted([1]) is true\n  is-sorted([1, 2, 3]) is true\n  is-sorted([1, 3, 2]) is false\nend\nfun is-bst(tree :: BinaryTree) -> Bool:\n  doc: \"is-bst returns true if tree is a Binary Search Tree and false otherwise.\"\n  is-sorted(tree-to-list(tree))\nwhere:\n  is-bst(mt) is true\n  is-bst(node(4, node(2, mt, mt), node(6, mt, mt))) is true\n  is-bst(node(1, node(2, mt, mt), node(6, mt, mt))) is false\n  is-bst(node(4, node(5, mt, mt), node(6, mt, mt))) is false\nend\n\n");
        scrlEditor.setViewportView(jTextArea1);

        jSplitPane2.setLeftComponent(scrlEditor);

        scrlCMD.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        jTextArea3.setColumns(20);
        jTextArea3.setRows(5);
        jTextArea3.setText("\n\n\n\n> raco pyret test.arr\nAll 10 tests passed, mate!\n> _");
        scrlCMD.setViewportView(jTextArea3);

        jSplitPane2.setRightComponent(scrlCMD);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 472, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 534, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea3;
    private javax.swing.JScrollPane scrlCMD;
    private javax.swing.JScrollPane scrlEditor;
    // End of variables declaration//GEN-END:variables

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void halt() throws IllegalStateException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void clipboardCut() throws IllegalStateException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void clipboardCopy() throws IllegalStateException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void clipboardPaste() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getBuffer() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean hasSelection() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getSelection() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void close() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public CharSequence getSelectedText(){
        return jTextArea1.getSelectedText();
    }
    
    public CharSequence getText(){
        return jTextArea1.getText();
    }
    
    public void paste(String toPaste){
        int position = jTextArea1.getCaretPosition();
        String current = jTextArea1.getText();
        jTextArea1.setText(current.substring(0, position) + toPaste + current.substring(position));
        jTextArea1.setCaretPosition(position + toPaste.length());
    }
    
    public void selectAll(){
        jTextArea1.setSelectionStart(0);
        jTextArea1.setSelectionEnd(jTextArea1.getText().length());
    }
    
    public void deleteSelection(){
        String selected = jTextArea1.getSelectedText();
        int position = jTextArea1.getCaretPosition();
        String current = jTextArea1.getText();
        jTextArea1.setText(current.substring(0, position - selected.length()) + current.substring(position));
        jTextArea1.setCaretPosition(position - selected.length());
    }

}
