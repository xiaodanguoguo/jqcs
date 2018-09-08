package com.ebase.core.fastdfs.proto.tracker;

import com.ebase.core.fastdfs.proto.AbstractFdfsCommand;
import com.ebase.core.fastdfs.proto.FdfsResponse;
import com.ebase.core.fastdfs.proto.tracker.internal.TrackerDeleteStorageRequest;

/**
 * 移除存储服务器命令
 * 
 * @author tobato
 *
 */
public class TrackerDeleteStorageCommand extends AbstractFdfsCommand<Void> {

    public TrackerDeleteStorageCommand(String groupName, String storageIpAddr) {
        super.request = new TrackerDeleteStorageRequest(groupName, storageIpAddr);
        super.response = new FdfsResponse<Void>() {
            // default response
        };
    }

}
