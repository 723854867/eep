package org.eep.sync;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.rubik.util.callback.NullResultCallback;
import org.rubik.util.common.CollectionUtil;

public class SyncUtil {

	public static final <T> void batchReplace(Collection<T> collection, NullResultCallback<List<T>> callback) { 
		List<T> list = new ArrayList<T>();
		while (!collection.isEmpty()) {
			Iterator<T> iterator = collection.iterator();
			while (iterator.hasNext() && list.size() <= 1000) {
				T object = iterator.next();
				iterator.remove();
				list.add(object);
			}
			if (!CollectionUtil.isEmpty(list)) {
				callback.invoke(list);
				list.clear();
			}
		}
	}
}
