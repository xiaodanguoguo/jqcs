package com.ebase.core.validator.aop;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.validation.Constraint;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.groups.Default;

import com.ebase.core.validator.annotation.Vali;
import com.ebase.core.validator.vali.api.FrameValidator;
import com.ebase.core.validator.vali.api.ValidatorContext;
import com.ebase.core.validator.method.MethodParamNameContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;

import com.alibaba.dubbo.common.bytecode.ClassGenerator;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtNewConstructor;
import javassist.Modifier;
import javassist.NotFoundException;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ClassFile;
import javassist.bytecode.ConstPool;
import javassist.bytecode.annotation.ArrayMemberValue;
import javassist.bytecode.annotation.BooleanMemberValue;
import javassist.bytecode.annotation.ByteMemberValue;
import javassist.bytecode.annotation.CharMemberValue;
import javassist.bytecode.annotation.ClassMemberValue;
import javassist.bytecode.annotation.DoubleMemberValue;
import javassist.bytecode.annotation.EnumMemberValue;
import javassist.bytecode.annotation.FloatMemberValue;
import javassist.bytecode.annotation.IntegerMemberValue;
import javassist.bytecode.annotation.LongMemberValue;
import javassist.bytecode.annotation.MemberValue;
import javassist.bytecode.annotation.ShortMemberValue;
import javassist.bytecode.annotation.StringMemberValue;

public class ValidatorUtil {

	private final static Logger logger = LoggerFactory.getLogger(ValidatorUtil.class);

	private static Map<String, List<FrameValidator>> valis = new ConcurrentHashMap<>();

	private static Validator validator;

