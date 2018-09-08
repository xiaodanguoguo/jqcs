package com.ebase.core.spel;

import org.junit.Test;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

public class SpelTest {

	@Test
	public void spel() {
		ExpressionParser parser = new SpelExpressionParser();
		EvaluationContext context = new StandardEvaluationContext();
		context.setVariable("end", "!");
		A a = new A();
		a.setId("1");
		a.setName("123");
		B b = new B();
		b.setAge(123);
		a.setB(b);
		context.setVariable("a", null);
		
		context.setVariable("b", "123");
		context.setVariable("c", "456");
		
		Expression expression = parser.parseExpression("('Hello' + ' World').concat(#end)");
		System.out.println(expression.getValue(context,String.class));
		
		Expression expression1 = parser.parseExpression("#a?.b?.age");
		System.out.println(expression1.getValue(context,Integer.class));
		
		Expression expression2 = parser.parseExpression("#b");
		System.out.println(expression2.getValue(context,String.class));
	}

}
