package com.example.cookapp.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class StreamTool {

	
		public static byte[] read(InputStream inStream) throws Exception
		{
			ByteArrayOutputStream outStream=new ByteArrayOutputStream();
			
			int len=0;
			
			byte[] buffer=new byte[4096];
			
			while((len=inStream.read(buffer))!=-1)
			{
				outStream.write(buffer,0,len);
			}
			
			inStream.close();
			
			return outStream.toByteArray();
		}
}
