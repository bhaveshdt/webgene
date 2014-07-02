package com.delrima.webgene.client.model;

public interface HasAncestors extends HasTreeMember {

    HasAncestors getMother();

    HasAncestors getFather();

}