	static {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	/**
	 * 校验
	 * 
	 * @param clazz
	 * @param method
	 * @param parameterTypes
	 * @param arguments
	 * @return
	 */
	public static String doValidate(Class<?> clazz, Method method, Class<?>[] parameterTypes, Object[] arguments)
			throws Exception {
		// 注解标签
		Vali a = method.getAnnotation(Vali.class);
		if (a == null) {
			a = clazz.getAnnotation(Vali.class);
		}
		
		//若是注解为空，则直接返回
		if(a==null){
			return null;
		}

		Set<ConstraintViolation<?>> results = null;
		if (a.useAnnotation()) {
			results = validateAnno(clazz, method, parameterTypes, arguments);
		}

		ValidatorContext context = null;
		// 校验类的使用
		if (a.value() != null && a.value().length > 0) {
			context = new ValidatorContext(method, arguments);
			String methodNameKey = MethodParamNameContext.getMethodKey(method);
			List<FrameValidator> validateList = valis.get(methodNameKey);
			if (validateList == null) {
				validateList = new ArrayList<>();
				Class<? extends FrameValidator>[] clazzs = a.value();
				for (Class<? extends FrameValidator> vaClazz : clazzs) {
					FrameValidator va = vaClazz.newInstance();
					va.init();
					validateList.add(va);
				}
			}

			Method vaMethod = ReflectionUtils.findMethod(FrameValidator.class, "validate",
					new Class<?>[] { ValidatorContext.class });

			for (FrameValidator frameValidator : validateList) {
				// 执行校验方法
				ReflectionUtils.invokeMethod(vaMethod, frameValidator, new Object[] { context });
			}
		}

		StringBuilder sb = new StringBuilder();

		if (results != null && results.size() > 0) {
			for (ConstraintViolation<?> constraintViolation : results) {
				sb.append(constraintViolation.getPropertyPath() + constraintViolation.getMessage()).append("\n");
			}
		}
		if (context != null && context.size() > 0) {
			Iterator<String> it = context.iterator();
			while (it.hasNext()) {
				sb.append(it.next()).append("\n");
			}
		}
		return sb.toString();
	}

	private static Set<ConstraintViolation<?>> validateAnno(Class<?> clazz, Method method, Class<?>[] parameterTypes,
			Object[] arguments) {
		Set<ConstraintViolation<?>> violations = new HashSet<ConstraintViolation<?>>();
		Object parameterBean = getMethodParameterBean(clazz, method, arguments);
		if (parameterBean != null) {
			Set<ConstraintViolation<Object>> vilos = validator.validate(parameterBean, Default.class);
			violations.addAll(vilos);
		}
		for (Object arg : arguments) {
			validate(violations, arg);
		}
		return violations;
	}

	private static void validate(Set<ConstraintViolation<?>> violations, Object arg) {
		if (arg != null && !isPrimitives(arg.getClass())) {
			if (Object[].class.isInstance(arg)) {
				for (Object item : (Object[]) arg) {
					validate(violations, item);
				}
			} else if (Collection.class.isInstance(arg)) {
				for (Object item : (Collection<?>) arg) {
					validate(violations, item);
				}
			} else if (Map.class.isInstance(arg)) {
				for (Map.Entry<?, ?> entry : ((Map<?, ?>) arg).entrySet()) {
					validate(violations, entry.getKey());
					validate(violations, entry.getValue());
				}
			} else {
				violations.addAll(validator.validate(arg, Default.class));
			}
		}
	}

	private static Object getMethodParameterBean(Class<?> clazz, Method method, Object[] args) {
		if (!hasConstraintParameter(method)) {
			return null;
		}
		try {
			String paramClassName = paramClassName(method, clazz);		
			Class<?> paramClass;
			try {
				paramClass = Class.forName(paramClassName, false,
						Thread.currentThread().getContextClassLoader());
			} catch (ClassNotFoundException e) {
				//创建类对象
				paramClass = createParamClass(paramClassName,clazz,method);
			}
			//初始化类对象
			Object parameterBean = paramClass.newInstance();
			
			//为该对象赋值
			String[] methodParamNames = MethodParamNameContext.getMethodParamNames(method);
			for (int i = 0; i < args.length; i++) {
				Field field = paramClass.getField(methodParamNames[i]);
				field.set(parameterBean, args[i]);
			}
			return parameterBean;
		} catch (Throwable e) {
			logger.warn(e.getMessage(), e);
			return null;
		}
	}
	
	private static Class<?> createParamClass(String paramClassName,Class<?> clazz, Method method){
		try {
			String[] methodParamNames = MethodParamNameContext.getMethodParamNames(method);
			
			// 获取 pool
			ClassPool pool = ClassGenerator.getClassPool(clazz.getClassLoader());
			// 创建参数类
			CtClass ctClass = pool.makeClass(paramClassName);

			// 获取创建的参数对象File
			ClassFile classFile = ctClass.getClassFile();

			// 设置java版本是 java7
			classFile.setMajorVersion(ClassFile.JAVA_7);

			// 设置默认的构造函数
			ctClass.addConstructor(CtNewConstructor.defaultConstructor(pool.getCtClass(paramClassName)));

			// 获取要 target方法的所有参数
			Class<?>[] parameterTypes = method.getParameterTypes();

			// 获取要target方法的所有注解，二维数组
			Annotation[][] parameterAnnotations = method.getParameterAnnotations();

			// 循环所有的参数，参数装载到创建的类中
			for (int i = 0; i < parameterTypes.length; i++) {

				// 获取field属性
				Class<?> type = parameterTypes[i];

				// 获取 field对应的注解
				Annotation[] annotations = parameterAnnotations[i];

				// 创建注解对象
				AnnotationsAttribute attribute = new AnnotationsAttribute(classFile.getConstPool(),
						AnnotationsAttribute.visibleTag);

				// 循环 创建attribute具体的值
				for (Annotation annotation : annotations) {
					if (annotation.annotationType().isAnnotationPresent(Constraint.class)) {
						javassist.bytecode.annotation.Annotation ja = new javassist.bytecode.annotation.Annotation(
								classFile.getConstPool(), pool.getCtClass(annotation.annotationType().getName()));
						Method[] members = annotation.annotationType().getMethods();
						for (Method member : members) {
							if (Modifier.isPublic(member.getModifiers()) && member.getParameterTypes().length == 0
									&& member.getDeclaringClass() == annotation.annotationType()) {
								Object value = member.invoke(annotation, new Object[0]);
								if (value != null) {
									MemberValue memberValue = createMemberValue(classFile.getConstPool(),
											pool.get(member.getReturnType().getName()), value);
									ja.addMemberValue(member.getName(), memberValue);
								}
							}
						}
						attribute.addAnnotation(ja);
					}
				}
				String fieldName = methodParamNames[i];

				// 创建field对象
				CtField ctField = CtField.make("public " + type.getCanonicalName() + " " + fieldName + ";",
						pool.getCtClass(paramClassName));
				
				// 添加注解到filed对象上
				ctField.getFieldInfo().addAttribute(attribute);

				// 将field添加到类上
				ctClass.addField(ctField);
			}
			return ctClass.toClass();
		} catch (Exception e) {
			throw new RuntimeException("创建校验类失败:"+paramClassName, e);
		}
	}

	/**
	 * 获取method的param类
	 * 
	 * @param methodName
	 * @return
	 */
	private static String paramClassName(Method method, Class<?> clazz) {
		StringBuilder strBu = new StringBuilder();
		strBu.append(clazz.getName()).append("$").append(toUpperMethoName(method.getName())).append("Praram");
		Class<?>[] parameterTypes = method.getParameterTypes();
		if (parameterTypes != null && parameterTypes.length > 0) {
			for (Class<?> z : parameterTypes) {
				strBu.append(z.getSimpleName());
			}
		}
		return strBu.toString();
	}

	private static String toUpperMethoName(String methodName) {
		return methodName.substring(0, 1).toUpperCase() + methodName.substring(1);
	}

	private static boolean hasConstraintParameter(Method method) {
		Annotation[][] parameterAnnotations = method.getParameterAnnotations();
		if (parameterAnnotations != null && parameterAnnotations.length > 0) {
			for (Annotation[] annotations : parameterAnnotations) {
				for (Annotation annotation : annotations) {
					if (annotation.annotationType().isAnnotationPresent(Constraint.class)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * 是否原始类型
	 * 
	 * @param cls
	 * @return
	 */
	private static boolean isPrimitives(Class<?> cls) {
		if (cls.isArray()) {
			return isPrimitive(cls.getComponentType());
		}
		return isPrimitive(cls);
	}

	/**
	 * 是否原始数据类型
	 * 
	 * @param cls
	 * @return
	 */
	private static boolean isPrimitive(Class<?> cls) {
		return cls.isPrimitive() || cls == String.class || cls == Boolean.class || cls == Character.class
				|| Number.class.isAssignableFrom(cls) || Date.class.isAssignableFrom(cls);
	}

	// Copy from
	// javassist.bytecode.annotation.Annotation.createMemberValue(ConstPool,
	// CtClass);
	private static MemberValue createMemberValue(ConstPool cp, CtClass type, Object value) throws NotFoundException {
		MemberValue memberValue = javassist.bytecode.annotation.Annotation.createMemberValue(cp, type);
		if (memberValue instanceof BooleanMemberValue)
			((BooleanMemberValue) memberValue).setValue((Boolean) value);
		else if (memberValue instanceof ByteMemberValue)
			((ByteMemberValue) memberValue).setValue((Byte) value);
		else if (memberValue instanceof CharMemberValue)
			((CharMemberValue) memberValue).setValue((Character) value);
		else if (memberValue instanceof ShortMemberValue)
			((ShortMemberValue) memberValue).setValue((Short) value);
		else if (memberValue instanceof IntegerMemberValue)
			((IntegerMemberValue) memberValue).setValue((Integer) value);
		else if (memberValue instanceof LongMemberValue)
			((LongMemberValue) memberValue).setValue((Long) value);
		else if (memberValue instanceof FloatMemberValue)
			((FloatMemberValue) memberValue).setValue((Float) value);
		else if (memberValue instanceof DoubleMemberValue)
			((DoubleMemberValue) memberValue).setValue((Double) value);
		else if (memberValue instanceof ClassMemberValue)
			((ClassMemberValue) memberValue).setValue(((Class<?>) value).getName());
		else if (memberValue instanceof StringMemberValue)
			((StringMemberValue) memberValue).setValue((String) value);
		else if (memberValue instanceof EnumMemberValue)
			((EnumMemberValue) memberValue).setValue(((Enum<?>) value).name());
		/* else if (memberValue instanceof AnnotationMemberValue) */
		else if (memberValue instanceof ArrayMemberValue) {
			CtClass arrayType = type.getComponentType();
			int len = Array.getLength(value);
			MemberValue[] members = new MemberValue[len];
			for (int i = 0; i < len; i++) {
				members[i] = createMemberValue(cp, arrayType, Array.get(value, i));
			}
			((ArrayMemberValue) memberValue).setValue(members);
		}
		return memberValue;
	}

}
