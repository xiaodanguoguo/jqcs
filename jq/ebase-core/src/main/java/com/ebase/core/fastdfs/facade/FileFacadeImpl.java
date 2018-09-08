package com.ebase.core.fastdfs.facade;

import com.ebase.core.exception.BusinessException;
import com.ebase.core.fastdfs.domain.MateData;
import com.ebase.core.fastdfs.domain.StorePath;
import com.ebase.core.fastdfs.dto.FileDTO;
import com.ebase.core.fastdfs.proto.storage.DownloadByteArray;
import com.ebase.core.fastdfs.service.FastFileStorageClient;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * <p>
 * 上传文件对外接口
 * </p>
 *
 * @project common-service-common-facade
 * @class FileFacadeImpl
 */
// @Vali(useAnnotation = true)
@Service("fileFacade")
public class FileFacadeImpl {
	private final static Logger logger = LoggerFactory.getLogger(FileFacadeImpl.class);

	static final String CONSTANT_FILE_NAME = "FILE_NAME";

	@Autowired
	private FastFileStorageClient storageClient;

	public String uploadFile(@NotNull String groupName, @NotNull InputStream inputStream, @NotEmpty String fileName, String fileExtName) throws BusinessException{
		try {
			byte[] byt = new byte[inputStream.available()];
			StorePath store = this.storageClient.uploadFile(groupName, inputStream, byt.length, fileExtName);
			Set<MateData> metaDataSet = new HashSet<MateData>();
			metaDataSet.add(new MateData(CONSTANT_FILE_NAME, fileName));
			this.storageClient.overwriteMetadata(groupName, store.getPath(), metaDataSet);
			return store.getFullPath();
		} catch (Exception ex) {
			logger.error("上传文件出错!", ex);
			throw new BusinessException("f0101", null);
		} finally {
			IOUtils.closeQuietly(inputStream);
		}
	}


	public String uploadFile(@NotNull String groupName, @NotNull byte[] contents, @NotEmpty String fileName, String fileExtName)
			throws BusinessException {
		ByteArrayInputStream in = null;
		try {
			in = new ByteArrayInputStream(contents);
			StorePath store = this.storageClient.uploadFile(groupName, in, contents.length, fileExtName);
			Set<MateData> metaDataSet = new HashSet<MateData>();
			metaDataSet.add(new MateData(CONSTANT_FILE_NAME, fileName));
			this.storageClient.overwriteMetadata(groupName, store.getPath(), metaDataSet);
			return store.getFullPath();
		} catch (Exception ex) {
			logger.error("上传文件出错!", ex);
			throw new BusinessException("f0101", null);
		} finally {
			IOUtils.closeQuietly(in);
		}
	}

	public FileDTO downloadFile(@NotEmpty String fileId) throws BusinessException {
		try {
			StorePath storePath = StorePath.praseFromUrl(fileId);
			byte[] contents = this.storageClient.downloadFile(storePath.getGroup(), storePath.getPath(),
					new DownloadByteArray());

			FileDTO file = new FileDTO();
			file.setContents(contents);
			file.setFileId(fileId);

			Set<MateData> metaDataSet = this.storageClient.getMetadata(storePath.getGroup(), storePath.getPath());
			for (Iterator<MateData> itr = metaDataSet == null ? null : metaDataSet.iterator(); itr != null && itr.hasNext();) {
				MateData element = itr.next();
				if (StringUtils.equalsIgnoreCase(CONSTANT_FILE_NAME, element.getName())) {
					file.setFileName(element.getValue());
					break;
				}
			}

			return file;
		} catch (Exception ex) {
			logger.error("下载文件出错!", ex);
			throw new BusinessException("f0102", null);
		}
	}

	public boolean exist(@NotEmpty String fileId) throws BusinessException {
		try {
			StorePath storePath = StorePath.praseFromUrl(fileId);
			this.storageClient.queryFileInfo(storePath.getGroup(), storePath.getPath());
			return true;
		} catch (Exception ex) {
			return false; // 文件找不到会报错，直接返回false
		}
	}

	public boolean delete(@NotEmpty String fileId) throws BusinessException {
		StorePath storePath = StorePath.praseFromUrl(fileId);
		try {
			this.storageClient.deleteFile(storePath.getGroup(), storePath.getPath());
			return true;
		} catch (Exception ex) {
			return false; // 文件找不到会报错，直接返回false
		}
	}

}
