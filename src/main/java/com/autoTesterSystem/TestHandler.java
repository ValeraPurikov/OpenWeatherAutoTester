package com.autoTesterSystem;

import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.junit.platform.launcher.listeners.TestExecutionSummary;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;


import static org.junit.platform.engine.discovery.DiscoverySelectors.selectClass;

public class TestHandler {
    private JList list; //The Assigned Tests UI List
    private JButton button; //Start/Stop button

    public TestHandler(JList l, JButton b) {
        list = l; //The Assigned Tests UI List
        button = b; //Start/Stop button
    }

    public void doAssignedTests() {
        //This method will launch each assigned test
        try {
            int successCount = 0;
            int failureCount = 0;
            ArrayList<String> failures = new ArrayList<>();
            //Loop that will iterate each assigned test
            for (int i = 0; i < list.getModel().getSize(); i++) {
                String originalString = list.getModel().getElementAt(i).toString();
                System.out.println("Starting test: " + originalString);
                TestExecutionSummary summary = startTest(originalString.replaceAll("\\s", ""));
                if (summary.getFailures().isEmpty()) {
                    successCount++;
                } else {
                    failureCount++;
                    failures.add(originalString);
                    for (TestExecutionSummary.Failure f : summary.getFailures()) {
                        //Generate explanation for each parameter-set failed within a test
                        System.out.print("Failed Test: \"" + originalString + "\". Reason - " +
                                f.getException().getMessage());
                    }
                }
                System.out.println();
            }
            //Print the output digest
            System.out.println("\nTests Passed: " + successCount + "/" + list.getModel().getSize() + "\n" +
                    "Tests Failed: " + failureCount + "/" + list.getModel().getSize() + "\n");
            if (!failures.isEmpty()) {
                System.out.print("Tests Failed: ");
                for (int i = 0; i < failures.size(); i++) {
                    System.out.print(failures.get(i));
                    if (i != failures.size() - 1)
                        System.out.print(", ");
                }
                System.out.println();
            }


        } catch (IllegalArgumentException | ClassNotFoundException | InstantiationException | InvocationTargetException
                | NoSuchMethodException | IllegalAccessException e) {
            e.printStackTrace();
        }
        button.setText("Start Tests");
    }

    public static Class newInstance(String tempName)
            throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException,
            IllegalArgumentException, InvocationTargetException {
        //This method returns a Class Object of specified test, and in the process creates a new instance of the test,
        // using the current .class file
        //With this, it is possible to edit tests logic, then launch it without re-launching the application, but instead
        // only re-compiling it.
        URLClassLoader tmp =
                new URLClassLoader(new URL[]{getClassPath()}) {
                    public Class loadClass(String name) throws ClassNotFoundException {
                        if ((name).equals(tempName))
                            return findClass(name);
                        return super.loadClass(name);
                    }
                };
        Class c = tmp.loadClass(tempName);

        return c.getDeclaredConstructor().newInstance().getClass();
    }

    private static URL getClassPath() {
        String resName =
                TestHandler.class.getName().replace('.', '/') + ".class";
        String loc =
                TestHandler.class.getClassLoader().getResource(resName)
                        .toExternalForm();
        try {
            return new URL(loc.substring(0, loc.length() - resName.length()));
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    private TestExecutionSummary startTest(String tempString)
            throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException,
            IllegalAccessException {
        Class clsInstance = newInstance("com.JUnitTests." + tempString); // This is the class of assigned test
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
        return listener.getSummary();
    }
}
