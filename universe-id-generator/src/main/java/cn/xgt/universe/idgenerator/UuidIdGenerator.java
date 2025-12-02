package cn.xgt.universe.idgenerator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

/**
 * @author XGT
 * @description 基于UUID的ID生成器实现
 * @date 2025/11/5
 */
public class UuidIdGenerator implements IdGenerator {

	private static final Logger logger = LoggerFactory.getLogger(UuidIdGenerator.class);

	@Override
	public Long nextId(String key) {
		// UUID 无法转换为 Long，返回 null 或抛出异常
		throw new UnsupportedOperationException("UUID generator does not support Long type ID");
	}

	@Override
	public Long nextId() {
		throw new UnsupportedOperationException("UUID generator does not support Long type ID");
	}

	@Override
	public String nextIdString(String key) {
		throw new UnsupportedOperationException("UUID generator does not support key param");
	}

	@Override
	public String nextIdString() {
		String uuid = UUID.randomUUID().toString().replace("-", "");
		logger.debug("生成UUID: uuid={}", uuid);
		return uuid;
	}

	@Override
	public String nextIdWithPrefix(String key, String prefix) {
		throw new UnsupportedOperationException("UUID generator does not support key param");
	}

	@Override
	public String nextIdWithPrefix(String prefix) {
		String uuid = nextIdString();
		return prefix + "_" + uuid;
	}

	@Override
	public String nextIdWithPrefixAndLength(String key, String prefix, int length) {
		throw new UnsupportedOperationException("UUID generator does not support key param");
	}
}
