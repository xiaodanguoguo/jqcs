package com.ebase.core.message.transaction;

import javax.transaction.xa.XAResource;

public class ResourceArchive {
	private XAResource resource;
	private TransactionXid xid;
	private boolean delisted;

	public XAResource getResource() {
		return resource;
	}

	public void setResource(XAResource resource) {
		this.resource = resource;
	}

	public TransactionXid getXid() {
		return xid;
	}

	public void setXid(TransactionXid xid) {
		this.xid = xid;
	}

	public boolean isDelisted() {
		return delisted;
	}

	public void setDelisted(boolean delisted) {
		this.delisted = delisted;
	}

}
