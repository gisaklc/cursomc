package com.gisaklc.cursomc.services;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.gisaklc.cursomc.resource.exception.FileException;

//serviço por fornecer funcionalidades de imagem

@Service
public class ImageService {

	/** pegar uma imagem e converter para BufferedImage JPG 
	 * este codigo é especifico**/
	public BufferedImage getJpgImageFromFile(MultipartFile uploadedFile) {

		//extrai a extensao do arquivo multipartFile
		String ext = FilenameUtils.getExtension(uploadedFile.getOriginalFilename());

		if (!"png".equals(ext) && !"jpg".equals(ext)) {
			throw new FileException("Somente imagens PNG e JPG são permitidas");
		}

		try {
			//ler imagem do arquivo
			BufferedImage img = ImageIO.read(uploadedFile.getInputStream());
			if ("png".equals(ext)) {
				img = pngToJpg(img);//converter para jpg se for pnf
			}
			return img;
		} catch (IOException e) {
			throw new FileException("Erro ao ler arquivo");
		}
	}

	public BufferedImage pngToJpg(BufferedImage img) {
		BufferedImage jpgImage = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
		jpgImage.createGraphics().drawImage(img, 0, 0, Color.WHITE, null);
		return jpgImage;
	}
	//retorna um inputStream a partir de um bufferedImage
	//o metodo q faz o upload do s3 recebe um inputStream 
	public InputStream getInputStream(BufferedImage img, String extension) {
		try {
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			ImageIO.write(img, extension, os);
			return new ByteArrayInputStream(os.toByteArray());
		} catch (IOException e) {
			throw new FileException("Erro ao ler arquivo");
		}
	}

//	public BufferedImage cropSquare(BufferedImage sourceImg) {
//		int min = (sourceImg.getHeight() <= sourceImg.getWidth()) ? sourceImg.getHeight() : sourceImg.getWidth();
//		return Scalr.crop(sourceImg, (sourceImg.getWidth() / 2) - (min / 2), (sourceImg.getHeight() / 2) - (min / 2),
//				min, min);
//	}
//
//	public BufferedImage resize(BufferedImage sourceImg, int size) {
//		return Scalr.resize(sourceImg, Scalr.Method.ULTRA_QUALITY, size);
//	}
}
