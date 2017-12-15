/*
  This is the confidential, unpublished property of Calix Networks.  Receipt
  or possession of it does not convey any rights to divulge, reproduce, use,
  or allow others to use it without the specific written authorization of
  Calix Networks and use must conform strictly to the license agreement
  between user and Calix Networks.

  Copyright (c) 2000-2001 Calix Networks.  All rights reserved.
*/

package com.calix.bseries.gui;

import com.objectsavvy.base.gui.graphics.graph.model.Graph;
import com.objectsavvy.base.gui.graphics.graph.model.BasicGraphImpl;
import com.objectsavvy.base.util.debug.Code;

import com.objectsavvy.base.persistence.model.IDatabase;

import com.calix.ems.model.CalixB6Chassis;
import com.calix.ems.model.EMSRegion;

/**
 * A factory type class responsible for creating DetailedViewGraph
 * objects.
 */
public class BseriesTopologyGraphImpl extends BasicGraphImpl {
  private CalixB6Chassis m_chassis = null;
  private BseriesTopologyGraph m_graph = null;
  
  private IDatabase m_database = null;

  public BseriesTopologyGraphImpl(CalixB6Chassis selection) {
	  m_chassis = selection;
  }
  
  public void setDatabase(IDatabase pDatabase) {
    m_database = pDatabase;
  }
  

  public Graph createGraph(Object semanticObject) {
    if(Code.debug())
      Code.debug("TopologyGraphImpl.createGraph ...");
    if(m_graph == null) {
      m_graph = new BseriesTopologyGraph(this,m_chassis);
      m_graph.setDatabase(m_database);
      m_graph.loadData();
    }
    return m_graph;
  }

  private static final String _rev_="$Revision: 2 $";
}
