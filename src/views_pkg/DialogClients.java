/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views_pkg;

import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import model_pkg.Conexion;

/**
 *
 * @author Gomez
 */
public class DialogClients extends javax.swing.JFrame {

    /**
     * Creates new form DialogClients
     */
    Conexion con = new Conexion();
    Connection cn;
    Statement st;
    ResultSet rs;
    DefaultTableModel modelo;

    public DialogClients() {
        initComponents();
    }

    DialogClients(FrameHospitals aThis, boolean b) {
        initComponents();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        show_owners();
        clear_rows_table();
        show_owners();
    }

    void show_owners() {
        String sql = "SELECT * FROM `tb_pet_owners` WHERE 1";
        try {
            cn = con.getConnection();
            st = cn.createStatement();
            rs = st.executeQuery(sql);
            //Los datos que devuelve la consulta se muestran en la tabla
            Object[] owner = new Object[6];
            modelo = (DefaultTableModel) tb_owners.getModel();
            while (rs.next()) {
                owner[0] = rs.getInt("id");
                owner[1] = rs.getString("owner");
                owner[2] = rs.getString("document_type");
                owner[3] = rs.getInt("document");
                owner[4] = rs.getString("contact");
                owner[5] = rs.getString("gender");
                modelo.addRow(owner);
                System.out.println(rs.getInt("id"));
            }
            tb_owners.setModel(modelo);
        } catch (SQLException e) {

        }
    }

    void add_pet_owner() {
        String owner = txt_owner.getText();
        String document_type = cb_document_type.getSelectedItem().toString();
        int document = Integer.parseInt(txt_document.getText());
        String contact = txt_contact.getText();
        String gender = "";
        if (rb_f.isSelected()) {
            gender = "Femenino";
        } else {
            gender = "Masculino";
        }
        if (owner.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Falta ingresar el nombre del dueño de la mascota");
        } else {
            String query = "INSERT INTO `tb_pet_owners`(`owner`, `document_type`, `document`, `contact`, `gender`) VALUES('" + owner + "', '" + document_type + "'," + document + "," + contact + ",'" + gender + "')";

            try {
                cn = con.getConnection();
                st = cn.createStatement();
                st.executeUpdate(query);
                JOptionPane.showMessageDialog(this, "El dueño de la mascota ha sido creado");
                clear_rows_table();
                show_owners();
            } catch (HeadlessException | SQLException e) {
                JOptionPane.showMessageDialog(this, "No se pudo crear el dueño de la mascota");
            }
        }
    }

