package com.yanlilong.docrobot.kafka.transform;

import com.yanlilong.docrobot.kafka.model.NodeEvent;
import org.alfresco.model.ContentModel;
import org.alfresco.service.cmr.repository.*;
import org.alfresco.service.cmr.site.SiteInfo;
import org.alfresco.service.cmr.site.SiteService;
import org.alfresco.service.cmr.tagging.TaggingService;
import org.alfresco.service.namespace.QName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.alfresco.model.ContentModel.*;


public class NodeRefToNodeEvent {
    private static final Logger LOGGER = LoggerFactory.getLogger(NodeRefToNodeEvent.class);
    private final ContentService contentService;
    private final NodeService nodeService;
    private final SiteService siteService;
    private final TaggingService taggingService;


    public NodeRefToNodeEvent(ContentService contentService, NodeService nodeService, SiteService siteService, TaggingService taggingService) {
        this.contentService = contentService;
        this.nodeService = nodeService;
        this.siteService = siteService;
        this.taggingService = taggingService;
    }

    public NodeEvent transform(NodeRef nodeRef) {
        String id;
        if (nodeService.getPrimaryParent(nodeRef) != null && nodeService.getPrimaryParent(nodeRef).getParentRef() != null) {
            id = nodeService.getPrimaryParent(nodeRef).getParentRef().getId();
        } else {
            id = "";
        }
        String contentType;
        if (nodeService.getType(nodeRef) != null) {
            contentType = nodeService.getType(nodeRef).toPrefixString();
        } else {
            contentType = "";
        }
        Map<QName, Serializable> props = nodeService.getProperties(nodeRef);
        NodeEvent nodeEvent = NodeEvent.builder().nodeRef(nodeRef.getId())
                .created((Date) props.get(PROP_CREATED))
                .creator((String) props.get(PROP_CREATOR))
                .modified((Date) props.get(PROP_MODIFIED))
                .modifier((String) props.get(PROP_MODIFIER))
                .path(nodeService.getPath(nodeRef) != null ? nodeService.getPath(nodeRef).toString() : "")
                .parent(id)
                .contentType(contentType).build();
        SiteInfo siteInfo = siteService.getSite(nodeRef);
        if (siteInfo != null) {
            nodeEvent.setSiteId(siteInfo.getShortName());
        }
        List<String> tags = taggingService.getTags(nodeRef);
        if (props.get(PROP_CONTENT) != null) {
            ContentReader reader = null;
            try {
                reader = contentService.getReader(nodeRef, PROP_CONTENT);
            } catch (Exception e) {
                LOGGER.error("Error reading content:" + e.getMessage());

            }
            if (reader != null) {
                nodeEvent.setMimetype(reader.getMimetype());
                nodeEvent.setSize(reader.getContentData().getSize());
            }
        }
        return nodeEvent;

    }
}
