package bjtu.pt.easycontracts.controller;

import bjtu.pt.easycontracts.pojo.table.ContractAttachment;
import bjtu.pt.easycontracts.service.ContractFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * <Description> FileController
 *
 * @author 26802
 * @version 1.0
 * @ClassName FileController
 * @taskId
 * @see bjtu.pt.easycontracts.controller
 */
@Controller
public class FileController {

    @Autowired
    private ContractFileService contractFileService;

    @RequestMapping("/fileDown/{fname}/{contractId}")
    public ResponseEntity<byte[]> testResponseEntity(@PathVariable("contractId") Integer contractId,
                                                     @PathVariable("fname")String fname,
                                                     HttpSession session) throws IOException {
        ContractAttachment contractAttachment = contractFileService.getContractAttachment(contractId, fname);
        if (contractAttachment==null){
            return null;
        }
        String path = contractAttachment.getPath();
//        String path = "/demo/mygit/"+projectName+"/"+fname;
        //获取服务器中文件的真实路径
//        File file = ResourceUtils.getFile(path);
        File file = new File(path);
        //创建输入流
        InputStream is = new FileInputStream(file);
        //创建字节数组
        byte[] bytes = new byte[is.available()];
        //将流读到字节数组中
        is.read(bytes);
        //创建HttpHeaders对象设置响应头信息
        MultiValueMap<String, String> headers = new HttpHeaders();
        //设置要下载方式以及下载文件的名字
        headers.add("Content-Disposition", "attachment;filename="+fname);
        //设置响应状态码
        HttpStatus statusCode = HttpStatus.OK;
        //创建ResponseEntity对象
        ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(bytes, headers, statusCode);
        //关闭输入流
        is.close();
        return responseEntity;
    }

}
