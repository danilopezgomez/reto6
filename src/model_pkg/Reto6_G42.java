package model_pkg;

import views_pkg.FrameHospitals;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Gomez
 */
public class Reto6_G42 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Conexion inst_connect = new Conexion();
        FrameHospitals inst_frame = new FrameHospitals();
        inst_connect.getConnection();
        inst_frame.setVisible(true);

    }
    
}
