/*
 *  Copyright (C) 2011 Glenn Rune Strandbråten 
 * 
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 * 
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 * 
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package myrmidia.File;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import javax.swing.JOptionPane;

/**
 * Class used to create a lock file when the application starts, and destroy 
 * that file upon application shutdown. The file will prevent two instances of
 * the software to run simultaneously. 
 * 
 * @author Glenn Rune Strandbråten
 * @version 1.0
 */
public class LockFile{
    private File lockFile = null;
    private FileChannel channel;
    private FileLock lock;
    
    private LockFile(){}
   
    /**
     * Method used to aquire the singleton instance of the LockFile.
     * @return the LockFile singleton object reference.
     */
    public static LockFile getInstance(){
        return LockFileHolder.INSTANCE;
    }
    private static class LockFileHolder{
        private static final LockFile INSTANCE = new LockFile();
    }

    /**
     * Method used to create the file which prevents other instances from
     * running. This method will also force an application shutdown if another
     * instance is running.
     */
    public void lockFile(){
        try {
            String path = System.getProperty("user.home")+"/"+"Myrmidia.lock";
            lockFile = new File(path);
            if(lockFile.exists())
                lockFile.delete();
            channel = new RandomAccessFile(lockFile,"rw").getChannel();
            lock = channel.tryLock();
            if(lock == null) {
                channel.close();
                JOptionPane.showMessageDialog(null, "<html>Another instance of "
                        + "this application is already running on your machine."
                        + "<br>Only one instance is allowed to run at any given"
                        + " time.<br>The application will now close.", 
                        "Error 06: Another instance",
                        JOptionPane.ERROR_MESSAGE);
                System.exit(-1);
            }
            ShutdownHook shutdownHook = new ShutdownHook();
            Runtime.getRuntime().addShutdownHook(shutdownHook);
        }
        catch (IOException ex){}
    }
    
    /**
     * Method used to delete the file which prevents other instances from
     * launching.
     */
    public void unlockFile() {
        try {
            if(lock != null){
                lock.release();
                channel.close();
                lockFile.delete();
                lockFile = null;
            }
        }
        catch(IOException ex){}
    }
    
    /**
     * A shutdown hook which is activated when the application recieves any
     * shutdown command. The thread will delete the lock file in order to 
     * allow future application launches to run.
     */
    public class ShutdownHook extends Thread {
        @Override
        public void run() {
            unlockFile();
        }
    }
}
