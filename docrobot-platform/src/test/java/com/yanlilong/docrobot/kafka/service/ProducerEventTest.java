package com.yanlilong.docrobot.kafka.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yanlilong.docrobot.kafka.model.NodeEvent;
import com.yanlilong.docrobot.kafka.model.NodePermissions;
import com.yanlilong.docrobot.kafka.transform.NodeRefToNodeEvent;
import com.yanlilong.docrobot.kafka.transform.NodeRefToNodePermissions;
import org.alfresco.service.cmr.repository.ContentService;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.security.PermissionService;
import org.alfresco.service.cmr.site.SiteService;
import org.alfresco.service.cmr.tagging.TaggingService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

/**
 * Test ObjectMapping for NodeEvent
 */
public class ProducerEventTest {
    private ContentService contentService;
    private NodeService nodeService;
    private SiteService siteService;
    private TaggingService taggingService;
    private NodeRef nodeRef;
    private NodeRefToNodePermissions nodePermissionsTransformer;
    NodePermissions nodePermissions;



    @Before
    public void init() {
        contentService = Mockito.mock(ContentService.class);
        nodeService = Mockito.mock(NodeService.class);
        siteService = Mockito.mock(SiteService.class);
        taggingService = Mockito.mock(TaggingService.class);
        nodeRef = new NodeRef("workspace://SpacesStore/UUID-1");
        PermissionService permissionService = Mockito.mock(PermissionService.class);

        nodePermissionsTransformer = new NodeRefToNodePermissions(nodeService, permissionService);
        nodePermissions=Mockito.mock(NodePermissions.class);
        //when(nodePermissionsTransformer.transform(nodeRef)).thenReturn(nodePermissions);
    }

    @Test
    public void nodeEventTest() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        NodeRefToNodeEvent nodeTransformer = new NodeRefToNodeEvent(contentService, nodeService, siteService, taggingService);
        NodeEvent e = nodeTransformer.transform(nodeRef);
        e.setNodeRef(nodeRef.getId());
        e.setEventType((NodeEvent.EventType.UPDATE));

        e.setPermissions(nodePermissionsTransformer.transform(nodeRef));
        String message = mapper.writeValueAsString(e);
        System.out.println(message);
        NodeEvent event = mapper.readValue(message, NodeEvent.class);
        assertNotNull(event);
        assertEquals(nodeRef.getId(), event.getNodeRef());
       // assertEquals(nodePermissions,event.getPermissions());
    }

}