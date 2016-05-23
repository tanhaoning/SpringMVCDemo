package com.spring.other;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

public class IDEAKeyGen extends JDialog {
    private JPanel contentPane = new JPanel();
    private JTextField userName = new JTextField();
    private JFormattedTextField version = new JFormattedTextField();
    private JTextField licenseKey = new JTextField();
    private KeyGen keyGen = new KeyGen();

    public IDEAKeyGen() {
        this.userName.setPreferredSize(new Dimension(150, 30));
        this.version.setPreferredSize(new Dimension(50, 30));
        this.licenseKey.setPreferredSize(new Dimension(340, 30));
        this.version.setText("14");

        this.contentPane.add(new JLabel("User Name"));
        this.contentPane.add(this.userName);
        this.contentPane.add(new JLabel("Version"));
        this.contentPane.add(this.version);
        this.contentPane.add(new JLabel("License Key"));
        this.contentPane.add(this.licenseKey);
        this.contentPane.add(new JLabel("Power By fireflyc"));

        setContentPane(this.contentPane);
        setModal(true);
        KeyListener keyListener = new KeyListener() {
            public void keyTyped(KeyEvent e) {
            }

            public void keyPressed(KeyEvent e) {
            }

            public void keyReleased(KeyEvent e) {
                byte bVersion = 14;
                try {
                    bVersion = Byte.parseByte(IDEAKeyGen.this.version.getText());
                } catch (Exception ex) {
                }
                IDEAKeyGen.this.version.setText(String.valueOf(bVersion));
                String key = IDEAKeyGen.this.keyGen.key(IDEAKeyGen.this.userName.getText(), bVersion);
                IDEAKeyGen.this.licenseKey.setText(key);
            }
        };
        this.userName.addKeyListener(keyListener);
        this.version.addKeyListener(keyListener);

        setDefaultCloseOperation(0);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                IDEAKeyGen.this.onClose();
            }
        });
        this.contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                IDEAKeyGen.this.onClose();
            }
        }
                , KeyStroke.getKeyStroke(27, 0), 1);
    }

    private void onClose() {
        dispose();
    }

    public static void main(String[] args) {
        IDEAKeyGen dialog = new IDEAKeyGen();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}