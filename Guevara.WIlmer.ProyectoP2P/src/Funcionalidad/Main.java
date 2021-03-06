/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Funcionalidad;

/**
 *
 * @author Wilmer
 */
import java.awt.Font;
import java.awt.Frame;
import java.awt.TextArea;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Main extends Frame implements Runnable {

    // text output of all connections
    private final TextArea textArea;
    // broadcast and receive of UDP; used for TCP connection(s) to peer(s)
    private final Nodo broadcasts;
    // list of all sockets for TCP output
    private final ArrayList<Socket> sockets;
    // storage for text data
    private StringBuilder lines;
    // continue running application?
    private boolean run = true;

    public Main() {
        // create field objects
        sockets = new ArrayList<>();
        lines = new StringBuilder();
        textArea = new TextArea(20, 80);
        // set focusable to false to ensure keys are captured by frame
        textArea.setFocusable(false);
        // monospace ftw
        textArea.setFont(Font.decode("monospaced"));
        // the only gui object is the text area
        add(textArea);
        pack();

        // start socket server to accept incoming connections
        new Thread(this).start();

        // instantiate and assign window listener and key listener to frame
        FrameListener frameListener = new FrameListener(this);
        addWindowListener(frameListener);
        addKeyListener(frameListener);

        // late initialize of UDP broadcast and receive, to ensure needed
        // objects are instantiated
        broadcasts = new Nodo(this);

        setVisible(true);
    }

    // global quit method shuts down everything and exits
    public void quit() {
        run = false;
        broadcasts.quit();
        System.exit(0);
    }

    // method called by key listener
    public void keyTyped(KeyEvent ke) {
        int i;
        synchronized (sockets) {
            // iterate through all sockets, and flush character through
            for (i = 0; i < sockets.size(); i++) {
                try {
                    Socket s = sockets.get(i);
                    PrintWriter pw = new PrintWriter(s.getOutputStream());
                    pw.print(String.valueOf(ke.getKeyChar()));
                    pw.flush();
                } catch (IOException ex) {
                    // remove socket, continue to any next if exception occurs
                    // (socket closed)
                    ex.printStackTrace();
                    sockets.remove(i);
                    continue;
                }
            }
        }
    }

    // method called by per-connection thread defined in socketStream
    public void putChar(int ch) {
        // check for backspace and space for delete,
        // otherwise put character into buffer,
        // and show updated buffer
        if (ch == 8 && lines.length() > 0) {
            lines.delete(lines.length() - 1, lines.length());
        } else {
            lines.append((char) ch);
        }
        synchronized (textArea) {
            textArea.setText(lines.toString() + '.');
        }
    }

    // method called by UDP listener
    // exits if connection fails
    void newAddress(InetAddress address) {
        synchronized (sockets) {
//            // check if already connected to address, and exit if true

            for (Socket addr : sockets) {

                if (addr.getInetAddress().getHostAddress()
                        .equals(address.getHostAddress())) {
                    return;
                }
            }
            // create a new socket and add it to transmission pool
            Socket s;

            try {
                s = new Socket(address.getHostAddress(), VariablesGlobals.TCPPORT);
            } catch (IOException ex) {
                return;
            }
            sockets.add(s);
        }
    }

    // called by socket server thread
    // defines a thread for each connection,
    // which calls putChar for every received character
    // exits thread if error occurs (socket closed)
    private void socketStream(final Socket s) {
        final InputStream is;
        try {
            is = s.getInputStream();
        } catch (IOException ex) {
            return;
        }
        final InputStreamReader isr = new InputStreamReader(is);
        final BufferedReader br = new BufferedReader(isr);
        new Thread(new Runnable() {
            public void run() {
                while (run && s.isConnected()) {
                    try {
                        if (br.ready()) {
                            putChar(br.read());
                        }
                    } catch (IOException ex) {
                        return;
                    }
                }
            }
        }).start();
    }

    // socket server accepts incoming connection,
    // and creates a thread to pass characters to the screen
    public void run() {
        try {
            ServerSocket ss = new ServerSocket(VariablesGlobals.TCPPORT);
            while (ss.isBound() && run) {
                socketStream(ss.accept());
            }
            quit();
        } catch (IOException ex) {
            ex.printStackTrace();
            quit();
        }
    }

    // application entry
    public static void main(String[] args) {
        new Main();
    }
}
