package com.ebase.core.message.connector;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.resource.spi.ManagedConnectionFactory;
import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;

import com.ebase.core.message.Message;
import com.ebase.core.message.MessageException;
import com.ebase.core.message.transaction.TransactionXid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ebase.core.message.service.MessageProduceService;

public class XAResourceImpl implements XAResource {
	static Logger logger = LoggerFactory.getLogger(XAResourceImpl.class);

	private ManagedConnectionFactory mcf;
	private MessageProduceService messageProducer;

	private Map<Xid, List<Message>> messageMap = new ConcurrentHashMap<Xid, List<Message>>();
	private ThreadLocal<Xid> globalXidMap = new ThreadLocal<Xid>();

	protected Xid getGlobalXid(Xid branch) {
		byte[] byteArray = branch.getGlobalTransactionId();
		byte[] globalTransactionId = new byte[byteArray.length];
		System.arraycopy(byteArray, 0, globalTransactionId, 0, byteArray.length);

		TransactionXid xid = new TransactionXid();
		xid.setGlobalTransactionId(globalTransactionId);
		return xid;
	}

	public void addMessage(Message message) throws XAException {
		Xid xid = this.globalXidMap.get();
		if (xid == null) {
			throw new XAException(XAException.XAER_NOTA);
		}
		// message.setApplication(this.applicationPropertySupport.getApplication());
		List<Message> messageList = this.messageMap.get(xid);
		messageList.add(message);
	}

	public void commit(Xid branch, boolean arg1) throws XAException {
		this.globalXidMap.remove();

		Xid xid = this.getGlobalXid(branch);
		boolean failure = false;
		List<Message> messages = this.messageMap.remove(xid);

		for (int i = 0; i < messages.size(); i++) {
			Message message = messages.get(i);

			try {
				this.messageProducer.sendMessage(message);
			} catch (MessageException ex) {
				failure = true;
				logger.error(String.format("发送消息失败(%s)!", message), ex);
			}
		}

		if (failure) {
			throw new XAException(XAException.XAER_RMFAIL);
		}
	}

	public void rollback(Xid branch) throws XAException {
		this.globalXidMap.remove();

		Xid xid = this.getGlobalXid(branch);
		this.messageMap.remove(xid);
	}

	public void start(Xid branch, int flag) throws XAException {
		Xid xid = this.getGlobalXid(branch);
		if (flag == XAResource.TMNOFLAGS) {
			if (this.messageMap.containsKey(xid)) {
				throw new XAException(XAException.XAER_PROTO);
			}
			this.globalXidMap.set(xid);
			this.messageMap.put(xid, new ArrayList<Message>());
		} else if (flag == XAResource.TMRESUME) {
			if (this.messageMap.containsKey(xid) == false) {
				throw new XAException(XAException.XAER_PROTO);
			}
			this.globalXidMap.set(xid);
		} else if (flag == XAResource.TMJOIN) {
			if (this.messageMap.containsKey(xid) == false) {
				throw new XAException(XAException.XAER_PROTO);
			}
			this.globalXidMap.set(xid);
		} else {
			throw new XAException(XAException.XAER_PROTO);
		}
	}

	public void end(Xid branch, int flag) throws XAException {
		Xid xid = this.getGlobalXid(branch);
		if (flag == XAResource.TMSUCCESS || flag == XAResource.TMFAIL || flag == XAResource.TMSUSPEND) {
			if (this.messageMap.containsKey(xid) == false) {
				throw new XAException(XAException.XAER_PROTO);
			}
			this.globalXidMap.remove();
		} else {
			throw new XAException(XAException.XAER_PROTO);
		}
	}

	public void forget(Xid branch) throws XAException {
		Xid xid = this.getGlobalXid(branch);
		this.messageMap.remove(xid);
	}

	public int getTransactionTimeout() throws XAException {
		return 0;
	}

	public boolean isSameRM(XAResource xares) throws XAException {
		if (xares == null) {
			return false;
		} else if (XAResourceImpl.class.isInstance(xares) == false) {
			return false;
		}
		XAResourceImpl that = (XAResourceImpl) xares;
		return this.getMcf().equals(that.getMcf());
	}

	public int prepare(Xid arg0) throws XAException {
		throw new XAException(XAException.XAER_RMFAIL);
	}

	public Xid[] recover(int arg0) throws XAException {
		return new Xid[0];
	}

	public boolean setTransactionTimeout(int arg0) throws XAException {
		return false;
	}

	public MessageProduceService getMessageProducer() {
		return messageProducer;
	}

	public void setMessageProducer(MessageProduceService messageProducer) {
		this.messageProducer = messageProducer;
	}

	public ManagedConnectionFactory getMcf() {
		return mcf;
	}

	public void setMcf(ManagedConnectionFactory mcf) {
		this.mcf = mcf;
	}

}
