package cn.xgt.universe.idgenerator;

/**
 * @author XGT
 * @description ID生成器配置属性
 * @date 2025/11/4
 */

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "universe.id-generator")
public class IdGeneratorProperties {

	/**
	 * Redis key前缀，默认：id_generator:
	 */
	private String keyPrefix = "id_generator";

	/**
	 * 默认业务键
	 */
	private String defaultKey = "id_generator_default";

	/**
	 * Redis key过期时间（秒），0表示永不过期，默认：0
	 */
	private long expireTime = 0;

	/**
	 * 是否启用ID生成器，默认：true
	 */
	private boolean enabled = true;
}
