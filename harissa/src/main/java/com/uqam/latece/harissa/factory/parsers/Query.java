package com.uqam.latece.harissa.factory.parsers;


import com.jayway.jsonpath.Filter;

public class Query {

    //region PrivateFields
    private String pathToNode;
    private Filter filter;
    //endregion

    public Query(String pathToNode, Filter filter) {
        this.pathToNode = pathToNode;
        this.filter = filter;
    }

    //region PublicMethods
    public Query(String pathToNode)
    {
        this.pathToNode = pathToNode;
    }
    //endregion

    //region Setters&Getters
    public String getPathToNode() {
        return pathToNode;
    }

    public void setPathToNode(String pathToNode) {
        this.pathToNode = pathToNode;
    }

    public Filter getFilter() {
        return filter;
    }

    public void setFilter(Filter filter) {
        this.filter = filter;
    }
    //endregion

}
