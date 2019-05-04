package com.google.cloud.android.speech;

public class Node {
    private int node_id;
    private String node_type;
    private String node_name;
    private double pos_x;
    private double pos_y;
    private String node_neighbors;

    public Node(int node_id_,double pos_x_, double pos_y_, String node_type_, String node_neighbors_, String node_name_)
    {
        node_id=node_id_;
        node_type=node_type_;
        node_name=node_name_;
        pos_x=pos_x_;
        pos_y=pos_y_;
        node_neighbors=node_neighbors_;
    }

    public int getNode_id() {
        return node_id;
    }

    public String getNode_type() {
        return node_type;
    }

    public String getNode_name() {
        return node_name;
    }

    public double getPos_x() {
        return pos_x;
    }

    public double getPos_y() {
        return pos_y;
    }

    public String getNode_neighbors() {
        return node_neighbors;
    }
}
