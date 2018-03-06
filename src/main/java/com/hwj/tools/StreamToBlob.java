package com.hwj.tools;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Clob;

import org.hibernate.LobHelper;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.springframework.stereotype.Component;

@Component
public class StreamToBlob {

	public Blob toBlob(InputStream inputStream) throws IOException {

		Configuration cfg = new Configuration().configure();
		ServiceRegistry sr = new ServiceRegistryBuilder().applySettings(
				cfg.getProperties()).buildServiceRegistry();
		SessionFactory sf = cfg.buildSessionFactory(sr);
		Session session = sf.openSession();

		LobHelper lobHelper = session.getLobHelper();
		Blob blob = lobHelper.createBlob(inputStream, inputStream.available());
		session.close();

		return blob;

	}

	public byte[] blobToBytes(Blob blob) {

		InputStream is = null;
		byte[] b = null;
		try {
			is = blob.getBinaryStream();
			b = new byte[(int) blob.length()];
			is.read(b);
			return b;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
				is = null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return b;
	}
	
	
	public Clob toClob(String string) throws IOException {

		Configuration cfg = new Configuration().configure();
		ServiceRegistry sr = new ServiceRegistryBuilder().applySettings(
				cfg.getProperties()).buildServiceRegistry();
		SessionFactory sf = cfg.buildSessionFactory(sr);
		Session session = sf.openSession();

		LobHelper lobHelper = session.getLobHelper();
		Clob clob = lobHelper.createClob(string);
		session.close();

		return clob;

	}

}
