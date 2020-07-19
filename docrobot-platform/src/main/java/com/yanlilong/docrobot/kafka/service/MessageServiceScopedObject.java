package com.yanlilong.docrobot.kafka.service;

import org.alfresco.repo.jscript.BaseScopableProcessorExtension;
import org.alfresco.repo.jscript.ScriptNode;
//BaseScopableProcessorExtension: Is required in order for us to add new root scoped objects to the javascript APIs.
// Again, this is invaluable for administration of Alfresco implementations.
//If you open up the ability to create root scoped objects then I would hope you would also allow access to ScriptNode.
public class MessageServiceScopedObject  extends BaseScopableProcessorExtension {
    private MessageService messageService;

    public MessageServiceScopedObject(MessageService messageService) {
        this.messageService = messageService;
    }

    public void ping(ScriptNode scriptNode) {
        messageService.ping(scriptNode.getNodeRef());
    }
}