    void edit_owner() {
        int id = Integer.parseInt(txt_id.getText());
        String name = txt_owner.getText();
        String document_type = cb_document_type.getSelectedItem().toString();
        int document = Integer.parseInt(txt_document.getText());
        String contact = txt_contact.getText();
        String gender;
        if (rb_f.isSelected()) {
            gender = "Femenino";
        } else {
            gender = "Masculino";
        }
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Falta ingresar el nombre del dueño");
        } else {
            String query = "UPDATE tb_pet_owners SET owner= '" + name + "', document_type= '" + document_type + "', document = " + document + ", contact=" + contact + ",gender= '" + gender + "' WHERE id = " + id;
            //UPDATE tb_persons SET dni =dni, nombre= 'name' WHERE id = id
            try {
                cn = con.getConnection();
                st = cn.createStatement();
                st.executeUpdate(query);
                JOptionPane.showMessageDialog(this, "El hospital ha sido modificado con éxito");
                clear_rows_table();
                show_owners();
            } catch (HeadlessException | SQLException e) {
                JOptionPane.showMessageDialog(this, "No se pudo modificar la información del dueño");
            }
        }
    }

    void delete_owner() {
        int fila = tb_owners.getSelectedRow();
        int id = Integer.parseInt(txt_id.getText());
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "No has seleccionado un dueño de mascota");
        } else {

            System.out.println("ID: " + id);
            String query = "DELETE FROM tb_pet_owners WHERE id = " + id;
            try {
                cn = con.getConnection();
                st = cn.createStatement();
                st.executeUpdate(query);
                JOptionPane.showMessageDialog(this, "El dueño ha sido eliminado con exito");
                clear_rows_table();
                show_owners();
            } catch (HeadlessException | SQLException e) {
            }
        }
    }

    void clear_rows_table() {
        for (int i = 0; i < tb_owners.getRowCount(); i++) {
            modelo.removeRow(i);
            i = i - 1;
        }
        txt_owner.setText("");
        txt_document.setText("");
        txt_contact.setText("");
        txt_id.setText("");
        cb_document_type.setSelectedIndex(0);
        rb_f.setSelected(false);
        rb_m.setSelected(false);
    }

    private void tb_ownersMouseClicked(java.awt.event.MouseEvent evt) {
        int row = tb_owners.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Debes seleccionar un hospital");
        } else {
            int id = Integer.parseInt((String) tb_owners.getValueAt(row, 0).toString());
            String name = tb_owners.getValueAt(row, 1).toString();
            String document_type = tb_owners.getValueAt(row, 2).toString();
            int document = Integer.parseInt((String) tb_owners.getValueAt(row, 3).toString());
            String contact = tb_owners.getValueAt(row, 4).toString();
            String gender = tb_owners.getValueAt(row, 5).toString();
            System.out.println(id + " - " + name + " - " + gender);
            txt_id.setText("" + id);
            txt_owner.setText(name);
            txt_document.setText("" + document);
            txt_contact.setText(contact);
            cb_document_type.setSelectedItem(document_type);

            if (gender.equals("Femenino")) {
                rb_f.setSelected(true);
                rb_m.setSelected(false);
            }
            if (gender.equals("Masculino")) {
                rb_m.setSelected(true);
                rb_f.setSelected(false);
            }
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

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txt_owner = new javax.swing.JTextField();
        txt_document = new javax.swing.JTextField();
        txt_contact = new javax.swing.JTextField();
        cb_document_type = new javax.swing.JComboBox<>();
        rb_f = new javax.swing.JRadioButton();
        rb_m = new javax.swing.JRadioButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tb_owners = new javax.swing.JTable();
        txt_id = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setText("FORMULARIO REGISTRO DE DUEÑO");

        jLabel2.setText("NOMBRE");

        jLabel3.setText("TIPO DE DOCUMENTO");

        jLabel4.setText("NUMERO DE DOCUMENTO");

        jLabel5.setText("CONTACTO");

        jLabel6.setText("GENERO");

        cb_document_type.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "C.C.", "T.I", "C.E" }));

        rb_f.setText("FEMENINO");

        rb_m.setText("MASCULINO");

        jButton1.setText("AGREGAR CLIENTE");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("EDITAR INFORMACION");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("ELIMINAR CLIENTE");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        tb_owners.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID", "NOMBRE ", "TIPO DOCUMENTO", "NUMERO", "CONTACTO", "GENERO"
            }
        ));
        jScrollPane1.setViewportView(tb_owners);

        jLabel7.setText("ID");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 657, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(262, 262, 262)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(73, 73, 73)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel6))
                                .addGap(27, 27, 27)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(txt_contact, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)
                                        .addComponent(txt_document, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)
                                        .addComponent(txt_owner)
                                        .addComponent(cb_document_type, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(rb_f)
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jButton3)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(rb_m)
                                                .addGap(38, 38, 38)
                                                .addComponent(jLabel7))))))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jButton1)
                                .addGap(69, 69, 69)
                                .addComponent(jButton2)
                                .addGap(167, 167, 167)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txt_id, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(94, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(txt_owner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(24, 24, 24))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(cb_document_type, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4)
                    .addComponent(txt_document, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txt_contact, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(rb_f)
                    .addComponent(rb_m)
                    .addComponent(jLabel7)
                    .addComponent(txt_id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addGap(62, 62, 62)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(255, 255, 255))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        add_pet_owner();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        edit_owner();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        delete_owner();
    }//GEN-LAST:event_jButton3ActionPerformed

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
            java.util.logging.Logger.getLogger(DialogClients.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DialogClients.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DialogClients.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DialogClients.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DialogClients().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cb_document_type;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JRadioButton rb_f;
    private javax.swing.JRadioButton rb_m;
    private javax.swing.JTable tb_owners;
    private javax.swing.JTextField txt_contact;
    private javax.swing.JTextField txt_document;
    private javax.swing.JTextField txt_id;
    private javax.swing.JTextField txt_owner;
    // End of variables declaration//GEN-END:variables
}
