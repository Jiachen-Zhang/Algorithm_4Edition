# Union Find 

> A set of algorithm for solving dynamic connectivity problem.

## Dynamic Connectivity

- **Union command**: connect two objects. // add an edge
- **Find/connected query**: is there a path connecting the two objects?
- `is connected to` :
  - **Reflexive**: `p` is connected to `p`.
  - **Symmetric**: If `p` is connected to `q`, then `q` is connected to `p`.
  - **Transitive**: If `p` is connected to `q` and `q` is connected to `r`, the `p` is connected to `r`.

***

- **Connected Components**: Maximal **set** of objects that are mutually connected.
  - **Find query**: Check if two objects are in the same component.
  - **Union command**: Replace components containing two objects with their union.

## Quick Find

```java
public class QuickFindUF{
    private int[] id;
    
    public QuickFindUF(int N){
        id = new int[N];
        for(int i = 0; i < N; i++)
            id[i] = i;
    }
    
    public boolean connected(int p, int q)
        return id[p] == id[q];
    
    public void union(int p, int q){//connect all connected to p to q
        int pid = id[p];	// as id[p] will change, store it before process
        int qid = id[q];
        for(int i = 0; i < N; i++)
            if(id[i] == pid) id[i] = qid;
    }
}
```

## Quick Union

```java
public class QuickUnionUF{
    private int[] id;
    
    public QuickUnionUF(int N){
        id = new int[N];
        for(int i = 0; i < N; i++) id[i] = i;
    }
    
    private int root(int i){
        while(i != id[i]) i = id[i];
        return i;
    }
    
    public boolean connected(int p, int q)
        return root(p) == root(q);
    
    public void union(int p, int q)
        id[root(p)] = root(q);
    
}
```

## Improvements

### Weighted Quick-union

- Modify quick-union to avoid tall trees
- Keep **track of size** of each tree
- Balance by linking root of smaller tree to root of larger tree

### Quick-union with Path Compression

- Just after computing the root of `p`, set the id of each examined node to point to that root.
  - Two-pass implementation: add second loop.
  - Simpler one-pass variant: Make every other node in path point to its grandparent.

```java
public class QuickUnionUF{
    private int[] id;
    private int[] sz;
    
    public QuickUnionUF(int N){
        id = new int[N];
        sz = new int[N];
        for(int i = 0; i < N; i++) id[i] = i;
    }
    
    private int root(int i){
        while(i != id[i]){
            id[i] = id[id[i]];
			i = id[i];
        } 
        return i;
    }
    
    public boolean connected(int p, int q)
    {    return root(p) == root(q);}
    
    public void union(int p, int q){
        int i = root(p);
    	int j = root(q);
    	if(i == j) return;
    	if(sz[i] < sz[j]){id[i] = i; sz[j] += sz[i];}
    	else			 {id[j] = i; sz[i] += sz[j];}
    }

}
```

## Applications

