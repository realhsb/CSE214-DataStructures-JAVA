package hw214_1;

public class Euclidean {
    protected static <RM extends Ring & Modulo> Ring euclidean(RM a, RM b) {
      //TODO: return a if comp.eq(a, b)
      //      return b if comp.eq(a, a.addIdentity())
      //      return a if comp.eq(b, b.addIdentity())
      //      Otherwise, make a recursive call after mod()
    	if (Comp.eq(a, b)) {
    		return a;
    	}
    	else if (Comp.eq(a, a.addIdentity())) {
    		return b;
    	}else if (Comp.eq(b, b.addIdentity())) {
    		return a;
    	}
    	RM c = a;
    	RM d = b;
    	if (Comp.gt(a, b)) {
    		c = (RM)a.mod(b);
    	}
    	if (Comp.lt(a, b)) {
    		d = (RM)b.mod(a);
    	}
    	return euclidean(c,d);
    }
    public static <RM extends Ring & Modulo> Ring GCD(RM a, RM b) {
        if(Comp.lt(a, a.addIdentity()))     //if a < 0
            a = (RM) a.addInverse();
        if(Comp.lt(b, b.addIdentity()))     //if b < 0
            b = (RM) b.addInverse();
        return euclidean(a, b);
    }
    public static <RM extends Ring & Modulo> Ring LCM(RM a, RM b) {
        Ring gcd = GCD(a, b);
        Ring q = b.quo(gcd);
        return a.mul(q);
    }
}
