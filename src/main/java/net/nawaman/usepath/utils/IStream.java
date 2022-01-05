package net.nawaman.usepath.utils;

import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The stream type that we will be using in this module.
 * 
 * @param <T>  the data type of the stream.
 */
public interface IStream<T> extends ExtensibleStream<T> {
	
	public static <DATA> IStream<DATA> empty() {
		return new IStream.FromStream<DATA>(Stream.empty());
	}
	
	@SafeVarargs
	public static <DATA> IStream<DATA> forStream(Stream<DATA>... originals) {
		return new IStream.FromStream<DATA>(Stream.of(originals).flatMap(each -> each));
	}
	
	@SafeVarargs
	public static <DATA> IStream<DATA> of(DATA... values) {
		return new IStream.FromStream<DATA>(Stream.of(values));
	}
	
	public static <DATA> IStream<DATA> forCollection(Collection<DATA> values) {
		return new IStream.FromCollection<DATA>(values);
	}
	
	public static <DATA> IStream<DATA> forSupplier(Supplier<Stream<DATA>> supplier) {
		return supplier::get;
	}
	
	public static class FromCollection<DATA> implements IStream<DATA> {
		
		private final Collection<DATA> original;
		
		public FromCollection(Collection<DATA> original) {
			this.original = original;
		}
		
		@Override
		public Stream<DATA> stream() {
			return original.stream();
		}
		
		@Override
		public long count() {
			return original.size();
		}
		
		@Override
		public String toString() {
			return original.toString();
		}
	}
	
	public static class FromStream<DATA> implements IStream<DATA> {
		
		private final Stream<DATA> original;
		
		public FromStream(Stream<DATA> original) {
			this.original = original;
		}
		
		@Override
		public Stream<DATA> stream() {
			return original;
		}
		
		@Override
		public String toString() {
			return toList().toString();
		}
	}
	
	public default List<T> toList() {
		return collect(Collectors.toList());
	}
	
	public default IStream<T> concatWith(Stream<T> tail) {
		if (tail == null) {
			return this;
		}
		
		return IStream.forStream(Stream.concat(this, tail));
	}
	
}
