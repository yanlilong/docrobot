package com.yanlilong.docrobot.datacapture.behavior;

import org.alfresco.service.namespace.QName;

import static org.alfresco.service.namespace.QName.createQName;


    import static org.alfresco.service.namespace.QName.createQName;

public class DemoConstants {
        private DemoConstants() {
        }

        public static final String NAMESPACE_ACME_URL = "http://www.acme.org/model/content/1.0";
        public final static QName TYPE_DOCUMENT=createQName(NAMESPACE_ACME_URL, "document");
        public final static QName PROP_DOCUMENT_ID=createQName(NAMESPACE_ACME_URL, "documentId");

    }

