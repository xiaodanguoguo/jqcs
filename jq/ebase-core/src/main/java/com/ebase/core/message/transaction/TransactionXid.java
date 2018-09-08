package com.ebase.core.message.transaction;

import java.util.Arrays;

import javax.transaction.xa.Xid;

public class TransactionXid implements Xid {
	public static final int FORMAT_ID = 1674;

	private byte[] globalTransactionId = new byte[0];
	private byte[] branchQualifier = new byte[0];

	public int hashCode() {
		int hash = 11;
		hash += 13 * FORMAT_ID;
		hash += 17 * Arrays.hashCode(branchQualifier);
		hash += 19 * Arrays.hashCode(globalTransactionId);
		return hash;
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		} else if (TransactionXid.class.isInstance(obj) == false) {
			return false;
		}
		TransactionXid that = (TransactionXid) obj;
		if (this.getFormatId() != that.getFormatId()) {
			return false;
		} else if (Arrays.equals(branchQualifier, that.branchQualifier) == false) {
			return false;
		} else if (Arrays.equals(globalTransactionId, that.globalTransactionId) == false) {
			return false;
		}
		return true;
	}

	public int getFormatId() {
		return FORMAT_ID;
	}

	public byte[] getGlobalTransactionId() {
		return globalTransactionId;
	}

	public void setGlobalTransactionId(byte[] globalTransactionId) {
		this.globalTransactionId = globalTransactionId;
	}

	public byte[] getBranchQualifier() {
		return branchQualifier;
	}

	public void setBranchQualifier(byte[] branchQualifier) {
		this.branchQualifier = branchQualifier;
	}

}
