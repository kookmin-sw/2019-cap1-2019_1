package com.google.cloud.android.speech;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashSet;

public class Node implements Parcelable
{
    private int node_id;
    private String node_name;
    private double pos_x;
    private double pos_y;
    private String indoor;
    private String floor;
    private String node_type;
    private HashSet<Integer> node_neighbors;

    public Node(int node_id_,String node_name_, double pos_x_, double pos_y_, String indoor_, String floor_, String node_type_, HashSet<Integer> node_neighbors_)
    {
        node_id=node_id_;
        node_name=node_name_;
        pos_x=pos_x_;
        pos_y=pos_y_;
        indoor=indoor_;
        floor=floor_;
        node_type=node_type_;
        node_neighbors=node_neighbors_;
    }

    public Node(int node_id_,String node_name_, double pos_x_, double pos_y_, String indoor_, String floor_, String node_type_)
    {
        node_id=node_id_;
        node_name=node_name_;
        pos_x=pos_x_;
        pos_y=pos_y_;
        indoor=indoor_;
        floor=floor_;
        node_type=node_type_;
        node_neighbors=new HashSet<Integer>();
    }

    public Node(Parcel in) {
        node_id = in.readInt();
        node_name = in.readString();
        pos_x = in.readDouble();
        pos_y = in.readDouble();
        indoor = in.readString();
        floor = in.readString();
        node_type = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(node_id);
        dest.writeString(node_name);
        dest.writeDouble(pos_x);
        dest.writeDouble(pos_y);
        dest.writeString(indoor);
        dest.writeString(floor);
        dest.writeString(node_type);
    }

    public static final Creator<Node> CREATOR = new Creator<Node>() {
        @Override
        public Node createFromParcel(Parcel in) {
            return new Node(in);
        }

        @Override
        public Node[] newArray(int size) {
            return new Node[size];
        }
    };

    public int getNode_id() {
        return node_id;
    }

    public void setNode_id(int node_id) {
        this.node_id = node_id;
    }

    public String getNode_name() {
        return node_name;
    }

    public void setNode_name(String node_name) {
        this.node_name = node_name;
    }

    public double getPos_x() {
        return pos_x;
    }

    public void setPos_x(double pos_x) {
        this.pos_x = pos_x;
    }

    public double getPos_y() {
        return pos_y;
    }

    public void setPos_y(double pos_y) {
        this.pos_y = pos_y;
    }

    public String getIndoor() {
        return indoor;
    }

    public void setIndoor(String indoor) {
        this.indoor = indoor;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getNode_type() {
        return node_type;
    }

    public void setNode_type(String node_type) {
        this.node_type = node_type;
    }

    public HashSet<Integer> getNode_neighbors() {
        return node_neighbors;
    }

    //node_neighbors에 node_id 하나씩 추가할때 사용
    public void addNodeNeighbors(int node_id) {
        this.node_neighbors.add(node_id);
    }

    //node_neighbors에 node_id 하나씩 제거할때 사용
    public void removeNodeNeighbors(int node_id) {
        this.node_neighbors.remove(node_id);
    }
}