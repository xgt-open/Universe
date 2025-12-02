package cn.xgt.universe.idgenerator;

/**
 * @author XGT
 * @description TODO分布式ID生成器接口
 * @date 2025/11/4
 */
public interface IdGenerator {

	/**
	 * 生成下一个ID
	 *
	 * @param key 业务键，用于区分不同业务的ID序列
	 * @return 生成的ID
	 */
	Long nextId(String key);

	/**
	 * 生成下一个ID（使用默认key）
	 *
	 * @return 生成的ID
	 */
	Long nextId();

	/**
	 * 生成下一个ID字符串（支持UUID等字符串类型）
	 *
	 * @param key 业务键
	 * @return 生成的ID字符串
	 */
	String nextIdString(String key);

	/**
	 * 生成下一个ID字符串（使用默认key）
	 *
	 * @return 生成的ID字符串
	 */
	String nextIdString();

	/**
	 * 生成带前缀的ID字符串
	 *
	 * @param key 业务键
	 * @param prefix 前缀
	 * @return 格式化的ID字符串，如：ORDER_10001
	 */
	String nextIdWithPrefix(String key, String prefix);

	/**
	 * 生成带前缀的ID字符串（使用默认key）
	 *
	 * @param prefix 前缀
	 * @return 格式化的ID字符串
	 */
	String nextIdWithPrefix(String prefix);

	/**
	 * 生成固定长度的ID字符串（前面补0）
	 *
	 * @param key 业务键
	 * @param prefix 前缀
	 * @param length ID数字部分长度
	 * @return 格式化的ID字符串，如：ORDER_00001
	 */
	String nextIdWithPrefixAndLength(String key, String prefix, int length);
}
