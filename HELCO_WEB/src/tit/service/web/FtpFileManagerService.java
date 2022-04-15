package tit.service.web;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import tit.base.ServiceManagerFactory;
import tit.service.core.logger.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class FtpFileManagerService {
    private static FtpFileManagerService instance;
    private final Logger logger = ServiceManagerFactory.getLogger();
    private static final String SERVER = "10.105.1.48";
    private static final int PORT = 20020;
    private static final String USER = "srmftpacct";
    private static final String PASSWD = "ehgur123";
    private static final String ROOT = "/file_storage/";

    private FTPClient ftpClient;

    private FtpFileManagerService() {
        ftpClient = new FTPClient();
        ftpClient.setControlEncoding("euc-kr"); // �ѱ� encoding....
 
        //ftpClient.setControlEncoding("utf-8"); // �ѱ� encoding....
    }

    public static synchronized FtpFileManagerService getInstance() {
        if (instance == null) {
            instance = new FtpFileManagerService();
        }
        return instance;
    }

    public ArrayList<HashMap<String, String>> getFileInfoList(String relativePath) {
        ArrayList<HashMap<String, String>> ret = new ArrayList<HashMap<String, String>>();
        if (!login()) {
            return null;
        }
        FTPFile[] list = new FTPFile[0];
        try {
            list = ftpClient.listFiles(ROOT + relativePath);
        } catch (IOException e) {
            logger.write(e.getMessage());
            e.printStackTrace();
        }
        if (list == null) return null;
        for (FTPFile file : list) {
            HashMap<String, String> item = new HashMap<String, String>();
            item.put("FNAME", file.getName());
            item.put("FPATH", ROOT + relativePath);
            Date date = file.getTimestamp().getTime();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            item.put("UDATE", format.format(date));
            ret.add(item);
        }
        return ret;
    }

    public void close() {
        if (ftpClient.isConnected()) {
            if (logout()) {
                disconnect();
            }
        }
    }

    // ������ �н������ �α���
    private boolean login() {
        try {
            if (connect()) {
                return ftpClient.login(USER, PASSWD);
            }
        } catch (IOException ioe) {
            logger.write(ioe.getMessage());
            ioe.printStackTrace();
        }
        return false;
    }

    // �����κ��� �α׾ƿ�
    private boolean logout() {
        try {
            return ftpClient.logout();
        } catch (IOException ioe) {
            logger.write(ioe.getMessage());
            ioe.printStackTrace();
        }
        return false;
    }

    // ������ ����
    private boolean connect() {
        boolean bConnected = false;
        System.out.println("���� ���� ��!");
        try {
            ftpClient.connect(SERVER, PORT);
            int reply;
            // ���� �õ���, �����ߴ��� ���� �ڵ� Ȯ��
            reply = ftpClient.getReplyCode();

            if (!FTPReply.isPositiveCompletion(reply)) {
                ftpClient.disconnect();
                logger.write("connection is rejected from ftp server");
                System.err.println("connection is rejected from ftp server");
                // System.exit(1);

            } else {
                bConnected = true;
                logger.write("connected to ftp server!");
                System.out.println("connected to ftp server!");
            }
        } catch (IOException ioe) {
            logger.write(ioe.getMessage());
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException f) {
                    //
                }
            }
            logger.write("fail to connect ftp server");
            System.err.println("fail to connect ftp server");
            //System.exit(1);
        }

        return bConnected;
    }

    public InputStream getFileStream(String root, String path) {
        if (!login()) {
            return null;
        }

        InputStream in = null;
        try {
        	ftpClient.setFileTransferMode(ftpClient.BINARY_FILE_TYPE);
    		ftpClient.setFileType(ftpClient.BINARY_FILE_TYPE);
    		ftpClient.enterLocalPassiveMode();
            in = ftpClient.retrieveFileStream(root + path);
        } catch (IOException e) {
            logger.write(e.getMessage());
            e.printStackTrace();
        }
        return in;
    }

    public InputStream getFileStream(String path) {
        return getFileStream(ROOT, path);
    }

    // �����κ��� ������ �ݴ´�
    private void disconnect() {
        try {
            ftpClient.disconnect();
        } catch (IOException ioe) {
            logger.write(ioe.getMessage());
            ioe.printStackTrace();
        }
    }
}
