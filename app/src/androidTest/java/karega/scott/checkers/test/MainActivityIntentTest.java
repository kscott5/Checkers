package karega.scott.checkers.test;
import karega.scott.checkers.MainActivity;

import androidx.test.filters.MediumTest;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import org.junit.Rule;

@MediumTest
public class MainActivityIntentTest {
	@Rule
	ActivityScenarioRule rule = new ActivityScenarioRule<>(MainActivity.class);
}
