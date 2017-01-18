package com.bb;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author dmytrov
 */
@SpringBootApplication
public class ActorBbDemoApplication {

	public static void main(String[] args) {

		AnnotationConfigApplicationContext ctx =
				new AnnotationConfigApplicationContext();
		ctx.register(Configuration.class);
		ctx.refresh();
	}
}
