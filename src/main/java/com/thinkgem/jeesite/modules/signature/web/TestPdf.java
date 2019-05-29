package com.thinkgem.jeesite.modules.signature.web;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;

public class TestPdf {

/*	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Document document = new Document();
		 try {
			File file = new File("D:/test.pdf");
			
			if(!file.exists()){
				file.createNewFile();
			}
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("D:/test.pdf"));
			BaseFont baseFont = BaseFont.createFont("C:/Windows/Fonts/SIMYOU.TTF",BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED);    
			 Font font = new Font(baseFont);   
			 document.open();
			 document.add(new Paragraph("解决中文问题了2221111！",font));
			 document.close();
			 
			 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
	
	public static void main(String []args){
        File file = new File("d:/test.pdf");
        RandomAccessFile raf = null;
        FileChannel fc = null;
        FileLock fl = null;
        try{
            raf = new RandomAccessFile(file, "rw");
            fc = raf.getChannel();
            //此处主要是针对多线程获取文件锁时轮询锁的状态。如果只是单纯获得锁的话，直接fl = fc.tryLock();即可
            while(true){
                try{
                    //无参独占锁
                    //fl = fc.tryLock();
                    //采用共享锁
                    fl = fc.tryLock(0,Long.MAX_VALUE,true);
                    if(fl!=null){
                        System.out.println("get the lock");
                        break;
                    }
                }catch(Exception e){
                    //如果是同一进程的多线程，重复请求tryLock()会抛出OverlappingFileLockException异常
                    System.out.println("current thread is block");
                }
            }
            //获得文件锁权限后，进行相应的操作
            fl.release();
            fc.close();
            raf.close();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(fl!=null&&fl.isValid()){
                try {
                    fl.release();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

	}

}
