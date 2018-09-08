package com.ebase.core.fastdfs.proto.tracker;

import com.ebase.core.fastdfs.domain.StorageNodeInfo;
import com.ebase.core.fastdfs.proto.AbstractFdfsCommand;
import com.ebase.core.fastdfs.proto.FdfsResponse;
import com.ebase.core.fastdfs.proto.tracker.internal.TrackerGetFetchStorageRequest;

/**
 * 获取源服务器
 * 
 * @author tobato
 *
 */
public class TrackerGetFetchStorageCommand extends AbstractFdfsCommand<StorageNodeInfo> {

    public TrackerGetFetchStorageCommand(String groupName, String path, boolean toUpdate) {
        super.request = new TrackerGetFetchStorageRequest(groupName, path, toUpdate);
        super.response = new FdfsResponse<StorageNodeInfo>() {
            // default response
        };
    }

}
