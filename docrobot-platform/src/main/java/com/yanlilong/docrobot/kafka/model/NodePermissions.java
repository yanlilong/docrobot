package com.yanlilong.docrobot.kafka.model;
import java.util.Set;
public class NodePermissions {
    private Set<NodePermission> permissions;
    private boolean isInheritanceEnabled;
    public NodePermissions(NodePermissionsBuilder builder){ }
    public NodePermissions(){}
    public void setPermissions(Set<NodePermission> permissions) {
        this.permissions = permissions;
    }

    public void setInheritanceEnabled(boolean inheritanceEnabled) {
        isInheritanceEnabled = inheritanceEnabled;
    }

    public static class NodePermissionsBuilder{
        private Set<NodePermission> permissions;
        private boolean isInheritanceEnabled;

        public NodePermissionsBuilder() {
        }

        public NodePermissionsBuilder permissions(Set<NodePermission> permissions){
            this.permissions=permissions;
            return this;
        }

        public NodePermissionsBuilder isInheritanceEnabled(boolean isInheritanceEnabled){
            this.isInheritanceEnabled=isInheritanceEnabled;
            return this;
        }
        public NodePermissions build(){
           return new NodePermissions(this);
        }
    }
}
