package syneren.sietech.registration.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Util {

	public static String passwordGenerate(String string){
		return org.jboss.security.auth.spi.Util.createPasswordHash("SHA-256", "BASE64", null, null,string);
	}

	public static <T> List<T> castList(Class<? extends T> clazz, Collection<?> c)throws ClassCastException{
		List<T> r = new ArrayList<T>(c.size());
		for (Object o : c) {
			r.add(clazz.cast(o));
		}
		return r;
	}
}
