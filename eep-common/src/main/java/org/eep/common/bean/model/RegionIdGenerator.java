package org.eep.common.bean.model;

import java.io.Serializable;
import java.text.DecimalFormat;

import org.rubik.bean.core.model.Pair;

/**
 * 最多允许5级行政区划
 * 
 * @author lynn
 */
public class RegionIdGenerator implements Serializable {

	private static final long serialVersionUID = 6151916508617906982L;
	
	public static final int MAX_LEVEL		= 5;
	private static final DecimalFormat FORMAT = new DecimalFormat("000");
	
	private long id;
	private int level;
	
	public RegionIdGenerator() {}
	
	public RegionIdGenerator(long id, int level) {
		this.id = id;
		this.level = level;
	}
	
	/**
	 * 该坐标同级别的下一个坐标值
	 * 
	 * @return
	 */
	public long nextId() {
		String value = String.valueOf(id);
		int stop = value.length() - (MAX_LEVEL - level) * 3;
		int start = level == 1 ? 0 : value.length() - (MAX_LEVEL - level + 1) * 3;
		String slice = value.substring(start, stop);
		StringBuilder builder = new StringBuilder();
		builder.append(value.substring(0, start));
		int seg = Integer.valueOf(slice) + 1;
		builder.append(FORMAT.format(seg));
		int round = level;
		while (round < MAX_LEVEL) {
			builder.append("000");
			round++;
		}
		return Long.valueOf(builder.toString());
	}
	
	/**
	 * 该坐标的坐标取值范围
	 * 
	 * @return
	 */
	public Pair<Long, Long> range() {
		long nextGeo = nextId();
		return new Pair<Long, Long>(id, nextGeo - 1);
	}
	
	/**
	 * 子坐标的第一个坐标值
	 * 
	 * @return
	 */
	public long childFirstId() { 
		if (level == MAX_LEVEL)
			throw new UnsupportedOperationException("MAX_LEVEL id has no child!");
		String value = String.valueOf(id);
		int mark = (MAX_LEVEL - level) * 3;
		int start = value.length() - mark;
		int stop = value.length() - (mark - 3);
		String slice = value.substring(start, stop);
		StringBuilder builder = new StringBuilder();
		builder.append(value.substring(0, start));
		int seg = Integer.valueOf(slice) + 1;
		builder.append(FORMAT.format(seg));
		int round = level + 1;
		while (round < MAX_LEVEL) {
			builder.append("000");
			round++;
		}
		return Long.valueOf(builder.toString());
	}
	
	public static final long initialId() { 
		return 1000000000000l;
	}
}

