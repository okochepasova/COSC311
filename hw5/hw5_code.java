class IntBSTNode {
    protected int key;
    protected IntBSTNode left, right;

    public IntBSTNode() {
        left = right = null;
    }
    public IntBSTNode(int el) {
        this(el,null,null);
    }
    public IntBSTNode(int el, IntBSTNode lt, IntBSTNode rt) {
        key = el;
        left = lt;
        right = rt;
    }
}


public class IntBST{

    protected IntBSTNode root;

    public IntBST() {
        root = null;
    }

    public void preorder() {
        preorder(root);
        System.out.println();
    }
    protected void preorder(IntBSTNode p) {
        if (p != null) {
            visit(p);
            preorder(p.left);
            preorder(p.right);
        }
    }
    public void inorder() {
        inorder(root);
        System.out.println();
    }
    protected void inorder(IntBSTNode p) {
        if (p != null) {
            inorder(p.left);
            visit(p);
            inorder(p.right);
        }
    }
    public void postorder() {
        postorder(root);
        System.out.println();
    }
    protected void postorder(IntBSTNode p) {
        if (p != null) {
            postorder(p.left);
            postorder(p.right);
            visit(p);
        }
    }
    protected void visit(IntBSTNode p) {
        System.out.print(p.key + " ");
        //callA(p);
    }

    void callA(IntBSTNode p) {
        if (p.left != null && p.key - p.left.key < 2)
	        p.left.key += 2;
    }
    void callB(IntBSTNode p) {
        if (p.left == null)
            p.right = null;
    }
    void callC(IntBSTNode p) {
        if (p.left == null)
	        p.left = new IntBSTNode(p.key-1);
    }
    void callD(IntBSTNode p) {
        IntBSTNode temp = p.right;
        p.right = p.left;
        p.left = temp;
    }

    public static void main(String []args){
        IntBSTNode zero = new IntBSTNode(0), four = new IntBSTNode(4,zero,null),
eight = new IntBSTNode(8), seven = new IntBSTNode(7,null,eight),
six = new IntBSTNode(6,null,seven), five = new IntBSTNode(5,four,six),
thirteen = new IntBSTNode(13), fifteen = new IntBSTNode(15,thirteen,null),
thirty = new IntBSTNode(30), twenty = new IntBSTNode(20,fifteen,thirty),
ten = new IntBSTNode(10,five,twenty);
        IntBST tree = new IntBST();
        tree.root = ten;

        tree.preorder();
        //tree.inorder();
        //tree.postorder();
    }
}