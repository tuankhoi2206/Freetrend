package ds.program.fvhr.minh.dao;

import java.io.Serializable;
import java.util.List;
/**
 * Interface chỉ bao gồm các public abstract method và constant.
 * 
 * @author Hieu
 *
 * @param <T> - domain entity class
 * @param <PK> - primary key object class
 */
public interface DAO<T, PK extends Serializable> {
	int VERY_HUGE_RESULT=1000000;//~ public static final int
	/**
	 * Query 1 bảng theo điều kiện
	 * @param maxResult số record lớn nhất trả về
	 * @param sql câu lệnh HQL
	 * @param params
	 * @return danh sách object T
	 */
	List<T> query(int maxResult, String sql, Object... params);
	
	/**
	 * Query tất cả record
	 * @param maxResult - số record lớn nhất
	 * @return
	 */
	List<T> queryAll(int maxResult);
	
	/**
	 * Insert 1 record vào database
	 * @param entity - mapping object
	 * @return
	 */
	void insert(T entity);
	
	void update(T entity);
	
	void saveOrUpdate(T entity);
	
	void delete(T entity);
	
	T findByPk(PK pk);
	
	/** Có thể cài đặt thêm các function khác**/
}
