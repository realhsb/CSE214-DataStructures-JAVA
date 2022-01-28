package hw214_3;

import java.util.Iterator;

public class CircularlyDblLinkedList<E> implements List<E>, Iterable<E> {
    protected static class Node<E> {
        public E e;
        public Node<E> prev, next;
        public Node() { 
            this.e = null; this.prev = this; this.next = this;
        }
        public Node(E e, Node<E> prev, Node<E> next) {
            this.e = e; this.prev = prev; this.next = next;
        }
    }
    public static class NodeIterator<E> implements Iterator<E> {
        private Node<E> head, curr;
        public NodeIterator(Node<E> head) {
            this.head = head; this.curr = head;
        }
        //TODO: implement Iterator<E>
        public boolean hasNext() {
        	return curr.next != null;
        }
        public E next() {
        	return curr.next.e;
        }
    }
    
    protected Node<E> head;
    protected int size;

    //constructor
    public CircularlyDblLinkedList() {
        head = new Node<E>();
        size = 0;
    }
    
    //TODO: implement interface List
    public E get(int i) {
        return findNode(i).e;
    }

    public E set(int i, E e) {
    	E old = findNode(i).e;
    	findNode(i).e = e;
    	return old;
    }
    
    public int size() {
    	return this.size;
    }
    
    public boolean isEmpty() {
    	return this.size == 0;
    }
    
    public E remove(int i) {
    	E new_e = findNode(i).e;
    	if(isEmpty()) {
    		System.out.println("There is no element.");
    	}else if(size == 1) {
    		head = null;
    	}else if(size == 2) {
    		if(i == 0) {
    			findNode(i+1).prev = null;
    			findNode(i+1).next = null;
    			head = findNode(i+1);
    		}else {
    			head.prev = null;
    			head.next = null;
    		}
    	}else {
    		findNode(i).prev.next = findNode(i).next;
    		findNode(i).next.prev = findNode(i).prev;
    	}
    	size--;
    	return new_e;
    }
    
    public void add(int i, E e) {
    	Node<E> n = new Node<E>(e, null, null);
    	if(isEmpty()) {
    		head = n;
    	}else if(size == 1) {
    		n.prev = head;
    		n.next = head;
    		head.prev = n;
    		head.next = n;
    	}else {
    		if(i == 0) {
    			n.prev = findNode(i).prev;
    	    	n.next = findNode(i);
    	    	findNode(i).prev.next = n;
    	    	findNode(i).prev = n;
    	    	head = n;
    		}else if(size != i && i != 0) {
    			n.prev = findNode(i).prev;
    			n.next = findNode(i);
    			findNode(i).prev.next = n;
    			findNode(i).prev = n;
    		}else if(size == i){
    			n.prev = findNode(i - 1);
    			n.next = head;
    			head.prev = n;
    			findNode(i - 1).next = n;
    		}
    	}
    	size++;
    }
    
    
    //TODO: implement interface Iterable
    public Iterator<E> iterator(){
    	return new NodeIterator<E>(head);
    }
    

    //helper methods
    protected Node<E> findNode(int i) {
        if(i < 0 || i >= size)
            throw new IndexOutOfBoundsException("invalid index: " + i + " is not in [ 0, " + size + ")");
        
        //TODO: find the node at index i and return it 
        Node<E> n = head;
        int x = 0;
        
        while(x != i) {
        	n = n.next;
        	x++;
        }
        return n;
    }
    private static void onFalseThrow(boolean b) {
        if(!b)
            throw new RuntimeException("Error: unexpected");            
    }
    public static void main(String[] args) {
        CircularlyDblLinkedList<Integer> list = new CircularlyDblLinkedList<Integer>();
        list.add(list.size(),2);
        list.add(list.size(),3);
        list.add(list.size(),4);
        list.add(0, 1);
        onFalseThrow(list.remove(list.size()-1) == 4); 
        onFalseThrow(list.remove(list.size()-1) == 3); 
        onFalseThrow(list.remove(0) == 1); 
        onFalseThrow(list.remove(list.size()-1) == 2); 
        onFalseThrow(list.isEmpty()); 
        System.out.println("Success!");
    }    
}
