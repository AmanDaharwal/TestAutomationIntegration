package runner;

import io.cucumber.core.cli.Main;

public class TestRunner {

    public TestRunner(){

        run();
    }

    private void run() {

        String[] array = { "--tags", "@indigo",
                            "--plugin", "pretty",
                            "--plugin", "html:target/cucumber.html",
                            "--threads", "1",
                            "--glue", "com.steps",
                            "src/test/resources/features"};
        System.out.println("Inside Run -- ");
        byte status = Main.run(array);
    }

    public static void main(String[] args){

        System.out.println("Running Main Method  -> ");
        new TestRunner();
    }
}
