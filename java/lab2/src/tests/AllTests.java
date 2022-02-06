package tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	CommandTest.class,
	WordCheckerTest.class,
	CommandParserTest.class,
	WorkersTest.class,
	MainTest.class
})
public class AllTests {
	
}
