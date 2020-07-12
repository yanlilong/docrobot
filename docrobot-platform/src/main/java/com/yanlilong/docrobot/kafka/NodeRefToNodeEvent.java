package com.yanlilong.docrobot.kafka;

import org.alfresco.service.cmr.repository.ContentService;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.site.SiteService;
import org.alfresco.service.cmr.tagging.TaggingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NodeRefToNodeEvent {
    private static final Logger LOGGER = LoggerFactory.getLogger(NodeRefToNodeEvent.class);
    private ContentService contentService;
    private NodeService nodeService;
    private SiteService siteService;
    private TaggingService taggingService;
}
