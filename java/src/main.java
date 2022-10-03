
import java.io.File;
import java.util.Scanner;
import logic.html;
import logic.os;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

/**
 *
 * @author derek
 */
public class main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] files) {
        
        if (files.length>0) {
            Scanner input = new Scanner(System.in);
            String db = "stars";
            String oldfile = "output.sql";
            File f = new File(oldfile);
            f.delete();
            int l = files.length;
            int count = 1;
            for (int i = 0; i < l; i++) {
                String content[] = os.read(files[i]);
                String name = "Unknow", x = null, y = null, size = null, date = null;
                for (int j = 0; j < content.length; j++) {
                    if (content[j].contains("ROIxpos")) {
                        x = content[j].replace("c ROIxpos = ", "").replace("/ Raster X position [pixel]", "").replace(" ", "");
                    }
                    if (content[j].contains("ROIypos")) {
                        y = content[j].replace("c ROIypos = ", "").replace("/ Raster Y position [pixel] ", "").replace(" ", "");
                    }
                    if (content[j].contains("CCDPsiz")) {
                        size = content[j].replace("c CCDPsiz =  ", "").replace("/ CCD physical pixel size ", "").replace("um", "").replace("x", ".").replace(" ", "");
                    }
                    if (content[j].contains("ObsStaDa")) {
                        date = content[j].replace("c ObsStaDa= ", "").replace("/ observation start date ", "").replace(" ", "");
                    }
                    if (content[j].contains("StarInFo")) {
                        name = content[j].replace("c StarInFo= ", "").replace("/ star HD number + info", "").replace("  ", "");
                    }
                }
                if (x!=null && y!=null && date!=null) {
                    String sql = "";
                    String query = "INSERT INTO " + db + " (nombre, x, y, size, starDate, info) VALUES (\"" + name + "\"," + x + "," + y + "," + size + ",\"" + date + "\", \""+html.getHtml(files[i],name).replace("\n", "")+"\");";
                    if (f.exists()) {
                        String[] d = os.read(oldfile);
                        for (int j = 0; j < d.length; j++) {
                            sql+=d[j];
                        }
                    }
                    sql += query + "\n";
                    os.save("output.sql", sql);
                    System.out.println("[" + count + "/" + l + "] " + date + " Done");
                }
                else{
                    System.out.println("[" + count + "/" + l + "] " + "NO DATA!");
                }
                count++;
            }  
        }
    }
}
