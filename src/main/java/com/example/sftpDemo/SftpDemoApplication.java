package com.example.sftpDemo;

import java.util.Properties;
import java.util.Vector;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

@SpringBootApplication
public class SftpDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SftpDemoApplication.class, args);
		
		try {
            String user = "test_user";
            String pass = "test";
            Properties config = new Properties();
            config.put("StrictHostKeyChecking","no");
            String host = "127.0.0.1";

            JSch jSch = new JSch();
            Session session = jSch.getSession(user,host);
            session.setPassword(pass);
            session.setConfig(config);
            session.connect();
            ChannelSftp channelSftp = (ChannelSftp) session.openChannel("sftp");
            channelSftp.connect();

            // read all remote file 
            Vector fileList = channelSftp.ls("inbound/");
            for (int i = 0; i < fileList.size(); i++) {
                ChannelSftp.LsEntry lsEntry = (ChannelSftp.LsEntry) fileList.get(i);
                System.out.println(lsEntry.getFilename());
            }
            
//            	rename the remote file
//            	channelSftp.rename("inbound/test.txt","inbound/testfile.txt");
//            upload the local file in remote server
//           channelSftp.put("C:\\desktop\\newfile.txt","inbound/newFile.txt");
            channelSftp.get("inbound/newfile2.txt", "C:\\desktop\\download\\newfile2.txt");

            channelSftp.get("inbound/newfile2.txt","newFile2.txt");
            System.out.println("Session connected: "+session.isConnected());
            channelSftp.disconnect();
            session.disconnect();
        } catch (Exception e) {
			// TODO: handle exception
		}
	}

}
