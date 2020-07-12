package com.yanlilong.docrobot;
import org.alfresco.service.namespace.QName;

import static org.alfresco.service.namespace.QName.createQName;

public class DocRobotConstants {
    private DocRobotConstants() {
    }

    public static final String NAMESPACE_DOC_ROBOT_URL = "http://www.yanlilong.com/model/content/docrobot/1.0";
    public final static QName TYPE_SUMMER_HOLIDAYS=createQName(NAMESPACE_DOC_ROBOT_URL, "summerholiday");
    public final static QName PROP_START_DATE=createQName(NAMESPACE_DOC_ROBOT_URL, "startDate");
    public final static QName PROP_FINISH_DATE=createQName(NAMESPACE_DOC_ROBOT_URL, "finishDate");

    public static final String NAMESPACE_ACME_URL = "http://www.acme.org/model/content/1.0";
    public final static QName TYPE_SOMMER_PLAN=createQName(NAMESPACE_ACME_URL, "sommerPlan");
    public final static QName PROP_PLAN_ID=createQName(NAMESPACE_ACME_URL, "planId");

}
