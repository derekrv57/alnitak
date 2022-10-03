package logic;

import java.awt.Component;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Inet4Address;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author derek
 */
public class os {
    static String OS = System.getProperty("os.name").toLowerCase();
    public static boolean isWindows() {
        return (OS.indexOf("win") >= 0);
    }

    public static boolean isMac() {
        return (OS.indexOf("mac") >= 0);
    }

    public static boolean isUnix() {
        return (OS.indexOf("nix") >= 0
                || OS.indexOf("nux") >= 0
                || OS.indexOf("aix") > 0);
    }
    
    public static void execute(String cmd) {
        if (isWindows()) {
            run(cmd);
        }
        else{
            bash(cmd);
        }
    }
    
    static void bash(String cmd){
        save("/tmp/.qlc+.sh", "echo BASH: " +cmd+"\n"+cmd);
        run("chmod 777 /tmp/.qlc+.sh");
        run("/tmp/.qlc+.sh");
        new File("/tmp/.qlc+.sh").delete();
    }
    
    static void run(String cmd){
        System.out.println(cmd);
        String s;
        Process p;
        String output = "";
        try {
            p = Runtime.getRuntime().exec(cmd);
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while ((s = br.readLine()) != null) {
                System.out.println("line: " + s.replace("Copyright (c) Heikki Junnila (hjunnila@users.sf.net)", "").replace("Copyright (c) Massimo Callegari (massimocallegari@yahoo.it)", ""));
                output += s + "\n";
            }
            p.waitFor();
            System.out.println("exit: " + p.exitValue());
            output = output.replace("This program is licensed under the terms of the Apache 2.0 license.\n", "").replace("Copyright (c) Heikki Junnila (hjunnila@users.sf.net)\n", "").replace("Copyright (c) Massimo Callegari (massimocallegari@yahoo.it)\n", "").replace("Copyright (c) Heikki Junnila (hjunnila@users.sf.net).\n", "").replace("Copyright (c) Massimo Callegari (massimocallegari@yahoo.it).\n", "");
            p.destroy();
            /*if (p.exitValue() != 0 && p.exitValue() !=1) {
                JOptionPane.showMessageDialog(null, "Exit: " + p.exitValue() + "\n\nDetails:\n" + output, "Error", JOptionPane.ERROR_MESSAGE);
            }*/
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, output, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public static String[] getOutput(String cmd){
        execute(cmd +" >> output");
        String[] output = read("output");
        new File("output").delete();
        return output;
    }
    
    public static void launch(String profile){
        if (isWindows()) {
            execute("c:/QLC+/qlcplus.exe -w --open \""+profile+"\"");
        }
        if (isUnix()) {
            execute("qlcplus -w --open \""+profile+"\"");
        }
    }
    
    
    public static void save(String name, String str){   
        try {
            FileWriter myWriter = new FileWriter(name);
            myWriter.write(str);
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static String[] read(String file){
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(file));
            LinkedList<String> line = new LinkedList<String>();
            while (br.ready()) {
                line.add(br.readLine());
            }
            return line.toArray(new String[0]);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(os.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (IOException ex) {
            Logger.getLogger(os.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public static String showFileDialog(Component frame){
        if (os.isUnix() && new File("/bin/zenity").exists()) {
            try {
                return os.getOutput("zenity --file-selection --title=\"Select a File\"")[1];
            } catch (Exception e) {
                return null;
            }
        }
        else{
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileHidingEnabled(false);
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            if (fileChooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
                return fileChooser.getSelectedFile().toString();
            } else {
                return null;
            }
        }
    }
    
    public static String showSaveDialog(Component frame){
        if (os.isUnix() && new File("/bin/zenity").exists()) {
            try {
                return os.getOutput("zenity --file-selection --save --title=\"Save as...\"")[1];
            } catch (Exception e) {
                return null;
            }
        }
        else{
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileHidingEnabled(false);
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            if (fileChooser.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
                return fileChooser.getSelectedFile().toString();
            } else {
                return null;
            }
        }
    }
    
    public static String showDirectoryDialog(Component frame){
        if (os.isUnix() && new File("/bin/zenity").exists()) {
            try {
                return os.getOutput("zenity --file-selection --directory --title=\"Select a Directory\"")[1];
            } catch (Exception e) {
                return null;
            }
        }
        else{
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileHidingEnabled(false);
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            if (fileChooser.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
                return fileChooser.getSelectedFile().toString();
            } else {
                return null;
            }
        }
    }
    
    public static void checkFs(){
        String userDir = System.getProperty("user.home")+"/";
        if (os.isUnix()) {
            userDir+=".qlcplus/";
        }
        if (os.isWindows()) {
            userDir+="QLC+/";
        }
        String[] folders= {"","Fixtures","InputProfiles","MidiTemplates","ModifiersTemplates","RGBScripts"};
        int l = folders.length;
        for (int i = 0; i < l; i++) {
            File dir;
            if (os.isWindows()) {
                dir = new File(userDir + "/" + folders[i]);
            }
            else{
                dir = new File(userDir + "/" + folders[i].toLowerCase());
            }
            if (!dir.exists()) {
                dir.mkdir();
                System.out.println("Fixing "+folders[i]);
            }
        }
    }
    
    public static String getIp(){
        String ip = null;
        if (os.isUnix()) {
            ip = os.getOutput("hostname -I")[1];
            if (ip.length() == 0) {
                try {
                    ip = Inet4Address.getLocalHost().getHostAddress();
                } catch (UnknownHostException ex) {
                    Logger.getLogger(os.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            try {
                ip = Inet4Address.getLocalHost().getHostAddress();
            } catch (UnknownHostException ex) {
                Logger.getLogger(os.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return ip;
    }
    public static void open(String file){
        if (isUnix()){
            execute("xdg-open '"+file+"'");
        }
        if (isMac()){
            execute("open '"+file+"'");
        }
        if (isWindows()){
            execute("\""+file+"\"");
        }
    }
    
    public static boolean isPortOpen(int port) {
        try ( Socket ignored = new Socket("localhost", port)) {
            return false;
        } catch (IOException ignored) {
            return true;
        }
    }
    
    public static boolean isRuning(){
        return !isPortOpen(9999);
    }
    
    public static void deleteDirectory(File file) {
        try {
            if (file.isDirectory()) {
                File[] entries = file.listFiles();
                if (entries != null) {
                    for (File entry : entries) {
                        deleteDirectory(entry);
                    }
                }
            }
            if (!file.delete()) {
                try {
                    throw new IOException("Failed to delete " + file);
                } catch (IOException ex) {
                    Logger.getLogger(os.class.getName()).log(Level.SEVERE, null, ex);
                }
            }  
        } catch (Exception e) {
        }
    }
    
    public static void copy(File source, File dest) throws IOException {
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(source);
            os = new FileOutputStream(dest);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        } finally {
            is.close();
            os.close();
        }
    }

    public static String getMD5(File f){
        try {
            return checksum(MessageDigest.getInstance("MD5"),f);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(os.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (IOException ex) {
            Logger.getLogger(os.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    private static String checksum(MessageDigest digest,File file) throws IOException {
        FileInputStream fis = new FileInputStream(file);
        byte[] byteArray = new byte[1024];
        int bytesCount = 0;
        while ((bytesCount = fis.read(byteArray)) != -1) {
            digest.update(byteArray, 0, bytesCount);
        };
        fis.close();
        byte[] bytes = digest.digest();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            sb.append(Integer
                    .toString((bytes[i] & 0xff) + 0x100, 16)
                    .substring(1));
        }
        System.out.println(sb.toString());
        return sb.toString();
    }
    
    public static void saveUrl(final String filename, final String urlString)throws MalformedURLException, IOException {
        BufferedInputStream in = null;
        FileOutputStream fout = null;
        try {
            in = new BufferedInputStream(new URL(urlString).openStream());
            fout = new FileOutputStream(filename);
            final byte data[] = new byte[1024];
            int count;
            while ((count = in.read(data, 0, 1024)) != -1) {
                fout.write(data, 0, count);
            }
        } finally {
            if (in != null) {
                in.close();
            }
            if (fout != null) {
                fout.close();
            }
        }
    }
    
    public static String MD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }
    
    public static boolean isLiscense(String name, String key){
        for (int i = 0; i < 100; i++) {
            name=MD5(name);
        }
        return name.equals(key);
    }
}
