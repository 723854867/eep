package org.eep.common.bean.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.eep.common.bean.entity.SysRegion;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegionNode implements Serializable {

	private static final long serialVersionUID = -9059736736591660894L;

	private long id;
	private int left;
	private int right;
	private String code;
	private String name;
	private Long parent;
	// 是否拥有
	private boolean own;
	// 是否开放
	private boolean open;
	private List<RegionNode> children;
	
	public RegionNode() {}
	
	public RegionNode(SysRegion region) {
		this.id = region.getId();
		this.open = region.isOpen();
		this.left = region.getLeft();
		this.code = region.getCode();
		this.name = region.getName();
		this.right = region.getRight();
		this.children = new ArrayList<RegionNode>();
	}
	
	public void addChild(RegionNode node) { 
		this.children.add(node);
	}
}
