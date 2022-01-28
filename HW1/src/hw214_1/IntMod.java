package hw214_1;

//Integer modulo m (m is a prime number)
// IntMod(7, 5) is 7 in modulo 5 system
// IntMod(7, 5) is equivalent to IntMod(2, 5)
//
public class IntMod implements Field, Ordered {
    private int n, m;
    
    public IntMod(int n, int m) {
        if(m <= 0)
            throw new IllegalArgumentException("Not a positive divisor");
        n = n % m;
        n = n < 0 ? n + m : n; //if n is negative, make it positive
        this.n = n;
        this.m = m;
    }
    public int getInt() {
        return n;
    }
    public int getMod() {
        return m;
    }
    
    //Ring
    public Ring add(Ring a) {
    	IntMod i = (IntMod)a;
    	if(m != i.m) {
    		System.out.println("Error");
    	}
    	return new IntMod(n + i.n, m);
    }
    public Ring addIdentity() {
    	return new IntMod(0, m);
    }
    public Ring addInverse() {
    	return new IntMod(-n, m);
    }
    public Ring mul(Ring a) {
    	IntMod new_a = (IntMod) a;
    	return new IntMod(n*new_a.n, m);
    }
    
    //Ordered
    public boolean ge(Ordered a) {
    	IntMod new_a = (IntMod) a;
    	return (n % m) >= (new_a.n % new_a.m);
    }
    
    //Field
    public Ring mulIdentity() {
    	return new IntMod(1, m);
    }
    public Ring mulInverse() {
    	int i = 1;
    	if(((n * i) % m) == 0) {
    		throw new IllegalArgumentException("야ㅑㅑㅑㅑㄱ");
    	}
    	while((n * i) % m != 1) {
    		i++;
    	}
    	return new IntMod(i, m);
    }
}


















