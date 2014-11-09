package com.archvile.page;

import java.util.ArrayList;
import java.util.List;

import com.archvile.node.nodes.AnchorNode;
import com.archvile.node.nodes.BodyNode;
import com.archvile.node.nodes.DivNode;
import com.archvile.node.nodes.H1Node;
import com.archvile.node.nodes.H2Node;
import com.archvile.node.nodes.MetaNode;
import com.archvile.node.nodes.TitleNode;

public class Page {

	private String url;
	private TitleNode title;
	private BodyNode body;
	private List<DivNode> divs;
	private List<AnchorNode> anchors;
	private List<MetaNode> metas;
	private List<H1Node> h1s;
	private List<H2Node> h2s;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getTitle() {
		return title.getTitle();
	}
	
	public void setTitle(TitleNode title) {
		this.title = title;
	}
	
	public BodyNode getBody() {
		return body;
	}

	public void setBody(BodyNode body) {
		this.body = body;
	}
	
	public List<DivNode> getDivs() {
		return divs;
	}
	
	public void addDiv(DivNode div) {
		if (divs == null) divs = new ArrayList<DivNode>();
		if (!divs.contains(div)) {
			divs.add(div);	
		}
	}
	
	public void setDivs(List<DivNode> divs) {
		this.divs = divs;
	}
	
	public List<AnchorNode> getAnchors() {
		return anchors;
	}
	
	public void addAnchor(AnchorNode anchor) {
		if (anchors == null) anchors = new ArrayList<AnchorNode>();
		if (!anchors.contains(anchor) && anchor.isValid()) {
			anchors.add(anchor);
		}
	}
	
	public void setAnchors(List<AnchorNode> anchors) {
		this.anchors = anchors;
	}
	
	public void addMeta(MetaNode meta) {
		if (metas == null) metas = new ArrayList<MetaNode>();
		metas.add(meta);
	}
	
	public List<MetaNode> getMetas() {
		return metas;
	}
	
	public void setMetas(List<MetaNode> metas) {
		this.metas = metas;
	}
	
	public void addH1(H1Node h1) {
		if (h1s == null) h1s = new ArrayList<H1Node>();
		h1s.add(h1);
	}
	
	public List<H1Node> getH1s() {
		return h1s;
	}

	public void setH1s(List<H1Node> h1s) {
		this.h1s = h1s;
	}
	
	public void addH2(H2Node h2) {
		if (h2s == null) h2s = new ArrayList<H2Node>();
		h2s.add(h2);
	}
	
	public void setH2s(List<H2Node> h2s) {
		this.h2s = h2s;
	}
	
	public List<H2Node> getH2s() {
		return h2s;
	}	
	
}
