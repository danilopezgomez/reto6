/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model_pkg;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Gomez
 */
public class DataModelDB {

    public ArrayList<DataDB> getOwners(int idOwner) {
        Conexion con = new Conexion();
        Connection cn;
        Statement st;
        ResultSet rs;
        DataDB datosDB;
        ArrayList list = new ArrayList();
        try {
            String sql = "SELECT id, owner FROM `tb_pet_owners` WHERE 1";
            cn = con.getConnection();
            st = cn.createStatement();
            rs = st.executeQuery(sql);
            if (cn != null) {
                System.out.println("Conexión éxitosa DataModelDB");
                while (rs.next()) {
                    datosDB = new DataDB();
                    datosDB.setIdOwner(rs.getInt("id"));
                    datosDB.setOwner(rs.getString("owner"));
                    list.add(datosDB);
                }
            } else {
                JOptionPane.showMessageDialog(null, ".::Error al realizar la consulta::.", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, ".::Error en la conexión::.", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        return list;
    }

}
