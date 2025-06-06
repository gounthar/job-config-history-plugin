package hudson.plugins.jobConfigHistory;

import hudson.model.ItemGroup;
import jenkins.model.Jenkins;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 *
 * Tests for the ConfigInfo.
 *
 * @author Mirko Friedenhagen
 */
class ConfigInfoTest {

    private static final String DATE = "2012-11-21_11-29-12";

    private final HistoryDescr historyDescr = new HistoryDescr(
            "Firstname Lastname", "userId", "operation", DATE, null, null);

    @BeforeEach
    void setUp() {
        ItemGroup<?> itemGroupMock = mock(ItemGroup.class);
        when(itemGroupMock.getFullName()).thenReturn("does not matter parent");
        Jenkins jenkinsMock = mock(Jenkins.class);
        when(jenkinsMock.getFullName()).thenReturn("Job1");
    }

    /**
     * Test of create method, of class ConfigInfo.
     */
    @Test
    void configInfoShouldBeInitializedCorrectly() {
        ConfigInfo sut = ConfigInfo.create("jobName", true, historyDescr,
                false);
        assertNotNull(sut);
        assertFalse(sut.getIsJob());
    }

    @Test
    void toStringShouldContainOperationString() {
        ConfigInfo sut = ConfigInfo.create("jobName", true, historyDescr,
                false);
        String result = sut.toString();
        assertThat(result, startsWith("operation on "));
    }

    @Test
    void dateShouldBeParsedCorrectly() {
        ConfigInfo sut = ConfigInfo.create("jobName", true, historyDescr,
                false);
        Date expResult = new GregorianCalendar(2012, Calendar.NOVEMBER, 21, 11, 29, 12).getTime();
        Date result = sut.parsedDate();
        assertEquals(expResult, result);
    }
}
