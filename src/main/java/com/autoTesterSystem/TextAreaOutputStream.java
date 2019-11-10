package com.autoTesterSystem;

import java.io.IOException;
import java.io.OutputStream;
import javax.swing.JTextArea;
public class TextAreaOutputStream extends OutputStream { // this class makes it so all consoles output is displayed in console

    private final JTextArea textArea; //pointer to console
    private final StringBuilder sb = new StringBuilder();
    private boolean waitForSpace = false;

    public TextAreaOutputStream(final JTextArea textArea) {
        this.textArea = textArea;
    }

    @Override
    public void flush() {

    }

    @Override
    public void close() {

    }

    @Override
    public void write(int b) throws IOException {

        if (b == '\r')
            return;

        if (b == '\n' || (b == ' ' && waitForSpace)) {
            final String text = sb.toString() + "\n";
            textArea.append(text);
            waitForSpace = false;
            sb.setLength(0);
            return;
        }
        sb.append((char) b);
        if (sb.length() >= 90){
            waitForSpace = true;
        }

    }
}
