package com.ebase.core.fastdfs.proto;

import com.ebase.core.fastdfs.conn.Connection;

/**
 * Fdfs交易命令抽象
 * 
 * @author tobato
 *
 */
public interface FdfsCommand<T> {

    /** 执行交易 */
    public T execute(Connection conn);

}
