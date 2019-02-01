package com.yaoguang.greendao.biz;


import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhongjh on 2017/3/31.
 * 数据库基类
 *
 * @param <T> 实体
 * @param <K> 主键类型，设置Long
 */
public abstract class BaseBiz<T, K> {

    private AbstractDao<T, K> mDao;

    public BaseBiz(AbstractDao dao) {
        mDao = dao;
    }

    /* *
     * 添加
     *
     * @param main_two_item_head 实体类
     * @return
     */
    public long save(T item) {
        return mDao.insert(item);
    }

    /* *
     * 添加 批量
     *
     * @param items 数组(实体类)
     */
    @SafeVarargs
    public final void save(T... items) {
        mDao.insertInTx(items);
    }

    /* *
     * 添加 批量
     *
     * @param items 集合(实体类)
     */
    public void save(List<T> items) {
        mDao.insertInTx(items);
    }

    /* *
       * 添加或更新
     *
     * @param main_two_item_head 实体类
     * @return 返回添加的id
     */
    public long saveOrUpdate(T item) {
        return mDao.insertOrReplace(item);
    }

    /* *
     * 添加或更新 批量
     *
     * @param items 集合(实体类)
     */
    public void saveOrUpdate(List<T> items) {
        mDao.insertOrReplaceInTx(items);
    }

    /* *
     * 删除
     *
     * @param id id
     */
    public void deleteByKey(K id) {
        mDao.deleteByKey(id);
    }

    /* *
     * 删除
     *
     * @param main_two_item_head 实体类
     */
    public void delete(T item) {
        mDao.delete(item);
    }

    /* *
     * 删除 批量
     *
     * @param items 数组(实体类)
     */
    @SafeVarargs
    public final void delete(T... items) {
        mDao.deleteInTx(items);
    }

    /* *
     * 删除 批量
     *
     * @param items 数组(实体类)
     */
    public void delete(List<T> items) {
        mDao.deleteInTx(items);
    }

    /* *
     * 删除 全部
     */
    public void deleteAll() {
        mDao.deleteAll();
    }

    /* *
     * 更新
     *
     * @param main_two_item_head 实体类
     */
    public void update(T item) {
        mDao.update(item);
    }

    /* *
     * 更新 批量
     *
     * @param items 数组(实体类)
     */
    public void update(T... items) {
        mDao.updateInTx(items);
    }

    /* *
     * 更新 批量
     *
     * @param items 数组(实体类)
     */
    public void update(List<T> items) {
        mDao.updateInTx(items);
    }

    /* *
     * 查询 单
     *
     * @param id id
     * @return 实体类
     */
    public T query(K id) {
        return mDao.load(id);
    }

    /* *
     * 查询 全部
     *
     * @return 集合
     */
    public List<T> queryAll() {
        return mDao.loadAll();
    }

    /* *
     *
     * @param where  where语句，包括'where'  查询的格式: where begin_date_time >= ? AND end_date_time<=?
     * @param params 查询参数
     * @return 集合
     */
    public List<T> queryRaw(String where, String... params) {
        List<T> list = mDao.queryRaw(where, params);
        //这个封装包有问题，如果size为0的话，进行添加删除有问题的
        if (list.size() <= 0) {
            list = new ArrayList<>();
            return list;
        } else {
            return mDao.queryRaw(where, params);
        }
    }

    /* *
     * QueryBuilder可以帮助你构建自定义的查询语句，而不使用SQL的情况。
     * 并不是每个人都喜欢书写SQL语句，当然很容易就会出一些错，这些错误只有在运行的时候才会被发现。
     * 而QueryBuilder很容易使用，节省了你书写SQL语句的时间。当然，由于语法的检验是在编译时才执行，所以在查询语句中发现bug是很困难的。
     * QueryBuilder的编译时间会检验属性的引用，这样能够在greenDao后面，通过代码生成的方法发现bug。
     * <p/>
     * 范例1：查找所有以“Joe”为first name 的用户，并以last name排序：
     * List joes = userDao.queryBuilder() .where(Properties.FirstName.eq("Joe")) .orderAsc(Properties.LastName) .list();
     * <p/>
     * 范例2：获取id为cityId并且infotype为HBContant.CITYINFO_SL的数据集合：
     * QueryBuilder<CityInfoDB> qb = cityInfoDao.queryBuilder();
     * qb.where(qb.and(Properties.CityId.eq(cityId),Properties.InfoType.eq(HBContant.CITYINFO_SL)));
     * qb.orderAsc(Properties.Id);// 排序依据
     * return qb.list();
     * <p/>
     * 嵌套情况：
     * 范例3：获取用户名字为“Joe”并且在1970年10月之后出生的用户
     * 这里要说明下：user 的birthday对于year，month，和day是一个分离的属性。我们可以以一种更正常的方式表达这种条件：
     * QueryBuilder qb = userDao.queryBuilder();
     * qb.where(Properties.FirstName.eq("Joe"),
     * qb.or(Properties.YearOfBirth.gt(1970),
     * qb.and(Properties.YearOfBirth.eq(1970), Properties.MonthOfBirth.ge(10))));
     * List youngJoes = qb.list();
     *
     * gt代表>
     * lt代表<
     *
     * @return
     */
    public QueryBuilder<T> queryBuilder() {
        return mDao.queryBuilder();
    }

    /* *
     * 查询
     *
     * @return 个数
     */
    public long count() {
        return mDao.count();
    }

    /* *
     * 重置所有局部改变属性的实体从数据库重新加载值。
     *
     * @param main_two_item_head 实体
     */
    public void refresh(T item) {
        mDao.refresh(item);
    }

    /* *
     * 分离一个实体的身份范围(会话)。随后的查询结果不会返回这个对象。
     *
     * @param main_two_item_head 实体
     */
    public void detach(T item) {
        mDao.detach(item);
    }

    /* *
     * 插入给定的实体在数据库中使用一个事务。
     *
     * @param entities
     */
    public void updateInTx(Iterable<T> entities) {
        mDao.updateInTx(entities);
    }

    /* *
     * 插入给定的实体在数据库中使用一个事务。
     *
     * @param entities
     */
    @SafeVarargs
    public final void updateInTx(T... entities) {
        mDao.updateInTx(entities);
    }


}
