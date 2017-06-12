package com.renms.common.cache;

import org.apache.commons.lang.SerializationException;
import org.springframework.data.redis.serializer.RedisSerializer;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public class KryoRedisSerializer<T> implements RedisSerializer<T> {

	private static ThreadLocal<Kryo> kryo = new ThreadLocal<Kryo>() {
		@Override
		protected Kryo initialValue() {
			return new Kryo();
		}
	};

	@Override
	public byte[] serialize(Object t) throws SerializationException {
		if (t == null)
			return null;
		byte[] buffer = new byte[4096];
		Output output = new Output(buffer, -1);
		kryo.get().writeClassAndObject(output, t);
		return output.toBytes();
	}

	@SuppressWarnings("unchecked")
	@Override
	public T deserialize(byte[] bytes) throws SerializationException {
		if (bytes == null)
			return null;
		Input input = new Input(bytes);
		return (T) kryo.get().readClassAndObject(input);
	}

}