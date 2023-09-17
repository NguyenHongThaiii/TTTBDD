package ecommerce.mobile.utils;

import org.modelmapper.ModelMapper;

public class MapperUtils {
	private static final ModelMapper mapper = new ModelMapper();

	public static <T, U> T mapToDTO(U entity, Class<T> dtoClass) {
		return mapper.map(entity, dtoClass);
	}

	public static <T, U> U mapToEntity(T dto, Class<U> entityClass) {
		return mapper.map(dto, entityClass);
	}

	public static <T, U> void mapToEntity(T dto, U entity) {
		mapper.map(dto, entity);
	}
}