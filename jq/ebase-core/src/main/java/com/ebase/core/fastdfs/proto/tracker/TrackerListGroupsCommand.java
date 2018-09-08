package com.ebase.core.fastdfs.proto.tracker;

import com.ebase.core.fastdfs.domain.GroupState;
import com.ebase.core.fastdfs.proto.AbstractFdfsCommand;
import com.ebase.core.fastdfs.proto.tracker.internal.TrackerListGroupsRequest;
import com.ebase.core.fastdfs.proto.tracker.internal.TrackerListGroupsResponse;

import java.util.List;

/**
 * 列出组命令
 * 
 * @author tobato
 *
 */
public class TrackerListGroupsCommand extends AbstractFdfsCommand<List<GroupState>> {

    public TrackerListGroupsCommand() {
        super.request = new TrackerListGroupsRequest();
        super.response = new TrackerListGroupsResponse();
    }

}
