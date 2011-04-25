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
package myrmidia.UI.Resources;

import java.awt.BorderLayout;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
/**
 *
 * @author Glenn Rune Strandbråten
 */
public class ProgressDialog extends JDialog{

    private JProgressBar progressBar;
    private Thread progressThread;
    private ProgressDialog(JFrame parentFrame, String title, boolean modal){
        super(parentFrame, title, modal);
    }
    public static ProgressDialog getProgressDialog(JFrame parent,int max){
        final ProgressDialog dialog = new ProgressDialog(parent, "Progress", true);
        dialog.progressBar = new JProgressBar(0,max);

        dialog.getContentPane().setLayout(new BorderLayout());
        dialog.getContentPane().add(BorderLayout.CENTER, dialog.progressBar);
        dialog.getContentPane().add(BorderLayout.NORTH, new JLabel("Please wait while data is retrieved from the database"));
        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        dialog.setSize(330, 65);
        dialog.setLocationRelativeTo(parent);
        dialog.setResizable(false);

        dialog.progressThread = new Thread(new Runnable(){
            public void run(){
                System.out.println("Started dialog thread");
                dialog.setVisible(true);
                System.out.println("Ended dialog thread");
            }
        });
        dialog.progressThread.setName("Progress thread");
        dialog.progressThread.start();
        return dialog;
    }

    public void setValue(int value){
        progressBar.setValue(value);
        pack();
    }

    public void incrementValue(){
        progressBar.setValue(progressBar.getValue()+1);
        pack();
    }

    public void start(){
        progressThread.start();
    }
}
