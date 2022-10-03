/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logic;

import java.io.File;

/**
 *
 * @author derek
 */
public class html {
    static String html="";
    public static String getHtml(String f, String name){
        boolean description=false ,data=false;
        String content[] = os.read(f);
        String desc="", dat="";
        for (int i = 0; i < content.length; i++) {
            if (description) {
                desc += content[i].substring(11, content[i].length()).replaceAll("=", "")+"\n";
            }
            if (data) {
                dat = content[i];
                for (int k = 0; k < content[i].length(); k++) {
                    dat=dat.replace("  ", " ");
                }
                dat=dat.replace(" ", "\n");
                data = false;
            }
            if (content[i].contains("c data description -")) {
                description = true;
            }
            if (content[i].contains("c end header -")) {
                data = true;
                description = false;
            }
        }
        addLine(desc, "desc.tmp");
        addLine(dat+"\neof", "dat.tmp");
        String[] f1 = os.read("desc.tmp");
        String[] f2 = os.read("dat.tmp");
        int l = f1.length;
        if (l>4) {
            l=4;
        }
        for (int i = 0; i < l; i++) {
            html+=f1[i]+": "+f2[i]+"<br>";
        }
        //System.out.println(html);
        new File("desc.tmp").delete();
        new File("dat.tmp").delete();
        return html.replace("  ", " ");
    }
    
    static void addLine(String txt, String file){
        txt = txt.replace("r --", "").replace("-", "");
        os.save(file, txt);
    }
}
