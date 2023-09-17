package ecommerce.mobile.seriveimp;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import ecommerce.mobile.service.CloudinaryService;
import io.micrometer.common.util.StringUtils;

@Service
public class CloudinaryServiceImp implements CloudinaryService {
	private final Cloudinary cloudinary;
	private final String CLOUD_NAME;
	private final String API_KEY;
	private final String API_SECRET;
	private final String ROOT_NAME;
	private static final Logger logger = LoggerFactory.getLogger(CloudinaryServiceImp.class);
	ObjectMapper objMapper = new ObjectMapper();

	public CloudinaryServiceImp(@Value("${app.cloudinary.name}") String CLOUD_NAME,
			@Value("${app.cloudinary.api_key}") String API_KEY,
			@Value("${app.cloudinary.api_secret}") String API_SECRET,
			@Value("${app.cloudinary.root_name}") String ROOT_NAME) {
		this.CLOUD_NAME = CLOUD_NAME;
		this.API_KEY = API_KEY;
		this.API_SECRET = API_SECRET;
		this.ROOT_NAME = ROOT_NAME;
		this.cloudinary = new Cloudinary(
				ObjectUtils.asMap("cloud_name", CLOUD_NAME, "api_key", API_KEY, "api_secret", API_SECRET));
	}

	@SuppressWarnings("unchecked")
	public String uploadFile(MultipartFile file, String folerName, String type) throws IOException {
		Map<String, Object> params = ObjectUtils.asMap("folder", folerName, "resource_type", type);

		Map<?, ?> uploadResult = cloudinary.uploader().upload(file.getBytes(), params);
		return uploadResult.get("url").toString();
	}

	@Override
	public void deleteImage(String publicId) throws IOException {
		cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());

	}

	@Override
	public void uploadImages(List<String> listImages, List<MultipartFile> listFiles, String path, String typeUpload)
			throws IOException {
		if (listFiles != null) {
			for (MultipartFile menu : listFiles) {
				if (menu != null) {
					String url = this.uploadImage(menu, path, typeUpload);
					listImages.add(url);
				}
			}
		}
	}

	@Override
	public void removeListImageFromCloudinary(String listMenusDb, String path) throws IOException {

		if (listMenusDb != null && StringUtils.isNotEmpty(listMenusDb)) {
			List<String> listImages = this.objMapper.readValue(listMenusDb, new TypeReference<List<String>>() {
			});

			if (listImages.size() > 0) {
				for (String image : listImages) {
					if (!image.contains(ROOT_NAME) || !image.contains(path))
						continue;

					String[] parts = image.split("/");
					String lastPart = parts[parts.length - 1];
					String idPart = path + lastPart.substring(0, lastPart.lastIndexOf("."));
					this.deleteImage(idPart);
					logger.info(idPart);
				}
			}
		}
	}

	@Override
	public void removeImageFromCloudinary(String image, String path) throws IOException {
		if (image == null || !image.contains(ROOT_NAME) || !image.contains(path))
			return;

		String[] parts = image.split("/");
		String lastPart = parts[parts.length - 1];
		String idPart = path + lastPart.substring(0, lastPart.lastIndexOf("."));
		this.deleteImage(idPart);
	}

	@Override
	public String uploadImage(MultipartFile file, String path, String typeUpload) throws IOException {
		if (file != null) {
			String url = this.uploadFile(file, path, typeUpload);
			return url;
		}
		return null;
	}

}
