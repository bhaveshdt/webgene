package com.delrima.webgene.client.model;

import java.util.List;

public interface HasDescendants extends HasTreeMember {

    List<HasDescendants> getChildren();

}
