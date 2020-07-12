package com.yanlilong.docrobot.datacapture.behavior;

import org.alfresco.repo.node.NodeServicePolicies;
import org.alfresco.repo.policy.Behaviour;
import org.alfresco.repo.policy.JavaBehaviour;
import org.alfresco.service.ServiceRegistry;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.namespace.QName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Map;

import static com.yanlilong.docrobot.DocRobotConstants.TYPE_SOMMER_PLAN;

public class SommerPlanBehavior {
    private static final Logger LOGGER = LoggerFactory.getLogger(SommerPlanBehavior.class);
    private final ServiceRegistry serviceRegistry;

    public SommerPlanBehavior(ServiceRegistry serviceRegistry) {
        this.serviceRegistry = serviceRegistry;
        LOGGER.info("BEHAVIOR");
    }

    public void registerEventHandlers() {
          serviceRegistry.getPolicyComponent().bindClassBehaviour(
         NodeServicePolicies.OnUpdatePropertiesPolicy.QNAME, TYPE_SOMMER_PLAN,
         new JavaBehaviour(this, "onUpdateSummerHolidayProperties",
         Behaviour.NotificationFrequency.TRANSACTION_COMMIT));

    }

     public void onUpdateSummerHolidayProperties(NodeRef summerPlan, Map<QName, Serializable> before,
                                                 Map<QName, Serializable> after) {
     //if(after.containsKey(PROP_START_DATE))
     LOGGER.info("before" + before);
     LOGGER.info("after" + after);
     }

}
