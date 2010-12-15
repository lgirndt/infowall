package infowall.web.controller;

import com.google.common.collect.Lists;
import infowall.domain.model.Dashboard;
import infowall.domain.process.DashboardProcess;
import infowall.testing.Mocks;
import infowall.web.spring.FlashMessage;
import infowall.web.spring.FlashMessageImpl;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static org.springframework.test.web.ModelAndViewAssert.assertModelAttributeValue;
import static org.springframework.test.web.ModelAndViewAssert.assertViewName;

/**
 *
 */
public class DashboardControllerTest {
    private Mocks mocks;
    private DashboardProcess process;
    private DashboardController controller;

    @Before
    public void setUp() throws Exception {
        mocks = new Mocks();
        process = mocks.createMock(DashboardProcess.class);
        FlashMessage flash = mocks.createMock(FlashMessageImpl.class);
        controller = new DashboardController(process, flash);
    }

    @Test
    public void listAllDashboards(){

        List<Dashboard> actual = Lists.newArrayList();

        EasyMock.expect(process.listAllDashboards()).andReturn(actual);

        mocks.replayAll();
        ModelAndView mav = controller.listDashboards();
        mocks.verifyAll();

        assertViewName(mav,"dashboard/list");
        assertModelAttributeValue(mav,"dashboards",actual);
    }
}
