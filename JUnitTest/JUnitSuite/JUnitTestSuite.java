package JUnitSuite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
/**
 * This is the JUnit Test Suite for the tests related to risk game build 2
 *
 * @author Arun
 * @version 1.0
 * @since   2018-10-28 
 */ 
import Player.Player_Test;
@RunWith(Suite.class)
@SuiteClasses({Driver.Main_Test.class, Map.Map_Test.class, Player_Test.class})
public class JUnitTestSuite {   
}
 
