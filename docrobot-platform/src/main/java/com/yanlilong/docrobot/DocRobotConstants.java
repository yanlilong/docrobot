package com.yanlilong.docrobot;
import org.alfresco.service.namespace.QName;

import static org.alfresco.service.namespace.QName.createQName;

public class DocRobotConstants {
    private DocRobotConstants() {
    }

    public static final String NAMESPACE_DOC_ROBOT = "http://docrobot/model/content/1.0";
    public final static QName TYPE_SUMMER_HOLIDAYS=createQName(NAMESPACE_DOC_ROBOT, "summerholidays");
    public final static QName PROP_START_DATE=createQName(NAMESPACE_DOC_ROBOT, "startDate");
    public final static QName PROP_FINISH_DATE=createQName(NAMESPACE_DOC_ROBOT, "finishDate");

}
