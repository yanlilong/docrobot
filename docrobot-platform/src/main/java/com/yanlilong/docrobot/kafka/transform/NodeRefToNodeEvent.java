package com.yanlilong.docrobot.kafka.transform;

import com.yanlilong.docrobot.kafka.model.NodeEvent;
import org.alfresco.model.ContentModel;
import org.alfresco.service.cmr.repository.ContentReader;
import org.alfresco.service.cmr.repository.ContentService;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
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

    public NodeEvent transform(NodeRef nodeRef){
        Map<QName, Serializable> props=nodeService.getProperties(nodeRef);
        NodeEvent nodeEvent=new NodeEvent.NodeEventBuilder().nodeRef(nodeRef.getId())
                .creator((String)props.get(PROP_CREATOR))
                .created((Date)props.get(PROP_CREATED))
                .modifier((String)props.get(PROP_MODIFIED))
                .modified((Date) props.get(PROP_MODIFIED))
                .path(nodeService.getPath(nodeRef).toString())
                .parent(nodeService.getPrimaryParent(nodeRef).getParentRef().getId())
                .contentType(nodeService.getType(nodeRef).toPrefixString())
                .build();

        SiteInfo siteInfo=siteService.getSite(nodeRef);
        if(siteInfo!=null){
            nodeEvent.setSiteId(siteInfo.getShortName());
        }
        List<String> tags=taggingService.getTags(nodeRef);
        if(props.get(PROP_CONTENT)!=null){
            ContentReader reader=null;
            try{
                reader=contentService.getReader(nodeRef,PROP_CONTENT);
            }catch(Exception e){
                LOGGER.error("Error reading content:"+e.getMessage());

            }
            if(reader!=null){
                nodeEvent.setMimetype(reader.getMimetype());
                nodeEvent.setSize(reader.getContentData().getSize());
            }
        }
        return nodeEvent;

    }
}
