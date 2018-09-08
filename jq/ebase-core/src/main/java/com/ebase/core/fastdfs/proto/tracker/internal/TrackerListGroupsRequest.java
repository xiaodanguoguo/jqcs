package com.ebase.core.fastdfs.proto.tracker.internal;

import com.ebase.core.fastdfs.proto.CmdConstants;
import com.ebase.core.fastdfs.proto.FdfsRequest;
import com.ebase.core.fastdfs.proto.ProtoHead;

/**
 * 列出分组命令
 * 
 * @author tobato
 *
 */
public class TrackerListGroupsRequest extends FdfsRequest {

    public TrackerListGroupsRequest() {
        head = new ProtoHead(CmdConstants.TRACKER_PROTO_CMD_SERVER_LIST_GROUP);
    }
}
