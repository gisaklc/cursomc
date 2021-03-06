package com.gisaklc.cursomc.services;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.gisaklc.cursomc.resource.exception.FileException;

@Service
public class S3Service {

	private Logger LOG = LoggerFactory.getLogger(S3Service.class);

	@Autowired
	private AmazonS3 s3client;

	@Value("${s3.bucket}")
	private String bucketName;

	public void uploadFile(String localFilePath) {

		try {
			File file = new File(localFilePath);
			LOG.info("INICIANDO UPLOAD");
			s3client.putObject(new PutObjectRequest(bucketName, "teste.jpg", file));
			LOG.info("UPLOAD FINALIZADO");
		} catch (AmazonServiceException e) {
			LOG.info("AmazonServiceException " + e.getErrorMessage());
			LOG.info("Status Code " + e.getErrorCode());
		} catch (AmazonClientException e) {
			LOG.info("AmazonServiceException " + e.getMessage());

		}

	}

	// Multiport tipo do spring o arquivo q vem da requisicao
	// URI o endereco Web do novo recurso q foi enviado
	public URI uploadFile(MultipartFile multipartFile) throws IOException {
		try {
			String fileName = multipartFile.getOriginalFilename();// extrai o nome do arquivo enviado
			InputStream is = multipartFile.getInputStream();// obj de Read do java io q encapsula o proc de leitura a partir da origem
			String contentType = multipartFile.getContentType();// inf fo tipo do arquivo enviado
			return uploadFile(is, fileName, contentType);
		} catch (AmazonServiceException e) {
			throw new FileException("Erro de IO: " + e.getMessage());
		}
	}

//metodo sobrecarregado com outros parametros
	public URI uploadFile(InputStream is, String fileName, String contentType) {
		try {
			ObjectMetadata meta = new ObjectMetadata();//da biblioteca da Amazon
			meta.setContentType(contentType);
			LOG.info("Iniciando upload");
			s3client.putObject(bucketName, fileName, is, meta);
			LOG.info("Upload finalizado");
			return s3client.getUrl(bucketName, fileName).toURI();//converte para URI
		} catch (URISyntaxException e) {
			throw new FileException("Erro ao converter URL para URI");
		}
	}
}
