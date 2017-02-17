package org.nnstu.test;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.nnstu.launcher.MainLauncher;
import org.nnstu.test.stubs.ServerStub;
import org.nnstu.test.stubs.single.SingleServerStub;

import static org.junit.Assert.assertTrue;

public class MainLauncherUnitTest {
    private MainLauncher emptyInstance = null;
    private MainLauncher singleInstance = null;
    private MainLauncher multiInstance = null;

    @Before
    public void createInstanceBeforeTest() {
        emptyInstance = new MainLauncher("org.nnstu.test.stubs.empty");
        singleInstance = new MainLauncher("org.nnstu.test.stubs.single");
        multiInstance = new MainLauncher("org.nnstu.test.stubs");
    }

    @Test
    public void emptyLaunchTest() {
        String launchResult = emptyInstance.simultaneousLaunch();
        assertTrue(
                "Check if no instances were started.",
                StringUtils.containsIgnoreCase(
                        launchResult,
                        "No servers found, exiting."
                )
        );
    }

    @Test
    public void singleLaunchTest() {
        String launchResult = singleInstance.simultaneousLaunch();
        assertTrue(
                "Check if the single instance was started.",
                StringUtils.containsIgnoreCase(
                        launchResult,
                        " on port " + new SingleServerStub().getServerPort()
                )
        );
    }

    @Test
    public void multiLaunchTest() {
        String launchResult = multiInstance.simultaneousLaunch();
        System.out.println(launchResult);
        assertTrue(
                "Check if the SingleServerStub instance was started.",
                StringUtils.containsIgnoreCase(
                        launchResult,
                        " on port " + new SingleServerStub().getServerPort()
                )
        );
        assertTrue(
                "Check if the ServerStub instance was started.",
                StringUtils.containsIgnoreCase(
                        launchResult,
                        " on port " + new ServerStub().getServerPort()
                )
        );
    }
}
