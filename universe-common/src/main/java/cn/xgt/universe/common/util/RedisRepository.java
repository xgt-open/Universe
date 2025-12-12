package cn.xgt.universe.common.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author XGT
 * @description TODO
 * @date 2025/12/12
 */
@Slf4j
@Component
public class RedisRepository {

	@Autowired
	private StringRedisTemplate template;

	public RedisRepository(StringRedisTemplate template) {
		this.template = template;
	}

	public RedisRepository() {
	}

	public boolean set(String key, String value) {
		try {
			log.info("redis写入内容, key:[ {} ], value:[ {} ].", key, value);
			if (StringUtils.isNotBlank(key) && StringUtils.isNotBlank(value)) {
				this.template.opsForValue().set(key, value);
			}

			return true;
		} catch (Exception var4) {
			log.error("redis写入失败", var4);
			return false;
		}
	}

	public boolean set(String key, String value, long expiryTime) {
		return this.set(key, value, expiryTime, TimeUnit.SECONDS);
	}

	public boolean set(String key, String value, long timeout, TimeUnit unit) {
		try {
			log.info("redis写入内容, key:[ {} ], value:[ {} ], expiryTime:[ {} ], TimeUnit:[ {} ].", new Object[]{key, value, timeout, unit.toString()});
			if (StringUtils.isNotBlank(key) && StringUtils.isNotBlank(value)) {
				this.template.opsForValue().set(key, value, timeout, unit);
				return true;
			}
		} catch (Exception var7) {
			log.error("redis写入失败", var7);
		}

		return false;
	}

	public boolean setIfAbsent(String key, String value, long timeout, TimeUnit unit) {
		try {
			log.info("redis写入内容, key:[ {} ], value:[ {} ], expiryTime:[ {} ], TimeUnit:[ {} ].", new Object[]{key, value, timeout, unit.toString()});
			if (StringUtils.isNotBlank(key) && StringUtils.isNotBlank(value)) {
				return this.template.opsForValue().setIfAbsent(key, value, timeout, unit);
			}
		} catch (Exception var7) {
			log.error("redis写入失败", var7);
		}

		return false;
	}

	public String get(String key) {
		log.info("获取redis数据, key:[ {} ].", key);
		if (StringUtils.isBlank(key)) {
			log.info("获取redis数据失败, KEY不能为空.");
			throw new RuntimeException("获取redis数据失败, KEY不能为空.");
		} else {
			return (String)this.template.opsForValue().get(key);
		}
	}

	public Integer getInteger(String key) {
		log.info("获取redis数据(Integer), key:[ {} ].", key);
		String data = this.get(key);
		if (StringUtils.isNotBlank(data)) {
			if (StringUtils.isNumeric(data)) {
				return Integer.valueOf(data);
			} else {
				throw new RuntimeException("redis存储数据为非数值型数据");
			}
		} else {
			return null;
		}
	}

	public boolean hasKey(String key) {
		log.info("查询redis是否存在key:[ {} ].", key);
		if (StringUtils.isBlank(key)) {
			log.info("查询redis是否存在key失败, key不能为空.");
			throw new RuntimeException("查询redis是否存在key失败, key不能为空.");
		} else {
			return this.template.hasKey(key);
		}
	}

	public Long increment(String key, int value) {
		log.info("增量redis数据, key:[ {} ], value:[ {} ].", key, value);
		if (StringUtils.isBlank(key)) {
			log.info("增量redis数据失败, KEY不能为空.");
			throw new RuntimeException("增量redis数据失败, KEY不能为空.");
		} else {
			return this.template.opsForValue().increment(key, (long)value);
		}
	}

	public boolean addSet(String key, Set<String> set) {
		try {
			log.info("写入redis set数据, key:[ {} ].", key);
			if (StringUtils.isBlank(key)) {
				log.info("写入redis set数据失败, KEY不能为空.");
			} else {
				if (!CollectionUtils.isEmpty(set)) {
					this.template.opsForSet().add(key, set.toArray(new String[set.size()]));
					return true;
				}

				log.info("写入redis set数据失败, set不能为空.");
			}
		} catch (Exception var4) {
			log.error("写入redis set数据失败", var4);
		}

		return false;
	}

