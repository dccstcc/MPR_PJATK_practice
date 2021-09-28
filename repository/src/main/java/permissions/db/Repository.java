package permissions.db;

import java.util.List;

public interface Repository<TEntity> {

	public TEntity withId(int id);
	public List<TEntity> allOnPage(PagingInfo page);
	public void add(TEntity entity);
	public void modify(TEntity entity);
	public void remove(TEntity entity);
}
