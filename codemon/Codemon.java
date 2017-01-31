/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import javax.swing.JPanel;
/**
 *
 * @author jbray_49
 */
public class Codemon{
    
    static
    {
        System.loadLibrary("codemon");
    }

    native void parse(String file_name, String file_path, String c_path);

    native int runTest(String file_name, String file_path, String r_path, int interations);

    native int runSelf(String file1_name, String file2_name, String file_path, String file2_path, String r_path, int iterations);

    native void runPVP(String file_name, String file_path, String r_path, int iterations, int vs);

    native int getReport(String file_name, String reportID);

    /*public Codemon(){
}*/
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Codemon CM = new Codemon();
        Panel window = new Panel(CM);
        window.setVisible(true);
    }
}
