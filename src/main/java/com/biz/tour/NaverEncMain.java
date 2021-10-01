package com.biz.tour;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Scanner;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

/*
 * 키보드에서 문자열을 입력받아서 암호화된 문자열을 생성
 */
public class NaverEncMain {

	public static void main(String[] args) {
		StandardPBEStringEncryptor pbEnc=new StandardPBEStringEncryptor();
		/*
		 * local comp 설정된 환경 변수들 목록을 가져와서 그중에 BIZ.COM 으로 등록된 값을 보여라
		 */
		Map<String,String> envList=System.getenv();
		System.out.println(envList.get("testsalt"));
		
		Scanner scanner=new Scanner(System.in);
		System.out.println("naver user name >> ");
		String userName=scanner.nextLine();
		System.out.println("naver Password >> ");
		String password=scanner.nextLine();
		
		/*
		 * 암호화를 하기위한 설정
		 */
		pbEnc.setAlgorithm("PBEWithMD5AndDES");
		pbEnc.setPassword(envList.get("testsalt"));
		
		String encUserName=pbEnc.encrypt(userName);
		String encPassword=pbEnc.encrypt(password);
		
		System.out.printf("userName: %s \n",encUserName);
		System.out.printf("password: %s \n",encPassword);
		
		String saveFile="./src/main/webapp/WEB-INF/spring/appServlet/props/naver.email.properties";
		
		String saveUserName=String.format("naver.username=ENC(%s)", encUserName);
		String savePassword=String.format("naver.password=ENC(%s)", encPassword);
		
		
		try {
			PrintWriter out=new PrintWriter(saveFile);
			out.println(saveUserName);
			out.println(savePassword);
			out.flush();
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		scanner.close();
	}

}
