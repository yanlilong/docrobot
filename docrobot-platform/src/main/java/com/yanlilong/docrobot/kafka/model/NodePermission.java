package com.yanlilong.docrobot.kafka.model;

public class NodePermission {
    private String authority;
    private String authorityType;
    private String permission;
    private boolean isInherited;
    public NodePermission(NodePermissionBuilder builder){ }
    public static class NodePermissionBuilder{
        private String authority;
        private String authorityType;
        private String permission;
        private boolean isInherited;
        public NodePermissionBuilder(){

        }
        public NodePermissionBuilder authority(String authority){
            this.authority=authority;
            return this;
        }
        public NodePermissionBuilder authorityType(String authorityType){
            this.authorityType=authorityType;
            return this;
        }

        public NodePermissionBuilder permission(String permission){
            this.permission=permission;
            return this;
        }
        public NodePermissionBuilder isInherited(boolean isInherited){
            this.isInherited=isInherited;
            return this;
        }
        public NodePermission build(){
            return new NodePermission(this);
        }

    }
}
