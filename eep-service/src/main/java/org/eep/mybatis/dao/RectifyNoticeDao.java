package org.eep.mybatis.dao;

import java.util.List;

import org.eep.common.bean.entity.RectifyNotice;
import org.eep.common.bean.model.RectifyNoticeInfo;
import org.eep.common.bean.param.RectifyNoticesParam;
import org.rubik.mybatis.extension.Dao;

public interface RectifyNoticeDao extends Dao<Long, RectifyNotice> {

	List<RectifyNoticeInfo> list(RectifyNoticesParam param);
}