	public boolean updateSet(String key, Set<String> set) {
		try {
			this.deleteKey(key);
			log.info("写入redis set数据, key:[ {} ].", key);
			if (StringUtils.isBlank(key)) {
				log.info("写入redis set数据失败, KEY不能为空.");
			} else {
				if (!CollectionUtils.isEmpty(set)) {
					this.template.opsForSet().add(key, set.toArray(new String[set.size()]));
					return true;
				}

				log.info("写入redis set数据失败, set不能为空.");
			}
		} catch (Exception var4) {
			log.error("写入redis set数据失败", var4);
		}

		return false;
	}

	public boolean setSet(String key, String... values) {
		try {
			log.info("写入redis set数据, key:[ {} ], values:[ {} ].", key, JSONUtil.toJsonStr(values));
			if (StringUtils.isBlank(key)) {
				log.info("写入redis set数据失败, KEY不能为空.");
			} else {
				if (values != null && values.length != 0) {
					this.template.opsForSet().add(key, values);
					return true;
				}

				log.info("写入redis set数据失败, 数据不能为空.");
			}
		} catch (Exception var4) {
			log.error("写入redis set数据失败", var4);
		}

		return false;
	}

	public Boolean isMember(String key, Object o) {
		log.info("查看集合中是否存在指定数据, key:[ {} ], o:[ {} ].", key, o.toString());
		if (StringUtils.isBlank(key)) {
			log.info("查看集合中是否存在指定数据失败, key不能为空.");
			throw new RuntimeException("查看集合中是否存在指定数据失败, key不能为空.");
		} else {
			return this.template.opsForSet().isMember(key, o);
		}
	}

	public Boolean expire(String key, long timeout) {
		return this.expire(key, timeout, TimeUnit.SECONDS);
	}

	public Boolean expire(String key, long timeout, TimeUnit unit) {
		log.info("设置redis数据过期时间, key:[ {} ], timeout:[ {} ], TimeUnit:[ {} ].", new Object[]{key, timeout, unit.toString()});
		if (StringUtils.isBlank(key)) {
			log.info("设置redis数据过期时间失败, key不能为空.");
			throw new RuntimeException("设置redis数据过期时间失败, key不能为空.");
		} else {
			return this.template.expire(key, timeout, unit);
		}
	}

	public Long getExpiry(String key) {
		log.info("获取redis数据过期时间, key:[ {} ].", key);
		if (StringUtils.isBlank(key)) {
			log.info("获取redis数据过期时间失败, key不能为空.");
			throw new RuntimeException("获取redis数据过期时间失败, key不能为空.");
		} else {
			return this.template.getExpire(key);
		}
	}

	public void deleteKey(String key) {
		log.info("删除redis数据, key:[ {} ].", key);
		if (StringUtils.isBlank(key)) {
			log.info("删除redis数据失败, KEY不能为空.");
			throw new RuntimeException("删除redis数据失败, KEY不能为空.");
		} else {
			this.template.delete(key);
		}
	}

	public void deleteKeyPrefix(String keyPrefix) {
		log.info("删除redis数据, keyPrefix:[ {} ].", keyPrefix);
		if (StringUtils.isBlank(keyPrefix)) {
			log.info("删除redis数据失败, keyPrefix不能为空.");
			throw new RuntimeException("删除redis数据失败, keyPrefix不能为空.");
		} else {
			Set keySet;
			if (keyPrefix.endsWith("*")) {
				keySet = this.template.keys(keyPrefix);
			} else {
				keySet = this.template.keys(keyPrefix + "*");
			}

			this.template.delete(keySet);
		}
	}

	public boolean update(String key, String value) {
		log.info("更新redis数据, key:[ {} ]", key);
		return this.hasKey(key) ? this.set(key, value, this.getExpiry(value), TimeUnit.SECONDS) : this.set(key, value);
	}
}
