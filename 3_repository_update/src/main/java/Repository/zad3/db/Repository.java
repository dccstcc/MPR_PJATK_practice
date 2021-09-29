package Repository.zad3.db;

public interface Repository<TEntity_1, TEntity_2> {

	public TEntity_1 withId(int id);
	public TEntity_2 allOnPage(TEntity_1 entity_1, PagingInfo page);
	public void add(TEntity_1 entity_1, TEntity_2 entity_2);
	public void modify(TEntity_1 entity_1, TEntity_2 entity_2);
	public void remove(TEntity_1 entity_1);
}
