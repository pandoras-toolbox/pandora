package box.pandora.payroll_test_gatling;

import io.gatling.app.Gatling;

public final class Engine {

    private Engine() {
    }

    public static void main(String[] args) {
        // There is also --launcher and --build-tool-version, but we do not know what they are good for.
        Gatling.main(new String[]{
                // A name of a Simulation class to run.
                "--simulation", BasicSimulation.class.getName(),
                // Use this folder as the folder where results are stored.
                "--results-folder", "payroll-load-test/build/reports",
                // A short description of the run to include in the report.
                //"--run-description", "???",
                // Run simulation but does not generate reports. false by default.
                //"--no-reports", "false",
                // Generate the reports for the simulation in this folder.
                //"--reports-only", "???",
        });

    }

}
