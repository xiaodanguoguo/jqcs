package jq.steel.cs.services.base.facade.util;

import jq.steel.cs.services.base.facade.common.SysPramType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @Auther: wangyu
 */
public class MapperKeepUtil {

    private static Logger LOG = LoggerFactory.getLogger(MapperKeepUtil.class);

    /**
     * 公用的 方法 就单表
     * @param list 要进行 删除,修改,添加的 结果集
     * @param mapper maping 对象
     * @param <T>
     * @return 成功 or 失败
     */
    public static <T> Boolean commonKeep(List<T> list, Object mapper){

        //加一些公共的校验






        Class<?> aClass = mapper.getClass();
        try {
            if((aClass.getMethod("deleteByPrimaryKey") == null) || (aClass.getMethod("updateByPrimaryKeySelective") == null)
                    || (aClass.getMethod("insertSelective") == null)){
                LOG.info("mapper 映射不存在");
                return false;
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        for(T t:list){
            try {
                Field opt = t.getClass().getDeclaredField("opt");
                if(opt == null){
                    return false;
                }
                opt.setAccessible(true);

                Object o = opt.get(t);
                if(o == null && o.toString().trim().equals("")){
                    LOG.info("opt 值不存在");
                    return false;
                }
                if(SysPramType.DELETE.getMsg().equals(o.toString())){

                    Method deleteByPrimaryKey = aClass.getMethod("deleteByPrimaryKey", Long.class);


                    Field field = t.getClass().getDeclaredFields()[0]; //取id 不知道值
                    field.setAccessible(true);

                    deleteByPrimaryKey.invoke(mapper,field.get(t));

                }else if(SysPramType.UPDATE.getMsg().equals(o.toString())){

                    Method updateByPrimaryKeySelective = aClass.getMethod("updateByPrimaryKeySelective",t.getClass());

                    updateByPrimaryKeySelective.invoke(mapper,t);

                }else if(SysPramType.INSERT.getMsg().equals(o.toString())){

                    Method insertSelective = aClass.getMethod("insertSelective",t.getClass());

                    insertSelective.invoke(mapper,t);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        return true;

    }
}
