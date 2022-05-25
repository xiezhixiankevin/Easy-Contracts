package bjtu.pt.easycontracts.service.impl;

import bjtu.pt.easycontracts.mapper.ContractAttachmentMapper;
import bjtu.pt.easycontracts.pojo.table.ContractAttachment;
import bjtu.pt.easycontracts.service.ContractFileService;
import bjtu.pt.easycontracts.utils.Global;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import java.io.*;
/**
 * <Description> ContractFileServiceImpl
 *
 * @author 26802
 * @version 1.0
 * @ClassName ContractFileServiceImpl
 * @taskId
 * @see bjtu.pt.easycontracts.service.impl
 */
@Service
public class ContractFileServiceImpl implements ContractFileService {

    @Autowired
    private ContractAttachmentMapper contractAttachmentMapper;

    @Override
    public int addContractFile(ContractAttachment contractAttachment, MultipartFile upload) {

        // 1. 获取要上传文件的文件名
        String fileName = upload.getOriginalFilename();
        // 2. 自定义上传文件夹
        String path = Global.FILE_PATH +"//"+"contract"+contractAttachment.getContractid().toString();
        // 3. 判断路径是否存在，不存在则新建
        File file  = new File(path);
        if (!file.exists()){
            file.mkdir();
        }
        String filePath = path+"//"+fileName; //文件路径
        File newFile = new File(filePath);
        try {
            upload.transferTo(newFile);
            //将附件信息存入数据库
            contractAttachment.setFilename(fileName);
            contractAttachment.setPath(filePath);
            contractAttachmentMapper.insert(contractAttachment);
            return Global.SUCCESS;
        } catch (IOException e) {
            return Global.FAIL;
        }
    }

    @Override
    public int deleteContractFile(int id) {
        return 0;
    }


    @Override
    public int deleteFile(String filePath) {
        return 0;
    }
}