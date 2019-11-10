package com.autoTesterSystem;

import java.io.IOException;
import java.io.OutputStream;
import javax.swing.JTextArea;

public class TextAreaOutputStream extends OutputStream { // This class makes it so all consoles output is displayed in console

    private final JTextArea textArea; //Pointer to console
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
        //When System.out.print is called, for each char in the string to be printed, this method is called
        if (b == '\r')
            return;

        //When message ends with \n, it means the message has been completed and we need to add it to UI console
        //Alternatively, this code is executed, when text line is too long and we need to break it when the current
        //char is a space
        if (b == '\n' || (b == ' ' && waitForSpace)) {
            final String text = sb.toString() + "\n";
            textArea.append(text);
            waitForSpace = false;
            sb.setLength(0);
            return;
        }
        //Add the char to the text line
        sb.append((char) b);

        //If line is too long, we must break line when next space char arrives
        if (sb.length() >= 90) {
            waitForSpace = true;
        }

    }
}
