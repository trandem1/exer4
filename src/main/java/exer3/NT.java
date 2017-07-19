package exer3;

import static spark.Spark.*;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import spark.Request;
import spark.Route;


public class NT {
	public static void main(String[] args) {
	    final Cache<Long, String> cache;
		cache = CacheBuilder.newBuilder().maximumSize(100).expireAfterAccess(10, TimeUnit.SECONDS).build();
		Route x = new Route() {
			public Object handle(Request request, spark.Response response) throws Exception {
				String x = request.params(":num");
				long num = Long.parseLong(x);
				if (!cache.asMap().containsKey(num)) {
					ArrayList<Long> nums = new ArrayList<Long>();
					nums.add(2l);
					for (long i = 3; i <= num; i++) {
						int d = 0;
						for (long j = 2; j <= Math.sqrt(i); j++) {
							if (i % j == 0) {
								d++;
								continue;
							}
						}
						if (d == 0) {
							nums.add(i);
						}
					}
					String t = " ";
					for (long y : nums) {
						t = t + " " + y;
					}
					cache.put(num, t);
				}
				return cache.asMap().get(num);
			}	
		};
		get("/nguyento/:num", x);
	}
}