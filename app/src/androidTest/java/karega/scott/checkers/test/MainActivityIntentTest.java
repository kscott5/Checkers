package karega.scott.checkers.test;
import karega.scott.checkers.MainActivity;

import androidx.test.filters.MediumTest;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import androidx.test.espresso.intent.Intents;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import org.junit.Rule;

@MediumTest
public class MainActivityIntentTest {
	@Rule
	ActivityScenarioRule rule = new ActivityScenarioRule<>(MainActivity.class);

	@Before
	public void before() {
		Intents.init();
	}

	@After
	public void after() {
		Intents.release();
	}

	@Test
	public void newGamePassNPlay() {
	}

	@Test
	public void newGameDevicePlay() {
	}
}
