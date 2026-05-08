import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.unitime.timetable.gwt.shared.EventInterface.ResourceType.SUBJECT;


import org.hibernate.Session;
import org.hibernate.query.Query;
import org.mockito.MockedStatic;
import org.junit.jupiter.api.Test;

import org.unitime.timetable.events.ResourceLookupBackend;
import org.unitime.timetable.gwt.command.client.GwtRpcException;

import org.unitime.timetable.model.SubjectArea;
import org.unitime.timetable.model.dao.EventDAO;
import org.unitime.timetable.model.dao.SessionDAO;


import java.util.Collections;

class ResourceLookupBackendTest {

    private ResourceLookupBackend backend;
    @Test
    void testThrowsException() {

        Session hibSession = mock(Session.class);
        Query<SubjectArea> query = mock(Query.class);
        org.unitime.timetable.model.Session academicSession = mock(org.unitime.timetable.model.Session.class);

        when(academicSession.getUniqueId()).thenReturn(1L);
        when(hibSession.createQuery(anyString(), eq(SubjectArea.class))).thenReturn(query);
        when(query.setParameter(anyString(), any())).thenReturn(query);
        when(query.list()).thenReturn(Collections.emptyList());

        try (MockedStatic<EventDAO> eventDaoMock = mockStatic(EventDAO.class);
             MockedStatic<SessionDAO> sessionDaoMock = mockStatic(SessionDAO.class))
        {
            EventDAO eventDao = mock(EventDAO.class);
            SessionDAO sessionDao = mock(SessionDAO.class);

            eventDaoMock.when(EventDAO::getInstance).thenReturn(eventDao);
            sessionDaoMock.when(SessionDAO::getInstance).thenReturn(sessionDao);

            when(eventDao.getSession()).thenReturn(hibSession);
            when(sessionDao.get(anyLong())).thenReturn(academicSession);

            ResourceLookupBackend backend = new ResourceLookupBackend();
            GwtRpcException ex = assertThrows(GwtRpcException.class, () -> backend.findResource(1L, SUBJECT, "invalid subject"));

            assertTrue(ex.getMessage().toLowerCase().contains("subject not found"));
        }
    }
}