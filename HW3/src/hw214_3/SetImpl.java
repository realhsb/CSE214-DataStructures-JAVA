package hw214_3;

//import booleanAlebra.SetImpl;

public class SetImpl<E extends Comparable<E>> implements Set<E> {
	private CircularlyDblLinkedList<E> list;

	public SetImpl() {
		list = new CircularlyDblLinkedList<E>();
	}

	public SetImpl(SetImpl<E> set) {
		list = set.copyList();
		dedupe();
	}

	public SetImpl(E[] arr) {
		list = new CircularlyDblLinkedList<E>();
		for (int i = 0; i < arr.length; i++)
			list.add(i, arr[i]);
		dedupe();
	}

	// TODO: implement interface Set
	public boolean isEqual(Set<E> set) {
		SetImpl<E> s = (SetImpl<E>) set;
		if (list.size() != s.list.size())
			return false;
		for (int i = 0; i < list.size(); i++)
			if (list.get(i).compareTo(s.list.get(i)) != 0)
				return false;
		return true;
	}

	public Set<E> union(Set<E> set) {
		SetImpl<E> s = (SetImpl<E>) set;
		SetImpl<E> new_s = new SetImpl<E>();
		for (int i = 0; i < list.size(); i++) {
			new_s.list.add(0, list.get(i));
		}
		for (int i = 0; i < s.list.size(); i++) {
			new_s.list.add(0, s.list.get(i));
		}
		new_s.dedupe();
		return new_s;
	}

	public Set<E> intersection(Set<E> set) {
		SetImpl<E> s = (SetImpl<E>) set;
		SetImpl<E> new_s = new SetImpl<E>();
		for (int i = 0; i < list.size(); i++) {
			for(int j = 0; j < s.list.size(); j++) {
				if(list.get(i).compareTo(s.list.get(j)) == 0) {
					new_s.list.add(0, list.get(i));
				}
			}
		}
		new_s.dedupe();
		return new_s;
	}

	public Set<E> difference(Set<E> set) {
		SetImpl<E> s = (SetImpl<E>) set;
		SetImpl<E> new_s = new SetImpl<E>();
		for (int i = 0; i < list.size(); i++) {
			int j = 0;
			for (j = 0; j < s.list.size(); j++) {
				if(list.get(i).compareTo(s.list.get(j)) == 0) {
					break;
				}
				
			}
			if (j == s.list.size()) {
				new_s.list.add(0, list.get(i));
			}
		}
		
		for (int a = 0; a < s.list.size(); a++) {
			for (int b = 0; b < list.size(); b++) {
				if(s.list.get(a).compareTo(list.get(b)) == 0) {
					break;
				}
				if(b == list.size() -1 && s.list.get(a).compareTo(list.get(b)) != 0) {
				new_s.list.add(0, s.list.get(a));
				}
			}
		}
		new_s.dedupe();
		return new_s;
	}

	// helper methods
	private CircularlyDblLinkedList<E> copyList() {
		CircularlyDblLinkedList<E> dst = new CircularlyDblLinkedList<E>();
		int i = 0;
		for (E e : list)
			dst.add(i++, e);
		return dst;
	}

	private void dedupe() {
		// TODO: 1) sort
		// 2) remove any consecutive duplicate elements
		sort();
		for (int i = 0; i < list.size() - 1; i++) {
			for (int j = i + 1; j < list.size();j++) {
				if(list.get(i).compareTo(list.get(j)) == 0) {
					list.remove(j);
					j = i;
				}
			}
		}
	}

	private void sort() { // selection-sort
		// TODO: using the selection-sort, sort list
		int min;
		for (int i = 0; i < list.size() - 1; i++) {
			min = i;
			for (int j = i + 1; j < list.size(); j++) {
				if (list.get(j).compareTo(list.get(min)) < 0) {
					min = j;
				}
			}
			E temp = list.get(min);
			list.set(min, list.get(i));
			list.set(i, temp);
		}
	}

	private static void onFalseThrow(boolean b) {
		if (!b)
			throw new RuntimeException("Error: unexpected");
	}

	public static void main(String[] args) {
		SetImpl<Integer> a = new SetImpl<Integer>(new Integer[] { 1 });
		SetImpl<Integer> b = new SetImpl<Integer>(new Integer[] { 1, 2 });
		SetImpl<Integer> c = new SetImpl<Integer>(new Integer[] { 2, 3 });
		SetImpl<Integer> e = new SetImpl<Integer>();
		SetImpl<Integer> x = new SetImpl<Integer>(new Integer[] { 1, 1, 1 });
		SetImpl<Integer> y = new SetImpl<Integer>(new Integer[] { 1, 2, 1, 2 });
		SetImpl<Integer> z = new SetImpl<Integer>(new Integer[] { 3, 2, 3, 3 });
		SetImpl<Integer> u = new SetImpl<Integer>(new Integer[] { 1, 2, 3 });

		onFalseThrow(x.isEqual(a));
		onFalseThrow(y.isEqual(b));
		onFalseThrow(z.isEqual(c));
		onFalseThrow(y.union(z).isEqual(u));
		onFalseThrow(x.intersection(y).isEqual(x));
		onFalseThrow(x.intersection(z).isEqual(e));
		onFalseThrow(u.difference(z).isEqual(x));
		System.out.println("Success!");
	}
}
