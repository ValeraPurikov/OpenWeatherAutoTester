package com.autoTesterSystem;

import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.junit.platform.launcher.listeners.TestExecutionSummary;

import javax.sound.midi.SysexMessage;
import javax.swing.*;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;


import static org.junit.platform.engine.discovery.DiscoverySelectors.selectClass;

public class TestHandler {
    JList list; //the Assigned Tests UI List
    JButton button; //Start/Stop button
    public TestHandler(JList l, JButton b){
        list = l; //the Assigned Tests UI List
        button = b; //Start/Stop button
    }
    public void doAssignedTests(){
        //This method will launch launch each assigned test
        try {
            int successCount = 0;
            int failureCount = 0;
            ArrayList<String> failures = new ArrayList<>();
            //loop that will iterate each assigned test
            for (int i = 0; i < list.getModel().getSize(); i++){
                String originalString = list.getModel().getElementAt(i).toString();
                String tempString = originalString.replaceAll("\\s","");
                Class clsInstance = newInstance("com.JUnitTests."+tempString); // this is the class of assigned test
                //Prepare Junit 5 Launcher
                LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
                        .selectors(selectClass(clsInstance))
                        .build();
                Launcher launcher = LauncherFactory.create();
                //Listener to receive output later
                SummaryGeneratingListener listener = new SummaryGeneratingListener();
                launcher.registerTestExecutionListeners(listener);
                //Start the test
                launcher.execute(request);
                //Receive output
                TestExecutionSummary summary = listener.getSummary();
                if (summary.getFailures().isEmpty()) {
                    successCount++;
                }else {
                    failureCount++;
                    failures.add(originalString);
                    for (TestExecutionSummary.Failure f : summary.getFailures()) {
                        //Generate explanation for each parameter-set failed within a test
                        System.out.print("Failed Test: \""+ originalString+"\". Reason - ");
                        System.out.println(f.getException().toString().replace("org.opentest4j.AssertionFailedError: ", ""));
                    }
                }
            }
            //Print the output digest
            System.out.println("Tests Passed: "+successCount+"/"+list.getModel().getSize());
            System.out.println("Tests Failed: "+failureCount+"/"+list.getModel().getSize());
            if (!failures.isEmpty()){
                System.out.print("Tests Failed: ");
                for (int i = 0 ; i < failures.size(); i++){
                    System.out.print(failures.get(i));
                    if (i !=  failures.size() - 1)
                        System.out.print(", ");
                }
                System.out.println();
            }


        } catch (IllegalArgumentException | ClassNotFoundException | InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
            e.printStackTrace();
        }
        button.setText("Start Tests");
    }
    public static Class newInstance(String tempName) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException {
        //This method returns a Class Object of specified test, and in the process creates a new instance of the test, using the current .class file
        //With this, it is possible to edit tests logic, then launch it without re-launching the application, but instead only re-compiling it.
        URLClassLoader tmp =
                new URLClassLoader(new URL[] {getClassPath()}) {
                    public Class loadClass(String name) throws ClassNotFoundException {
                        if ((name).equals(tempName))
                            return findClass(name);
                        return super.loadClass(name);
                    }
                };
        Class c  = tmp.loadClass(tempName);

        return c.getDeclaredConstructor().newInstance().getClass();
    }
    private static URL getClassPath() {
        String resName =
                TestHandler.class.getName().replace('.', '/') + ".class";
        String loc =
                TestHandler.class.getClassLoader().getResource(resName)
                        .toExternalForm();
        URL cp;
        try {
            cp = new URL(loc.substring(0, loc.length() - resName.length()));
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        return cp;
    }
}
